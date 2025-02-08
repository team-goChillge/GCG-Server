package com.example.gochillge.domain.post.repo;

import com.example.gochillge.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
