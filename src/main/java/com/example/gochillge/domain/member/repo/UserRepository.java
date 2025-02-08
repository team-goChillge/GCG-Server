package com.example.gochillge.domain.member.repo;

import com.example.gochillge.domain.member.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
