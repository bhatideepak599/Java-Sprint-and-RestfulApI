package com.techlabs.app.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techlabs.app.dto.TransactionResponse;
import com.techlabs.app.service.TransactionService;
import com.techlabs.app.util.PageResponse;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
	@Autowired
	private TransactionService transactionService;
	private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

	@Operation(summary = "Make A New Transaction")
	@PostMapping("/{sender}/{reciever}")
	public ResponseEntity<TransactionResponse> makeNewTransaction(@PathVariable("sender") Long sender,
			@PathVariable("reciever") Long reciever, @RequestParam(name = "amount") double amount) {
		logger.info("Making a transaction");

		TransactionResponse transactionResponse = transactionService.makeNewTransaction(sender, reciever, amount);
		return new ResponseEntity<>(transactionResponse, HttpStatus.ACCEPTED);
	}

	@Operation(summary = "Get All Transactions By Transaction Id")
	@GetMapping("/{id}")
	public ResponseEntity<TransactionResponse> getTransactionsById(@PathVariable("id") Long id) {
		logger.info("Fetching Transaction by Transaction Id");
		TransactionResponse transactionResponse = transactionService.getTransactionById(id);
		return new ResponseEntity<>(transactionResponse, HttpStatus.OK);
	}

	@Operation(summary = "Get All Transactions By Account Number")
	@GetMapping("/account/{accountNumber}")
	public ResponseEntity<PageResponse<TransactionResponse>> getTransactionsByAccountNumber(
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "2") int size,
			@RequestParam(name = "sortBy", defaultValue = "transactionId") String sortBy,
			@RequestParam(name = "direction", defaultValue = "asc") String direction,
			@PathVariable("accountNumber") Long accountNumber) {
		logger.info("Fetching Transaction By Account Number");
		PageResponse<TransactionResponse> transactions = transactionService.getTransactionsByAccountNumber(page, size,
				sortBy, direction, accountNumber);
		return new ResponseEntity<>(transactions, HttpStatus.OK);
	}

	@Operation(summary = "Get All Transactions Between A Date Range")
	@GetMapping("/dates")
	public ResponseEntity<PageResponse<TransactionResponse>> getAllTransactionsBetweenDateRange(
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "2") int size,
			@RequestParam(name = "sortBy", defaultValue = "transactionId") String sortBy,
			@RequestParam(name = "direction", defaultValue = "asc") String direction,
			@RequestParam(name = "startDate") LocalDate startDate, @RequestParam(name = "endDate") LocalDate endDate) {

		logger.info("Fetching Transaction By Between Date Range");
		LocalDateTime startDateTimestamp = startDate.atStartOfDay();
		LocalDateTime endDateTimestamp = endDate.atStartOfDay();
		PageResponse<TransactionResponse> transactionList = transactionService.getAllTransactionsBetweenDateRange(page,
				size, sortBy, direction, startDateTimestamp, endDateTimestamp);

		return new ResponseEntity<>(transactionList, HttpStatus.FOUND);
	}

}
