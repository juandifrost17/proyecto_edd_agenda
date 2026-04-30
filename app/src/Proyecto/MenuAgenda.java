package Proyecto;
import java.util.LinkedList;

public class MenuAgenda {
    private Agenda agenda;
    private GestorAgenda gestor;

    public MenuAgenda(Agenda agenda) {
        this.agenda = agenda;
        this.gestor = new GestorAgenda();
    }

    private void imprimirCancelado() {
        System.out.println("Operación cancelada.");
    }

    private void imprimirLista(LinkedList<Contacto> contactos) {
        System.out.println("═".repeat(30));
        for (int i = 0; i < contactos.size(); i++) {
            System.out.println((i + 1) + " | " + contactos.get(i));
        }
        System.out.println("═".repeat(30));
    }

    public void mostrarMenuPrincipal() {
        int opcion;
        do {
            System.out.println("\n--- AGENDA DE CONTACTOS ---");
            System.out.println("1. Mostrar Contactos");
            System.out.println("2. Buscar Contactos");
            System.out.println("3. Registrar Contactos");
            System.out.println("4. Eliminar Contactos");
            System.out.println("5. Cargar CSV");
            System.out.println("6. Exportar Contactos");
            System.out.println("0. Salir");
            opcion = gestor.leerEntero("Seleccione una opción: ");
            switch (opcion) {
                case 1 -> mostrarTodosLosContactos();
                case 2 -> buscarContactos();
                case 3 -> capturarNuevoContacto();
                case 4 -> menuEliminacion();
                case 5 -> menuCargaCSV();
                case 6 -> menuExportacion();
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    public void mostrarTodosLosContactos() {
        System.out.println("\n--- LISTA DE CONTACTOS ---");
        LinkedList<Contacto> contactos = agenda.obtenerTodosLosContactos();
        if (contactos == null || contactos.isEmpty()) {
            System.out.println("[!] No hay contactos registrados.");
            return;
        }
        imprimirLista(contactos);
    }

    public void buscarContactos() {
        System.out.println("\n--- BÚSQUEDA POR PREFIJO ---");
        String prefijo = gestor.leerCriterioBusqueda("Ingrese prefijo (nombre/apellido/apodo)");
        if (prefijo == null) {
            imprimirCancelado();
            return;
        }
        LinkedList<Contacto> resultados = agenda.buscarPorPrefijoConFrecuencia(prefijo);
        if (resultados.isEmpty()) {
            System.out.println("[!] No se encontraron contactos con ese prefijo.");
            return;
        }
        System.out.println("\nResultados:");
        imprimirLista(resultados);
    }

    public void capturarNuevoContacto() {
        System.out.println("\n--- NUEVO CONTACTO ---");
        String nom = gestor.leerCadena("Nombre");
        if (nom == null) { imprimirCancelado(); return; }
        String ape = gestor.leerCadena("Apellido");
        if (ape == null) { imprimirCancelado(); return; }
        String apo = gestor.leerApodo("Apodo");
        if (apo == null) { imprimirCancelado(); return; }
        String mov = gestor.leerTelefono("Celular");
        if (mov == null) { imprimirCancelado(); return; }
        String con = gestor.leerTelefono("Convencional");
        if (con == null) { imprimirCancelado(); return; }
        String cor = gestor.leerCorreo("Correo");
        if (cor == null) { imprimirCancelado(); return; }
        agenda.registrarContacto(new Contacto(nom, ape, apo, mov, con, cor));
        System.out.println("[+] Contacto guardado e indexado.");
    }

    public void menuEliminacion() {
        System.out.println("\n--- ELIMINAR CONTACTO ---");
        String criterio = gestor.leerCriterioBusqueda("Ingrese nombre/apellido/apodo");
        if (criterio == null) { imprimirCancelado(); return; }
        LinkedList<Contacto> encontrados = agenda.buscarParaEliminar(criterio);
        if (encontrados.isEmpty()) {
            System.out.println("[!] No se encontró ningún contacto.");
            return;
        }
        for (int i = 0; i < encontrados.size(); i++) {
            System.out.println((i + 1) + ". " + encontrados.get(i));
        }
        int seleccion = gestor.leerEntero("Seleccione (0 para cancelar): ");
        if (seleccion > 0 && seleccion <= encontrados.size()) {
            if (agenda.eliminarContacto(encontrados.get(seleccion - 1))) {
                System.out.println("[✓] Contacto eliminado exitosamente.");
            } else {
                System.out.println("[!] Error al eliminar.");
            }
        } else if (seleccion == 0) {
            imprimirCancelado();
        } else {
            System.out.println("Selección inválida.");
        }
    }

    public void menuCargaCSV() {
        System.out.println("\n--- CARGAR CONTACTOS DESDE CSV ---");
        System.out.println("1. Cargar desde archivo predeterminado (contactos.csv)");
        System.out.println("2. Cargar indicando ruta");
        System.out.println("0. Cancelar");
        int op = gestor.leerEntero("Seleccione una opción: ");
        if (op == 1) {
            CargadorDatos.cargar("contactos.csv", agenda);
        } else if (op == 2) {
            String ruta = gestor.leerRuta("Ingrese la ruta completa del archivo CSV");
            if (ruta == null) { imprimirCancelado(); return; }
            CargadorDatos.cargar(ruta, agenda);
        } else {
            imprimirCancelado();
        }
    }

    public void menuExportacion() {
        System.out.println("\n--- EXPORTAR CONTACTOS ---");
        System.out.println("1. Guardar");
        System.out.println("2. Guardar como");
        System.out.println("0. Cancelar");
        int op = gestor.leerEntero("Seleccione una opción: ");
        if (op == 1) {
            System.out.println("[+] Agenda exportada en: " + ServicioExportacion.exportarEnCarpetaPredeterminada(agenda));
        } else if (op == 2) {
            String ruta = gestor.leerRuta("Ingrese la ruta completa del archivo CSV");
            if (ruta == null) { imprimirCancelado(); return; }
            ruta = ServicioExportacion.prepararRuta(ruta);
            if (ruta == null) { imprimirCancelado(); return; }
            if (ServicioExportacion.archivoExiste(ruta)) {
                System.out.println("\nEl archivo ya existe.");
                System.out.println("¿Desea sobrescribirlo?");
                System.out.println("1. Sí");
                System.out.println("2. No");
                int sobrescribir = gestor.leerEntero("Seleccione opción: ");
                if (sobrescribir != 1) { System.out.println("Exportación cancelada."); return; }
            }
            ServicioExportacion.exportar(agenda, ruta);
            System.out.println("[+] Agenda exportada en: " + ruta);
        } else {
            imprimirCancelado();
        }
    }
}