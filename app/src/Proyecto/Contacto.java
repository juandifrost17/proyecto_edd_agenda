package Proyecto;
import java.util.Objects;

public class Contacto {
    private String nombre;
    private String apellido;
    private String apodo;
    private String telefonoMovil;
    private String telefonoConvencional;
    private String correo;
    private int frecuencia;

    public Contacto(String nombre, String apellido, String apodo, String telefonoMovil, String telefonoConvencional, String correo) {
        this.nombre = nombre.trim();
        this.apellido = apellido.trim();
        this.apodo = apodo.trim();
        this.telefonoMovil = telefonoMovil.trim();
        this.telefonoConvencional= telefonoConvencional.trim();
        this.correo= correo.trim();
        this.frecuencia =0;
    }

    public int getFrecuencia() {return frecuencia;}
    public String getNombre(){ return nombre; }
    public String getApellido(){ return apellido; }
    public String getApodo(){ return apodo; }
    public String getTelefonoMovil(){ return telefonoMovil; }
    public String getTelefonoConvencional(){ return telefonoConvencional; }
    public String getCorreo(){ return correo; }

    public void incrementarFrecuencia() { this.frecuencia++; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Contacto other)) return false;
        return nombre.equals(other.nombre) && apellido.equals(other.apellido) && apodo.equals(other.apodo) && telefonoMovil.equals(other.telefonoMovil);
    }

    @Override
    public int hashCode() {return Objects.hash(nombre, apellido, apodo,telefonoMovil);}

    @Override
    public String toString() {
        return "Nombre: " + nombre + " " + apellido +
                " (" + apodo + ") | " +
                "Móvil: " + telefonoMovil + " | " +
                "Conv: " + telefonoConvencional + " | " +
                "Correo: " + correo;
    }
}