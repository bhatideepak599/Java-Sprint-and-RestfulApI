package com.techlabs.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.techlabs.app.dto.AccountResponse;

import com.techlabs.app.entity.Account;
import com.techlabs.app.entity.Bank;
import com.techlabs.app.entity.Customer;
import com.techlabs.app.exception.BankException;
import com.techlabs.app.exception.NoCustomerRecordFoundException;
import com.techlabs.app.mapping.MappingBetweenDtoAndEntity;
import com.techlabs.app.repository.AccountRepository;
import com.techlabs.app.repository.BankRepository;
import com.techlabs.app.repository.CustomerRepository;
import com.techlabs.app.util.PageResponse;

@Service
public class AccountServiceImpl implements AccountService {
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private MappingBetweenDtoAndEntity mapper;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private BankRepository bankRepository;

	@Autowired
	private EmailService emailService;
	private static final Logger logger=LoggerFactory.getLogger(AccountServiceImpl.class);

	@Override
	public PageResponse<AccountResponse> getAllAccounts(int page, int size, String sortBy, String direction) {
		Sort sort = direction.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(page, size, sort);
		Page<Account> all = accountRepository.findAll(pageable);
		if (all.getContent().isEmpty()) {
			logger.error("No Accounts Found");
			throw new BankException("No Accounts Found");
		}

		List<AccountResponse> accountResponseList = mapper.getAccountResponseList(all.getContent());
		return new PageResponse<AccountResponse>(accountResponseList, all.getNumber(), all.getNumberOfElements(),
				all.getTotalElements(), all.getTotalPages(), all.isLast());

	}

	@Override
	public AccountResponse createAccount(Long customerId, Long bankId) {
		Optional<Customer> customerOptional = customerRepository.findById(customerId);
		Optional<Bank> bankOptional = bankRepository.findById(bankId);

		if (customerOptional.isEmpty()) {
			logger.error("No Customer Found With Id: " + customerId);
			throw new NoCustomerRecordFoundException("No Customer Found With Id: " + customerId);
		}
		if (bankOptional.isEmpty()) {
			logger.error("No Bank Found With Id: " + bankId);
			throw new BankException("No Bank Found With Id: " + bankId);
		}

		Customer customer = customerOptional.get();
		Bank bank = bankOptional.get();

		Account account = new Account();
		account.setBalance(1000);
		account.setBank(bank);
		account.setCustomer(customer);
		account.setActive(true);
		account.setRecievedTransactions(new ArrayList<>());
		account.setSentTransactions(new ArrayList<>());
		accountRepository.save(account);

		if (!bank.getAccounts().contains(account)) {
			bank.getAccounts().add(account);
			bankRepository.save(bank);
		}

		if (!customer.getAccounts().contains(account)) {
			customer.getAccounts().add(account);
		}

		customer.setTotalBalance(customer.getTotalBalance() + 1000);
		customerRepository.save(customer);
		Long accountNumber = account.getAccountNumber();
		String username = customer.getUser().getUsername();
		String subject = "Regarding Account Opening";
		String name = customer.getUser().getFirstName() + " " + customer.getUser().getLastName(); 
		String body = body("", account.getBank().getFullName(), accountNumber);

		emailService.sendEmail(username, subject, body);
		return mapper.accountToAccountResponse(account);
	}

	@Override
	public String deleteAccount(Long accountNumber) {
		Optional<Account> byId = accountRepository.findById(accountNumber);
		if (byId.isEmpty() || byId.get().isActive() == false) {
			logger.error("Account Not Found With Account Number " + accountNumber);
			throw new BankException("Account Not Found With Account Number " + accountNumber);
		}
		Account account = byId.get();

		Customer customer = account.getCustomer();
		customer.setTotalBalance(customer.getTotalBalance() - account.getBalance());
		customer.getAccounts().remove(account);

		Bank bank = account.getBank();
		bank.getAccounts().remove(account);
		account.setActive(false);
		account.setBalance(0);

		account.setCustomer(customer);
		customer.getAccounts().add(account);
		bank.getAccounts().add(account);

		accountRepository.save(account);
		customerRepository.save(customer);
		bankRepository.save(bank);

		return "Account Deleted SuccessFully";
	}

	@Override
	public AccountResponse getAccountByAccount(Long accountNumber) {
		Optional<Account> byId = accountRepository.findById(accountNumber);
		if (byId.isEmpty() || byId.get().isActive() == false) {
			logger.error("Account Not Found With Account Number " + accountNumber);
			throw new BankException("Account Not Found With Account Number " + accountNumber);
		}
		Account account = byId.get();

		return mapper.accountToAccountResponse(account);
	}

	@Override
	public AccountResponse updateAccount(Long accountNumber) {
		Optional<Account> byId = accountRepository.findById(accountNumber);
		if (byId.isEmpty()) {
			logger.error("Account Not Found With Account Number " + accountNumber);
			throw new BankException("Account Not Found With Account Number " + accountNumber);
		}
		if (byId.get().isActive()) {
			logger.warn("Account is Already Active");
			throw new BankException("Account is Already Active");
		}
		Account account = byId.get();

		Customer customer = account.getCustomer();
		Bank bank = account.getBank();
		bank.getAccounts().remove(account);
		customer.getAccounts().remove(account);

		account.setActive(true);
		account.setBalance(1000);

		double balance = customer.getTotalBalance() + 1000;
		customer.setTotalBalance(balance);

		account.setCustomer(customer);
		customer.getAccounts().add(account);
		bank.getAccounts().add(account);

		accountRepository.save(account);
		customerRepository.save(customer);
		bankRepository.save(bank);

		return mapper.accountToAccountResponse(account);
	}

	private String body(String name, String bankName, Long accountNumber) {
		String bodyMessage = "Congratulations " + name + ". Welcome to " + bankName + " . Your Account Number is "
				+ accountNumber;
		return bodyMessage;
	}
}
