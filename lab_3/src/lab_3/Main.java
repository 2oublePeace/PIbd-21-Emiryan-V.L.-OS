package lab_3;

public class Main {
    public static void main(String[] args) {
        Core core = new Core();
        Manager manager = new Manager(core);
        manager.makeProcesess();
        core.launchProcesess();
    }
}
