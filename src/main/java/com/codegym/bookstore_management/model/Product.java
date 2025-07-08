package com.codegym.bookstore_management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(max = 255, message = "Tên sản phẩm không được vượt quá 255 ký tự")
    private String name;

    @NotNull(message = "Giá sản phẩm không được để trống")
    @Positive(message = "Giá sản phẩm phải lớn hơn 0")
    private Double price;

    private String image;

    @Size(max = 500, message = "Mô tả ngắn không được vượt quá 500 ký tự")
    private String shortDesc;

    @Size(max = 2000, message = "Mô tả chi tiết không được vượt quá 2000 ký tự")
    private String detailDesc;

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 0, message = "Số lượng phải là số không âm")
    private Long quantity;

    @Min(value = 0, message = "Số lượng đã bán phải là số không âm")
    private Long sold;

    @NotBlank(message = "Tác giả không được để trống")
    @Size(max = 255, message = "Tên tác giả không được vượt quá 255 ký tự")
    private String author;

    @NotBlank(message = "Thể loại không được để trống")
    @Size(max = 100, message = "Thể loại không được vượt quá 100 ký tự")
    private String genre;

    @OneToMany(mappedBy = "product")
    private List<OrderDetail> orderDetails;
}
