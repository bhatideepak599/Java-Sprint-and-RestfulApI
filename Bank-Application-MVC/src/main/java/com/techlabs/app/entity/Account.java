package com.techlabs.app.entity;

import java.util.List;

import com.techlabs.app.dto.TransactionResponse;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "accounts")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long accountNumber;
	
	@NotNull
	private double balance;
	private boolean Active;
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = "bank_id")
	private Bank bank;

	@OneToMany(mappedBy = "senderAccountNumber", cascade = CascadeType.ALL,orphanRemoval = true)
	private List<Transaction> sentTransactions;
	
	@OneToMany(mappedBy = "recieverAccountNumber", cascade = CascadeType.ALL,orphanRemoval = true)
	private List<Transaction> recievedTransactions;
	 

}
