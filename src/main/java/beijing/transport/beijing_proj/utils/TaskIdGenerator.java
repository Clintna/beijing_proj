package beijing.transport.beijing_proj.utils;

import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;

/**
 * 任务ID生成工具
 *
 * @Author Jinglin
 * @Date 2022年4月26日13:49:15
 * @Version 1.0
 */
public class TaskIdGenerator {
    /**
     * 最新一次生成时间
     */
    private static Long latestTimeStamp = System.currentTimeMillis();

    /**
     * ip序列
     */
    private final static Integer IP_SEQUENCE = 2 << 17;

    /**
     * 根据租户ID生成任务ID
     *
     * @param tenantId
     * @return
     */
    public static synchronized String nextId(final Long tenantId) {

        StringBuilder newId = new StringBuilder(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")))
                .append(buildSegByTenantId(tenantId))
                .append(buildSegByHost())
                .append(buildSegByTimeStamp());
        latestTimeStamp = System.currentTimeMillis();
        return newId.toString();
    }

    /**
     * 不带租户的唯一ID
     *
     * @return
     */
    public static synchronized String nextId() {
        StringBuilder newId = new StringBuilder(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")))
                .append(buildSegByTenantId(-1L))
                .append(buildSegByHost())
                .append(buildSegByTimeStamp());
        latestTimeStamp = System.currentTimeMillis();
        return newId.toString();
    }

    /**
     * 构建租户ID模块
     *
     * @param tenantId
     * @return
     */
    public static String buildSegByTenantId(final Long tenantId) {
        if (null == tenantId) {
            throw new IllegalArgumentException(String.format("构建唯一流水时, 租户id不能为空"));
        }
        String seg2 = String.valueOf((tenantId ^ tenantId >>> 32) % 1024);
        return filledByZero(seg2, 5);
    }

    /**
     * 构建网络ip模块
     *
     * @return
     * @throws SocketException
     */
    public static String buildSegByHost() {
        String localIp = getLocalIp();
        if (null == localIp) {
            localIp = "127.0.0.1";
        }
        String[] ips = localIp.split("\\.");
        if (ips.length != 4) {
            throw new IllegalArgumentException(String.format("非法的ip地址: %s", localIp));
        }

        Long ipTemp = Long.valueOf(ips[0] + ips[1] + ips[2] + ips[3]);
        String seg = String.valueOf((ipTemp ^ ipTemp >>> 16) % IP_SEQUENCE);
        return filledByZero(seg, 6);
    }

    /**
     * 随机时间戳
     *
     * @return
     */
    public static String buildSegByTimeStamp() {
        long currentTime = System.currentTimeMillis();
        if (currentTime < latestTimeStamp.longValue()) {
            throw new IllegalArgumentException(String.format("出现了时钟回拨, currentTime: %s, lastedTime: %s", currentTime, latestTimeStamp));
        }

        if (currentTime == latestTimeStamp.longValue()) {
            currentTime = tillToNextTime();
        }

        String seg = String.valueOf((currentTime ^ currentTime >>> 32) % 2048);
        return filledByZero(seg, 4);
    }

    /**
     * 填充0
     *
     * @param temp
     * @return
     */
    public static String filledByZero(final String temp, final Integer targetLength) {

        int needFillIndex = targetLength - temp.length();
        if (0 == needFillIndex) {
            return temp;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < needFillIndex; i++) {
            sb.append("0");
        }
        return sb.append(temp).toString();
    }


    /**
     * 获取本机IP
     *
     * @return
     * @throws SocketException
     */
    private static String getLocalIp() {

        String ip = null;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
        }

        if (null != ip) {
            return ip;
        }

        try {

            Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();

            while (enumeration.hasMoreElements()) {
                NetworkInterface nf = enumeration.nextElement();
                if (nf.isLoopback() || nf.isVirtual()
                        || !nf.isUp()) {
                    continue;
                }

                Enumeration<InetAddress> addresses = nf.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress ipAddress = addresses.nextElement();
                    if (ipAddress instanceof Inet4Address) {
                        return ipAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            return null;
        }
        return null;
    }

    /**
     * 阻塞, 直到下一个毫秒
     *
     * @return
     */
    private static Long tillToNextTime() {
        for (; ; ) {
            long currentTime = System.currentTimeMillis();
            if (currentTime > latestTimeStamp.longValue()) {
                return currentTime;
            }
        }
    }

}

 