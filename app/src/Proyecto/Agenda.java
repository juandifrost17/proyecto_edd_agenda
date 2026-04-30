package Proyecto;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Agenda {
    private APrefijo<Contacto> arbol;
    private Set<String> archivosCargados;

    public Agenda() {
        this.arbol = new APrefijo<>();
        this.archivosCargados = new HashSet<>();
    }

    public APrefijo<Contacto> getArbol() { return arbol; }

    public boolean archivoYaFueCargado(Path ruta) {
        if (ruta == null) return false;
        return archivosCargados.contains(ruta.toAbsolutePath().normalize().toString().toLowerCase());
    }

    public void marcarArchivoComoCargado(Path ruta) {
        if (ruta == null) return;
        archivosCargados.add(ruta.toAbsolutePath().normalize().toString().toLowerCase());
    }

    private void insertarAtributo(String valor, Contacto c) {
        if (valor != null && !valor.trim().isEmpty()) {
            arbol.insertar(valor.trim().toLowerCase(), c);
        }
    }

    private boolean eliminarAtributo(String valor, Contacto c) {
        if (valor != null && !valor.trim().isEmpty()) {
            return arbol.eliminarContacto(valor.trim().toLowerCase(), c);
        }
        return false;
    }

    public void registrarContacto(Contacto c) {
        if (c == null) return;
        insertarAtributo(c.getNombre(), c);
        insertarAtributo(c.getApellido(), c);
        insertarAtributo(c.getApodo(), c);
    }

    public boolean eliminarContacto(Contacto c) {
        boolean eliminado = false;
        if (eliminarAtributo(c.getNombre(), c)) eliminado = true;
        if (eliminarAtributo(c.getApellido(), c)) eliminado = true;
        if (eliminarAtributo(c.getApodo(), c)) eliminado = true;
        return eliminado;
    }

    private LinkedList<Contacto> obtenerUnicos(String prefijo) {
        LinkedList<Contacto> resultadosBrutos = arbol.buscarPorPrefijo(prefijo.trim().toLowerCase());
        Set<Contacto> vistos = new HashSet<>();
        LinkedList<Contacto> unicos = new LinkedList<>();
        for (Contacto c : resultadosBrutos) {
            if (vistos.add(c)) unicos.add(c);
        }
        return unicos;
    }

    public LinkedList<Contacto> buscarParaEliminar(String prefijo) {
        return Heap.ordenarConHeap(obtenerUnicos(prefijo), new ComparadorFrecuencia(), false);
    }

    public LinkedList<Contacto> buscarPorPrefijoConFrecuencia(String prefijo) {
        LinkedList<Contacto> unicos = obtenerUnicos(prefijo);
        for (Contacto c : unicos) c.incrementarFrecuencia();
        return Heap.ordenarConHeap(unicos, new ComparadorFrecuencia(), false);
    }

    public LinkedList<Contacto> obtenerTodosLosContactos() {
        LinkedList<Contacto> unicos = obtenerUnicos("");
        return Heap.ordenarConHeap(unicos, new ComparadorAlfabetico(), true);
    }
}