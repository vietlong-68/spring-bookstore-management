package com.codegym.bookstore_management.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.codegym.bookstore_management.model.Product;
import com.codegym.bookstore_management.service.ProductService;

@Controller
@RequestMapping("/admin/products")
public class ProductController {

    private final ProductService productService;
    private final com.codegym.bookstore_management.service.UploadService uploadService;

    public ProductController(ProductService productService,
            com.codegym.bookstore_management.service.UploadService uploadService) {
        this.productService = productService;
        this.uploadService = uploadService;
    }

    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productService.findAll());
        return "admin/product/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        return "admin/product/form";
    }

    @PostMapping("/save")
    public String saveProduct(@ModelAttribute("product") Product product,
            @RequestParam(value = "productImage", required = false) MultipartFile productImage) {

        if (productImage != null && !productImage.isEmpty()) {
            String productImageWebPath = uploadService.uploadFile(productImage, "productImage");
            if (productImageWebPath != null) {
                product.setImage(productImageWebPath);
            }
        }

        productService.save(product);

        return "redirect:/admin/products";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.findById(id));
        return "admin/product/form";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute("product") Product product) {
        productService.save(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
        return "redirect:/admin/products";
    }

    @GetMapping("/detail/{id}")
    public String viewProductDetail(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.findById(id));
        return "admin/product/detail";
    }
}