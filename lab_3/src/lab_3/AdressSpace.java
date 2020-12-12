package lab_3;

import java.util.ArrayList;

public class AdressSpace {
    private int baseRegister;
    private int restrictiveRegister;
    private ArrayList<VirtualPage> pageList = new ArrayList<>();

    public int getRange() {
        return restrictiveRegister - baseRegister;
    }

    public ArrayList<VirtualPage> getPageList() {
        return pageList;
    }

    public void setBaseRegister(int baseRegister) {
        this.baseRegister = baseRegister;
    }

    public int getBaseRegister() {
        return baseRegister;
    }



    public AdressSpace(int baseRegister, int restrictiveRegister) {
        this.baseRegister = baseRegister;
        this.restrictiveRegister = restrictiveRegister;
    }
}
