package logica;

import java.util.*;

public class Partida {
    // Arreglo con 10 palabras
    public String[] listaPalabrasMaestra = {"Luna", "Sol", "Estrella", "Nube", "Rayo", "Cometa", "Planeta", "Tierra", "Marte", "Cielo"};
    public List<String> palabrasDeEstaRonda;
    public int nivelActual;

    public Partida(int nivel) {
        this.nivelActual = nivel;
        this.palabrasDeEstaRonda = seleccionarPalabrasAlAzar();
    }

    private List<String> seleccionarPalabrasAlAzar() {
        // Genera un número aleatorio entre 3 y 5 (Requisito: el usuario no decide cuántas)
        int cantidad = new Random().nextInt(3) + 3; 
        
        // Mezclamos la lista maestra para elegir palabras distintas cada vez
        List<String> temporal = Arrays.asList(listaPalabrasMaestra);
        Collections.shuffle(temporal);
        
        // Tomamos solo la cantidad aleatoria decidida (3, 4 o 5)
        return new ArrayList<>(temporal.subList(0, cantidad));
    }

    public boolean validarRespuesta(String entradaUsuario) {
        // Convertimos lo que escribió el usuario en una lista, separando por comas
        String[] palabrasUsuario = entradaUsuario.trim().split("\\s*,\\s*");
        List<String> listaUsuario = Arrays.asList(palabrasUsuario);

        // Creamos una copia de las palabras correctas para comparar
        List<String> listaCorrecta = new ArrayList<>(palabrasDeEstaRonda);
        
        // Si es Nivel 2, ordenamos la lista correcta alfabéticamente (Requisito Nivel 2)
        if (this.nivelActual == 2) {
            Collections.sort(listaCorrecta);
        }

        // Compara si ambas listas son exactamente iguales (100% de coincidencia)
        return listaCorrecta.equals(listaUsuario);
    }

    public List<String> getPalabrasDeEstaRonda() {
        return palabrasDeEstaRonda;
    }
}