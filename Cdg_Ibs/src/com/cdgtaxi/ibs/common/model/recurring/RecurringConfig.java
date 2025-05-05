package com.cdgtaxi.ibs.common.model.recurring;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class RecurringConfig {
	private static final Logger logger = Logger.getLogger(RecurringConfig.class);
	private static Properties config;
//	private static final String CONFIG_FILE_NAME = "recurring.properties";

	private static Session session = null;
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
	

	public boolean isActive() throws JSchException {
		try {
			if(session.isConnected()) {
				return true;
			}else {
				return false;
			}
		}catch(Exception e) {
			return false;
			//any error would mean session is not established
		}
	}
	public static void connect(String userid, String hostName, String password, int port) throws JSchException {
		
		try {
		JSch jsch = new JSch();
		session = jsch.getSession(userid, hostName, port);
		session.setPassword(password);
		// host checking should be enabled for security
		java.util.Properties config = new java.util.Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);
		session.connect();
		logger.info("SFTP CONNECTED = "+session.isConnected());
		
		}catch(Exception e ) {
			logger.info("Connection failed");
		}
	}

	public void upload(String source, String destination, String deleteAsc) throws JSchException, SftpException {
		try {
		Channel channel = session.openChannel("sftp");
		channel.connect();
		ChannelSftp sftpChannel = (ChannelSftp) channel;
		
		
		
		sftpChannel.put(source, destination);
		sftpChannel.exit();
		logger.info("SFTP UPLOAD SUCESSFULLY");
		
//		if(deleteAsc.equalsIgnoreCase("Y"))
//			new File(source).delete();
		
		}catch(Exception e) {
			e.printStackTrace();
			logger.info("SFTP UPLOAD FAILED");
		}
		
	}

	public void download(String source, String destination) throws JSchException, SftpException {
		try {
		Channel channel = session.openChannel("sftp");
		channel.connect();
		ChannelSftp sftpChannel = (ChannelSftp) channel;
		sftpChannel.get(source, destination);
		sftpChannel.exit();

		logger.info("SFTP DOWNLOAD SUCESSFULLY");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void downloadFilesInDirectory(String source, String destination, String filePrefix1, String filePrefix2) throws JSchException, SftpException {
		try {
		Channel channel = session.openChannel("sftp");
		channel.connect();
		ChannelSftp sftpChannel = (ChannelSftp) channel;
		int i = 0;
	    Vector<LsEntry> files = sftpChannel.ls(source);
	    for (LsEntry entry : files)
		    {
//		    System.out.println(entry.getFilename());
			if(!entry.getFilename().contains(".asc") && entry.getFilename().contains(filePrefix1) && entry.getFilename().contains(filePrefix2)) {
//				System.out.println(source+"/"+entry.getFilename());
//				System.out.println(destination+entry.getFilename());
				sftpChannel.get(source+entry.getFilename(), destination+entry.getFilename());
				logger.info("File downloaded : "+ source+"/"+entry.getFilename() +" to : "+destination+entry.getFilename());
				i++;
			}
	    }
		sftpChannel.exit();

		if(i > 0)
		{
			logger.info("SFTP DOWNLOAD SUCESSFULLY");
		}
		else
		{
			logger.info("SFTP no file Downloaded at : "+source + ". destination : "+destination+". looking for prefix : "+filePrefix1 +" & "+filePrefix2);
		}
		}catch(Exception e) {	
			e.printStackTrace();
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
	public void moveSFTPFilesToArchived(String source, String destination, String file) throws JSchException, SftpException {
		try {
		Channel channel = session.openChannel("sftp");
		channel.connect();
		ChannelSftp sftpChannel = (ChannelSftp) channel;
		//System.out.println(source+"/"+entry.getFilename());
//				System.out.println(destination+entry.getFilename());
		sftpChannel.rename(source+file, destination+file);
		
		sftpChannel.exit();

		logger.info("SFTP DOWNLOAD SUCESSFULLY");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void disconnect() {
		if (session != null) {
			session.disconnect();
			logger.info("SFTP DISCONNECTED");
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
		RecurringConfig.config = config;
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
}
