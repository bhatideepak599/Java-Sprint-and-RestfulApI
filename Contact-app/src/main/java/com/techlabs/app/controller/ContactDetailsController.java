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

import com.techlabs.app.dto.ContactDetailsDto;
import com.techlabs.app.dto.ContactRequestDto;
import com.techlabs.app.dto.ContactResponseDto;
import com.techlabs.app.service.ContactDetailsService;
import com.techlabs.app.util.PageResponse;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/")
public class ContactDetailsController {
	@Autowired
	private ContactDetailsService contactDetailsService;
	private static final Logger logger = LoggerFactory.getLogger(ContactDetailsController.class);

	@Operation(summary = "Create Contact detail")
	@PostMapping("contacts/{contactId}/details")
	public ResponseEntity<ContactDetailsDto> createContactDetail(@PathVariable("contactId") Long contactId,
			@RequestBody ContactDetailsDto contactDetailsDto) {
		logger.info("Adding  Contact Details");
		ContactDetailsDto contactDetailsResponseDto = contactDetailsService.createContactDetail(contactId,
				contactDetailsDto);
		return new ResponseEntity<ContactDetailsDto>(contactDetailsResponseDto, HttpStatus.OK);

	}

	@Operation(summary = "Get Contact Detail by ID")
	@GetMapping("contact-details/{id}")
	public ResponseEntity<ContactDetailsDto> getContactdetailsById(@PathVariable("id") Long id) {
		logger.info("Get Contact Detail by ID");
		ContactDetailsDto contactDetailsDto = contactDetailsService.getContactdetailsById(id);
		return new ResponseEntity<ContactDetailsDto>(contactDetailsDto, HttpStatus.OK);

	}

	@Operation(summary = "Get All Contact Details for a Contact")
	@GetMapping("contacts/{contactId}/details")
	public ResponseEntity<PageResponse<ContactDetailsDto>> getAllContactdetails(
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "2") int size,
			@RequestParam(name = "sortBy", defaultValue = "firstName") String sortBy,
			@RequestParam(name = "direction", defaultValue = "asc") String direction,
			@PathVariable("contactId") Long contactId) {
		
		logger.info("Fetching All Contacts Details");
		PageResponse<ContactDetailsDto> contacts = contactDetailsService.getAllContactdetails(page, size, sortBy,
				direction, contactId);

		return new ResponseEntity<>(contacts, HttpStatus.OK);
	}

	@Operation(summary = "Update Contact Detail")
	@PutMapping("contact-details")
	public ResponseEntity<ContactDetailsDto> updateContactDetails(@RequestBody ContactDetailsDto contactDetailsDto) {
		logger.info("Updating Contact Detail");
		ContactDetailsDto contactDetails = contactDetailsService.updateContactDetails(contactDetailsDto);
		return new ResponseEntity<ContactDetailsDto>(contactDetails, HttpStatus.OK);

	}

	@Operation(summary = "Delete Deatils Contact By  Id")
	@DeleteMapping("contact-details/{id}")
	public ResponseEntity<String> deleteContactdetails(@PathVariable("id") Long id) {
		logger.info("Deleting  Contact Details");
		String message = contactDetailsService.deleteContactdetails(id);
		return new ResponseEntity<>(message, HttpStatus.OK);

	}
}
