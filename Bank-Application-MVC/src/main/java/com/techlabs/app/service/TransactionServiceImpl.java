package com.techlabs.app.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.techlabs.app.controller.TransactionController;
import com.techlabs.app.dto.TransactionResponse;
import com.techlabs.app.entity.Account;
import com.techlabs.app.entity.Customer;
import com.techlabs.app.entity.Transaction;
import com.techlabs.app.exception.BankException;
import com.techlabs.app.mapping.MappingBetweenDtoAndEntity;
import com.techlabs.app.repository.AccountRepository;
import com.techlabs.app.repository.TransactionRepository;
import com.techlabs.app.util.PageResponse;

@Service
public class TransactionServiceImpl implements TransactionService {
	private TransactionRepository transactionRepository;
	private AccountRepository accountRepository;
	private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);
	@Autowired
	private MappingBetweenDtoAndEntity mapper;

	public TransactionServiceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository) {
		super();
		this.transactionRepository = transactionRepository;
		this.accountRepository = accountRepository;
	}

	@Override
	public TransactionResponse makeNewTransaction(Long sender, Long reciever, double amount) {
		Optional<Account> byId = accountRepository.findById(reciever);
		Optional<Account> byId2 = accountRepository.findById(sender);
		if (byId.isEmpty() || byId.get().isActive() == false) {
			logger.error("Reciever Account Number : " + reciever + " is Invalid!");
			throw new BankException("Reciever Account Number : " + reciever + " is Invalid!");
		}
		if (byId2.isEmpty() || byId2.get().isActive() == false) {
			logger.error("Sender Account Number : " + sender + " is Invalid!");
			throw new BankException("Sender Account Number : " + sender + " is Invalid!");
		}

		Account senderAccount = byId2.get();
		if (senderAccount.getBalance() < amount) {
			logger.error("Insufficient Balance. Transaction Failed!.");
			throw new BankException("Insufficient Balance. Transaction Failed!.");
		}
		Account recieverAccount = byId.get();
		recieverAccount.setBalance(amount + recieverAccount.getBalance());
		senderAccount.setBalance(senderAccount.getBalance() - amount);

		Customer senderCustomer = senderAccount.getCustomer();
		Customer recieverCustomer = recieverAccount.getCustomer();
		senderCustomer.setTotalBalance(senderCustomer.getTotalBalance() - amount);
		recieverCustomer.setTotalBalance(recieverCustomer.getTotalBalance() + amount);

		senderAccount.setCustomer(senderCustomer);
		recieverAccount.setCustomer(recieverCustomer);

		Transaction transaction = new Transaction();
		transaction.setTransactionDate(new Date());
		transaction.setAmount(amount);
		transaction.setSenderAccountNumber(senderAccount);
		transaction.setRecieverAccountNumber(recieverAccount);
		Transaction savedTransaction = transactionRepository.save(transaction);
		accountRepository.save(senderAccount);
		accountRepository.save(recieverAccount);

		return mapper.transactionToTransactionResponse(savedTransaction);
	}

	@Override
	public TransactionResponse getTransactionById(Long id) {
		Optional<Transaction> byId = transactionRepository.findById(id);
		if (byId.isEmpty()) {
			logger.error("No Transaction Found With Id : " + id);
			throw new BankException("No Transaction Found With Id : " + id);
		}
		Transaction transaction = byId.get();
		return mapper.transactionToTransactionResponse(transaction);
	}

	@Override
	public PageResponse<TransactionResponse> getTransactionsByAccountNumber(int page, int size, String sortBy,
			String direction, Long accountNumber) {

		Optional<Account> byId = accountRepository.findById(accountNumber);
		if (byId.isEmpty()) {
			logger.error("No Account Found With Account Number : " + accountNumber);
			throw new BankException("No Account Found With Account Number : " + accountNumber);
		}

		List<Transaction> receivedTransactions = byId.get().getRecievedTransactions();
		List<Transaction> sentTransactions = byId.get().getSentTransactions();
		sentTransactions.addAll(receivedTransactions);

		return mapper.pagingAndSortingIntransaction(page, size, sortBy, direction, sentTransactions);

	}

	@Override
	public PageResponse<TransactionResponse> getAllTransactionsBetweenDateRange(int page, int size, String sortBy,
			String direction, LocalDateTime startDateTime, LocalDateTime endDateTime) {
		
		List<Transaction> transactions = transactionRepository.findAllByTransactionDateBetween(startDateTime,
				endDateTime);

		return mapper.pagingAndSortingIntransaction(page, size, sortBy, direction,transactions);
	}

}
