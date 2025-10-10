package model;   // Declaramos el paquete

/**
 * Clase modelo que representa una inscripción individual.
 */
public class Inscripcion {
    private String nombre;
    private String dni;
    private String curso;
    private String hora;


    public Inscripcion(String nombre, String dni, String curso, String hora) {
        this.nombre = nombre;
        this.dni = dni;
        this.curso = curso;
        this.hora = hora;
    }

    // Métodos getter
    public String getNombre() { return nombre; }
    public String getDni() { return dni; }
    public String getCurso() { return curso; }
    public String getHora() { return hora; }

    @Override
    public String toString() {
        // Esto define cómo se mostrará cada inscripción en la interfaz
        return nombre + " (" + dni + ") - " + curso + " | " + hora;
    }

    public Object getFechaHora() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
