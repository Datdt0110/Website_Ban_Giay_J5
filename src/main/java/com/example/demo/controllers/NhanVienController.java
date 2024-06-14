package com.example.demo.controllers;

import com.example.demo.entity.NhanVien;
import com.example.demo.repository.assignment2.NhanVienRepositoryy;
import com.example.demo.securityConfig.AuthChecker;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("nhan-vien")
public class NhanVienController {
    @Autowired
    NhanVienRepositoryy nhanVienRepository;
    List<NhanVien> listNV = new ArrayList<>();

    @GetMapping("index")
    public String getIndex(Model model,
                           @RequestParam(defaultValue = "1") int page,
                           @RequestParam(defaultValue = "5") int size,
                           @RequestParam(name = "timKiem", defaultValue = "") String timKiem,
                           @RequestParam(name = "ma", required = false) String ma,
                           HttpSession session) {
        if (!AuthChecker.isLoggedIn(session)) {
            session.setAttribute("error", "Bạn phải đăng nhập trước.");
            return "redirect:/login";
        } else {
            if (ma != null && !ma.isEmpty()) {
                NhanVien nhanVien = nhanVienRepository.findByMa(ma);
                if (nhanVien != null) {
                    listNV = List.of(nhanVien);
                } else {
                    listNV = List.of();
                }
            } else if (timKiem.isEmpty()) {
                Pageable pageable = PageRequest.of(page - 1, size);
                listNV = nhanVienRepository.findAllPaging(pageable).getContent();
            } else {
                listNV = nhanVienRepository.findByTimKiem(timKiem);
            }

            if (listNV.isEmpty()) {
                model.addAttribute("error", "Bảng trống");
            } else {
                model.addAttribute("listNV", listNV);
            }

            model.addAttribute("currentPage", page);
            model.addAttribute("pageSize", size);
            model.addAttribute("totalPages", (int) Math.ceil((double) nhanVienRepository.findAll().size() / size));
            return "nhan_vien/index";
        }
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        this.nhanVienRepository.deleteById(id);
        return "redirect:/nhan-vien/index";
    }

    @GetMapping("create")
    public String create(@ModelAttribute("data") NhanVien nv, HttpSession session) {
        if (!AuthChecker.isLoggedIn(session)) {
            session.setAttribute("error", "Bạn phải đăng nhập trước.");
            return "redirect:/login";
        }
        return "nhan_vien/create";
    }

    @PostMapping("store")
    public String store(Model model, @Valid NhanVien nv, BindingResult validateResult) {
        if (validateResult.hasErrors()) {
            List<FieldError> listError = validateResult.getFieldErrors();
            Map<String, String> errors = new HashMap<>();
            for (FieldError fe : listError) {
                errors.put(fe.getField(), fe.getDefaultMessage());
            }
            model.addAttribute("errors", errors);
            model.addAttribute("data", nv);
            return "nhan_vien/create";
        }
        nhanVienRepository.save(nv);
        return "redirect:/nhan-vien/index";
    }

    @GetMapping("edit/{id}")
    public String edit(Model model, @PathVariable("id") Integer id, HttpSession session) {
        if (!AuthChecker.isLoggedIn(session)) {
            session.setAttribute("error", "Bạn phải đăng nhập trước.");
            return "redirect:/login";
        }
        NhanVien listNVDetail = this.nhanVienRepository.findById(id).orElse(null);
        if (listNVDetail == null) {
            model.addAttribute("error", "Nhân viên không tồn tại.");
            return "redirect:/nhan-vien/index";
        }
        model.addAttribute("listNVDetail", listNVDetail);
        return "nhan_vien/edit";
    }

    @PostMapping("update/{id}")
    public String update(@PathVariable("id") Integer id, @Valid NhanVien nv, BindingResult validateResult, Model model) {
        if (validateResult.hasErrors()) {
            List<FieldError> listError = validateResult.getFieldErrors();
            Map<String, String> errors = new HashMap<>();
            for (FieldError fe : listError) {
                errors.put(fe.getField(), fe.getDefaultMessage());
            }
            model.addAttribute("errors", errors);
            model.addAttribute("listNVDetail", nv);
            return "nhan_vien/edit";
        }
        nv.setId(id);
        nhanVienRepository.save(nv);
        return "redirect:/nhan-vien/index";
    }

    @GetMapping("export")
    public void export(HttpServletResponse response) throws IOException {
        List<NhanVien> listNV = nhanVienRepository.findAll();

        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=nhan_vien.xlsx");

        try (Workbook workbook = new XSSFWorkbook(); OutputStream out = response.getOutputStream()) {
            Sheet sheet = workbook.createSheet("NhanVien");

            Row headerRow = sheet.createRow(0);
            String[] headers = {"ID", "MaNV", "Tên nhân viên", "Tên đăng nhập", "Mật khẩu", "Trạng thái"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            int rowNum = 1;
            for (NhanVien nv : listNV) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(nv.getId());
                row.createCell(1).setCellValue(nv.getMa());
                row.createCell(2).setCellValue(nv.getTen());
                row.createCell(3).setCellValue(nv.getTenDN());
                row.createCell(4).setCellValue(nv.getMatKhau());
                row.createCell(5).setCellValue(nv.getTrangThai());
            }

            workbook.write(out);
        }
    }

    @PostMapping("import")
    public String importExcel(@RequestParam("file") MultipartFile file, Model model) {
        if (file.isEmpty()) {
            model.addAttribute("error", "Vui lòng chọn một tệp Excel.");
            return "nhan_vien/index";
        }

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }

                NhanVien nhanVien = new NhanVien();
                nhanVien.setMa(getCellValue(row.getCell(1)));
                nhanVien.setTen(getCellValue(row.getCell(2)));
                nhanVien.setTenDN(getCellValue(row.getCell(3)));
                nhanVien.setMatKhau(getCellValue(row.getCell(4)));
                nhanVien.setTrangThai(getCellIntegerValue(row.getCell(5)));

                nhanVienRepository.save(nhanVien);
            }
        } catch (IOException e) {
            model.addAttribute("error", "Đã xảy ra lỗi khi xử lý tệp: " + e.getMessage());
            return "nhan_vien/index";
        }

        return "redirect:/nhan-vien/index";
    }

    private String getCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf((int) cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    private Integer getCellIntegerValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
            case NUMERIC:
                return (int) cell.getNumericCellValue();
            case STRING:
                try {
                    return Integer.parseInt(cell.getStringCellValue());
                } catch (NumberFormatException e) {
                    return null;
                }
            default:
                return null;
        }
    }
}
