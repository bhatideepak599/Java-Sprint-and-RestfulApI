package com.techlabs.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.techlabs.app.controller.BankController;
import com.techlabs.app.dto.BankRequest;
import com.techlabs.app.dto.BankResponse;
import com.techlabs.app.dto.CustomerResponseDto;
import com.techlabs.app.entity.Account;
import com.techlabs.app.entity.Bank;
import com.techlabs.app.entity.Customer;
import com.techlabs.app.exception.BankException;
import com.techlabs.app.mapping.MappingBetweenDtoAndEntity;
import com.techlabs.app.repository.AccountRepository;
import com.techlabs.app.repository.BankRepository;
import com.techlabs.app.util.PageResponse;

import jakarta.validation.Valid;

@Service
public class BankServiceImpl implements BankService {
	private BankRepository bankRepository;
	private MappingBetweenDtoAndEntity mapper;
	private AccountRepository accountRepository;
	private static final Logger logger=LoggerFactory.getLogger(BankServiceImpl.class);
	public BankServiceImpl(BankRepository bankRepository, MappingBetweenDtoAndEntity mapper,
			AccountRepository accountRepository) {
		super();
		this.bankRepository = bankRepository;
		this.mapper = mapper;
		this.accountRepository = accountRepository;
	}

	@Override
	public PageResponse<BankResponse> getAllBanks(int page, int size, String sortBy, String direction) {

		Sort sort = direction.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(page, size, sort);
		Page<Bank> all = bankRepository.findAll(pageable);
		if (all.getContent().isEmpty()) {
			logger.error("No Banks Found");
			throw new BankException("No Banks Found");
		}
		List<BankResponse> bankResponseList = mapper.getBankResponseList(all.getContent());
		return new PageResponse<BankResponse>(bankResponseList, all.getNumber(), all.getNumberOfElements(),
				all.getTotalElements(), all.getTotalPages(), all.isLast());
		
	}

	@Override
	public BankResponse addNewBank(BankRequest bankRequest) {
		logger.info("New Bank Has Been Added");
		bankRequest.setActive(true);
		Bank bank = mapper.bankRequestToBank(bankRequest);

		bank.setAccounts(new ArrayList<Account>());
		Bank save = bankRepository.save(bank);
		return mapper.bankToBankResponse(save);
	}

	@Override
	public BankResponse getBankById(Long id) {
		Optional<Bank> byId = bankRepository.findById(id);
		if (byId.isEmpty()) {
			logger.error("No Bank Found With Id : " + id);
			throw new BankException("No Bank Found With Id : " + id);
		}
		return mapper.bankToBankResponse(byId.get());

	}

	@Override
	public BankResponse updateBank(BankRequest bankRequest) {
		Optional<Bank> byId = bankRepository.findById(bankRequest.getBankId());
		if (byId.isEmpty()) {
			logger.error("No Bank Found With Id : " + bankRequest.getBankId());
			throw new BankException("No Bank Found With Id : " + bankRequest.getBankId());
		}
		Bank bank = byId.get();
		bank.setFullName(bankRequest.getFullName());
		bank.setAbbreviation(bankRequest.getAbbreviation());

		if (bank.isActive() == false) {
			bank.setActive(true);
//			List<Account> accounts = bank.getAccounts();
//			for (Account account : accounts) {
//				account.setBalance(1000);
//				account.setActive(true);
//				double balance = 1000 + account.getCustomer().getTotalBalance();
//				account.getCustomer().setTotalBalance(balance);
//			}
//			accountRepository.saveAll(accounts);

		}
		Bank save = bankRepository.save(bank);
		return mapper.bankToBankResponse(save);
	}

	@Override
	public String deleteBankById(Long id) {
		Optional<Bank> byId = bankRepository.findById(id);
		if (byId.isEmpty()) {
			logger.error("No Bank Found With Id : " + id);
			throw new BankException("No Bank Found With Id : " + id);
		}
		// bankRepository.delete(byId.get());
		List<Account> accounts = byId.get().getAccounts();
		for (Account account : accounts) {
			account.setActive(false);
		}
		accountRepository.saveAll(accounts);
		return "Bank Deleted SuccessFully";
	}

}
