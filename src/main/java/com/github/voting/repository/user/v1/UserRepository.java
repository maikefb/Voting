package com.github.voting.repository.user.v1;

import com.github.voting.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findBycpfCnpj(String cpfCnpj);
}
