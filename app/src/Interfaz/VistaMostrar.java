package Interfaz;
import Proyecto.Contacto;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.util.LinkedList;

public class VistaMostrar extends VBox {
    private VBox contenedorResultados;
    private Label lblEstado;

    private String estiloTarjeta(boolean hover) {
        String fondo = hover ? "#fafcff" : "white";
        String borde = hover ? "#2980b9" : "#e8ebed";
        return "-fx-background-color: " + fondo + "; -fx-background-radius: 6;"
                + "-fx-border-color: " + borde + "; -fx-border-radius: 6;";
    }

    private void actualizarEstado(String color, String mensaje) {
        lblEstado.setStyle("-fx-text-fill: " + color + ";");
        lblEstado.setText(mensaje);
    }

    public VistaMostrar() {
        this.setSpacing(0);
        this.setPadding(new Insets(0));
        this.setStyle("-fx-background-color: #ecf0f1;");
        VBox header = new VBox(4);
        header.setPadding(new Insets(30, 35, 20, 35));
        header.setStyle("-fx-background-color: white; -fx-border-color: #dce1e3; -fx-border-width: 0 0 1 0;");
        Label titulo = new Label("Mostrar Contactos");
        titulo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        titulo.setStyle("-fx-text-fill: #2c3e50;");
        Label desc = new Label("Lista completa de contactos registrados en la agenda.");
        desc.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 13;");
        lblEstado = new Label();
        lblEstado.setFont(Font.font("Segoe UI", 12));
        lblEstado.setStyle("-fx-text-fill: #27ae60;");
        header.getChildren().addAll(titulo, desc, lblEstado);
        contenedorResultados = new VBox(0);
        ScrollPane scroll = new ScrollPane(contenedorResultados);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: #ecf0f1; -fx-background-color: #ecf0f1; -fx-border-color: transparent;");
        scroll.setPadding(new Insets(10, 35, 20, 35));
        VBox.setVgrow(scroll, Priority.ALWAYS);
        this.getChildren().addAll(header, scroll);
        cargarContactos();
    }

    private void cargarContactos() {
        contenedorResultados.getChildren().clear();
        LinkedList<Contacto> contactos = AppJavaFX.getAgendaCompartida().obtenerTodosLosContactos();
        if (contactos.isEmpty()) {
            actualizarEstado("#e67e22", "No hay contactos registrados.");
            return;
        }
        actualizarEstado("#27ae60", contactos.size() + " contacto(s) registrado(s).");
        int i = 1;
        for (Contacto c : contactos) {
            contenedorResultados.getChildren().add(crearTarjeta(c, i++));
        }
    }

    private HBox crearTarjeta(Contacto c, int indice) {
        HBox tarjeta = new HBox(15);
        tarjeta.setPadding(new Insets(14, 18, 14, 18));
        tarjeta.setAlignment(Pos.CENTER_LEFT);
        tarjeta.setStyle(estiloTarjeta(false));
        tarjeta.setEffect(new DropShadow(4, Color.rgb(0, 0, 0, 0.04)));
        VBox.setMargin(tarjeta, new Insets(0, 35, 8, 35));
        Label numLabel = new Label(String.valueOf(indice));
        numLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        numLabel.setStyle("-fx-text-fill: #2980b9; -fx-background-color: #ebf5fb; -fx-padding: 4 10; -fx-background-radius: 12;");
        VBox info = new VBox(3);
        Label nombre = new Label(c.getNombre() + " " + c.getApellido() + "  (" + c.getApodo() + ")");
        nombre.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 14));
        nombre.setStyle("-fx-text-fill: #2c3e50;");
        Label detalle = new Label("Móvil: " + c.getTelefonoMovil() + "   |   Conv: " + c.getTelefonoConvencional() + "   |   " + c.getCorreo());
        detalle.setFont(Font.font("Segoe UI", 12));
        detalle.setStyle("-fx-text-fill: #7f8c8d;");
        info.getChildren().addAll(nombre, detalle);
        HBox.setHgrow(info, Priority.ALWAYS);
        tarjeta.getChildren().addAll(numLabel, info);
        tarjeta.setOnMouseEntered(e -> tarjeta.setStyle(estiloTarjeta(true)));
        tarjeta.setOnMouseExited(e -> tarjeta.setStyle(estiloTarjeta(false)));
        return tarjeta;
    }
}