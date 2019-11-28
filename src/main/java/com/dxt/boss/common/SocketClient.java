package com.dxt.boss.common;

import com.dxt.common.AppConstant;
import com.dxt.service.CacheManager;
import com.dxt.util.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.net.www.http.HttpClient;

import javax.annotation.PostConstruct;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

@Component
public class SocketClient {

    /**
     * 高位在低字节
     */
    private static final int ALIGNMENT_HIGHT = 0;
    /**
     * 低位在高字节
     */
    private static final int ALIGNMENT_LOW = 1;
    /**
     * 报文头长度
     */
    private static final int HEADER_LEN = 4;

    private Socket socket = null;
    private InputStream in = null;
    private DataOutputStream dos = null;

    private static SocketClient socketClient;

    @Autowired
    private CacheManager cacheManager;

    @PostConstruct
    public void init() {
        socketClient = this;
        socketClient.cacheManager = this.cacheManager;
    }

    /**
     * 客户端发送请求
     *
     * @param content 发送的内容
     * @return
     * @throws Exception
     */
    public static String send(String content) throws Exception {
        SocketClient client = new SocketClient();
        return client.sendJson(content);
    }

    private String sendJson(String json) throws Exception {
        byte[] bytes;
        try {
            open();
            byte[] reqData = json.getBytes("utf-8");
            int length = reqData.length;
            byte[] reqLen = intToByteArray(length, 4, ALIGNMENT_HIGHT);
            byte[] inPack = new byte[length + 4];
            System.arraycopy(reqLen, 0, inPack, 0, 4);
            System.arraycopy(reqData, 0, inPack, 4, length);
            send(inPack);
            bytes = receive();
        } finally {
            close();
        }
        return new String(bytes, "utf-8");
    }

    /**
     * 建立Socket连接
     *
     * @throws Exception
     */
    private void open() throws IOException {
//        socket = new Socket(AppConstant.UIP_SOCKET.IP, AppConstant.UIP_SOCKET.PORT);

        socket = new Socket(socketClient.cacheManager.
                getSysConfigByCode(AppConstant.SYS_CONFIG_KEY.KEY_UIP_SOCKET_IP),
                Integer.parseInt(socketClient.cacheManager.
                        getSysConfigByCode(AppConstant.SYS_CONFIG_KEY.KEY_UIP_SOCKET_PORT)));

        if (socket != null) {
            socket.setTcpNoDelay(true);
            socket.setSoTimeout(Integer.parseInt(socketClient.cacheManager.
                    getSysConfigByCode(AppConstant.SYS_CONFIG_KEY.KEY_UIP_SOCKET_TIMEOUT)));
            in = socket.getInputStream();
            dos = new DataOutputStream(socket.getOutputStream());
        }

    }

    /**
     * 关闭Socket连接
     *
     * @throws Exception
     */
    private void close() throws IOException {
        if (socket != null) {
            in.close();
            dos.close();
            socket.close();
        }
    }

    /**
     * 发送字节流
     *
     * @param data 需发送的数据
     * @throws IOException
     */
    private void send(byte[] data) throws IOException {
        if (data == null || data.length == 0) {
            throw new IOException("[socket_client]指定的数据为空!");
        }
        dos.write(data);
        dos.flush();
    }

    /**
     * 接收数据
     * @return byte[]
     * @throws IOException
     */
    private byte[] receive() throws Exception {
        // 读取报文头部信息
        byte[] headBuffer = new byte[HEADER_LEN];
        int offset = 0;
        while (offset < HEADER_LEN) {
            int size = in.read(headBuffer, offset, HEADER_LEN - offset);
            if (size == -1) {
                throw new IOException("[socket_client]读数据失败，read返回-1");
            }
            offset += size;
        }
        //计算报文长度
        int length = byteArrayToInt(headBuffer, offset, ALIGNMENT_HIGHT);
        offset = 0;
        //报文体全部字节
        byte[] data = new byte[length];
        while (offset < length) {
            int size = in.read(data, offset, length - offset);
            if (size > 0) {
                offset += size;
            }
        }
        return data;
    }

    /**
     * 将指定的二进制数组转换成int型
     * @param data      二进制数组对象
     * @param length    数据长度
     * @param alignment 数据对齐方式
     * @return int        转换后的数据
     * @throws Exception
     */
    private static int byteArrayToInt(byte[] data, int length, int alignment) throws Exception {
        int value = 0;

        switch (alignment) {
            case ALIGNMENT_HIGHT:
                for (int i = 0; i < length; i++) {
                    value <<= 8;
                    value += toInt(data[i]);
                }
                break;
            case ALIGNMENT_LOW:
                for (int i = 0; i < length; i++) {
                    value <<= 8;
                    value += toInt(data[length - i - 1]);
                }
                break;
            default:
                throw new Exception("[socket_client]数据对齐方式非法!");
        }

        return value;
    }

    private static int toInt(byte b) {
        if (b >= 0) {
            return (int) b;
        } else {
            return (int) (b + 256);
        }
    }

    /**
     * 将指定的int型转换成二进制数组
     * @param value     int型数据
     * @param length    数据长度
     * @param alignment 数据对齐方式
     * @return byte[]    转换后的数据
     * @throws Exception
     */
    private byte[] intToByteArray(int value, int length, int alignment) throws Exception {
        byte[] data = new byte[length];

        switch (alignment) {
            case ALIGNMENT_HIGHT:
                for (int i = 0; i < length; i++) {
                    data[length - i - 1] = (byte) (value & 0xff);
                    value >>>= 8;
                }
                break;
            case ALIGNMENT_LOW:
                for (int i = 0; i < length; i++) {
                    data[i] = (byte) (value & 0xff);
                    value >>>= 8;
                }
                break;
            default:
                throw new Exception("[socket_client]数据对齐方式非法!");
        }
        return data;
    }

    public CacheManager getCacheManager() {
        return cacheManager;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
}
