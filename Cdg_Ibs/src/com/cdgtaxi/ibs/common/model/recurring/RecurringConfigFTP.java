package com.cdgtaxi.ibs.common.model.recurring;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

public class RecurringConfigFTP {
	private static final Logger logger = Logger.getLogger(RecurringConfigFTP.class);
	private static Properties config;
//	private static final String CONFIG_FILE_NAME = "recurring.properties";

	private static org.apache.commons.net.ftp.FTPClient ftpClient = null;
	/**
	 * To load the properties file.
	 * 
	 * @throws IOException
	 *             If reading of properties file failed
	 */
	public static void load() throws IOException {

		String directory = "";
		/*
		 * *******************************************************************
		 * THIS PART IS TO BE UNCOMMENTED WHEN USE WITH WINDOW SERVICE.
		 * FOR LOCAL TESTING, YOU MAY UNCOMMENT IT AWAY.
		 */
		// logger.info("MAIN-CLASS PATH: "+System.getProperty("java.class.path"));
		// directory = (new File(Sys.getProperty("java.class.path"))).getParent()+"/";
		/* ******************************************************************** */

//		logger.info("LOADING PROPERTIES: " + directory + CONFIG_FILE_NAME);
//		config = new Properties();
//		FileInputStream fis = new FileInputStream(directory + CONFIG_FILE_NAME);
//		config.load(fis);
//		fis.close();
//		logger.info("LOAD COMPLETED!");
	}

	
	/*
	 * DEFINE ALL METHODS TO RETRIEVE THE PROPERTIES BELOW
	 * ---------------------------------------------------
	 */

	public static String getDirectory() {
		return config.getProperty("recurring.directory");
	}
	public static String getFilename() {
		return config.getProperty("recurring.filename");
	}
	

	public boolean isActive() {
		try {
			if(ftpClient.isConnected()) {
				return true;
			}else {
				return false;
			}
		}catch(Exception e) {
			return false;
			//any error would mean session is not established
		}
	}	
	public static void connectFTP(String userid, String hostName, String password, int port) {
		
		try {

			logger.info("Connecting to ftp : "+hostName+" , port : "+port + " userid: " + userid + " password: " + password) ;

			 ftpClient = new org.apache.commons.net.ftp.FTPClient();
			
			 ftpClient.connect(hostName, port);
	            
	         int replyCode = ftpClient.getReplyCode();
	         if (!FTPReply.isPositiveCompletion(replyCode)) {
	                ftpClient.disconnect();
		            throw new Exception("Connect failed");
	         }
	         
	         ftpClient.enterLocalPassiveMode();
	         boolean success = ftpClient.login(userid, password);
	         
	         if (!success) {
	              ftpClient.disconnect();
	              throw new Exception("Could not login to the server");
	          }
		
			logger.info("Connected to ftp : "+hostName+" , port : "+port);

		}catch(Exception e ) {
			logger.info("Connection failed : "+e.getMessage());
		}
	}

	public void upload(String source, String destination, String deleteAsc) {
		try {
			
			File firstLocalFile = new File(source);
			InputStream inputStream = new FileInputStream(firstLocalFile);
			boolean done = RecurringConfigFTP.ftpClient.storeFile(destination, inputStream);
	        inputStream.close();
	        if (done) {
				logger.info("File uploaded : "+ source +" to : "+destination);
				logger.info("FTP UPLOAD SUCESSFULLY");
	        }
	        else
	        	logger.info("FTP UPLOAD FAIL");

//			if(deleteAsc.equalsIgnoreCase("Y"))
//			new File(source).delete();
	        
		}catch(Exception e) {
			e.printStackTrace();
			logger.info("FTP UPLOAD FAILED at "+ source +" for destination : "+destination);
		}

	}

	public void download(String source, String destination) {
		try {

			FileOutputStream outStream = new FileOutputStream(destination);
			boolean done = RecurringConfigFTP.ftpClient.retrieveFile(source, outStream);
	        outStream.close();
	        if (done) {
				logger.info("File downloaded : "+ source +" to : "+destination);
				logger.info("FTP DOWNLOAD SUCESSFULLY");
	        }
	        else
	        	logger.info("FTP DOWNLOAD FAIL");

		}catch(Exception e) {
			e.printStackTrace();
			logger.info("FTP DOWNLOAD FAILED at "+ source +" for destination : "+destination);
		}
	}
	
	public void downloadFilesInDirectory(String source, String destination, String filePrefix1, String filePrefix2) {
		try {
//			System.out.println("#####prefix1: "+ prefix1);
//			System.out.println("#####prefix2: "+ prefix2);
//			System.out.println("#####prefix3: "+ prefix3);

			int i = 0;
			// lists files and directories in the current working directory
			FTPFile[] files = RecurringConfigFTP.ftpClient.listFiles(source);
			for (FTPFile file : files) {
				
				    if (!file.isDirectory()) {
				    	if(!file.getName().contains(".asc") && file.getName().contains(filePrefix1) && file.getName().contains(filePrefix2)) 
				    	{
					    	System.out.println("source + entry: " + source+"/"+file.getName() + " destination + entry: " + destination + file.getName());
					    	FileOutputStream outStream = new FileOutputStream(destination+file.getName());
							RecurringConfigFTP.ftpClient.retrieveFile(source+"/"+file.getName(), outStream);
					        outStream.close();
					    	logger.info("File downloaded : "+ source+"/"+file.getName() +" to : "+destination+file.getName());
					    	i++;
				    	}
				    }
			}
			
			if(i > 0)
			{
				logger.info("SFTP DOWNLOAD SUCESSFULLY");
			}
			else
			{
				logger.info("FTP no file Downloaded at : "+source + ". destination : "+destination+". looking for prefix : "+filePrefix1 +" & "+filePrefix2);
			}
		}catch(Exception e) {
			e.printStackTrace();
			logger.info("FTP no file Downloaded at : "+source + ". destination : "+destination+". looking for prefix : "+filePrefix1 +" & "+filePrefix2);
		}
	}
	public void moveFileToBackUp(String srcFolder, String srcFileName, String destFold) throws Exception {

		File srcFile = new File(srcFolder);
		File destFolder = new File(destFold);
		File destFile = new File(destFolder, srcFileName);

		logger.info("Attempting to move file from " + srcFolder + " to " + destFolder);

		if (srcFile.exists()) {
			InputStream in = null;
			OutputStream out = null;

			if (!destFolder.exists()) {
				destFolder.mkdir();
				logger.info("Directory created " + destFolder);
			}
			// Use bytes stream to support all file types
			in = new FileInputStream(srcFile);
			out = new FileOutputStream(destFile);
			byte[] buffer = new byte[1024];

			int length;
			// copy the file content in bytes
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}

			in.close();
			out.close();
			logger.info("File move from " + srcFolder + " to " + destFolder);
			srcFile.delete();
		}
	}
	public void moveSFTPFilesToArchived(String source, String destination, String file) {
		try {

			RecurringConfigFTP.ftpClient.rename(source+file, destination+file);
			logger.info("File move to archive : "+ source+file +" to : "+destination+file);

			logger.info("FTP MOVE SUCESSFULLY");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void disconnect() {
		try
		{
			if(RecurringConfigFTP.ftpClient.isConnected())
			{
				RecurringConfigFTP.ftpClient.logout();
				RecurringConfigFTP.ftpClient.disconnect();
				logger.info("SFTP DISCONNECTED");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static String getHostName() {
		return config.getProperty("ftp.ip");
	}

	public static String getPort() {
		return config.getProperty("ftp.port");
	}

	public static String getSFTPUser() {
		return config.getProperty("ftp.userid");
	}

	public static String getSFTPPasswword() {
		return config.getProperty("ftp.password");
	}

	public Properties getConfig() {
		return config;
	}

	public void setConfig(Properties config) {
		RecurringConfigFTP.config = config;
	}
	
	public static String getGPGScriptsFolder() {
		return config.getProperty("gpg.scripts.folder");
	}
	
	public static String getMerchantId() {
		return config.getProperty("recurring.merchantId");
	}

	public static String getAccountId() {
		return config.getProperty("recurring.accountId");
	}
	
	public static String getRecurringUploadAutoUrl(){
		return config.getProperty("recurring.upload.auto.url");
	}
	
	public static String getRecurringDownloadAutoUrl(){
		return config.getProperty("recurring.download.auto.url");
	}
	
    private static void showServerReply(org.apache.commons.net.ftp.FTPClient ftpClient) {
        String[] replies = ftpClient.getReplyStrings();
        if (replies != null && replies.length > 0) {
            for (String aReply : replies) {
                logger.info("SERVER: " + aReply);
            }
        }
    }
}
