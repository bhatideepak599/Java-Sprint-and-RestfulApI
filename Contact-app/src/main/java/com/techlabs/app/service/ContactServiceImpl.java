package com.techlabs.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.techlabs.app.dto.ContactRequestDto;
import com.techlabs.app.dto.ContactResponseDto;
import com.techlabs.app.entity.Contact;
import com.techlabs.app.entity.User;
import com.techlabs.app.exception.ContactNotFoundException;
import com.techlabs.app.mapper.Mapper;
import com.techlabs.app.repository.ContactRepository;
import com.techlabs.app.repository.UserRepository;
import com.techlabs.app.util.PageResponse;

@Service
public class ContactServiceImpl implements ContactService {
	private ContactRepository contactRepository;
	private UserRepository userRepository;
	private Mapper mapper;

	private static final Logger logger = LoggerFactory.getLogger(ContactServiceImpl.class);

	public ContactServiceImpl(ContactRepository contactRepository, UserRepository userRepository, Mapper mapper) {
		super();
		this.contactRepository = contactRepository;
		this.userRepository = userRepository;

		this.mapper = mapper;
	}

	@Override
	public String deleteContact(Long id) {
		Optional<Contact> byId = contactRepository.findById(id);
		if (byId.isEmpty() || byId.get().isActive() == false) {
			logger.error("No Contact Found With Contact Id : " + id);
			throw new ContactNotFoundException("No Contact Found With Contact Id : " + id);
		}
		Contact contact = byId.get();
		User user = contact.getUser();
		user.getContacts().remove(contact);
		contact.setActive(false);
		user.getContacts().add(contact);
		userRepository.save(user);
		contactRepository.save(contact);
		
		return "Contact Deleted Successfully.";
	}

	@Override
	public ContactResponseDto updateContact(ContactRequestDto contactRequestDto) {
		Long id = contactRequestDto.getId();
		Optional<Contact> byId = contactRepository.findById(id);
		if (byId.isEmpty()) {
			logger.error("No Contact Found With Contact Id : " + id);
			throw new ContactNotFoundException("No Contact Found With Contact Id : " + id);
		}
		Contact contact = byId.get();

		if (contactRequestDto.getFirstName() != null  ) {
			if(contactRequestDto.getFirstName().length() != 0)
				contact.setFirstName(contactRequestDto.getFirstName());
		}
			

		if (contactRequestDto.getLastName() != null) {
			if(contactRequestDto.getLastName().length() != 0)
				contact.setLastName(contactRequestDto.getLastName());
		}
			

//		if (contact.isActive() == false) {
//
//			String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
//			User user = userRepository.findByUsername(currentUser).get();
//			contact.setUser(user);
//		}
		
		User user = contact.getUser();
		user.getContacts().remove(contact);
		contact.setActive(true);

		user.getContacts().add(contact);
		contact.setUser(user);

		userRepository.save(user);
		Contact save = contactRepository.save(contact);

		return mapper.contactToContactDto(save);
	}

	@Override
	public PageResponse<ContactResponseDto> getAllContacts(int page, int size, String sortBy, String direction) {

		Sort sort = direction.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(page, size, sort);
		Page<Contact> all = contactRepository.findAll(pageable);

		if (all.getContent().isEmpty()) {
			logger.error("No Contacts Found");
			throw new ContactNotFoundException("No Contacts Found!");
		}

		List<ContactResponseDto> users = all.getContent().stream().map(user -> mapper.contactToContactDto(user))
				.collect(Collectors.toList());
		List<ContactResponseDto> activeUsers=new ArrayList<>();
		for(ContactResponseDto c:users) {
			if(c.isActive()) activeUsers.add(c);
		}
		return new PageResponse<ContactResponseDto>(activeUsers, all.getNumber(), all.getNumberOfElements(),
				all.getTotalElements(), all.getTotalPages(), all.isLast());

	}

	@Override
	public ContactResponseDto getContactById(Long id) {
		Optional<Contact> byId = contactRepository.findById(id);
		if (byId.isEmpty() || byId.get().isActive() == false) {
			logger.error("No Contact Found With Contact Id : " + id);
			throw new ContactNotFoundException("No Contact Found With Contact Id : " + id);
		}
		return mapper.contactToContactDto(byId.get());
	}

	@Override
	public ContactResponseDto createNewContact(ContactRequestDto contactRequestDto) {
		Contact contact = new Contact();
		contact.setFirstName(contactRequestDto.getFirstName());
		contact.setLastName(contactRequestDto.getLastName());
		contact.setActive(true);
		contact.setContactDetails(new ArrayList<>());

		String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepository.findByUsername(currentUser).get();
		contact.setUser(user);
		user.getContacts().add(contact);
		contactRepository.save(contact);
		userRepository.save(user);

		return mapper.contactToContactDto(contact);
	}

}
