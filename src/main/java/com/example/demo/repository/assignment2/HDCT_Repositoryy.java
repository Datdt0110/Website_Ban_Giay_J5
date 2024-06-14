package com.example.demo.repository.assignment2;

import com.example.demo.entity.HDCT;
import com.example.demo.entity.HoaDon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface HDCT_Repositoryy extends JpaRepository<HDCT, Integer> {
    @Query("SELECT hdct FROM HDCT hdct")
    Page<HDCT> findAllPaging(Pageable pageable);

    // Phương thức để tìm HDCT theo idHd
    // Thêm @Query để chỉ định câu truy vấn tùy chỉnh
    @Query("SELECT h FROM HDCT h WHERE h.id = ?1")
    List<HDCT> findByIdId(Integer id);
    @Query("SELECT st FROM HDCT st WHERE st.spct.id = :idSPCT")
    List<HDCT> findByIdSPCT(@Param("idSPCT") Integer idSPCT);

    @Query("SELECT h FROM HDCT h WHERE h.hoaDon.id = :hoaDonId")
    List<HDCT> findByHoaDonId(@Param("hoaDonId") Integer hoaDonId);



}
