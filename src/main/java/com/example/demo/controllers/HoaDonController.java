package com.example.demo.controllers;

import com.example.demo.entity.HoaDon;
import com.example.demo.entity.KhachHang;
import com.example.demo.entity.NhanVien;
import com.example.demo.repository.assignment2.HoaDonRepositoryy;
import com.example.demo.repository.assignment2.KhachHangRepositoryy;
import com.example.demo.repository.assignment2.NhanVienRepositoryy;
import com.example.demo.securityConfig.AuthChecker;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("hoa-don")
public class HoaDonController {
    @Autowired
    HoaDonRepositoryy hoaDonRepository;
    @Autowired
    NhanVienRepositoryy nhanVienRepository;
    @Autowired
    KhachHangRepositoryy khachHangRepository;

    @GetMapping("index")
    public String getIndex(Model model, @RequestParam(defaultValue = "1") int page,
                           @RequestParam(defaultValue = "5") int size,
                           @RequestParam(name = "idKH", defaultValue = "-1") Integer idKH, HttpSession session) {
        if (!AuthChecker.isLoggedIn(session)) {
            session.setAttribute("error", "Bạn phải đăng nhập trước.");
            return "redirect:/login";
        } else if (!AuthChecker.isAdmin(session)) {
            session.setAttribute("error", "Admin mới được vào xem hóa đơn!");
            return "redirect:/nhan-vien/trang-chu";
        } else {
            List<HoaDon> listHD;
            if (idKH == -1) {
                listHD = hoaDonRepository.findAll(PageRequest.of(page - 1, size)).getContent();
            } else {
                listHD = hoaDonRepository.findByIdKH(idKH);
            }

            if (listHD.isEmpty()) {
                model.addAttribute("error", "Không tìm thấy hóa đơn cho khách hàng có ID: " + idKH);
            } else {
                model.addAttribute("listHD", listHD);
            }

            model.addAttribute("listNV", nhanVienRepository.findAll());
            model.addAttribute("listKH", khachHangRepository.findAll());

            model.addAttribute("currentPage", page);
            model.addAttribute("pageSize", size);
            model.addAttribute("totalPages", (int) Math.ceil((double) hoaDonRepository.count() / size));

            return "hoa_don/index";
        }
    }

    @GetMapping("create")
    public String create(@ModelAttribute("data") HoaDon hd, HttpSession session) {
        if (!AuthChecker.isLoggedIn(session)) {
            session.setAttribute("error", "Bạn phải đăng nhập trước.");
            return "redirect:/login";
        }
        return "hoa_don/create";
    }

    @PostMapping("store")
    public String store(Model model, @Valid HoaDon hd, BindingResult validateResult) {
        if (validateResult.hasErrors()) {
            List<FieldError> listError = validateResult.getFieldErrors();
            Map<String, String> errors = new HashMap<>();
            for (FieldError fe : listError) {
                errors.put(fe.getField(), fe.getDefaultMessage());
            }
            model.addAttribute("errors", errors);
            model.addAttribute("data", hd);
            return "hoa_don/create";
        }
        hoaDonRepository.save(hd);
        return "redirect:/hoa-don/index";
    }

    @GetMapping("edit/{id}")
    public String edit(Model model, @PathVariable("id") Integer id, HttpSession session) {
        if (!AuthChecker.isLoggedIn(session)) {
            session.setAttribute("error", "Bạn phải đăng nhập trước.");
            return "redirect:/login";
        } else if (!AuthChecker.isAdmin(session)) {
            session.setAttribute("error", "Admin mới được vào xem hóa đơn!");
            return "redirect:/nhan-vien/trang-chu";
        }

        HoaDon listHDDetail = hoaDonRepository.findById(id).get();
        model.addAttribute("listHDDetail", listHDDetail);

        List<NhanVien> listNV = nhanVienRepository.findAll();
        model.addAttribute("listNV", listNV);
        List<KhachHang> listKH = khachHangRepository.findAll();
        model.addAttribute("listKH", listKH);

        return "hoa_don/edit";
    }

    @PostMapping("update/{id}")
    public String update(@PathVariable("id") Integer id, @Valid HoaDon hd, BindingResult validateResult, Model model, HttpSession session) {
        if (!AuthChecker.isLoggedIn(session)) {
            session.setAttribute("error", "Bạn phải đăng nhập trước.");
            return "redirect:/login";
        } else if (!AuthChecker.isAdmin(session)) {
            session.setAttribute("error", "Admin mới được vào xem hóa đơn!");
            return "redirect:/nhan-vien/trang-chu";
        }
        if (validateResult.hasErrors()) {
            List<FieldError> listError = validateResult.getFieldErrors();
            Map<String, String> errors = new HashMap<>();
            for (FieldError fe : listError) {
                errors.put(fe.getField(), fe.getDefaultMessage());
            }
            model.addAttribute("errors", errors);
            model.addAttribute("listHDDetail", hd);

            model.addAttribute("listNV", nhanVienRepository.findAll());
            model.addAttribute("listKH", khachHangRepository.findAll());

            return "hoa_don/edit";
        }

        hd.setId(id);
        hoaDonRepository.save(hd);
        return "redirect:/hoa-don/index";
    }
}
