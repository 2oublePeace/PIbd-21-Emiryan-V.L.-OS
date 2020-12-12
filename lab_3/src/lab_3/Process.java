package lab_3;

import java.util.ArrayList;
import java.util.Random;

public class Process {
    private int processID;
    private String processState;
    private int memoryCapacity;
    AdressSpace adressSpace;

    public Process(int processID, String processState, int memoryCapacity, AdressSpace adressSpace) {
        this.processID = processID;
        this.processState = processState;
        this.memoryCapacity = memoryCapacity;
        this.adressSpace = adressSpace;
        makePageList();
    }

    public Process(int processID, String processState) {
        this.processID = processID;
        this.processState = processState;
    }

    public void makePageList() {
        Random random = new Random();
        while(adressSpace.getPageList().size() * 4 < adressSpace.getRange()) {
            adressSpace.getPageList().add(new VirtualPage(random.nextInt(64) + 32, processID));
        }
    }

    public String getProcessState() {
        return processState;
    }

    public void setProcessState(String processState) {
        this.processState = processState;
    }

    public ArrayList<VirtualPage> getPageList() {
        return adressSpace.getPageList();
    }

    public void setAdressSpace(AdressSpace adressSpace) {
        this.adressSpace = adressSpace;
    }
}
