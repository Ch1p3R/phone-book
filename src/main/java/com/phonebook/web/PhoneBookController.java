package com.phonebook.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.phonebook.model.PhoneBookEntry;
import com.phonebook.service.PhoneBookEntryService;
import com.phonebook.service.SecurityService;

@Controller
@RequestMapping("/phonebook")
public class PhoneBookController {

	@Autowired
	private SecurityService securityService;
	@Autowired
	private PhoneBookEntryService pbeService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView phoneBookMain(Model model) {

		ModelAndView modelAndView = new ModelAndView();
		String accId = securityService.findLoggedInAccountId();
		if (accId != null) {

			if (!model.containsAttribute("entry")) {
				modelAndView.addObject("entry", new PhoneBookEntry());
			}
			List<PhoneBookEntry> entries = pbeService.findAllByAccountId(accId);
			modelAndView.addObject("entries", entries);
			modelAndView.setViewName("phonebook");
		} else {
			modelAndView.setViewName("/login");
		}
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView save(@Valid @ModelAttribute("entry") PhoneBookEntry entry, BindingResult result,
			final RedirectAttributes redirectAttributes) {
		ModelAndView modelAndView = new ModelAndView();
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.entry", result);
			redirectAttributes.addFlashAttribute("entry", entry);
			modelAndView.setViewName("redirect:/phonebook");
			modelAndView.addObject("entry", entry);
			return modelAndView;
		}
		String accId = securityService.findLoggedInAccountId();

		if (accId != null) {
			pbeService.createOrUpdate(accId, entry);
			modelAndView.setViewName("redirect:/phonebook");
		} else {
			modelAndView.setViewName("/login");
			return modelAndView;
		}

		return modelAndView;

	}

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE, headers = "Accept=application/json", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PhoneBookEntry> delete(@RequestBody PhoneBookEntry entry) {

		String accId = securityService.findLoggedInAccountId();
		if (accId != null) {
			pbeService.delete(accId, entry);
			return new ResponseEntity<PhoneBookEntry>(HttpStatus.OK);
		} else {
			return new ResponseEntity<PhoneBookEntry>(HttpStatus.BAD_REQUEST);
		}
	}


	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search(@RequestParam(value = "firstName", required = false) String firstName,
			@RequestParam(value = "lastName", required = false) String lastName,
			@RequestParam(value = "mobileNumber", required = false) String mobileNumber) {
		ModelAndView modelAndView = new ModelAndView();
		String accId = securityService.findLoggedInAccountId();
		if (accId != null) {
			List<PhoneBookEntry> entries = pbeService.search(accId, firstName, lastName, mobileNumber);
			modelAndView.addObject("entries", entries);
			modelAndView.addObject("entry", new PhoneBookEntry());
			modelAndView.setViewName("phonebook");
		} else {
			modelAndView.setViewName("/login");
		}
		return modelAndView;

	}


}