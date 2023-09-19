package com.practise.cafe.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.practise.cafe.utility.CafeConstants;
import com.practise.cafe.model.dto.PasswordChangeRequest;
import com.practise.cafe.model.dto.UserLoginRequest;
import com.practise.cafe.model.dto.UserSignupRequest;
import com.practise.cafe.model.entity.User;
import com.practise.cafe.repo.UserRepo;
import com.practise.cafe.security.CustomerUserDetailsService;
import com.practise.cafe.security.JwtFilter;
import com.practise.cafe.security.JwtService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {


@Autowired
private UserRepo userRepo;

@Autowired
private PasswordEncoder passwordEncoder;

@Autowired 
private AuthenticationManager authenticationManager;

@Autowired 
private CustomerUserDetailsService customerUserDetailsService;

@Autowired 
private JwtService jwtService;

@Autowired  
private JwtFilter jwtFilter;


    public  ResponseEntity<String> signup(UserSignupRequest userRequest) {
		log.info("In User signup service");
		try {
            Optional<User> recordUser = userRepo.findByEmail(userRequest.getEmail());
            if(recordUser.isEmpty()) {
                User user =User.builder()
                    .name(userRequest.getName())
                    .email(userRequest.getEmail())
                    .contactNumber(userRequest.getContactNumber())
                    .password(passwordEncoder.encode(userRequest.getPassword()))
                    .status(false)
                    .role(CafeConstants.USER)
					.build();	
                userRepo.save(user);
                return new ResponseEntity<String>("Successsfully registered",HttpStatus.OK);
            }else {
                return new ResponseEntity<String>("Email already exists",HttpStatus.BAD_REQUEST);
            }
		} catch (Exception e) {
			e.printStackTrace();
	}
		return new ResponseEntity<String>(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.BAD_REQUEST);
	}
	public  ResponseEntity<String> login(UserLoginRequest userRequest) {
        log.info("In User login service");
		try {	
			Authentication authentication=authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(userRequest.getEmail(), userRequest.getPassword()));	
			if(authentication.isAuthenticated()) {
				customerUserDetailsService.loadUserByUsername(userRequest.getEmail());
				if(customerUserDetailsService.getUserDetails().getStatus().equals(true)) {
					return new ResponseEntity<String>("{\"token\":\""+
							jwtService.generateToken(customerUserDetailsService.getUserDetails().getEmail(), customerUserDetailsService.getUserDetails().getRole())+"\"}",
							HttpStatus.OK);
				}else {
					return new ResponseEntity<String>("{\"message\":\""+"Wait for Admin approval"+"\"}",HttpStatus.BAD_REQUEST);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
	}
		return new ResponseEntity<String>(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.BAD_REQUEST);
	}


		
	public ResponseEntity<String> updateUserStatus( Long id,Boolean status) {
		log.info("In User update status service");
		try{
		Optional<User> user = userRepo.findById(id);
		if(user.isPresent()){
			user.get().setStatus(status);
			userRepo.save(user.get());
			return new ResponseEntity<String>("Status updated successfully", HttpStatus.OK);
		}
		return new ResponseEntity<String>("User not found", HttpStatus.BAD_REQUEST);
	}catch(Exception e){
		e.printStackTrace();
	}
		return new ResponseEntity<String>(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public void deleteUser( Long id) {
		this.userRepo.deleteById(id);
	}
	public ResponseEntity<String> changePassword(PasswordChangeRequest request ) {
		log.info("In User Change password service");

		try{
			Optional<User> user = userRepo.findByEmail(jwtFilter.getCurrentUser());
			if(user.isPresent()){
				if(passwordEncoder.matches(request.getOldPassword(), user.get().getPassword())){
					user.get().setPassword(passwordEncoder.encode(request.getNewPassword()));
					userRepo.save(user.get());
					return new ResponseEntity<String>("Password changed successfully",HttpStatus.OK);
				}
				return new ResponseEntity<String>("Please enter correct credentials",HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<String>(CafeConstants.BAD_REUEST,HttpStatus.BAD_REQUEST);
		}
		catch(Exception e ){
			e.printStackTrace();
		}
		return new ResponseEntity<String>(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	public ResponseEntity<List<User>> getUser() {
		try{
			return new ResponseEntity<List<User>>(userRepo.findAll(),HttpStatus.OK);
		}
		catch(Exception e ){
			e.printStackTrace();
		}
		return new ResponseEntity<List<User>>(new ArrayList(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
    
}