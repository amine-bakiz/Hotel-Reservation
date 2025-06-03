package com.hotel.auth;


import com.hotel.dto.AuthRequest;
import com.hotel.dto.AuthResponse;
import com.hotel.dto.SignupRequest;
import com.hotel.dto.UserDto;
import com.hotel.entity.UserApp;
import com.hotel.repository.UserRepository;
import com.hotel.services.UserService;
import com.hotel.security.JwtUtil;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest){
        try {
            UserDto createUser = authService.createUser(signupRequest);
            return new ResponseEntity<>(createUser, HttpStatus.OK);
        }catch (EntityExistsException entityExistsException){
            return new ResponseEntity<>("User already Exists", HttpStatus.NOT_ACCEPTABLE);
        }catch (Exception e){
            return new ResponseEntity<>("User not created, come later",HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public AuthResponse createAhthResponse(@RequestBody AuthRequest authRequest){
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(),authRequest.getPassword())
            );
        }catch (BadCredentialsException e){
            throw new BadCredentialsException("Incorrect Email or Password");
        }

        final UserDetails userDetails=userService.loadUserByUsername(authRequest.getEmail());

        Optional<UserApp> optionalUserApp=userRepository.findByEmail(userDetails.getUsername());
        final String jwt= jwtUtil.generateToken(userDetails);

        AuthResponse authResponse=new AuthResponse();
        if(optionalUserApp.isPresent()){
            authResponse.setJwt(jwt);
            authResponse.setUserRole(String.valueOf(optionalUserApp.get().getRole()));
            authResponse.setUserId(String.valueOf(optionalUserApp.get().getId()));
        }
        return authResponse;
    }



}
