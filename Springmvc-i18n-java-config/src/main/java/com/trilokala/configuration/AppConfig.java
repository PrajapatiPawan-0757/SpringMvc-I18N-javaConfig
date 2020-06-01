package com.trilokala.configuration;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
@ComponentScan(value = { "com.trilokala.*" })
public class AppConfig extends WebMvcConfigurerAdapter {

	@Bean
	public InternalResourceViewResolver getResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setViewClass(JstlView.class);
		resolver.setPrefix("/WEB-INF/jsp/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	/*
	 * messageSource bean is spring built-in bean name which will manipulate
	 * internationalization messages. All message files is saved in
	 * src/main/resources/config/ folder, if the config folder do not exist, you
	 * need to create it first by hand. Each message file is a properties file,
	 * the file base name is messages and suffix with the language or country
	 * ISO code, such as messages_en, messages_zh_cn etc.
	 */

	@Bean(name = "messageSource")
	public MessageSource getMessageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages");
		messageSource.setDefaultEncoding("utf-8");
		return messageSource;

	}

	/*
	 * The localeResolver is used to resolve user locale data. The
	 * CookieLocaleResolver object is used to save user locale information in
	 * browser cookie. This way user locale info can be transferred between
	 * request. If user disable cookie then you can use SessionLocaleResolver
	 * instead.
	 */

	 @Bean(name = "localeResolver")
	public CookieLocaleResolver getCookieLocaleResolver() {
		CookieLocaleResolver localeResolver = new CookieLocaleResolver();
		// Set cookie name
		localeResolver.setCookieName("cookie-locale-info");
		// Set default locale value.
		localeResolver.setDefaultLocale(Locale.ENGLISH);
		// Set cookie max exist time.
		localeResolver.setCookieMaxAge(3600);
		return localeResolver;
	}

	/*
	 * The LocaleChangeInterceptor is a interceptor that will intercept user
	 * locale change by a parameter value. For example, if we set the locale
	 * change parameter name is locale, then request url
	 * http://localhost:8088/index.jsp?locale=en will change user locale to en
	 * and display messages in src/main/resources/config/messages_en.properties.
	 */

	 @Bean(name="localeInterceptor")
	public LocaleChangeInterceptor getLocaleChangeInterCepor() {
		LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
		interceptor.setParamName("lang");
		return interceptor;
	}

	/*
	 * Also need to register above locale interceptor in spring then it will
	 * intercept user request url and parse out the lang query string to get
	 * user request locale.
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(getLocaleChangeInterCepor());
	}

}
