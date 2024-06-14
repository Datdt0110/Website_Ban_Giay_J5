package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "MauSac")
@Component
public class MauSac {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @NotBlank(message = "Mã không được để trống")
    @Column(name = "Ma")
    private String ma;

    @NotBlank(message = "Tên không được để trống")
    @Column(name = "Ten")
    private String ten;

    @NotNull(message = "Trạng thái không được để trống")
    @Column(name = "TrangThai")
    private Integer trangThai;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "NgayTao")
    private Date ngayTao;


}
