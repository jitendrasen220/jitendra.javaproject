package org.dms.DMS.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dms.DMS.model.UserVo;
import org.hibernate.query.Query;
import org.json.JSONException;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

public class CommonUtil {

	private static final Logger logger = Logger.getLogger(CommonUtil.class);

	public static String getFormatedNumber(int number, int length) {
		String value = String.valueOf(number);
		int origLength = value.length();
		String temp="";
		for(int i=0;i<(length-origLength);i++){
			temp+="0";
		}
		value=temp+value;
		return value;
	}

	public static String convertXMLDataToXMLPageData(String xmlData) {
		String replaceData = "<?xml version='1.0' encoding='UTF-8'?>";
		String mergeData = replaceData
		+ xmlData.substring(21, xmlData.length());
		return mergeData;
	}

	public static String escapeDoubleQuotes(String xmlData) {
		String xmlDataWithDoubleQuotes = "\"";
		String xmlDataWithSingleQuotes = "'";
		String mergeData = xmlData.replace(xmlDataWithDoubleQuotes, xmlDataWithSingleQuotes);

		xmlDataWithDoubleQuotes = "\n";
		xmlDataWithSingleQuotes = "";
		mergeData = mergeData.replace(xmlDataWithDoubleQuotes, xmlDataWithSingleQuotes);
		
		xmlDataWithDoubleQuotes = "\\";
		xmlDataWithSingleQuotes = "\\";
		xmlDataWithSingleQuotes = xmlDataWithSingleQuotes+"\\";
		mergeData = mergeData.replace(xmlDataWithDoubleQuotes, xmlDataWithSingleQuotes);

		return mergeData;
	}

	public static String convertXMLDataToXMLGridData(String xmlData) {
		String replaceData = "<?xml version='1.0' encoding='UTF-8'?>";
		String mergeData = xmlData.substring(21, xmlData.length());
		String endData = "", startData = "";
		while (mergeData.indexOf("~") != -1) {
			startData = mergeData.substring(0, mergeData.lastIndexOf(">",
					mergeData.indexOf("~")) + 1);
			endData = mergeData.substring(mergeData.indexOf("~") + 1, mergeData
					.length());
			mergeData = startData + endData;
		}
		mergeData = replaceData + mergeData;
		return mergeData;
	}

	/**
	 * @param document
	 * @return
	 * @throws IllegalArgumentException
	 * @throws Exception
	 */
	public static String getXmlString(Document document) {
		// Root node
		Node rootNode = null;

		// Get root element
		rootNode = document.getDocumentElement();

		// If root element does not exist throw null
		if (rootNode == null) {
			return null;
		}

		OutputFormat format = new OutputFormat(document);
		StringWriter stringOut = new StringWriter();
		XMLSerializer serial = new XMLSerializer(stringOut, format);
		try {
			serial.asDOMSerializer();
			serial.serialize(document.getDocumentElement());
		} catch (IOException e) {
			logger.error("IOException in getXmlString : " + e.toString());
			e.printStackTrace();
		}


		return stringOut.toString();
	}

	/**
	 * @return
	 */
	public static Document getNewDocument() {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = null;
		Document document = null;
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		document = documentBuilder.newDocument();
		return document;
	}

	/**
	 * @return
	 */
	public static void createElementWithData(Document document, Element element, String elementName, String elementData){

		Element dataEle = document.createElement(elementName);
		
		if(elementData == null){
			elementData = "";
		}
		
		dataEle.appendChild(document.createTextNode(elementData));
		element.appendChild(dataEle);
	}

	/**
	 * @return
	 */
	public static Element createElement(Document document, Element element, String elementName){

		Element dataEle = document.createElement(elementName);
		element.appendChild(dataEle);
		return dataEle;
	}

	/**
	 * Create xml element with int data
	 * @param Document document
	 * @param Element element
	 * @param String elementName
	 * @param int elementData
	 */
	public static void createElementWithData(Document document, Element element, String elementName, Integer elementData){

		Element dataEle = document.createElement(elementName);
		if(elementData != null) {
			dataEle.appendChild(document.createTextNode(Integer.toString(elementData)));
		} else{
			dataEle.appendChild(document.createTextNode(""));
		}
		element.appendChild(dataEle);
	}
	
	/**
	 * Create xml element with date data
	 * @param Document document
	 * @param Element element
	 * @param String elementName
	 * @param int elementData
	 */
	public static void createElementWithData(Document document, Element element, String elementName, Date elementData) {

		Element dataEle = document.createElement(elementName);
		if (elementData != null) {
			dataEle.appendChild(document.createTextNode(DateUtil.formatDate(elementData)));
		} else {
			dataEle.appendChild(document.createTextNode(""));
		}
		element.appendChild(dataEle);
	}

	/**
	 * Create xml element with double data
	 * 
	 * @param Document document
	 * @param Element element
	 * @param String elementName
	 * @param Double elementData
	 */
	public static void createElementWithData(Document document, Element element, String elementName, Double elementData){
		NumberFormat formatter = new DecimalFormat("#0.00");
		Element dataEle = document.createElement(elementName);
		if(elementData != null) {
			dataEle.appendChild(document.createTextNode(formatter.format(elementData)));
		} else{
			dataEle.appendChild(document.createTextNode(""));
		}
		element.appendChild(dataEle);
	}
	
	/**
	 * Create xml element with double data with specified decimal place
	 * 
	 * @param Document document
	 * @param Element element
	 * @param String elementName
	 * @param Double elementData
	 * @param Integer decimalPlace
	 */
	public static void createElementWithData(Document document, Element element, String elementName, Double elementData, Integer decimalPlace){
		
		Element dataEle = document.createElement(elementName);
		if(elementData != null) {
			dataEle.appendChild(document.createTextNode(String.format("%."+decimalPlace+"f",elementData)));
		} else{
			dataEle.appendChild(document.createTextNode(""));
		}
		element.appendChild(dataEle);
	}

	/**
	 * create xml element with null data
	 * @param Document document
	 * @param Element element
	 * @param String elementName
	 */
	public static void createElementWithNullData(Document document, Element element, String elementName){

		Element dataEle = document.createElement(elementName);
		dataEle.appendChild(document.createTextNode(""));
		element.appendChild(dataEle);
	}


	/**
	 * @return
	 */
	public static Element createRootElement(Document document, String elementName){

		Element rootElement = document.createElement(elementName);
		document.appendChild(rootElement);

		return rootElement;
	}

	/**
	 * @param int number
	 * @return String
	 */
	public static String convertDecimalToWord(int number) {
		String[] digits = {"One", "Two", "Three", "Four", "Five",
				"Six", "Seven", "Eight", "Nine"};
		String[] tense = {null, "Twenty", "Thirty", "Forty", "Fifty",
				"Sixty", "Seventy", "Eighty", "Ninety"};
		String[] teens = {"Ten", "Eleven", "Twelve", "Thirteen", "Fourteen",
				"Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};

		StringBuilder sb = new StringBuilder();
		int x = number / 100;
		if (x > 0) {
			sb.append(digits[x - 1] + " hundred");
		}
		x = number % 100;
		int tens = x / 10;
		if (tens > 0) {
			if (sb.length() > 0) {
				sb.append(" ");
			}
			if (tens > 1) {
				sb.append(tense[tens - 1]);
			} else {
				sb.append(teens[x - 10]);
				number = 0;
			}
		}
		x = number % 10;
		if (x > 0) { 
			if (sb.length() > 0) {
				if (tens >= 2) { 
					sb.append("-");
				} else {
					sb.append(" ");
				}
			}
			sb.append(digits[x - 1]);
		}
		return sb.toString();
	}

	/**
	 * @param String str
	 * @param String pattern
	 * @param String replace
	 * @return String 
	 */
	public static String patternReplace(String str, String pattern, String replace) {
		int s = 0;
		int e = 0;
		StringBuffer result = new StringBuffer();

		while ((e = str.indexOf(pattern, s)) >= 0) {
			result.append(str.substring(s, e));
			result.append(replace);
			s = e+pattern.length();
		}
		result.append(str.substring(s));
		return result.toString();
	}


	
	/**
	 * @param int number
	 * @return String
	 */
	public static String convertIntegerToThWord(int number) {
		String[] digits = {"First", "Second", "Third", "Fourth", "Fifth",
				"Sixth", "Seventh", "Eighth", "Ninth"};
		String[] tense = {null, "Twentieth", "Thirtieth", "Fortieth", "Fiftieth",
				"Sixtieth", "Seventieth", "Eightieth", "Ninetieth"};
		String[] teens = {"Tenth", "Eleventh", "Twelvth", "Thirteenth", "Fourteenth",
				"Fifteenth", "Sixteenth", "Seventeenth", "Eighteenth", "Nineteenth"};

		StringBuilder sb = new StringBuilder();
		int x = number / 100;
		if (x > 0) {
			sb.append(digits[x - 1] + " hundred");
		}
		x = number % 100;
		int tens = x / 10;
		if (tens > 0) {
			if (sb.length() > 0) {
				sb.append(" ");
			}
			if (tens > 1) {
				sb.append(tense[tens - 1]);
			} else {
				sb.append(teens[x - 10]);
				number = 0;
			}
		}
		x = number % 10;
		if (x > 0) { 
			if (sb.length() > 0) {
				if (tens >= 2) { 
					sb.append("-");
				} else {
					sb.append(" ");
				}
			}
			sb.append(digits[x - 1]);
		}
		return sb.toString();
	}



	//Method is used to round off double value to specified decimal point
	/**
	 * @param double value
	 * @param int decPoint
	 * @return float
	 */
	public static float round(double value, int decPoint) {
		float p = (float)Math.pow(10,decPoint);
		value = value * p;
		float tmp = Math.round(value);
		return (float)tmp/p;
	}
	
	/**
	 * return round value
	 * @param double value
	 * @param int decPoint
	 * @return double
	 */
	public static double roundDouble(double value, int decPoint) {
		logger.debug("Entering roundDouble ..");
		
		double p = (double)Math.pow(10,decPoint);
		value = value * p;
		double tmp = Math.round(value);
		
		logger.debug("Entering roundDouble ..");
		return (double)tmp/p;
	}
	
	

	

	// Converts String array to Integer array
	/**
	 * @param String[] sarray
	 * @return Integer[] 
	 * @throws Exception
	 */
	public static Integer[] convertStringArraytoIntArray(String[] sarray) throws Exception {
		if (sarray != null) {
			Integer intarray[] = new Integer[sarray.length];
			for (int i = 0; i < sarray.length; i++) {
				intarray[i] = Integer.parseInt(sarray[i]);
			}
			return intarray;
		}
		return null;
	}

	/**
	 * @param String xml
	 * @return Document
	 * @throws Exception
	 */
	public static Document getXMLDocument(String xml) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		Document doc = null;
		db = factory.newDocumentBuilder();
		InputSource inStream = new InputSource();
		inStream.setCharacterStream(new java.io.StringReader(xml));
		doc = db.parse(inStream);
		return doc;
	}

	/*
	 *  Used to create cell with attribute and data
	 */
	public static Element createElementWithAttData(Document document, Element element, String elementName, String elementData, String attrName,
			String attrValue) {

		Element dataEle = document.createElement(elementName);
		dataEle.setAttribute(attrName, attrValue);
		dataEle.appendChild(document.createTextNode(elementData));
		element.appendChild(dataEle);
		return dataEle;
	}


	/**
	 * @param str
	 * @return
	 */
	public static String[] splitFileName(String str) {
		String arr[] = str.split("/");
		if(arr.length == 1 ) {
			arr = str.split("\\\\");
		}
		return arr;
	}


	

	/**
	 * @param dept
	 * @param loc
	 * @param pfx
	 * @param queryGeneratedNo
	 * @param shortFy
	 * @return
	 */
	public static String generatedNoSerFormat(String dept, String loc, String pfx, String formatedNumber, String shortFy) {
		logger.info("Entering generatedNoSerFormat()..");
		String genFormat = "";
		
		if(StringUtils.isNotEmpty(dept)) {
			genFormat = genFormat + dept + "/"; 
		}
		if(StringUtils.isNotEmpty(loc)) {
			genFormat = genFormat + loc + "/"; 
		}
		if(StringUtils.isNotEmpty(pfx)) {
			genFormat = genFormat + pfx + "/"; 
		}
		if(StringUtils.isNotEmpty(formatedNumber)) {
			genFormat = genFormat + formatedNumber + "/"; 
		}
		if(StringUtils.isNotEmpty(shortFy)) {
			genFormat = genFormat + shortFy; 
		}
		logger.info("Entering generatedNoSerFormat()..");
		return genFormat;
	}

	
	
	/**
	 * Method to generate short fin year for a user selected fin year.
	 * @param String finYr
	 * @return String
	 */
	public static String getShortFinYr(String finYr) {
		logger.info("Entering getShortFinYr");
		//String fy = WebUtility.getFinancialYearSession().getFinYrName();
		String shortFy = "";
		if( finYr.length() == 9) {  // 2012-2013
			shortFy = finYr.substring(2,4) + "-" + finYr.substring(7, 9);
		} else if( finYr.length() == 7){ // 2012-13
			shortFy = finYr.substring(2,4) + "-" + finYr.substring(5, 7);
		} else if( finYr.length() == 4){ // 2013
			shortFy = finYr;
		}
		logger.info("Entering getShortFinYr()..");
		return shortFy;
	}
	

	//Method is used to avoid Exponential form of Double value 
	/**
	 * @param Double val
	 * @param String format
	 * @return String
	 */
	public static String formatDoubleValue(Double val,String format){
		DecimalFormat formatter = new DecimalFormat(format);
		return formatter.format(val);
	}

	//Method is used to avoid Exponential form of Double value 
	/**
	 * @param Double val
	 * @param String format
	 * @return String
	 */
	public static String formatDoubleValue(Double val, Integer decimalPlace) {
		if (val == null) {
			return "";
		} else {
			return String.format("%."+ decimalPlace +"f",val);
		}
	}
	
	public static double parseDoubleValue(String val){
		if(StringUtils.isNotBlank(val)) {
			return Double.parseDouble(val);
		} else {
			return Double.parseDouble("0.000");
		}
	}
	
	public static BigDecimal parseBigDecimalValue(String val){
		if(StringUtils.isNotBlank(val)) {
			return new BigDecimal(val);
		} else {
			return new BigDecimal("0.000");
		}
	}

	
	

	/**
	 * @param String rplStr
	 * @param String[] charToRpl
	 * @param String rplChar
	 * @return String
	 */
	public static String replaceChar(String rplStr, String[] charToRpl, String rplChar) {
		logger.info("Entering replaceChar ..");

		String mergeData = rplStr;
		for (String chrToRpl : charToRpl){
			mergeData = mergeData.replace(chrToRpl, rplChar);
		}

		logger.info("Exiting replaceChar ..");
		return mergeData;
	}

	/**
	 * @param Document document
	 * @return String
	 */
	public static String generateXmlGridDataFrmDoc(Document document) {
		logger.info("Entering generateXmlGridDataFrmDoc");

		String xml = CommonUtil.getXmlString(document);

		//replace new line characters
		String replacedXml = CommonUtil.escapeDoubleQuotes(CommonUtil.convertXMLDataToXMLGridDataAndReplaceCRLF(xml));

		//remove invalid replacement of new line character
		String finalXml = CommonUtil.removeUnwantedNewLnCharFrmXml(replacedXml);

		logger.info("Exiting generateXmlGridDataFrmDoc");
		return finalXml;
	}

	public static String convertXMLDataToXMLGridDataAndReplaceCRLF(String xmlData) {
		logger.debug("Entering convertXMLDataToXMLGridDataAndReplaceCRLF ..");
		logger.info("before 1..:: "+xmlData);

		// Following line replace all the CRLF(Carriage return and Line field) with &#10
		String mergeData = xmlData.replaceAll("\r\n|\r|\n|\n\r", "&#10;");

		logger.debug("Exiting convertXMLDataToXMLGridDataAndReplaceCRLF ..");
		logger.info("mergeData1 is ..:: "+mergeData);
		return mergeData;
	}

	public static String removeUnwantedNewLnCharFrmXml(String xmlData) {
		logger.debug("Entering removeUnwantedNewLnCharFrmXml ..");

		String replaceData = "<?xml version='1.0' encoding='UTF-8'?>";
		String mergeData = replaceData + xmlData.substring(43, xmlData.length());

		// Following line replace all the CRLF(Carriage return and Line field) with &#10
		mergeData = mergeData.replaceAll("\r\n|\r|\n|\n\r", "&#10;");

		logger.debug("Exiting removeUnwantedNewLnCharFrmXml ..");
		return mergeData;
	}

	public static String convertXMLDataToXMLPageDataAndReplaceCRLF(String xmlData) {
		logger.debug("Entering convertXMLDataToXMLPageDataAndReplaceCRLF ..");

		String replaceData = "<?xml version='1.0' encoding='UTF-8'?>";
		String mergeData = replaceData + xmlData.substring(21, xmlData.length());

		// Following line replace all the CRLF(Carriage return and Line field) with &#10
		mergeData = mergeData.replaceAll("\r\n|\r|\n|\n\r", "&#10;");

		logger.debug("Exiting convertXMLDataToXMLPageDataAndReplaceCRLF ..");
		return mergeData;
	}


	
	
	
	//
	/**
	 * @param data
	 * @param length
	 * @return
	 */
	public static String padSpaces(String data, Integer length ) {
		logger.info("Entering padSpaces()..");
		if(data == null ) {
			data = "";
		}
		if(data.length() > length) {
			data = data.substring(0, length);
		}
		logger.info("Exiting padSpaces()..");
		return StringUtils.rightPad(data, length);
	}
	
	
	/**
	 * @param data
	 * @param length
	 * @return
	 */
	public static String prependZeros(String data, Integer length ) {
		logger.info("Entering prependZeros()..");
		if(data == null ) {
			data = "";
		}
		if(data.length() > length) {
			data = data.substring(0, length);
		}
		String str = data;
		StringBuilder sb = new StringBuilder();
		for (int toprepend=length-str.length(); toprepend>0; toprepend--) {
			sb.append('0');
		}
		sb.append(str);
		logger.info("Exiting prependZeros()..");
		return sb.toString();
	}
	
	/**
	 * @param data
	 * @param length
	 * @return
	 */
	public static String padDashes(String data, Integer length ) {
		logger.info("Entering padDashes()..");
		if(data == null ) {
			data = "";
		}
		if(data.length() > length) {
			data = data.substring(0, length);
		}
		String str = data;
		StringBuilder sb = new StringBuilder();
		for (int toprepend=length-str.length(); toprepend>0; toprepend--) {
			sb.append('-');
		}
		sb.append(str);
		logger.info("Exiting padDashes()..");
		return sb.toString();
	}
	
	/**
	 * @param data
	 * @param length
	 * @return
	 */
	public static String prependSpaces(String data, Integer length ) {
		logger.info("Entering prependSpaces()..");
		if(data == null ) {
			data = "";
		}
		if(data.length() > length) {
			data = data.substring(0, length);
		}
		String str = data;
		StringBuilder sb = new StringBuilder();
		for (int toprepend=length-str.length(); toprepend>0; toprepend--) {
			sb.append(' ');
		}
		sb.append(str);
		logger.info("Exiting prependSpaces()..");
		return sb.toString();
	}
	
	/**
	 * @param document
	 * @param element
	 * @param elementName
	 * @param elementData
	 */
	public static void createElementWithData(Document document, Element element, String elementName, BigDecimal elementData){

		Element dataEle = document.createElement(elementName);
		if(elementData != null) {
			dataEle.appendChild(document.createTextNode((elementData).toString()));
		} else{
			dataEle.appendChild(document.createTextNode(""));
		}
		element.appendChild(dataEle);
	}

	/**
	 * @param BigDecimal val
	 * @param String format
	 * @return String
	 */
	public static String formatBigDecimalValue(BigDecimal val,String format){
		DecimalFormat formatter = new DecimalFormat(format);
		if(val != null) {
			return formatter.format(val);
		} else {
			return StringUtils.EMPTY;
		}
	}
	
	/**
	 * @param BigDecimal val
	 * @param String format
	 * @return BigDecimal
	 */
	/*public static BigDecimal formatBigDecimalVal(BigDecimal val,String format){
		DecimalFormat formatter = new DecimalFormat(format);
		if(val != null) {
			return new BigDecimal(formatter.format(val));
		} else {
			return null;
		}
	}*/
	
	

	
	/**
	 * Method to round BigDecimal Value..
	 * @param BigDecimal val
	 * @param int precision
	 * @return BigDecimal
	 */
	public static BigDecimal roundBigDecimal(BigDecimal val,int precision) {
		BigDecimal bigDecimalVal;
		bigDecimalVal = val.setScale(precision, RoundingMode.HALF_UP);
		return bigDecimalVal;
	}
	
	/**
	 * Method to round BigDecimal Value..
	 * @param BigDecimal val
	 * @param int precision
	 * @return BigDecimal
	 */
	public static BigDecimal roundBigDecimal(Double val, int precision) {
		
		BigDecimal bigDecimalVal = new BigDecimal(val);
		bigDecimalVal = bigDecimalVal.setScale(precision, RoundingMode.HALF_UP);
		return bigDecimalVal;
	}
	
	/**
	 * Method to round BigDecimal Value..
	 * @param BigDecimal val
	 * @param int precision
	 * @return BigDecimal
	 */
	public static BigDecimal roundBigDecimal(Double val) {
		return roundBigDecimal(val, 2);
	}
	
	/**
	 * Method to create Root Rows Element with "rows"
	 * @param Document document
	 * @return Element
	 */
	public static Element createRootRowsElement(Document document){

		Element rootElement = document.createElement(Constants.ROWS);
		document.appendChild(rootElement);
		return rootElement;
	}
	
	/**
	 * Method to create Element Cell With String Data "cell"
	 * @param Document document
	 * @param Element element
	 * @param String elementData
	 */
	public static void createElementCellWithData(Document document, Element element, String elementData){

		createElementWithData(document, element, Constants.CELL, elementData);
	}
	
	/**
	 * Method to create Element Cell With Integer Data
	 * @param Document document
	 * @param Element element
	 * @param Integer elementData
	 */
	public static void createElementCellWithData(Document document, Element element, Integer elementData){

		createElementWithData(document, element, Constants.CELL, elementData);
	}
	
	
	/**
	 * Method to create Element Cell With Double Data
	 * @param Document document
	 * @param Element element
	 * @param Double elementData
	 */
	public static void createElementCellWithData(Document document, Element element, Double elementData){

		createElementWithData(document, element, Constants.CELL, elementData);
	}
	
	/**
	 * Method to create Element Cell With Date Data "cell"
	 * @param Document document
	 * @param Element element
	 * @param Date elementData
	 */
	public static void createElementCellWithData(Document document, Element element, Date elementData){

		createElementWithData(document, element, Constants.CELL, elementData);
	}
	
	/**
	 * Method to create Element With Null Cell Data "cell"
	 * @param Document document
	 * @param Element element
	 */
	public static void createElementWithNullCellData(Document document, Element element){

		Element dataEle = document.createElement(Constants.CELL);
		dataEle.appendChild(document.createTextNode(StringUtils.EMPTY));
		element.appendChild(dataEle);
	}
	
	/**
	 * Method to create Element Row with "row"
	 * @param Document document
	 * @param Element elementX
	 * @return Element
	 */
	public static Element createElementRow(Document document, Element element){

		Element dataEle = document.createElement(Constants.ROW);
		element.appendChild(dataEle);
		return dataEle;
	}
		
	/**
	 * set Content Type, File Name and (Inline or Attachment) 
	 * @param HttpServletResponse response
	 * @param String contenttype
	 * @param String inlineAttach
	 * @param String filename
	 */
	public static void setContTypeFileNm(HttpServletResponse response, String contenttype, String inlineAttach, String filename){
		
		//set content type
		response.setContentType(contenttype);
		
		//forward slash not valid for file name
		//replace forward slash(/) with underscore(_)
		filename = filename.replaceAll("/", "_");
		
		//replace _null with emty string
		filename = filename.replaceAll("_null", "");
		
		//set file name and (inline or attachment)
		response.setHeader("content-disposition",  (inlineAttach + "; filename=\""+ filename + "\""));		
	}
	
	/**
	 * @param String marksNumbers
	 * @return String
	 */
	public static String convertDataToPrintDataAndReplaceCRLF(String marksNumbers) {
		logger.debug("Entering convertDataToDataAndReplaceCRLF ..");

		// Following line replace all the CRLF(Carriage return and Line field)
		// with &#10
		String mergeData = marksNumbers.replaceAll("\r\n|\r|\n|\n\r", "&#10;");

		logger.debug("Exiting convertDataToDataAndReplaceCRLF ..");
		return mergeData;
	}
	
	/**
	 * method to convert xml to html table
	 * @param String xmlString
	 * @param String[] header
	 * @return
	 */
	public static String getHtmlTableFromXmlString(String xmlString, String[] header) {
		String xmlStr = "<table border='1' cellpadding=\"5\" style=\"border-collapse: collapse; font-size: 12px \"><tr>";

		// set table header
		for (int sz = 0; sz < header.length; sz++) {
			xmlStr += "<th>" + header[sz] + "</th>";
		}
		xmlStr += Constants.HTML_TR_END;

		// replace xml tag with html tag
		xmlString = xmlString.replaceAll("/rows", "/table");
		xmlString = xmlString.replaceAll("rows/", "/table");
		xmlString = xmlString.replaceAll("<rows>", "");
		xmlString = xmlString.replaceAll("row", "tr");
		xmlString = xmlString.replaceAll("</cell>", Constants.HTML_TD_END);
		xmlString = xmlString.replaceAll("<cell>", "<td nowrap>");

		// comments <?xml version="1.0"?>
		xmlString = xmlString.replaceAll("<\\?", "<!--<?");
		xmlString = xmlString.replaceAll("\\?>", "?>-->");

		xmlStr += xmlString;

		logger.info(" Exiting getHtmlTable");
		return xmlStr;
	}
	
	/**
	 * @param String strVal
	 * @return String
	 */
	public static String setTemplateField(String strVal) {

		strVal = StringUtils.isNotBlank(strVal) ? strVal : StringUtils.EMPTY;				
		return strVal;
	}
	
	/**
	 * @param Date date
	 * @return String
	 */
	public static String setTemplateField(Date date) {

		String strVal = date != null ? DateUtil.formatDate(date,Constants.DATE_FORMAT_PATTERN_DDMMMYYYY) : StringUtils.EMPTY;		
		return strVal;
	}
	
	/**
	 * @param Date date
	 * @return String
	 */
	public static String setTemplateField(Date date, String format) {

		String strVal = date != null ? DateUtil.formatDate(date, format) : StringUtils.EMPTY;		
		return strVal;
	}

	/**
	 * @param Double val
	 * @return String
	 */
	public static String setTemplateField(Double val) {

		String doubleVal = val != null ? Double.toString(val) : StringUtils.EMPTY;		
		return doubleVal;
	}
	
	/**
	 * @param document
	 * @param element
	 * @param elementData
	 * @param gridAttrMap
	 */
	public static void createElementCellWithCellColorAndData(Document document, Element element, Double elementData, Map<String, Object> gridAttrMap) {
		createElementWithCellColorAndData(document, element, Constants.CELL, elementData, gridAttrMap);
	}

	/**
	 * @param document
	 * @param element
	 * @param elementName
	 * @param elementData
	 * @param gridAttrMap
	 */
	public static void createElementWithCellColorAndData(Document document, Element element, String elementName, Double elementData,
			Map<String, Object> gridAttrMap) {

		NumberFormat formatter = new DecimalFormat("#0.00");
		Element dataEle = document.createElement(elementName);
		if (elementData != null) {
			dataEle.appendChild(document.createTextNode(formatter.format(elementData)));
		} else {
			dataEle.appendChild(document.createTextNode(""));
		}

		Set<Entry<String, Object>> filterSet = gridAttrMap.entrySet();
		Iterator<Entry<String, Object>> filters = filterSet.iterator();

		while (filters.hasNext()) {
			Entry<String, Object> entry = (Entry<String, Object>) filters.next();
			if (entry.getValue() != null) {
				dataEle.setAttribute(entry.getKey().toString(), entry.getValue().toString());
			}
		}
		element.appendChild(dataEle);
	}

	/**
	 * @method to set color to XDHTML grid cell
	 * @param Document document
	 * @param Element rowElement
	 * @param String element
	 * @param Map<Object, String> colorMap
	 * @param String key
	 */
	public static void createElementCellWithCellColorAndData(Document document,	Element rowElement, String element, Map<Object, String> colorMap, String key) {

		String elementName = Constants.CELL;
		Element dataEle = document.createElement(elementName);
		
		if (element != null) {
			dataEle.appendChild(document.createTextNode(element));
		} else {
			dataEle.appendChild(document.createTextNode(""));
		}

		dataEle.setAttribute("style", colorMap.get(key));
		rowElement.appendChild(dataEle);
	}
	
	/**
	 * @param xml
	 * @return NodeList rows
	 * @throws Exception
	 */
	public static NodeList getXMLRows(String xml) throws Exception {

		Document doc = getXMLDocument(xml);
		NodeList rows = doc.getElementsByTagName(Constants.ROW);
		return rows;
	}

	/**
	 * @param nodes
	 * @param row
	 * @return NodeList cells
	 */ 
	public static NodeList getXMLCells(NodeList nodes, int row) {
		Element element = (Element) nodes.item(row);
		NodeList cells = element.getElementsByTagName(Constants.CELL);
		return cells;
	}

	/**
	 * @param lines
	 * @param cell
	 * @return String text
	 */
	public static String getXMLCellData(NodeList lines, int cell) {
		Element line = (Element) lines.item(cell);
		Node child = line.getFirstChild();
		CharacterData cd = (CharacterData) child;
		String text = (cd == null ? StringUtils.EMPTY : cd.getData().trim());
		return text;
	}

	public static String convertXMLDataToXMLGridDataForFTP(String xmlData) {
		String replaceData = "<?xml version='1.0' encoding='UTF-8'?>";
		String mergeData = xmlData.substring(0, xmlData.length());
		String endData = "", startData = "";
		while (mergeData.indexOf("~") != -1) {
			startData = mergeData.substring(0, mergeData.lastIndexOf(">", mergeData.indexOf("~")) + 1);
			endData = mergeData.substring(mergeData.indexOf("~") + 1, mergeData.length());
			mergeData = startData + endData;
		}
		mergeData = replaceData + mergeData;
		return mergeData;
	}
	
	 /**
	 * @param voucherAmount
	 * @return
	 */
	public static String generateAmountInWords(Double voucherAmount) {
	        String unit = Constants.INR_UNIT;
	        String subUnit = Constants.INR_SUB_UNIT;
	        String dNum = "";
	        String totalAmountInWords = "";
	        String amountInWords = "";
	        NumberFormat formatter = new DecimalFormat("#0.00");
	        String totalAmount = formatter.format(voucherAmount);
	        String[] tokens = new String[2];
	        int i = 0;
	        StringTokenizer st = new StringTokenizer(totalAmount, ".");
	        while (st.hasMoreTokens()) {
	                tokens[i] = (st.nextToken());
	                i++;
	        }
	        if (tokens[1] != null && tokens[1] != "") {
	                if (tokens[1].length() == 1) {
	                        tokens[1] = tokens[1] + "0";
	                }
	                dNum = CommonUtil.convertDecimalToWord(Integer.parseInt(tokens[1]));
	        }
	        if (tokens[0].equalsIgnoreCase("0") || tokens[0].equalsIgnoreCase("") || tokens[0] == null) {
	                dNum = " " + subUnit + " " + dNum + " " + "Only";
	                totalAmountInWords = dNum;
	        } else {
	                amountInWords = EnglishNumberToWords.convertNumberToWords(Long.parseLong(tokens[0]));
	                amountInWords = " " + unit + " " + amountInWords;
	                if (dNum.equalsIgnoreCase("")) {
	                        dNum = "Only";
	                } else {
	                        dNum = "and" + " " + " " + subUnit + " " + dNum + " " + "Only";
	                }
	                totalAmountInWords = amountInWords + dNum;
	                totalAmountInWords = CommonUtil.patternReplace(amountInWords, "Only", dNum);
	        }
	        return totalAmountInWords;
	}

	/**
	 * @param cntNo
	 * @return boolean
	 */
	public static boolean isValidCntNo(String cntNo) {
		logger.info("Entering isValidCntNo");

		String cntRegex = "^[A-Za-z]{4}\\d{7}$";

		Pattern p = Pattern.compile(cntRegex);
		java.util.regex.Matcher m = p.matcher(cntNo.trim());

		logger.info("Existing isValidCntNo");
		return m.matches();
	}
	
	 /**
     * @param String val
     * @return boolean
     */
	public static boolean isValidBLNumber(String val) {
		String blPattern = "^[a-zA-Z0-9.\\-_()\\s]*$";
		Pattern p = Pattern.compile(blPattern);
		java.util.regex.Matcher m = p.matcher(val);
		return m.matches();
	}

	 /**
     * @param String val
     * @return boolean
     */
	public static boolean isValidFiledValue(String val) {
		String pattern = "^[a-zA-Z0-9.\\-_()\\s]*$";
		Pattern p = Pattern.compile(pattern);
		java.util.regex.Matcher m = p.matcher(val);
		return m.matches();
	}
	
	/**
	 * @param document
	 * @param element
	 * @param elementName
	 * @param gridAttrMap
	 * @return
	 */
	public static Element createElementRowWithColor(Document document, Element element, String elementName, Map<String, Object> gridAttrMap) {
		Element dataEle = document.createElement(elementName);

		createElementWithRowColorAndData(document, element, elementName, gridAttrMap, dataEle);

		return dataEle;
	}

	/**
	 * @param document
	 * @param element
	 * @param elementName
	 * @param gridAttrMap
	 * @param dataEle
	 */
	public static void createElementWithRowColorAndData(Document document, Element element, String elementName, Map<String, Object> gridAttrMap,
			Element dataEle) {

		Set<Entry<String, Object>> filterSet = gridAttrMap.entrySet();
		Iterator<Entry<String, Object>> filters = filterSet.iterator();

		while (filters.hasNext()) {
			Entry<String, Object> entry = (Entry<String, Object>) filters.next();
			if (entry.getValue() != null) {
				dataEle.setAttribute(entry.getKey().toString(), entry.getValue().toString());
			}
		}
		element.appendChild(dataEle);
	}

	public static boolean isDoubleVal(String val) {
		String pattern = "^[0-9]{0,10}(\\.[0-9]{0,2})?$";
		Pattern p = Pattern.compile(pattern);
		java.util.regex.Matcher m = p.matcher(val);
		return m.matches();
	}
	
	public static boolean isDoubleValPrecisionAndDecimalPlaces(String val) {
		String pattern = "^[0-9]{0,6}(\\.[0-9]{0,2})?$";
		Pattern p = Pattern.compile(pattern);
		java.util.regex.Matcher m = p.matcher(val);
		return m.matches();
	}
	
	/**
	 * @param String val
	 * @param String pattern
	 * @return boolean
	 */
	public static boolean isBigDecimal(String val, String pattern) {
		Pattern p = Pattern.compile(pattern);
		java.util.regex.Matcher m = p.matcher(val);
		return m.matches();
	}	
	
	/**
	 * @param response
	 * @param contenttype
	 * @param inlineAttach
	 * @param filename
	 * @param extention
	 */
	public static void setContTypeFileNm(HttpServletResponse response, String contenttype, String inlineAttach, String filename, String extention) {

		// forward slash not valid for file name
		// replace forward slash(/) with underscore(_)
		filename = filename.replaceAll("/", "_");

		// replace _null with emty string
		filename = filename.replaceAll("_null", "");

		// set content type
		response.setContentType(contenttype);

		// set file name and (inline or attachment)
		response.setHeader("content-disposition", (inlineAttach + "; filename=\"" + filename + extention + "\" "));
	}
	
	 /**
     * @param String val
     * @return boolean
     */
	public static boolean isValidIGMNumber(String val) {
		String igmPattern = "^\\d{7}$";
		Pattern p = Pattern.compile(igmPattern);
		java.util.regex.Matcher m = p.matcher(val);
		return m.matches();
	}
	
	/**
	 * @param String val
	 * @return
	 */
	public static boolean isValidItemNumber(String val) {
		String itemPattern =  "^\\d{1,4}$";
		Pattern p = Pattern.compile(itemPattern);
		java.util.regex.Matcher m = p.matcher(val);
		return m.matches();
	}
	
	
	/**
	 * @method is use to check valid percentage
	 * @param String string
	 * @return Boolean b
	 */
	public static boolean isValidPercentage(String string) {
		String percentPattern = "^[-+]?\\d{0,3}+(\\.{0,1}(\\d{0,2}))?$";
		Pattern p = Pattern.compile(percentPattern);
		java.util.regex.Matcher m = p.matcher(string);
		Boolean b = false;
		try {
			if (Double.parseDouble(string) <= 100 && m.matches()) {
				b = true;
			}
		
		} catch (NumberFormatException nme) {

		}
		return b;
	}
	
	/**
	 * Method is used to format Double value
	 * @param String val
	 * @param Double format
	 * @return Double
	 */
	public static Double formatDoubleVal(Double val,String format){
		DecimalFormat formatter = new DecimalFormat(format);
		return Double.valueOf(formatter.format(val));
	}
	
	/**
	 * @method to mask a input string 
	 * @param String mobNo
	 * @param Integer startLen
	 * @param Integer endLen
	 * @return String
	 */
	public static String maskWithX(String str, Integer startLen, Integer endLen) {
		String masked = StringUtils.EMPTY;
		if (StringUtils.isNotBlank(str) && str.length() >= 10) {
			int total = str.length();
			int masklen = total - (startLen + endLen);
			StringBuffer maskedbuf = new StringBuffer(str.substring(0, startLen));
			for (int i = 0; i < masklen; i++) {
				maskedbuf.append('X');
			}
			maskedbuf.append(str.substring(startLen + masklen, total));
			masked = maskedbuf.toString();
		}
		return masked;
	}

	/**
	 * @param String treeXml
	 * @param int number
	 * @return Boolean retBool
	 */
	public static Boolean validateTree(String treeXml, int number) {
		logger.info("Entering validateTree()");

		Boolean retBool = new Boolean(true);
		try {
			// ** This Code is use to Fetch the Data from XML String
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder db;
			db = factory.newDocumentBuilder();
			InputSource inStream = new InputSource();
			if (number == 1) {
				inStream.setCharacterStream(new java.io.StringReader(treeXml));
			}
			Document doc = db.parse(inStream);
			NodeList nodes = doc.getElementsByTagName("tree");
			for (int row = 0; row < nodes.getLength(); row++) {
				Element element = (Element) nodes.item(row);
				// It will check for inner nodes named as "cell"
				NodeList lines = element.getElementsByTagName("item");
				int j = 0;
				for (int cell = 1; cell <= lines.getLength(); cell++) {
					Element line = (Element) lines.item(cell);
					String child2 = line == null ? "null" : line.getAttribute("checked");
					if (child2 != null && child2.equalsIgnoreCase("1")) {
						j++;
					}
				}
				if (j == 0 && number == 1) {
					retBool = false;
					return retBool;
				}
			}

		} catch (Exception e) {
			logger.error("Exception ..", e);
		}

		logger.info("Exiting validateTree()");
		return retBool;
	}
	/**
	 * @param JSONObject json
	 * @param String keyStr
	 * @param String valStr
	 * @throws JSONException
	 */
	/*public static void populateJSON(JSONObject json, String keyStr, String valStr) throws JSONException {
		if (StringUtils.isNotBlank(valStr)) {
			json.put(keyStr, valStr);
		} else {
			json.put(keyStr, StringUtils.EMPTY);
		}
	}

	*//**
	 * @param json
	 * @param keyStr
	 * @param val
	 * @param pattern
	 * @throws JSONException
	 *//*
	public static void populateJSON(JSONObject json, String keyStr, Date val, String pattern) throws JSONException {
		if (val != null) {
			json.put(keyStr, DateUtil.formatDate(val, pattern));
		} else {
			json.put(keyStr, StringUtils.EMPTY);
		}
	}

	*//**
	 * @param json
	 * @param keyStr
	 * @param val
	 * @throws JSONException
	 *//*
	public static void populateJSON(JSONObject json, String keyStr, Date val) throws JSONException {
		populateJSON(json, keyStr, val, Constants.DATE_FORMAT_PATTERN);
	}
	
	*//**
	 * @param JSONObject json
	 * @param String keyStr
	 * @param Double val
	 * @throws JSONException
	 *//*
	public static void populateJSON(JSONObject json, String keyStr, Double val) throws JSONException {
		NumberFormat formatter = new DecimalFormat("#0.00");
		if (val != null) {
			json.put(keyStr, new Double(formatter.format(val)));
		} else {
			json.put(keyStr, StringUtils.EMPTY);
		}
	}
	
	*//**
	 * @param JSONObject json
	 * @param  String keyStr
	 * @param Integer val
	 * @throws JSONException
	 *//*
	public static void populateJSON(JSONObject json, String keyStr, Integer val) throws JSONException {
		if (val != null) {
			json.put(keyStr, val);
		} else {
			json.put(keyStr, StringUtils.EMPTY);
		}
	}
	
	public static void populateJSON(JSONObject json, String keyStr, String val1Str, String val2Str, String val3Str, String joinSmbl)
			throws JSONException {

		if (StringUtils.isNotBlank(val1Str)) {
			json.put(keyStr, val1Str);
		} else if (StringUtils.isNotBlank(val2Str) && StringUtils.isNotBlank(val3Str)) {
			json.put(keyStr, val2Str + joinSmbl + val3Str);
		} else {
			json.put(keyStr, StringUtils.EMPTY);
		}
	}

	 *//**
     * @param String val
     * @return boolean
     *//*
	public static boolean isValidSchemeCodeValue(String val) {
		String pattern = "^[a-zA-Z0-9_\\s]*$";
		Pattern p = Pattern.compile(pattern);
		java.util.regex.Matcher m = p.matcher(val);
		return m.matches();
	}

	*//**
	 * @param json
	 * @param keyStr
	 * @param valStr
	 * @throws JSONException
	 *//*
	public static void populateJSON(JSONObject json, String keyStr, Boolean valStr) throws JSONException {
		if (valStr != null) {
			json.put(keyStr, valStr);
		} else {
			json.put(keyStr, StringUtils.EMPTY);
		}
	}

	*//**
	 * @param JSONObject json
	 * @param String keyStr
	 * @param String valStr
	 * @throws JSONException
	 *//*
	public static void populateJSON(JSONObject json, String keyStr, Character valStr) throws JSONException {
		if (valStr != null) {
			json.put(keyStr, valStr);
		} else {
			json.put(keyStr, StringUtils.EMPTY);
		}
	}*/

	public static boolean isValidCompNm(String val) {
		String compNmPattern = "^[ a-zA-Z0-9()\\.\\&'-]+$";
		Pattern p = Pattern.compile(compNmPattern);
		java.util.regex.Matcher m = p.matcher(val);
		return m.matches();
	}
	
	public static boolean isValidNumeric(String val) {
		String NumericValue = "^[0-9]+$";
		Pattern p = Pattern.compile(NumericValue);
		java.util.regex.Matcher m = p.matcher(val);
		return m.matches();
	}
	
	public static boolean isValidLicNo(String val) {
		String licNo = "^[a-zA-Z0-9\\/]+$";
		Pattern p = Pattern.compile(licNo);
		java.util.regex.Matcher m = p.matcher(val);
		return m.matches();
	}
	public static boolean isValidCountryCode(String val) {
		String countryCode = "^[0-9 +]+$";
		Pattern p = Pattern.compile(countryCode);
		java.util.regex.Matcher m = p.matcher(val);
		return m.matches();
	}
	public static boolean isValidEmailId(String val) {
		String emailId = "^[a-zA-Z0-9-_]+(\\.[-_a-zA-Z0-9]+)*@[a-zA-Z0-9-_]+(\\.[a-zA-Z0-9-_]+)*(\\.[a-zA-Z-_]{2,15})$";
		Pattern p = Pattern.compile(emailId);
		java.util.regex.Matcher m = p.matcher(val);
		return m.matches();
	}
	
	public static boolean validatePanNo(String val) {
		String pancard = "^([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}?$";
		Pattern p = Pattern.compile(pancard);
		java.util.regex.Matcher m = p.matcher(val);
		return m.matches();
	}
	
	public static boolean isAlphaNumRgx(String val) {
		String alphaNum = "^[a-zA-Z0-9]+$";
		Pattern p = Pattern.compile(alphaNum);
		java.util.regex.Matcher m = p.matcher(val);
		return m.matches();
	}

	public static String getCompSvcTpLocBySvcTp(List<String> compSvcList, String svcTp) {
		String serviceLocTp = StringUtils.EMPTY;
		if (compSvcList != null && compSvcList.size() > 0) {
			for (String svcTpList : compSvcList) {
				if (svcTpList.contains(svcTp)) {
					String[] array = svcTpList.split("\\|");
					if (array[0].equals(svcTp)) {
						serviceLocTp = array[1];
						break;
					}
				}
			}
		}
		return serviceLocTp;
	}
	
	public static boolean isReqFile(String filePath, String attChext) {
		int dotPos = filePath.lastIndexOf(Constants.DOT);
		String ext = filePath.substring(dotPos + 1, filePath.length());
		if (attChext.equalsIgnoreCase(ext)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param filePath
	 * @throws DMSException
	 * @throws IOException
	 */
	/*public static void downloadPrint(String filePath) throws DMSException, IOException {
		logger.info("Entering generateVgmReqPrint() ..");

		HttpServletResponse response = WebUtility.getResponse();
		InputStream stream = null;
		OutputStream os = null;
		try {
			int dotPos = filePath.lastIndexOf(".");
			String ext = filePath.substring(dotPos + 1, filePath.length());
			File file = new File(filePath);
			if (!file.exists()) {
			} else {
				if (ext.equalsIgnoreCase("DOC")) {
					response.setContentType("application/msword");
					response.setHeader("Content-Disposition", "inline;" + "filename=" + file.getName());
				} else if (ext.equalsIgnoreCase("DOCX")) {
					response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
					response.setHeader("Content-Disposition", "attachment;" + "filename=" + file.getName());
				} else {
					response.setContentType("text/plain");
					response.setHeader("Content-Disposition", "attachment;" + "filename=" + file.getName());
				}

				stream = new FileInputStream(file);
				long length = file.length();

				byte[] bytes = new byte[(int) length];
				int offset = 0;
				int numRead = 0;
				while (offset < bytes.length && (numRead = stream.read(bytes, offset, bytes.length - offset)) >= 0) {
					offset += numRead;
				}
				os = response.getOutputStream();
				os.write(bytes);				
				response.flushBuffer();
			}
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION, e);
			throw new DMSException("An error occurred while processing your request, please contact administrator.", ErrorCodes.GENERAL_EXCEPTION_KEY);
		} finally {
			stream.close();
			os.close();
			os.flush();
		}

		logger.info("Exiting generateVgmReqPrint() ..");
	}*/
	
	/**
	 * @param Integer val
	 * @return String
	 */
	public static String setTemplateField(Integer val) {

		String integerVal = val != null ? Integer.toString(val) : StringUtils.EMPTY;		
		return integerVal;
	}
	
	/**
	 * @method to length of the string is less than or equal to allowed length
	 * @param String val
	 * @param Integer allowedLen
	 * @return Boolean
	 */
	public static boolean isStrLenExceed(String val, Integer allowedLen) {
		return (StringUtils.isNotBlank(val) && val.length() > allowedLen);
	}
	
	/**
	 * @param fileName
	 * @param ext 
	 * @param ext
	 * @return
	 */
	/*public static String getTemplateFilePath(String fileName, String ext) {
		logger.info("Exiting getTemplateFilePath() ..");
		
		fileName = fileName + ext;
		String appPath = WebUtility.getSession().getServletContext().getRealPath("");
		String filePath = appPath + File.separator + Constants.Templates.TEMPLATE_FOLDER_PATH + File.separator + fileName;
		
		logger.info("Exiting getTemplateFilePath() ..");
		return filePath;
	}*/

	/**
	 * @return String urlStr
	 * @throws IOException
	 */
	public static String getProperty() throws IOException {

		return getPropertyFileValue("urlRedirection.properties", "odex.web.page.url.req", "odex.web.page.url");
	}

	/**
	 * @param fileNm
	 * @param propertyNm1
	 * @param propertyNm2
	 * @return
	 * @throws IOException
	 */
	private static String getPropertyFileValue(String fileNm, String propertyNm1, String propertyNm2) throws IOException {
		InputStream inStream = null;
		String urlStr = StringUtils.EMPTY;
		Properties p = null;
		try {
			p = new Properties();
			Class<?> cl = new Object() {
			}.getClass().getEnclosingClass();
			inStream = cl.getClassLoader().getResourceAsStream(fileNm);
			if (inStream != null) {
				p.load(inStream);
				if (StringUtils.isNotBlank(propertyNm2) && Constants.Y.equalsIgnoreCase(p.getProperty(propertyNm1))) {
					urlStr = p.getProperty(propertyNm2);
				} else if (StringUtils.isBlank(propertyNm2)) {
					urlStr = p.getProperty(propertyNm1);
				}
			}
		} finally {
			if (inStream != null)
				inStream.close();
			if (p != null)
				p.clear();
		}
		return urlStr;
	}

	/**
	 * This function validates the 'val' according to regular expression provided in 'regex'
	 * @param val
	 * @param regex
	 * @return
	 */
	public static boolean isValidFormat(String val, String regex) {
		return ((Pattern.compile(regex)).matcher(val)).matches();
	}

	/**
	 * @param fromAdd
	 * @return
	 */
	public static String getEmailAddress(String fromAdd) {

		String[] intermediateValue = fromAdd.split("<");
		if (intermediateValue.length == 1) {
			return fromAdd;
		}
		intermediateValue = intermediateValue[1].split(">");
		String email = intermediateValue[0];

		return email;
	}
	
	/**
	 * @method used to validate VGM Weight
	 * This function validates if given decimal no is having mentioned no of digits before and after decimal point.
	 * e.g. validateDecimalNo(vgmRequestVo.getTotWt().toString(), 4, 6, 0, 2)
	 * verifies VGM is having 4 to 6 no of digits before decimal point; and 0 to 2 no of digits after decimal point.
	 * This function can also be used to validate a whole no if we pass minDigitsAfterDeciPt = 0 and maxDigitsAfterDeciPt = 0
	 * @param String val
	 * @return boolean
	 */
	public static boolean validateDecimalNo(String val, int minDigitsBeforeDeciPt, int maxDigitsBeforeDeciPt, int minDigitsAfterDeciPt, int maxDigitsAfterDeciPt ) {
		return ((Pattern.compile("^[0-9]{"+minDigitsBeforeDeciPt+","+maxDigitsBeforeDeciPt+"}(\\.[0-9]{"+minDigitsAfterDeciPt+","+maxDigitsAfterDeciPt+"})?$")).matcher(val)).matches();
	}
	
	/**
	 * This method formats decimal no according to format given
	 * @param amount
	 * @return
	 */
	public static String formatAmount(Double amount, String format) {
		return (new DecimalFormat(format)).format(amount);
	}
	
	/**
	 * This method formats decimal no keeping 2 digits after decimal
	 * @param amount
	 * @return
	 */
	public static String formatAmount(Double amount) {
		return formatAmount(amount, Constants.STRING_DECIMAL_FORMAT);
	}

	/**
	 * @Method used for close Output Stream ANd Input stream memory
	 * @param InputStream stream
	 * @param OutputStream os
	 */
	public static String removeNonAsciiCharAndReplaceTabAndNewLine(String inputVal) {
		if (StringUtils.isNotBlank(inputVal)) {
			inputVal = (Normalizer.normalize(inputVal, Normalizer.Form.NFD)).replaceAll(Constants.NON_ASCII_REGEX, StringUtils.EMPTY);
			inputVal = inputVal.replaceAll(Constants.TAB_NEW_LINE_REGEX, Constants.SPACE).trim();
		}
		return inputVal;
	}

	/**
	 * @Method used for replace Non ASCII value with special symbol and Tab and New line with space
	 * @param String inputVal
	 * @return String
	 * @throws IOException 
	 */
	public static String removeNonAsciiSpclCharAndReplaceTabAndNewLineWithSpace(String inputVal) {
		try {
			if (StringUtils.isNotBlank(inputVal)) {
				String regex = getPropertyFileValue("application.properties", Constants.JUNK_CHAR_REG_EXP, "");
				inputVal = inputVal.replaceAll(regex, StringUtils.EMPTY);
				inputVal = inputVal.replaceAll(Constants.TAB_NEW_LINE_REGEX, Constants.SPACE).trim();
			}
		} catch (Exception e) {
			logger.error("IOException in removeNonAsciiSpclCharAndReplaceTabAndNewLineWithSpace : " + e.toString());
			e.printStackTrace();
		}
		return inputVal;
	}

	/**
	 * @Method used for replace Non ASCII value with special symbol and Tab and New line
	 * @param String inputVal
	 * @return String
	 * @throws IOException 
	 */
	public static String removeNonAsciiSpclCharAndReplaceTabAndNewLine(String inputVal) {
		try {
			if (StringUtils.isNotBlank(inputVal)) {
				String regex = getPropertyFileValue("application.properties", Constants.JUNK_CHAR_REG_EXP, "");
				inputVal = inputVal.replaceAll(regex, StringUtils.EMPTY);
				inputVal = inputVal.replaceAll(Constants.TAB_NEW_LINE_REGEX, "").trim();
			}
		} catch (Exception e) {
			logger.error("IOException in removeNonAsciiSpclCharAndReplaceTabAndNewLine : " + e.toString());
			e.printStackTrace();
		}
		return inputVal;
	}

	/**
	 * @Method used for replace Non ASCII value with special symbol and Tab and New line
	 * @param String inputVal
	 * @return String
	 * @throws IOException 
	 */
	public static String removeAllNonAsciiSpclCharAndReplaceTabAndNewLine(String inputVal) {
		try {
			if (StringUtils.isNotBlank(inputVal)) {
				inputVal = (Normalizer.normalize(inputVal, Normalizer.Form.NFD)).replaceAll(Constants.NON_ASCII_REGEX, StringUtils.EMPTY);
				inputVal = inputVal.replaceAll(Constants.TAB_NEW_LINE_REGEX, "").trim();
			}
		} catch (Exception e) {
			logger.error("IOException in removeNonAsciiSpclCharAndReplaceTabAndNewLine : " + e.toString());
			e.printStackTrace();
		}
		return inputVal;
	}

	public static boolean isDoubleValSevenDigitAndTwoDec(String val) {
		String pattern = "^\\d{0,7}(\\.\\d{0,2})?$";
		Pattern p = Pattern.compile(pattern);
		java.util.regex.Matcher m = p.matcher(val);
		return m.matches();
	}

	public static String replaceCommaAndHyphen(String authMobNo) {
		authMobNo = authMobNo.replace(Constants.STRING_HYPHEN, "");
		authMobNo = authMobNo.replace(Constants.SPACE, "");
		return authMobNo;
	}
	
	/**
	 * @method used for replace non alphanumeric character by underscore
	 * @param String val
	 * @return
	 */
	public static String replaceNonAplphaNumByUnderscor(String val) {
		return val.replaceAll("[^a-zA-Z0-9_]", Constants.UNDERSCORE);
	}
	
	public static String replaceNullByEmpty(String val) {
		return val==null?StringUtils.EMPTY:val;
	}
	
	/**
	 * @method used to replace to ASCII value which is not present in asciiValList by another character  
	 * @param String target
	 * @param Character replaceStr
	 * @param List<Integer> asciiValList
	 * @return String string
	 */
	public static String removeAsciiValue(String target, Character replaceStr, List<Integer> asciiValList) {
		
		char chrArry[] = target.toCharArray(); // To convert String into Character Array

		if (chrArry == null) {
			return null;
		} else {
			char newChrArry[] = new char[chrArry.length];
			int i = 0;
			int nVal = 0;
			for (char chr : chrArry) {
				nVal = (int) chr;  //To get ASCII value of character
				if (!asciiValList.contains(nVal)) {
					if (replaceStr != null) {
						newChrArry[i] = replaceStr; // To replace Character by replaceStr
						i++;
					}
				} else {
					newChrArry[i] = chr;
					i++;
				}
			}
			return new String(newChrArry).trim(); // To convert Character into String 
		}

	}

	/**
	 * @param pattern1
	 * @param pattern2
	 * @param text
	 * @return
	 */
	public static List<String> splitSubStringBetween(String pattern1, String pattern2, String text) {
		List<String> strList = new ArrayList<String>();
		Pattern p = Pattern.compile(Pattern.quote(pattern1) + "[\\w\\W]*?" + Pattern.quote(pattern2));
		Matcher m = p.matcher(text);
		while (m.find()) {
			strList.add(m.group());
		}
		return strList;
	}
	
	/**
	 * @param obj
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static Boolean isObjectNotEmpty(Object obj) throws IllegalArgumentException, IllegalAccessException {
		logger.info("Entering isObjectNotEmpty()..");

		Boolean isNotEmpty = false;
		for (Field f : obj.getClass().getDeclaredFields()) {
			f.setAccessible(true);
			if (f.get(obj) != null && f.getName() != Constants.SERIAL_VERSION_ID && (!StringUtils.equalsIgnoreCase(f.getType().toString(), Constants.BOOLEAN_STRING))) {
				isNotEmpty = true;
				break;
			} else {
				isNotEmpty = false;
				continue;
			}
		}

		logger.info("Exiting isObjectNotEmpty()..");
		return isNotEmpty;
	}
//	public static String setProcessUserQuery(UserVo usrVo) throws IllegalArgumentException, IllegalAccessException {
//		StringBuilder sqlQuery = new StringBuilder();
//		String brnchMkr = usrVo.getBrnchMaker();
//		String brnchChkr = usrVo.getBrnchChecker();
//		String hoRole = usrVo.getHoRole();
//		
//		if (StringUtils.equalsIgnoreCase(usrVo.getOfficeType(), Constants.BRNCH_OFFC_TP)) {
//			sqlQuery.append(" and p.frmOffice =:officeId and p.frmOfficeType =:officeType  and p.processStatus != null  ");
//			if (StringUtils.isNotEmpty(brnchMkr) && StringUtils.isNotEmpty(brnchChkr)) {
//				sqlQuery.append(" and ( (p.frmUsrType =:brnchMkr and p.userId =:usrId) or ( frmUsrType =:brnchChkr and p.userId !=:usrId) )");
//    		} else if (StringUtils.isNotEmpty(brnchMkr)) {
//    			sqlQuery.append(" and (p.frmUsrType =:brnchMkr and p.userId =:usrId)");
//    		} else if (StringUtils.isNotEmpty(brnchChkr)) {
//    			sqlQuery.append(" and (p.frmUsrType =:brnchChkr and p.userId !=:usrId)");
//    		}
//		} else {
//			sqlQuery.append("  and p.frmOfficeType =:officeType and p.frmUsrType =:usrType and p.processStatus.proStatusId not in (:statList) ");
//    		
//    		if (StringUtils.isNotEmpty(hoRole) && StringUtils.equalsIgnoreCase(hoRole, "APPROVER")) {
//    			sqlQuery.append(" and ( p.allocatedTo = null or p.allocatedTo =:usrId ) ");
//    		}
//    		
//    		if (usrVo.getAmountLimit() > 0) {
//    			sqlQuery.append(" and p.amount <= :amountLimit"); 
//        	}
//    	}
//		
//		return sqlQuery.toString();
//	}
	
	
//	public static void setProcessUservoParameter(UserVo usrVo, String brnchMkr, String brnchChkr, String hoRole, Query query) {
//		query.setParameter("officeType", usrVo.getOfficeType());
//		
//		if (StringUtils.equalsIgnoreCase(usrVo.getOfficeType(), Constants.BRNCH_OFFC_TP)) {
//			query.setParameter("officeId", usrVo.getOfficeId());
//			query.setParameter("usrId", usrVo.getId());
//			if (StringUtils.isNotEmpty(brnchMkr)) {
//				query.setParameter("brnchMkr", brnchMkr);
//			} 
//			if (StringUtils.isNotEmpty(brnchChkr)) {
//				query.setParameter("brnchChkr", brnchChkr);
//			}
//		} else {
//			List<Integer> statList = new ArrayList<>();
//			statList.add(Constants.TaskStatusEnum.PendingPrevious.getStatusCode());
//			statList.add(Constants.TaskStatusEnum.Stopped.getStatusCode());
//			statList.add(Constants.TaskStatusEnum.Cancelled.getStatusCode());
//			statList.add(Constants.TaskStatusEnum.Reassigned.getStatusCode());
//			statList.add(Constants.TaskStatusEnum.Overdue.getStatusCode());		
//			query.setParameterList("statList", statList);
//			query.setParameter("usrType", usrVo.getHoUser());
//			query.setParameter("officeId", usrVo.getZone());
//			if (StringUtils.isNotEmpty(hoRole) && StringUtils.equalsIgnoreCase(hoRole, "APPROVER")) {
//				query.setParameter("usrId", usrVo.getId());
//			}
//			if (usrVo.getAmountLimit() > 0) {
//				query.setParameter("amountLimit", new BigDecimal(usrVo.getAmountLimit()));
//			}
//		}
//	}

//	public static String setProcessViewUserQuery(UserVo usrVo) {
//		StringBuilder sqlQuery = new StringBuilder();
//		String brnchMkr = usrVo.getBrnchMaker();
//		String brnchChkr = usrVo.getBrnchChecker();
//		String hoRole = usrVo.getHoRole();
//		
//		if (StringUtils.equalsIgnoreCase(usrVo.getOfficeType(), Constants.BRNCH_OFFC_TP)) {
//			sqlQuery.append(" and p.frmOffice =:officeId and p.frmOfficeType =:officeType    ");
//			if (StringUtils.isNotEmpty(brnchMkr) && StringUtils.isNotEmpty(brnchChkr)) {
//				sqlQuery.append(" and ( (p.frmUsrType =:brnchMkr and p.userId =:usrId) or ( p.frmUsrType =:brnchChkr and p.userId !=:usrId) )");
//    		} else if (StringUtils.isNotEmpty(brnchMkr)) {
//    			sqlQuery.append(" and (p.frmUsrType =:brnchMkr and p.userId =:usrId)");
//    		} else if (StringUtils.isNotEmpty(brnchChkr)) {
//    			sqlQuery.append(" and (p.frmUsrType =:brnchChkr and p.userId !=:usrId)");
//    		}
//		} else {
//			sqlQuery.append("  and p.frmOffice =:officeId and p.frmOfficeType =:officeType and p.frmUsrType =:usrType and p.statusId not in (:statList) ");
//    		
//    		if (StringUtils.isNotEmpty(hoRole) && StringUtils.equalsIgnoreCase(hoRole, "APPROVER")) {
//    			sqlQuery.append(" and ( p.allocatedTo is null or p.allocatedTo =:usrId ) ");
//    		}
//    		
//    		if (usrVo.getAmountLimit() > 0) {
//    			sqlQuery.append(" and p.amount <= :amountLimit"); 
//        	}
//    	}
//		
//		return sqlQuery.toString();
//	}
	
	//---------------------number to word convert-------------------------------------------------------------------------
	
	static public class ScaleUnit {
        private int exponent;
        private String[] names;
        private ScaleUnit(int exponent, String...names) {
            this.exponent = exponent;
            this.names = names;
        }
        public int getExponent() {
            return exponent;
        }
        public String getName(int index) {
            return names[index];
        }
    }

    /**
     * See http://www.wordiq.com/definition/Names_of_large_numbers
     */
    static private ScaleUnit[] SCALE_UNITS = new ScaleUnit[] {
        new ScaleUnit(63, "vigintillion", "decilliard"),
        new ScaleUnit(60, "novemdecillion", "decillion"),
        new ScaleUnit(57, "octodecillion", "nonilliard"),
        new ScaleUnit(54, "septendecillion", "nonillion"),
        new ScaleUnit(51, "sexdecillion", "octilliard"),
        new ScaleUnit(48, "quindecillion", "octillion"),
        new ScaleUnit(45, "quattuordecillion", "septilliard"),
        new ScaleUnit(42, "tredecillion", "septillion"),
        new ScaleUnit(39, "duodecillion", "sextilliard"),
        new ScaleUnit(36, "undecillion", "sextillion"),
        new ScaleUnit(33, "decillion", "quintilliard"),
        new ScaleUnit(30, "nonillion", "quintillion"),
        new ScaleUnit(27, "octillion", "quadrilliard"),
        new ScaleUnit(24, "septillion", "quadrillion"),
        new ScaleUnit(21, "sextillion", "trilliard"),
        new ScaleUnit(18, "quintillion", "trillion"),
        new ScaleUnit(15, "quadrillion", "billiard"),
        new ScaleUnit(12, "trillion", "billion"),
        new ScaleUnit(9, "billion", "milliard"),
        new ScaleUnit(6, "million", "million"),
        new ScaleUnit(3, "thousand", "thousand"),
        new ScaleUnit(2, "hundred", "hundred"),
        //new ScaleUnit(1, "ten", "ten"),
        //new ScaleUnit(0, "one", "one"),
        new ScaleUnit(-1, "tenth", "tenth"),
        new ScaleUnit(-2, "hundredth", "hundredth"),
        new ScaleUnit(-3, "thousandth", "thousandth"),
        new ScaleUnit(-4, "ten-thousandth", "ten-thousandth"),
        new ScaleUnit(-5, "hundred-thousandth", "hundred-thousandth"),
        new ScaleUnit(-6, "millionth", "millionth"),
        new ScaleUnit(-7, "ten-millionth", "ten-millionth"),
        new ScaleUnit(-8, "hundred-millionth", "hundred-millionth"),
        new ScaleUnit(-9, "billionth", "milliardth"),
        new ScaleUnit(-10, "ten-billionth", "ten-milliardth"),
        new ScaleUnit(-11, "hundred-billionth", "hundred-milliardth"),
        new ScaleUnit(-12, "trillionth", "billionth"),
        new ScaleUnit(-13, "ten-trillionth", "ten-billionth"),
        new ScaleUnit(-14, "hundred-trillionth", "hundred-billionth"),
        new ScaleUnit(-15, "quadrillionth", "billiardth"),
        new ScaleUnit(-16, "ten-quadrillionth", "ten-billiardth"),
        new ScaleUnit(-17, "hundred-quadrillionth", "hundred-billiardth"),
        new ScaleUnit(-18, "quintillionth", "trillionth"),
        new ScaleUnit(-19, "ten-quintillionth", "ten-trillionth"),
        new ScaleUnit(-20, "hundred-quintillionth", "hundred-trillionth"),
        new ScaleUnit(-21, "sextillionth", "trilliardth"),
        new ScaleUnit(-22, "ten-sextillionth", "ten-trilliardth"),
        new ScaleUnit(-23, "hundred-sextillionth", "hundred-trilliardth"),
        new ScaleUnit(-24, "septillionth","quadrillionth"),
        new ScaleUnit(-25, "ten-septillionth","ten-quadrillionth"),
        new ScaleUnit(-26, "hundred-septillionth","hundred-quadrillionth"),
    };

    static public enum Scale {
        SHORT,
        LONG;

        public String getName(int exponent) {
            for (ScaleUnit unit : SCALE_UNITS) {
                if (unit.getExponent() == exponent) {
                    return unit.getName(this.ordinal());
                }
            }
            return ""; 
        }
    }

    /**
     * Change this scale to support American and modern British value (short scale)
     * or Traditional British value (long scale)
     */
    static public Scale SCALE = Scale.SHORT; 


    static abstract public class AbstractProcessor {

        static protected final String SEPARATOR = " ";
        static protected final int NO_VALUE = -1;

        protected List<Integer> getDigits(long value) {
            ArrayList<Integer> digits = new ArrayList<Integer>();
            if (value == 0) {
                digits.add(0);
            } else {
                while (value > 0) {
                    digits.add(0, (int) value % 10);
                    value /= 10;
                }
            }
            return digits;
        }

        public String getName(long value) {
            return getName(Long.toString(value));
        }

        public String getName(double value) {
            return getName(Double.toString(value));
        }

        abstract public String getName(String value);
    }

    static public class UnitProcessor extends AbstractProcessor {

        static private final String[] TOKENS = new String[] {
            "one", "two", "three", "four", "five", "six", "seven", "eight", "nine",
            "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"
        };

        @Override
        public String getName(String value) {
            StringBuilder buffer = new StringBuilder();

            int offset = NO_VALUE;
            int number;
            if (value.length() > 3) {
                number = Integer.valueOf(value.substring(value.length() - 3), 10);
            } else {
                number = Integer.valueOf(value, 10);
            }
            number %= 100;
            if (number < 10) {
                offset = (number % 10) - 1;
                //number /= 10;
            } else if (number < 20) {
                offset = (number % 20) - 1;
                //number /= 100;
            }

            if (offset != NO_VALUE && offset < TOKENS.length) {
                buffer.append(TOKENS[offset]);
            }

            return buffer.toString();
        }

    }

    static public class TensProcessor extends AbstractProcessor {

        static private final String[] TOKENS = new String[] {
            "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"
        };

        static private final String UNION_SEPARATOR = " ";

        private UnitProcessor unitProcessor = new UnitProcessor();

        @Override
        public String getName(String value) {
            StringBuilder buffer = new StringBuilder();
            boolean tensFound = false;

            int number;
            if (value.length() > 3) {
                number = Integer.valueOf(value.substring(value.length() - 3), 10);
            } else {
                number = Integer.valueOf(value, 10);
            }
            number %= 100;   // keep only two digits
            if (number >= 20) {
                buffer.append(TOKENS[(number / 10) - 2]);
                number %= 10;
                tensFound = true;
            } else {
                number %= 20;
            }

            if (number != 0) {
                if (tensFound) {
                    buffer.append(UNION_SEPARATOR);
                }
                buffer.append(unitProcessor.getName(number));
            }

            return buffer.toString();
        }
    }

    static public class HundredProcessor extends AbstractProcessor {

        private int EXPONENT = 2;

        private UnitProcessor unitProcessor = new UnitProcessor();
        private TensProcessor tensProcessor = new TensProcessor();

        @Override
        public String getName(String value) {
            StringBuilder buffer = new StringBuilder();
            try {
            int number;
            if (value.isEmpty()) {
                number = 0;
            } else if (value.length() > 4) {
                number = Integer.valueOf(value.substring(value.length() - 4), 10);
            } else {
                number = Integer.valueOf(value, 10);
            }
            number %= 1000;  // keep at least three digits

            if (number >= 100) {
                buffer.append(unitProcessor.getName(number / 100));
                buffer.append(SEPARATOR);
                buffer.append(SCALE.getName(EXPONENT));
            }

            String tensName = tensProcessor.getName(number % 100);

            if (!tensName.isEmpty() && (number >= 100)) {
                buffer.append(SEPARATOR);
            }
            buffer.append(tensName);
            } catch (Exception e) {
            	e.printStackTrace();
            }

            return buffer.toString();
        }
    }

    static public class CompositeBigProcessor extends AbstractProcessor {

        private HundredProcessor hundredProcessor = new HundredProcessor();
        private AbstractProcessor lowProcessor;
        private int exponent;

        public CompositeBigProcessor(int exponent) {
            if (exponent <= 3) {
                lowProcessor = hundredProcessor;
            } else {
                lowProcessor = new CompositeBigProcessor(exponent - 3);
            }
            this.exponent = exponent;
        }

        public String getToken() {
            return SCALE.getName(getPartDivider());
        }

        protected AbstractProcessor getHighProcessor() {
            return hundredProcessor;
        }

        protected AbstractProcessor getLowProcessor() {
            return lowProcessor;
        }

        public int getPartDivider() {
            return exponent;
        }

        @Override
        public String getName(String value) {
            StringBuilder buffer = new StringBuilder();

            String high, low;
            if (value.length() < getPartDivider()) {
                high = "";
                low = value;
            } else {
                int index = value.length() - getPartDivider();
                high = value.substring(0, index);
                low = value.substring(index);
            }

            String highName = getHighProcessor().getName(high);
            String lowName = getLowProcessor().getName(low);

            if (!highName.isEmpty()) {
                buffer.append(highName);
                buffer.append(SEPARATOR);
                buffer.append(getToken());

                if (!lowName.isEmpty()) {
                    buffer.append(SEPARATOR);
                }
            }

            if (!lowName.isEmpty()) {
                buffer.append(lowName);
            }

            return buffer.toString();
        }
    }

    static public class DefaultProcessor extends AbstractProcessor {

        static private String MINUS = "minus";
        static private String UNION_AND = "and";

        static private String ZERO_TOKEN = "zero";

        private AbstractProcessor processor = new CompositeBigProcessor(63);

        @Override
        public String getName(String value) {
            boolean negative = false;
            if (value.startsWith("-")) {
                negative = true;
                value = value.substring(1);
            }

            int decimals = value.indexOf(".");
            String decimalValue = null;
            if (0 <= decimals) {
                decimalValue = value.substring(decimals + 1);
                value = value.substring(0, decimals);
            }

            String name = processor.getName(value);

            if (name.isEmpty()) {
                name = ZERO_TOKEN;
            } else if (negative) {
                name = MINUS.concat(SEPARATOR).concat(name); 
            }

            if (!(null == decimalValue || decimalValue.isEmpty())) {
                name = name.concat(SEPARATOR).concat(UNION_AND).concat(SEPARATOR)
                    .concat(processor.getName(decimalValue))
                    .concat(SEPARATOR).concat(SCALE.getName(-decimalValue.length()));
            }

            return name;
        }

    }

    static public AbstractProcessor processor;
    public static String getNumToWords(String val) {
    	processor = new DefaultProcessor();
    	String word = StringUtils.EMPTY;
	    try {
	    	word = processor.getName(val);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
		return word.toUpperCase();
    }
    public static String getNumToWords(Long val) {
    	processor = new DefaultProcessor();
    	String word = StringUtils.EMPTY;
	    try {
	    	word = processor.getName(val);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
		return word.toUpperCase();
    }

    public static void main(String...args) {

        processor = new DefaultProcessor();

        long[] values = new long[] {
            0,
            4,
            10,
            12,
            100,
            108,
            299,
            1000,
            1003,
            2040,
            45213,
            100000,
            100005,
            100010,
            202020,
            202022,
            999999,
            1000000,
            1000001,
            10000000,
            10000007,
            99999999,
            Long.MAX_VALUE,
            Long.MIN_VALUE
        };

        String[] strValues = new String[] {
            "0001",
            "3500",
            "3.141592",
            "1000013.222"
        };

        for (long val : values) {
            System.out.println(val + " = " + processor.getName(val) );
        }

        for (String strVal : strValues) {
            System.out.println(strVal + " = " + processor.getName(strVal) );
        }

        // generate a very big number...
        StringBuilder bigNumber = new StringBuilder();
        for (int d=0; d<66; d++) {
            bigNumber.append( (char) ((Math.random() * 10) + '0'));
        }
        bigNumber.append(".");
        for (int d=0; d<26; d++) {
            bigNumber.append( (char) ((Math.random() * 10) + '0'));
        }

        System.out.println(bigNumber.toString() + " = " + processor.getName(bigNumber.toString()));

    }
}
