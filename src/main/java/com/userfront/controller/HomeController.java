package com.userfront.controller;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.userfront.Dao.RoleDao;
import com.userfront.domain.PrimaryAccount;
import com.userfront.domain.SavingsAccount;
import com.userfront.domain.User;
import com.userfront.domain.security.UserRole;
import com.userfront.service.UserService;

@Controller
public class HomeController {

	/** The application logger */
    private static final Logger LOG = LoggerFactory.getLogger(HomeController.class);
    
	@Autowired
	private UserService userService;

	@Autowired
	private RoleDao roleDao;
	
	@RequestMapping("/")
	public String home() {
		return "redirect:/index";
	}

	@RequestMapping("/index")
	public String index() {
		return "index";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model) {
		User user = new User();
		model.addAttribute(user);

		return "signup";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signupPost(@ModelAttribute("user") User user, Model model) {
		System.out.println("Welcome signupPost!!!");
		if (userService.checkUserExists(user.getUserName(), user.getEmail())) {

			if (userService.checkUserNameExists(user.getUserName())) {
				model.addAttribute("userNameExists", true);
			}

			if (userService.checkEmailExists(user.getEmail())) {
				model.addAttribute("emailExists", true);
			}

			return "signup";
		} else {
			
			 Set<UserRole> userRoles = new HashSet<>();
             userRoles.add(new UserRole(user, roleDao.findByName("ROLE_USER")));

			userService.createUser(user,userRoles);
			
			return "redirect:/";
		}
	}
	
	@RequestMapping("/userFront")
	public String userFront(Principal principal,Model model){
		LOG.info("Welcome userFront userName->" + principal.getName());
		User user = userService.findByUserName(principal.getName());
		
////		#1
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//	    String name1 = auth.getName(); //get logged in username

		PrimaryAccount primaryAccount = user.getPrimaryAccount();
		SavingsAccount savingsAccount = user.getSavingsAccount();
		
		model.addAttribute("primaryAccount",primaryAccount);
		model.addAttribute("savingsAccount",savingsAccount);
		
		return "userFront";
	}

}
