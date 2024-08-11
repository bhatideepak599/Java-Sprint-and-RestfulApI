package com.techlabs.app.service;

import com.techlabs.app.dto.ContactRequestDto;
import com.techlabs.app.dto.ContactResponseDto;
import com.techlabs.app.util.PageResponse;

public interface ContactService {

	String deleteContact(Long id);

	ContactResponseDto updateContact(ContactRequestDto contactRequestDto);

	PageResponse<ContactResponseDto> getAllContacts(int page, int size, String sortBy, String direction);

	ContactResponseDto getContactById(Long id);

	ContactResponseDto createNewContact(ContactRequestDto contactRequestDto);


	
}
