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
@Component
@Entity // Đánh dấu đây là một Entity
@Table(name = "SanPham") // Đặt tên bảng trong cơ sở dữ liệu
public class SanPham {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "ID")
        private Integer id;
        // Các thuộc tính khác của sản phẩm
        @NotBlank(message = "Mã không được để trống")
        @Column(name="Ma")
        private String ma;
        @NotBlank(message = "Tên không được để trống")
        @Column(name="Ten")
        private String ten;
        @NotNull(message = "Trạng thái không được trống")
        @Column(name="TrangThai")
        private Integer trangThai;

        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "NgayTao")
        private Date ngayTao;

}
