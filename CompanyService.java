package org.dms.DMS.service;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.dms.DMS.entity.Company;
import org.dms.DMS.model.PaginationResult;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
@Transactional
public interface CompanyService {

	/*public List<Company> getCompanies();*/

	public Integer saveCompany(Company vo);

	public boolean getCompanywithCode(String companyCode);

	boolean getCompanywithName(String companyName);

	boolean getCompanywithDomain(String domain);

	public Company getCompanyDtlsByCompId(Integer companyId);

	Map<String, Object> getCompanyDtlsByVo(Company usrVo);

	public Integer updateCompany(Company company);

	/*public List<Company> getCmpnyDtls(String companyCode, String companyName);	*/

	PaginationResult<Company> getCompanyCompanyWithPagination(int page, int maxResult, int maxNavigationPage,
			Company vo);

}
