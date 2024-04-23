package com.caching.reuserequest.sample.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.ContentCachingRequestWrapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CachingWrapperInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
		Exception {

		log.info("CachingWrapperInterceptor preHandle request : {}, response : {} ", request.getClass().getName(),
			response.getClass().getName());

		ContentCachingRequestWrapper req = (ContentCachingRequestWrapper)request;
		log.info("CachingWrapperInterceptor preHandle body : {}", req.getContentAsString());

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
		ModelAndView modelAndView) throws Exception {

		ContentCachingRequestWrapper req = (ContentCachingRequestWrapper)request;
		log.info("CachingWrapperInterceptor postHandle body : {}", req.getContentAsString());
	}
}
