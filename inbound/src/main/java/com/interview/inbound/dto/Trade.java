package com.interview.inbound.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interview.inbound.util.LocalDateSerializer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trade implements Serializable {

	private static final long serialVersionUID = 4578262950999794438L;
	private String tradeId;
	private long version;
	private String counterPatryId;
	private String bookId;
	
    @JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate maturityDate;
}
