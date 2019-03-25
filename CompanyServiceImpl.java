package org.dms.DMS.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.dms.DMS.dao.CompanyDao;
import org.dms.DMS.entity.Address;
import org.dms.DMS.entity.City;
import org.dms.DMS.entity.Company;
import org.dms.DMS.entity.Country;
import org.dms.DMS.entity.State;
import org.dms.DMS.exceptions.DMSException;
import org.dms.DMS.model.PaginationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service(value = "companyService")
@Component
@Transactional
public class CompanyServiceImpl implements CompanyService {

	private static final Logger logger = Logger.getLogger(UserServiceImpl.class);

	/* @Resource(name = "userDao") */
	@Autowired
	private CompanyDao companyDao;


	@Override
	public PaginationResult<Company> getCompanyCompanyWithPagination(int page, int maxResult, int maxNavigationPage,Company vo) {
		return companyDao.getCompanyDtlsWithPagination(page, maxResult, maxNavigationPage, vo);
	}

	/*@Override
	public List<Company> getCmpnyDtls(String companyCode, String companyName) {

		List<Company> companyList = new ArrayList<>();	
		try {
			CompanyVo vo = new CompanyVo();
			vo.setCompanyCode(companyCode);
			vo.setCompanyName(companyName);
			// usr = userDAO.getUsrDtls(vo);

			if (StringUtils.isBlank(vo.getCompanyCode()) && StringUtils.isBlank("" + vo.getCompanyName())) {
				companyList = companyDao.getCompanies();
				// userList.add(usr);
			} else if (StringUtils.isNotBlank(vo.getCompanyCode())
					&& StringUtils.isNotBlank("" + vo.getCompanyName())) {
				companyList = companyDao.getCompanyListwithCompCodeAndCompName(companyCode, companyName);
				// userList.add(usr);
			}

			else if (StringUtils.isNotBlank(vo.getCompanyCode())) {
				companyList = companyDao.getCompanyListwithcompanyCode(companyCode);			
			}

			else {
				companyList = companyDao.getCompanyListwithcompanyName(companyName);				
			}

		} catch (Exception e) {
			try {
				throw new DMSException(e);
			} catch (DMSException e1) {				
				e1.printStackTrace();
			}
		}
		return companyList;
	}*/

	@Override
	public Integer updateCompany(Company vo) {
		logger.info("Enter updateCompany()..");
		Integer id = null;
		try {
			Company company = getCompanyDtlsByCompId(vo.getCompanyId());
			company.setCompanyType(vo.getCompanyType());
			setCompanyDtls(vo, company);
			companyDao.updateUser(company);
			id = company.getCompanyId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Exit updateUser()..");
		return id;
	}

	@Override
	public boolean getCompanywithCode(String companyCode) {

		return companyDao.getCompanywithCode(companyCode);
	}

	@Override
	public boolean getCompanywithDomain(String domain) {

		return companyDao.getCompanywithDomain(domain);
	}

	@Override
	public boolean getCompanywithName(String companyName) {

		return companyDao.getCompanywithName(companyName);
	}

	@Override
	public Integer saveCompany(Company vo) {
		logger.info("Enter saveCompany() service..");
		Integer id = null;
		try {
			Company company = new Company();
			setCompanyDtls(vo, company);
			companyDao.save(company);
			id = company.getCompanyId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Exit saveUser()..");
		return id;
	}

	private void setCompanyDtls(Company vo, Company company) {
		company.setCompanyName(vo.getCompanyName());
		company.setCompanyCode(vo.getCompanyCode());
		company.setCompanyType(vo.getCompanyType());
		company.setDomain(vo.getDomain());
		company.setEmail(vo.getEmail());
		company.setHdOfice(1);
		company.setUrl(vo.getUrl());

		Address ui = vo.getAddress();
		Address addrss = new Address();
		if (ui != null) {
			addrss.setAddId(ui.getAddId());
			addrss = ui;
		}

		if (ui != null) {

			if (ui.getCityId() != null) {
				City city = new City();
				city.setCityId(ui.getCityId());
				addrss.setCity(city);
			} else {
				addrss.setCity(null);
			}

			if (ui.getStateId() != null) {
				State state = new State();
				state.setStateId(ui.getStateId());
				addrss.setState(state);
			} else {
				addrss.setState(null);
			}
			if (ui.getCntryId() != null) {
				Country country = new Country();
				country.setCountryId(ui.getCntryId());
				addrss.setCountry(country);
			} else {
				addrss.setCountry(null);
			}
		}
		company.setAddress(addrss);
	}

	/*@Override
	public List<Company> getCompanies() {
		return companyDao.getCompanies();
	}*/

	@Override
	public Map<String, Object> getCompanyDtlsByVo(Company vo) {
		Map<String, Object> map = new HashMap<String, Object>();
		Company company = null;
		try {
			company = getCompanyDtlsByCompId(vo.getCompanyId());
			setCompanyDtlsToVo(vo, company);
			map.put("company", vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Exit populateUsersDtl()..");
		return map;
	}

	private void setCompanyDtlsToVo(Company vo, Company company) throws DMSException {

		try {
			vo.setCompanyCode(company.getCompanyCode());
			vo.setCompanyName(company.getCompanyName());
			vo.setCompanyId(company.getCompanyId());
			vo.setCompanyType(company.getCompanyType());
			vo.setDomain(company.getDomain());
			vo.setUrl(company.getUrl());
			vo.setEmail(company.getEmail());
			Address ui = company.getAddress();
			Address addrVo = new Address();
			if (ui != null) {
				addrVo.setAddId(ui.getAddId());
				addrVo = ui;
			}
			if (ui != null) {
				if (ui.getCity() != null) {
					addrVo.setCityId(ui.getCity().getCityId());
				}

				if (ui.getState() != null) {
					addrVo.setStateId(ui.getState().getStateId());
				}

				if (ui.getCountry() != null) {
					addrVo.setCntryId(ui.getCountry().getCountryId());
				}
			}
			vo.setAddress(addrVo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DMSException(e);
		}
		logger.info("Exit populateUsersDtl()..");

	}

	@Override
	public Company getCompanyDtlsByCompId(Integer companyId) {

		return companyDao.getCompanywithCompId(companyId);
	}

}
