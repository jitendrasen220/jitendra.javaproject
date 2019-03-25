package org.dms.DMS.entity;

/**
 *
 * @author Administrator
 */
public class Authentification {
	private String mailDomain;
	private String mailHostIp;
	private String mailPort;
	private String connection;
	private String adminMailId;
	private String password;
	private String serverIp;
	private String lookupFolder;
	private String xlsFolder;
	// private String processfolder;
	private String domController;
	private String orgUnit;
	private String securityAuth;
	private String referral;
	private String docFsUserName;
	private String docFsPassword;
	private String xlFsUserName;
	private String xlFsPassword;
	// private String nasStoreDoc;
	// private String nasStoreXls;
	private String store;
	private String uploadfolder;
	private String activityfolder;

	public String getUploadfolder() {
		return uploadfolder;
	}

	public void setUploadfolder(String uploadfolder) {
		this.uploadfolder = uploadfolder;
	}

	public String getActivityfolder() {
		return activityfolder;
	}

	public void setActivityfolder(String activityfolder) {
		this.activityfolder = activityfolder;
	}

	public String getMailDomain() {
		return mailDomain;
	}

	public void setMailDomain(String mailDomain) {
		this.mailDomain = mailDomain.trim();
	}

	public String getMailHostIp() {
		return mailHostIp;
	}

	public void setMailHostIp(String mailHostIp) {
		this.mailHostIp = mailHostIp.trim();
	}

	public String getConnection() {
		return connection;
	}

	public void setConnection(String connection) {
		this.connection = connection.trim();
	}

	public String getAdminMailId() {
		return adminMailId;
	}

	public void setAdminMailId(String adminMailId) {
		this.adminMailId = adminMailId.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password.trim();
	}

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp.trim();
	}

	public String getMailPort() {
		return mailPort;
	}

	public void setMailPort(String mailPort) {
		this.mailPort = mailPort.trim();
	}

	public String getLookupFolder() {
		return lookupFolder;
	}

	public void setLookupFolder(String lookupFolder) {
		this.lookupFolder = lookupFolder.trim();
	}

	public String getXlsFolder() {
		return xlsFolder;
	}

	public void setXlsFolder(String xlsFolder) {
		this.xlsFolder = xlsFolder.trim();
	}
	/*
	 * public String getProcessfolder() { return processfolder; }
	 * 
	 * public void setProcessfolder(String processfolder) { this.processfolder =
	 * processfolder.trim(); }
	 */

	public String getDomController() {
		return domController;
	}

	public void setDomController(String domController) {
		this.domController = domController.trim();
	}

	public String getOrgUnit() {
		return orgUnit;
	}

	public void setOrgUnit(String orgUnit) {
		this.orgUnit = orgUnit.trim();
	}

	public String getSecurityAuth() {
		return securityAuth;
	}

	public void setSecurityAuth(String securityAuth) {
		this.securityAuth = securityAuth.trim();
	}

	public String getReferral() {
		return referral;
	}

	public void setReferral(String referral) {
		this.referral = referral.trim();
	}

	public String getDocFsUserName() {
		return docFsUserName;
	}

	public void setDocFsUserName(String docFsUserName) {
		this.docFsUserName = docFsUserName.trim();
	}

	public String getDocFsPassword() {
		return docFsPassword;
	}

	public void setDocFsPassword(String docFsPassword) {
		this.docFsPassword = docFsPassword.trim();
	}

	public String getXlFsUserName() {
		return xlFsUserName;
	}

	public void setXlFsUserName(String xlFsUserName) {
		this.xlFsUserName = xlFsUserName.trim();
	}

	public String getXlFsPassword() {
		return xlFsPassword;
	}

	public void setXlFsPassword(String xlFsPassword) {
		this.xlFsPassword = xlFsPassword.trim();
	}

	/*
	 * public String getNasStoreDoc() { return nasStoreDoc; }
	 * 
	 * public void setNasStoreDoc(String nasStoreDoc) { this.nasStoreDoc =
	 * nasStoreDoc.trim(); }
	 * 
	 * public String getNasStoreXls() { return nasStoreXls; }
	 * 
	 * public void setNasStoreXls(String nasStoreXls) { this.nasStoreXls =
	 * nasStoreXls.trim(); }
	 */

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store.trim();
	}

	/*
	 * public static Authentification getConfigurartion(){ Authentification a=null;
	 * try{ Properties prop = new Properties(); prop.load(new
	 * FileInputStream(com.app.cron.InitializeServlet.contextPath+
	 * "config.properties")); a=new Authentification();
	 * a.setMailDomain(prop.getProperty("maildomain"));
	 * a.setMailHostIp(prop.getProperty("host"));
	 * a.setConnection(prop.getProperty("connection"));
	 * a.setMailPort(prop.getProperty("port"));
	 * a.setAdminMailId(prop.getProperty("mailid"));
	 * a.setServerIp(prop.getProperty("serverip"));
	 * a.setPassword(prop.getProperty("password"));
	 * a.setXlsFolder(prop.getProperty("xlsfolder"));
	 * a.setLookupFolder(prop.getProperty("docfolder")); //
	 * a.setProcessfolder(prop.getProperty("proFolder"));
	 * a.setDomController(prop.getProperty("dc"));
	 * a.setOrgUnit(prop.getProperty("ou"));
	 * a.setSecurityAuth(prop.getProperty("securityauth"));
	 * a.setReferral(prop.getProperty("referral"));
	 * a.setDocFsUserName(prop.getProperty("docfsusername"));
	 * a.setDocFsPassword(prop.getProperty("docfspassword"));
	 * a.setXlFsUserName(prop.getProperty("xlfsusername"));
	 * a.setXlFsPassword(prop.getProperty("xlfspassword"));
	 * a.setNasStoreDoc(prop.getProperty("nasstoredoc"));
	 * a.setNasStoreXls(prop.getProperty("nasstorexls"));
	 * a.setStore(prop.getProperty("store"));
	 * a.setUploadfolder(prop.getProperty("uploadfolder"));
	 * a.setActivityfolder(prop.getProperty("activityfolder")); }catch(Exception e){
	 * e.printStackTrace(); } return a; }
	 */
	/*
	 * public String execute() throws IOException{ HttpSession
	 * session=ServletActionContext.getRequest().getSession(); String
	 * uid=(String)session.getAttribute("uid"); try{ Properties prop = new
	 * Properties(); prop.load(new
	 * FileInputStream(com.app.cron.InitializeServlet.contextPath+
	 * "config.properties")); prop.setProperty("maildomain", this.mailDomain);
	 * prop.setProperty("connection", this.connection); prop.setProperty("host",
	 * this.mailHostIp); prop.setProperty("port", this.mailPort);
	 * prop.setProperty("mailid", this.adminMailId); prop.setProperty("adminid",
	 * this.adminMailId.split("@")[0]); prop.setProperty("serverip", this.serverIp);
	 * prop.setProperty("password", this.password);
	 * prop.setProperty("xlsfolder",this.xlsFolder);
	 * prop.setProperty("docfolder",this.lookupFolder); //
	 * prop.setProperty("proFolder",this.processfolder); prop.setProperty("dc",
	 * this.domController); prop.setProperty("ou", this.orgUnit);
	 * prop.setProperty("securityauth", this.securityAuth);
	 * prop.setProperty("referral", this.referral);
	 * //prop.setProperty("docfsusername", this.docFsUserName); //
	 * prop.setProperty("docfspassword", this.docFsPassword); //
	 * prop.setProperty("xlfsusername", this.xlFsUserName); //
	 * prop.setProperty("xlfspassword", this.xlFsPassword);
	 * prop.setProperty("nasstoredoc", this.nasStoreDoc);
	 * prop.setProperty("nasstorexls", this.nasStoreXls); prop.setProperty("store",
	 * this.store); prop.setProperty("uploadfolder", this.uploadfolder);
	 * prop.setProperty("activityfolder",this.activityfolder); prop.store(new
	 * FileOutputStream(com.app.cron.InitializeServlet.contextPath+
	 * "config.properties"), null); new UserVo().updateAdminUser(this.adminMailId);
	 * new ActivityLog(new UsersBean().getUser(Integer.parseInt(uid)).getLoginId() ,
	 * "Admin Settings Changed", ActivityLog.SUCCESS,ActivityLog.ADMIN).writeLog();
	 * System.out.println("Success"); }catch(Exception e){ new ActivityLog(new
	 * UsersBean().getUser(Integer.parseInt(uid)).getLoginId() ,
	 * "Admin Settings Changed", ActivityLog.FAILED,ActivityLog.ADMIN).writeLog();
	 * e.printStackTrace(); } return SUCCESS; }
	 */
}
