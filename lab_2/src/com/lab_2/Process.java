package com.lab_2;

import java.util.ArrayList;

public class Process {
    private int processID;
    private String processState;
    private int processCounter;
    private int threadsNum;
    private int timeToProcess;
    ArrayList threads = new ArrayList();

    public Process(int processID, String processState, int processCounter, int timeToProcess) {
        this.processID = processID;
        this.processState = processState;
        this.processCounter = processCounter;
        this.timeToProcess = timeToProcess;
        threadsNum = (int)(Math.random() * 4) + 1;
        createThreads(threadsNum);
    }

    private void createThreads(int num) {
        for (int i = 0; i < num; i++) {
            threads.add(new Thread(processID, (int)Math.random() * 10, "Paused"));
        }
    }

    public void launchThreads() {
        processState = "Active";
        for (int i = 0; i < threads.size(); i++) {
            Thread tempThread = (Thread) threads.get(i);
            processCounter += tempThread.performOperations(timeToProcess);
        }

        if(isAllThreadsEnded()) {
            processState = "Ended";
            return;
        }

        processState = "Paused";
    }

    private boolean isAllThreadsEnded() {
        for(int i = 0; i < threads.size(); i++) {
            Thread tempThread = (Thread)threads.get(i);
            if(tempThread.getThreadState() == "Active" || tempThread.getThreadState() == "Paused") {
                return false;
            }
        }
        return true;
    }

    public String getProcessState() {
        return processState;
    }

    public ArrayList getThreads() {
        return threads;
    }
}
