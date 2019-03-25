package org.dms.DMS.controller;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dms.DMS.exceptions.DMSException;
import org.dms.DMS.model.UploadFileIndexFormVo;
import org.dms.DMS.model.UserVo;
import org.dms.DMS.service.UploadDocumentService;
import org.dms.DMS.service.UserService;
import org.dms.DMS.util.DMSBaseController;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Transactional
@EnableWebMvc
public class UploadDocumentController extends DMSBaseController{
	
	protected Log logger = LogFactory.getLog(this.getClass());
	@Resource(name = "userService")
	private UserService userService;
	
	@Resource(name="uploadDocumentService")
	private UploadDocumentService uploadDocumentService;
	
	@RequestMapping(value="uploadSave")
	public String uplpadDocumentdoctype(Model model, @ModelAttribute("uploadFileIndexForm") UploadFileIndexFormVo uploadFileIndexForm,boolean readonly, BindingResult result,final RedirectAttributes redirectAttributes) throws DMSException {
		try {
		logger.info("Enter UploadDocumentController()..page->" +uploadFileIndexForm.getCompnyId());
		UserVo usrDtls = getUserSession();
		logger.info("usrDtls.getLoginId():: " + usrDtls.getLoginId());
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		int docTpId=uploadFileIndexForm.getDocTpId();
		UserVo userV = userService.getUsrDtls(usrDtls.getLoginId());
		Integer uploadValue=uploadDocumentService.getUploadSave(uploadFileIndexForm,usrDtls.getUserId(),userV.getCompanyId());
      
		uploadDocumentService.getUploadDocumentId(uploadFileIndexForm, docTpId);
		uploadFileIndexForm.setUpdate("true");
		logger.info("Exit  uplpadDocumentdoctype( )");
		
		return "uploadDocument/uploadDocumentList";
		}catch (Exception e) {
         e.getMessage();
		}
		
		return "uploadDocument/uploadDocumentList";
	}
	
	@RequestMapping(value="uploadListFile") 
	public String  uploadDocumentlist(Model model, @ModelAttribute("uploadFileIndexForm") UploadFileIndexFormVo form,boolean readonly,@RequestParam(value = "docTpId") int docTpId,final RedirectAttributes redirectAttributes) throws DMSException {
		
		logger.info("Enter uploadDocumentlist().. .... ");
		UserVo usrDtls = getUserSession();
		logger.info("usrDtls.getLoginId():: " + usrDtls.getLoginId());
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		uploadDocumentService.getUploadDocumentId(form, docTpId);
		
		model.mergeAttributes(returnMap);
		logger.info("Exit  uploadDocumentlist( )");
		return "uploadDocument/uploadDocumentList";
//		return "redirect:/uploadDocumentList";
	}

}
