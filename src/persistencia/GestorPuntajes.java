package persistencia;

import java.io.*;
import java.util.*;

public class GestorPuntajes {
    private static final String NOMBRE_ARCHIVO = "puntuaciones_maximas.txt";

    public static void guardarRecord(String nombreJugador, int puntos) {
        List<String> todosLosPuntajes = leerPuntajes();
        // Agregamos el nuevo resultado: "Nombre - Puntos"
        todosLosPuntajes.add(nombreJugador + " - " + puntos);

        // Ordenamos de mayor a menor basándonos en el número después del " - "
        todosLosPuntajes.sort((a, b) -> {
            int p1 = Integer.parseInt(a.split(" - ")[1]);
            int p2 = Integer.parseInt(b.split(" - ")[1]);
            return Integer.compare(p2, p1);
        });

        // Escribimos en el bloc de notas solo los 3 mejores (Requisito Score)
        try (PrintWriter escritor = new PrintWriter(new FileWriter(NOMBRE_ARCHIVO))) {
            for (int i = 0; i < Math.min(3, todosLosPuntajes.size()); i++) {
                escritor.println(todosLosPuntajes.get(i));
            }
        } catch (IOException e) {
            System.out.println("Error al escribir el archivo.");
        }
    }

    public static List<String> leerPuntajes() {
        List<String> lista = new ArrayList<>();
        File archivo = new File(NOMBRE_ARCHIVO);
        if (!archivo.exists()) return lista;

        try (Scanner lector = new Scanner(archivo)) {
            while (lector.hasNextLine()) {
                lista.add(lector.nextLine());
            }
        } catch (FileNotFoundException e) { }
        return lista;
    }
}