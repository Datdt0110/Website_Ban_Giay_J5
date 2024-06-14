package com.example.demo.controllers;

import com.example.demo.entity.KhachHang;
import com.example.demo.repository.assignment2.KhachHangRepositoryy;
import com.example.demo.securityConfig.AuthChecker;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("khach-hang")
public class KhachHangController {
    @Autowired
    KhachHangRepositoryy khachHangRepository;
    List<KhachHang> listKH = new ArrayList<>();

    @GetMapping("index")
    public String getIndex(Model model, @RequestParam(defaultValue = "1") int page,
                           @RequestParam(defaultValue = "5") int size,
                           @RequestParam(name = "timKiem", defaultValue = "") String timKiem, HttpSession session) {
        if (!AuthChecker.isLoggedIn(session)) {
            session.setAttribute("error", "Bạn phải đăng nhập trước.");
            return "redirect:/login";
        } else {
            if (timKiem.isEmpty()) {
                Pageable pageable = PageRequest.of(page - 1, size);
                listKH = khachHangRepository.findAllPaging(pageable).getContent();
            } else {
                listKH = khachHangRepository.findByTimKiem(timKiem);
            }
            if (listKH.isEmpty()) {
                model.addAttribute("error", "Bảng trống");
            } else {
                model.addAttribute("listKH", listKH);
            }

            model.addAttribute("currentPage", page);
            model.addAttribute("pageSize", size);
            model.addAttribute("totalPages", (int) Math.ceil((double) khachHangRepository.findAll().size() / size));
            return "khach_hang/index";
        }
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        this.khachHangRepository.deleteById(id);
        return "redirect:/khach-hang/index";
    }

    @GetMapping("create")
    public String create(@ModelAttribute("data") KhachHang kh, HttpSession session) {
        if (!AuthChecker.isLoggedIn(session)) {
            session.setAttribute("error", "Bạn phải đăng nhập trước.");
            return "redirect:/login";
        }
        return "khach_hang/create";
    }

    @PostMapping("store")
    public String store(Model model, @Valid KhachHang kh, BindingResult validateResult) {
        if (validateResult.hasErrors()) {
            List<FieldError> listError = validateResult.getFieldErrors();
            Map<String, String> errors = new HashMap<>();
            for (FieldError fe : listError) {
                errors.put(fe.getField(), fe.getDefaultMessage());
            }
            model.addAttribute("errors", errors);
            model.addAttribute("data", kh);
            return "khach_hang/create";
        }
        khachHangRepository.save(kh);
        return "redirect:/khach-hang/index";
    }

    @GetMapping("edit/{id}")
    public String edit(Model model, @PathVariable("id") Integer id, HttpSession session) {
        if (!AuthChecker.isLoggedIn(session)) {
            session.setAttribute("error", "Bạn phải đăng nhập trước.");
            return "redirect:/login";
        }
        KhachHang listKHDetail = this.khachHangRepository.findById(id).get();
        model.addAttribute("listKHDetail", listKHDetail);
        return "khach_hang/edit";
    }

    @PostMapping("update/{id}")
    public String update(@PathVariable("id") Integer id, @Valid KhachHang kh, BindingResult validateResult, Model model) {
        if (validateResult.hasErrors()) {
            List<FieldError> listError = validateResult.getFieldErrors();
            Map<String, String> errors = new HashMap<>();
            for (FieldError fe : listError) {
                errors.put(fe.getField(), fe.getDefaultMessage());
            }
            model.addAttribute("errors", errors);
            model.addAttribute("listKHDetail", kh);
            return "khach_hang/edit";
        }
        kh.setId(id);
        khachHangRepository.save(kh);
        return "redirect:/khach-hang/index";
    }
}
