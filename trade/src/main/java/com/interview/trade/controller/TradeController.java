package com.interview.trade.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interview.trade.dto.Trade;
import com.interview.trade.exception.ValidationException;
import com.interview.trade.service.TradeService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class TradeController {
	
	@Autowired
	private TradeService tradeService;

	@PostMapping("/trades")
	public void createTrade(@RequestBody Trade trade) throws ValidationException {
		log.info("Trade recieved: {}", trade);
		tradeService.validateTrade(trade);
		log.info("Recieved trade is valid: {}", trade);
		tradeService.storeTrade(trade);
	}
}
