package com.userfront.service.userserviceimpl;

import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.userfront.Dao.RoleDao;
import com.userfront.Dao.UserDao;
import com.userfront.domain.User;
import com.userfront.domain.security.UserRole;
import com.userfront.service.AccountService;
import com.userfront.service.UserService;


@Service
@Transactional
public class UserServiceImpl implements UserService{

	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
    private RoleDao roleDao;
	
	@Autowired
    private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
    private AccountServiceImpl accountServiceImpl;
	
	public void save(User user){
		userDao.save(user);
	}
	
	public User findByUserName(String userName){
		return userDao.findByUserName(userName);
	}
	
	public User findByEmail(String email){
		return userDao.findByEmail(email);
	}
	
	public User createUser(User user, Set<UserRole> userRoles) {
        User localUser = userDao.findByUserName(user.getUsername());

        if (localUser != null) {
            LOG.info("User with username {} already exist. Nothing will be done. ", user.getUsername());
        } else {
            String encryptedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encryptedPassword);
            for (UserRole ur : userRoles) {
                roleDao.save(ur.getRole());
            }
            user.getUserRoles().addAll(userRoles);
            user.setPrimaryAccount(accountServiceImpl.createPrimaryAccount());
            user.setSavingsAccount(accountServiceImpl.createSavingsAccount());

            localUser = userDao.save(user);
        }

        return localUser;
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
