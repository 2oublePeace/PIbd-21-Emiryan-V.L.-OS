import java.util.Random;

public class Process {
    private int processID;
    private String processState;
    private int processCounter;
    private Random random;

    public Process(int processID, String processState, int processCounter) {
        this.processID = processID;
        this.processState = processState;
        this.processCounter = processCounter;
        this.random = new Random();
    }

    public int processing(int timeToProcess) {
        processCounter = 0;
        processState = "running";
        for (int i = 0; i < timeToProcess; i++) {
            processCounter++;
        }
        processState = "pause";
        return processCounter;
    }


}
