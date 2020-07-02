package com.team2.laps.controller;

import java.util.ArrayList;
import java.util.Collections;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.team2.laps.exception.AppException;
import com.team2.laps.model.Role;
import com.team2.laps.model.User;
import com.team2.laps.payload.SignUpRequest;
import com.team2.laps.repository.RoleRepository;
import com.team2.laps.repository.UserRepository;
import com.team2.laps.service.StaffService;

@RestController
@RequestMapping("/api/staffs")
public class StaffController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	StaffService sService;

//	@GetMapping("/test")
//	public RoleName getRoleName() {
//		RoleName rn = RoleName.ROLE_MANAGER;
//		Role assignRole = roleRepository.findByName(rn).get();
//		return assignRole.getName();
//	}

//	@GetMapping("/staffs")
//	public ArrayList<String> staffList() {
//		return sService.findAllStaffNames();
//	}
//	
//    @GetMapping
//    public ResponseEntity<?> staffList() {
//        return ResponseEntity.ok(new ApiResponse(sService.findAll()));
//    }
//    
////
//	@DeleteMapping("/{id}")
//	public ArrayList<String> deleteStaff(@PathVariable Long id) {
//		sService.deleteStaff(id);
//		return sService.findAllStaffNames();
//	}
	

	@PostMapping("/add")
	public ArrayList<User> addStaff(@Valid
			@RequestBody SignUpRequest signUpRequest) {
		User user = new User(signUpRequest.getName(), signUpRequest.getEmail(),
				signUpRequest.getPassword());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		Role userRole = roleRepository.findByName(signUpRequest.getRole())
				.orElseThrow(() -> new AppException("User Role not set."));
		user.setRoles(Collections.singleton(userRole));
		userRepository.save(user);
		return sService.findAll();
	}
//
//	@GetMapping("/test")
//	public RoleName getRoleName() {
//		RoleName rn = RoleName.ROLE_MANAGER;
//		Role assignRole = roleRepository.findByName(rn).get();
//		return assignRole.getName();
//	}
//
//	@PutMapping("/{id}")
//	public ArrayList<String> editStaff(@PathVariable Long id,
//			@RequestBody StaffModification sm){
//		User existUser = sService.findStaffById(id);
//		User supervisor = sService.findStaffByName(sm.getSupervisorName());
//		Role assignRole = roleRepository.findByName(RoleName.ROLE_MANAGER).get();
//		existUser.setName(sm.getName());
//		existUser.setAnnualLeaveEntitled(sm.getAnnualLeaveEntitled());
//		existUser.setReportTo(supervisor);
//		existUser.setRoles(Collections.singleton(assignRole));
//		sService.saveStaff(existUser);
//		return sService.findAllStaffNames();
//	}
}