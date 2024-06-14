package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "SPChiTiet")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class SPCT {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "MaSPCT") // Xác định tên cột trong bảng
    @NotBlank(message = "Mã không được để trống")
    private String ma;

    @ManyToOne
    @JoinColumn(name = "idKichThuoc", referencedColumnName = "id")
    private KichThuoc kichThuoc;

    @ManyToOne
    @JoinColumn(name = "idMauSac", referencedColumnName = "id")
    private MauSac mauSac;

    @ManyToOne
    @JoinColumn(name = "idSanPham", referencedColumnName = "id")
    private SanPham sanPham;

    @NotNull(message = "Số lượng không được trống")
    @Positive(message = "Số lượng phải là số dương")
    @Digits(integer = 3, fraction = 2, message = "Số lượng phải đúng định dạng")
    @Column(name = "SoLuong")
    private Integer soLuong;

    @NotNull(message = "Đơn giá không được trống")
    @DecimalMin(value = "10", message = "Đơn giá phải lớn hơn 10")

    @Column(name = "DonGia", precision = 10, scale = 2)
    private BigDecimal donGia;

    @Column(name = "TrangThai") // Xác định tên cột trong bảng
    @NotNull(message = "Trạng thái không được để trống")
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
