package com.techlabs.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techlabs.app.dto.AccountResponse;
import com.techlabs.app.service.AccountService;
import com.techlabs.app.util.PageResponse;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/account")
public class AccountController {
	@Autowired
	private AccountService accountService;
	private static final Logger logger=LoggerFactory.getLogger(AccountController.class);
	
	@Operation(summary = "Get All Accounts")
	@GetMapping()
	public ResponseEntity<PageResponse<AccountResponse>> getAllAccounts(
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "2") int size,
			@RequestParam(name = "sortBy", defaultValue = "accountNumber") String sortBy,
			@RequestParam(name = "direction", defaultValue = "asc") String direction) {
		logger.info("Fetching All The Accounts");
		PageResponse<AccountResponse> accounts = accountService.getAllAccounts(page, size, sortBy, direction);

		return new ResponseEntity<>(accounts, HttpStatus.OK);
	}

	@Operation(summary = "Create A New Account ")
	@PostMapping("/{customerId}/{bankId}")
	public ResponseEntity<AccountResponse> createAccount(@PathVariable("customerId") Long customerId,
			@PathVariable("bankId") Long bankId) {
		AccountResponse account = accountService.createAccount(customerId, bankId);

		return new ResponseEntity<>(account, HttpStatus.OK);
	}

	@Operation(summary = "Delete Account")
	@DeleteMapping("{accountNumber}")
	public ResponseEntity<String> deleteAccount(@PathVariable("accountNumber") Long accountNumber) {
		String message = accountService.deleteAccount(accountNumber);

		return new ResponseEntity<>(message, HttpStatus.OK);
	}

	@Operation(summary = "Fetch Account By Account Number")
	@GetMapping("{accountNumber}")
	public ResponseEntity<AccountResponse> getAccountByAccountNumber(
			@PathVariable("accountNumber") Long accountNumber) {
		AccountResponse accountResponse = accountService.getAccountByAccount(accountNumber);

		return new ResponseEntity<>(accountResponse, HttpStatus.OK);
	}
	
	@Operation(summary = "Update Account")
	@PutMapping("{accountNumber}")
	public ResponseEntity<AccountResponse> updateAccount(@PathVariable("accountNumber") Long accountNumber) {
		AccountResponse account = accountService.updateAccount(accountNumber);

		return new ResponseEntity<>(account, HttpStatus.OK);
	}

}
