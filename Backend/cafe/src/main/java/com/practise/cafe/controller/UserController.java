package com.practise.cafe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.practise.cafe.model.dto.PasswordChangeRequest;
import com.practise.cafe.model.dto.UserLoginRequest;
import com.practise.cafe.model.dto.UserSignupRequest;
import com.practise.cafe.model.entity.User;
import com.practise.cafe.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    @Operation(
        description = "Signup User Post Request",
        summary="Used to save user in database",
        responses = {
            @ApiResponse(description="success",responseCode = "200"),
            @ApiResponse(description="Bad request",responseCode = "400")
        }
    )
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody(required = true) @Valid UserSignupRequest userRequest){
        return userService.signup(userRequest);
    }
    @Operation(
        description = "Login User Post Request",
        summary="Used to check user credentials i.e. email and password",
        responses = {
            @ApiResponse(description="success",responseCode = "200"),
            @ApiResponse(description="Bad request",responseCode = "400")
        }
    )
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody(required = true) @Valid UserLoginRequest userRequest){
        return userService.login(userRequest);
    }

        @Operation(
        description = "Get Health",
        summary="To get the health of api",
        responses = {
            @ApiResponse(description="success",responseCode = "200")
        }
    )
    @GetMapping("/health")
    public ResponseEntity<String> health(){
        return new ResponseEntity<String>("Working Endpoints health",HttpStatus.OK);
    }


    @Operation(
        description = "To check the secured route",
        summary="Used to check user authentication",
        responses = {
            @ApiResponse(description="Success",responseCode = "200"),
            @ApiResponse(description="Unauthorised",responseCode = "400")
        }
    )
    @GetMapping("/secured")
    public ResponseEntity<String> hello(){
        return new ResponseEntity<String>("This is a secured conection!!",HttpStatus.OK);
    }

    @GetMapping
	public ResponseEntity<List<User>>  getUser() {	
		return this.userService.getUser();
	}
	
	
	@GetMapping(path="/update")
	public ResponseEntity<String>  updateUserStatus(@RequestParam Long id, @RequestParam Boolean status) {	
		System.out.println("In controller");
		return this.userService.updateUserStatus(id,status);
	}
	
	@DeleteMapping(path="/user/{id}")
	public void  deleteUser(@PathVariable Long id) {	
		this.userService.deleteUser(id);
	}


	@PostMapping(path="/change-password")
	public ResponseEntity<String>  changePassword(@RequestBody PasswordChangeRequest request ) {	
		return this.userService.changePassword(request);
	}
    
}
