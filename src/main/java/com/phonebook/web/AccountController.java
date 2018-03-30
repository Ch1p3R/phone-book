package com.phonebook.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.phonebook.model.Account;
import com.phonebook.service.AccountService;
import com.phonebook.service.SecurityService;

@Controller
public class AccountController {

	@Autowired
	private AccountService accountService;
	@Autowired
	private SecurityService securityService;

	@RequestMapping(value = { "/", "/login" }, method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {

		ModelAndView modelAndView = new ModelAndView();
		if (error != null) {
			modelAndView.addObject("error", "Invalid username or password!");
		}
		if (logout != null) {
			modelAndView.addObject("logout", true);
		}
		if (SecurityContextHolder.getContext() != null && 
	    		SecurityContextHolder.getContext().getAuthentication() != null) { 
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();

			if (!(auth instanceof AnonymousAuthenticationToken)) {
				System.out.println("yoyoy");
			    //return new ModelAndView("forward:/phonebook");
			}
	    }
		modelAndView.setViewName("login");
		return modelAndView;
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    return "redirect:/login?logout";
	}


	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public ModelAndView registration() {
		ModelAndView modelAndView = new ModelAndView();
		Account acc = new Account();
		modelAndView.addObject("account", acc);
		modelAndView.setViewName("registration");
		return modelAndView;
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid Account account, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registration");
			modelAndView.addObject("account", account);
			return modelAndView;
		}
		Account accExists = accountService.findByName(account.getName());
		if (accExists != null) {
			bindingResult.rejectValue("name", "error.name",
							"There is already a account registered with the login name provided");
			modelAndView.setViewName("registration");
			return modelAndView;
		}

		
		String unencryptedPass = account.getPassword();
		accountService.create(account);
		securityService.autologin(account.getName(), unencryptedPass);

		modelAndView.addObject("account", new Account());
		modelAndView.setViewName("redirect:/phonebook");
		return modelAndView;
	}
	
	@RequestMapping("/login-error")
	public ModelAndView loginError() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("loginError", true);
		return modelAndView;
	}
}
