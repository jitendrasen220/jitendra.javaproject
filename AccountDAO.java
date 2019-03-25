package org.dms.DMS.dao;

import javax.transaction.Transactional;

import org.dms.DMS.entity.Users;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
@Component
public interface AccountDAO {

	public Users findAccount(String userName);

}