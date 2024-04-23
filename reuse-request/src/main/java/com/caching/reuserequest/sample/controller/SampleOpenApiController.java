package com.caching.reuserequest.sample.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caching.reuserequest.sample.model.SampleRequest;
import com.caching.reuserequest.sample.model.SampleResponse;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/open-api")
@RestController
@Slf4j
public class SampleOpenApiController {

	@PostMapping
	public ResponseEntity<SampleResponse> ok(@RequestBody(required = false) SampleRequest sampleRequest) {
		log.info("Received request: {}", sampleRequest);
		return new ResponseEntity<>(new SampleResponse("200", "OK"), HttpStatus.OK);
	}
}
