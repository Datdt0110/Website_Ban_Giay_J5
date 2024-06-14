package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
@Entity
@Table(name = "KhachHang")
@Component
public class KhachHang {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @NotBlank(message = "Mã không được để trống")
    @Column(name = "MaKH")
    private String ma;

    @NotBlank(message = "Tên không được để trống")
    @Column(name = "Ten")
    private String ten;

    @NotBlank(message = "SDT không được để trống")
    @Pattern(regexp = "^(\\+?84|0)(\\d{9,10})$", message = "SDT không đúng định dạng")
    @Column(name = "SDT")
    private String sdt;

    @NotNull(message = "Trạng thái không được để trống")
    @Column(name = "TrangThai")
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
