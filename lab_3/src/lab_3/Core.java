package lab_3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class Core {
    HashMap<Integer, Process> processTable;
    HashMap<VirtualPage, PhysicalPage> pageLinkTable = new HashMap<>();
    LinkedList<Manager.Cell> cellList = new LinkedList<>();
    ArrayList<PhysicalPage> physicalPageList = new ArrayList<>();
    int RAM_CAPACITY = 128;

    public Core() {
        processTable = new HashMap<>();
        makePhysicalPages();
    }

    private void makePhysicalPages() {
        Random random = new Random();
        for (int i = 0; i < 128; i += 4) {
            physicalPageList.add(new PhysicalPage(random.nextInt(48) + 24));
        }
    }

    public void launchProcesess() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            Process process = processTable.get(random.nextInt(processTable.size()));
            process.setProcessState("working");
            for (int j = 0; j < process.getPageList().size(); j++) {
                pageLinkTable.get(process.getPageList().get(j)).incCountOfActions();
                if(process.getPageList().get(j).isCurrent == false) {
                    process.getPageList().get(j).setCurrent(true);
                }
                if(process.getPageList().get(j).isInRAM() == false) {
                    process.getPageList().get(j).setInRAM(true);
                }
            }
        }
    }

    public void setProcessTable(int index, Process process) {
        this.processTable.put(index, process);
    }
}
