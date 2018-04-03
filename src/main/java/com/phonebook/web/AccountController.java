package com.phonebook.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.phonebook.model.Account;
import com.phonebook.service.AccountService;
import com.phonebook.service.SecurityService;

@Controller
public class AccountController {

	@Autowired
	private AccountService accountService;
	@Autowired
	private SecurityService securityService;

	@RequestMapping(value = "/authorization", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error, Model model) {
		ModelAndView modelAndView = new ModelAndView();

		if (!model.containsAttribute("account")) {
			modelAndView.addObject("account", new Account());
		}
		if (error != null) {
			modelAndView.addObject("error", "Invalid username or password!");
		}
		if (SecurityContextHolder.getContext() != null
				&& SecurityContextHolder.getContext().getAuthentication() != null) {

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (!(auth instanceof AnonymousAuthenticationToken)) {

				return new ModelAndView("forward:/phonebook");
			}
		}
		modelAndView.setViewName("authorization");
		return modelAndView;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, HttpServletResponse response, @ModelAttribute Account accForm,
			BindingResult result) throws ServletException {

		request.login(accForm.getName(), accForm.getPassword());
		return "forward:/phonebook";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logoutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/authorization");
		modelAndView.addObject("logout", true);
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return modelAndView;
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid Account account, BindingResult bindingResult,
			final RedirectAttributes redirectAttributes) {
		ModelAndView modelAndView = new ModelAndView();

		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.account", bindingResult);
			redirectAttributes.addFlashAttribute("account", account);
			modelAndView.setViewName("redirect:/authorization");
			modelAndView.addObject("account", account);
			return modelAndView;
		}
		Account accExists = accountService.findByName(account.getName());
		if (accExists != null) {
			bindingResult.rejectValue("name", "error.name",
					"There is already a account registered with the login name provided");
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.account", bindingResult);
			redirectAttributes.addFlashAttribute("account", account);
			modelAndView.setViewName("redirect:/authorization");
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
		modelAndView.setViewName("redirect:/authorization");
		return modelAndView;
	}

	@RequestMapping("*")
	public ModelAndView handle() {
		ModelAndView modelAndView = new ModelAndView();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			return new ModelAndView("redirect:/phonebook");
		}

		modelAndView.setViewName("redirect:/authorization");
		return modelAndView;
	}
}
