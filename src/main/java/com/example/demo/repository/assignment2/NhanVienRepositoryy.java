package com.example.demo.repository.assignment2;

import com.example.demo.entity.NhanVien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NhanVienRepositoryy extends JpaRepository<NhanVien, Integer> {
    // Sửa câu truy vấn và tên phương thức cho findByTimKiem
    @Query("SELECT nv from NhanVien nv WHERE nv.ma LiKE %:timKiem% OR nv.ten LIKE %:timKiem%")
    List<NhanVien> findByTimKiem(String timKiem);

    // Thêm câu truy vấn cho phương thức findAllPaging
    @Query("SELECT nv FROM NhanVien nv ORDER BY nv.ma DESC")
    Page<NhanVien> findAllPaging(Pageable pageable);
    @Query("SELECT nv FROM NhanVien nv WHERE nv.tenDN = :tenDN")
    NhanVien findByTenDN(String tenDN);
    // Thêm phương thức tìm kiếm theo mã
    @Query("SELECT nv FROM NhanVien nv WHERE nv.ma = :ma")
    NhanVien findByMa(String ma);
}
