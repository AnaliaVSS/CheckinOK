import logic.SesionService;
import ui.CheckInGUI;

public class App {

    public static void main(String[] args) {
        // El servicio ahora carga automáticamente datos guardados o datos demo
        SesionService service = new SesionService();
        CheckInGUI.show(service);
    }
}
