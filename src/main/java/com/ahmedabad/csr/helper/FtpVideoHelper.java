package com.ahmedabad.csr.helper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

@Component
public class FtpVideoHelper {
private static final String FTP_SERVER = "89.117.188.211";
    private static final String FTP_USERNAME = "u703629182.srsvideos";
    private static final String FTP_PASSWORD = "Nc#7v36F]$~CP7T5";
    private static final int port = 21;

    public String uploadFile (InputStream inputStream, String remoteFileName) {
        FTPClient ftp = new FTPClient();
        try{
            ftp.connect(FTP_SERVER, port);
            System.out.println("connect to ftp server"+FTP_SERVER);

            boolean login = ftp.login(FTP_USERNAME, FTP_PASSWORD);
            if(!login){
                System.out.println("login failed");
                return null;
            }
              ftp.enterLocalPassiveMode();
              ftp.setFileType(FTP.BINARY_FILE_TYPE);

              String workingDir=ftp.printWorkingDirectory();
              System.out.println("corrent working directory is "+workingDir);

              String remotefilepath="/"+ remoteFileName;
              System.out.println("uploding file path"+remotefilepath);

              boolean done= ftp.storeFile(remotefilepath,inputStream);
              inputStream.close();
              
              if(done){
                System.out.println("file uploded successfully");
                return "https://lakhpatididi.in/SRS-documents/videos" + remotefilepath;
              }else{
                System.out.println("file uploding failed");
                int replayCode = ftp.getReplyCode();
                System.out.println("reply code "+replayCode);
                return null;
                            }
                      }catch (IOException e) {
                System.out.println("IOException: " + e.getMessage());
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
