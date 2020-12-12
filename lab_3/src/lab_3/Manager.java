package lab_3;

import java.util.HashMap;
import java.util.Random;

class Manager {
    Core core;
    public Manager(Core core) {
        this.core = core;
    }

    public void makeProcesess() {
        Random random = new Random();
        int register = 0;
        int memoryCapacity = 0;
        int nummerOfProcesess = random.nextInt(1) + 6;

        for(int i = 0; i < nummerOfProcesess; i++) {
            memoryCapacity = (4 << random.nextInt(2) + 1);
            if(register + memoryCapacity <= core.RAM_CAPACITY) {
                Process process = new Process(i, "pause", memoryCapacity, new AdressSpace(register, register + memoryCapacity));
                core.setProcessTable(i, process);

                Cell cell = new Cell(true, register, memoryCapacity, process);
                core.cellList.add(cell);
                makeLinkTable(process);

                register += memoryCapacity + 1;
            } else {
                VirtualPage vp = NRU();
                Process process = new Process(i, "pause", memoryCapacity, new AdressSpace(core.processTable.get(vp.pageID).adressSpace.getBaseRegister() - 4, core.processTable.get(vp.pageID).adressSpace.getBaseRegister()));
                PhysicalPage pp = core.pageLinkTable.get(vp);
                core.pageLinkTable.remove(vp);
                core.physicalPageList.add(pp);
                Cell cell = new Cell(true, register, core.RAM_CAPACITY - register, process);
                core.cellList.add(cell);
                break;
            }
        }
        if(register < core.RAM_CAPACITY) {
            Cell cell = new Cell(false, register, core.RAM_CAPACITY - register);
            core.cellList.add(cell);
        }
        System.out.println();
    }

    public void makeLinkTable(Process process) {
        Random random = new Random();
        for (int i = 0; i < process.getPageList().size(); i++) {
            if(i + 1 < core.physicalPageList.size()) {
                core.pageLinkTable.put(process.getPageList().get(i), core.physicalPageList.get(i));
                core.physicalPageList.remove(i);
            } else {
                break;
            }
        }
        System.out.println();
    }

    private VirtualPage NRU() {
        int processIndex = -1;
        int pageIndex = -1;
        boolean findPage = false;
        VirtualPage comparePage;
        HashMap<String, Process> replaceTable = new HashMap<>();
        replaceTable.put("no appeal and no modify", null);
        replaceTable.put("no appeal and was modify", null);
        replaceTable.put("was appeal and no modify", null);
        replaceTable.put("was appeal and was modify", null);
        comparePage = core.processTable.get(0).getPageList().get(0);
        for(int i = 0; i < core.processTable.size(); i++) {
            for(int j = 0; j < core.processTable.get(i).getPageList().size(); j++) {
                if(core.processTable.get(i).getPageList().get(j).isModified == false && core.pageLinkTable.get(core.processTable.get(i).getPageList().get(j)).countOfActions == 0) {
                    core.processTable.get(i).adressSpace.setBaseRegister(core.processTable.get(i).adressSpace.getBaseRegister() + 4);
                    return core.processTable.get(i).getPageList().get(j);
                }
            }

            for(int j = 0; j < core.processTable.get(i).getPageList().size(); j++) {
                if(core.processTable.get(i).getPageList().get(j).isModified == true && core.pageLinkTable.get(core.processTable.get(i).getPageList().get(j)).countOfActions == 0) {
                    return core.processTable.get(i).getPageList().get(j);
                }
            }

            for(int j = 0; j < core.processTable.get(i).getPageList().size(); j++) {
                if(core.processTable.get(i).getPageList().get(j).isModified == false && core.pageLinkTable.get(core.processTable.get(i).getPageList().get(j)).countOfActions < core.pageLinkTable.get(comparePage).countOfActions) {
                    comparePage = core.processTable.get(i).getPageList().get(j);
                    findPage = true;
                }
            }
            if(findPage) {
                return comparePage;
            }
        }
        return comparePage;
    }

    class Cell {
        boolean isEmployed;
        int adressStart;
        int memoryCapacity;
        Process process;

        public Cell(boolean isEmployed, int adressStart, int memoryCapacity, Process process) {
            this.isEmployed = isEmployed;
            this.adressStart = adressStart;
            this.memoryCapacity = memoryCapacity;
            this.process = process;
        }

        public Cell(boolean isEmployed, int adressStart, int memoryCapacity) {
            this.isEmployed = isEmployed;
            this.adressStart = adressStart;
            this.memoryCapacity = memoryCapacity;
            this.process = null;
        }
    }
}
