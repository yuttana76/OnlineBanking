package com.userfront.service;

import com.userfront.domain.User;

public interface UserService{
	
	 void save(User user);
	 User findByUserName(String userName);
	 User findByEmail(String email);
	 boolean checkUserNameExists(String userName);
	 boolean checkEmailExists(String email);
	 boolean checkUserExists(String userName,String email);
}
