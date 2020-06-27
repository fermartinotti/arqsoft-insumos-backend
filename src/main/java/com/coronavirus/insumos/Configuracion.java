package com.coronavirus.insumos;

import java.util.Arrays;
import java.util.List;

import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharingFilter;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.coronavirus.insumos.api.InsumosApi;
import com.coronavirus.insumos.utils.JWTAuthorizationFilter;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Configuracion extends WebSecurityConfigurerAdapter {

	@Autowired
	private InsumosApi insumosApi;

	@Bean(destroyMethod = "shutdown")
	public SpringBus cxf() {
		return new SpringBus();
	}

	@Bean(destroyMethod = "destroy")
	@DependsOn("cxf")
	public Server jaxRsServer() {

		List<Object> providers = Arrays.asList(new JacksonJsonProvider(), new CrossOriginResourceSharingFilter());

		final JAXRSServerFactoryBean factory = new JAXRSServerFactoryBean();
		factory.setServiceBeanObjects(this.insumosApi);
		factory.setProviders(providers);
		factory.setBus(cxf());
		factory.setAddress("/");

		return factory.create();
	}

	@Bean
	public ServletRegistrationBean<CXFServlet> cxfServlet() {
		final ServletRegistrationBean<CXFServlet> servletRegistrationBean = new ServletRegistrationBean<CXFServlet>(new CXFServlet(), "/*");
		servletRegistrationBean.setLoadOnStartup(1);
		return servletRegistrationBean;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
			.authorizeRequests()
			.antMatchers("/insumos/auth/**").permitAll()
			.antMatchers("/insumos/ticket/areas").permitAll()
			.anyRequest().authenticated();
	}
}
