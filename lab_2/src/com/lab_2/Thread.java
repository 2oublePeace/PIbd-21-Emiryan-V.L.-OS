package com.lab_2;

public class Thread {
    private int parentProcess;
    private int threadID;
    private String threadState;
    public int programCounter;
    public int operations;

    public Thread(int parentProcess, int threadID, String state) {
        this.parentProcess = parentProcess;
        this.threadID = threadID;
        this.threadState = state;
        this.programCounter = 0;
        operations = (int)(Math.random() * 10);
    }

    public String getThreadState() {
        return threadState;
    }

    public void setThreadState(String threadState) {
        this.threadState = threadState;
    }
}
