package com.interview.trade.scheduler;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.interview.trade.jpa.TradeRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TradeExpiryScheduler {
	
	@Autowired
	private TradeRepository tradeRepository;
	
	@Scheduled(fixedDelay = 60000)
	public void expireTradeStore() {
		log.info("expring trade store");
		tradeRepository.expireTradeWithMaturityBefore(LocalDate.now());
		log.info("expring trade store completed");
	}
}
