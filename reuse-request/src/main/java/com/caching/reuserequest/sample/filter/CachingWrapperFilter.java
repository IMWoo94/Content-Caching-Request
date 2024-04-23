package com.caching.reuserequest.sample.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CachingWrapperFilter extends OncePerRequestFilter {
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		log.info("CachingWrapperFilter doFilter");

		ContentCachingRequestWrapper req = new ContentCachingRequestWrapper(request);
		ContentCachingResponseWrapper res = new ContentCachingResponseWrapper(response);

		// log.info("inputStream read start");
		// ServletInputStream inputStream = req.getInputStream();
		// BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		// br.lines().forEach(it -> log.info("inputStream read", it));
		// log.info("inputStream read end");

		log.info("CachingWrapperFilter body : {}", req.getContentAsString());

		filterChain.doFilter(req, res);

		res.copyBodyToResponse();
	}
}
