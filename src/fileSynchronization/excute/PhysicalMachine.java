package fileSynchronization.excute;

import fileSynchronization.utils.ConfigUtils;

public class PhysicalMachine {
    public static void main(String[] s) {
        start();
    }

    private static void start() {
        if (ConfigUtils.getMode() == 0) {
            new fileSynchronization.fileIOBased.Server().start();
        } else {
            new fileSynchronization.socketBase.Server().start();
        }
    }
}
