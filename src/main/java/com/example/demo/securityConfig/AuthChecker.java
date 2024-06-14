package com.example.demo.securityConfig;

import com.example.demo.entity.NhanVien;
import jakarta.servlet.http.HttpSession;

public class AuthChecker {
    public static boolean isLoggedIn(HttpSession session) {
        NhanVien loggedInUser = (NhanVien) session.getAttribute("loggedInUser");
        return loggedInUser != null;
    }

    public static boolean isAdmin(HttpSession session) {
        NhanVien loggedInUser = (NhanVien) session.getAttribute("loggedInUser");
        if (loggedInUser.getVaiTro().equals("Admin")) {
            return true;
        }
        return false;
    }
}
