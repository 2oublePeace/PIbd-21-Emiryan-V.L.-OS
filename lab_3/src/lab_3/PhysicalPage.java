package lab_3;

public class PhysicalPage {
    int pageID;
    int size = 4;
    boolean isCurrent;
    int foreignID;
    int countOfActions;

    public PhysicalPage(int pageID, int foreignID) {
        this.pageID = pageID;
        this.isCurrent = false;
        this.foreignID = foreignID;
        this.countOfActions = 0;
    }

    public PhysicalPage(int pageID) {
        this.pageID = pageID;
        this.isCurrent = false;
        this.foreignID = -1;
        this.countOfActions = 0;
    }

    public void setForeignID(int foreignID) {
        this.foreignID = foreignID;
    }

    public void incCountOfActions() {
        countOfActions++;
    }
}
