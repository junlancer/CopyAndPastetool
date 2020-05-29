package fileSynchronization.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigUtils {
    private static Properties properties;
    private static long T;
    private static String serverIP;
    private static int serverPort_0;
    private static int serverPort_1;

    static {
        //user.dir指定了当前的路径,当前jar文件夹的路径
        String configPath = System.getProperty("user.dir") + "\\config.properties";
        System.out.println("configPath: " + configPath);
        properties = new Properties();
        // 使用InPutStream流读取properties文件
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(configPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            properties.load(bufferedReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String time = properties.getProperty("time");
        if (isNumeric(time)) {
            long t = Long.parseLong(time);
            if (t <= 200) {
                T = 200;
            } else {
                T = 2500 < t ? 2500 : t;
            }
        } else {
            T = 500;
        }
        serverIP = properties.getProperty("serverIP");
        serverPort_0 = Integer.parseInt(properties.getProperty("serverPort_0"));
        serverPort_1 = Integer.parseInt(properties.getProperty("serverPort_1"));
    }

    public static int getMode() {
        //默认为file, return 0, socket return 1;
        return "socket".equals(properties.getProperty("mode")) ? 1 : 0;
    }

    public static long getTime() {
        return T;
    }

    public static String getServerIP() {
        return serverIP;
    }

    public static int getServerPort_0() {
        return serverPort_0;
    }

    public static int getServerPort_1() {
        return serverPort_1;
    }

    public static String getSharedFilePath() {
        return properties.getProperty("sharedFilePath");
    }

    private static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }


}
