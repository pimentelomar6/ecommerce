package com.pimentelprojects.ecommerce.controllers;


import com.pimentelprojects.ecommerce.models.Product;
import com.pimentelprojects.ecommerce.models.User;
import com.pimentelprojects.ecommerce.services.ProductService;
import com.pimentelprojects.ecommerce.services.UploadFileService;
import com.pimentelprojects.ecommerce.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    private final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private UploadFileService fileService;


    @GetMapping("")
    public String show(Model model){
        model.addAttribute("products", productService.findAll());
        return "administrador/products/show";
    }

    @GetMapping("/create")
    public String create(){
        return "administrador/products/create";
    }

    @PostMapping("/create")
    public String save(Product product,
                       @RequestParam("img") MultipartFile file,
                       HttpSession httpSession) throws IOException {
        logger.info("Este es un objeto producto {}", product);
        User user = userService.findById(Long.parseLong(httpSession.getAttribute("idusuario").toString())).get();

        product.setUser(user);

        // Imagen
        if(product.getId()==null){
            String nameImage = fileService.saveImage(file);
            product.setImage(nameImage);
        }



        productService.saveProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model){
        Product product = new Product();
        Optional<Product> optionalProduct = productService.getProduct(id);
        product = optionalProduct.get();

        logger.info("producto buscado {}", product);

        model.addAttribute("product", product);

        return "administrador/products/edit";
    }

    @PostMapping("/update")
    public String update(Product product,  @RequestParam("img") MultipartFile file) throws IOException {

        Product product1 = new Product();
        product1 = productService.getProduct(product.getId()).get();

        if(file.isEmpty()){

            product.setImage(product1.getImage());
        } else {

            if(!product1.getImage().equals("default.jpg")){
                fileService.delete(product1.getImage());
            }



            String nameImage = fileService.saveImage(file);
            product.setImage(nameImage);
        }

        product.setUser(product1.getUser());
        productService.update(product);
        return "redirect:/products";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Long id){

        Product product = new Product();
        product = productService.getProduct(id).get();

        // Eliminar cuando la imagen no sea la por defecto
        if(!product.getImage().equals("default.jpg")){
            fileService.delete(product.getImage());
        }


        productService.detele(id);
        return "redirect:/products";
    }
}
