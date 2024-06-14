package com.example.demo.controllers;

import com.example.demo.entity.HDCT;
import com.example.demo.entity.HoaDon;
import com.example.demo.entity.SPCT;
import com.example.demo.repository.assignment2.HDCT_Repositoryy;
import com.example.demo.repository.assignment2.HoaDonRepositoryy;
import com.example.demo.repository.assignment2.SPCT_Repositoryy;
import com.example.demo.securityConfig.AuthChecker;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("hdct")
public class HDCT_Controller {
    @Autowired
    private HDCT_Repositoryy hdctRepository;
    private List<HDCT> listHDCT = new ArrayList<>();
    @Autowired
    private HoaDonRepositoryy hoaDonRepository;
    private List<HoaDon> listHD = new ArrayList<>();
    @Autowired
    private SPCT_Repositoryy spct_repository;
    private List<SPCT> listSPCT = new ArrayList<>();

    @GetMapping("index")
    public String getIndex(Model model, @RequestParam(defaultValue = "1") int page,
                           @RequestParam(defaultValue = "5") int size,
                           HttpSession session,
                           @RequestParam(name = "idSPCT", defaultValue = "-1") Integer idSPCT) {
        if (!AuthChecker.isLoggedIn(session)) {
            session.setAttribute("error", "Bạn phải đăng nhập trước.");
            return "redirect:/login";
        } else if (!AuthChecker.isAdmin(session)) {
            session.setAttribute("error", "Admin mới được vào xem hóa đơn!");
            return "redirect:/nhan-vien/trang-chu";
        } else {
            List<HDCT> listHDCT;
            if (idSPCT == -1) {
                PageRequest pageable = PageRequest.of(page - 1, size);
                Page<HDCT> hdctPage = hdctRepository.findAllPaging(pageable);
                listHDCT = hdctPage.getContent();
            } else {
                listHDCT = hdctRepository.findByIdSPCT(idSPCT);
            }
            if (listHDCT.isEmpty()) {
                model.addAttribute("error", "Bảng trống");
            } else {
                // Các thao tác khác
            }
            model.addAttribute("listHDCT", listHDCT);
            model.addAttribute("currentPage", page);
            model.addAttribute("pageSize", size);
            model.addAttribute("totalPages", (int) Math.ceil((double) hdctRepository.count() / size));
            return "hdct/index";
        }
    }

    @GetMapping("create")
    public String create(@ModelAttribute("data") HoaDon hd, HttpSession session) {
        if (!AuthChecker.isLoggedIn(session)) {
            session.setAttribute("error", "Bạn phải đăng nhập trước.");
            return "redirect:/login";
        }
        return "hdct/create";
    }

    @PostMapping("store")
    public String store(Model model, @Valid HDCT hdct, BindingResult validateResult, HttpSession session) {
        if (validateResult.hasErrors()) {
            if (!AuthChecker.isLoggedIn(session)) {
                session.setAttribute("error", "Bạn phải đăng nhập trước.");
                return "redirect:/login";
            }
            List<FieldError> listError = validateResult.getFieldErrors();
            Map<String, String> errors = new HashMap<>();
            for (FieldError fe : listError) {
                errors.put(fe.getField(), fe.getDefaultMessage());
            }
            model.addAttribute("errors", errors);
            model.addAttribute("data", hdct);
            return "hdct/create";
        }
        hdctRepository.save(hdct);
        return "redirect:/hdct/index";
    }

    @GetMapping("edit/{id}")
    public String edit(Model model, @PathVariable("id") Integer id, HttpSession session) {
        if (!AuthChecker.isLoggedIn(session)) {
            session.setAttribute("error", "Bạn phải đăng nhập trước.");
            return "redirect:/login";
        }
        HDCT hdct = hdctRepository.getOne(id);
        if (hdct == null) {
            session.setAttribute("error", "HDCT không tồn tại");
            return "redirect:/hdct/index";
        }
        List<HoaDon> listHD = hoaDonRepository.findAll();
        model.addAttribute("listHD", listHD);

        List<SPCT> listSPCT = spct_repository.findAll();
        model.addAttribute("listSPCT", listSPCT);

        model.addAttribute("hdct", hdct);
        return "hdct/edit";
    }


    @PostMapping("update/{id}")
    public String update(@PathVariable("id") Integer id, @Valid HDCT hdct, BindingResult validateResult, Model model) {
        if (validateResult.hasErrors()) {
            List<FieldError> listError = validateResult.getFieldErrors();
            Map<String, String> errors = new HashMap<>();
            for (FieldError fe : listError) {
                errors.put(fe.getField(), fe.getDefaultMessage());
            }
            model.addAttribute("errors", errors);
            model.addAttribute("listHDCTDetail", hdct);

            listHD = this.hoaDonRepository.findAll();
            model.addAttribute("listHD", listHD);

            listSPCT = this.spct_repository.findAll();
            model.addAttribute("listSPCT", listSPCT);

            return "hdct/edit";
        }
        hdct.setId(id);
        hdctRepository.save(hdct);
        return "redirect:/hdct/index";
    }

}
