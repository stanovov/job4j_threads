package ru.job4j;

public final class DCLSingleton {

    private static volatile DCLSingleton inst;

    public static DCLSingleton instOf() {
        DCLSingleton localInst = inst;
        if (localInst == null) {
            synchronized (DCLSingleton.class) {
                localInst = inst;
                if (localInst == null) {
                    localInst = new DCLSingleton();
                    inst = localInst;
                }
            }
        }
        return localInst;
    }

    private DCLSingleton() {
    }

}