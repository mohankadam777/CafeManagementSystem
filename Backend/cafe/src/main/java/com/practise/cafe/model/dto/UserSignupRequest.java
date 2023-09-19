package com.practise.cafe.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSignupRequest {
	@NotBlank
	private String name;

	@Pattern(regexp="^\\d{10}$",message = "Invalid Contact Number")
	private String contactNumber;

	@Email(message = "Invalid Email Address")
	private String email;

	@NotBlank
	private String password;

}
