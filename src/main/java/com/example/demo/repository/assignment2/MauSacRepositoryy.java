package com.example.demo.repository.assignment2;

import com.example.demo.entity.MauSac;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MauSacRepositoryy extends JpaRepository<MauSac, Integer> {
    @Query("SELECT ms from MauSac ms WHERE ms.ten LiKE %:timKiem% OR ms.ten LIKE %:timKiem%")
    List<MauSac> findByTimKiem(String timKiem);

    @Query("SELECT ms FROM MauSac  ms ORDER BY ms.ma DESC")
    Page<MauSac> findAllPaging(Pageable pageable);
}
