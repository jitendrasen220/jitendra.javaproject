package org.dms.DMS.ajaxController;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.dms.DMS.dao.UploadDocumentDao;
import org.dms.DMS.entity.DocumentRights;
import org.dms.DMS.model.DocTypeVo;
import org.dms.DMS.model.DocumentVo;
import org.dms.DMS.model.MailAttachVo;
import org.dms.DMS.model.UserVo;
import org.dms.DMS.service.DocumentService;
import org.dms.DMS.util.ApplicationContextHelper;
import org.dms.DMS.util.Constants;
import org.dms.DMS.util.DMSBaseController;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Transactional
@EnableWebMvc
public class DocumentAjaxController extends DMSBaseController{
	private static final Logger logger = Logger.getLogger(DocumentAjaxController.class);
	DocumentService documentService = null;
	UploadDocumentDao uploadDocumentDao = null;
	public List<DocTypeVo> getDocTypeByDept(Integer deptId) {
		logger.info("Entering getDocTypeByDept() ");
		
		List<DocTypeVo> docTypeList =  new ArrayList<>();
		try {
			documentService = ApplicationContextHelper.getInstance().getManagedBean("documentService", DocumentService.class);
			docTypeList = documentService.getDocTypeByDept(deptId);
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION , e);
		}
		logger.info("Entering getDocTypeByDept() ");
		return docTypeList;
	}
	
	public Boolean getPrntCheckSts(Integer docType) {
		logger.info("Entering into getPrntCheckSts() ");
		
		boolean rit = false;
		UserVo usrvo = getUserSession();
		if (usrvo.getAdminPower()==1 || usrvo.getAdminPower()==2) {
			rit = true;
		} else {
			uploadDocumentDao = ApplicationContextHelper.getInstance().getManagedBean("uploadDocumentDao", UploadDocumentDao.class);
			List<DocumentRights> rightsList = uploadDocumentDao.uploadDocumentByUsrId(usrvo.getUserId());
			for(DocumentRights rightsListSub : rightsList) {
				if (rightsListSub.getPrint() == 1 && rightsListSub.getDocTypeId().getDocTypeId() == docType) {
					rit = true;
				}
			}
		}
		logger.info("Exit from getPrntCheckSts() ");
		return rit;
	}
	
	public Boolean getDwdCheckSts(Integer docType) {
		logger.info("Entering into getPrntCheckSts() ");
		
		boolean rit = false;
		UserVo usrvo = getUserSession();
		if (usrvo.getAdminPower()==1 || usrvo.getAdminPower()==2) {
			rit = true;
		} else {
			uploadDocumentDao = ApplicationContextHelper.getInstance().getManagedBean("uploadDocumentDao", UploadDocumentDao.class);
			List<DocumentRights> rightsList = uploadDocumentDao.uploadDocumentByUsrId(usrvo.getUserId());
			for(DocumentRights rightsListSub : rightsList) {
				if (rightsListSub.getDownload() == 1 && rightsListSub.getDocTypeId().getDocTypeId() == docType) {
					rit = true;
				}
			}
		}
		logger.info("Exit from getPrntCheckSts() ");
		return rit;
	}
	
	public Boolean getPreviewCheckSts(Integer docType) {
		logger.info("Entering into getPrntCheckSts() ");
		
		boolean rit = false;
		UserVo usrvo = getUserSession();
		if (usrvo.getAdminPower()==1 || usrvo.getAdminPower()==2) {
			rit = true;
		} else {
			uploadDocumentDao = ApplicationContextHelper.getInstance().getManagedBean("uploadDocumentDao", UploadDocumentDao.class);
			List<DocumentRights> rightsList = uploadDocumentDao.uploadDocumentByUsrId(usrvo.getUserId());
			for(DocumentRights rightsListSub : rightsList) {
				if (rightsListSub.getCreator() == 1 && rightsListSub.getDocTypeId().getDocTypeId() == docType) {
					rit = true;
				}
			}
		}
		logger.info("Exit from getPrntCheckSts() ");
		return rit;
	}
	
public Boolean getMailCheckSts(String toEmail,String ccEmail,String subject,String message,String updFileNameMail,Integer mailIdDocumentId) {
		logger.info("Entering into getPrntCheckSts() ");
		
 		boolean rit = false;
		UserVo usrvo = getUserSession();
        if(usrvo.getAdminPower()==1 || usrvo.getAdminPower()==2) {
			System.out.println("getAdminPower()==1");
			MailAttachVo attachVo=new MailAttachVo();
			attachVo.setToEmail(toEmail);
			attachVo.setCcEmail(ccEmail);
			attachVo.setSubject(subject);
			attachVo.setMessage(message);
//			attachVo.setUpdFileNameMail(updFileNameMail);
			System.out.println("emailId :"+toEmail +"  , ccemailId : "+ccEmail+"   ,  subjectId  "+ subject+"   ,   messageId  : "+message+"  ,updFileNameMail  : "+updFileNameMail +"   ,   mailIdDocumentId    :"+mailIdDocumentId);
			documentService = ApplicationContextHelper.getInstance().getManagedBean("documentService", DocumentService.class);
			String sendmail= documentService.sendemailId(attachVo);
			
			System.out.println("sendmail  :"+sendmail);
//			System.out.println("emailId :"+toEmail +"  , ccemailId : "+ccEmail+"   ,  subjectId  "+ subject+"   ,   messageId  : "+message+"  ,updFileNameMail  : "+updFileNameMail +"   ,   mailIdDocumentId    :"+mailIdDocumentId);
			
			rit = true;
		} else {
			uploadDocumentDao = ApplicationContextHelper.getInstance().getManagedBean("uploadDocumentDao", UploadDocumentDao.class);
			List<DocumentRights> rightsList = uploadDocumentDao.uploadDocumentByUsrId(usrvo.getUserId());
			for(DocumentRights rightsListSub : rightsList) {
				if (rightsListSub.getVerifier() == 1) {
					System.out.println("emailId :"+toEmail +"  , ccemailId : "+ccEmail+"   ,  subjectId  "+ subject+"   ,   messageId  : "+message+"  ,updFileNameMail  : "+updFileNameMail +"   ,   mailIdDocumentId    :"+mailIdDocumentId);
					rit = true;
				}
			}
		}
		logger.info("Exit from getPrntCheckSts() ");
		return rit;
	}
/*	public Boolean getShareCheckSts(Integer shareId,Integer dropdown,String updFileName,Integer documentId) {
		logger.info("Entering into getPrntCheckSts() ");
		
		boolean rit = false;
		UserVo usrvo = getUserSession();
		if (usrvo.getAdminPower()==1 || usrvo.getAdminPower()==2) {
			
			System.out.println("getAdminPower ");
			
			
			System.out.println("shareId  :"+shareId +" dropdown "+dropdown +"   updFileName "+updFileName +"  documentId  "+documentId);
			
			rit = true;
		} else {
			uploadDocumentDao = ApplicationContextHelper.getInstance().getManagedBean("uploadDocumentDao", UploadDocumentDao.class);
			List<DocumentRights> rightsList = uploadDocumentDao.uploadDocumentByUsrId(usrvo.getUserId());
			for(DocumentRights rightsListSub : rightsList) {
				if (rightsListSub.getPreview() == 1) {
					
					System.out.println("shareId  :"+shareId +" dropdown "+dropdown +"   updFileName "+updFileName +"  documentId  "+documentId);
					
					System.out.println(uploadDocumentDao);
					
					rit = true;
				}
			}
		}
		logger.info("Exit from getPrntCheckSts() ");
		return rit;
	}*/
	
	public DocumentVo getShareCheckSts(Integer shareId,Integer dropdown,String updFileName,Integer documentId,HttpServletRequest request) {
		String url=null;
		boolean rit = false;
		UserVo usrvo = getAjaxUserSession();
		System.out.println(usrvo.toString());
		boolean rit1 = false;
		
		DocumentVo documentVo=new DocumentVo();
		/*documentVo.setShareId(shareId);
		documentVo.setDropdown(dropdown);
		documentVo.setUpdFileName(updFileName);
		documentVo.setDocumentId(documentId);
		documentVo.setRit(rit);
		*/
		
		try {
		if (usrvo.getAdminPower()==1 || usrvo.getAdminPower()==2) {
			documentService = ApplicationContextHelper.getInstance().getManagedBean("documentService", DocumentService.class);			
			url="/doumentProcessDownloadAttch?documentId="+documentId+"&updFileName="+updFileName;
			rit1=true;
			documentVo.setRit(rit1);
			documentVo.setUrl(url);
		} else {
			
			uploadDocumentDao = ApplicationContextHelper.getInstance().getManagedBean("uploadDocumentDao", UploadDocumentDao.class);
			List<DocumentRights> rightsList = uploadDocumentDao.uploadDocumentByUsrId(usrvo.getUserId());
			for(DocumentRights rightsListSub : rightsList) {
				if (rightsListSub.getPreview() == 1) {
					url="/doumentProcessDownloadAttch?documentId="+documentId+"&updFileName="+updFileName;
					rit1=true;
					documentVo.setRit(rit1);
					documentVo.setUrl(url);
				}
			}
		}
		}catch (Exception e) {
			System.out.println("  "+e.getMessage());
		}
		
		return documentVo;
	
	
	
	}
	
}