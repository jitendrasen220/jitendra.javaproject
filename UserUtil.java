package org.dms.DMS.util;


import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

@Component
public class UserUtil {
	public static final List<String> getUserRoles(){
		return Arrays.asList("SUPER_ADMIN","ADMIN","MANAGER","EMPLOYEE");
	}
	
	
	
	public List<String> getShipmentGrades(){
		return Arrays.asList("A","B","C");
	}
	
	public void uiComponent(ModelMap map) {
		map.addAttribute("horole", getUserRoles());
		//map.addAttribute("shipmentGrade", getShipmentGrades());
	}
}

