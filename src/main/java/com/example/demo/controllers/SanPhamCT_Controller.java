package com.example.demo.controllers;

import com.example.demo.entity.KichThuoc;
import com.example.demo.entity.MauSac;
import com.example.demo.entity.SPCT;
import com.example.demo.entity.SanPham;
import com.example.demo.repository.assignment2.KichThuocRepositoryy;
import com.example.demo.repository.assignment2.MauSacRepositoryy;
import com.example.demo.repository.assignment2.SPCT_Repositoryy;
import com.example.demo.repository.assignment2.SanPhamRepositoryy;
import com.example.demo.securityConfig.AuthChecker;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("spct")
public class SanPhamCT_Controller {
    @Autowired
    private SPCT_Repositoryy spctRepository;
    List<SPCT> listSPCT = new ArrayList<>();

    @Autowired
    private SanPhamRepositoryy sanPhamRepository;

    List<SanPham> listSP = new ArrayList<>();
    @Autowired
    private MauSacRepositoryy mauSacRepository;
    List<MauSac> listMS = new ArrayList<>();
    @Autowired
    private KichThuocRepositoryy kichThuocRepository;
    List<KichThuoc> listKT = new ArrayList<>();

    @GetMapping("index")
    public String getIndex(Model model, @RequestParam(defaultValue = "1") int page,
                           @RequestParam(defaultValue = "5") int size,
                           @RequestParam(name = "idSanPham", defaultValue = "-1") Integer idSanPham, HttpSession session) {
        if (!AuthChecker.isLoggedIn(session)) {
            session.setAttribute("error", "Bạn phải đăng nhập trước.");
            return "redirect:/login";
        } else {
            List<SPCT> listSPCT;
            if (idSanPham == -1) {
                PageRequest pageable = PageRequest.of(page - 1, size, Sort.by("id").descending());
                Page<SPCT> spctPage = spctRepository.findAll(pageable);
                listSPCT = spctPage.getContent();
            } else {
                listSPCT = spctRepository.findBySanPhamId(idSanPham);
                listSPCT.sort(Comparator.comparing(SPCT::getId).reversed());
            }

            listSP = sanPhamRepository.findAll();
            model.addAttribute("listSP", listSP);

            if (listSPCT.isEmpty()) {
                model.addAttribute("error", "Bảng trống");
            } else {
                model.addAttribute("listSPCT", listSPCT);
            }

            model.addAttribute("currentPage", page);
            model.addAttribute("pageSize", size);
            model.addAttribute("totalPages", (int) Math.ceil((double) spctRepository.count() / size));
            return "spct/index";
        }
    }


    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        this.spctRepository.deleteById(id);
        return "redirect:/spct/index";
    }

    @GetMapping("/create")
    public String createSPCT(Model model) {
        model.addAttribute("listSP", sanPhamRepository.findAll());
        model.addAttribute("listMS", mauSacRepository.findAll());
        model.addAttribute("listKT", kichThuocRepository.findAll());
        model.addAttribute("data", new SPCT());
        return "spct/create";
    }

    @PostMapping("/store")
    public String storeSPCT(@Valid @ModelAttribute("data") SPCT spct,
                            BindingResult result,
                            @RequestParam Integer idSanPham,
                            @RequestParam Integer idMauSac,
                            @RequestParam Integer idKichThuoc,
                            Model model) {
        if (result.hasErrors()) {
            List<FieldError> listError = result.getFieldErrors();
            Map<String, String> errors = new HashMap<>();
            for (FieldError fe : listError) {
                errors.put(fe.getField(), fe.getDefaultMessage());
            }
            model.addAttribute("errors", errors);
            model.addAttribute("listSP", sanPhamRepository.findAll());
            model.addAttribute("listMS", mauSacRepository.findAll());
            model.addAttribute("listKT", kichThuocRepository.findAll());
            return "spct/create";
        }

        spct.setSanPham(sanPhamRepository.getById(idSanPham));
        spct.setMauSac(mauSacRepository.getById(idMauSac));
        spct.setKichThuoc(kichThuocRepository.getById(idKichThuoc));
        spctRepository.save(spct);
        return "redirect:/spct/index";
    }

    @GetMapping("edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model, HttpSession session) {
        if (!AuthChecker.isLoggedIn(session)) {
            session.setAttribute("error", "Bạn phải đăng nhập trước.");
            return "redirect:/login";
        }
        Optional<SPCT> spctOptional = spctRepository.findById(id);
        if (spctOptional.isPresent()) {
            SPCT spct = spctOptional.get();
            model.addAttribute("spct", spct);
            model.addAttribute("listSP", sanPhamRepository.findAll());
            model.addAttribute("listMS", mauSacRepository.findAll());
            model.addAttribute("listKT", kichThuocRepository.findAll());
            return "spct/edit";
        } else {
            // Handle not found scenario
            return "redirect:/spct/index"; // Redirect to a list page or any other page
        }
    }

    @PostMapping("update/{id}")
    public String update(@PathVariable("id") Integer id, @Valid SPCT spct, BindingResult validateResult, Model model) {
        if (validateResult.hasErrors()) {
            List<FieldError> listError = validateResult.getFieldErrors();
            Map<String, String> errors = new HashMap<>();
            for (FieldError fe : listError) {
                errors.put(fe.getField(), fe.getDefaultMessage());
            }
            model.addAttribute("errors", errors);
            model.addAttribute("spct", spct);
            return "spct/edit";
        }
        spct.setId(id);
        spctRepository.save(spct);
        return "redirect:/spct/index";
    }

}
