package com.example.puddyBuddy.repository;

import com.example.puddyBuddy.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PreferRepository extends JpaRepository<Prefer, Long> {
    List<Prefer> findAll();

    Optional<Prefer> findByPreferId(Long preferId);
    List<Prefer> findByUserUserIdOrderByPreferIdAsc(Long userId);

}
