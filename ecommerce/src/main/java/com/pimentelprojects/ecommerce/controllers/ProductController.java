package com.pimentelprojects.ecommerce.controllers;


import com.pimentelprojects.ecommerce.models.Product;
import com.pimentelprojects.ecommerce.models.UserEntity;
import com.pimentelprojects.ecommerce.security.SecurityUtil;
import com.pimentelprojects.ecommerce.services.ProductService;
import com.pimentelprojects.ecommerce.services.imp.UploadFileService;
import com.pimentelprojects.ecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;


@Controller
@RequestMapping("/products")
public class ProductController {


    private UserService userService;
    private ProductService productService;

    private UploadFileService fileService;

    @Autowired
    public ProductController(UserService userService,
                             ProductService productService,
                             UploadFileService fileService) {
        this.userService = userService;
        this.productService = productService;
        this.fileService = fileService;
    }

    @GetMapping("")
    public String show(Model model){
        model.addAttribute("products", productService.findAll());
        if(productService.findAll().isEmpty()){
            return "products/show_empty";
        }
        return "products/show";
    }

    @GetMapping("/create")
    public String create(){
        return "products/create";
    }

    @PostMapping("/create")
    public String save(Product product,
                       @RequestParam("img") MultipartFile file) throws IOException {

        UserEntity userEntity = userService.findByEmail(SecurityUtil.getSessionEmail());

        product.setUserEntity(userEntity);

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

        Product product = productService.getProduct(id).get();

        model.addAttribute("product", product);

        return "products/edit";
    }

    @PostMapping("/update")
    public String update(Product product,  @RequestParam("img") MultipartFile file) throws IOException {

        Product product1 = productService.getProduct(product.getId()).get();

        if(file.isEmpty()){

            product.setImage(product1.getImage());
        } else {

            if(!product1.getImage().equals("default.jpg")){
                fileService.delete(product1.getImage());
            }

            String nameImage = fileService.saveImage(file);
            product.setImage(nameImage);
        }

        product.setUserEntity(product1.getUserEntity());
        productService.update(product);
        return "redirect:/products";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Long id){

        Product product = productService.getProduct(id).get();

        // Eliminar cuando la imagen no sea la por defecto
        if(!product.getImage().equals("default.jpg")){
            fileService.delete(product.getImage());
        }

        productService.detele(id);
        return "redirect:/products";
    }
}
