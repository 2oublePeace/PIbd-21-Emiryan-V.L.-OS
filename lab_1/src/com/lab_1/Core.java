package com.lab_1;

public class Core {

    ArgumentsStack stack = new ArgumentsStack();
    Calls calls;
    public Core(ArgumentsStack stack) {
        calls = new Calls();
        this.stack = stack;
    }

    public void printCallsList() {
        System.out.println("\n===============Calls===============");
        for (int i = 0; i < calls.getStackLength(); i++) {
            System.out.println("System call №" + (i + 1));
            System.out.println("Arguments type: ");
            for (int j = 0; j < calls.getArgsLength(i); j++) {
                System.out.println(calls.getArgs(i)[j]  + " ");
            }
            System.out.println("===================================");
        }
    }

    public void makeCall(int index) {
        if(stack.size() == 0){
            System.out.println("Ошибка! Отсутствуют аргументы функции");
            System.out.println("=====================================\n");
        } else if(index < 0 && index > calls.getStackLength()) {
            System.out.println("Ошибка! Отсутствует запрашиваемая функция");
            System.out.println("=========================================\n");
        } else if(stack.size() != calls.getArgsLength(index)){
            System.out.println("Ошибка! Неправильное количество аргументов");
            System.out.println("==========================================\n");
        } else {
            for (int i = 0; stack.size() != 0; i++) {
                if(calls.checkDataType(stack.pop()) == calls.getArgs(index)[calls.getArgsLength(index) - i - 1]){
                    continue;
                } else {
                    System.out.println("Ошибка! Неправильный тип данных аргумента");
                    return;
                }
            }
            System.out.println("Success system call №" + (index + 1));
            System.out.println("======================\n");
        }
        stack.clear();
    }

    public void addCall(String[] newArgument) {
        calls.addArgs(newArgument);
    }
}
