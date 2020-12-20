import java.util.ArrayList;
import java.util.Random;

public class Core {
    private int time;
    private Random random;
    private int woBreaking;
    private int wBreaking;
    ArrayList<Process> processes = new ArrayList<>();
    ArrayList<Driver> drivers = new ArrayList<>();

    public Core(int time) {
        this.time = time;
        this.woBreaking = 0;
        this.wBreaking = 0;
        random = new Random();
        connectDevices();
        makeProcesess();
        executeProcessWOB();
        this.time = time;
        executeProcessWB();
    }

    private void connectDevices() {
        int bound = random.nextInt(3) + 2;
        for(int i = 0; i < bound; i++) {
            drivers.add(new Driver(random.nextInt(10), random.nextInt(10)));
        }
    }

    private void makeProcesess() {
        for(int i = 0; i < random.nextInt(8) + 2; i++) {
            processes.add(new Process(random.nextInt(10), "Pause", 0));
        }
    }

    public void executeProcessWOB(){
        int i = 0;
        while (time > 0) {
            if(isBreak()) {
                time -= drivers.get(random.nextInt(drivers.size())).makeOperations();
            } else {
                woBreaking += processes.get(i).processing(random.nextInt(15) + 5);
                time -= woBreaking;
                if(i < processes.size() - 1) {
                    i++;
                } else {
                    i = 0;
                }
            }
        }
    }

    public void executeProcessWB(){
        int i = 0;
        int breaked = 0;
        while (time > 0) {
            if(isBreak()) {
                breaked = i;
                if(i < processes.size() - 1) {
                    i++;
                } else {
                    i = 0;
                }
                int timeToProcesess = drivers.get(random.nextInt(drivers.size())).makeOperations();
                time -= drivers.get(random.nextInt(drivers.size())).makeOperations();
                while(timeToProcesess > 0 && i != breaked) {
                    wBreaking += processes.get(i).processing(random.nextInt(timeToProcesess));
                    timeToProcesess -= wBreaking;
                    if(i < processes.size() - 1) {
                        i++;
                    } else {
                        i = 0;
                    }
                }
            } else {
                wBreaking += processes.get(i).processing(random.nextInt(15) + 5);
                time -= wBreaking;
                if(i < processes.size() - 1) {
                    i++;
                } else {
                    i = 0;
                }
            }
        }
    }

    private boolean isBreak() {
        int rnd = random.nextInt(10);
        if(rnd > 5) {
            return true;
        }
        return false;
    }

    public int getWoBreaking() {
        return woBreaking;
    }

    public int getwBreaking() {
        return wBreaking;
    }
}
