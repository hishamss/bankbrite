package net.revature.bankbrite.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {

	private final String JWT;
	private final String email;
	private final String role;

}
