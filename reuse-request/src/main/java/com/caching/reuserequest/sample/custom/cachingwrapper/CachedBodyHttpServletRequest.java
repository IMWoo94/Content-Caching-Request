package com.caching.reuserequest.sample.custom.cachingwrapper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.http.HttpMethod;
import org.springframework.util.StreamUtils;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CachedBodyHttpServletRequest extends HttpServletRequestWrapper {
	private byte[] cachedBody;
	private final Map<String, String[]> parameters = new HashMap<>();

	private boolean parsedParams;

	public CachedBodyHttpServletRequest(HttpServletRequest request) throws IOException {
		super(request);
		InputStream requestInputStream = request.getInputStream();
		this.cachedBody = StreamUtils.copyToByteArray(requestInputStream);
		this.parseParams();
		log.info("cachedBody length: {}", this.cachedBody.length);
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		return new CachedBodyServletInputStream(this.cachedBody);
	}

	@Override
	public BufferedReader getReader() throws IOException {
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.cachedBody);
		return new BufferedReader(new InputStreamReader(byteArrayInputStream));
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		if (parsedParams) {
			log.info("FormPost getParameterMap");
			Map<String, String[]> s = super.getParameterMap();
			return Stream.concat(parameters.entrySet().stream(), s.entrySet().stream()).collect(
				Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		}
		return super.getParameterMap();
	}

	@Override
	public Enumeration<String> getParameterNames() {
		if (parsedParams) {
			List<String> result = Collections.list(super.getParameterNames());
			result.addAll(parameters.keySet());

			return Collections.enumeration(result);
		}
		return super.getParameterNames();
	}

	@Override
	public String getParameter(String name) {
		return parameters.get(name) != null ? parameters.get(name)[0] : super.getParameter(name);
	}

	@Override
	public String[] getParameterValues(String name) {
		String[] s = super.getParameterValues(name);
		return ArrayUtils.addAll(s, parameters.get(name));
	}

	private void parseParams() throws IOException {
		log.info("FormPost parseParams");
		if (cachedBody.length != 0 && isFormPost()) {
			try {
				BufferedReader reader = this.getReader();
				reader.lines().forEach(it -> {
					// Param And Value
					String[] pav = it.split("&");
					for (String p : pav) {
						try {
							String decoded = URLDecoder.decode(p, getCharacterEncoding());
							log.info("FormPost Param And Value : {}", decoded);
							String[] pv = decoded.split("=");
							parameters.put(pv[0], pv.length > 1 ? new String[] {pv[1]} : new String[] {""});
						} catch (UnsupportedEncodingException e) {
							throw new RuntimeException(e);
						}
					}
				});
				parsedParams = true;
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private boolean isFormPost() {
		String contentType = this.getContentType();
		return contentType != null && contentType.contains("application/x-www-form-urlencoded")
			&& HttpMethod.POST.matches(this.getMethod());
	}
}
