package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
@Entity // Đánh dấu đây là một Entity
@Table(name = "NhanVien") // Đặt tên bảng trong cơ sở dữ liệu
public class NhanVien {
    @Id // Đánh dấu thuộc tính id là primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tự động tăng giá trị id
    private Integer id;

    @NotBlank(message = "Mã không được để trống")
    @Column(name = "MaNV") // Tên cột trong bảng
    private String ma;

    @NotBlank(message = "Tên nhân viên không được để trống")
    @Column(name = "Ten") // Tên cột trong bảng
    private String ten;



    @NotBlank(message = "Tên đăng nhập không được để trống")
    @Column(name = "TenDangNhap") // Tên cột trong bảng
    private String tenDN;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Column(name = "MatKhau") // Tên cột trong bảng
    private String matKhau;

    @Column(name = "VaiTro") // Tên cột trong bảng
    private String vaiTro;

    @Column(name = "trangThai") // Tên cột trong bảng
    private Integer trangThai;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "NgayTao")
    private Date ngayTao;


    public String hienThiTT() {
        if (trangThai == 0) {
            return "Hoạt động";
        } else if (trangThai == 1) {
            return "Ngừng hoạt động";
        }
        return null;
    }
}
