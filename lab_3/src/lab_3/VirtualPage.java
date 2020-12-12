package lab_3;

import java.util.ArrayList;
import java.util.Random;

public class VirtualPage {
    int pageID;
    int size = 4;
    boolean isInRAM;
    boolean isModified;
    boolean isCurrent;
    ArrayList<String> adresess;
    int processID;
    Core core;

    public VirtualPage(int pageID, int processID) {
        this.pageID = pageID;
        this.isInRAM = false;
        this.isModified = false;
        this.isCurrent = false;
        this.processID = processID;
        adresess = new ArrayList<>();
        core = new Core();
        makeAdresess();
    }

    void makeAdresess() {
        Random random = new Random();
        for (int i = 0; i < core.RAM_CAPACITY / 4; i++) {
            String hex = Integer.toHexString(random.nextInt(60000) + 32000);
            adresess.add(hex);
        }
    }

    public void setInRAM(boolean inRAM) {
        isInRAM = inRAM;
    }

    public void setModified(boolean modified) {
        isModified = modified;
    }

    public void setCurrent(boolean current) {
        isCurrent = current;
    }

    public boolean isModified() {
        return isModified;
    }

    public boolean isCurrent() {
        return isCurrent;
    }

    public boolean isInRAM() {
        return isInRAM;
    }
}
