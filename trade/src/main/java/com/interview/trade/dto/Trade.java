package com.interview.trade.dto;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interview.trade.util.LocalDateSerializer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "trade")
public class Trade implements Serializable {

	private static final long serialVersionUID = 4578262950999794438L;
	@Id
	private String tradeId;
	private long version;
	private String counterPatryId;
	private String bookId;
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate maturityDate;
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate createdDate;
	private String expired;
}
