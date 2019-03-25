package org.dms.DMS.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dms.DMS.dao.UploadDocumentDao;
import org.dms.DMS.entity.Document;
import org.dms.DMS.entity.DocumentRights;
import org.dms.DMS.entity.Users;
import org.dms.DMS.exceptions.DMSException;
import org.dms.DMS.model.DeptVo;
import org.dms.DMS.model.DocTypeVo;
import org.dms.DMS.model.DocumentVo;
import org.dms.DMS.model.MailAttachVo;
import org.dms.DMS.model.PaginationResult;
import org.dms.DMS.model.UserVo;
import org.dms.DMS.service.CityService;
import org.dms.DMS.service.DeptService;
import org.dms.DMS.service.DocumentService;
import org.dms.DMS.service.FileService;
import org.dms.DMS.service.UserService;
import org.dms.DMS.util.Constants;
import org.dms.DMS.util.Constants.ErrorCodes;
import org.dms.DMS.util.DMSBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.itextpdf.text.pdf.codec.Base64;

@Controller
@Transactional
@EnableWebMvc
public class DocumentController extends DMSBaseController  {
	protected Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	
	@Resource(name = "deptService")
	private DeptService deptService;
	
	@Resource(name = "documentService")
	private DocumentService documentService;
	
	@Resource(name = "fileService")
	private FileService fileService;
	
	@Autowired
	@Resource(name = "userService")
	private UserService userService;
	
	@Autowired
	@Resource(name = "cityService")
	private CityService cityService;
	
	
	
	@RequestMapping(value = { "/documentList" })
	public String getDeptDtlsWithPagination(Model model, 
		@RequestParam(value = "name", defaultValue = "") String likeName,
		@RequestParam(value = "page", defaultValue = "1") int page) throws DMSException {
		logger.info("Enter getDeptDtlsWithPagination()..page->" + page);
		final int maxResult = 10;
		final int maxNavigationPage = 10;
		UserVo usrVo = getUserSession();
		Users users = userService.getUsrDtlsById(usrVo.getUserId());
		usrVo.setAdminPower(users.getAdminPower());
		usrVo.setCompanyId(users.getCompany().getCompanyId());
		
		DeptVo deptVo = new DeptVo();
		deptVo.setDepartmentId(users.getDepartment().getDepartmentId());
		usrVo.setDepartmentVo(deptVo);
		
		DocumentVo documentVo = new DocumentVo();
		PaginationResult<Document> result = documentService.getDoumentWithPagination(page, maxResult, maxNavigationPage, usrVo, documentVo);
		model.addAttribute("paginationDocs", result);
		model.addAttribute("documentVo", new DocumentVo());
		model.addAttribute("power", usrVo.getAdminPower());
		
		if (usrVo.getAdminPower() == 2) {
			model.addAttribute("companyList", cityService.getAllCompany());
		} else if (usrVo.getAdminPower() == 1) {
			List<DeptVo> deptList = cityService.getDeptIdBycompanyId(usrVo.getCompanyId());
			model.addAttribute("departmentList", deptList);
		} else {
			/*List<DocTypeVo> docTypeList = documentService.getDocTypeByDept(usrVo.getDepartmentVo().getDepartmentId());
			model.addAttribute("docTypeList", docTypeList);*/
			List<DeptVo> deptList = cityService.getDeptIdBycompanyId(usrVo.getCompanyId());
			model.addAttribute("departmentList", deptList);
		}
		
		logger.info("Exit doumentProcessSearch()..page->" + page);
		return "documents/documentsSearch";
	}
	
	@RequestMapping(value = { "/documentSearch" })
	public String documentSearch(Model model, @ModelAttribute("documentVo") DocumentVo vo,
			@RequestParam(value = "page", defaultValue = "1") int page) {
		logger.info("Enter documentSearch()..page->" + page);

		final int maxResult = 10;
		final int maxNavigationPage = 10;

		UserVo usrVo = getUserSession();
		logger.info("usrDtls.getLoginId():: " + usrVo.getLoginId());
		PaginationResult<Document> result = null;
		
		result = documentService.getDoumentWithPagination(page, maxResult, maxNavigationPage, usrVo, vo);
		
		model.addAttribute("paginationDocs", result);
		model.addAttribute("documentVo", vo);
		model.addAttribute("search", 1);
		model.addAttribute("power", usrVo.getAdminPower());
		
		if (usrVo.getAdminPower() == 2) {
			model.addAttribute("companyList", cityService.getAllCompany());
		} else if (usrVo.getAdminPower() == 1) {
			List<DeptVo> deptList = cityService.getDeptIdBycompanyId(usrVo.getCompanyId());
			model.addAttribute("departmentList", deptList);
		} else {
			/*List<DocTypeVo> docTypeList = documentService.getDocTypeByDept(usrVo.getDepartmentVo().getDepartmentId());
			model.addAttribute("docTypeList", docTypeList);*/
			List<DeptVo> deptList = cityService.getDeptIdBycompanyId(usrVo.getCompanyId());
			model.addAttribute("departmentList", deptList);
		}
		logger.info("Exit documentSearch()..page->" + page);
		return "documents/documentsSearch";

	}
	
	
	@RequestMapping(value = { "/doumentProcessViewPdf" })
	public String doumentProcessViewPdf(HttpServletResponse response, Model model, @ModelAttribute("documentVo") DocumentVo documentVo,
			BindingResult result) {
		logger.info("Enter doumentProcessViewPdf()..");

		try {
				UserVo usrvo = getUserSession();
				String documentIdStr = getRequest().getParameter("documentId");
				Integer documentId = Integer.parseInt(documentIdStr);

				String updFileName = getRequest().getParameter("updFileName");
				
				if (documentId != null) {
					
					//Users users = userService.getUsrDtlsById(usrvo.getUserId());
					/*List<DocumentRights> rightsList = uploadDocumentDao.uploadDocumentByUsrId(usrvo.getUserId());
					
					for(DocumentRights rightsListSub : rightsList) {
						if (rightsListSub.getPreview() == 1) {*/
							documentVo.setDocumentId(documentId);
							
							Map<String, Object> returnMap = model.asMap();
							returnMap = documentService.getDocumentDtlsByVo(documentVo);
							
							model.addAttribute("documentVo", documentVo);
							
							model.addAttribute("departmentList", deptService.getAllDept());
							model.mergeAttributes(returnMap);
							
							System.out.println(documentId);
							System.out.println(updFileName);
						}
					
				
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("An Exception occurred while loading existing printPymtApproved screen", e);
			result.rejectValue(Constants.STRING_ERROR, ErrorCodes.GENERAL_EXCEPTION_KEY);
		}

		logger.info("Exit doumentProcessViewPdf()..");
		return "documents/documentView";
	}
	
	@RequestMapping(value = { "/doumentProcessViewAttch" })
	@ResponseBody
	public String doumentProcessViewAttch(HttpServletResponse response, Model model, @ModelAttribute("documentVo") DocumentVo documentVo, BindingResult result) {
		logger.info("Enter doumentProcessViewAttch()..");
		
		
		/*UserVo usrvo = getUserSession();*/
		//String fileNm = (String) model.asMap().get("fileNm");
		String fileNm = getRequest().getParameter("updFileName");
		String attchDir = StringUtils.EMPTY;
		attchDir = Constants.FolderStructure.UPLOAD_DOC_DIR;;
		
		/*if (usrvo == null || StringUtils.isBlank(usrvo.getLoginId())) {
			result.rejectValue(Constants.STRING_ERROR, ErrorCodes.GENERAL_EXCEPTION_KEY);
		} else {*/
			//fileService.getUplDocument(response, fileNm, attchDir, result);
		/*}*/
		
		String attchPath = fileService.viewUplDocument(response, fileNm, attchDir, result);
		
		logger.info("Exit doumentProcessViewAttch()..");
		return attchPath;
	}
	
	@RequestMapping(value = { "/doumentProcessPrintAttch" })
	@ResponseBody
	public String doumentProcessPrintAttch(HttpServletResponse response, Model model, @ModelAttribute("documentVo") DocumentVo documentVo, BindingResult result) {
		logger.info("Enter doumentProcessPrintAttch()..");
		
		
		/*UserVo usrvo = getUserSession();*/
		//String fileNm = (String) model.asMap().get("fileNm");
		String fileNm = getRequest().getParameter("updFileName");
		String attchDir = StringUtils.EMPTY;
		attchDir = Constants.FolderStructure.UPLOAD_DOC_DIR;;
		
		/*if (usrvo == null || StringUtils.isBlank(usrvo.getLoginId())) {
			result.rejectValue(Constants.STRING_ERROR, ErrorCodes.GENERAL_EXCEPTION_KEY);
		} else {*/
			//fileService.getUplDocument(response, fileNm, attchDir, result);
		/*}*/
		
		String attchPath = fileService.getUplDocument(response, fileNm, attchDir, result);
		
		logger.info("Exit doumentProcessPrintAttch()..");
		return attchPath;
	}
	
	@RequestMapping(value = { "/doumentProcessDownloadAttch" })
	@ResponseBody
	public String doumentProcessDownloadAttch(HttpServletResponse response, Model model, @ModelAttribute("documentVo") DocumentVo documentVo, BindingResult result) {
		logger.info("Enter doumentProcessPrintAttch()..");
		
		
		/*UserVo usrvo = getUserSession();*/
		//String fileNm = (String) model.asMap().get("fileNm");
		String fileNm = getRequest().getParameter("updFileName");
		String attchDir = StringUtils.EMPTY;
		attchDir = Constants.FolderStructure.UPLOAD_DOC_DIR;;
		
		/*if (usrvo == null || StringUtils.isBlank(usrvo.getLoginId())) {
			result.rejectValue(Constants.STRING_ERROR, ErrorCodes.GENERAL_EXCEPTION_KEY);
		} else {*/
			//fileService.getUplDocument(response, fileNm, attchDir, result);
		/*}*/
		
		String attchPath = null;
		try {
			attchPath = fileService.getUplDocumentToDownLd(response, fileNm, attchDir, result);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.info("Exit doumentProcessPrintAttch()..");
		return attchPath;
	}
	
	@RequestMapping(value = { "/doumentProcessViewMail" })
	public String mailAttchment(HttpServletResponse response, Model model, @ModelAttribute("documentVo") DocumentVo documentVo,
			BindingResult result) {
		logger.info("Enter doumentProcessViewMail()..");
		String attchPath = null;
		try {  
		

			MailAttachVo attachVo=new MailAttachVo();
			attachVo.setUpdFileNameMail(documentVo.getUpdFileName());
			attachVo.setMailIdDocumentId(documentVo.getDocumentId());
	
			documentVo.setMailAttachVo(attachVo);
			
		}catch (Exception e) {

		e.getMessage();
		
		}
		return "documents/mailattach";
	}
	
	@RequestMapping(value = { "/shareAttchment" })
	public String mailAttchmentsend(HttpServletResponse response, Model model, @ModelAttribute("documentVo") DocumentVo documentVo,
			BindingResult result) {
		logger.info("Enter shareAttchment()..");
		
		try {  
			MailAttachVo attachVo=new MailAttachVo();
			attachVo.setUpdFileNameMail(documentVo.getUpdFileName());
			attachVo.setMailIdDocumentId(documentVo.getDocumentId());
	
			documentVo.setMailAttachVo(attachVo);
			
			
			
			
			String fileNm = getRequest().getParameter("updFileName");
			String attchDir = StringUtils.EMPTY;
			attchDir = Constants.FolderStructure.UPLOAD_DOC_DIR;;
			
			System.out.println("attchDir  "+attchDir);
//			String attchPath = fileService.viewUplDocument(response, fileNm, attchDir, result);
			
			String path=Constants.FolderStructure.DRIVE_PATH_LEVEL;
			System.out.println(".DRIVE_PATH_LEVEL    :"+ path+fileNm); 
			
						
	System.out.println("doumentProcessViewMail");
	
		}catch (Exception e) {

		e.getMessage();
		
		}
		return "documents/shareAttchment";
	}	
	
	
	@RequestMapping(value = { "/doumentShare" })
	public void doumentShareAttch(HttpServletRequest request,HttpServletResponse response, Model model, @ModelAttribute("documentVo") DocumentVo documentVo, BindingResult result) {
		logger.info("Enter doumentProcessPrintAttch()..");
		
		/*UserVo usrvo = getUserSession();*/
		//String fileNm = (String) model.asMap().get("fileNm");
		String fileNm = getRequest().getParameter("updFileName");
		String attchDir = StringUtils.EMPTY;
		attchDir = Constants.FolderStructure.UPLOAD_DOC_DIR;;
		
		/*if (usrvo == null || StringUtils.isBlank(usrvo.getLoginId())) {
			result.rejectValue(Constants.STRING_ERROR, ErrorCodes.GENERAL_EXCEPTION_KEY);
		} else {*/
			//fileService.getUplDocument(response, fileNm, attchDir, result);
		/*}*/
		int port=request.getServerPort();
		String host=request.getLocalName();
		
		System.out.println("port   : "+port  +" host : "+host);
	
//		String url="localhost:8079/DMS/doumentProcessViewAttch?documentId=65&updFileName=Tulips_2019020733524232708530.jpg";
		
		String attchPath = fileService.getUplDocument(response, fileNm, attchDir, result);
		  String encodedBase64 = null;
		try {
			FileOutputStream fos = new FileOutputStream(attchPath);
			fos.write(Base64.decode(encodedBase64, Base64.DECODE));
			fos.close();
		}catch (Exception e) {
			// TODO: handle exception
		}
		logger.info("Exit doumentProcessPrintAttch()..");
//		return "documents/shareAttchment";
	}
	
	
	
}
