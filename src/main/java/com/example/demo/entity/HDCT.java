package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "HoaDonChiTiet")
@Component
public class HDCT {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")

    private Integer id;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "IdHoaDon", referencedColumnName = "id")
    private HoaDon hoaDon;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "IdSPCT", referencedColumnName = "id")
    private SPCT spct;
    @NotNull(message = "Số lượng không được trống")
    @Positive(message = "Số lượng phải là số dương")
    @Digits(integer = 3, fraction = 2, message = "Số lượng không được quá lớn")
    @Column(name = "SoLuong")
    private Integer soLuong;
    @NotNull(message = "Đơn giá không được trống")
    @DecimalMin(value = "10", message = "Đơn giá phải lớn hơn 10")

    @Column(name = "DonGia", precision = 10, scale = 2)
    private BigDecimal donGia;

    @Column(name = "TongTien")
    private BigDecimal tongTien;
    @NotNull(message = "Trạng thái không được trống")
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
