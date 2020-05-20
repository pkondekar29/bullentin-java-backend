package com.sap.ibso.ato.training.tools.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.ibso.ato.training.tools.model.Advertisement;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class AdvertisementControllerIntegrationTest {

	private static final String CONTROLLER_BASE_PATH = "/api/v1/ads";

	@Autowired
	private MockMvc mockMvc;

	@After
	public void tearDown() throws Exception {
		deleteAll();
	}

	@Test
	public void findAllShouldInitiallyReturnAnEmptyArray() throws Exception {
		findAll()
				.andExpect(status().isOk())
				.andExpect(jsonArrayLength(0));
	}

	@Test
	public void findAllShouldReturnAllCreatedAdvertisements() throws Exception {
		create(advertisement("A"));
		create(advertisement("B"));
		create(advertisement("C"));
		findAll()
				.andExpect(status().isOk())
				.andExpect(jsonArrayLength(3));
	}

	@Test
	public void findByIdReturnsNotFoundForAnInvalidId() throws Exception {
		findById(5180l)
				.andExpect(status().isNotFound());
	}

	public void findByIdReturnsTheAdvertisementWithTheSpecifiedId() throws Exception {
		create(advertisement("A"));
		Advertisement advertisementUT = createAndReturn(advertisement("B"));
		create(advertisement("C"));

		findById(advertisementUT.getId())
				.andExpect(status().isOk())
				.andExpect(jsonTitle(advertisementUT.getTitle()));

	}

	@Test
	public void createShouldSaveTheAdvertisementAndAssignAnId() throws Exception {
		create(advertisement("Hello World"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").isNumber())
				.andExpect(jsonTitle("Hello World"));
	}

	@Test
	public void updateShouldReturnNotFoundForAnInvalidId() throws Exception {
		update(advertisement("Hello World", 5180l))
				.andExpect(status().isNotFound());
	}

	@Test
	public void updateShouldReturnBadRequestForInconsistentIds() throws Exception {
		update(advertisement("Hello World", 5180l), 123l)
				.andExpect(status().isBadRequest());
	}

	@Test
	public void updateShouldReturnBadRequestIfIdInBodyIsNull() throws Exception {
		update(advertisement("Hello World", null), 5180l)
				.andExpect(status().isBadRequest());
	}

	@Test
	public void updateShouldUpdateTheAdvertisement() throws Exception {
		create(advertisement("A"));
		Advertisement advertisementUT = createAndReturn(advertisement("B"));
		create(advertisement("C"));

		advertisementUT.setTitle("Hello World");
		update(advertisementUT)
				.andExpect(status().isOk())
				.andExpect(jsonTitle(advertisementUT.getTitle()));

		findById(advertisementUT.getId())
				.andExpect(jsonTitle(advertisementUT.getTitle()));

	}

	@Test
	public void deleteShouldDeleteTheSpecifiedAdvertisement() throws Exception {
		create(advertisement("A"));
		Advertisement advertisementUT = createAndReturn(advertisement("B"));
		create(advertisement("C"));

		deleteById(advertisementUT.getId());
		findAll()
				.andExpect(status().isOk())
				.andExpect(jsonArrayLength(2));
	}

	@Test
	public void deleteShouldReturnNotFoundForAnInvalidId() throws Exception {
		deleteById(5180L)
				.andExpect(status().isNotFound());
	}

	@Test
	public void deleteAllShouldDeleteAllAdvertisements() throws Exception {
		create(advertisement("A"));
		create(advertisement("B"));
		deleteAll()
				.andExpect(status().isNoContent());
		findAll()
				.andExpect(jsonArrayLength(0));
	}

	private ResultActions create(Advertisement advertisement) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		return mockMvc.perform(post(CONTROLLER_BASE_PATH).content(objectMapper.writeValueAsString(advertisement)).contentType(MediaType.APPLICATION_JSON));
	}

	private Advertisement createAndReturn(Advertisement advertisement) throws Exception {
		MockHttpServletResponse mockHttpServletResponse = create(advertisement("B"))
				.andReturn().getResponse();
		return advertisement(mockHttpServletResponse);
	}

	private ResultActions update(Advertisement advertisement) throws Exception {
		return update(advertisement, advertisement.getId());
	}

	private ResultActions update(Advertisement advertisement, Long id) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		return mockMvc.perform(put(controllerBasePathWithId(id)).content(objectMapper.writeValueAsString(advertisement)).contentType(MediaType.APPLICATION_JSON));
	}

	private ResultActions findAll() throws Exception {
		return mockMvc.perform(get(CONTROLLER_BASE_PATH));
	}

	private ResultActions findById(Long id) throws Exception {
		return mockMvc.perform(get(controllerBasePathWithId(id)));
	}

	private ResultActions deleteById(Long id) throws Exception {
		return mockMvc.perform(delete(controllerBasePathWithId(id)));
	}

	private ResultActions deleteAll() throws Exception {
		return mockMvc.perform(delete(CONTROLLER_BASE_PATH));
	}

	private String controllerBasePathWithId(Long id) {
		return CONTROLLER_BASE_PATH + "/" + (id == null ? "" : id);
	}

	private Advertisement advertisement(String title) {
		Advertisement advertisement = new Advertisement();
		advertisement.setTitle(title);
		return advertisement;
	}

	private Advertisement advertisement(String title, Long id) {
		Advertisement advertisement = advertisement(title);
		advertisement.setId(id);
		return advertisement;
	}

	private Advertisement advertisement(MockHttpServletResponse mockHttpServletResponse) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(mockHttpServletResponse.getContentAsString(), Advertisement.class);
	}

	private static ResultMatcher jsonArrayLength(int length) {
		return jsonPath("$.length()").value(length);
	}

	private static ResultMatcher jsonTitle(String title) {
		return jsonPath("$.title").value(title);
	}
}
