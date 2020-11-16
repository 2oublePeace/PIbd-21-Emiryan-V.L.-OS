package com.lab_1;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        ArgumentsStack stack = new ArgumentsStack();
        Core core = new Core(stack);
        stack.push(1);
        core.makeCall(0);

        //Отсутствуют аргументы вызова
        core.makeCall(1);

        stack.push(1);
        stack.push("str");
        core.makeCall(2);

        stack.push(1);
        stack.push("str");
        stack.push("str");
        core.makeCall(3);

        //Неверное количество аргументов
        stack.push("str");
        stack.push(2);
        core.makeCall(4);

        //Добавление вызова и вызов с неверными типами данных
        core.addCall(new String[] {"Integer", "String", "Double"});
        stack.push(1);
        stack.push("str");
        stack.push(2);
        core.makeCall(5);

        core.printCallsList();
    }
}
