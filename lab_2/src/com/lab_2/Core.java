package com.volodya;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Core {
    private int processesNum;
    private int timeForProcess;
    ArrayList processesList = new ArrayList();
    Queue startedProcesses = new LinkedList();
    Queue pausedProcesses = new LinkedList();
    Queue endedProcesses = new LinkedList();


    public Core(int processesNum) {
        this.processesNum = processesNum;
    }

    public Core() {
        processesNum =(int) (Math.random() * 10);
        timeForProcess =(int) (Math.random() * 10);
        addProcesses();
    }

    public void addProcesses() {
        for(int i = 0; i < processesNum; i++) {
            processesList.add(new Process(i, "Paused", 0, timeForProcess));
        }
    }

    public void launchProcesses() {
        for(int i = 0; i < processesList.size(); i++) {
            pausedProcesses.add(processesList.get(i));
        }

        while(isAllProcessesNotEnded()) {
            for(int i = 0; i < pausedProcesses.size(); i++) {
                startedProcesses.add(pausedProcesses.poll());
                Process tempProcess = (Process) startedProcesses.poll();
                tempProcess.launchThreads();
                if(tempProcess.getProcessState() == "Paused") {
                    pausedProcesses.add(tempProcess);
                } else if(tempProcess.getProcessState() == "Ended") {
                    endedProcesses.add(tempProcess);
                }
            }
        }
    }

    private boolean isAllProcessesNotEnded() {
        for(int i = 0; i < processesList.size(); i++) {
            Process tempProcess = (Process) processesList.get(i);
            if(tempProcess.getProcessState() == "Active" || tempProcess.getProcessState() == "Paused") {
                return true;
            }
        }
        return false;
    }

    class Thread {
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

        private int performOperations(int timeToProcess) {
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

        private String getThreadState() {
            return threadState;
        }
    }
}
