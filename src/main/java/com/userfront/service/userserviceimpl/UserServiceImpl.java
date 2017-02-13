package com.userfront.service.userserviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userfront.Dao.UserDao;
import com.userfront.domain.User;
import com.userfront.service.UserService;


@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;
	
	public void save(User user){
		userDao.save(user);
	}
	
	public User findByUserName(String userName){
		return userDao.findByUserName(userName);
	}
	
	public User findByEmail(String email){
		return userDao.findByEmail(email);
	}
	
	public boolean checkUserNameExists(String userName){
		if( null != findByUserName(userName)){
			return true;
		}
		return false;
	}
	
	public boolean checkEmailExists(String email){
		if(null != findByEmail(email)){
			return true;
		}
		return false;
	}
	
	public boolean checkUserExists(String userName,String email){
		if(checkUserNameExists(userName) || checkEmailExists(email)){
			return true;
		}
		return false;
	}
}
