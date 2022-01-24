package com.webapp.ims.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.log.LoggerFactory;
import com.itextpdf.text.log.SysoLogger;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.security.BouncyCastleDigest;
import com.itextpdf.text.pdf.security.CertificateUtil;
import com.itextpdf.text.pdf.security.CrlClient;
import com.itextpdf.text.pdf.security.CrlClientOnline;
import com.itextpdf.text.pdf.security.DigestAlgorithms;
import com.itextpdf.text.pdf.security.ExternalDigest;
import com.itextpdf.text.pdf.security.ExternalSignature;
import com.itextpdf.text.pdf.security.MakeSignature;
import com.itextpdf.text.pdf.security.MakeSignature.CryptoStandard;
import com.itextpdf.text.pdf.security.OcspClient;
import com.itextpdf.text.pdf.security.OcspClientBouncyCastle;
import com.itextpdf.text.pdf.security.PrivateKeySignature;
import com.itextpdf.text.pdf.security.TSAClient;
import com.itextpdf.text.pdf.security.TSAClientBouncyCastle;
import com.webapp.ims.exception.FileNotFoundException;
import com.webapp.ims.model.DSCPdfUploadEntity;
import com.webapp.ims.model.DigitalSignatureBean;
import com.webapp.ims.model.DigitalSignatureEntity;
import com.webapp.ims.model.DscUserProfileEntity;
import com.webapp.ims.model.StoreSignatureEntity;
import com.webapp.ims.repository.DSCPdfUploadRepo;
import com.webapp.ims.repository.DigitalSignatureRepository;
import com.webapp.ims.repository.DscUserProfileRepo;
import com.webapp.ims.repository.StoreSignatureRepo;
import com.webapp.ims.service.DigitalSignatureService;

import sun.security.pkcs11.SunPKCS11;

@Service
@Transactional
public class DigitalSignatureServiceImpl implements DigitalSignatureService {

	@Autowired
	DSCPdfUploadRepo dSCPdfUploadRepo;

	@Autowired
	DigitalSignatureRepository digitalSignatureRepository;

	@Autowired
	StoreSignatureRepo storeSignatureRepo;

	@Autowired
	DscUserProfileRepo dscUserProfileRepo;

	@Override
	public DigitalSignatureBean createProfile(DigitalSignatureBean digitalSignatureBean) {
		DigitalSignatureEntity digitalSignatureEntity = new DigitalSignatureEntity();
		DigitalSignatureBean signatureBean = new DigitalSignatureBean();
		ModelMapper mapper = new ModelMapper();
		try {
			digitalSignatureEntity = mapper.map(digitalSignatureBean, DigitalSignatureEntity.class);
			digitalSignatureEntity = digitalSignatureRepository.save(digitalSignatureEntity);
			signatureBean = mapper.map(digitalSignatureEntity, DigitalSignatureBean.class);
			return signatureBean;
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return signatureBean;
	}

	@Override
	public void checkDigitalSignature() throws IOException, GeneralSecurityException, DocumentException {

		LoggerFactory.getInstance().setLogger(new SysoLogger());
		File tempDirectory = new File(System.getProperty("java.io.tmpdir"));
		int fileId = 108;
		FileInputStream fis = null;
		try {

			DSCPdfUploadEntity dSCPdfUploadEntity = dSCPdfUploadRepo.downloadFileById(fileId);
			byte[] initialArray = dSCPdfUploadEntity.getFile();
			InputStream dbPdfFile = new ByteArrayInputStream(initialArray);
			File tempFile = File.createTempFile("temp", null);

			FileOutputStream fos = new FileOutputStream(tempFile);
			fos.write(initialArray);
			File createNewPdf = new File(tempDirectory.getAbsolutePath() + "/temp.pdf");
			String pkcs11Config = "name=eToken\nlibrary=C:\\Windows\\System32\\eps2003csp11v2.dll";
			java.io.ByteArrayInputStream pkcs11ConfigStream = new java.io.ByteArrayInputStream(pkcs11Config.getBytes());
			SunPKCS11 providerPKCS11 = new SunPKCS11(pkcs11ConfigStream);
			java.security.Security.addProvider(providerPKCS11);
			String pin = "12345678";
			char[] pass = pin.toCharArray();

			Security.addProvider(providerPKCS11);
			KeyStore ks = KeyStore.getInstance("PKCS11");
			ks.load(null, pass);
			String alias = (String) ks.aliases().nextElement();
			PrivateKey pk = (PrivateKey) ks.getKey(alias, pass);
			Certificate[] chain = ks.getCertificateChain(alias);
			OcspClient ocspClient = new OcspClientBouncyCastle();
			TSAClient tsaClient = null;
			for (int i = 0; i < chain.length; i++) {
				X509Certificate cert = (X509Certificate) chain[i];
				String tsaUrl = CertificateUtil.getTSAURL(cert);
				if (tsaUrl != null) {
					tsaClient = new TSAClientBouncyCastle(tsaUrl);
					break;
				}
			}
			List<CrlClient> crlList = new ArrayList<CrlClient>();
			crlList.add(new CrlClientOnline(chain));

			sign(dbPdfFile, createNewPdf, chain, pk, DigestAlgorithms.SHA256, providerPKCS11.getName(),
					CryptoStandard.CMS, "HSM test", "Ghent", crlList, ocspClient, tsaClient, 0);
			File file = new File(tempDirectory.getAbsolutePath() + "/temp.pdf");

			PDDocument doc = PDDocument.load(new File(tempDirectory.getAbsolutePath() + "/temp.pdf"));
			PDPage page = doc.getPage(6);
			PDPageContentStream contentStream = new PDPageContentStream(doc, page);
			contentStream.beginText();
			contentStream.setFont(PDType1Font.TIMES_ROMAN, 16);
			contentStream.newLineAtOffset(25, 725);
			String text = "This is an example of adding text to a page in the pdf document."
					+ " we can add as many lines as we want like this using the draw string "
					+ "method of the ContentStream class";
			contentStream.showText(text);
			contentStream.endText();
			System.out.println("Content added");
			contentStream.close();
			doc.save(new File(tempDirectory.getAbsolutePath() + "/tempout.pdf"));
			doc.close();

			fis = new FileInputStream(file);
			byte[] data = new byte[(int) file.length()];
			fis.read(data);

			if (data != null) {
				dSCPdfUploadEntity.setFile(data);
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				Date date = new Date();
				System.out.println(formatter.format(date));
				String fileLength = (double) file.length() / (1024 * 1024) + " mb";
				dSCPdfUploadEntity.setFileSize(fileLength);
				dSCPdfUploadEntity.setCreatedDate(formatter.format(date));
				dSCPdfUploadRepo.save(dSCPdfUploadEntity);
			}

			// sign1(userFile_signed, userFile, chain, pk, DigestAlgorithms.SHA256,
			// providerPKCS11.getName(),
			// CryptoStandard.CMS, "HSM test", "Ghent", crlList, ocspClient, tsaClient, 0);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// tempDirectory = new File(System.getProperty("java.io.tmpdir"));
			// if (fileForDelete.delete()) {
//				System.out.println("File deleted successfully");
//			} else {
//				System.out.println("Failed to delete the file");
//			}

			fis.close();
		}

	}

	public void sign(InputStream targetStream, File fos, Certificate[] chain, PrivateKey pk, String digestAlgorithm,
			String provider, CryptoStandard subfilter, String reason, String location, Collection<CrlClient> crlList,
			OcspClient ocspClient, TSAClient tsaClient, int estimatedSize)
			throws GeneralSecurityException, IOException, DocumentException {
		File tempDirectory = new File(System.getProperty("java.io.tmpdir"));
		try {
			String signature = Integer.toString((int) (new Date().getTime()));
			PdfReader reader = new PdfReader(targetStream);
			FileOutputStream os = new FileOutputStream(fos);
			PdfStamper stamper = PdfStamper.createSignature(reader, os, '\0');
			PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
			appearance.setReason("Incentive" + signature);
			appearance.setLocation("Lucknow(U.P.)");

			Rectangle Rec = null; // new Rectangle(400, 748, 600, 100);
			String desg = "JMD";
			switch (desg) {
			case "PICKUPTeam":
				Rec = new Rectangle(400, 748, 500, 100);
			case "JMD":
				Rec = new Rectangle(36, 748, 400, 400);
				break;
			case "MD":
				Rec = new Rectangle(36, 500, 600, 100);
				break;
			case "ACS":
				Rec = new Rectangle(400, 0, 600, 100);
				break;
			default:
				// code block
			}
			// appearance.setVisibleSignature(new Rectangle(36, 748, 144, 780), 6, "sig");
			// appearance.setVisibleSignature(new Rectangle(36, 0, 600,
			// 100),reader.getNumberOfPages() , signature);

			// appearance.setVisibleSignature(new Rectangle(400, 748, 600, 100),
			// reader.getNumberOfPages(), signature);

			appearance.setVisibleSignature(Rec, reader.getNumberOfPages(), signature);
			ExternalSignature pks = new PrivateKeySignature(pk, digestAlgorithm, provider);
			ExternalDigest digest = new BouncyCastleDigest();
			MakeSignature.signDetached(appearance, digest, pks, chain, crlList, ocspClient, tsaClient, estimatedSize,
					subfilter);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {

		}

	}

	public void sign1(String src, String dest, Certificate[] chain, PrivateKey pk, String digestAlgorithm,
			String provider, CryptoStandard subfilter, String reason, String location, Collection<CrlClient> crlList,
			OcspClient ocspClient, TSAClient tsaClient, int estimatedSize)
			throws GeneralSecurityException, IOException, DocumentException {
		String src1 = "E:/results/report.pdf";
		String dest1 = "E:/results/report_signed.pdf";
		// Creating the reader and the stamper
		PdfReader reader = new PdfReader(src1);
		FileOutputStream os = new FileOutputStream(dest1);
		PdfStamper stamper = PdfStamper.createSignature(reader, os, '\0');
		// Creating the appearance
		PdfSignatureAppearance appearance = stamper.getSignatureAppearance();

		appearance.setReason("Second Officer Singed");
		appearance.setLocation("Lucknow(U.P.)");
		appearance.setSignatureCreator(dest1);
		// appearance.setVisibleSignature(new Rectangle(36, 748, 144, 780), 6, "sig");
		appearance.setVisibleSignature(new Rectangle(400, 0, 600, 100), reader.getNumberOfPages(), "sig11");
		// Creating the signature
		ExternalSignature pks = new PrivateKeySignature(pk, digestAlgorithm, provider);
		// System.out.println("pks" + pks);
		ExternalDigest digest = new BouncyCastleDigest();
		MakeSignature.signDetached(appearance, digest, pks, chain, crlList, ocspClient, tsaClient, estimatedSize,
				subfilter);
	}

	@Override
	public byte[] checkDigitalSingleItem(String applicantId, Model model)
			throws IOException, GeneralSecurityException, DocumentException {

		LoggerFactory.getInstance().setLogger(new SysoLogger());
		File tempDirectory = new File(System.getProperty("java.io.tmpdir"));
		int fileId = 88;
		try {

			byte[] initialArray = null;
			List<StoreSignatureEntity> storeSignatureEntityList = storeSignatureRepo.findSignatureByAppId(applicantId);
			StoreSignatureEntity storeSignatureEntity = new StoreSignatureEntity();
			if (storeSignatureEntityList.size() > 0) {
				storeSignatureEntity = storeSignatureEntityList.get(0);
			}
			DSCPdfUploadEntity dSCPdfUploadEntity = dSCPdfUploadRepo.downloadFileById(fileId);

// 			if (dSCPdfUploadEntity != null) {
//				initialArray = dSCPdfUploadEntity.getFile();
//			}
			// InputStream targetStream = new ByteArrayInputStream(initialArray);
//			File tempFile = File.createTempFile("te mp", null);
//			FileOutputStream fos = new FileOutputStream(tempFile);
//			fos.write(initialArray);

//			File fileWithAbsolutePath = new File(tempDirectory.getAbsolutePath() + "/temp.pdf");
//			String pkcs11Config = "name=eToken\nlibrary=C:\\Windows\\System32\\eps2003csp11v2.dll";

			URL res = getClass().getClassLoader().getResource("eps2003csp11v2.dll");
			File dllFile = Paths.get(res.toURI()).toFile();
			String absolutePath = "name=eToken\nlibrary=";
			absolutePath += dllFile.getAbsolutePath();

			java.io.ByteArrayInputStream pkcs11ConfigStream = new java.io.ByteArrayInputStream(absolutePath.getBytes());
			@SuppressWarnings("restriction")
			SunPKCS11 providerPKCS11 = new SunPKCS11(pkcs11ConfigStream);
			Security.addProvider(providerPKCS11);
			String pin = "12345678";
			char[] pass = pin.toCharArray();

			 
			KeyStore ks = KeyStore.getInstance("PKCS11",providerPKCS11);
			ks.load(null, pass);
			String alias = (String) ks.aliases().nextElement();
			PrivateKey pk = (PrivateKey) ks.getKey(alias, pass);
			Certificate[] chain = ks.getCertificateChain(alias);
			OcspClient ocspClient = new OcspClientBouncyCastle();

			/************* Create binary data *****************/
			// KeyStore keyStore = KeyStore.getInstance("PKCS12");
			// keyStore.load(new FileInputStream("receiver_keytore.p12"), "changeit");
			Certificate certificate = ks.getCertificate(alias);
			PublicKey publicKey = certificate.getPublicKey();
			byte[] messageBytes = Files.readAllBytes(Paths.get("message.txt"));

//			String str = "hello Pramod";
//			byte[] messageBytes = str.getBytes();
//			MessageDigest md = MessageDigest.getInstance("SHA-256");
//			byte[] messageHash = md.digest(messageBytes);
//
//			Cipher cipher = Cipher.getInstance("RSA");
//			cipher.init(Cipher.ENCRYPT_MODE, pk);
////			byte[] utf8 = str.getBytes("UTF8");
//
//			// Encrypt
////			byte[] enc = cipher.doFinal(utf8);
//
//			// Encode bytes to base64 to get a string
//			byte[] digitalSignature = cipher.doFinal(messageHash);
//
//			System.out.println("digitalSignature.toString()" + digitalSignature.toString());
////			String s = new sun.misc.BASE64Encoder().encode(enc);
//
//			Files.write(Paths.get("digital_signature_1"), digitalSignature);
//
//			/**** Verifying Signature ************/
//			byte[] encryptedMessageHash = Files.readAllBytes(Paths.get("digital_signature_1"));
//			 publicKey = certificate.getPublicKey();
//			Cipher cipher1 = Cipher.getInstance("RSA");
//
//			cipher1.init(Cipher.DECRYPT_MODE, publicKey);
//
//			byte[] decryptedMessageHash = cipher1.doFinal(encryptedMessageHash);
//
////			String str1 = "hello Pramod";
//			byte[] messageBytes1 =	 Files.readAllBytes(Paths.get("message.txt"));
//
//			MessageDigest md1 = MessageDigest.getInstance("SHA-256");
//
//			byte[] newMessageHash = md1.digest(messageBytes1);
//
//			boolean isCorrect = Arrays.equals(decryptedMessageHash, newMessageHash);

			// Get bytes, most important part
			// Decode base64 to get bytes
//			byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(s);

			// Decrypt
//			byte[] utf81 = cipher1.doFinal(dec);

			// Decode using utf-8
//			System.out.println(" ==================================" + new String(utf81, "UTF8"));

//			if (isCorrect) {
//				System.out.println("file is same");
//			} else {
//				System.out.println("file is not Same");
//			}

			/*************/
//		
//			Signature signature = Signature.getInstance("SHA256withRSA");
//			signature.initSign(privateKey);
//			byte[] messageBytes = Files.readAllBytes(Paths.get("message.txt"));
//
//			signature.update(messageBytes);
//			byte[] digitalSignature = signature.sign();
//			Files.write(Paths.get("digital_signature_2"), digitalSignature);
			/***********************/

			TSAClient tsaClient = null;
			// for (int i = 0; i < chain.length; i++) {
			CertificateFactory certFactory = CertificateFactory.getInstance("X.509");

			InputStream in = new ByteArrayInputStream(chain[0].getEncoded());

			X509Certificate cert = (X509Certificate) certFactory.generateCertificate(in);
			// X509Certificate cert = (X509Certificate) chain[i];

			String tsaUrl = CertificateUtil.getTSAURL(cert);
			byte[] singName = cert.getSignature();
//		 	String str =Base64.getEncoder().encodeToString(singName);
//		 		 	System.out.println("str" + str);
//		 	System.out.println("cert.getSigAlgName();" + cert.getSigAlgName());

			String certArr = cert.getSubjectDN().toString();
			System.out.println("cert.getSigAlgName();" + certArr);

			String[] certInfo = certArr.split(",");

			String str = certInfo[1];
			System.out.println(str);
			String[] strArr = str.split("=");

			if (tsaUrl != null) {
				tsaClient = new TSAClientBouncyCastle(tsaUrl);
				// break;
			}
			// }
			List<CrlClient> crlList = new ArrayList<CrlClient>();
			crlList.add(new CrlClientOnline(chain));

//			sign(targetStream, fileWithAbsolutePath, chain, pk, DigestAlgorithms.SHA256, providerPKCS11.getName(),
//					CryptoStandard.CMS, "HSM test", "Ghent", crlList, ocspClient, tsaClient, 0);

//			File file  = new File(tempDirectory.getAbsolutePath() + "/temp.pdf");
//			FileInputStream fis = new FileInputStream(file);
//			byte[] data = new byte[(int) file.length()];
//			fis.read(data);

			byte[] signature = chain[0].getEncoded(); // IOUtils.toByteArray(in);
			System.out.println(signature);
			String name = certInfo[0];
			name = name.substring(3, name.length());
			if (signature != null) {
				storeSignatureEntity.setAppId(applicantId);
				storeSignatureEntity.setName(name);
				storeSignatureEntity.setSignature(signature);
				storeSignatureEntity.setPriority(1);
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				Date date = new Date();
				System.out.println(formatter.format(date));
				System.out.println(strArr[1]);
				if (strArr[1] != null) {
					DscUserProfileEntity dscUserProfileEntity = dscUserProfileRepo.searchUserBySerialNo(strArr[1]);

					String designation = "";
					if (dscUserProfileEntity != null) {
						designation = dscUserProfileEntity.getDesignation();

						switch (designation) {
						case "":
//						storeSignatureEntity.setPickupsignature("Digitally signed by " + name + "\r\n" + "Date : "
//								+ formatter.format(date) + "\r\n" + "Location :" + "Lucknow");
							break;
						case "JMD":
							storeSignatureEntity.setJmdsignature("Digitally signed by " + name + "\r\n" + "Date : "
									+ formatter.format(date) + "\r\n" + "Location :" + "Lucknow");
							break;
						case "MD":
							storeSignatureEntity.setMdsignature("Digitally signed by " + name + "\r\n" + "Date : "
									+ formatter.format(date) + "\r\n" + "Location :" + "Lucknow");
							break;
						}
					}
				}
				storeSignatureEntity = storeSignatureRepo.save(storeSignatureEntity);

			}

//			if (data != null) {
//				dSCPdfUploadEntity.setFile(data);
//				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//				Date date = new Date();
//				System.out.println(formatter.format(date));
//				String fileLength = (double) file.length() / (1024 * 1024) + " mb";
//				dSCPdfUploadEntity.setFileSize(fileLength);
//				dSCPdfUploadEntity.setCreatedDate(formatter.format(date));
//				dSCPdfUploadRepo.save(dSCPdfUploadEntity);
//			}

			// sign1(userFile_signed, userFile, chain, pk, DigestAlgorithms.SHA256,
			// providerPKCS11.getName(),
			// CryptoStandard.CMS, "HSM test", "Ghent", crlList, ocspClient, tsaClient, 0);
			return signature;
		} catch (Exception ex) {
			model.addAttribute("message", "Please Insert Digital Signature USB key.");

			ex.printStackTrace();
		} finally {
			// tempDirectory = new File(System.getProperty("java.io.tmpdir"));
			// if (fileForDelete.delete()) {
//				System.out.println("File deleted successfully");
//			} else {
//				System.out.println("Failed to delete the file");
//			}
		}
		return null;

	}

	@Override
	public void cratePdf() throws java.io.FileNotFoundException {

		try {

			// 1. create the document page size: A4, margins: left:20 right:20 top:40
			// bottom:40
			Document document = new Document(); // (PageSize.A4, 20f,20f,40f,40);

			// for custom pagesize
			// Rectangle pagesize = new Rectangle(216f, 720f);

			// 2. get PdfWriter
			PdfWriter.getInstance(document, new FileOutputStream("pagesettings.pdf"));
			// PdfWriter.getInstance(document, getAbsolutePath() + "/pagesettings.pdf");
			// 3. open the document
			document.open();
			// 4. add the content
			document.add(new Paragraph("Hello World! pramod"));

			// 5. close the document
			document.close();

			System.out.println("Document created!");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

}
