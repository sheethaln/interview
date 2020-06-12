package com.interview.trade.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interview.trade.dto.Trade;
import com.interview.trade.exception.ValidationException;
import com.interview.trade.jpa.TradeRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TradeServiceImpl implements TradeService {

	@Autowired
	private TradeRepository tradeRepository;
	
	@Override
	public void validateTrade(Trade trade) throws ValidationException {
		log.info("validating trade {}", trade);
		if (trade.getMaturityDate().isBefore(LocalDate.now())) {
			log.error("Maturity date " + trade.getMaturityDate() + " is before current date " + LocalDate.now());
			throw new ValidationException("Maturity date " + trade.getMaturityDate() + " is before current date " + LocalDate.now());
		}
		log.info("valid trade {}", trade);
	}

	@Override
	public void storeTrade(Trade recievedTrade) {
		log.info("store trade {}", recievedTrade);
		Trade existingTrade = tradeRepository.findById(recievedTrade.getTradeId()).orElse(null);
		if (null != existingTrade) {
			log.info("found existing trade {}", existingTrade);
			validateNStoreBasedOnTradeVersion(existingTrade, recievedTrade);
		}
		recievedTrade.setCreatedDate(LocalDate.now());
		recievedTrade.setExpired("N");
		tradeRepository.save(recievedTrade);
	}

	private void validateNStoreBasedOnTradeVersion(Trade existingTrade, Trade recievedTrade) {
		log.info("validating trade version in existing trade {} against recievedTrade {}", existingTrade, recievedTrade);
		if (recievedTrade.getVersion() < existingTrade.getVersion()) {
			log.error("Trade version recieved " + recievedTrade + " is lower than the existing trade version " + existingTrade);
			throw new ValidationException("Trade version recieved " + recievedTrade + " is lower than the existing trade version " + existingTrade);
		}
	}
}
