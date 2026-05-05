package igu;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import logica.Partida;
import persistencia.GestorPuntajes;

public class VentanaJuego extends JFrame {
    private Partida partidaActiva;
    private int segundosRestantes = 30;
    private Timer cronometro;
    
    private JLabel lblReloj;
    private JTextField txtRespuesta;
    private JButton btnInicio, btnFin, btnCerrar;
    private DefaultTableModel modeloTabla;

    public VentanaJuego() {
        // Configuracion de la ventana
        setTitle("Simón Dice - Control Total");
        setSize(550, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(false); // Mantener bordes estandar
        getContentPane().setBackground(EstiloVisual.FONDO_PRINCIPAL);
        setLayout(new BorderLayout(15, 15));

        // --- NORTE: RELOJ ---
        lblReloj = new JLabel("30s", SwingConstants.CENTER);
        lblReloj.setFont(EstiloVisual.FUENTE_RELOJ);
        lblReloj.setForeground(EstiloVisual.TEXTO_OSCURO);
        add(lblReloj, BorderLayout.NORTH);

        // --- CENTRO: CAMPO DE TEXTO Y TABLA ---
        JPanel panelCentro = new JPanel(new BorderLayout(10, 10));
        panelCentro.setOpaque(false);

        txtRespuesta = new JTextField();
        txtRespuesta.setFont(new Font("Arial", Font.BOLD, 20));
        txtRespuesta.setHorizontalAlignment(JTextField.CENTER);
        txtRespuesta.setEnabled(false);
        txtRespuesta.setBorder(BorderFactory.createTitledBorder("Escribe aquí tus palabras"));

        // Tabla de puntuaciones
        modeloTabla = new DefaultTableModel(new String[]{"Posición", "Jugador", "Puntos"}, 0);
        JTable tabla = new JTable(modeloTabla);
        actualizarTabla();

        panelCentro.add(txtRespuesta, BorderLayout.NORTH);
        panelCentro.add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(panelCentro, BorderLayout.CENTER);

        // --- SUR: PANEL DE BOTONES (INICIO, FIN, CERRAR) ---
        JPanel panelBotones = new JPanel(new GridLayout(1, 3, 10, 10));
        panelBotones.setOpaque(false);

        btnInicio = new JButton("INICIAR");
        EstiloVisual.aplicarEstiloBoton(btnInicio, EstiloVisual.VERDE_BOTON);
        btnInicio.addActionListener(e -> iniciarJuego());

        btnFin = new JButton("FINALIZAR");
        EstiloVisual.aplicarEstiloBoton(btnFin, EstiloVisual.AZUL_BOTON);
        btnFin.setEnabled(false);
        btnFin.addActionListener(e -> finalizarJuegoManualmente());

        btnCerrar = new JButton("CERRAR");
        EstiloVisual.aplicarEstiloBoton(btnCerrar, new Color(149, 165, 166)); // Gris
        btnCerrar.addActionListener(e -> System.exit(0));

        panelBotones.add(btnInicio);
        panelBotones.add(btnFin);
        panelBotones.add(btnCerrar);
        
        add(panelBotones, BorderLayout.SOUTH);

        // Margen general
        ((JPanel)getContentPane()).setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    private void iniciarJuego() {
        partidaActiva = new Partida(1);
        String palabras = String.join(", ", partidaActiva.getPalabrasDeEstaRonda());
        
        JOptionPane.showMessageDialog(this, "Memoriza:\n" + palabras, "Fase de Memoria", JOptionPane.INFORMATION_MESSAGE);

        // Ajustar estados de botones
        txtRespuesta.setEnabled(true);
        txtRespuesta.setText("");
        txtRespuesta.requestFocus();
        btnInicio.setEnabled(false);
        btnFin.setEnabled(true);
        
        segundosRestantes = 30;
        lblReloj.setText("30s");
        lblReloj.setForeground(EstiloVisual.TEXTO_OSCURO);

        cronometro = new Timer(1000, e -> {
            segundosRestantes--;
            lblReloj.setText(segundosRestantes + "s");

            if (segundosRestantes <= 5) lblReloj.setForeground(Color.RED);

            if (segundosRestantes <= 0) {
                evaluarYTerminar("¡TIEMPO AGOTADO!");
            }
        });
        cronometro.start();
    }

    private void finalizarJuegoManualmente() {
        evaluarYTerminar("JUEGO FINALIZADO");
    }

    private void evaluarYTerminar(String mensaje) {
        if (cronometro != null) cronometro.stop();
        txtRespuesta.setEnabled(false);
        btnInicio.setEnabled(true);
        btnFin.setEnabled(false);

        String entrada = txtRespuesta.getText();
        if (partidaActiva.validarRespuesta(entrada)) {
            String nombre = JOptionPane.showInputDialog(this, mensaje + "\n¡Correcto! Tu nombre:");
            GestorPuntajes.guardarRecord(nombre != null ? nombre : "Anónimo", 1000);
            actualizarTabla();
        } else {
            JOptionPane.showMessageDialog(this, mensaje + "\nLas palabras no coinciden.");
        }
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        List<String> records = GestorPuntajes.leerPuntajes();
        for (int i = 0; i < records.size(); i++) {
            String[] datos = records.get(i).split(" - ");
            modeloTabla.addRow(new Object[]{(i + 1) + "º", datos[0], datos[1]});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaJuego().setVisible(true));
    }
}