package com.interview.trade.service;

import com.interview.trade.dto.Trade;
import com.interview.trade.exception.ValidationException;

public interface TradeService {
	void validateTrade(Trade trade) throws ValidationException;
	void storeTrade(Trade trade);
}
