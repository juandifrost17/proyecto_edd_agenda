package Proyecto;

import java.util.Comparator;

public class ComparadorFrecuencia implements Comparator<Contacto> {

    @Override
    public int compare(Contacto a, Contacto b) {
        //1. Prioridad: Mayor frecuencia
        if (a.getFrecuencia() != b.getFrecuencia()) {
            return Integer.compare(a.getFrecuencia(), b.getFrecuencia());}
        //2. Desempate: Orden alfabético A-Z
        //Nombre
        int cmpNombre = b.getNombre().compareToIgnoreCase(a.getNombre());
        if (cmpNombre != 0) return cmpNombre;
        //Apellido
        int cmpApellido = b.getApellido().compareToIgnoreCase(a.getApellido());
        if (cmpApellido != 0) return cmpApellido;
        //Apodo
        int cmpApodo = b.getApodo().compareToIgnoreCase(a.getApodo());
        if (cmpApodo != 0) return cmpApodo;
        //3. Desempate final: Numero de telefono
        return b.getTelefonoMovil().compareTo(a.getTelefonoMovil());
    }
}