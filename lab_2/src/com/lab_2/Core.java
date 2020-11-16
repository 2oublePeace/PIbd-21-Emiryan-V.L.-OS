package com.lab_2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Core {
    private int processesNum;
    private int timeForProcess;
    ArrayList processesList = new ArrayList();
    ArrayList threadList = new ArrayList();
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
            Process tempProcess = (Process) processesList.get(i);
            threadList.add(tempProcess.getThreads());
        }
    }

    public void launchProcesses() {
        for(int i = 0; i < processesList.size(); i++) {
            pausedProcesses.add(processesList.get(i));
        }

        while(isAllProcessesNotEnded()) {
            for(int i = 0; i < pausedProcesses.size(); i++) {
                Process workingProcess = (Process) pausedProcesses.poll();
                workingProcess.setProcessState("Active");
                threadList = workingProcess.getThreads();
                Thread tempThread = (Thread) threadList.get(i);
                for (int j = 0; j < threadList.size(); i++) {
                    workingProcess.setProcessCounter(performOperations(timeForProcess, tempThread));
                }

                if(workingProcess.isAllThreadsEnded()) {
                    workingProcess.setProcessState("Ended");
                    endedProcesses.add(workingProcess);
                    break;
                }

                workingProcess.setProcessState("Pause");
                pausedProcesses.add(workingProcess);
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

    private int performOperations(int timeForProcess, Thread thread) {
        thread.setThreadState("Active");
        do {
            if(thread.operations > 0) {
                thread.operations--;
                thread.programCounter++;
            }
        } while(thread.operations > 0 && thread.programCounter % timeForProcess != 0);
        if(thread.operations == 0) {
            thread.setThreadState("Ended");
        }
        thread.setThreadState("Paused");
        return thread.programCounter;
    }

}
