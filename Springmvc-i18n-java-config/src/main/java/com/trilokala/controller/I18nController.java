package com.trilokala.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class I18nController {

	/* Autowired messageSource bean defined in ContextConfig.java */
	@Autowired
	private MessageSource messageSource;

	// Process request to url http://localhost:8080/i18n/home.html.
	@RequestMapping("home")
	public String displayHomePage(Locale locale, Model model) {
		String nameArr[] = { "Jerry", "Richard" };

		// Get messages by locale value and add parameterized message for the
		// message.
		System.out.println(locale);
		String homePageWelcomeMessage = messageSource.getMessage("home.welcome", nameArr, locale);
	System.out.println(homePageWelcomeMessage);
		model.addAttribute("message", homePageWelcomeMessage);

		// obtain locale from LocaleContextHolder
		Locale currentLocale = LocaleContextHolder.getLocale();
		model.addAttribute("locale", currentLocale);

		return "home";
	}

	// Process request to url http://localhost:8080/i18n/about.html.
	@RequestMapping("about")
	public String displayAboutPage(Locale locale, Model model) {

		// obtain locale from LocaleContextHolder
		Locale currentLocale = LocaleContextHolder.getLocale();
		model.addAttribute("locale", currentLocale);

		return "about";
	}
}