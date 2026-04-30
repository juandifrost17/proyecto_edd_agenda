package Proyecto;
import Interfaz.AppJavaFX;
import javafx.application.Application;

public class PantallaAgenda {
    public static void main(String[] args) {
        Agenda agenda = new Agenda();
        GestorAgenda gestor = new GestorAgenda();
        System.out.println("--- INICIO DE SISTEMA ---");
        System.out.println("1. Modo Consola");
        System.out.println("2. Interfaz Gráfica");
        int seleccion = gestor.leerEntero("Seleccione modo de ejecución: ");
        if (seleccion == 1) {
            MenuAgenda menu = new MenuAgenda(agenda);
            menu.mostrarMenuPrincipal();
        } else if (seleccion == 2) {
            AppJavaFX.agendaCompartida = agenda;
            Application.launch(AppJavaFX.class, args);
        } else {
            System.out.println("Opción no válida. Cerrando...");
        }
    }
}