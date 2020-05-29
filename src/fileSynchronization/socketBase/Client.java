package fileSynchronization.socketBase;

import fileSynchronization.utils.ClipboardUtils;
import fileSynchronization.utils.ConfigUtils;
import fileSynchronization.utils.MsgUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Client {
    public void start() {

        //输出剪切板
        new Thread(() -> {
            Socket socket = new Socket();
            String beforeText = null;
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (true) {
                try {
                    Thread.sleep(ConfigUtils.getTime());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (socket.isClosed()) {
                    try {
                        socket = new Socket();
                        socket.connect(new InetSocketAddress(ConfigUtils.getServerIP(), ConfigUtils.getServerPort_0()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                String sysClipboardText = ClipboardUtils.getSysClipboardText();
                if(!sysClipboardText.equals(beforeText)) {
                    beforeText = sysClipboardText;
                    MsgUtils.sendMsg(socket, new String(ClipboardUtils.getSysClipboardText().getBytes(), StandardCharsets.UTF_8));
                } else {
                    //发心跳
                    MsgUtils.sendMsg(socket, new String("!QEasd".getBytes(), StandardCharsets.UTF_8));
                }
                //MsgUtils.sendMsg(socket, new String("client".getBytes(), StandardCharsets.UTF_8));

            }
        }).start();
        //写入剪切板
        new Thread(() -> {
            Socket socket = new Socket();
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (true) {
                try {
                    Thread.sleep(ConfigUtils.getTime());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (socket.isClosed()) {
                    try {
                        socket = new Socket();
                        socket.connect(new InetSocketAddress(ConfigUtils.getServerIP(), ConfigUtils.getServerPort_1()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                String text = MsgUtils.receiveMsg(socket);
                if (!text.equals("!QEasd")) {
                    ClipboardUtils.setSysClipboardText(text);
                } else {
                    //System.out.println(text);
                }
                //System.out.println("clientGet: " + MsgUtils.receiveMsg(socket));
            }
        }).start();

    }

}
