package com.caching.reuserequest.sample.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.caching.reuserequest.sample.interceptor.CachingWrapperInterceptor;
import com.caching.reuserequest.sample.interceptor.SampleInterceptor;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfiguration implements WebMvcConfigurer {
	private final SampleInterceptor sampleInterceptor;
	private final CachingWrapperInterceptor cachingWrapperInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// registry.addInterceptor(sampleInterceptor)
		// 	.addPathPatterns("/open-api/**");

		registry.addInterceptor(cachingWrapperInterceptor)
			.addPathPatterns("/open-api/**");
	}
}
