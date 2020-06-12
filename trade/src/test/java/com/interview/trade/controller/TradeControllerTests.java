package com.interview.trade.controller;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.interview.trade.dto.Trade;

public class TradeControllerTests extends AbstractTest {

	@Before
	public void setUp() {
		super.setUp();
	}
	
	@Test
	public void createTradeTest() throws Exception {
		String uri = "/api/v1/trades";
		
		Trade trade = new Trade();
		trade.setTradeId("T1");
		trade.setVersion(1);
		trade.setBookId("B1");
		trade.setMaturityDate(LocalDate.now());
		String inputJson = super.mapToJson(trade);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}
}
