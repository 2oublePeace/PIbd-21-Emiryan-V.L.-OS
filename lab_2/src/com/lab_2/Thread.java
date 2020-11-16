package com.lab_2;

public class Thread {
    private int parentProcess;
    private int threadID;
    private String threadState;
    private int programCounter;
    private int operations;

    public Thread(int parentProcess, int threadID, String state) {
        this.parentProcess = parentProcess;
        this.threadID = threadID;
        this.threadState = state;
        this.programCounter = 0;
        operations = (int)(Math.random() * 10);
    }

    public int performOperations(int timeToProcess) {
        threadState = "Active";
        do {
            if(operations > 0) {
                operations--;
                programCounter++;
            }
        } while(operations > 0 && programCounter % timeToProcess != 0);
        if(operations == 0) {
            threadState = "Ended";
        }
        threadState = "Paused";
        return programCounter;
    }

    public String getThreadState() {
        return threadState;
    }
}
