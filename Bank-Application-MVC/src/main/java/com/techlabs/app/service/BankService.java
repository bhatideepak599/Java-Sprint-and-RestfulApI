package com.techlabs.app.service;

import java.util.List;

import com.techlabs.app.dto.BankRequest;
import com.techlabs.app.dto.BankResponse;
import com.techlabs.app.util.PageResponse;

import jakarta.validation.Valid;

public interface BankService {

	PageResponse<BankResponse> getAllBanks(int page, int size, String sortBy, String direction);

	BankResponse addNewBank(@Valid BankRequest bankRequest);

	BankResponse getBankById(Long id);

	BankResponse updateBank(@Valid BankRequest bankRequest);

	String deleteBankById(Long id);

}
