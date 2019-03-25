package org.dms.DMS.ajaxController;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dms.DMS.model.CityVo;
import org.dms.DMS.model.CountryVo;
import org.dms.DMS.model.DeptVo;
import org.dms.DMS.model.RoleVo;
import org.dms.DMS.model.StateVo;
import org.dms.DMS.service.CityService;
import org.dms.DMS.service.UserService;
import org.dms.DMS.util.ApplicationContextHelper;
import org.dms.DMS.util.Constants;
import org.dms.DMS.util.DMSBaseController;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

//Enable Hibernate Transaction.
@Transactional
@EnableWebMvc
public class CityAjaxController extends DMSBaseController  {
	private static final Logger logger = Logger.getLogger(CityAjaxController.class);
	
	CityService cityService = null;
	
	public List<StateVo> getStateByCntryId(Integer cntryId) {
		logger.info("Entering getStateByCntryId() ");
		
		List<StateVo> stateList =  new ArrayList<>();
		try {
			cityService = ApplicationContextHelper.getInstance().getManagedBean("cityService", CityService.class);
			stateList = cityService.getStateByCntryId(cntryId);
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION , e);
		}
		logger.info("Entering getStateByCntryId() ");
		return stateList;
	}
	
	public CountryVo getCountryCode(Integer cntryId) {
		logger.info("Entering getCountryCode() ");
		
		CountryVo vo = null;
		try {
			cityService = ApplicationContextHelper.getInstance().getManagedBean("cityService", CityService.class);
			vo = cityService.getCountryCode(cntryId);
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION , e);
		}
		logger.info("Exiting getCountryCode() ");
		return vo;
	}
	
	public List<CityVo> getCityByStateId(Integer stateId) {
		logger.info("Entering getCityByStateId() ");
		
		List<CityVo> cityList =  new ArrayList<>();
		try {
			cityService = ApplicationContextHelper.getInstance().getManagedBean("cityService", CityService.class);
			cityList = cityService.getCityByStateId(stateId);
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION , e);
		}
		logger.info("Entering getCityByStateId() ");
		return cityList;
	}
	
	public List<DeptVo> getDeptIdBycompanyId(Integer companyId) {
		logger.info("Entering getDeptIdBycompanyId() ");
		
		List<DeptVo> deptList =  new ArrayList<>();
		try {
			cityService = ApplicationContextHelper.getInstance().getManagedBean("cityService", CityService.class);
			deptList = cityService.getDeptIdBycompanyId(companyId);
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION , e);
		}
		logger.info("Entering getDeptIdBycompanyId() ");
		return deptList;
	}
	
	public List<RoleVo> getRoleIdByDeptId(Integer deptId) {
		logger.info("Entering getRoleIdByDeptId() .........."+deptId);		
		List<RoleVo> roleList =  new ArrayList<>();
		try {
			System.err.println("Before call roleList...."+roleList);
			cityService = ApplicationContextHelper.getInstance().getManagedBean("cityService", CityService.class);
			roleList = cityService.getRoleIdByDeptId(deptId);
			System.err.println("roleList...."+roleList);
		} 
		catch (Exception e) {
			logger.error(Constants.EXCEPTION , e);
		}
		logger.info("Entering getRoleIdBycompanyId() ");
		return roleList;
	}

}
