package com.userfront.controller;

import java.security.Principal;
import java.util.List;

//import org.apache.aries.blueprint.di.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.userfront.domain.PrimaryAccount;
import com.userfront.domain.Recipient;
import com.userfront.domain.SavingsAccount;
import com.userfront.domain.User;

import com.userfront.service.TransactionService;
import com.userfront.service.UserService;


@Controller
@RequestMapping("/transfer")
public class TransferController {

	final String BETWEEN_ACCOUNTS_PAGE = "betweenAccounts";
	final String REDIRECT_USERFRONT_PAGE = "redirect:/userFront";
	final String RECIPIENT_PAGE ="recipient";
	final String REDIRECT_RECIPIENT_PAGE ="redirect:/transfer/recipient";
	
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
		
		return REDIRECT_USERFRONT_PAGE;
	}
	
	@RequestMapping(value="/recipient",method = RequestMethod.GET)
	private String recipient(Model model,Principal principal){
		List<Recipient> recipientList =transactionService.findRecipientList(principal);
		
		Recipient recipient = new Recipient();
		
		model.addAttribute("recipient",recipient);
		model.addAttribute("recipientList",recipientList);
		
		return RECIPIENT_PAGE;
	}
	
	
	@RequestMapping(value="/recipient/save",method = RequestMethod.POST)
	private String recipientPost(@ModelAttribute("recipient") Recipient recipient ,Principal principal){
		
		User user = userService.findByUserName(principal.getName());
		recipient.setUser(user);
		transactionService.saveRecipient(recipient);
		
		return REDIRECT_RECIPIENT_PAGE;
	}
	
	@RequestMapping(value="/recipient/edit",method = RequestMethod.GET)
	private String recipientEdit(@ModelAttribute("recipientName")String recipientName,Model model,Principal principal){
		
		List<Recipient> recipientList =transactionService.findRecipientList(principal);
		Recipient recipient = transactionService.findRecipientByName(recipientName);
		
		model.addAttribute("recipientList", recipientList);
		model.addAttribute("recipient", recipient);
		
		return RECIPIENT_PAGE;
	}
	
	@RequestMapping(value="/recipient/delete", method = RequestMethod.GET)
	private String recipientDelete(@ModelAttribute("recipientName")String recipientName,Model model,Principal principal){
		
		transactionService.deleteRecipientByName(recipientName);
		List<Recipient> recipientList = transactionService.findRecipientList(principal);
		
		Recipient recipient = new Recipient();
		model.addAttribute("recipientList", recipientList);
		model.addAttribute("recipient", recipient);
		
		return RECIPIENT_PAGE;
	}

}
