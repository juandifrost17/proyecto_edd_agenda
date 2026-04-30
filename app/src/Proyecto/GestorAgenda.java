package Proyecto;

import java.util.Scanner;

public class GestorAgenda {
    private Scanner sc = new Scanner(System.in);

    public boolean esTelefonoValido(String tel) {
        if (tel == null || tel.trim().isEmpty()) return false;
        return tel.trim().replaceAll("[\\s-]", "").matches("\\d{7,15}");
    }

    public boolean esCorreoValido(String correo) {
        if (correo == null || correo.trim().isEmpty()) return false;
        return correo.trim().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    private boolean esSoloLetras(String input) {
        for (char c : input.toCharArray()) {
            if (!Character.isLetter(c) && c != ' ') return false;
        }
        return true;
    }

    private boolean esSoloLetrasYNumeros(String input) {
        for (char c : input.toCharArray()) {
            if (!Character.isLetterOrDigit(c) && c != ' ') return false;
        }
        return true;
    }

    private String obtenerEntradaBase(String mensaje, boolean permiteVacio) {
        while (true) {
            System.out.print(mensaje + " (o escriba 'cancelar'): ");
            String input = sc.nextLine().trim();
            if (input.equalsIgnoreCase("cancelar")) return null;
            if (!permiteVacio && input.isEmpty()) {
                System.out.println("[!] Entrada inválida. El campo no puede estar vacío.");
                continue;
            }
            return input;
        }
    }

    public int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("[!] Entrada inválida. Ingrese un número entero.");
            }
        }
    }

    public String leerRuta(String mensaje) {
        return obtenerEntradaBase(mensaje, false);
    }

    public String leerCadena(String mensaje) {
        while (true) {
            String input = obtenerEntradaBase(mensaje, false);
            if (input == null) return null;
            if (esSoloLetras(input)) return input;
            System.out.println("[!] Entrada inválida. Use solo letras.");
        }
    }

    public String leerApodo(String mensaje) {
        while (true) {
            String input = obtenerEntradaBase(mensaje, false);
            if (input == null) return null;
            if (esSoloLetrasYNumeros(input)) return input;
            System.out.println("[!] Entrada inválida. Use solo letras y números.");
        }
    }

    public String leerCriterioBusqueda(String mensaje) {
        while (true) {
            String input = obtenerEntradaBase(mensaje, false);
            if (input == null) return null;
            if (esSoloLetrasYNumeros(input)) return input;
            System.out.println("[!] Entrada inválida. Use solo letras, números y espacios.");
        }
    }

    public String leerTelefono(String mensaje) {
        while (true) {
            System.out.print(mensaje + " (o escriba 'cancelar', Enter para vacío): ");
            String tel = sc.nextLine().trim();
            if (tel.equalsIgnoreCase("cancelar")) return null;
            if (tel.isEmpty()) return "";
            if (esTelefonoValido(tel)) return tel;
            System.out.println("[!] Entrada inválida. Ingrese entre 7 y 15 dígitos numéricos.");
        }
    }

    public String leerCorreo(String mensaje) {
        while (true) {
            System.out.print(mensaje + " (o escriba 'cancelar', Enter para vacío): ");
            String correo = sc.nextLine().trim();
            if (correo.equalsIgnoreCase("cancelar")) return null;
            if (correo.isEmpty()) return "";
            if (esCorreoValido(correo)) return correo;
            System.out.println("[!] Entrada inválida. Formato de correo incorrecto.");
        }
    }
}