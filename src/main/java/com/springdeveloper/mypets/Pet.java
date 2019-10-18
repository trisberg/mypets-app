package com.springdeveloper.mypets;

import org.springframework.data.annotation.Id;

public class Pet {

	@Id
	Long id;

	String name;

	private PetType type;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PetType getType() {
		return type;
	}

	public void setType(PetType type) {
		this.type = type;
	}
}