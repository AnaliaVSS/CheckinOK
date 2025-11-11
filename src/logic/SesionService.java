package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import model.Inscripcion;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase que gestiona las inscripciones (la lógica del programa).
 */
public class SesionService {

    // Lista de inscripciones almacenadas en memoria
    private final List<Inscripcion> inscripciones = new ArrayList<>();
    
    // Servicio de persistencia
    private final PersistenciaService persistenciaService = new PersistenciaService();
    
    // Formato de hora para asegurar consistencia
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    // Constructor que carga datos guardados o datos demo
    public SesionService() {
        cargarDatos();
    }

    /**
     * Carga datos al iniciar: primero intenta cargar del archivo,
     * si no existe, carga datos demo.
     */
    private void cargarDatos() {
        if (persistenciaService.existenDatosPrevios()) {
            // Cargar datos guardados
            List<Inscripcion> datosGuardados = persistenciaService.cargarInscripciones();
            inscripciones.addAll(datosGuardados);
        } else {
            // Si no hay datos previos, carga datos demo
            cargarDatosDemo();
        }
    }

    /**
     * Agrega una nueva inscripción y GUARDA automáticamente.
     */
    public void registrar(String nombre, String dni, String curso) {
        LocalDateTime ahora = LocalDateTime.now();
        String horaTxt = ahora.format(FMT);
        
        // Creamos la nueva inscripción. 
        Inscripcion ins = new Inscripcion(nombre, dni, curso, horaTxt);
        inscripciones.add(0, ins); // Lo agregamos al inicio
        
        // GUARDAR automáticamente después de cada registro
        guardar();
    }

    /**
     * Devuelve todas las inscripciones realizadas.
     */
    public List<Inscripcion> listar() {
        return inscripciones;
    }

    /**
     * Implementación completa del botón 'Buscar'. Filtra por nombre o DNI.
     */
    public List<Inscripcion> buscar(String q) {
        if (q == null || q.trim().isEmpty()) {
            return listar();
        }
        final String query = q.toLowerCase().trim();

        return inscripciones.stream()
                .filter(i -> i.getNombre().toLowerCase().contains(query) || i.getDni().contains(query))
                .collect(Collectors.toList());
    }

    /**
     * Implementación completa del botón 'Resumen'. Genera un resumen de inscripciones por curso.
     */
    public String resumen() {
        Map<String, Long> conteoPorCurso = inscripciones.stream()
                .collect(Collectors.groupingBy(Inscripcion::getCurso, Collectors.counting()));

        StringBuilder sb = new StringBuilder();
        sb.append("--- Resumen de Inscripciones por Curso ---\n\n");

        conteoPorCurso.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .forEach(entry -> {
                    sb.append(String.format("- %s: %d\n", entry.getKey(), entry.getValue()));
                });

        return sb.toString();
    }

    /**
     * Guarda manualmente todas las inscripciones.
     */
    public void guardar() {
        persistenciaService.guardarInscripciones(inscripciones);
    }

    /**
     * Carga algunos datos de ejemplo para probar la interfaz.
     * Solo se usa si NO hay datos previos guardados.
     */
    public void cargarDatosDemo() {
        try { Thread.sleep(50); } catch (Exception e) {}
        registrar("Ana López", "12345678", "Programación I");
        try { Thread.sleep(50); } catch (Exception e) {}
        registrar("Juan Pérez", "98765432", "Base de Datos");
        try { Thread.sleep(50); } catch (Exception e) {}
        registrar("Lucía Gómez", "45678912", "Lógica");
        try { Thread.sleep(50); } catch (Exception e) {}
        registrar("Pedro Ramírez", "11223344", "Programación I");
    }
}