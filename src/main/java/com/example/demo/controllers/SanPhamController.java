package com.example.demo.controllers;

import com.example.demo.entity.SanPham;
import com.example.demo.repository.assignment2.SanPhamRepositoryy;
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
@RequestMapping("san-pham")

public class SanPhamController {
    @Autowired
    private SanPhamRepositoryy sanPhamRepository ;
    List<SanPham> listSP = new ArrayList<>();



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
                listSP = sanPhamRepository.findAllPaging(pageable).getContent();
            } else {
                listSP = sanPhamRepository.findByTimKiem(timKiem);
            }

            if (listSP.isEmpty()) {
                model.addAttribute("error", "Bảng trống");
            } else {
                model.addAttribute("listSP", listSP);
            }

            model.addAttribute("currentPage", page);
            model.addAttribute("pageSize", size);
            model.addAttribute("totalPages", (int) Math.ceil((double) sanPhamRepository.findAll().size() / size));
            return "san_pham/index";
        }
    }


    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        this.sanPhamRepository.deleteById(id);
        return "redirect:/san-pham/index";
    }

    @GetMapping("create")
    public String create(@ModelAttribute("data") SanPham sp, HttpSession session) {
        if (!AuthChecker.isLoggedIn(session)) {
            session.setAttribute("error", "Bạn phải đăng nhập trước.");
            return "redirect:/login";
        }
        return "san_pham/create";
    }

    @PostMapping("store")
    public String store(Model model, @Valid SanPham sp, BindingResult validateResult) {
        if (validateResult.hasErrors()) {
            List<FieldError> listError = validateResult.getFieldErrors();
            Map<String, String> errors = new HashMap<>();
            for (FieldError fe : listError) {
                errors.put(fe.getField(), fe.getDefaultMessage());
            }
            model.addAttribute("errors", errors);
            model.addAttribute("data", sp);
            return "san_pham/create";
        }
        sanPhamRepository.save(sp);
        return "redirect:/san-pham/index";
    }

    @GetMapping("edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model, HttpSession session) {
        if (!AuthChecker.isLoggedIn(session)) {
            session.setAttribute("error", "Bạn phải đăng nhập trước.");
            return "redirect:/login";
        }
        SanPham listSPDetail = this.sanPhamRepository.findById(id).get();
        model.addAttribute("listSPDetail", listSPDetail);
        return "/san_pham/edit";
    }

    @PostMapping("update/{id}")
    public String update(@PathVariable("id") Integer id, @Valid SanPham sp, BindingResult validateResult, Model model) {
        if (validateResult.hasErrors()) {
            List<FieldError> listError = validateResult.getFieldErrors();
            Map<String, String> errors = new HashMap<>();
            for (FieldError fe : listError) {
                errors.put(fe.getField(), fe.getDefaultMessage());
            }
            model.addAttribute("errors", errors);
            model.addAttribute("listSPDetail", sp);
            return "/san_pham/edit";
        }
        sp.setId(id);
        sanPhamRepository.save(sp);
        return "redirect:/san-pham/index";
    }

}
