package com.sap.ibso.ato.training.tools.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import com.sap.ibso.ato.training.tools.service.ClaimGenerator;

@RestController
@RequestMapping("/hello")
@Api(tags = { "hello" })
public class HelloWorldController {

	@Autowired
	ClaimGenerator claimGenerator;

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@GetMapping("/{name}")
	@ApiOperation(value = "Say hello", nickname = "sayHello")
	public String sayHello(@PathVariable("name") String name) throws RequestInconsistentException {
		logger.info("Saying hello to {}", name);
		return "Hello palash" + HtmlUtils.htmlEscape(name) + "!";
	}

}
