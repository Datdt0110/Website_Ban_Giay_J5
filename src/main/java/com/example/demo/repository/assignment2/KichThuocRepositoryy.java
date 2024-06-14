package com.example.demo.repository.assignment2;

import com.example.demo.entity.KichThuoc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface KichThuocRepositoryy  extends JpaRepository<KichThuoc, Integer> {
    @Query("SELECT kt from KichThuoc kt WHERE kt.ma LiKE %:timKiem% OR kt.ten LIKE %:timKiem%")
    List<KichThuoc> findByTimKiem(String timKiem);

    @Query("SELECT kt FROM KichThuoc  kt ORDER BY kt.ma DESC")
    Page<KichThuoc> findAllPaging(Pageable pageable);
}
