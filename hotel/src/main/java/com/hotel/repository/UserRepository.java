package com.hotel.repository;

import com.hotel.entity.UserApp;
import com.hotel.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserApp,Long> {
    Optional<UserApp> findByEmail(String email);

    Optional<UserApp> findByRole(UserRole userRole);
}
