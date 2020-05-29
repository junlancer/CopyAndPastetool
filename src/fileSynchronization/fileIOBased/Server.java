package fileSynchronization.fileIOBased;

import fileSynchronization.utils.ClipboardUtils;
import fileSynchronization.utils.ConfigUtils;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @Author junlancer
 * @Date 2020.5.20 16.03
 * 复制端,物理机
 */
public class Server {
    private String cache;
    private FileOutputStream outputStream;
    private String filePath;

    public void start() {
        filePath = ConfigUtils.getSharedFilePath() + "\\sharedFile.txt";
        System.out.println(filePath);
        //设置刷新时间
        while (true) {
            try {
                Thread.sleep(ConfigUtils.getTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //读取字符
            String clipboardString = ClipboardUtils.getSysClipboardText();
            //判断是否写入,若有变化则写入到共享文件夹
            if (clipboardString != null && !clipboardString.equals(cache)) {
                //String不可变对象
                cache = clipboardString;
                //配置文件的时候要隐藏,防止运行过程中被删除
                writeTosharedFile(cache);
            }
        }
    }

    //外面配置共享文件夹地址
    private void writeTosharedFile(String cache) {
        File sharedFile = new File(filePath);
        if (!sharedFile.exists()) {
            try {
                sharedFile.createNewFile();
            } catch (IOException e) {
                System.out.println("创建共享文件失败!");
                e.printStackTrace();
            }
        } else {
            //存在则需要删掉并重新创建
            if (sharedFile.delete()) {
                sharedFile = new File(filePath);
                try {
                    sharedFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        PrintWriter out = null;
        try {
            outputStream = new FileOutputStream(sharedFile);
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(cache);
        try {
            out.write(cache);
        } catch (Exception e) {
            System.out.println("写入文件流出错");
            e.printStackTrace();
        } finally {
            try {
                out.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
