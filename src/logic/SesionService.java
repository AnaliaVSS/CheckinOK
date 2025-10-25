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
    
    // Formato de hora para asegurar consistencia
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    // Constructor que carga datos demo al iniciar la aplicación
    public SesionService() {
        cargarDatosDemo();
    }

    /**
     * Agrega una nueva inscripción.
     */
    public void registrar(String nombre, String dni, String curso) {
        LocalDateTime ahora = LocalDateTime.now();
        String horaTxt = ahora.format(FMT);
        
        // Creamos la nueva inscripción. 
        Inscripcion ins = new Inscripcion(nombre, dni, curso, horaTxt);
        inscripciones.add(0, ins); // Lo agregamos al inicio para que se vea primero
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
            return listar(); // Si no hay texto de búsqueda, muestra todo.
        }
        final String query = q.toLowerCase().trim();

        return inscripciones.stream()
                // Filtra si el nombre O el DNI contienen la cadena de búsqueda
                .filter(i -> i.getNombre().toLowerCase().contains(query) || i.getDni().contains(query))
                .collect(Collectors.toList());
    }

    /**
     * Implementación completa del botón 'Resumen'. Genera un resumen de inscripciones por curso.
     */
    public String resumen() {
        // 1. Agrupar las inscripciones por el campo 'curso' y contar cuántas hay de cada uno
        Map<String, Long> conteoPorCurso = inscripciones.stream()
                .collect(Collectors.groupingBy(Inscripcion::getCurso, Collectors.counting()));

        // 2. Construir el mensaje de resumen
        StringBuilder sb = new StringBuilder();
        sb.append("--- Resumen de Inscripciones por Curso ---\n\n");

        conteoPorCurso.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed()) // Ordena por cantidad descendente
                .forEach(entry -> {
                    sb.append(String.format("- %s: %d\n", entry.getKey(), entry.getValue()));
                });

        return sb.toString();
    }

    /**
     * Carga algunos datos de ejemplo para probar la interfaz.
     */
    // EN src/logic/SesionService.java

public void cargarDatosDemo() {
    // Estas líneas añaden datos, PERO NO DEBEN IMPRIMIR NADA
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
