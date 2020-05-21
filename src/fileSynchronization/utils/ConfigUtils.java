package fileSynchronization.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigUtils {

    public static String getSharedFilePath() {
        //user.dir指定了当前的路径,当前jar文件夹的路径
        String configPath = System.getProperty("user.dir") + "\\config.properties";
        System.out.println("path: " + configPath);
        Properties properties = new Properties();
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
        return properties.getProperty("sharedFilePath");

    }
}
