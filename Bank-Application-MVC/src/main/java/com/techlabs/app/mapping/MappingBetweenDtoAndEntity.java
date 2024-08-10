package com.techlabs.app.mapping;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.techlabs.app.dto.AccountResponse;
import com.techlabs.app.dto.BankRequest;
import com.techlabs.app.dto.BankResponse;
import com.techlabs.app.dto.CustomerRequest;
import com.techlabs.app.dto.CustomerResponseDto;
import com.techlabs.app.dto.TransactionResponse;
import com.techlabs.app.entity.Account;
import com.techlabs.app.entity.Bank;
import com.techlabs.app.entity.Customer;
import com.techlabs.app.entity.Transaction;
import com.techlabs.app.entity.User;
import com.techlabs.app.util.PageResponse;

@Component
public class MappingBetweenDtoAndEntity {
	@Autowired
	private PasswordEncoder passwordEncoder;
	private Set<Long> processedBanks = new HashSet<>();

	public CustomerResponseDto customerToCustomerResponseDto(Customer customer) {

		CustomerResponseDto customerResponseDto = new CustomerResponseDto();
		customerResponseDto.setCustomerId(customer.getCustomerId());
		customerResponseDto.setFirstName(customer.getFirstName());
		customerResponseDto.setLastName(customer.getLastName());
		customerResponseDto.setActive(customer.isActive());
		customerResponseDto.setTotalBalance(customer.getTotalBalance());

		List<AccountResponse> lst = customer.getAccounts().stream().map(account -> accountToAccountResponse(account))
				.collect(Collectors.toList());

		customerResponseDto.setAccounts(lst);
		return customerResponseDto;
	}

	public BankResponse bankToBankResponse(Bank bank, boolean includeAccounts) {
//        if (processedBanks.contains(bank.getBankId())) {
//            return null; // Avoid adding the same bank multiple times
//        }
//        processedBanks.add(bank.getBankId());

		BankResponse bankResponse = new BankResponse();
		bankResponse.setBankId(bank.getBankId());
		bankResponse.setFullName(bank.getFullName());
		bankResponse.setAbbreviation(bank.getAbbreviation());
		bankResponse.setActive(bank.isActive());
//        if (includeAccounts) {
//            bankResponse.setAccounts(getAccountResponseList(bank.getAccounts(), false));
//        }

		return bankResponse;
	}

	public AccountResponse accountToAccountResponse(Account account) {
		AccountResponse accountResponse = new AccountResponse();
		accountResponse.setAccountNumber(account.getAccountNumber());
		accountResponse.setBalance(account.getBalance());
		accountResponse.setActive(account.isActive());
		// Avoid circular reference by passing false to includeAccounts
		BankResponse bankResponse = bankToBankResponse(account.getBank(), false);

		accountResponse.setBank(bankResponse);

		List<TransactionResponse> receivedTransactions = account.getRecievedTransactions().stream()
				.map(this::transactionToTransactionResponse).collect(Collectors.toList());

		List<TransactionResponse> sentTransactions = account.getSentTransactions().stream()
				.map(this::transactionToTransactionResponse).collect(Collectors.toList());

		accountResponse.setRecievedTransactions(receivedTransactions);
		accountResponse.setSentTransactions(sentTransactions);
		return accountResponse;
	}

	public List<AccountResponse> getAccountResponseList(List<Account> accounts, boolean includeBank) {
		return accounts.stream().map(account -> {
			AccountResponse response = accountToAccountResponse(account);
			if (!includeBank) {
				response.setBank(null);
			}
			return response;
		}).collect(Collectors.toList());
	}

	public TransactionResponse transactionToTransactionResponse(Transaction transaction) {
		TransactionResponse transactionResponse = new TransactionResponse();
		transactionResponse.setTransactionId(transaction.getTransactionId());
		transactionResponse.setTransactionDate(transaction.getTransactionDate());
		transactionResponse.setAmount(transaction.getAmount());
		transactionResponse.setSenderAccountNumber(transaction.getSenderAccountNumber().getAccountNumber());
		transactionResponse.setReceiverAccountNumber(transaction.getRecieverAccountNumber().getAccountNumber());

		return transactionResponse;
	}

	public List<BankResponse> getBankResponseList(List<Bank> banks) {
		List<BankResponse> bankResponseList = new ArrayList<>();
		for (Bank bank : banks) {
			BankResponse response = bankToBankResponse(bank);
			if (response != null) {
				bankResponseList.add(response);
			}
		}
		return bankResponseList;
	}

	public List<CustomerResponseDto> getCustomerResponseList(List<Customer> customers) {
		List<CustomerResponseDto> customerResponseDTOS = new ArrayList<>();
		for (Customer customer : customers) {
			// if(customer.isActive())
			customerResponseDTOS.add(customerToCustomerResponseDto(customer));
		}
		return customerResponseDTOS;
	}

	public List<AccountResponse> getAccountResponseList1(List<Account> accounts) {
		List<AccountResponse> accountResponseDTOS = new ArrayList<>();
		for (Account account : accounts) {
			accountResponseDTOS.add(accountToAccountResponse(account));
		}
		return accountResponseDTOS;
	}

	public List<TransactionResponse> getTransactionResponseList(List<Transaction> transactions) {
		List<TransactionResponse> transactionResponse = new ArrayList<>();
		for (Transaction transaction : transactions) {
			transactionResponse.add(transactionToTransactionResponse(transaction));
		}
		return transactionResponse;
	}

	public Bank bankRequestToBank(BankRequest bankRequest) {

		Bank bank = new Bank();
		bank.setFullName(bankRequest.getFullName());
		bank.setAbbreviation(bankRequest.getAbbreviation());
		bank.setActive(bankRequest.isActive());
		return bank;
	}

	public User customerRequestToUser(CustomerRequest customerRequest) {
		User user = new User();
		user.setActive(customerRequest.isActive());
		user.setFirstName(customerRequest.getFirstName());
		user.setLastName(customerRequest.getLastName());
		user.setUsername(customerRequest.getUsername());
		user.setPassword(passwordEncoder.encode(customerRequest.getPassword()));

		return user;
	}

	public Customer customerRequestToCustomer(CustomerRequest customerRequest) {
		Customer customer = new Customer();
		customer.setFirstName(customerRequest.getFirstName());
		customer.setLastName(customerRequest.getLastName());
		customer.setActive(customerRequest.isActive());
	
		customer.setAccounts(new ArrayList<>());
		customer.setTotalBalance(0);
		return customer;
	}

	public BankResponse bankToBankResponse(Bank bank) {
		return bankToBankResponse(bank, true);
	}

	public List<AccountResponse> getAccountResponseList(List<Account> accounts) {
		return getAccountResponseList(accounts, true);
	}

	public PageResponse<TransactionResponse> pagingAndSortingIntransaction(int page, int size, String sortBy,
			String direction, List<Transaction> transactions) {
		List<TransactionResponse> transactionResponseList = getTransactionResponseList(transactions);

		transactionResponseList.sort((t1, t2) -> {
			switch (sortBy) {
			case "transactionDate":
				return direction.equalsIgnoreCase("ASC") ? t1.getTransactionDate().compareTo(t2.getTransactionDate())
						: t2.getTransactionDate().compareTo(t1.getTransactionDate());
			default:
				return direction.equalsIgnoreCase("ASC") ? t1.getTransactionId().compareTo(t2.getTransactionId())
						: t2.getTransactionId().compareTo(t1.getTransactionId());

			}
		});

		int start = Math.min(page * size, transactionResponseList.size());
		int end = Math.min(start + size, transactionResponseList.size());
		List<TransactionResponse> paginatedList = transactionResponseList.subList(start, end);

		return new PageResponse<>(paginatedList, page, paginatedList.size(), transactionResponseList.size(),
				(transactionResponseList.size() + size - 1) / size, end == transactionResponseList.size());

	}

}
