package com.techlabs.app.dto;

import java.util.List;

import com.techlabs.app.entity.Bank;
import com.techlabs.app.entity.Customer;
import com.techlabs.app.entity.Transaction;

import jakarta.persistence.CascadeType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class AccountResponse {

	@Id
	private Long accountNumber;

	@NotNull
	private double balance;
	//private CustomerResponseDto customerResponseDto;
	private BankResponse bank;
	private boolean active;
	private List<TransactionResponse> sentTransactions;
	private List<TransactionResponse> recievedTransactions;
}
