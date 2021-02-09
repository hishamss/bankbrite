package net.revature.bankbrite.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@NoArgsConstructor
public class LoginForm {
	
	private String username;
	private String password;
	
	
}
