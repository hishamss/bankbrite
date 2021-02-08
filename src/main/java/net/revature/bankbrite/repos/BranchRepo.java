package net.revature.bankbrite.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import net.revature.bankbrite.model.Branch;

public interface BranchRepo extends JpaRepository<Branch, Integer>{

}
