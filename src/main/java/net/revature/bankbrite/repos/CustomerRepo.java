package net.revature.bankbrite.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import net.revature.bankbrite.model.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Integer>{

	
}
