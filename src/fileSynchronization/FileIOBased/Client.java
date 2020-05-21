package fileSynchronization.FileIOBased;

import fileSynchronization.utils.ClipboardUtils;
import fileSynchronization.utils.ConfigUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @Author junlancer
 * @Date 2020.5.20 16.53
 * 粘贴端,虚拟机
 */
public class Client {
    private static FileInputStream inputStream;
    private static File sharedFile;
    private static String filePath;

    public static void main(String[] s) {
        filePath = ConfigUtils.getSharedFilePath() + "\\sharedFile.txt";
        long beforeTime = 0;
        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sharedFile = new File(filePath);
            if (sharedFile.lastModified() != beforeTime) {
                beforeTime = sharedFile.lastModified();
                BufferedReader in = null;
                try {
                    inputStream = new FileInputStream(sharedFile);
                    in = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                } catch (Exception e) {
                    System.out.println("读取共享文件流报错");
                    e.printStackTrace();
                }
                StringBuilder stringBuilder = new StringBuilder();
                char[] bytes = new char[128];
                int off = 0;
                int len = 0;
                while (true) {
                    try {
                        if ((len = in.read(bytes)) == -1) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    stringBuilder.append(new String(bytes, off, len));
                }
                try {
                    in.close();
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //System.out.println(stringBuilder.toString());
                ClipboardUtils.setSysClipboardText(stringBuilder.toString());
            }

        }

    }

}
