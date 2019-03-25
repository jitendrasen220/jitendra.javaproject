package org.dms.DMS.util;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Transient;

public class Constants {
	


	public static final int ADMIN = 1;
	public static final int ACTIVE = 1;
	public static final int DEACTIVE = 0;
	public static final int TERMINATED = 2;
	public static final int ADMIN_POWER = 1;
	public static final int SUPER_ADMIN_POWER = 2;
	public static final String DOCUMENT_ROOT = "Documents";
	public static final String PROCESS_ROOT = "Processes";
	public static final String ADMIN_NAME = "ADMIN";
	public static final String SUPER_ADMIN = "SUPER_ADMIN";

	public static final String HTTP_REQUEST = "HTTP_REQUEST";
	public static final String HTTP_RESPONSE = "HTTP_RESPONSE";
	public static final String URL_RIGHTS = "urlRights";
	public static final String SERVICE_SESSION_ATTRIBUTE = "seviceSession";
	public static final String EMPLOYEE_SESSION_ATTRIBUTE = "employeeSession";
	public static final String ACCESS_SESSION_ATTRIBUTE = "accessSession";
	public static final String USER_SESSION_ATTRIBUTE = "userSession";
	public static String SERIAL_VERSION_ID = "serialVersionUID";

	public static final String DATE_FORMAT_PATTERN_DD_MM_YYYY = "dd-MM-yyyy";
	public static final SimpleDateFormat DATE_FORMATTER_DD_MM_YYYY = new SimpleDateFormat(
			DATE_FORMAT_PATTERN_DD_MM_YYYY);
	public static final String DATE_FORMAT_PATTERN_DD_MM_YYYY_HH_MM = "dd-MM-yyyy HH:mm";
	public static final SimpleDateFormat DATE_FORMAT_PATTERN_DD_MM_YYYY_HH_MM_FORMATTER = new SimpleDateFormat(
			DATE_FORMAT_PATTERN_DD_MM_YYYY_HH_MM);
	public static final String DATE_TIME_SEC_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_TIME_SEC_FORMAT_PATTERN1 = "yyyy/MM/dd HH:mm:ss";
	public static final SimpleDateFormat DATE_TIME_SEC_FORMATTER = new SimpleDateFormat(DATE_TIME_SEC_FORMAT_PATTERN);

	public static final String DATE_FORMAT_PATTERN = "dd-MM-yyyy";
	public static final String DATE_FORMAT_PATTERN_NEW = "yyyy-MM-dd";
	public static final String DATE_FORMAT_PATTERN1_ddmmyyyyHHmmss = "dd/MM/yyyy HH:mm:ss";
	public static final String DATE_FORMAT_PATTERN_ddmmyyyyHHmmss = "dd-MM-yyyy HH:mm:ss";
	public static final String DATE_TIME_FORMAT_PATTERN_ddMMyyyyHHmm = "dd-MM-yyyy HH:mm";
	public static final String DATE_TIME_FORMAT_PATTERN_yyyyMMddHHmm = "yyyy-MM-dd HH:mm";
	public static final String DATE_TIME_FORMAT_PATTERN_yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT_HOUR = "HH";
	public static final String DATE_FORMAT_MINUTE = "mm";
	public static final String DATE_FORMAT_PATTERN_yyyymmddHHmmss = "yyyyMMddHHmmss";
	public static final String DATE_FORMAT_PATTERN_yyyymmdd = "yyyyMMdd";
	public static final String DATE_FORMAT_PATTERN_ddmmyyyy = "ddMMyyyy";
	public static final String DATE_FORMAT_PATTERN_HHmm = "HHmm";
	public static final String DATE_FORMAT_PATTERN_ddMM = "ddMM";
	public static final String DATE_FORMAT_PATTERN_ddMMyyyy = "dd/MM/yyyy";
	public static final String DATE_FORMAT_PATTERN_yyyy = "yyyy";
	public static final String TIME_FORMAT_PATTERN_HHmmss = "HHmmss";
	public static final String DATE_FORMAT_PATTERN_yymmdd = "YYMMdd";
	public static final String DATE_FORMAT_PATTERN_DDMMMYYYY = "dd-MMM-yyyy";
	public static final String DATE_FORMAT_PATTERN_yyyymmddHHmmssmmm = "yyyyMMddHHmmssmmm";
	public static final String DATE_FORMAT_PATTERN_DDMMMYYYYHHmmss = "dd-MMM-yyyy HH:mm:ss";
	public static final String DATE_FORMAT_PATTERN_DDMMMYYYYHHmm = "dd-MMM-yyyy HH:mm";
	public static final String DATE_FORMAT_PATTERN_yyyyMMddHHmmss = "yyyyMMdd HH:mm:ss";
	public static final String DATE_FORMAT_PATTERN_yyyyMMddHHmm = "yyyyMMddHHmm";
	public static final String DATE_FORMAT_PATTERN_DDMMMYY = "dd-MMM-yy";
	public static final String DATE_FORMAT_PATTERN_NO_SPACE_ddmmyyyyHHmmss = "ddMMyyyyHHmmss";
	public static final String DATE_FORMAT_PATTERN_DDMMYY = "dd-MM-yy";
	public static final String DATE_FORMAT_PATTERN_mmddyyyyHHmmss = "MM/dd/yyyy HH:mm:ss";
	public static final String DATE_FORMAT_PATTERN_DDMMM = "dd MMM";
	public static final String DATE_FORMAT_PATTERN_hhmm = "h:mm";
	public static final String DATE_FORMAT_PATTERN_hhmmss = "h:mm:ss";
	public static final String DATE_FORMAT_PATTERN_hhmma = "h:mm a";
	public static final String DATE_FORMAT_PATTERN_hhmmssa = "h:mm:ss a";
	public static final String DATE_FORMAT_PATTERN_MM_YYYY = "MM-yyyy";
	public static final String DATE_FORMAT_PATTERN_MMM_YYYY = "MMM-yyyy";
	public static final String DATE_FORMAT_PATTERN_MM = "MM";
	public static final String DATE_FORMAT_PATTERN_YY = "YY";
	public static final String DATE_FORMAT_PATTERN_MMMM_YYYY = "MMMM-yyyy";
	public static final String DATE_FORMAT_PATTERN_SPACE_DDMMMYYYY = "dd MMM yyyy";
	public static final String DATE_FORMAT_PATTERN_DD_MMM_YYYY = "dd/MMM/yyyy";
	public static final String DATE_FORMAT_PATTERN_ddMMyy = "dd/MM/yy";
	public static final String DATE_FORMAT_PATTERN_MMMMddyyyy = "MMMM dd yyyy"; // date with full month name
	public static final String DATE_FORMAT_PATTERN_MMddyyyy = "MM/dd/yyyy";
	public static final String DATE_FORMAT_PATTERN_MM_dd_yyyy = "MM-dd-yyyy";
	public static final String DATE_FORMAT_PATTERN_DOT_DDMMYYYY = "dd.MM.yyyy";

	public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(DATE_FORMAT_PATTERN);
	public static final SimpleDateFormat DATE_FORMATTER_NEW = new SimpleDateFormat(DATE_FORMAT_PATTERN_NEW);
	public static final SimpleDateFormat DATE_FORMATTER_WITH_SLACE = new SimpleDateFormat(DATE_FORMAT_PATTERN_ddMMyyyy);
	public static final SimpleDateFormat DATE_TIME_SEC_FORMATTER_ddmmyyyyHHmmss = new SimpleDateFormat(
			DATE_FORMAT_PATTERN_ddmmyyyyHHmmss);
	public static final SimpleDateFormat DATE_TIME_SEC_FORMATTER1_ddmmyyyyHHmmss = new SimpleDateFormat(
			DATE_FORMAT_PATTERN1_ddmmyyyyHHmmss);
	public static final SimpleDateFormat DATE_TIME_FORMATTER_ddMMyyyyHHmm = new SimpleDateFormat(
			DATE_TIME_FORMAT_PATTERN_ddMMyyyyHHmm);
	public static final SimpleDateFormat DATE_TIME_FORMATTER_yyyyMMddHHmm = new SimpleDateFormat(
			DATE_TIME_FORMAT_PATTERN_yyyyMMddHHmm);
	public static final SimpleDateFormat DATE_TIME_FORMATTER_yyyymmddHHmmssmmm = new SimpleDateFormat(
			DATE_FORMAT_PATTERN_yyyymmddHHmmssmmm);
	public static final SimpleDateFormat DATE_TIME_FORMATTER_MM_yyyy = new SimpleDateFormat(
			DATE_FORMAT_PATTERN_MM_YYYY);
	public static final SimpleDateFormat DATE_TIME_FORMATTER_MMM_yyyy = new SimpleDateFormat(
			DATE_FORMAT_PATTERN_MMM_YYYY);

	public static final String UTC_DATE_FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm:ssZ";
	public static final String GMT_TIME_ZONE = "GMT";
	public static final String START_TIME = " 00:00:00";
	public static final String END_TIME = " 23:59:59";
	public static final String SQL_TIMESTAMP_FORMAT = "dd-mm-yyyy hh24:mi:ss";
	public static final String DATE_FORMAT_PATTERN_ddmmyyyy24hrs = "dd/MM/yyyy hh:mm";

	public static final String NOT_APPLICABLE = "N/A";
	public static final String MODAL_REDIRECT = "redirect:";
	public static final String PATH_COMMON = "/common/";
	public static final String STRING_FALSE = "false";
	public static final String STRING_TRUE = "true";
	public static final String UPDATE_SUCCESS = "updatedSuccessfully";
	public static final String CREATE_SUCCESS = "createdSuccessfully";
	public static final String SUBMITTED = "Submitted";
	public static final String EMPTY_DATA = "empty_data";

	public static final String EXCEPTION = "EXCEPTION: ";
	public static final String WARNING = "WARNING: ";
	public static final String STRING_ERROR = "error";
	public static final String STRING_READ_ONLY = "readonly";
	public static final String CONSTANT_1 = "1";
	public static final String DEFAULT_LANGUAGE = "ENGLISH";
	public static final String START = "START";
	public static final String END = "END";
	public static final String STATUS_ACTIVE = "ACTIVE";
	public static final String STATUS_INACTIVE = "INACTIVE";

	public static final String ID = "id";
	public static final String CELL = "cell";
	public static final String ROWS = "rows";
	public static final String ROW = "row";
	public static final String STRING_FILE_NM = "fileNm";
	public static final String STRING_FILE_FLAG = "fileFlag";

	public static final String SELECTED_COUNTRY = "India";
	
	public static final String NON_ASCII_REGEX = "[^\\x00-\\x7F]";
	public static final String TAB_NEW_LINE_REGEX = "[\\t|(\\r\\n|\\n)]";
	public static final String JUNK_CHAR_REG_EXP = "junk.char.reg.exp";
	public static final String SPACED_HYPHEN = " - ";
	public static final String STRING_HYPHEN = "-";

	public static final String INR_UNIT = "Rupees";
	public static final String INR_SUB_UNIT = "Paise";

	public static final String PAN_NO = "PAN No.(For Individual)";
	public static final String DOT = ".";
	public static final String INDIA = "INDIA";
	public static final String BOOLEAN_STRING = "boolean";

	public static final String THREE_DIGIT_FORMAT = ".###";

	public static class Actions {
		public static final String CREATE_ACTION = "CREATE";
		public static final String VIEW_ACTION = "VIEW";
		public static final String DELETE_ACTION = "DELETE";
		public static final String MODIFY_ACTION = "MODIFY";
		public static final String APPROVE_ACTION = "APPROVE";
		public static final String REVERSE_ACTION = "REVERSE";
	}

	public static final String UNDERSCORE = "_";
	public static final String TAB = "\t";
	public static final String COLON = ":";
	public static final String HTML_LINE_BREAK = "<br>";
	public static final String HTML_LINE_BREAK_END = "<br/>";
	public static final String HTML_TD_START = "<td>";
	public static final String HTML_TD_END = "</td>";
	public static final String HTML_TR_START = "<tr>";
	public static final String HTML_TR_END = "</tr>";
	public static final String HTML_TABLE_START = "<table>";
	public static final String HTML_TABLE_END = "</table>";
	public static final String FRWD_SLASH = "/";
	public static final String COMMA = ",";
	public static final String SPACE = " ";
	public static final String STRING_EMPTY = "";
	public static final String SPACED_COMMA = " , ";
	public static final String NEW_LINE = "\n";
	public static final String HASH = "#";
	public static final String HTML_TH_START = "<th>";
	public static final String HTML_TH_END = "</th>";
	public static final String Y = "Y";
	public static final String STRING_BRACKET_OPEN = "(";
	public static final String STRING_BRACKET_CLOSE = ")";
	public static final String STRING_DECIMAL_FORMAT = "#.00";
	public static final String BRNCH_USR_TP = "BRNCH_USR_TP";
	public static final String ZONE_USR_TP = "ZONE_USR_TP";
	public static final String OFFICE_TYPE = "OFFICE_TYPE";

	public static final String DRAFT_TENOR = "DRAFT_TENOR";

	public static final String HO_ROLE = "HO_ROLE";
	public static final String CIPlIPDFCLIENT_URL = "cipl.ipdf.client.url";
	public static final String INCOTERMS = "INCOTERMS";

	public static final String LC_DOC_REQ = "LC_DOC_REQ";
	public static final String LC_MNDTRY_DOC = "LC_MNDTRY_DOC";
	public static final String LC_DECLR_BRNCH = "LC_DECLR_BRNCH";
	public static final String MIS_DTLS_TP_1 = "MIS_DTLS_TP_1";

	public static final String LC_SEARCH_STATUS = "LC_SEARCH_STATUS";
	public static final String OUTWRD_MNDTRY_DOC = "OUTWRD_MNDTRY_DOC";
	public static final String OUTWRD_DECLR_BRNCH = "OUTWRD_DECLR_BRNCH";
	public static final String INVSMNT_TP = "INVSMNT_TP";
	public static final String ACC_TP = "ACC_TP";
	public static final CharSequence FCY_OTHR = "FCY_OTHR";
	public static final CharSequence LRS = "LRS";
	public static final String RLTN_SHIP = "RLTN_SHIP";
	public static final String ABA_CD = "ABA_CD";
	public static final String ERROR_PAGE = "/error";
	public static final String BRNCH_OFFC_TP = "BRNCH";

	public static final String ADVNCE_MNDTRY_DOC = "ADVNCE_MNDTRY_DOC";
	public static final String ADVNCE_DECLR_BRNCH = "ADVNCE_DECLR_BRNCH";

	public static class ProcessValidate {

		public static final String APPROVE = "APPROVE";
		public static final String REJECT = "REJECT";
		public static final String PREVIEW = "PREVIEW";
		public static final String DOWNLOAD = "DOWNLOAD";
		public static final String PRINT = "PRINT";
		public static final String MAIL = "MAIL";
		public static final String FAX = "FAX";
		public static final String UPLOAD = "UPLOAD";
		public static final String DEL = "DEL";
		public static final String CREATOR = "CREATOR";
		public static final String VERIFIER = "VERIFIER";

	}

	public static class purposeTypes {
		public static final CharSequence MNTNCE_CLS_RLTV = "MNTNCE_CLS_RLTV";
		public static final CharSequence EDUCATION = "EDUCATION";
		public static final CharSequence PUR_IMMVBL_PRPRTY = "PUR_IMMVBL_PRPRTY";
		public static final CharSequence INVSMNT_ABRD = "INVSMNT_ABRD";
	}

	public static class Validator {
		public static final String Alpha = "^[a-zA-Z()\\s]+$";
		public static final String Code = "^[a-zA-Z_]+$";
		public static final String AlphaNum = "^[a-zA-Z0-9]+$";
		public static final String NumberFormat = "^[0-9]+$";
		public static final String PanNum = "^[a-zA-Z]{5}[0-9]{4}[a-zA-z]{1}$";
		public static final String MobNum = "^[0-9]{10}$";
		public static final String TelNum = "^[0-9]{12}$";
		public static final String Email = "^[a-zA-Z0-9_!#*$%&/.=?^|~+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
		public static final String NumberFloat = "^[+-]?([0-9]*[.])?[0-9]+$";
		public static final String Rate = "^([0-9]*[.])?[0-9]+$";
		public static final String CountryCode = "^[+][0-9]+$";
	}

	public static class FileExt {
		public static final String PDF_EXT = ".pdf";
		public static final String EXCEL_EXT = ".xls";
		public static final String EXCELX_EXT = ".xlsx";
		public static final String WORD_EXT = ".doc";
		public static final String WORDX_EXT = ".docx";
		public static final String CSV_EXT = ".csv";
	}

	public static class Templates {
		public static final String TEMPLATE_FOLDER_PATH = "WEB-INF\\templates";
	}

	public static class ErrorCodes {
		public static final String GENERAL_EXCEPTION_KEY = "error.unknown";
		public static final String ERROR_PROCESS_NOT_CREATED = "error.process.not.created";
	}

	public static class NumberSeries {
		public static final Integer MAX_NO = 999;
		public static final String FIXED_PREFIX_FOR_PROCESS = "5370";
		public static final String SEQU = "SEQU";
	}

	public static class FolderStructure {

		public static final String UPLOAD_BPM_IRRVCBL_DOC_DIR = "upload_bpm_irrevocable_doc_dir";
		public static final String UPLOAD_BPM_OUTWRD_RMNTNCE_DIR = "upload_bpm_outwrd_rmntnce_dir";
		public static final String UPLOAD_DOC_DIR = "doc_dir";
		public static final String DRIVE_PATH_LEVEL = "level_drive_path";
		public static final String SCAN_TEMP_DIR = "scan_temp_dir";
		public static final String IPDF_TEMP_DIR = "ipdf_temp_dir";

	}

	public static class Tasks {
		public static final String TABLE_PROCESS = "Process";
		public static final String BACKEND = "backend";
	}

	public static class Priority {
		public static final String LOW = "LOW";
		public static final String MEDIUM = "MEDIUM";
		public static final String HIGH = "HIGH";
	}

	public static enum ProcessTypeEnum {
		IRRVCBL_DOC_LTTR(4), INWARD_REMITTNCE(1), OUTWARD_REMITTNCE(5), ADVANCE_REMITTNCE(2); // semicolon needed when
																								// fields / methods
																								// follow

		private final int processId;

		ProcessTypeEnum(int processId) {
			this.processId = processId;
		}

		public int getProcessId() {
			return this.processId;
		}
	}

	public static Map<Integer, String> ProcessTpMap() {
		Map<Integer, String> proTptMap = new HashMap<>();
		ProcessTypeEnum[] statArr = ProcessTypeEnum.values();
		for (ProcessTypeEnum status : statArr) {
			proTptMap.put(status.getProcessId(), status.name());
		}
		return proTptMap;
	}

	public static Map<String, Integer> ProcessTpIdMap() {
		Map<String, Integer> proTptMap = new HashMap<>();
		ProcessTypeEnum[] statArr = ProcessTypeEnum.values();
		for (ProcessTypeEnum status : statArr) {
			proTptMap.put(status.name(), status.getProcessId());
		}
		return proTptMap;
	}

	public static enum TaskStatusEnum {
		Draft(4), Sent(2), InProcess(8), Pending(3), Approved(1), Reassigned(7), Rejected(6), PendingPrevious(
				9), Cancelled(10), Overdue(11), Authorized(12), PendingExecution(13), Stopped(14), Closed(15);

		private int statusCode;

		private TaskStatusEnum(int s) {
			statusCode = s;
		}

		public int getStatusCode() {
			return statusCode;
		}
	}

	public static Map<Integer, String> TaskStatusMap() {
		Map<Integer, String> statMap = new HashMap<>();
		TaskStatusEnum[] statArr = TaskStatusEnum.values();
		for (TaskStatusEnum status : statArr) {
			statMap.put(status.getStatusCode(), status.name());
		}
		return statMap;
	}

	public static Map<String, Integer> TaskStatusIdMap() {
		Map<String, Integer> statMap = new HashMap<>();
		TaskStatusEnum[] statArr = TaskStatusEnum.values();
		for (TaskStatusEnum status : statArr) {
			statMap.put(status.name(), status.getStatusCode());
		}
		return statMap;
	}

	public static enum ProcessRedirectUrlEnum {
		IRRVCBL_DOC_LTTR("lcFormView"), INWARD_REMITTNCE("inWardRemittanceView"), OUTWARD_REMITTNCE("outwrdRmtnceView");

		private final String processUrl;

		ProcessRedirectUrlEnum(String processUrl) {
			this.processUrl = processUrl;
		}

		public String getProcessUrl() {
			return "redirect:/" + this.processUrl;
		}
	}

	public static Map<String, String> ProcessRedirectUrlMap() {
		Map<String, String> statMap = new HashMap<>();
		ProcessRedirectUrlEnum[] statArr = ProcessRedirectUrlEnum.values();
		for (ProcessRedirectUrlEnum status : statArr) {
			statMap.put(status.name(), status.getProcessUrl());
		}
		return statMap;
	}

}
