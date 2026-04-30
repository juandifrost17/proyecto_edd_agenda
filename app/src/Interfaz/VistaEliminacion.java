package Interfaz;
import Proyecto.Contacto;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.util.LinkedList;

public class VistaEliminacion extends VBox {
    private TextField txtPrefijo;
    private VBox contenedorResultados;
    private Label lblEstado;

    private String estiloTextField(boolean focused) {
        String borde = focused ? "#e67e22" : "#dce1e3";
        return "-fx-background-color: white; -fx-border-color: " + borde + ";" + "-fx-border-radius: 6; -fx-background-radius: 6; -fx-padding: 10 15; -fx-font-size: 14;";
    }

    private String estiloBtnBuscar(boolean hover) {
        String fondo = hover ? "#d35400" : "#e67e22";
        return "-fx-background-color: " + fondo + "; -fx-text-fill: white;" + "-fx-padding: 10 24; -fx-background-radius: 6; -fx-cursor: hand;";
    }

    private String estiloTarjeta(boolean hover) {
        String fondo = hover ? "#fff9f5" : "white";
        String borde = hover ? "#e67e22" : "#e8ebed";
        return "-fx-background-color: " + fondo + "; -fx-background-radius: 6;" + "-fx-border-color: " + borde + "; -fx-border-radius: 6;";
    }

    private String estiloBtnEliminar(boolean hover) {
        String fondo = hover ? "#fdedec" : "transparent";
        String texto = hover ? "#e74c3c" : "#bdc3c7";
        return "-fx-background-color: " + fondo + "; -fx-text-fill: " + texto + ";" + "-fx-padding: 6 12; -fx-background-radius: 6; -fx-cursor: hand;";
    }

    private void actualizarEstado(String color, String mensaje) {
        lblEstado.setStyle("-fx-text-fill: " + color + ";");
        lblEstado.setText(mensaje);
    }

    public VistaEliminacion() {
        this.setSpacing(0);
        this.setPadding(new Insets(0));
        this.setStyle("-fx-background-color: #ecf0f1;");
        VBox header = new VBox(4);
        header.setPadding(new Insets(30, 35, 20, 35));
        header.setStyle("-fx-background-color: white; -fx-border-color: #dce1e3; -fx-border-width: 0 0 1 0;");
        Label titulo = new Label("Eliminar Contacto");
        titulo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        titulo.setStyle("-fx-text-fill: #2c3e50;");
        Label desc = new Label("Busque un contacto por prefijo y seleccione cuál desea eliminar.");
        desc.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 13;");
        header.getChildren().addAll(titulo, desc);
        HBox barraBusqueda = new HBox(10);
        barraBusqueda.setPadding(new Insets(20, 35, 10, 35));
        barraBusqueda.setAlignment(Pos.CENTER_LEFT);
        txtPrefijo = new TextField();
        txtPrefijo.setPromptText("Nombre, apellido o apodo...");
        txtPrefijo.setPrefWidth(320);
        txtPrefijo.setStyle(estiloTextField(false));
        txtPrefijo.focusedProperty().addListener((obs, old, focused) -> txtPrefijo.setStyle(estiloTextField(focused)));
        Button btnBuscar = new Button("Buscar");
        btnBuscar.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        btnBuscar.setStyle(estiloBtnBuscar(false));
        btnBuscar.setOnMouseEntered(e -> btnBuscar.setStyle(estiloBtnBuscar(true)));
        btnBuscar.setOnMouseExited(e -> btnBuscar.setStyle(estiloBtnBuscar(false)));
        lblEstado = new Label();
        lblEstado.setFont(Font.font("Segoe UI", 12));
        barraBusqueda.getChildren().addAll(txtPrefijo, btnBuscar, lblEstado);
        contenedorResultados = new VBox(0);
        ScrollPane scroll = new ScrollPane(contenedorResultados);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: #ecf0f1; -fx-background-color: #ecf0f1; -fx-border-color: transparent;");
        scroll.setPadding(new Insets(10, 35, 20, 35));
        VBox.setVgrow(scroll, Priority.ALWAYS);
        btnBuscar.setOnAction(e -> ejecutarBusqueda());
        txtPrefijo.setOnAction(e -> ejecutarBusqueda());
        this.getChildren().addAll(header, barraBusqueda, scroll);
    }

    private void ejecutarBusqueda() {
        contenedorResultados.getChildren().clear();
        String prefijo = txtPrefijo.getText().trim();
        if (prefijo.isEmpty()) {
            actualizarEstado("#e74c3c", "Ingrese un prefijo.");
            return;
        }
        LinkedList<Contacto> resultados = AppJavaFX.getAgendaCompartida().buscarParaEliminar(prefijo);
        if (resultados.isEmpty()) {
            actualizarEstado("#e67e22", "No se encontraron contactos.");
            return;
        }
        actualizarEstado("#27ae60", resultados.size() + " contacto(s). Haga clic en ✕ para eliminar.");
        int i = 1;
        for (Contacto c : resultados) {
            contenedorResultados.getChildren().add(crearTarjetaEliminable(c, i++));
        }
    }

    private HBox crearTarjetaEliminable(Contacto c, int indice) {
        HBox tarjeta = new HBox(15);
        tarjeta.setPadding(new Insets(14, 18, 14, 18));
        tarjeta.setAlignment(Pos.CENTER_LEFT);
        tarjeta.setStyle(estiloTarjeta(false));
        tarjeta.setEffect(new javafx.scene.effect.DropShadow(4, Color.rgb(0, 0, 0, 0.04)));
        VBox.setMargin(tarjeta, new Insets(0, 0, 8, 0));
        Label numLabel = new Label(String.valueOf(indice));
        numLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        numLabel.setStyle("-fx-text-fill: #e67e22; -fx-background-color: #fdf2e9; -fx-padding: 4 10; -fx-background-radius: 12;");
        VBox info = new VBox(3);
        Label nombre = new Label(c.getNombre() + " " + c.getApellido() + "  (" + c.getApodo() + ")");
        nombre.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 14));
        nombre.setStyle("-fx-text-fill: #2c3e50;");
        Label detalle = new Label("Móvil: " + c.getTelefonoMovil() + "   |   " + c.getCorreo());
        detalle.setFont(Font.font("Segoe UI", 12));
        detalle.setStyle("-fx-text-fill: #7f8c8d;");
        info.getChildren().addAll(nombre, detalle);
        HBox.setHgrow(info, Priority.ALWAYS);
        Button btnEliminar = new Button("✕");
        btnEliminar.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        btnEliminar.setStyle(estiloBtnEliminar(false));
        btnEliminar.setOnMouseEntered(e -> btnEliminar.setStyle(estiloBtnEliminar(true)));
        btnEliminar.setOnMouseExited(e -> btnEliminar.setStyle(estiloBtnEliminar(false)));
        btnEliminar.setOnAction(e -> confirmarEliminacion(c, tarjeta));
        tarjeta.getChildren().addAll(numLabel, info, btnEliminar);
        tarjeta.setOnMouseEntered(e -> tarjeta.setStyle(estiloTarjeta(true)));
        tarjeta.setOnMouseExited(e -> tarjeta.setStyle(estiloTarjeta(false)));
        return tarjeta;
    }

    private void confirmarEliminacion(Contacto c, HBox tarjeta) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmar eliminación");
        alerta.setHeaderText("¿Eliminar a " + c.getNombre() + " " + c.getApellido() + "?");
        alerta.setContentText("Esta acción no se puede deshacer.");
        alerta.showAndWait().ifPresent(respuesta -> {
            if (respuesta == ButtonType.OK) {
                if (AppJavaFX.getAgendaCompartida().eliminarContacto(c)) {
                    contenedorResultados.getChildren().remove(tarjeta);
                    actualizarEstado("#27ae60", "✓ Contacto eliminado.");
                } else {
                    actualizarEstado("#e74c3c", "✕ Error al eliminar.");
                }
            }
        });
    }
}