package fileSynchronization.utils;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class MsgUtils {

    public static Socket sendMsg(Socket socket, String s) {
        OutputStream outputStream = null;
        try {
            outputStream = socket.getOutputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            outputStream.write(s.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return socket;
    }

    public static String receiveMsg(Socket socket) {
        InputStream inputStream = null;
        try {
            inputStream = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder stringBuilder = new StringBuilder();
        byte[] bytes = new byte[1024];
        int off = 0;
        int len = 0;
        while (true) {
            try {
                if ((len = inputStream.read(bytes)) == -1) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            stringBuilder.append(new String(new String(bytes, off, len).getBytes(), StandardCharsets.UTF_8));
        }
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
