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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.techlabs.app.dto.BankRequest;
import com.techlabs.app.dto.BankResponse;
import com.techlabs.app.service.BankService;
import com.techlabs.app.util.PageResponse;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/bank")
public class BankController {
	@Autowired
	private BankService bankService;
	private static final Logger logger=LoggerFactory.getLogger(BankController.class);
	
	@Operation(summary = "Fetch All Banks")
	@GetMapping()
	public ResponseEntity<PageResponse<BankResponse>> getAllBanks(
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "2") int size,
			@RequestParam(name = "sortBy", defaultValue = "bankId") String sortBy,
			@RequestParam(name = "direction", defaultValue = "asc") String direction){
		logger.info("Getting All Bank Details");
		PageResponse<BankResponse> banks= bankService.getAllBanks(page, size, sortBy, direction);
		
		return new ResponseEntity<>(banks,HttpStatus.OK);
	}
	
	@Operation(summary = "Add A New Bank")
	@PostMapping
	public ResponseEntity<BankResponse> addNewBank(@Valid @RequestBody BankRequest bankRequest){
		logger.info("Adding a New Bank ");
		BankResponse bank= bankService.addNewBank(bankRequest);
		
		return new ResponseEntity<>(bank,HttpStatus.OK);
	}
	
	@Operation(summary = "Get Bank By Id ")
	@GetMapping("/{id}")
	public ResponseEntity<BankResponse> getBankById(@PathVariable("id") Long id) {
		logger.info("Getting Bank by Bank Id ");
		BankResponse bank= bankService.getBankById(id);
		
		return new ResponseEntity<>(bank,HttpStatus.OK);
	}
	
	@Operation(summary = "Delete Bank By Id")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteBankById(@PathVariable("id") Long id) {
		logger.info(" Deleting Bank by Bank Id");
		String bank= bankService.deleteBankById(id);
		
		return new ResponseEntity<>(bank,HttpStatus.OK);
	}
	
	@Operation(summary = "Update Bank")
	@PutMapping
	public ResponseEntity<BankResponse> updateBank(@Valid @RequestBody BankRequest bankRequest){
		logger.info(" Updating Bank");
		BankResponse bank= bankService.updateBank(bankRequest);
		
		return new ResponseEntity<>(bank,HttpStatus.OK);
	}
	
}
