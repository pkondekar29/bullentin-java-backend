package com.sap.ibso.ato.training.tools.config;

import com.sap.hcp.cf.logging.servlet.filter.RequestLoggingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggerConfig {

	@Bean
	public FilterRegistrationBean cfLoggingFilterRegistration() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		RequestLoggingFilter requestLoggingFilter = new RequestLoggingFilter();
		registrationBean.setFilter(requestLoggingFilter);
		return registrationBean;
	}
}
