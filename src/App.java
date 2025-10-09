import logic.SesionService;
import ui.CheckInGUI;

public class App {

    public static void main(String[] args) {
        SesionService service = new SesionService();
        service.cargarDatosDemo();
        CheckInGUI.show(service);
    }
}

