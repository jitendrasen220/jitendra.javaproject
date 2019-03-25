package org.dms.DMS.dao;

import javax.persistence.criteria.CriteriaBuilder;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.dms.DMS.entity.Users;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

// Transactional for Hibernate
@Component
@Repository
@Transactional
public class AccountDAOImpl implements AccountDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Users findAccount(String userName) {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Users> crit = builder.createQuery(Users.class);
		Root<Users> usr = crit.from(Users.class);
		crit.where(builder.equal(builder.upper(usr.get("loginId")), userName.toUpperCase()));
		return (Users) session.createQuery(crit).uniqueResult();
	}
}
