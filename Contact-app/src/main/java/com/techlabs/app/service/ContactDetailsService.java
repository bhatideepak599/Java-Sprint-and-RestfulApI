package com.techlabs.app.service;

import com.techlabs.app.dto.ContactDetailsDto;

import com.techlabs.app.util.PageResponse;

public interface ContactDetailsService {

	ContactDetailsDto createContactDetail(Long contactId, ContactDetailsDto contactDetailsDto);

	PageResponse<ContactDetailsDto> getAllContactdetails(int page, int size, String sortBy, String direction,
			Long contactId);

	ContactDetailsDto getContactdetailsById(Long id);

	ContactDetailsDto updateContactDetails(ContactDetailsDto contactDetailsDto);

	String deleteContactdetails(Long id);

}
