package com.chirantan.project.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.chirantan.project.entities.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	private int id;
	
	@NotEmpty
	@Size(min=4,message="User name must be min of 4 characters")
	private String name;
	@Email(message="email address is invalid")
	private String email;
	@NotEmpty
	@Size(min=3 ,max=10 , message="Password must be within range 3 to 10")
//	@Pattern(regexp=) check in internet and then keep a pattern
	
	private String password;
	@NotNull
	private String about;
	
	private Set <RoleDto> roles= new HashSet<>();

}
