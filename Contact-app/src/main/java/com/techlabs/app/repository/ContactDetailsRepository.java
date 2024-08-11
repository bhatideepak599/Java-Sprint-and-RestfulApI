package com.techlabs.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techlabs.app.entity.ContactDetails;

@Repository
public interface ContactDetailsRepository extends JpaRepository<ContactDetails, Long> {

}
