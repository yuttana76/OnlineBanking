package com.userfront.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.userfront.domain.PrimaryAccount;
import com.userfront.domain.SavingsAccount;
import com.userfront.domain.User;
import com.userfront.service.TransactionService;
import com.userfront.service.UserService;


@Controller
@RequestMapping("/transfer")
public class TransferController {

	final String BETWEEN_ACCOUNTS_PAGE = "betweenAccounts";
	final String REDIRECT_USERFRONT_PAGE = "redirect:/userFront";
	
	@Autowired
	private UserService userService; 
	
	@Autowired
	private TransactionService transactionService;
	
	@RequestMapping(value="/betweenAccounts",method=RequestMethod.GET)
	private String betweenAccounts(Model model){
		model.addAttribute("transferFrom", "");
		model.addAttribute("transferTo", "");
		model.addAttribute("amount", "");
		
		return BETWEEN_ACCOUNTS_PAGE;
	}
	
	@RequestMapping(value="/betweenAccounts",method=RequestMethod.POST)
	private String betweenAccountsPost(
			@ModelAttribute("transferFrom") String transferFrom,
			@ModelAttribute("transferTo") String transferTo,
			@ModelAttribute("amount") String amount,
			Principal principal
			) throws Exception{
		
		transactionService.betweenAccountsTransfer(transferFrom, transferTo, amount, principal);
		
//		User user = userService.findByUserName(principal.getName());
//		
//		PrimaryAccount primaryAccount = user.getPrimaryAccount();
//		SavingsAccount savingsAccount = user.getSavingsAccount();
//		transactionService.betweenAccountsTransfer(transferFrom, transferTo, amount, primaryAccount, savingsAccount);
//		
		return "redirect:/userFront";
		//return REDIRECT_USERFRONT_PAGE;
	}
}
