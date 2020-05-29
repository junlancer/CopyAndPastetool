package fileSynchronization.socketBase;

import fileSynchronization.utils.ClipboardUtils;
import fileSynchronization.utils.ConfigUtils;
import fileSynchronization.utils.MsgUtils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Server {
    public void start() {
        //服务器端调用 listen() 函数后，套接字进入LISTEN状态，开始监听客户端请求
        ServerSocket serverSocket1 = null;
        ServerSocket serverSocket2 = null;
        try {
            serverSocket1 = new ServerSocket(ConfigUtils.getServerPort_0(), 2);
            serverSocket2 = new ServerSocket(ConfigUtils.getServerPort_1(), 2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //写入剪切板
        ServerSocket finalServerSocket1 = serverSocket1;
        new Thread(() -> {
            Socket socket = null;
            try {
                //为了后面循环能减少一次判断
                socket = finalServerSocket1.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (true) {
                socket = listen(finalServerSocket1, socket);

                String text = MsgUtils.receiveMsg(socket);
                if (!text.equals("!QEasd")) {
                    ClipboardUtils.setSysClipboardText(text);
                } else {
                    //System.out.println(text);
                }
            }
        }).start();

        //输出剪切板
        ServerSocket finalServerSocket2 = serverSocket2;
        new Thread(() -> {
            String beforeText = null;
            Socket socket = null;
            try {
                socket = finalServerSocket2.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (true) {
                socket = listen(finalServerSocket2, socket);
                String sysClipboardText = ClipboardUtils.getSysClipboardText();
                if (!sysClipboardText.equals(beforeText)) {
                    beforeText = sysClipboardText;
                    MsgUtils.sendMsg(socket, new String(sysClipboardText.getBytes(), StandardCharsets.UTF_8));
                } else {
                    //发心跳
                    MsgUtils.sendMsg(socket, new String("!QEasd".getBytes(), StandardCharsets.UTF_8));
                }
                //MsgUtils.sendMsg(socket, new String("".getBytes(), StandardCharsets.UTF_8));
            }
        }).start();
    }

    private static Socket listen(ServerSocket serverSocket, Socket socket) {
        try {
            Thread.sleep(ConfigUtils.getTime());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (socket.isClosed()) {
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return socket;
    }

}
