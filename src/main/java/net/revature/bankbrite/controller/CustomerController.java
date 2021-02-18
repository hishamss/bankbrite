package net.revature.bankbrite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.revature.bankbrite.model.Customer;
import net.revature.bankbrite.repos.CustomerRepo;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	private CustomerRepo customerRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping
	public List<Customer> getAllCustomers() {
		return customerRepo.findAll();
	}
	
	@PostMapping
	public Customer addCustomer (@RequestBody Customer customer) {
		
		customer.setPassword(passwordEncoder.encode(customer.getPassword()));
		return customerRepo.save(customer);
		
	}

}
