package com.example.demo.repository.assignment2;

import com.example.demo.entity.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SanPhamRepositoryy extends JpaRepository<SanPham,Integer> {
    @Query("SELECT sp FROM SanPham sp WHERE sp.ten LIKE %:timKiem% OR sp.ma LIKE %:timKiem%")
    List<SanPham> findByTimKiem(String timKiem);

    @Query("SELECT sp FROM SanPham sp ORDER BY sp.ma DESC") // Sắp xếp theo mã sản phẩm từ cao xuống thấp
    Page<SanPham> findAllPaging(Pageable pageable);
}
