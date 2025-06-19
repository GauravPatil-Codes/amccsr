package com.ahmedabad.csr.helper;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

@Component
public class FtpHelper {
    private static final String FTP_SERVER = "89.117.188.211";
    private static final String FTP_USERNAME = "u703629182.srsimages";
    private static final String FTP_PASSWORD = "z+jN&Sgo4;Hr8hI*";
    private static final int port = 21;

    public String uploadFile(InputStream inputStream, String remoteFileName) {
        FTPClient ftp = new FTPClient();
        try {
            ftp.connect(FTP_SERVER, port);

            boolean login = ftp.login(FTP_USERNAME, FTP_PASSWORD);
            if (!login) {
                return null;
            }
            ftp.enterLocalPassiveMode();
            ftp.setFileType(FTP.BINARY_FILE_TYPE);

            String workingDir = ftp.printWorkingDirectory();

            String remotefilepath = "/" + remoteFileName;

            boolean done = ftp.storeFile(remotefilepath, inputStream);
            inputStream.close();

            if (done) {

                return "https://lakhpatididi.in/SRS-documents/images" + remotefilepath;
            } else {

                int replayCode = ftp.getReplyCode();

                return null;
            }
        } catch (IOException e) {

            e.printStackTrace();
            return null;
        } finally {
            try {
                if (ftp.isConnected()) {
                    ftp.logout();
                    ftp.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}