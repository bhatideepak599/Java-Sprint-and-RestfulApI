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

import com.techlabs.app.dto.ContactRequestDto;
import com.techlabs.app.dto.ContactResponseDto;
import com.techlabs.app.dto.UserRequestDto;
import com.techlabs.app.service.ContactService;
import com.techlabs.app.util.PageResponse;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {
	@Autowired
	private ContactService contactService;
	private static final Logger logger=LoggerFactory.getLogger(ContactController.class);
	
	@Operation(summary = "Create A Contact")
	@PostMapping
	public ResponseEntity<ContactResponseDto> createNewContact(@RequestBody ContactRequestDto contactRequestDto){
		logger.info("Adding A New Contact");
		ContactResponseDto contactResponseDto=contactService.createNewContact(contactRequestDto);
		return new ResponseEntity<ContactResponseDto>(contactResponseDto,HttpStatus.OK);
		
	}
	
	@Operation(summary = "Get Contact By Id")
	@GetMapping("/{id}")
	public ResponseEntity<ContactResponseDto> getContactById(@PathVariable("id") Long id){
		logger.info("Fetching Contact By Id");
		ContactResponseDto contactResponseDto=contactService.getContactById(id);
		return new ResponseEntity<ContactResponseDto>(contactResponseDto,HttpStatus.OK);
		
	}
	
	@Operation(summary = "Fetch All Contacts")
	@GetMapping()
	public ResponseEntity<PageResponse<ContactResponseDto>> getAllContacts(
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "2") int size,
			@RequestParam(name = "sortBy", defaultValue = "firstName") String sortBy,
			@RequestParam(name = "direction", defaultValue = "asc") String direction) {
		logger.info("Fetching All The Contacts");
		PageResponse<ContactResponseDto> contacts = contactService.getAllContacts(page, size, sortBy, direction);

		return new ResponseEntity<>(contacts, HttpStatus.OK);
	}
	
	@Operation(summary = "Update An Existing Contact")
	@PutMapping
	public ResponseEntity<ContactResponseDto> updateContact(@RequestBody ContactRequestDto contactRequestDto){
		logger.info("Updating Existing Contact");
		ContactResponseDto contactResponseDto=contactService.updateContact(contactRequestDto);
		return new ResponseEntity<ContactResponseDto>(contactResponseDto,HttpStatus.OK);
		
	}
	
	@Operation(summary = "Delete Contact By Contact Id")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteContact(@PathVariable("id") Long id){
		logger.info("Deleting a Contact");
		String  message=contactService.deleteContact(id);
		return new ResponseEntity<>(message,HttpStatus.OK);
		
	}
}
