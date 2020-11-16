package com.lab_1;

import java.util.ArrayList;

public class Calls {
    private ArrayList<String[]> functionArgs = new ArrayList<>();

    public Calls() {
        functionArgs.add(new String[]{"Integer"});
        functionArgs.add(new String[]{"Integer", "Integer"});
        functionArgs.add(new String[]{"Integer", "String"});
        functionArgs.add(new String[]{"Integer", "String", "String"});
        functionArgs.add(new String[]{"Integer", "String", "Integer"});
    }

    public String checkDataType(Object o) {
        if(isInt(o)) {
            return "Integer";
        } else if(isString(o)) {
            return "String";
        } else if(isDouble(o)) {
            return "Double";
        }
        return null;
    }

    private boolean isInt(Object o) {
        if(o instanceof Integer) {
            return true;
        }
        return false;
    }

    private boolean isDouble(Object o) {
        if(o instanceof Double) {
            return true;
        }
        return false;
    }

    private boolean isString(Object o) {
        if(o instanceof String) {
            return true;
        }
        return false;
    }

    public void addArgs(String[] newArgument) {
        functionArgs.add(newArgument);
    }

    public String[] getArgs(int functionIndex) {
        return functionArgs.get(functionIndex);
    }

    public int getArgsLength(int index) {
        return functionArgs.get(index).length;
    }

    public int getStackLength() {
        return functionArgs.size();
    }
}
