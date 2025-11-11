package logic;

import model.Inscripcion;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio que gestiona la persistencia de inscripciones en archivo.
 */
public class PersistenciaService {
    
    private static final String ARCHIVO_DATOS = "inscripciones.dat";
    private static final String SEPARADOR = "|";
    
    /**
     * Guarda todas las inscripciones en un archivo de texto.
     */
    public void guardarInscripciones(List<Inscripcion> inscripciones) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_DATOS))) {
            for (Inscripcion ins : inscripciones) {
                // Formato: nombre|dni|curso|hora
                String linea = String.join(SEPARADOR, 
                    ins.getNombre(), 
                    ins.getDni(), 
                    ins.getCurso(), 
                    ins.getHora()
                );
                writer.write(linea);
                writer.newLine();
            }
            System.out.println("✓ Datos guardados correctamente en " + ARCHIVO_DATOS);
        } catch (IOException e) {
            System.err.println("✗ Error al guardar datos: " + e.getMessage());
        }
    }
    
    /**
     * Carga las inscripciones desde el archivo.
     * Si el archivo no existe, retorna una lista vacía.
     */
    public List<Inscripcion> cargarInscripciones() {
        List<Inscripcion> inscripciones = new ArrayList<>();
        
        // Verificar si el archivo existe
        if (!Files.exists(Paths.get(ARCHIVO_DATOS))) {
            System.out.println("→ No hay datos previos. Iniciando con lista vacía.");
            return inscripciones;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_DATOS))) {
            String linea;
            int contador = 0;
            
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split("\\" + SEPARADOR);
                
                if (partes.length == 4) {
                    Inscripcion ins = new Inscripcion(
                        partes[0], // nombre
                        partes[1], // dni
                        partes[2], // curso
                        partes[3]  // hora
                    );
                    inscripciones.add(ins);
                    contador++;
                }
            }
            
            System.out.println("✓ Cargados " + contador + " registros desde " + ARCHIVO_DATOS);
            
        } catch (IOException e) {
            System.err.println("✗ Error al cargar datos: " + e.getMessage());
        }
        
        return inscripciones;
    }
    
    /**
     * Verifica si existe un archivo de datos previo.
     */
    public boolean existenDatosPrevios() {
        return Files.exists(Paths.get(ARCHIVO_DATOS));
    }
}