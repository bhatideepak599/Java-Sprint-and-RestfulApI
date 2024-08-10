package com.techlabs.app.service;



import com.techlabs.app.dto.AccountResponse;

import com.techlabs.app.util.PageResponse;

public interface AccountService {

	PageResponse<AccountResponse> getAllAccounts(int page, int size, String sortBy, String direction);

	AccountResponse createAccount(Long customerId, Long bankId);

	String deleteAccount(Long accountNumber);

	AccountResponse getAccountByAccount(Long accountNumber);

	AccountResponse updateAccount(Long accountNumber);

}
