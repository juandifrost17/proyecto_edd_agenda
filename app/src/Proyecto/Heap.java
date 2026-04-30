package Proyecto;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;

public class Heap<E> {
    private ArrayList<E> datos;
    private int n;
    private boolean ordenAsc;
    private Comparator<E> cmp;

    public Heap(Comparator<E> cmp, boolean ordenAsc) {
        this.datos = new ArrayList<>();
        this.datos.add(null);
        this.n = 0;
        this.ordenAsc = ordenAsc;
        this.cmp = cmp;
    }

    private boolean esHoja(int indice) {
        return idxIzq(indice) == -1 && idxDer(indice) == -1;
    }

    public boolean estaVacio() {
        return n == 0;
    }

    public int idxIzq(int r) {
        int idx = 2 * r;
        if (idx <= n) {
            return idx;
        }
        return -1;
    }

    public int idxDer(int r) {
        int idx = 2 * r + 1;
        if (idx <= n) {
            return idx;
        }
        return -1;
    }

    public int idxPadre(int r) {
        if (r <= 1) {
            return -1;
        }
        return r / 2;
    }

    public void insertar(E elemento) {
        datos.add(elemento);
        n++;
        flotar(n);
    }

    private void intercambio(int i, int j) {
        E temp = datos.get(i);
        datos.set(i, datos.get(j));
        datos.set(j, temp);
    }

    public E extraer() {
        if (n == 0) {
            return null;
        }
        E resultado = datos.get(1);
        intercambio(1, n);
        datos.remove(n);
        n--;
        if (n > 0) {
            ajustar(1);
        }
        return resultado;
    }

    private boolean tienePrioridad(E candidato, E actual) {
        int resultado = cmp.compare(candidato, actual);
        if (ordenAsc) {
            return resultado < 0;
        }
        return resultado > 0;
    }

    private void flotar(int indice) {
        if (indice <= 1) {
            return;
        }
        int padre = idxPadre(indice);
        if (tienePrioridad(datos.get(indice), datos.get(padre))) {
            intercambio(padre, indice);
            flotar(padre);
        }
    }

    private void ajustar(int indice) {
        if (esHoja(indice)) {
            return;
        }
        int izq = idxIzq(indice);
        int der = idxDer(indice);
        int mejor = indice;
        if (izq != -1 && tienePrioridad(datos.get(izq), datos.get(mejor))) {
            mejor = izq;
        }
        if (der != -1 && tienePrioridad(datos.get(der), datos.get(mejor))) {
            mejor = der;
        }
        if (mejor != indice) {
            intercambio(indice, mejor);
            ajustar(mejor);
        }
    }

    public static <E> LinkedList<E> ordenarConHeap(LinkedList<E> lista, Comparator<E> cmp, boolean asc) {
        Heap<E> heap = new Heap<>(cmp, asc);
        for (E elemento : lista) {
            heap.insertar(elemento);
        }
        LinkedList<E> resultado = new LinkedList<>();
        while (!heap.estaVacio()) {
            resultado.addLast(heap.extraer());
        }
        return resultado;
    }
}