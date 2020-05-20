package com.sap.ibso.ato.training.tools.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class HelloWorldControllerIntegrationTest {

	private static final String CONTROLLER_BASE_PATH = "/hello";

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void sayHelloReturnsNameParameterInResponse() throws Exception {
		sayHello("John")
				.andExpect(status().isOk());
	}

	private ResultActions sayHello(String name) throws Exception {
		return mockMvc.perform(get(CONTROLLER_BASE_PATH + "/" + name));
	}
}
