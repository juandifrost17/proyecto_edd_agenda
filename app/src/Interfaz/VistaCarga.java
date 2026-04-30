package Interfaz;
import Proyecto.CargadorDatos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import java.io.File;

public class VistaCarga extends VBox {
    private final VistaBienvenida contenedorPrincipal;
    private final VBox panelMensaje;

    private String estiloTarjetaResultado(boolean exito) {
        String fondo  = exito ? "#f8fcff"  : "#fffaf5";
        String borde  = exito ? "#aed6f1"  : "#f5cba7";
        return "-fx-background-color: " + fondo + "; -fx-background-radius: 8;"
                + "-fx-border-color: " + borde + "; -fx-border-radius: 8;";
    }

    private String estiloTituloResultado(boolean exito) {
        String color = exito ? "#2980b9" : "#d35400";
        return "-fx-text-fill: " + color + ";";
    }

    public VistaCarga(VistaBienvenida contenedorPrincipal) {
        this.contenedorPrincipal = contenedorPrincipal;
        this.setSpacing(0);
        this.setPadding(new Insets(0));
        this.setStyle("-fx-background-color: #ecf0f1;");
        VBox header = new VBox(4);
        header.setPadding(new Insets(30, 35, 20, 35));
        header.setStyle("-fx-background-color: white; -fx-border-color: #dce1e3; -fx-border-width: 0 0 1 0;");
        Label titulo = new Label("Cargar CSV");
        titulo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        titulo.setStyle("-fx-text-fill: #2c3e50;");
        Label desc = new Label("Seleccione un archivo CSV para cargar contactos a la agenda.");
        desc.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 13;");
        header.getChildren().addAll(titulo, desc);
        VBox contenido = new VBox(20);
        contenido.setPadding(new Insets(25, 35, 25, 35));
        VBox panel = new VBox(18);
        panel.setPadding(new Insets(25));
        panel.setStyle("-fx-background-color: white; -fx-background-radius: 8;" + "-fx-border-color: #e8ebed; -fx-border-radius: 8;");
        panel.setEffect(new DropShadow(8, Color.rgb(0, 0, 0, 0.06)));
        Label icono = new Label("⬆");
        icono.setFont(Font.font(36));
        Label panelTitulo = new Label("Archivo CSV");
        panelTitulo.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 15));
        panelTitulo.setStyle("-fx-text-fill: #2c3e50;");
        Label panelDesc = new Label("Elija un archivo CSV válido para cargar los contactos.");
        panelDesc.setStyle("-fx-text-fill: #95a5a6; -fx-font-size: 12;");
        panelDesc.setWrapText(true);
        Button btnSeleccionar = new Button("Seleccionar archivo CSV");
        btnSeleccionar.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        btnSeleccionar.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white;" + "-fx-padding: 10 20; -fx-background-radius: 6; -fx-cursor: hand;");
        btnSeleccionar.setOnAction(e -> seleccionarYCargarArchivo());
        panelMensaje = new VBox();
        panelMensaje.setSpacing(0);
        panel.getChildren().addAll(icono, panelTitulo, panelDesc, btnSeleccionar, panelMensaje);
        contenido.getChildren().add(panel);
        this.getChildren().addAll(header, contenido);
    }

    private void seleccionarYCargarArchivo() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Seleccionar archivo CSV");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos CSV", "*.csv"));
        File archivo = fc.showOpenDialog(getScene().getWindow());
        if (archivo == null) return;
        String resultado = CargadorDatos.cargarSilencioso(
                archivo.getAbsolutePath(),
                AppJavaFX.getAgendaCompartida()
        );
        mostrarResultado(resultado, archivo.getAbsolutePath(), resultado.startsWith("[+]"));
    }

    private void mostrarResultado(String mensaje, String rutaArchivo, boolean exito) {
        panelMensaje.getChildren().clear();
        VBox tarjeta = new VBox(12);
        tarjeta.setAlignment(Pos.CENTER_LEFT);
        tarjeta.setPadding(new Insets(18));
        tarjeta.setStyle(estiloTarjetaResultado(exito));
        Label titulo = new Label(exito ? "Carga completada" : "Carga no realizada");
        titulo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 15));
        titulo.setStyle(estiloTituloResultado(exito));
        Label detalle = new Label(mensaje);
        detalle.setWrapText(true);
        detalle.setStyle("-fx-text-fill: #5d6d7e; -fx-font-size: 13;");
        Label ruta = new Label("Archivo: " + rutaArchivo);
        ruta.setWrapText(true);
        ruta.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 12;");
        tarjeta.getChildren().addAll(titulo, detalle, ruta);
        if (exito) {
            Button btnMostrar = new Button("Mostrar contactos");
            btnMostrar.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
            btnMostrar.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;"
                    + "-fx-padding: 10 22; -fx-background-radius: 6; -fx-cursor: hand;");
            btnMostrar.setOnAction(e -> contenedorPrincipal.irAMostrarDesdeCarga());
            tarjeta.getChildren().add(btnMostrar);
        }
        panelMensaje.getChildren().add(tarjeta);
    }
}