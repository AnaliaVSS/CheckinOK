package ui;   // Declaramos el paquete

import logic.SesionService;
import model.Inscripcion;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Ventana principal de la aplicación.
 */
public class CheckInGUI {

    // Formato de fecha/hora para mostrar en el listado
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static void show(SesionService service) {
        // Crea la ventana de forma segura en el hilo de Swing
        SwingUtilities.invokeLater(() -> createAndShow(service));
    }

    private static void createAndShow(SesionService service) {
        JFrame frame = new JFrame("Registro de Inscripciones");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);               // un poco más grande para que entren los nuevos controles
        frame.setLocationRelativeTo(null);

        // Panel raíz con BorderLayout para secciones
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(20, 20, 15, 20));
        root.setBackground(new Color(230, 240, 255)); // color de fondo celeste
        frame.setContentPane(root);

        // Panel principal (form + barra búsqueda + listado) con BoxLayout vertical
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        root.add(panel, BorderLayout.CENTER);

        // ====== FORMULARIO ======
        JTextField txtNombre = new JTextField(20);
        JTextField txtDni = new JTextField(10);
        JComboBox<String> cmbCurso = new JComboBox<>(new String[]{
                "Programación I","Programación II", "Base de Datos", "Matemática", "Inglés", "Lógica"
        });
        JButton btnRegistrar = new JButton("Registrar");

        // Estética del botón Registrar
        btnRegistrar.setBackground(new Color(30, 144, 255)); // azul tipo "DodgerBlue"
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(25,25,112), 1, true),
                BorderFactory.createEmptyBorder(8,16,8,16)
        ));

        // Contenedor del formulario (alineado vertical)
        JPanel form = new JPanel();
        form.setOpaque(false);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.add(new JLabel("Nombre:"));
        form.add(txtNombre);
        form.add(Box.createVerticalStrut(6));
        form.add(new JLabel("CI:"));
        form.add(txtDni);
        form.add(Box.createVerticalStrut(6));
        form.add(new JLabel("Curso:"));
        form.add(cmbCurso);
        form.add(Box.createVerticalStrut(10));
        form.add(btnRegistrar);

        panel.add(form);
        panel.add(Box.createVerticalStrut(10));

        // ====== BARRA DE BÚSQUEDA Y ACCIONES ======
        JPanel barra = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        barra.setOpaque(false);

        JTextField txtBuscar = new JTextField(20);
        JButton btnBuscar = new JButton("Buscar");
        JButton btnMostrarTodo = new JButton("Mostrar todo");
        JButton btnResumen = new JButton("Resumen");

        barra.add(new JLabel("Buscar (nombre o documento):"));
        barra.add(txtBuscar);
        barra.add(btnBuscar);
        barra.add(btnMostrarTodo);
        barra.add(btnResumen);

        panel.add(barra);
        panel.add(Box.createVerticalStrut(8));

        // ====== LISTADO ======
        JTextArea txtListado = new JTextArea(16, 60);
        txtListado.setEditable(false);
        txtListado.setFont(new Font("Consolas", Font.PLAIN, 13));
        JScrollPane scroll = new JScrollPane(txtListado);

        panel.add(scroll);

        // ====== ACCIONES ======

        // Registrar y refrescar listado
        btnRegistrar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            String dni = txtDni.getText().trim();
            String curso = (String) cmbCurso.getSelectedItem();

            // Validaciones simples
            if (nombre.isEmpty() || dni.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Por favor complete nombre y CI.");
                return;
            }

            service.registrar(nombre, dni, curso);
            listar(txtListado, service.listar());

            // Limpiamos campos
            txtNombre.setText("");
            txtDni.setText("");
            txtNombre.requestFocusInWindow();
        });

        // Buscar por texto
        btnBuscar.addActionListener(e -> {
            String q = txtBuscar.getText();
            listar(txtListado, service.buscar(q));
        });

        // Mostrar todo (resetea búsqueda)
        btnMostrarTodo.addActionListener(e -> {
            txtBuscar.setText("");
            listar(txtListado, service.listar());
        });

        // Resumen básico por curso
        btnResumen.addActionListener(e -> {
            String msg = service.resumen();
            JOptionPane.showMessageDialog(frame, msg, "Resumen por curso", JOptionPane.INFORMATION_MESSAGE);
        });

        // Carga inicial (si hay datos demo)
        listar(txtListado, service.listar());

        frame.setVisible(true);
    }

      /**
     * Muestra las inscripciones en el área de texto con hora (si está disponible).
     */
    private static void listar(JTextArea txt, List<Inscripcion> inscripciones) {
        txt.setText("");

        // 1. Imprimir Encabezado (alineado)
        txt.append(String.format("%-25s | %-12s | %-15s | %s%n", 
            "NOMBRE", "CI", "CURSO", "FECHA/HORA"));
        
        // 2. Imprimir Separador
        txt.append("------------------------------------------------------------------------------\n");

        // 3. Imprimir Registros
        for (Inscripcion i : inscripciones) {
            // Formatea el texto de salida en columnas alineadas
            txt.append(String.format("%-25s | %-12s | %-15s | %s%n",
                    i.getNombre(), i.getDni(), i.getCurso(), i.getHora()));
        }
        
        txt.setCaretPosition(0); // Scroll al inicio
    }
} // <--- Cierre de la clase
    
