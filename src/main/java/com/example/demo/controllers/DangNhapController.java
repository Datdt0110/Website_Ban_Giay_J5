package com.example.demo.controllers;

import com.example.demo.entity.NhanVien;
import com.example.demo.repository.assignment1.DangNhapRepository;
import com.example.demo.repository.assignment2.NhanVienRepositoryy;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DangNhapController {

    @Autowired
    private DangNhapRepository dangNhapRepository;

    @Autowired
    private NhanVienRepositoryy nhanVienRepository;

    @PostMapping("/login")
    public String login(@RequestParam(name = "tenDN") String tenDN,
                        @RequestParam(name = "matKhau") String matKhau,
                        HttpSession session, Model model) {
        NhanVien nv = new NhanVien();
        nv.setTenDN(tenDN);
        nv.setMatKhau(matKhau);

        boolean isValid = dangNhapRepository.validateLogin(nv);
        if (isValid) {
            NhanVien loggedInUser = nhanVienRepository.findByTenDN(tenDN);
            if (loggedInUser != null) {
                session.setAttribute("loggedInUser", loggedInUser);
                session.removeAttribute("errorMessage");

                String vaiTro = loggedInUser.getVaiTro();
                if (vaiTro != null) {
                    if (vaiTro.equals("Admin")) {
                        model.addAttribute("loggedInUser", loggedInUser);
                        return "redirect:/admin/trang-chu";
                    } else if (vaiTro.equals("Nhân Viên")) {
                        return "redirect:/nhan-vien/trang-chu";
                    } else {
                        model.addAttribute("errorMessage", "Vai trò không hợp lệ.");
                        return "login";
                    }
                } else {
                    model.addAttribute("errorMessage", "Vai trò của người dùng không xác định.");
                    return "login";
                }
            } else {
                model.addAttribute("errorMessage", "Người dùng không tồn tại.");
                return "login";
            }
        } else {
            model.addAttribute("errorMessage", "Đăng nhập không thành công. Vui lòng kiểm tra lại tên đăng nhập và mật khẩu.");
            model.addAttribute("data", nv);
            return "login";
        }
    }


    @GetMapping("/login")
    public String showLogin(HttpSession session, Model model, @ModelAttribute("data") NhanVien nv) {
        model.addAttribute("successMessage", session.getAttribute("successMessage"));
        model.addAttribute("errorMessage", session.getAttribute("errorMessage"));
        return "login";
    }
}
