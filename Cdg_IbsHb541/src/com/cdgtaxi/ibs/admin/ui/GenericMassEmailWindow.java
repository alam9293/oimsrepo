package com.cdgtaxi.ibs.admin.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.mail.SendFailedException;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.zkforge.fckez.FCKeditor;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zk.ui.metainfo.EventHandler;
import org.zkoss.zk.ui.metainfo.ZScript;
import org.zkoss.zk.ui.util.ConditionImpl;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.SimpleConstraint;
import org.zkoss.zul.Textbox;

import com.cdgtaxi.ibs.common.ConfigurableConstants;
import com.cdgtaxi.ibs.common.model.email.FileAttachment;
import com.cdgtaxi.ibs.common.ui.CommonWindow;
import com.cdgtaxi.ibs.master.model.MstbGenericEmailTemplate;
import com.cdgtaxi.ibs.master.model.MstbMasterTable;
import com.cdgtaxi.ibs.util.EmailUtil;
import com.cdgtaxi.ibs.web.constraint.EmailConstraint;

@SuppressWarnings("unchecked")
public class GenericMassEmailWindow extends CommonWindow implements AfterCompose {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(GenericMassEmailWindow.class);

	private Label fromLabel;
	private Textbox toTB, ccTB, bccTB, subjectTB, nameTB;
	private Button toExcelUploadBtn, ccExcelUploadBtn, bccExcelUploadBtn;
	private Listbox templateLB, attachmentLB;
	private FCKeditor bodyTB;
	private Image deleteTemplateImage;
	private Div templateNameDiv;

	public void afterCompose() {
		Components.wireVariables(this, this);

		fromLabel.setValue(EmailUtil.from);

		Listitem newItem = new Listitem();
		newItem.setValue("NEW");
		newItem.setLabel("New");
		templateLB.appendChild(newItem);
		templateLB.setSelectedIndex(0);

		List<MstbGenericEmailTemplate> templates = this.businessHelper.getGenericBusiness().getAll(
				MstbGenericEmailTemplate.class);
		Collections.sort(templates, new Comparator<MstbGenericEmailTemplate>() {
			public int compare(MstbGenericEmailTemplate o1, MstbGenericEmailTemplate o2) {
				if (o1 != null && o2 != null)
					return o1.getName().compareTo(o2.getName());
				else
					return 0;
			}
		});

		for (MstbGenericEmailTemplate template : templates) {
			Listitem item = new Listitem();
			item.setValue(template);
			item.setLabel(template.getName());
			templateLB.appendChild(item);
		}
	}

	public void uploadExcelToField() throws InterruptedException {
		try {
			Media media = Fileupload.get("Please choose an Excel file...", "Upload email address via Excel");
			if (media != null) {
				if (!media.getContentType().equals("application/vnd.ms-excel"))
					throw new WrongValueException("Only Excel file can be accepted!");
				toTB.setValue(readExcelFileForEmailAddresses(media));
			}
		} catch (WrongValueException wve) {
			throw wve;
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void uploadExcelCcField() throws InterruptedException {
		try {
			Media media = Fileupload.get("Please choose an Excel file...", "Upload email address via Excel");
			if (media != null) {
				if (!media.getContentType().equals("application/vnd.ms-excel"))
					throw new WrongValueException("Only Excel file can be accepted!");
				ccTB.setValue(readExcelFileForEmailAddresses(media));
			}
		} catch (WrongValueException wve) {
			throw wve;
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void uploadExcelBccField() throws InterruptedException {
		try {
			Media media = Fileupload.get("Please choose an Excel file...", "Upload email address via Excel");
			if (media != null) {
				if (!media.getContentType().equals("application/vnd.ms-excel"))
					throw new WrongValueException("Only Excel file can be accepted!");
				bccTB.setValue(readExcelFileForEmailAddresses(media));
			}
		} catch (WrongValueException wve) {
			throw wve;
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	@SuppressWarnings("rawtypes")
	private String readExcelFileForEmailAddresses(Media media) throws IOException {
		String emailAddresses = "";

		POIFSFileSystem myFileSystem = new POIFSFileSystem(media.getStreamData());
		HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);
		HSSFSheet mySheet = myWorkBook.getSheetAt(0);

		Iterator rowIter = mySheet.rowIterator();
		while (rowIter.hasNext()) {
			HSSFRow myRow = (HSSFRow) rowIter.next();
			Iterator cellIter = myRow.cellIterator();
			while (cellIter.hasNext()) {
				HSSFCell myCell = (HSSFCell) cellIter.next();
				String emailAddress = myCell.toString();
				emailAddresses += emailAddress + ";";
			}
		}

		return emailAddresses;
	}

	private void validate() {
		String toRecipients = toTB.getValue();
		String ccRecipients = ccTB.getValue();
		String bccRecipients = bccTB.getValue();
		String subject = subjectTB.getValue();
		String body = bodyTB.getValue();

		if ((toRecipients == null || toRecipients.length() == 0)
				&& (ccRecipients == null || ccRecipients.length() == 0)
				&& (bccRecipients == null || bccRecipients.length() == 0))
			throw new WrongValueException(toTB, "Either TO, CC or BCC is mandatory!");
		if (body == null || body.length() == 0)
			throw new WrongValueException(bodyTB, "* Mandatory Field");

		// validation of the email format
		String[] toRecipientsArray = toRecipients.split(";");
		String[] ccRecipientsArray = ccRecipients.split(";");
		String[] bccRecipientsArray = bccRecipients.split(";");

		EmailConstraint emailConstraint = new EmailConstraint();
		List<String> toRecipientsWrongFormatEmails = new ArrayList<String>();
		List<String> ccRecipientsWrongFormatEmails = new ArrayList<String>();
		List<String> bccRecipientsWrongFormatEmails = new ArrayList<String>();

		for (String email : toRecipientsArray) {
			try {
				emailConstraint.validate(toTB, email.trim());
			} catch (WrongValueException wve) {
				toRecipientsWrongFormatEmails.add(email.trim());
			}
		}
		for (String email : ccRecipientsArray) {
			try {
				emailConstraint.validate(ccTB, email.trim());
			} catch (WrongValueException wve) {
				ccRecipientsWrongFormatEmails.add(email.trim());
			}
		}
		for (String email : bccRecipientsArray) {
			try {
				emailConstraint.validate(bccTB, email.trim());
			} catch (WrongValueException wve) {
				bccRecipientsWrongFormatEmails.add(email.trim());
			}
		}

		if (!toRecipientsWrongFormatEmails.isEmpty() || !ccRecipientsWrongFormatEmails.isEmpty()
				|| !bccRecipientsWrongFormatEmails.isEmpty()) {
			String errorMessage = "";
			if (!toRecipientsWrongFormatEmails.isEmpty()) {
				errorMessage += "\nTO Recipients - Invalid Email Format: ";
				for (String email : toRecipientsWrongFormatEmails)
					errorMessage += email + ";";
			}
			if (!ccRecipientsWrongFormatEmails.isEmpty()) {
				errorMessage += "\nCC Recipients - Invalid Email Format: ";
				for (String email : ccRecipientsWrongFormatEmails)
					errorMessage += email + ";";
			}
			if (!bccRecipientsWrongFormatEmails.isEmpty()) {
				errorMessage += "\nBCC Recipients - Invalid Email Format: ";
				for (String email : bccRecipientsWrongFormatEmails)
					errorMessage += email + ";";
			}
			throw new WrongValueException(errorMessage);
		}
	}

	public void sendWithoutSaving() throws InterruptedException {
		logger.info("");

		try {
			this.validate();

			String toRecipients = toTB.getValue();
			String ccRecipients = ccTB.getValue();
			String bccRecipients = bccTB.getValue();
			String subject = subjectTB.getValue();
			String body = bodyTB.getValue();

			this.displayProcessing();

			// If the TO recipients are empty, we forced it to undisclosed recipients.
			if (toRecipients == null || toRecipients.length() == 0)
				toRecipients = "undisclosed-recipients@cdgtaxi.com.sg";

			// preparing attachment list
			List<FileAttachment> fileAttachments = new ArrayList<FileAttachment>();
			for (Object object : attachmentLB.getItems()) {
				Listitem item = (Listitem) object;
				fileAttachments.add((FileAttachment) item.getValue());
			}

			EmailUtil.sendEmail(fromLabel.getValue(), toRecipients.split(";"), ccRecipients.split(";"),
					bccRecipients.split(";"), subject, body, fileAttachments);

			Messagebox.show("Mass email complete!");

			Executions.getCurrent().sendRedirect(""); // TO DO A REFRESH

		} catch (WrongValueException wve) {
			throw wve;
		} catch (HibernateOptimisticLockingFailureException holfe) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.HIBERNATE_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);

			Executions.getCurrent().sendRedirect(""); // TO DO A REFRESH

			holfe.printStackTrace();
		} catch (SendFailedException sfe) {
			sfe.printStackTrace();
			Messagebox.show(sfe.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void sendAndSave() throws InterruptedException {
		logger.info("");

		try {
			this.validate();

			Object value = templateLB.getSelectedItem().getValue();
			// Existing Template
			if (value instanceof MstbGenericEmailTemplate) {
				MstbGenericEmailTemplate existingTemplate = (MstbGenericEmailTemplate) value;
				existingTemplate.setContent(bodyTB.getValue());
				this.businessHelper.getGenericBusiness().update(existingTemplate,
						CommonWindow.getUserLoginIdAndDomain());
			}
			// New Template
			else {
				SimpleConstraint noEmptyConstraint = new SimpleConstraint(SimpleConstraint.NO_EMPTY,
						"* Mandatory Field");
				noEmptyConstraint.validate(nameTB, nameTB.getValue());

				MstbGenericEmailTemplate exampleResult = (MstbGenericEmailTemplate) this.businessHelper
						.getGenericBusiness().get(MstbGenericEmailTemplate.class, nameTB.getValue());
				if (exampleResult != null)
					throw new WrongValueException(nameTB, "Name has been taken!");

				String templateName = nameTB.getValue();
				MstbGenericEmailTemplate newTemplate = new MstbGenericEmailTemplate();
				newTemplate.setName(templateName);
				newTemplate.setContent(bodyTB.getValue());
				this.businessHelper.getGenericBusiness().save(newTemplate,
						CommonWindow.getUserLoginIdAndDomain());
			}

			// Since send without saving works the same to fireout email, so we just call the method
			this.sendWithoutSaving();
		} catch (WrongValueException wve) {
			throw wve;
		} catch (HibernateOptimisticLockingFailureException holfe) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.HIBERNATE_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);

			Executions.getCurrent().sendRedirect(""); // TO DO A REFRESH

			holfe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void deleteTemplate() throws InterruptedException {
		try {
			Listitem selectedItem = templateLB.getSelectedItem();
			MstbGenericEmailTemplate existingTemplate = (MstbGenericEmailTemplate) selectedItem.getValue();
			this.businessHelper.getGenericBusiness().delete(existingTemplate);

			templateLB.removeItemAt(selectedItem.getIndex());
			templateLB.setSelectedIndex(0);
			this.onSelectTemplate();

		} catch (WrongValueException wve) {
			throw wve;
		} catch (HibernateOptimisticLockingFailureException holfe) {
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.HIBERNATE_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);

			Executions.getCurrent().sendRedirect(""); // TO DO A REFRESH

			holfe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void onSelectTemplate() {
		Object value = templateLB.getSelectedItem().getValue();
		if (value instanceof MstbGenericEmailTemplate) {
			deleteTemplateImage.setVisible(true);
			templateNameDiv.setVisible(false);
			bodyTB.setValue(((MstbGenericEmailTemplate) value).getContent());
		} else {
			deleteTemplateImage.setVisible(false);
			templateNameDiv.setVisible(true);
			bodyTB.setValue("");
		}
	}

	public void attachFile() throws InterruptedException {
		try {
			Media media = Fileupload.get();

			if (media != null) {
				byte[] bytes;
				if (!media.getContentType().equals("text/plain")) {

					InputStream is = media.getStreamData();
					long length = is.available();
					bytes = new byte[(int) length];
					is.read(bytes);
				} else {
					bytes = media.getStringData().getBytes();
				}

				File file = File.createTempFile("IBS", "");
				FileOutputStream outputStream = new FileOutputStream(file);
				outputStream.write(bytes);
				logger.info("Temp File: " + file.getAbsolutePath());

				long fileSize = file.length();

				MstbMasterTable master = ConfigurableConstants.getMasterTable(
						ConfigurableConstants.GENERIC_MASS_EMAIL_MASTER_TYPE,
						ConfigurableConstants.GENERIC_MASS_EMAIL_FILE_SIZE_MASTER_CODE);

				long maxFileSize = Long.parseLong(master.getMasterValue());
				if (fileSize > maxFileSize)
					throw new WrongValueException("Attached file exceeds max file size " + maxFileSize
							+ " bytes!");

				this.appendFileAttachmentToListbox(new FileAttachment(media.getName(), file));

			}
		} catch (WrongValueException wve) {
			throw wve;
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show(com.cdgtaxi.ibs.common.NonConfigurableConstants.COMMON_ERROR_MESSAGE, "Error",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	private void appendFileAttachmentToListbox(FileAttachment attachment) {
		Listitem item = new Listitem();
		item.appendChild(newListcell(attachment.fileName));
		item.setValue(attachment);

		Listcell deleteImageCell = new Listcell();
		Image deleteImage = new Image("/images/delete.png");
		deleteImage.setStyle("cursor: pointer");
		// returns a listitem
		ZScript showInfo = ZScript
				.parseContent("genericMassEmailWindow.deleteRow(self.getParent().getParent())");
		showInfo.setLanguage("java");
		EventHandler pdEvent = new EventHandler(showInfo, ConditionImpl.getInstance(null, null));
		deleteImage.addEventHandler("onClick", pdEvent);
		deleteImageCell.appendChild(deleteImage);
		item.appendChild(deleteImageCell);

		attachmentLB.appendChild(item);
	}

	public void deleteRow(Listitem item) {
		attachmentLB.removeChild(item);
	}

	@Override
	public void refresh() {

	}
}
