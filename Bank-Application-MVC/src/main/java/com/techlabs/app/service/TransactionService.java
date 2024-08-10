package com.techlabs.app.service;

import java.time.LocalDateTime;
import java.util.List;

import com.techlabs.app.dto.TransactionResponse;
import com.techlabs.app.util.PageResponse;

public interface TransactionService {

	TransactionResponse makeNewTransaction(Long sender, Long reciever, double amount);

	TransactionResponse getTransactionById(Long id);

	PageResponse<TransactionResponse> getTransactionsByAccountNumber(int page, int size, String sortBy, String direction, Long accountNumber);

	PageResponse<TransactionResponse> getAllTransactionsBetweenDateRange(int page, int size, String sortBy, String direction, LocalDateTime startDateTimestamp,
			LocalDateTime endDateTimestamp);

}
