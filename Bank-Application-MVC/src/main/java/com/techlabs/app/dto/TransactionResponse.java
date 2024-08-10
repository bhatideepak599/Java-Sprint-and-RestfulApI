package com.techlabs.app.dto;

import java.util.Date;

import com.techlabs.app.entity.Account;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TransactionResponse {
	@Id
	private Long transactionId;

	private Date transactionDate;
	private Long senderAccountNumber;
	private Long receiverAccountNumber;
	@NotBlank
	@NotNull
	private double amount;
	
}
