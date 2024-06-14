package com.example.demo.repository.assignment1;

import com.example.demo.entity.NhanVien;
import com.example.demo.repository.assignment2.NhanVienRepositoryy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DangNhapRepository {
    @Autowired
    NhanVienRepositoryy nhanVienRepository;
    List<NhanVien> listNV = new ArrayList<>();

    public boolean validateLogin(NhanVien nv) {
        for (NhanVien s : nhanVienRepository.findAll()) {
            if (s.getTenDN().equalsIgnoreCase(nv.getTenDN()) && s.getMatKhau().equalsIgnoreCase(nv.getMatKhau())) {
                return true;
            }
        }
        return false;
    }



}
