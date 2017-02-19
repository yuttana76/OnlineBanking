package com.userfront.service;

import java.util.Set;

import com.userfront.domain.User;
import com.userfront.domain.security.UserRole;

public interface UserService{
	
	 void save(User user);
	 User findByUserName(String userName);
	 User findByEmail(String email);
	 boolean checkUserNameExists(String userName);
	 boolean checkEmailExists(String email);
	 boolean checkUserExists(String userName,String email);
	 User createUser(User user, Set<UserRole> userRoles);
}
