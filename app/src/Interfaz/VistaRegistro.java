package Interfaz;
import Proyecto.CargadorDatos;
import Proyecto.Contacto;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class VistaRegistro extends VBox {
    private TextField txtNombre;
    private TextField txtApellido;
    private TextField txtApodo;
    private TextField txtMovil;
    private TextField txtConvencional;
    private TextField txtCorreo;
    private Label lblMensaje;

    private String estiloTextField(boolean focused) {
        String fondo = focused ? "white" : "#f8f9fa";
        String borde = focused ? "#2980b9" : "#dce1e3";
        return "-fx-background-color: " + fondo + "; -fx-border-color: " + borde + ";" + "-fx-border-radius: 4; -fx-background-radius: 4; -fx-padding: 8 12;";
    }

    private String estiloBtnGuardar(boolean hover) {
        String fondo = hover ? "#219a52" : "#27ae60";
        return "-fx-background-color: " + fondo + "; -fx-text-fill: white;" + "-fx-padding: 10 28; -fx-background-radius: 6; -fx-cursor: hand;";
    }

    public VistaRegistro() {
        this.setSpacing(0);
        this.setPadding(new Insets(0));
        this.setStyle("-fx-background-color: #ecf0f1;");
        VBox header = new VBox(4);
        header.setPadding(new Insets(30, 35, 20, 35));
        header.setStyle("-fx-background-color: white; -fx-border-color: #dce1e3; -fx-border-width: 0 0 1 0;");
        Label titulo = new Label("Registrar Contacto");
        titulo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        titulo.setStyle("-fx-text-fill: #2c3e50;");
        Label desc = new Label("Complete los campos para agregar un nuevo contacto a la agenda.");
        desc.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 13;");
        header.getChildren().addAll(titulo, desc);
        VBox formContainer = new VBox(20);
        formContainer.setPadding(new Insets(25, 35, 25, 35));
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(14);
        grid.setStyle("-fx-background-color: white; -fx-padding: 25; -fx-background-radius: 8;");
        grid.setEffect(new javafx.scene.effect.DropShadow(8, Color.rgb(0, 0, 0, 0.06)));
        txtNombre = crearCampo("Ej: Carlos");
        txtApellido = crearCampo("Ej: Pérez");
        txtApodo = crearCampo("Ej: Carlitos");
        txtMovil = crearCampo("Ej: 0991234567");
        txtConvencional = crearCampo("Ej: 022345678");
        txtCorreo = crearCampo("Ej: correo@mail.com");
        agregarFila(grid, 0, "Nombre *", txtNombre);
        agregarFila(grid, 1, "Apellido *", txtApellido);
        agregarFila(grid, 2, "Apodo *", txtApodo);
        agregarFila(grid, 3, "Celular *", txtMovil);
        agregarFila(grid, 4, "Convencional *", txtConvencional);
        agregarFila(grid, 5, "Correo *", txtCorreo);
        HBox botones = new HBox(12);
        botones.setAlignment(Pos.CENTER_LEFT);
        botones.setPadding(new Insets(10, 0, 0, 0));
        Button btnGuardar = new Button("Guardar Contacto");
        btnGuardar.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        btnGuardar.setStyle(estiloBtnGuardar(false));
        btnGuardar.setOnMouseEntered(e -> btnGuardar.setStyle(estiloBtnGuardar(true)));
        btnGuardar.setOnMouseExited(e -> btnGuardar.setStyle(estiloBtnGuardar(false)));
        btnGuardar.setOnAction(e -> procesarRegistro());
        Button btnLimpiar = new Button("Limpiar");
        btnLimpiar.setFont(Font.font("Segoe UI", 13));
        btnLimpiar.setStyle("-fx-background-color: transparent; -fx-text-fill: #7f8c8d;"
                        + "-fx-padding: 10 20; -fx-background-radius: 6; -fx-cursor: hand;"
                        + "-fx-border-color: #bdc3c7; -fx-border-radius: 6;"
        );
        btnLimpiar.setOnAction(e -> limpiarCampos());
        lblMensaje = new Label();
        lblMensaje.setFont(Font.font("Segoe UI", 13));
        lblMensaje.setWrapText(true);
        botones.getChildren().addAll(btnGuardar, btnLimpiar, lblMensaje);
        formContainer.getChildren().addAll(grid, botones);
        this.getChildren().addAll(header, formContainer);
    }

    private TextField crearCampo(String placeholder) {
        TextField tf = new TextField();
        tf.setPromptText(placeholder);
        tf.setPrefWidth(260);
        tf.setStyle(estiloTextField(false));
        tf.focusedProperty().addListener((obs, old, focused) -> tf.setStyle(estiloTextField(focused)));
        return tf;
    }

    private void agregarFila(GridPane grid, int fila, String etiqueta, TextField campo) {
        Label lbl = new Label(etiqueta);
        lbl.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 12));
        lbl.setStyle("-fx-text-fill: #34495e;");
        grid.add(lbl, 0, fila);
        grid.add(campo, 1, fila);
    }

    private void procesarRegistro() {
        String nom = txtNombre.getText().trim();
        String ape = txtApellido.getText().trim();
        String apo = txtApodo.getText().trim();
        String mov = txtMovil.getText().trim();
        String con = txtConvencional.getText().trim();
        String cor = txtCorreo.getText().trim();
        if (!esTextoValido(nom)) {
            mostrarError("Nombre inválido. Use solo letras.");
            return;
        }
        if (!esTextoValido(ape)) {
            mostrarError("Apellido inválido. Use solo letras.");
            return;
        }
        if (apo.isEmpty()) {
            mostrarError("El apodo no puede estar vacío.");
            return;
        }
        if (!CargadorDatos.esTelefonoValido(mov)) {
            mostrarError("Celular inválido. Debe tener entre 7 y 15 dígitos.");
            return;
        }

        if (!CargadorDatos.esTelefonoValido(con)) {
            mostrarError("Convencional inválido. Debe tener entre 7 y 15 dígitos.");
            return;
        }
        if (!CargadorDatos.esCorreoValido(cor)) {
            mostrarError("Correo inválido.");
            return;
        }
        AppJavaFX.getAgendaCompartida().registrarContacto(new Contacto(nom, ape, apo, mov, con, cor));
        lblMensaje.setTextFill(Color.web("#27ae60"));
        lblMensaje.setText("✓ Contacto guardado con éxito.");
        limpiarCampos();
    }

    private boolean esTextoValido(String texto) {
        if (texto.isEmpty()) {
            return false;
        }
        for (char c : texto.toCharArray()) {
            if (!Character.isLetter(c) && c != ' ') {
                return false;
            }
        }
        return true;
    }

    private void mostrarError(String msj) {
        lblMensaje.setTextFill(Color.web("#e74c3c"));
        lblMensaje.setText("✕ " + msj);
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtApellido.clear();
        txtApodo.clear();
        txtMovil.clear();
        txtConvencional.clear();
        txtCorreo.clear();
    }
}