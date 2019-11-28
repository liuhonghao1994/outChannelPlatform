package com.dxt.common;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;
import java.io.InputStream;

public class FtpFileUtil {

    public static boolean uploadFile(String originFileName, InputStream input, String ftpAddress, int ftpPort,
                                     String ftpUserName, String ftpUserPwd, String ftpBasePath){
        boolean success = false;
        FTPClient ftp = new FTPClient();
        ftp.setControlEncoding("UTF-8");
        try {
            int reply;
            // 连接FTP服务器
            ftp.connect(ftpAddress, ftpPort);
            // 登录
            ftp.login(ftpUserName, ftpUserPwd);
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return success;
            }
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftp.makeDirectory(ftpBasePath);
            ftp.changeWorkingDirectory(ftpBasePath);
            ftp.storeFile(originFileName,input);
            input.close();
            ftp.logout();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return success;
    }

}
