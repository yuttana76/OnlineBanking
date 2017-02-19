package com.userfront.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountController {
	
	@RequestMapping("/primaryAccount")
	public String primaryAccount(){
		return "primaryAccount";
	} 
	
	@RequestMapping("/savingAccount")
	public String savingAccount(){
		return "savingAccount";
	} 

}
