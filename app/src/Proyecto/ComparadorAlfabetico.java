package Proyecto;
import java.util.Comparator;
public class ComparadorAlfabetico implements Comparator<Contacto> {

    @Override
    public int compare(Contacto a, Contacto b) {
        int cmpNombre = a.getNombre().compareToIgnoreCase(b.getNombre());
        if (cmpNombre != 0) return cmpNombre;
        int cmpApellido = a.getApellido().compareToIgnoreCase(b.getApellido());
        if (cmpApellido != 0) return cmpApellido;
        return a.getApodo().compareToIgnoreCase(b.getApodo());
    }
}