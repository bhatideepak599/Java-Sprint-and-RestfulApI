package com.techlabs.app.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.techlabs.app.dto.ContactDetailsDto;
import com.techlabs.app.entity.Contact;
import com.techlabs.app.entity.ContactDetails;
import com.techlabs.app.exception.ContactNotFoundException;
import com.techlabs.app.mapper.Mapper;
import com.techlabs.app.repository.ContactDetailsRepository;
import com.techlabs.app.repository.ContactRepository;
import com.techlabs.app.repository.UserRepository;
import com.techlabs.app.util.PageResponse;

@Service
public class ContactDetailsServiceImpl implements ContactDetailsService {
	private ContactRepository contactRepository;

	private Mapper mapper;
	private ContactDetailsRepository contactDetailsRepository;

	public ContactDetailsServiceImpl(ContactRepository contactRepository, Mapper mapper,
			ContactDetailsRepository contactDetailsRepository) {
		super();
		this.contactRepository = contactRepository;

		this.mapper = mapper;
		this.contactDetailsRepository = contactDetailsRepository;
	}

	private static final Logger logger = LoggerFactory.getLogger(ContactDetailsServiceImpl.class);

	@Override
	public ContactDetailsDto createContactDetail(Long contactId, ContactDetailsDto contactDetailsDto) {

		Optional<Contact> byId = contactRepository.findById(contactId);

		if (byId.isEmpty() || byId.get().isActive() == false) {
			logger.error("No Contact Found With Contact Id : " + contactId);
			throw new ContactNotFoundException("No Contact Found With Contact Id : " + contactId);
		}

		Contact contact = byId.get();
		ContactDetails contactDetails = new ContactDetails();
		contactDetails.setType(contactDetailsDto.getType());
		contactDetails.setValue(contactDetailsDto.getValue());
		contactDetails.setContact(contact);

		ContactDetails save = contactDetailsRepository.save(contactDetails);
		contact.getContactDetails().add(save);

		return mapper.ContactDetailsToContactDetailsDto(save);
	}

	@Override
	public PageResponse<ContactDetailsDto> getAllContactdetails(int page, int size, String sortBy, String direction,
			Long contactId) {

		Optional<Contact> byId = contactRepository.findById(contactId);

		if (byId.isEmpty()) {
			logger.error("No Contact Found");
			throw new ContactNotFoundException("No Contacts Found!. ");
		}

		Contact contact = byId.get();
		List<ContactDetails> contactDetails = contact.getContactDetails();
//		for(ContactDetails c: contactDetails) {
//			System.out.println(c.getType());
//		}
		return mapper.pagingAndSortingInContactDetails(page, size, sortBy, direction, contactDetails);

	}

	@Override
	public ContactDetailsDto getContactdetailsById(Long id) {

		Optional<ContactDetails> byId = contactDetailsRepository.findById(id);
		if (byId.isEmpty()) {
			logger.error("No Contact Details Found With  Id : " + id);
			throw new ContactNotFoundException("No Contact Details Found With  Id : " + id);
		}

		return mapper.ContactDetailsToContactDetailsDto(byId.get());
	}

	@Override
	public ContactDetailsDto updateContactDetails(ContactDetailsDto contactDetailsDto) {
		Optional<ContactDetails> byId = contactDetailsRepository.findById(contactDetailsDto.getContact_detail_id());
		if (byId.isEmpty()) {
			logger.error("No Contact Details Found With  Id : " + contactDetailsDto.getContact_detail_id());
			throw new ContactNotFoundException(
					"No Contact Details Found With  Id : " + contactDetailsDto.getContact_detail_id());

		}
		ContactDetails contactDetails = byId.get();
		if (contactDetailsDto.getType() != null) {
			if (contactDetailsDto.getType() != "") {
				contactDetails.setType(contactDetailsDto.getType());
			}
		}
		if (contactDetailsDto.getValue() != null) {
			if (contactDetailsDto.getValue() != "") {
				contactDetails.setValue(contactDetailsDto.getValue());
			}
		}
		ContactDetails save = contactDetailsRepository.save(contactDetails);

		return mapper.ContactDetailsToContactDetailsDto(save);
	}

	@Override
	public String deleteContactdetails(Long id) {
		Optional<ContactDetails> byId = contactDetailsRepository.findById(id);
		if (byId.isEmpty()) {
			logger.error("No Contact Details Found With  Id : " + id);
			throw new ContactNotFoundException("No Contact Details Found With  Id : " + id);
		}
		ContactDetails contactDetails = byId.get();
		Contact contact = contactDetails.getContact();
		contact.getContactDetails().remove(contactDetails);
		contactRepository.save(contact);
		contactDetailsRepository.delete(contactDetails);

		return "Contact Deatils Deleted SuccessFully.";
	}

}
