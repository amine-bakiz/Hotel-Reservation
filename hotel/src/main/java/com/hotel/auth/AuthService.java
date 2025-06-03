package com.hotel.auth;


import com.hotel.dto.SignupRequest;
import com.hotel.dto.UserDto;
import com.hotel.entity.UserApp;
import com.hotel.enums.UserRole;
import com.hotel.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService{

    private final UserRepository userRepository;

    @PostConstruct
    public void createAdminAccount(){
        Optional<UserApp> adminAccount = userRepository.findByRole(UserRole.ADMIN);
        if(adminAccount.isEmpty()){
            UserApp userApp = new UserApp();
            userApp.setEmail("admin@gmail.com");
            userApp.setUsername("admin");
            userApp.setRole(UserRole.ADMIN);
            userApp.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepository.save(userApp);
            System.out.println("Admin account created successfully");
        }else {
            System.out.println("Admin account already exist");
        }
    }


    public UserDto createUser(SignupRequest signupRequest){
        if(userRepository.findByEmail(signupRequest.getEmail()).isPresent()){
            throw new EntityExistsException("User Already Present");
        }
        UserApp user = new UserApp();

        user.setEmail(signupRequest.getEmail());
        user.setUsername(signupRequest.getUsername());
        user.setRole(UserRole.CUSTOMER);
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        UserApp createdUser = userRepository.save(user);

        return createdUser.getUserDto();

    }


}
