package com.interview.trade.service;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.interview.trade.dto.Trade;
import com.interview.trade.exception.ValidationException;
import com.interview.trade.jpa.TradeRepository;

@RunWith(MockitoJUnitRunner.class)
public class TradeServiceImplTests {

	@InjectMocks
	TradeServiceImpl tradeServiceImpl;
	
	@Mock
	TradeRepository tradeRepository;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test(expected = ValidationException.class)
	public void validateTradeTest() {
		Trade trade = new Trade();
		trade.setTradeId("T1");
		trade.setVersion(1);
		trade.setBookId("B1");
		trade.setMaturityDate(LocalDate.now().minusDays(2));
		
		tradeServiceImpl.validateTrade(trade);
		
	}
	
	@Test(expected = ValidationException.class)
	public void storeTradeExceptionTest() {
		Trade trade = new Trade();
		trade.setTradeId("T1");
		trade.setVersion(1);
		trade.setBookId("B1");
		trade.setMaturityDate(LocalDate.now());
		
		Trade optionalTrade = new Trade();
		optionalTrade.setTradeId("T1");
		optionalTrade.setVersion(2);
		Optional<Trade> ot = Optional.of(optionalTrade);
		Mockito.when(tradeRepository.findById(trade.getTradeId())).thenReturn(ot);
		
		tradeServiceImpl.storeTrade(trade);
	}
	
	@Test
	public void storeTradeTest() {
		Trade trade = new Trade();
		trade.setTradeId("T1");
		trade.setVersion(2);
		trade.setBookId("B1");
		trade.setMaturityDate(LocalDate.now());
		
		Trade optionalTrade = new Trade();
		optionalTrade.setTradeId("T1");
		optionalTrade.setVersion(1);
		Optional<Trade> ot = Optional.of(optionalTrade);
		Mockito.when(tradeRepository.findById(trade.getTradeId())).thenReturn(ot);
		
		tradeServiceImpl.storeTrade(trade);
	}
}
