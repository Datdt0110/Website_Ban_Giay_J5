package com.example.demo.repository.assignment2;

import com.example.demo.entity.HoaDon;
import com.example.demo.entity.KhachHang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface HoaDonRepositoryy extends JpaRepository<HoaDon,Integer> {
    // Thêm câu truy vấn cho phương thức findAll
    @Query("SELECT hd FROM HoaDon hd ORDER BY hd.id DESC")
    Page<HoaDon> findAll(Pageable pageable);

    // Thêm câu truy vấn cho phương thức findByIdKH
    @Query("SELECT hd FROM HoaDon hd WHERE hd.khachHang.id = :idKH")
    List<HoaDon> findByIdKH(@Param("idKH") Integer idKH);
    @Query("SELECT hd FROM HoaDon hd WHERE hd.trangThai = :trangThai")
    List<HoaDon> findByTrangThai(@Param("trangThai") int trangThai);

    @Query("SELECT hd FROM HoaDon hd WHERE hd.khachHang = ?1 AND hd.trangThai = ?2")
    List<HoaDon> findByKhachHangAndTrangThai(KhachHang khachHang, int trangThai);


}
