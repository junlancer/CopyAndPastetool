package fileSynchronization.excute;

import fileSynchronization.utils.ConfigUtils;

public class VirtualMachine {
    public static void main(String[] s) {
        start();
    }

    private static void start() {
        if (ConfigUtils.getMode() == 0) {
            new fileSynchronization.fileIOBased.Client().start();
        } else {
            new fileSynchronization.socketBase.Client().start();
        }
    }
}
