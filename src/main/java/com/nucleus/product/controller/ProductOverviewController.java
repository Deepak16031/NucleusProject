package com.nucleus.product.controller;

import com.nucleus.login.logindetails.LoginDetailsImpl;
import com.nucleus.product.dao.ProductDAO;
import com.nucleus.product.model.Product;
import com.nucleus.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductOverviewController {

    @Autowired
    ProductService productService;

    @PreAuthorize("hasRole('ROLE_CHECKER') or hasRole('ROLE_MAKER')")
    @GetMapping(value = {"/product" })
    public ModelAndView productOverview() {
        ModelAndView modelAndView = new ModelAndView("views/product/productOverview");
        LoginDetailsImpl details = new LoginDetailsImpl();
        List<Product> productList = productService.getProductList();
        modelAndView.addObject("products", productList);
        return modelAndView;
    }

    @PreAuthorize("hasRole('ROLE_CHECKER')")
    @GetMapping(value = "/product/{productId}")
    public ModelAndView productViewById(@PathVariable(value = "productId") String productId){
        Product product = productService.getProductById(productId);
        ModelAndView modelAndView = new ModelAndView("views/product/newProductCreationChecker");
        modelAndView.addObject("product", product);
        return modelAndView;
    }

    @PreAuthorize("hasRole('ROLE_CHECKER')")
    @PostMapping(value = "/product/{productId}/update")
    public ModelAndView updateProductStatus(@PathVariable(value = "productId") String productId, @RequestParam("action") String action){
        Product product = productService.getProductById(productId);
        LoginDetailsImpl details = new LoginDetailsImpl();

        product.setAuthorizedBy(details.getUserName());
        product.setAuthorizedDate(LocalDate.now());
        product.setStatus(action);

        product = productService.updateProduct(product);
        if(product!=null){
            ModelAndView modelAndView = new ModelAndView("views/product/productSuccess");
            modelAndView.addObject("messageHeader", "Product was successfully " + action );
            modelAndView.addObject("messageBody", "You successfully changed the product status." );
            modelAndView.addObject("productCode", productId);
            return modelAndView;
        }
        ModelAndView modelAndView = new ModelAndView("views/product/productError");
        modelAndView.addObject("messageHeader", "Product status could not be updated" );
        modelAndView.addObject("messageBody", "Product status update failed. Please try again" );
        modelAndView.addObject("productCode", productId);
        return modelAndView;

    }

    @PreAuthorize("hasRole('ROLE_MAKER')")
    @GetMapping(value = "/product/{productId}/delete")
    public ModelAndView deleteProduct(@PathVariable(value = "productId") String productId){
        Boolean success = productService.deleteProduct(productId);
        if(success){
            ModelAndView modelAndView = new ModelAndView("views/product/productSuccess");
            modelAndView.addObject("messageHeader", "Product was deleted" );
            modelAndView.addObject("messageBody", "You successfully deleted a product" );
            modelAndView.addObject("productCode", productId);
            return modelAndView;
        }
        ModelAndView modelAndView = new ModelAndView("views/product/productError");
        modelAndView.addObject("messageHeader", "Product could not be deleted" );
        modelAndView.addObject("messageBody", "Product deletion failed. Please try again" );
        modelAndView.addObject("productCode", productId);
        return modelAndView;
    }
}
