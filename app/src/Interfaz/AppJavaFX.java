package Interfaz;
import Proyecto.Agenda;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppJavaFX extends Application {
    public static Agenda agendaCompartida;
    public static Agenda getAgendaCompartida() {
        if (agendaCompartida == null) {
            agendaCompartida = new Agenda();
        }
        return agendaCompartida;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        getAgendaCompartida();
        VistaBienvenida root = new VistaBienvenida();
        Scene scene = new Scene(root, 900, 600);
        primaryStage.setTitle("Sistema de Gestión de Contactos");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}