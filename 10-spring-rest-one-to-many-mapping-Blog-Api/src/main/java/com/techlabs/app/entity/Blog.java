package com.techlabs.app.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "blog")
public class Blog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotBlank()
	@Size(min = 2, max = 50)
	private String title;
	
	@NotBlank()
	private String category;
	
	@NotBlank()
	private String data;
	
	private Date date;
	@NonNull
	private boolean published;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "blog")
	@JsonManagedReference
	private List<Comment> comments;

}
