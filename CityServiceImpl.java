package org.dms.DMS.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.dms.DMS.dao.CityDao;
import org.dms.DMS.entity.City;
import org.dms.DMS.entity.Country;
import org.dms.DMS.entity.State;
import org.dms.DMS.exceptions.DMSException;
import org.dms.DMS.model.CityVo;
import org.dms.DMS.model.CompanyVo;
import org.dms.DMS.model.CountryVo;
import org.dms.DMS.model.DeptVo;
import org.dms.DMS.model.MasterRoleVo;
import org.dms.DMS.model.PaginationResult;
import org.dms.DMS.model.RoleVo;
import org.dms.DMS.model.StateVo;
import org.dms.DMS.util.AESencyrptor;
import org.dms.DMS.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service(value = "cityService")
@Component
@Transactional
public class CityServiceImpl implements CityService {

	private static final Logger logger = Logger.getLogger(CityServiceImpl.class);

	@Autowired
	// @Resource(name = "cityDao")
	private CityDao cityDao;

	@Override
	public List<RoleVo> getAllRole() {
		logger.info("Entering ProcessTypeMstServiceImpl getAllRole() ");
		List<RoleVo> roleList = null;
		try {
			roleList = cityDao.getAllRole();
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION, e);
		}
		logger.info("Exit from ProcessTypeMstServiceImpl getAllDepartment() ");
		return roleList;
	}

	@Override
	public List<DeptVo> getAllDepartment() {
		logger.info("Entering ProcessTypeMstServiceImpl getAllDepartment() ");
		List<DeptVo> departmentList = null;
		try {
			departmentList = cityDao.getAllDepartment();
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION, e);
		}
		logger.info("Exit from ProcessTypeMstServiceImpl getAllDepartment() ");
		return departmentList;
	}

	@Override
	public List<CompanyVo> getAllCompany() {
		logger.info("Entering ProcessTypeMstServiceImpl getAllDepartment() ");
		List<CompanyVo> companyList = null;
		try {
			companyList = cityDao.getAllCompany();
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION, e);
		}
		logger.info("Exit from ProcessTypeMstServiceImpl getAllDepartment() ");
		return companyList;
	}

	@Override
	public List<StateVo> getStateByCntryId(Integer cntryId) {
		logger.info("Entering getStateByCntryId() ");
		List<StateVo> stateList = null;
		try {
			stateList = cityDao.getStateByCntryId(cntryId);
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION, e);
		}
		logger.info("Entering getStateByCntryId() ");
		return stateList;
	}

	@Override
	public Integer saveCity(CityVo vo) {
		logger.info("Enter into saveCity()..");
		Integer id = null;
		try {
			City city = new City();
			populateCityDtl(city, vo);
			cityDao.saveCity(city);
			id = city.getCityId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Exit from saveState()..");
		return id;
	}

	private void populateCityDtl(City city, CityVo vo) {
		logger.info("Enter into populateCityDtl()..");
		city.setCityId(vo.getCityId());
		city.setName(vo.getName());
		State st = new State();
		st.setStateId(vo.getStateId());
		city.setState(st);
		logger.info("Exit from populateCityDtl()..");

	}

	@Override
	public List<CountryVo> getAllCountry() {
		logger.info("Entering getAllCountry() ");
		List<CountryVo> stateList = null;
		try {
			stateList = cityDao.getAllCountry();
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION, e);
			/*
			 * throw new DMSException(e.getMessage());
			 */ }
		logger.info("Entering getAllCountry() ");
		return stateList;
	}

	@Override
	public List<CityVo> getCityByStateId(Integer stateId) {
		logger.info("Entering getCityByStateId() ");
		List<CityVo> cityList = null;
		try {
			cityList = cityDao.getCityByStateId(stateId);
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION, e);
		}
		logger.info("Entering getCityByStateId() ");
		return cityList;
	}

	@Override
	public CountryVo getCountryCode(Integer cntryId) {
		logger.info("Entering getStateByCntryId() ");
		CountryVo vo = null;
		try {
			vo = cityDao.getCountryCode(cntryId);
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION, e);
		}
		logger.info("Entering getStateByCntryId() ");
		return vo;
	}

	
	
	//-----------------------

	@Override
	public List<StateVo> getAllStateList() {
		logger.info("Entering getAllStateList() ");
		List<StateVo> allStateList = null;
		try {
			allStateList = cityDao.getAllState();
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION, e);
		}
		logger.info("Exit from getAllStateList() ");
		return allStateList;
	}

	@Override
	public PaginationResult<CountryVo> getCountryDtlsWithPagination(int page, int maxResult, int maxNavigationPage,
			String likeName) {
		return cityDao.getCountryDtlsWithPagination(page, maxResult, maxNavigationPage, likeName);
	}

	@Override
	public PaginationResult<StateVo> getStateDtlsWithPagination(int page, int maxResult, int maxNavigationPage,
			String likeName) {
		// TODO Auto-generated method stub
		return cityDao.getStateDtlsWithPagination(page, maxResult, maxNavigationPage, likeName);
	}

	@Override
	public PaginationResult<CityVo> getCityDtlsWithPagination(int page, int maxResult, int maxNavigationPage,
			String likeName) {
		// TODO Auto-generated method stub
		return cityDao.getCityDtlsWithPagination(page, maxResult, maxNavigationPage, likeName);
	}

	@Override
	public Integer saveCountry(CountryVo vo) {
		logger.info("Enter into saveCountry()..");
		Integer id = null;
		try {
			Country cntry = new Country();			
			populateCountryDtl(cntry, vo);
	        cityDao.saveCountry(cntry);
	        id = cntry.getCountryId();
		} catch(Exception e) {
			e.printStackTrace();
		}
		logger.info("Exit from saveCountry()..");
		return id;
	}

	private void populateCountryDtl(Country cntry, CountryVo vo) throws Exception {
		logger.info("Enter into populateCountryDtl()..");
		cntry.setCountryId(vo.getCountryId());
		cntry.setName(vo.getName());
		cntry.setCode(vo.getCode());
		cntry.setAlfaCode(vo.getAlfaCode());
		logger.info("Exir from populateCountryDtl()..");

	}

	@Override
	public Map<String, Object> getCountryDtlsByVo(CountryVo vo) {
		logger.info("Entering into getCountryDtlsByVo()..");
		Map<String, Object> map = new HashMap<String, Object>();
		Country cntry = null;
		try {
			cntry = getCountryDtlsById(vo.getCountryId());
			setCountryDtlsToVo(vo, cntry);
			map.put("countryVo", vo);
		} catch (DMSException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Exit getBrnchDtlsByVo()..");
		return map;
	}

	private void setCountryDtlsToVo(CountryVo vo, Country cntry) throws Exception {
		try {
		vo.setEncId(AESencyrptor.encrypt(cntry.getCountryId().toString()));			
		vo.setName(cntry.getName());
		vo.setCode(cntry.getCode());
		vo.setAlfaCode(cntry.getAlfaCode());
		} catch (DMSException e) {
			throw new DMSException(e);
		}
	}

	private Country getCountryDtlsById(Integer countryId) throws DMSException {
		logger.info("Entering into getCountryDtlsById()..");
		Country cntry = null;
		try {
			CountryVo vo = new CountryVo();
			vo.setCountryId(countryId);
			cntry = cityDao.getCountryDtls(vo);
		} catch (Exception e) {
			throw new DMSException(e);
		}
		logger.info("Exit getCountryDtlsById()..");
		return cntry;
	}

	@Override
	public Integer updateCountry(CountryVo vo) {
		logger.info("Enter updateCountry()..");
		Integer id = null;
		try {
			Country cntry = getCountryDtlsById(vo.getCountryId());
			populateCountryDtl(cntry, vo);
			cityDao.updateCountry(cntry);
			id = cntry.getCountryId();
		} catch(Exception e) {
			e.printStackTrace();
		}
		logger.info("Exit updateCountry()..");
		return id;
	}
	
	public Integer saveState(StateVo vo) {
		logger.info("Enter into saveState()..");
		Integer id = null;
		try {
			State st = new State();
			populateStateDtl(st, vo);
	        cityDao.saveState(st);
	        id = st.getStateId();
		} catch(Exception e) {
			e.printStackTrace();
		}
		logger.info("Exit from saveState()..");
		return id;
	}

	private void populateStateDtl(State st, StateVo vo) {
		logger.info("Enter into populateStateDtl()..");

		try {
		st.setStateId(vo.getStateId());
		st.setName(vo.getName());
		Country cntry = new Country();
		cntry.setCountryId(vo.getCountryId());
		st.setCountry(cntry);
		
		logger.info("Exit From populateStateDtl()..");	
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Map<String, Object> getStateDtlsByVo(StateVo vo) {
		logger.info("Entering into getStateDtlsByVo()..");
		Map<String, Object> map = new HashMap<String, Object>();
		State st = null;
		try {
			st = getStateDtlsById(vo.getStateId());
			setStateDtlsToVo(vo, st);
			map.put("stateVo", vo);
		} catch (DMSException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Exit getBrnchDtlsByVo()..");
		return map;
	}

	private void setStateDtlsToVo(StateVo vo, State st) throws Exception {
		vo.setEncId(AESencyrptor.encrypt(st.getStateId().toString()));			
		vo.setName(st.getName());
		Country cntry = st.getCountry();
		vo.setCountryId(cntry.getCountryId());
	}

	private State getStateDtlsById(Integer stateId) throws DMSException {
		logger.info("Entering into getStateDtlsById()..");
		State st = null;
		try {
			StateVo vo = new StateVo();
			vo.setStateId(stateId);
			st = cityDao.getStateDtls(vo);
		} catch (Exception e) {
			throw new DMSException(e);
		}
		logger.info("Exit getStateDtlsById()..");
		return st;
	}

	@Override
	public Integer updateState(StateVo vo) {
		logger.info("Enter updateState()..");
		Integer id = null;
		try {
			State st = getStateDtlsById(vo.getStateId());
			populateStateDtl(st, vo);
			cityDao.updateState(st);
			id = st.getStateId();
		} catch(Exception e) {
			e.printStackTrace();
		}
		logger.info("Exit updateState()..");
		return id;
	}

	@Override
	public Map<String, Object> getCityDtlsByVo(CityVo vo) throws DMSException {
		logger.info("Entering into getCityDtlsByVo()..");
		Map<String, Object> map = new HashMap<String, Object>();
		City city = null;
		try {
			city = getCityDtlsById(vo.getCityId());
			setCityDtlsToVo(vo, city);
			map.put("cityVo", vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Exit getCityDtlsByVo()..");
		return map;
	}

	private void setCityDtlsToVo(CityVo vo, City city) throws Exception {
		vo.setEncId(AESencyrptor.encrypt(city.getCityId().toString()));			
			vo.setName(city.getName());
			vo.setStateId(city.getState().getStateId());
			vo.setCountryId(city.getState().getCountry().getCountryId());
	}

	private City getCityDtlsById(Integer cityId) throws DMSException {
		logger.info("Entering into getCityDtlsById()..");
		City city = null;
		try {
			CityVo vo = new CityVo();
			vo.setCityId(cityId);
			city = cityDao.getCityDtls(vo);
			logger.info("Exit getCityDtlsById()..");

		} catch (Exception e) {
			throw new DMSException(e);
		}
		return city;
	}

	@Override
	public Integer updateCity(CityVo vo) {
		logger.info("Enter updateCity()..");
		Integer id = null;
		try {
			City city = getCityDtlsById(vo.getCityId());
			populateCityDtl(city, vo);
			cityDao.updateCity(city);
			id = city.getCityId();
		} catch(Exception e) {
			e.printStackTrace();
		}
		logger.info("Exit updateState()..");
		return id;
	}

	@Override
	public Object getSelectedCountry() {
		logger.info("Entering getAllCountry() ");
		List<Country> stateList = null;
		try {
		stateList = cityDao.getSelectedCountry();
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION, e);
	}
		logger.info("Entering getAllCountry() ");
		return stateList;
	}
	
	@Override
	public List<RoleVo> getRoleIdByDeptId(Integer deptId) {
		logger.info("Entering getRoleIdByDeptId() ");
		List<RoleVo> roleList = null;
		try {
			System.err.println("roleList"+roleList);
			roleList = cityDao.getRoleByDeptId(deptId);
			System.err.println("After .....roleList"+roleList);
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION, e);
		}
		logger.info("Entering getRoleIdByDeptId() ");
		return roleList;
	}

	@Override
	public List<DeptVo> getDeptIdBycompanyId(Integer companyId) {
		logger.info("Entering getDeptIdBycompanyId() ");
		List<DeptVo> deptList = null;
		try {
			deptList = cityDao.getDeptIdBycompanyId(companyId);
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION, e);
		}
		logger.info("Entering getDeptIdBycompanyId() ");
		return deptList;
	}
}
