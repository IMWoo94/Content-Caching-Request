package com.caching.reuserequest.sample.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SampleInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
		Exception {
		log.info("SampleInterceptor preHandle");

		// ServletInputStream inputStream = request.getInputStream();
		// BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		// reader.lines().forEach(it -> log.info("read {}", it));
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
		ModelAndView modelAndView) throws Exception {
		log.info("SampleInterceptor postHandle");
		// ServletInputStream inputStream = request.getInputStream();
		// BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		// reader.lines().forEach(it -> log.info("read {}", it));

		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
		Exception ex) throws Exception {
		log.info("SampleInterceptor afterCompletion");
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
}
