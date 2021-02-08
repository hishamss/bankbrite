package net.revature.bankbrite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.revature.bankbrite.model.Branch;
import net.revature.bankbrite.repos.BranchRepo;

@RestController
@RequestMapping("/branch")
public class BranchController {
	
	@Autowired
	private BranchRepo branchRepo;
	
	@GetMapping
	public List<Branch> getAllBranches() {
		return branchRepo.findAll();
	}

	@PostMapping
	public Branch addBranch(@RequestBody Branch branch) {
		System.out.println(branch.getCity());
		
		return branchRepo.save(branch);
	}
}
