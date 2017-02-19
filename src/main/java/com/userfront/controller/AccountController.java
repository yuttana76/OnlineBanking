package com.userfront.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.userfront.domain.PrimaryAccount;
import com.userfront.domain.SavingsAccount;
import com.userfront.domain.User;
import com.userfront.service.UserService;

@Controller
@RequestMapping("/account")
public class AccountController {
	
	private static final Logger LOG = LoggerFactory.getLogger(AccountController.class);
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/primaryAccount")
	public String primaryAccount(Model model,Principal principal){
	
		User user = userService.findByUserName(principal.getName());
		PrimaryAccount primaryAccount = user.getPrimaryAccount();
		
		LOG.info("primaryAccount getId="+primaryAccount.getId()
				+" ;getAccountNumber="+primaryAccount.getAccountNumber());
		
		model.addAttribute("primaryAccount", primaryAccount);
		return "primaryAccount";
	} 
	
	@RequestMapping("/savingsAccount")
	public String savingAccount(Model model,Principal principal){
		
		User user= userService.findByUserName(principal.getName());
		SavingsAccount savingsAccount = user.getSavingsAccount();

		LOG.info("savingAccount getId="+savingsAccount.getId()
		+" ;getAccountNumber="+savingsAccount.getAccountNumber());

		model.addAttribute("savingsAccount", savingsAccount);
		return "savingsAccount";
	} 

}
