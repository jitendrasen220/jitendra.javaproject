package org.dms.DMS.service;

import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.dms.DMS.exceptions.DMSException;
import org.dms.DMS.model.CityVo;
import org.dms.DMS.model.CompanyVo;
import org.dms.DMS.model.CountryVo;
import org.dms.DMS.model.DeptVo;
import org.dms.DMS.model.PaginationResult;
import org.dms.DMS.model.RoleVo;
import org.dms.DMS.model.StateVo;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
@Transactional
public interface CityService {

	public Integer saveCity(CityVo vo);

	public Object getAllCountry();

	public CountryVo getCountryCode(Integer cntryId);

	List<StateVo> getStateByCntryId(Integer cntryId);

	public List<CityVo> getCityByStateId(Integer stateId);

	public Object getAllDepartment();

	public List<CompanyVo> getAllCompany();

	public Object getAllRole();

	public List<StateVo> getAllStateList();

	public PaginationResult<CountryVo> getCountryDtlsWithPagination(int page, int maxResult, int maxNavigationPage, String likeName);

	public PaginationResult<StateVo> getStateDtlsWithPagination(int page, int maxResult, int maxNavigationPage, String likeName);

	public PaginationResult<CityVo> getCityDtlsWithPagination(int page, int maxResult, int maxNavigationPage, String likeName);

	public Integer saveCountry(CountryVo vo);

	public Map<String, Object> getCountryDtlsByVo(CountryVo vo);

	public Integer updateCountry(CountryVo vo);

	public Integer saveState(StateVo vo);

	public Map<String, Object> getStateDtlsByVo(StateVo vo);

	public Integer updateState(StateVo vo);

	public Map<String, Object> getCityDtlsByVo(CityVo vo) throws DMSException;

	public Integer updateCity(CityVo vo);

	public Object getSelectedCountry();

	public List<RoleVo> getRoleIdByDeptId(Integer deptId);

	public List<DeptVo> getDeptIdBycompanyId(Integer companyId);

/*	public List<DeptVo> getDeptByCompId(Integer companyId);*/
}
