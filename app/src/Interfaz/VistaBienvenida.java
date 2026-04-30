package Interfaz;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class VistaBienvenida extends BorderPane {
    private Button btnActivo = null;
    private Button btnMostrar;
    private Button btnBuscar;
    private Button btnRegistrar;
    private Button btnEliminar;
    private Button btnCargar;
    private Button btnExportar;

    private interface CreadorVista {
        javafx.scene.Node crear();
    }

    private String estiloInactivo() {
        return "-fx-background-color: transparent;"
                + "-fx-text-fill: #bdc3c7;"
                + "-fx-padding: 10 15;"
                + "-fx-background-radius: 6;"
                + "-fx-cursor: hand;";
    }

    private String estiloHover(String color) {
        return "-fx-background-color: " + color + "22;"
                + "-fx-text-fill: " + color + ";"
                + "-fx-padding: 10 15;"
                + "-fx-background-radius: 6;"
                + "-fx-cursor: hand;";
    }

    private String estiloActivo(String color) {
        return "-fx-background-color: " + color + "33;"
                + "-fx-text-fill: white;"
                + "-fx-padding: 10 15;"
                + "-fx-background-radius: 6;"
                + "-fx-cursor: hand;"
                + "-fx-border-color: " + color + ";"
                + "-fx-border-width: 0 0 0 3;"
                + "-fx-border-radius: 6;";
    }

    public VistaBienvenida() {
        VBox sidebar = new VBox(8);
        sidebar.setPadding(new Insets(25, 15, 25, 15));
        sidebar.setStyle("-fx-background-color: #1a252f;");
        sidebar.setPrefWidth(220);
        Label titulo = new Label("AGENDA");
        titulo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        titulo.setStyle("-fx-text-fill: #ecf0f1; -fx-padding: 0 0 15 5;");
        Label subtitulo = new Label("Gestión de Contactos");
        subtitulo.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 11; -fx-padding: 0 0 20 5;");
        btnMostrar = crearBotonSidebar("📋  Mostrar", "#16a085");
        btnBuscar = crearBotonSidebar("🔍  Buscar", "#2980b9");
        btnRegistrar = crearBotonSidebar("＋  Registrar", "#27ae60");
        btnEliminar = crearBotonSidebar("✕  Eliminar", "#e67e22");
        btnCargar = crearBotonSidebar("⬆  Cargar CSV", "#f39c12");
        btnExportar = crearBotonSidebar("📁  Exportar CSV", "#8e44ad");
        VBox espaciador = new VBox();
        espaciador.setStyle("-fx-pref-height: 999999;");
        VBox.setVgrow(espaciador, Priority.ALWAYS);
        Button btnSalir = crearBotonSidebar("Salir", "#c0392b");
        btnSalir.setStyle(
                btnSalir.getStyle()
                        + "-fx-background-color: transparent;"
                        + "-fx-text-fill: #e74c3c;"
                        + "-fx-border-color: #e74c3c;"
                        + "-fx-border-radius: 6;"
        );
        configurarNavegacion(btnMostrar, "#16a085", () -> new VistaMostrar());
        configurarNavegacion(btnBuscar, "#2980b9", () -> new VistaBusqueda());
        configurarNavegacion(btnRegistrar, "#27ae60", () -> new VistaRegistro());
        configurarNavegacion(btnEliminar, "#e67e22", () -> new VistaEliminacion());
        configurarNavegacion(btnCargar, "#f39c12", () -> new VistaCarga(this));
        configurarNavegacion(btnExportar, "#8e44ad", () -> new VistaExportacion());
        btnSalir.setOnAction(e -> System.exit(0));
        sidebar.getChildren().addAll(
                titulo, subtitulo,
                btnMostrar, btnBuscar, btnRegistrar,
                btnEliminar, btnCargar, btnExportar,
                espaciador, btnSalir
        );
        VBox centro = new VBox(12);
        centro.setAlignment(Pos.CENTER);
        centro.setStyle("-fx-background-color: #ecf0f1;");
        Label bienvenida = new Label("Bienvenido");
        bienvenida.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        bienvenida.setStyle("-fx-text-fill: #2c3e50;");
        Label instruccion = new Label("Seleccione una opción del menú lateral para comenzar.");
        instruccion.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 14;");
        centro.getChildren().addAll(bienvenida, instruccion);
        this.setLeft(sidebar);
        this.setCenter(centro);
    }

    public void irAMostrarDesdeCarga() {
        marcarActivo(btnMostrar, "#16a085");
        this.setCenter(new VistaMostrar());
    }

    private Button crearBotonSidebar(String texto, String color) {
        Button btn = new Button(texto);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 13));
        btn.setStyle(estiloInactivo());
        btn.setOnMouseEntered(e -> {
            if (btn != btnActivo) {
                btn.setStyle(estiloHover(color));
            }
        });
        btn.setOnMouseExited(e -> {
            if (btn != btnActivo) {
                btn.setStyle(estiloInactivo());
            }
        });
        return btn;
    }

    private void configurarNavegacion(Button btn, String color, CreadorVista creadorVista) {
        btn.setOnAction(e -> {
            marcarActivo(btn, color);
            this.setCenter(creadorVista.crear());
        });
    }

    private void marcarActivo(Button btn, String color) {
        if (btnActivo != null) {
            btnActivo.setStyle(estiloInactivo());
        }
        btnActivo = btn;
        btn.setStyle(estiloActivo(color));
    }
}