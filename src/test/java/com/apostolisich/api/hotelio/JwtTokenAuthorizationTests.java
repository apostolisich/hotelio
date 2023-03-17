package com.apostolisich.api.hotelio;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
class JwtTokenAuthorizationTests {

	private static final String HOTEL_LIST_TEST_URL = "/list?latitude=40.629269&longitude=22.947412&radius=120";

	private final MockMvc mockMvc;

	@Autowired
	public JwtTokenAuthorizationTests(WebApplicationContext webApplicationContext) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
	}

	@Test
	void testUnauthorizedAccessWhenNoJwtTokenIsProvided() throws Exception {
		mockMvc.perform(get(HOTEL_LIST_TEST_URL)).andExpect(status().isUnauthorized());
	}

	@Test
	void testAuthorizedAccessWhenJwtTokenIsProvided() throws Exception {
		MvcResult result = mockMvc.perform(post("/jwtToken").with(httpBasic("demo", "password")))
				.andExpect(status().isOk())
				.andReturn();

		String jwtToken = result.getResponse().getContentAsString();

		mockMvc.perform(get(HOTEL_LIST_TEST_URL).header("Authorization", "Bearer " + jwtToken)).andExpect(status().isOk());
	}

}
