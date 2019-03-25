package org.dms.DMS.dao;

import java.util.List;

import javax.transaction.Transactional;

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
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

public interface CityDao {

	void saveCity(City city);

	List<CountryVo> getAllCountry() throws DMSException;

	List<StateVo> getStateByCntryId(Integer cntryId);

	List<CityVo> getCityByStateId(Integer stateId);

	CountryVo getCountryCode(Integer cntryId) throws DMSException;

	City getCityById(Integer id) throws DMSException;

	List<DeptVo> getAllDepartment() throws DMSException;

	List<CompanyVo> getAllCompany();

	List<RoleVo> getAllRole() throws DMSException;

	public List<StateVo> getAllState() throws DMSException;
	
	public PaginationResult<CountryVo> getCountryDtlsWithPagination(int page, int maxResult, int maxNavigationPage,
			String likeName);

	public PaginationResult<StateVo> getStateDtlsWithPagination(int page, int maxResult, int maxNavigationPage,
			String likeName);

	public PaginationResult<CityVo> getCityDtlsWithPagination(int page, int maxResult, int maxNavigationPage,
			String likeName);

	public void saveCountry(Country cntry);

	public Country getCountryDtls(CountryVo vo)throws DMSException;

	public void updateCountry(Country cntry);

	public void saveState(State st);

	public State getStateDtls(StateVo vo)throws DMSException;

	public void updateState(State st);

	public City getCityDtls(CityVo vo)throws DMSException;

	public void updateCity(City city);

	Boolean checkIsCountryExist(String name, Integer countryId);

	 Boolean checkIsStateExist(String name, Integer stateId);

	 Boolean checkIsCityExist(String name, Integer cityId);

	public List<Country> getSelectedCountry()  throws DMSException;
	
	List<DeptVo> getDeptIdBycompanyId(Integer companyId);

	List<RoleVo> getRoleByDeptId(Integer deptId);
}
