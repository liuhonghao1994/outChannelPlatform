package com.dxt.boss.common;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class IDHelper {

    private static long LASTTIMESTAMP = 0L;
    private static int SEQ = 1001;

    private static String localIP = "";

    /**
     * 提供唯一ID，通过timestamp+seq实现
     * 注意事项，如果分布式部署,IP不起作用时，需要在不同的机器定义不同的MAX_SEQ和MIN_SEQ
     * @return
     */
    public static String getTimestampId(String type) {
        long timestamp = System.currentTimeMillis();
        if (timestamp == LASTTIMESTAMP) {
            if (SEQ < BossConstant.ID_CONSTANTS.MAX_SEQ) {
                ++SEQ;
            } else {
                tillNextTimeUnit();
                LASTTIMESTAMP = System.currentTimeMillis();
                SEQ = BossConstant.ID_CONSTANTS.MIN_SEQ;
            }
        } else {
            LASTTIMESTAMP = timestamp;
            SEQ = BossConstant.ID_CONSTANTS.MIN_SEQ;
        }
        if (BossConstant.ID_CONSTANTS.TYPE_LONG.equals(type)) {
            return getLocalIP() + String.valueOf(LASTTIMESTAMP) + SEQ;
        } else {
            return getLocalIP() + String.valueOf(SEQ);
        }

    }

    /**
     * 死循环，直到下一个毫秒
     */
    private static void tillNextTimeUnit() {
        long timestamp = System.currentTimeMillis();
        while (timestamp == LASTTIMESTAMP) {
            timestamp = System.currentTimeMillis();
        }
    }

    private static String getLocalIP() {
        if (null != localIP && !"".equals(localIP)) {
            return localIP;
        }
        try {
            //获取多个网卡
            Enumeration<?> e1 = NetworkInterface.getNetworkInterfaces();
            while (e1.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) e1.nextElement();
                if(("eth0").equals(ni.getName()) || ("bond0").equals(ni.getName())){
                    Enumeration<?> e2 = ni.getInetAddresses();
                    while (e2.hasMoreElements()) {
                        InetAddress ia = (InetAddress) e2.nextElement();
                        //排除IPv6地址
                        if (ia instanceof Inet6Address) {
                            continue;
                        }
                        localIP = ia.getHostAddress().replace(".", "");
                    }
                    break;
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return localIP.replace(".", "");
    }

    public static void main(String args[]) {
        System.out.println(getLocalIP());
    }

}
