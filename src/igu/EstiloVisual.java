package igu;

import java.awt.*;
import javax.swing.*;

public class EstiloVisual {
    // Definición de colores tipo "Flat Design"
    public static final Color FONDO_PRINCIPAL = new Color(236, 240, 241); // Blanco nube
    public static final Color AZUL_BOTON = new Color(52, 152, 219);      // Azul sereno
    public static final Color VERDE_BOTON = new Color(46, 204, 113);     // Verde esmeralda
    public static final Color TEXTO_OSCURO = new Color(44, 62, 80);      // Gris azulado
    public static final Color COLOR_RELOJ_LISTO = new Color(231, 76, 60); // Rojo suave

    // Fuentes
    public static final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 28);
    public static final Font FUENTE_RELOJ = new Font("Consolas", Font.BOLD, 40);
    public static final Font FUENTE_BOTON = new Font("Tahoma", Font.BOLD, 14);

    // Método para aplicar estilo a los botones
    public static void aplicarEstiloBoton(JButton boton, Color colorFondo) {
        boton.setBackground(colorFondo);
        boton.setForeground(Color.WHITE);
        boton.setFont(FUENTE_BOTON);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(colorFondo.darker(), 1),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}