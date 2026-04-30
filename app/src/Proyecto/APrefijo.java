package Proyecto;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class APrefijo<E> {
    private Map<Character, APrefijo<E>> hijos;
    private boolean finNombre;
    private LinkedList<E> datos;
    public Map<Character, APrefijo<E>> getHijos() {return hijos;}
    public LinkedList<E> getDatos() {return datos;}
    public boolean isFinNombre() {return finNombre;}

    public APrefijo() {
        this.hijos = new HashMap<>();
        this.finNombre = false;
        this.datos = new LinkedList<>();
    }

    public void insertar(String palabra, E dato) {
        APrefijo<E> actual = this;
        for (int i = 0; i < palabra.length(); i++) {
            char letra = palabra.charAt(i);
            if (!actual.hijos.containsKey(letra)) {
                actual.hijos.put(letra, new APrefijo<>());
            }
            actual = actual.hijos.get(letra);
        }
        actual.finNombre = true;
        actual.datos.add(dato);
    }

    //Buscar todos los contactos cuyo nombre empieza con el prefijo
    public LinkedList<E> buscarPorPrefijo(String prefijo) {
        APrefijo<E> nodoBase = buscarNodoFinal(prefijo);
        LinkedList<E> resultados = new LinkedList<>();
        if (nodoBase != null) {
            recolectarDatos(nodoBase, resultados);
        }
        return resultados;
    }

    //Metodo auxiliar para encontrar el nodo donde termina el prefijo
    private APrefijo<E> buscarNodoFinal(String prefijo) {
        APrefijo<E> actual = this;
        for (int i = 0; i < prefijo.length(); i++) {
            char letra = prefijo.charAt(i);
            actual = actual.hijos.get(letra);
            if (actual == null) return null;
        }
        return actual;
    }

    //Metodo recursivo para entrar en todas las ramas y traer los contactos
    private void recolectarDatos(APrefijo<E> nodo, LinkedList<E> lista) {
        if (nodo.finNombre) {
            for (E dato : nodo.datos) {
                lista.add(dato);
            }
        }
        for (APrefijo<E> hijo : nodo.hijos.values()) {
            recolectarDatos(hijo, lista);
        }
    }

    //Coordina la logica para la eliminacion
    public boolean eliminarContacto(String palabra, E dato) {
        APrefijo<E> nodoFinal = buscarNodoFinal(palabra);
        if (nodoFinal == null) return false;
        boolean removido = removerDato(nodoFinal, dato);
        if (removido) {
            podarRama(this, palabra, 0);
        }
        return removido;
    }

    //Solo elimina el dato del nodo
    private boolean removerDato(APrefijo<E> nodo, E dato) {
        if (!nodo.finNombre) return false;
        boolean removido = nodo.datos.remove(dato);
        if (removido && nodo.datos.isEmpty()) {
            nodo.finNombre = false;
        }
        return removido;
    }

    //Limpiar nodos vacíos que quedaron huérfanos dentro del arbol
    private boolean podarRama(APrefijo<E> nodo, String palabra, int indice) {
        if (indice == palabra.length()) {
            return nodo.hijos.isEmpty() && !nodo.finNombre && nodo.datos.isEmpty();
        }
        char letra = palabra.charAt(indice);
        if (!nodo.hijos.containsKey(letra)) return false;
        APrefijo<E> hijo = nodo.hijos.get(letra);
        boolean deboBorrar = podarRama(hijo, palabra, indice + 1);
        if (deboBorrar) {
            nodo.hijos.remove(letra);
        }
        return nodo.hijos.isEmpty() && !nodo.finNombre && nodo.datos.isEmpty();
    }
}