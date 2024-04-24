package com.caching.reuserequest.sample.filter;

import java.io.IOException;
import java.util.Enumeration;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.extern.slf4j.Slf4j;

// @Component
@Slf4j
public class SampleFilter implements Filter {

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws
		IOException,
		ServletException {

		log.info("SampleFilter doFilter before");
		Enumeration<String> parameterNames = servletRequest.getParameterNames();
		log.info("parameterNames read {}", parameterNames);
		filterChain.doFilter(servletRequest, servletResponse);
		log.info("SampleFilter doFilter after");

	}
}
