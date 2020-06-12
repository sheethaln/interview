package com.interview.trade.jpa;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.interview.trade.dto.Trade;

@Repository
public interface TradeRepository extends CrudRepository<Trade, String> {
	
	@Transactional
	@Modifying
	@Query("update Trade t set t.expired = 'Y' where t.maturityDate < :date")
	void expireTradeWithMaturityBefore(@Param("date") LocalDate date);
}
