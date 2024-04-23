package com.caching.reuserequest.sample.interceptor;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CustomCachingWrapperInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
		Exception {

		log.info("CachingWrapperInterceptor preHandle request : {}, response : {} ", request.getClass().getName(),
			response.getClass().getName());

		log.info("CustomCachingWrapperInterceptor inputStream read start");
		ServletInputStream inputStream = request.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		br.lines().forEach(it -> log.info("CustomCachingWrapperInterceptor read {}", it));
		log.info("CustomCachingWrapperInterceptor inputStream read end");

		return true;
	}
}
