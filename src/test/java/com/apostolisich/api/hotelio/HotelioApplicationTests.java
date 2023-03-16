package com.apostolisich.api.hotelio;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.apostolisich.api.hotelio.config.AppConfig;
import com.apostolisich.api.hotelio.config.RsaKeyProperties;
import com.apostolisich.api.hotelio.config.SecurityConfig;
import com.apostolisich.api.hotelio.restcontroller.AuthController;
import com.apostolisich.api.hotelio.restcontroller.HotelListRestController;
import com.apostolisich.api.hotelio.service.JwtTokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest({HotelListRestController.class, AuthController.class})
@Import({SecurityConfig.class, JwtTokenService.class})
class HotelioApplicationTests {

	private final MockMvc mockMvc;

	@Autowired
	public HotelioApplicationTests(MockMvc mockMvc) {
		this.mockMvc = mockMvc;
	}

	@Test
	void testUnauthorizedAccessWhenNoJwtTokenIsProvided() throws Exception {
		mockMvc.perform(get("/hotel/list?latitude=40.629269&longitude=22.947412&radius=120")).andExpect(status().isUnauthorized());
	}

	//TODO Add tests for the JWT token and all the requests

}
