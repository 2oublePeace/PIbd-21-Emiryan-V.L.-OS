import java.util.Random;

public class Driver {
    private int driverID;
    private int deviceID;
    private int operations;
    private int time;
    Random random;

    public Driver(int driverID, int deviceID) {
        this.driverID = driverID;
        this.deviceID = deviceID;
        this.time = 0;
        this.operations = 0;
        this.random = new Random();
    }

    public int makeOperations() {
        time = random.nextInt(50) + 5;
        operations++;
        return time;
    }
}
