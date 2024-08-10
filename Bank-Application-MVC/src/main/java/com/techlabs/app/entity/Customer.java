package com.techlabs.app.entity;

import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "customers")
public class Customer {

	@Id
	
	@Column(nullable = false,unique = true)
	private Long customerId;
	
	@NotBlank
	@NotNull
	@Column(nullable = false)
	private String firstName;
	private String lastName;
	
	private double totalBalance;
	private boolean active;
	@OneToOne
	@MapsId
	@JoinColumn(name = "customerId")
	private User user;

	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL,orphanRemoval = true)
	private List<Account> accounts;
	

}
