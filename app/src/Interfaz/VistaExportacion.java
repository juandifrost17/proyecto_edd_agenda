package Interfaz;
import Proyecto.ServicioExportacion;
import javafx.stage.FileChooser;
import java.io.File;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class VistaExportacion extends VBox {
    private TextField txtRuta;
    private Label lblEstado;

    private String estiloBtnAccion(String color) {
        return "-fx-background-color: " + color + "; -fx-text-fill: white;" + "-fx-padding: 10 20; -fx-background-radius: 6; -fx-cursor: hand;";
    }

    private String normalizarNombre() {
        String nombre = txtRuta.getText().trim();
        if (nombre.isEmpty()) {
            nombre = "contactos_exportados";
        }
        if (!nombre.toLowerCase().endsWith(".csv")) {
            nombre += ".csv";
        }
        return nombre;
    }

    public VistaExportacion() {
        this.setSpacing(0);
        this.setPadding(new Insets(0));
        this.setStyle("-fx-background-color: #ecf0f1;");
        VBox header = new VBox(4);
        header.setPadding(new Insets(30, 35, 20, 35));
        header.setStyle("-fx-background-color: white; -fx-border-color: #dce1e3; -fx-border-width: 0 0 1 0;");
        Label titulo = new Label("Exportar Agenda");
        titulo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        titulo.setStyle("-fx-text-fill: #2c3e50;");
        Label desc = new Label("Exporte todos los contactos a un archivo CSV ordenado alfabéticamente.");
        desc.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 13;");
        header.getChildren().addAll(titulo, desc);
        VBox contenido = new VBox(20);
        contenido.setPadding(new Insets(25, 35, 25, 35));
        VBox panel = new VBox(18);
        panel.setPadding(new Insets(25));
        panel.setStyle("-fx-background-color: white; -fx-background-radius: 8;"
                + "-fx-border-color: #e8ebed; -fx-border-radius: 8;");
        panel.setEffect(new javafx.scene.effect.DropShadow(8, Color.rgb(0, 0, 0, 0.06)));
        Label icono = new Label("📁");
        icono.setFont(Font.font(36));
        Label panelTitulo = new Label("Archivo de destino");
        panelTitulo.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 15));
        panelTitulo.setStyle("-fx-text-fill: #2c3e50;");
        Label panelDesc = new Label("Ingrese el nombre del archivo CSV o elija una ubicación.");
        panelDesc.setStyle("-fx-text-fill: #95a5a6; -fx-font-size: 12;");
        panelDesc.setWrapText(true);
        HBox filaRuta = new HBox(10);
        filaRuta.setAlignment(Pos.CENTER_LEFT);
        txtRuta = new TextField("contactos_exportados");
        txtRuta.setPrefWidth(300);
        txtRuta.setStyle("-fx-background-color: #f8f9fa; -fx-border-color: #dce1e3;"
                + "-fx-border-radius: 6; -fx-background-radius: 6; -fx-padding: 10 15; -fx-font-size: 13;");
        filaRuta.getChildren().add(txtRuta);
        Button btnElegir = new Button("Guardar como");
        Button btnExportar = new Button("Guardar");
        Button btnPredeterminado = new Button("Guardar predeterminadamente");
        btnElegir.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        btnExportar.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        btnPredeterminado.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        btnElegir.setStyle(estiloBtnAccion("#3498db"));
        btnExportar.setStyle(estiloBtnAccion("#8e44ad"));
        btnPredeterminado.setStyle(estiloBtnAccion("#2ecc71"));
        btnElegir.setMaxWidth(Double.MAX_VALUE);
        btnExportar.setMaxWidth(Double.MAX_VALUE);
        btnPredeterminado.setMaxWidth(Double.MAX_VALUE);
        VBox filaBotones = new VBox(10);
        filaBotones.setAlignment(Pos.CENTER_LEFT);
        filaBotones.getChildren().addAll(btnElegir, btnExportar, btnPredeterminado);
        lblEstado = new Label();
        lblEstado.setFont(Font.font("Segoe UI", 13));
        lblEstado.setWrapText(true);
        panel.getChildren().addAll(icono, panelTitulo, panelDesc, filaRuta, filaBotones, lblEstado);
        contenido.getChildren().add(panel);
        this.getChildren().addAll(header, contenido);
        btnExportar.setOnAction(e -> exportarDesdeTexto());
        btnElegir.setOnAction(e -> elegirUbicacion());
        btnPredeterminado.setOnAction(e -> exportarPredeterminado());
    }

    private void exportarDesdeTexto() {
        String ruta = ServicioExportacion.prepararRuta(normalizarNombre());
        ejecutarExportacion(ruta);
    }

    private void exportarPredeterminado() {
        ok("Agenda exportada en carpeta predeterminada: " + ServicioExportacion.exportarEnCarpetaPredeterminada(AppJavaFX.getAgendaCompartida()));
    }

    private void elegirUbicacion() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Guardar agenda");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivo CSV", "*.csv"));
        fc.setInitialFileName(normalizarNombre());
        File archivo = fc.showSaveDialog(getScene().getWindow());
        if (archivo == null) {
            return;
        }
        String ruta = ServicioExportacion.prepararRuta(archivo.getAbsolutePath());
        ejecutarExportacion(ruta);
    }

    private void ejecutarExportacion(String ruta) {
        if (ruta == null) {
            error("Ruta inválida.");
            return;
        }
        if (ServicioExportacion.archivoExiste(ruta) && !confirmarSobrescritura()) {
            error("Exportación cancelada.");
            return;
        }
        ServicioExportacion.exportar(AppJavaFX.getAgendaCompartida(), ruta);
        ok("Agenda exportada en: " + ruta);
    }

    private boolean confirmarSobrescritura() {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Archivo existente");
        alerta.setHeaderText("El archivo ya existe");
        alerta.setContentText("¿Desea sobrescribirlo?");
        ButtonType si = new ButtonType("Sí");
        ButtonType no = new ButtonType("No");
        alerta.getButtonTypes().setAll(si, no);
        return alerta.showAndWait().orElse(no) == si;
    }

    private void ok(String msg) {
        lblEstado.setTextFill(Color.web("#27ae60"));
        lblEstado.setText("✓ " + msg);
    }

    private void error(String msg) {
        lblEstado.setTextFill(Color.web("#e74c3c"));
        lblEstado.setText("✕ " + msg);
    }
}