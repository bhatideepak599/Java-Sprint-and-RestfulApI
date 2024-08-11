package com.techlabs.app.mapper;

import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.techlabs.app.dto.ContactDetailsDto;
import com.techlabs.app.dto.ContactResponseDto;
import com.techlabs.app.dto.RegisterDTO;

import com.techlabs.app.dto.UserResponseDto;
import com.techlabs.app.entity.Contact;
import com.techlabs.app.entity.ContactDetails;
import com.techlabs.app.entity.User;
import com.techlabs.app.util.PageResponse;

@Configuration
public class Mapper {
	@Autowired
	private PasswordEncoder passwordEncoder;

	public User registerToUser(RegisterDTO registerDto) {
		User user = new User();
		user.setFirstName(registerDto.getFirstName());
		user.setLastName(registerDto.getLastName());
		user.setActive(true);
		user.setAdmin(false);
		user.setUsername(registerDto.getUsername());
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
		user.setContacts(new ArrayList<>());
		return user;
	}

	public UserResponseDto userToUserResponseDto(User user) {
		UserResponseDto userResponseDto = new UserResponseDto();
		userResponseDto.setFirstName(user.getFirstName());
		userResponseDto.setLastName(user.getLastName());
		userResponseDto.setActive(user.isActive());
		userResponseDto.setUsername(user.getUsername());
		userResponseDto.setUser_id(user.getId());

		List<ContactResponseDto> contactDtoList = getContactToContactDtoList(user.getContacts());
		userResponseDto.setContactDtoList(contactDtoList);

		return userResponseDto;
	}

	public List<ContactResponseDto> getContactToContactDtoList(List<Contact> contacts) {
		List<ContactResponseDto> contactDtoList = new ArrayList<>();
		for (Contact contact : contacts) {
			contactDtoList.add(contactToContactDto(contact));
		}
		return contactDtoList;
	}

	public ContactResponseDto contactToContactDto(Contact contact) {
		ContactResponseDto contactResponseDto = new ContactResponseDto();
		contactResponseDto.setActive(contact.isActive());
		contactResponseDto.setFirstName(contact.getFirstName());
		contactResponseDto.setContact_Id(contact.getId());
		contactResponseDto.setLastName(contact.getLastName());

		List<ContactDetailsDto> contactDetailDtoList = getContactDetailsToContactDetailsDtoList(
				contact.getContactDetails());
		contactResponseDto.setContactDetailDtoList(contactDetailDtoList);
		return contactResponseDto;
	}

	public List<ContactDetailsDto> getContactDetailsToContactDetailsDtoList(List<ContactDetails> contactDetails) {
		List<ContactDetailsDto> contactDetailDtoList = new ArrayList<>();
		for (ContactDetails details : contactDetails) {
			contactDetailDtoList.add(ContactDetailsToContactDetailsDto(details));
		}
		return contactDetailDtoList;
	}

	public ContactDetailsDto ContactDetailsToContactDetailsDto(ContactDetails details) {
		ContactDetailsDto contactDetailsDto = new ContactDetailsDto();
		contactDetailsDto.setContact_detail_id(details.getId());
		contactDetailsDto.setType(details.getType());
		contactDetailsDto.setValue(details.getValue());
		return contactDetailsDto;
	}

	public PageResponse<ContactDetailsDto> pagingAndSortingInContactDetails(int page, int size, String sortBy,
			String direction, List<ContactDetails> contacts) {
		List<ContactDetailsDto> contactDetailsDtoList = contacts.stream()
				.map(contact -> ContactDetailsToContactDetailsDto(contact)).collect(Collectors.toList());

		contactDetailsDtoList.sort((t1, t2) -> {
			switch (sortBy) {
			case "type":
				return direction.equalsIgnoreCase("ASC") ? t1.getType().compareTo(t2.getType())
						: t2.getType().compareTo(t1.getType());
			default:
				return direction.equalsIgnoreCase("ASC")
						? t1.getContact_detail_id().compareTo(t2.getContact_detail_id())
						: t2.getContact_detail_id().compareTo(t1.getContact_detail_id());

			}
		});

		int start = Math.min(page * size, contactDetailsDtoList.size());
		int end = Math.min(start + size, contactDetailsDtoList.size());
		List<ContactDetailsDto> paginatedList = new ArrayList<>(contactDetailsDtoList.subList(start, end));

		return new PageResponse<>(paginatedList, page, paginatedList.size(), contactDetailsDtoList.size(),
				(contactDetailsDtoList.size() + size - 1) / size, end == contactDetailsDtoList.size());

	}

}
