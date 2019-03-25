package org.dms.DMS.authentication;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.dms.DMS.dao.AccountDAO;
import org.dms.DMS.entity.Users;
import org.dms.DMS.util.AESencyrptor;
import org.dms.DMS.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
@PropertySource("classpath:application.properties")
public class MyDBAuthenticationService implements UserDetailsService {

@Autowired
private AccountDAO accountDAO;

@Autowired
private Environment env;

@Override
//@Transactional
public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
	 if(!userName.contains("@"))
         userName=userName + "@" +  env.getProperty("mail.domain");
	 	Users usr = accountDAO.findAccount(userName);
		String pwd = StringUtils.EMPTY;
		if (usr == null) {
		throw new UsernameNotFoundException("User " + userName + " was not found in the database");
	} else {
		
		try {
			pwd = AESencyrptor.decrypt(usr.getPassword().trim());
		} catch (Exception e) {
			// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
	String role = StringUtils.EMPTY;
		if (usr.getAdminPower() == Constants.ADMIN_POWER ||usr.getAdminPower() == Constants.SUPER_ADMIN_POWER) {
			role = "ADMIN";
		} else if (usr.getAdminPower() == 0) {
			role = "USER";
		}
				
		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();		
		//ROLE_EMPLOYEE, ROLE_MANAGER
		GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
		
		grantList.add(authority);
		
		boolean enabled = Constants.ACTIVE==1 ? true: false;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		
		UserDetails userDetails = (UserDetails) new User(usr.getLoginId(), 
				pwd, enabled, accountNonExpired, //
		credentialsNonExpired, accountNonLocked, grantList);
		return userDetails;
		}

}