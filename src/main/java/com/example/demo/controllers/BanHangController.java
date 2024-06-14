package com.example.demo.controllers;

import com.example.demo.entity.*;
import com.example.demo.repository.assignment2.*;
import com.example.demo.securityConfig.AuthChecker;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("ban-hang")
public class BanHangController {

    @Autowired
    HoaDonRepositoryy hoaDonRepository;

    @Autowired
    HDCT_Repositoryy hdctRepository;

    @Autowired
    KhachHangRepositoryy khachHangRepository;

    @Autowired
    KichThuocRepositoryy kichThuocRepository;

    @Autowired
    MauSacRepositoryy mauSacRepository;

    @Autowired
    NhanVienRepositoryy nhanVienRepository;

    @Autowired
    SanPhamRepositoryy sanPhamRepository;

    @Autowired
    SPCT_Repositoryy spct_repository;

    List<HoaDon> listHD;
    List<HDCT> listHDCT;
    List<KhachHang> listKH;
    List<KichThuoc> listKT;
    List<MauSac> listMS;
    List<NhanVien> listNV;
    List<SanPham> listSP;
    List<SPCT> listSPCT;

    HoaDon hoaDonDetail;
    KhachHang khachHangDetail;
    Integer idHoaDon;
    double tongTien;

    public BanHangController() {
        listHD = new ArrayList<>();
        listHDCT = new ArrayList<>();
        listKH = new ArrayList<>();
        listKT = new ArrayList<>();
        listMS = new ArrayList<>();
        listNV = new ArrayList<>();
        listSP = new ArrayList<>();
        listSPCT = new ArrayList<>();
        hoaDonDetail = new HoaDon();
        khachHangDetail = new KhachHang();
        idHoaDon = 1;
        tongTien = 0;
    }

    @GetMapping("index")
    public String getIndex(Model model, HttpSession session, @RequestParam(name = "sdtKH", defaultValue = "") String sdtKH) {
        if (!AuthChecker.isLoggedIn(session)) {
            session.setAttribute("error", "Bạn phải đăng nhập trước.");
            return "redirect:/login";
        }
        if (sdtKH.isEmpty()) {
            model.addAttribute("listNV", nhanVienRepository.findAll());
            model.addAttribute("listKH", khachHangRepository.findAll());
            model.addAttribute("listHD", hoaDonRepository.findByTrangThai(1)); // Chỉ hiển thị hóa đơn chưa thanh toán
            model.addAttribute("listHDCT", hdctRepository.findAll());
            model.addAttribute("listKT", kichThuocRepository.findAll());
            model.addAttribute("listMS", mauSacRepository.findAll());
            model.addAttribute("listSP", sanPhamRepository.findAll());
            model.addAttribute("listSPCT", spct_repository.findAll());

            model.addAttribute("hoaDonDetail", hoaDonDetail);
            model.addAttribute("khachHangDetail", khachHangDetail);
            model.addAttribute("tongTien", tongTien);
        } else {
            KhachHang khachHangDetail = khachHangRepository.findBySdtKH(sdtKH);
            model.addAttribute("khachHangDetail", khachHangDetail);
        }
        return "ban_hang/index";
    }




//    @PostMapping("tao-hoa-don/{id}")
//    public String taoHoaDon(Model model, HoaDon hd, @RequestParam(name = "tenKH", defaultValue = "") String tenKH,
//                            @PathVariable("id") Integer idNV) {
//
//        // Tìm khách hàng theo tên
//        KhachHang kh = khachHangRepository.findByTenKH(tenKH);
//        if (kh == null) {
//            model.addAttribute("error", "Khách hàng không tồn tại.");
//            return "redirect:/ban-hang/index";
//        }
//
//        // Tìm nhân viên theo ID
//        NhanVien nv = nhanVienRepository.findById(idNV).orElse(null);
//        if (nv == null) {
//            model.addAttribute("error", "Nhân viên không tồn tại.");
//            return "redirect:/ban-hang/index";
//        }
//
//        // Tạo hóa đơn mới
//        HoaDon newHoaDon = new HoaDon();
//        newHoaDon.setKhachHang(kh); // Sử dụng đối tượng KhachHang
//        newHoaDon.setTrangThai(1);
//        newHoaDon.setNhanVien(nv); // Sử dụng ID của nhân viên từ URL
//        LocalDate today = LocalDate.now();
//        newHoaDon.setNgayMuaHang(today);
//
//        // Lưu hóa đơn vào cơ sở dữ liệu
//        hoaDonRepository.save(newHoaDon);
//
//        // Đặt hóa đơn chi tiết mới được tạo thành hóa đơn hiện tại
//        hoaDonDetail = newHoaDon;
//
//
//
//        // Chuyển hướng về trang bán hàng
//        return "redirect:/ban-hang/index";
//    }
@PostMapping("tao-hoa-don/{id}")
public String taoHoaDon(Model model, HoaDon hd, @PathVariable("id") Integer idNV) {

    // Tìm khách hàng với ID mặc định là 1
    KhachHang kh = khachHangRepository.findById(1).orElse(null);
    if (kh == null) {
        model.addAttribute("error", "Khách hàng mặc định không tồn tại.");
        return "redirect:/ban-hang/index";
    }

    // Tìm nhân viên theo ID
    NhanVien nv = nhanVienRepository.findById(idNV).orElse(null);
    if (nv == null) {
        model.addAttribute("error", "Nhân viên không tồn tại.");
        return "redirect:/ban-hang/index";
    }

    // Tạo hóa đơn mới
    HoaDon newHoaDon = new HoaDon();
    newHoaDon.setKhachHang(kh); // Sử dụng khách hàng với ID 1
    newHoaDon.setTrangThai(1);
    newHoaDon.setNhanVien(nv); // Sử dụng ID của nhân viên từ URL
    LocalDate today = LocalDate.now();
    newHoaDon.setNgayMuaHang(today);

    // Lưu hóa đơn vào cơ sở dữ liệu
    hoaDonRepository.save(newHoaDon);

    // Đặt hóa đơn chi tiết mới được tạo thành hóa đơn hiện tại
    hoaDonDetail = newHoaDon;

    // Chuyển hướng về trang bán hàng
    return "redirect:/ban-hang/index";
}


    @GetMapping("search")
    public String search(Model model, @RequestParam(name = "sdtKH", defaultValue = "") String sdtKH) {
        KhachHang khachHangDetail = khachHangRepository.findBySdtKH(sdtKH);
        model.addAttribute("khachHangDetail", khachHangDetail);

        model.addAttribute("listNV", nhanVienRepository.findAll());
        model.addAttribute("listKH", khachHangRepository.findAll());

        model.addAttribute("listHD", hoaDonRepository.findByTrangThai(1)); // Chỉ hiển thị hóa đơn chưa thanh toán
        model.addAttribute("listHDCT", hdctRepository.findAll());
        model.addAttribute("listKT", kichThuocRepository.findAll());
        model.addAttribute("listMS", mauSacRepository.findAll());
        model.addAttribute("listSP", sanPhamRepository.findAll());
        model.addAttribute("listSPCT", spct_repository.findAll());

        return "ban_hang/index";
    }


    @GetMapping("selectHD/{id}")
    public String selectHD(Model model, @PathVariable("id") Integer idHoaDon) {
        // Tìm hóa đơn theo id
        HoaDon hoaDon = hoaDonRepository.findById(idHoaDon).orElse(null);
        if (hoaDon == null) {
            model.addAttribute("error", "Hóa đơn không tồn tại.");
            return "redirect:/ban-hang/index";
        }
        hoaDonDetail = hoaDon;

        // Tìm khách hàng theo id của hóa đơn
        KhachHang khachHang = khachHangRepository.findById(hoaDon.getKhachHang().getId()).orElse(null);
        if (khachHang == null) {
            model.addAttribute("error", "Khách hàng không tồn tại.");
            return "redirect:/ban-hang/index";
        }
        khachHangDetail = khachHang;

        // Lấy danh sách chi tiết hóa đơn theo id của hóa đơn
        listHDCT = hdctRepository.findByHoaDonId(idHoaDon);

        // Tính tổng tiền từ danh sách chi tiết hóa đơn
        tongTien = listHDCT.stream().mapToDouble(hdct -> hdct.getDonGia().doubleValue()).sum();

        // Đưa các thuộc tính vào model để hiển thị trên trang
        model.addAttribute("hoaDonDetail", hoaDonDetail);
        model.addAttribute("khachHangDetail", khachHangDetail);
        model.addAttribute("listHDCT", listHDCT);
        model.addAttribute("tongTien", tongTien);

        // Đưa các danh sách khác vào model để hiển thị trên trang
        model.addAttribute("listNV", nhanVienRepository.findAll());
        model.addAttribute("listKH", khachHangRepository.findAll());
        model.addAttribute("listHD", hoaDonRepository.findByTrangThai(1)); // Chỉ hiển thị hóa đơn chưa thanh toán
        model.addAttribute("listKT", kichThuocRepository.findAll());
        model.addAttribute("listMS", mauSacRepository.findAll());
        model.addAttribute("listSP", sanPhamRepository.findAll());
        model.addAttribute("listSPCT", spct_repository.findAll());

        return "ban_hang/index";
    }
    @GetMapping("deleteHDCT/{id}")
    public String deleteHDCT(Model model, @PathVariable("id") Integer idHDCT) {
        HDCT hdct = hdctRepository.findById(idHDCT).orElse(null);
        if (hdct != null) {
            // Lấy sản phẩm chi tiết tương ứng với bản ghi đã xóa
            SPCT chiTietSanPham = hdct.getSpct();
            if (chiTietSanPham != null) {
                int soLuongSanPhamHienTai = chiTietSanPham.getSoLuong();
                int soLuongDaBan = hdct.getSoLuong();
                chiTietSanPham.setSoLuong(soLuongSanPhamHienTai + soLuongDaBan); // Tăng số lượng sản phẩm

                // Lưu lại thông tin sản phẩm chi tiết
                spct_repository.save(chiTietSanPham);
            }

            // Chuyển đổi BigDecimal sang double
            double donGia = hdct.getDonGia().doubleValue();
            tongTien -= donGia * hdct.getSoLuong(); // Giảm tổng tiền theo số lượng

            // Xóa bản ghi từ danh sách hóa đơn chi tiết
            hdctRepository.deleteById(idHDCT);
            model.addAttribute("listHD", hoaDonRepository.findByTrangThai(1)); // Chỉ hiển thị hóa đơn chưa thanh toán
        }
        return "redirect:/ban-hang/index";
    }
//    @GetMapping("deleteHDCT/{id}")
//    public String deleteHDCT(Model model, @PathVariable("id") Integer idHDCT) {
//        HDCT hdct = hdctRepository.findById(idHDCT).orElse(null);
//        if (hdct != null) {
//            // Chuyển đổi BigDecimal sang double
//            double donGia = hdct.getDonGia().doubleValue();
//            tongTien -= donGia; // Giảm tổng tiền
//
//            // Lấy sản phẩm chi tiết tương ứng với bản ghi đã xóa
//            SPCT chiTietSanPham = hdct.getSpct();
//            int soLuongSanPhamHienTai = chiTietSanPham.getSoLuong();
//            chiTietSanPham.setSoLuong(soLuongSanPhamHienTai + 1); // Tăng số lượng sản phẩm
//
//            // Lưu lại thông tin sản phẩm chi tiết
//            spct_repository.save(chiTietSanPham);
//
//
//            // Xóa bản ghi từ danh sách hóa đơn chi tiết
//            hdctRepository.deleteById(idHDCT);
//            model.addAttribute("listHD", hoaDonRepository.findByTrangThai(1)); // Chỉ hiển thị hóa đơn chưa thanh toán
//        }
//        return "redirect:/ban-hang/index";
//    }

//    @GetMapping("selectSPCT/{id}")
//    public String selectSPCT(Model model, @PathVariable("id") Integer id) {
//        // Tìm sản phẩm chi tiết theo id
//        SPCT chiTietSanPhamDetail = spct_repository.findById(id).orElse(null);
//        if (chiTietSanPhamDetail == null) {
//            model.addAttribute("error", "Sản phẩm không tồn tại.");
//            return "redirect:/ban-hang/index";
//        }
//
//        boolean daCoTrongHoaDon = false;
//        for (HDCT hoaDonCT : listHDCT) {
//            if (hoaDonCT.getSpct().getId().equals(id)) {
//                int newSoLuong = hoaDonCT.getSoLuong() + 1;
//                hoaDonCT.setSoLuong(newSoLuong);
//                double tongTienDouble = hoaDonCT.getSpct().getDonGia().doubleValue() * newSoLuong;
//                hoaDonCT.setTongTien(BigDecimal.valueOf(tongTienDouble));
//
//                // Cập nhật lại số lượng sản phẩm trong danh sách chi tiết sản phẩm
//                chiTietSanPhamDetail.setSoLuong(chiTietSanPhamDetail.getSoLuong() - 1);
//                spct_repository.save(chiTietSanPhamDetail);
//
//                // Lưu lại thông tin hóa đơn chi tiết
//                hdctRepository.save(hoaDonCT);
//
//                daCoTrongHoaDon = true;
//                break;
//            }
//        }
//
//        if (!daCoTrongHoaDon) {
//            HDCT hoaDonChiTiet = new HDCT();
//            hoaDonChiTiet.setHoaDon(hoaDonDetail);
//            hoaDonChiTiet.setSpct(chiTietSanPhamDetail);
//            hoaDonChiTiet.setDonGia(chiTietSanPhamDetail.getDonGia());
//            hoaDonChiTiet.setSoLuong(1);
//            hoaDonChiTiet.setTrangThai(0);
//            hoaDonChiTiet.setTongTien(chiTietSanPhamDetail.getDonGia().multiply(BigDecimal.valueOf(1)));
//
//            hdctRepository.save(hoaDonChiTiet);
//
//            // Cập nhật lại số lượng sản phẩm trong danh sách chi tiết sản phẩm
//            chiTietSanPhamDetail.setSoLuong(chiTietSanPhamDetail.getSoLuong() - 1);
//            spct_repository.save(chiTietSanPhamDetail);
//        }
//
//        // Cập nhật lại danh sách chi tiết hóa đơn và tổng tiền
//        listHDCT = hdctRepository.findByHoaDonId(idHoaDon);
//        tongTien = listHDCT.stream()
//                .mapToDouble(hdct -> hdct.getTongTien().doubleValue())
//                .sum();
//
//        model.addAttribute("hoaDonDetail", hoaDonDetail);
//        model.addAttribute("khachHangDetail", khachHangDetail);
//        model.addAttribute("listHDCT", listHDCT);
//        model.addAttribute("tongTien", tongTien);
//
//        return "redirect:/ban-hang/index";
//    }
@GetMapping("selectSPCT/{id}")
public String selectSPCT(Model model, @PathVariable("id") Integer id) {
    // Tìm sản phẩm chi tiết theo id
    SPCT chiTietSanPhamDetail = spct_repository.findById(id).orElse(null);
    if (chiTietSanPhamDetail == null) {
        model.addAttribute("error", "Sản phẩm không tồn tại.");
        return "redirect:/ban-hang/index";
    }

    boolean daCoTrongHoaDon = false;

    for (HDCT hoaDonCT : listHDCT) {
        if (hoaDonCT.getSpct().getId().equals(id) && hoaDonCT.getTrangThai() == 0) {
            int newSoLuong = hoaDonCT.getSoLuong() + 1;
            hoaDonCT.setSoLuong(newSoLuong);
            double tongTienDouble = hoaDonCT.getSpct().getDonGia().doubleValue() * newSoLuong;
            hoaDonCT.setTongTien(BigDecimal.valueOf(tongTienDouble));

            // Cập nhật lại số lượng sản phẩm trong danh sách chi tiết sản phẩm
            chiTietSanPhamDetail.setSoLuong(chiTietSanPhamDetail.getSoLuong() - 1);
            spct_repository.save(chiTietSanPhamDetail);

            // Lưu lại thông tin hóa đơn chi tiết
            hdctRepository.save(hoaDonCT);

            daCoTrongHoaDon = true;
            break;
        }
    }

    if (!daCoTrongHoaDon) {
        HDCT hoaDonChiTiet = new HDCT();
        hoaDonChiTiet.setHoaDon(hoaDonDetail);
        hoaDonChiTiet.setSpct(chiTietSanPhamDetail);
        hoaDonChiTiet.setDonGia(chiTietSanPhamDetail.getDonGia());
        hoaDonChiTiet.setSoLuong(1);
        hoaDonChiTiet.setTrangThai(0);
        hoaDonChiTiet.setTongTien(chiTietSanPhamDetail.getDonGia().multiply(BigDecimal.valueOf(1)));

        hdctRepository.save(hoaDonChiTiet);

        // Cập nhật lại số lượng sản phẩm trong danh sách chi tiết sản phẩm
        chiTietSanPhamDetail.setSoLuong(chiTietSanPhamDetail.getSoLuong() - 1);
        spct_repository.save(chiTietSanPhamDetail);
    }

    // Cập nhật lại danh sách chi tiết hóa đơn và tổng tiền
    listHDCT = hdctRepository.findByHoaDonId(idHoaDon);
    tongTien = listHDCT.stream()
            .mapToDouble(hdct -> hdct.getTongTien().doubleValue())
            .sum();

    model.addAttribute("hoaDonDetail", hoaDonDetail);
    model.addAttribute("khachHangDetail", khachHangDetail);
    model.addAttribute("listHDCT", listHDCT);
    model.addAttribute("tongTien", tongTien);

    return "redirect:/ban-hang/index";
}


    //
@PostMapping("thanh-toan")
public String thanhToan(Model model) {
    if (hoaDonDetail != null && hoaDonDetail.getTrangThai() == 1) {
        hoaDonDetail.setTrangThai(0); // Đánh dấu hóa đơn đã được thanh toán
        hoaDonRepository.save(hoaDonDetail);

        for (HDCT hdct : listHDCT) {
            hdct.setTrangThai(0); // Đánh dấu hóa đơn chi tiết đã được thanh toán
            hdctRepository.save(hdct);

            // Giảm số lượng sản phẩm trong kho
            SPCT spct = hdct.getSpct();
            if (spct != null) {
                spct.setSoLuong(spct.getSoLuong() - hdct.getSoLuong());
                spct_repository.save(spct);
            }
        }

        // Xóa danh sách chi tiết hóa đơn và cập nhật tổng tiền
        listHDCT.clear();
        tongTien = 0;
        idHoaDon++;


    }
    return "redirect:/ban-hang/index";
}

}
