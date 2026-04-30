package Proyecto;
import java.io.File;

public class ServicioExportacion {
    private static final String ARCHIVO_PREDETERMINADO = "contactos_exportados.csv";

    public static String exportarEnCarpetaPredeterminada(Agenda agenda) {
        CargadorDatos.exportar(ARCHIVO_PREDETERMINADO, agenda.getArbol());
        return ARCHIVO_PREDETERMINADO;
    }

    public static String prepararRuta(String ruta) {
        if (ruta == null || ruta.trim().isEmpty()) {
            return "contactos_exportados.csv";
        }
        ruta = ruta.trim();
        File f = new File(ruta);
        if (f.exists() && f.isDirectory()) {
            return ruta + File.separator + "contactos_exportados.csv";
        }
        if (!ruta.toLowerCase().endsWith(".csv")) {
            ruta += ".csv";
        }
        File archivo = new File(ruta);
        File carpeta = archivo.getParentFile();
        if (carpeta != null && !carpeta.exists()) {
            System.out.println("[!] La carpeta indicada no existe.");
            return null;
        }
        return ruta;
    }

    public static boolean archivoExiste(String ruta) {
        File archivo = new File(ruta);
        return archivo.exists();
    }

    public static void exportar(Agenda agenda, String ruta) {
        CargadorDatos.exportar(ruta, agenda.getArbol());
    }
}