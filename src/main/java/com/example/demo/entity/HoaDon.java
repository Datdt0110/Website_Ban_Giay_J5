package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "HoaDon")
@Component
public class HoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "IdNhanVien", referencedColumnName = "id")
    private NhanVien nhanVien;

    @ManyToOne
    @JoinColumn(name = "IdKhachHang", referencedColumnName = "id")
    private KhachHang khachHang;

    @NotNull(message = "Ngày mua hàng không được để trống")

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "NgayMuaHang")
    private LocalDate ngayMuaHang;

    @Column(name = "TrangThai")
    private Integer trangThai;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "NgayTao")
    private Date ngayTao;

    public String hienThiTT() {
        if (trangThai == 0) {
            return "Đã thanh toán";
        } else if (trangThai == 1) {
            return "Chưa thanh toán";
        }
        return null;
    }
}
