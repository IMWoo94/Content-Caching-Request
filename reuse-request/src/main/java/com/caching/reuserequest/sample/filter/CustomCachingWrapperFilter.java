package com.caching.reuserequest.sample.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.caching.reuserequest.sample.custom.cachingwrapper.CachedBodyHttpServletRequest;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CustomCachingWrapperFilter extends OncePerRequestFilter {
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		log.info("CachingWrapperFilter doFilter");

		CachedBodyHttpServletRequest req = new CachedBodyHttpServletRequest(request);
		ContentCachingResponseWrapper res = new ContentCachingResponseWrapper(response);

		log.info("inputStream read start");
		ServletInputStream inputStream = req.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		br.lines().forEach(it -> log.info("inputStream read {}", it));
		log.info("inputStream read end");

		filterChain.doFilter(req, res);

		res.copyBodyToResponse();
	}
}
