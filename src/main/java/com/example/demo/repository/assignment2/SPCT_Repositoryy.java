package com.example.demo.repository.assignment2;

import com.example.demo.entity.SPCT;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SPCT_Repositoryy extends JpaRepository<SPCT,Integer> {
    @Query("SELECT spct FROM SPCT spct ORDER BY spct.id DESC")
    Page<SPCT> findAllPaging(Pageable pageable);


    // Phương thức để tìm SPCT theo idSanPham
    // Thêm @Query để chỉ định câu truy vấn tùy chỉnh
    @Query("SELECT s FROM SPCT s WHERE s.sanPham.id = :idSanPham")
    List<SPCT> findBySanPhamId(@Param("idSanPham") Integer idSanPham);

    // Tìm theo tên sản phẩm
    @Query("SELECT s FROM SPCT s WHERE s.sanPham.ten = :tenSanPham")
    List<SPCT> findBySanPham_Ten(@Param("tenSanPham") String tenSanPham);

    // Tìm theo ID sản phẩm

}
