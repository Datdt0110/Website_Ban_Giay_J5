package com.example.demo.repository.assignment2;

import com.example.demo.entity.KhachHang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KhachHangRepositoryy extends JpaRepository<KhachHang, Integer> {
    @Query("SELECT kh from KhachHang kh WHERE kh.ma LiKE %:timKiem% OR kh.ten LIKE %:timKiem%")
    List<KhachHang> findByTimKiem(String timKiem);

    @Query("SELECT kh FROM KhachHang kh  ORDER BY kh.ma DESC")
    Page<KhachHang> findAllPaging(Pageable pageable);

    // Thêm câu truy vấn cho phương thức findBySdtKH
    @Query("SELECT kh FROM KhachHang kh WHERE kh.sdt = :sdtKH")
    KhachHang findBySdtKH(String sdtKH);

    // Thêm câu truy vấn cho phương thức findByTenKH
    @Query("SELECT kh FROM KhachHang kh WHERE kh.ten = :tenKH")
    KhachHang findByTenKH(String tenKH);
}
