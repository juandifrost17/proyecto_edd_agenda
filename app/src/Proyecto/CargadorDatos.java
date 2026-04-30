package Proyecto;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.*;

public class CargadorDatos {
    public static String cargar(String rutaArchivo, Agenda agenda) {
        return cargarInterno(rutaArchivo, agenda, true);
    }

    public static String cargarSilencioso(String rutaArchivo, Agenda agenda) {
        return cargarInterno(rutaArchivo, agenda, false);
    }

    private static String cargarInterno(String rutaArchivo, Agenda agenda, boolean imprimirEnConsola) {
        if (agenda == null) {
            String msg = "[!] No se puede cargar contactos porque la agenda es nula.";
            if (imprimirEnConsola) System.out.println(msg);
            return msg;
        }
        Path rutaReal;
        try {
            rutaReal = resolverRutaReal(rutaArchivo);
        } catch (IOException e) {
            String msg = "[!] No se pudo leer el archivo: " + e.getMessage();
            if (imprimirEnConsola) System.out.println(msg);
            return msg;
        }
        if (agenda.archivoYaFueCargado(rutaReal)) {
            String msg = "[!] Este archivo ya fue cargado anteriormente. Pruebe con otro.";
            if (imprimirEnConsola) System.out.println(msg);
            return msg;
        }
        int cargados = 0;
        int linea = 0;
        boolean primeraLinea = true;
        try (BufferedReader br = Files.newBufferedReader(rutaReal)) {
            String lineaTexto;
            while ((lineaTexto = br.readLine()) != null) {
                linea++;
                lineaTexto = lineaTexto.trim();
                if (lineaTexto.isEmpty()) continue;
                if (primeraLinea) {
                    primeraLinea = false;
                    if (esCabecera(lineaTexto)) continue;
                }
                String[] partes = lineaTexto.split(",", -1);
                if (advertenciaLinea(partes.length < 6, "faltan campos", linea, imprimirEnConsola)) {
                    continue;
                }
                Contacto contacto = procesarLinea(partes, linea, imprimirEnConsola);
                if (contacto == null) continue;
                agenda.registrarContacto(contacto);
                cargados++;
            }
            agenda.marcarArchivoComoCargado(rutaReal);
        } catch (IOException e) {
            String msg = "[!] No se pudo leer el archivo: " + e.getMessage();
            if (imprimirEnConsola) System.out.println(msg);
            return msg;
        }
        String msg = "[+] Carga completada. Contactos válidos cargados: " + cargados;
        if (imprimirEnConsola) System.out.println(msg);
        return msg;
    }

    private static Contacto procesarLinea(String[] partes, int linea, boolean imprimir) {
        String nombre = partes[0].trim();
        String apellido = partes[1].trim();
        String apodo = partes[2].trim();
        String telefonoMovil = partes[3].trim();
        String telefonoConvencional = partes[4].trim();
        String correo = partes[5].trim();
        if (advertenciaLinea(nombre.isEmpty(), "nombre vacío", linea, imprimir)) return null;
        if (advertenciaLinea(!correo.isEmpty() && !esCorreoValido(correo), "correo inválido: " + correo, linea, imprimir)) return null;
        if (advertenciaLinea(!telefonoMovil.isEmpty() && !esTelefonoValido(telefonoMovil), "teléfono móvil inválido: " + telefonoMovil, linea, imprimir)) return null;
        if (advertenciaLinea(!telefonoConvencional.isEmpty() && !esTelefonoValido(telefonoConvencional), "teléfono convencional inválido: " + telefonoConvencional, linea, imprimir)) return null;
        if (advertenciaLinea(!tieneDatosSuficientes(apellido, apodo, telefonoMovil, telefonoConvencional, correo), "sin datos de contacto suficientes", linea, imprimir)) return null;
        return new Contacto(nombre, apellido, apodo, telefonoMovil, telefonoConvencional, correo);
    }

    private static boolean tieneDatosSuficientes(String apellido, String apodo, String telMovil, String telConv, String correo) {
        return !(apellido.isEmpty() && apodo.isEmpty()
                && telMovil.isEmpty() && telConv.isEmpty() && correo.isEmpty());
    }

    private static boolean advertenciaLinea(boolean condicionInvalida, String mensaje, int linea, boolean imprimir) {
        if (condicionInvalida) {
            if (imprimir) {
                System.out.println("[AVISO] Línea " + linea + " ignorada (" + mensaje + ").");
            }
            return true;
        }
        return false;
    }

    private static Path resolverRutaReal(String rutaArchivo) throws IOException {
        if (rutaArchivo == null || rutaArchivo.trim().isEmpty()) {
            throw new FileNotFoundException("La ruta del archivo está vacía.");
        }
        Path rutaDirecta;
        try {
            rutaDirecta = Paths.get(rutaArchivo);
        } catch (InvalidPathException e) {
            throw new FileNotFoundException("Ruta inválida: " + rutaArchivo);
        }
        if (rutaDirecta.isAbsolute()) {
            if (Files.exists(rutaDirecta) && Files.isRegularFile(rutaDirecta)) {
                return rutaDirecta.toAbsolutePath().normalize();
            }
            throw new FileNotFoundException("No se encontró el archivo: " + rutaArchivo);
        }
        List<Path> rutasPosibles = List.of(
                Paths.get(rutaArchivo),
                Paths.get("ListaContacto", rutaArchivo),
                Paths.get("..", rutaArchivo),
                Paths.get(System.getProperty("user.dir"), rutaArchivo)
        );
        for (Path ruta : rutasPosibles) {
            if (Files.exists(ruta) && Files.isRegularFile(ruta)) {
                return ruta.toAbsolutePath().normalize();
            }
        }
        InputStream recurso = CargadorDatos.class.getClassLoader().getResourceAsStream(rutaArchivo);
        if (recurso != null) {
            Path temporal = Files.createTempFile("contactos_cargados_", ".csv");
            Files.copy(recurso, temporal, StandardCopyOption.REPLACE_EXISTING);
            return temporal.toAbsolutePath().normalize();
        }
        throw new FileNotFoundException("No se encontró el archivo: " + rutaArchivo);
    }

    private static boolean esCabecera(String linea) {
        String texto = linea.trim().toLowerCase();
        return texto.startsWith("# formato:")
                || texto.equals("nombre,apellido,apodo,telefonomovil,telefonoconvencional,correo");
    }

    public static boolean esCorreoValido(String correo) {
        if (correo == null || correo.trim().isEmpty()) return true;
        return correo.trim().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    public static boolean esTelefonoValido(String tel) {
        if (tel == null || tel.trim().isEmpty()) return true;
        String limpio = tel.trim().replaceAll("[\\s-]", "");
        return limpio.matches("\\d{7,15}");
    }

    public static void exportar(String rutaArchivo, APrefijo<Contacto> arbol) {
        LinkedList<Contacto> todos = new LinkedList<>();
        recolectarTodos(arbol, todos);
        Set<Contacto> vistos = new HashSet<>();
        LinkedList<Contacto> unicos = new LinkedList<>();
        for (Contacto c : todos) {
            if (vistos.add(c)) {
                unicos.addLast(c);
            }
        }
        ordenarAlfabeticamente(unicos);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
            bw.write("Nombre,Apellido,Apodo,TelefonoMovil,TelefonoConvencional,Correo");
            bw.newLine();
            for (Contacto c : unicos) {
                bw.write(valorCSV(c.getNombre()) + ","
                        + valorCSV(c.getApellido()) + ","
                        + valorCSV(c.getApodo()) + ","
                        + valorCSV(c.getTelefonoMovil()) + ","
                        + valorCSV(c.getTelefonoConvencional()) + ","
                        + valorCSV(c.getCorreo()));
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("[!] No se pudo escribir el archivo: " + e.getMessage());
        }
    }

    private static void recolectarTodos(APrefijo<Contacto> nodo, LinkedList<Contacto> lista) {
        if (nodo.isFinNombre()) {
            for (Contacto c : nodo.getDatos()) {
                lista.add(c);
            }
        }
        for (APrefijo<Contacto> hijo : nodo.getHijos().values()) {
            recolectarTodos(hijo, lista);
        }
    }

    private static void ordenarAlfabeticamente(LinkedList<Contacto> lista) {
        ComparadorAlfabetico comp = new ComparadorAlfabetico();
        for (int i = 1; i < lista.size(); i++) {
            Contacto actual = lista.get(i);
            int j = i - 1;
            while (j >= 0 && comp.compare(lista.get(j), actual) > 0) {
                lista.set(j + 1, lista.get(j));
                j--;
            }
            lista.set(j + 1, actual);
        }
    }

    private static String valorCSV(String valor) {
        if (valor == null) return "";
        return valor.trim();
    }
}