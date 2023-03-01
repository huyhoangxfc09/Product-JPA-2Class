package com.example.product.controller;

import com.example.product.model.Category;
import com.example.product.model.Product;
import com.example.product.service.core.ICategoryService;
import com.example.product.service.core.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private IProductService iProductService;
    @Autowired
    private ICategoryService categoryService;
    @ModelAttribute("categories")
    private List<Category> getCategories() {
        return categoryService.findAll();
    }
    @GetMapping("")
    public String listProduct(Model model,@RequestParam(defaultValue = "0")int page,
                                  @RequestParam(defaultValue = "5")int size){
        Page<Product> products = iProductService.getAllProduct(page,size);
        model.addAttribute("product", products.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("category", new Category());
        model.addAttribute("products", products);
        return "product/list";
    }
    @GetMapping("create")
    private ModelAndView createForm(){
        ModelAndView modelAndView = new ModelAndView("product/form");
        modelAndView.addObject("product", new Product());
        modelAndView.addObject("create","create");
        return modelAndView;
    }
//    @PostMapping("/create")
//    private String createProduct(@Valid @ModelAttribute Product product,RedirectAttributes attributes){
//        iProductService.save(product);
//        attributes.addFlashAttribute("message", "Tạo mới thành công");
//        return "redirect:/products";
//    }

//    @PostMapping("/create")
//    private String createProduct(@Valid @ModelAttribute Product product,
//                                 RedirectAttributes attributes,
//                                 BindingResult bindingResult){
//        if (bindingResult.hasErrors()){
//            attributes.addFlashAttribute("product",product);
//            attributes.addFlashAttribute("create","create");
//            return "product/form";
//        }
//        iProductService.save(product);
//        attributes.addFlashAttribute("message", "Tạo mới thành công");
//        return "redirect:/products";
//    }
    @PostMapping("/create")
    private ModelAndView createProduct(@Valid @ModelAttribute Product product,
                                 BindingResult bindingResult){
        if (bindingResult.hasErrors()){
          return new ModelAndView("product/form");
        }
        ModelAndView modelAndView = new ModelAndView("redirect:/products");
        iProductService.save(product);
        modelAndView.addObject("message", "Tạo mới thành công");
        return modelAndView;
    }
    @GetMapping("/update/{id}")
    private ModelAndView updateForm(@PathVariable("id")Long id){
        ModelAndView modelAndView = new ModelAndView("product/form");
        modelAndView.addObject("update","update");
        modelAndView.addObject("product",iProductService.findById(id));
        return modelAndView;
    }
    @PostMapping("/update/{id}")
    private String updateProduct(@ModelAttribute Product product, RedirectAttributes attributes){
        iProductService.save(product);
        attributes.addFlashAttribute("message","Update thành công");
        return "redirect:/products";
    }
    @GetMapping("/delete/{id}")
    private String deleteProduct(@PathVariable("id")Long id){
        iProductService.remove(id);
        return "redirect:/products";
    }
    @GetMapping("/detail/{id}")
    private ModelAndView detailProduct(@PathVariable("id")Long id){
        ModelAndView modelAndView = new ModelAndView("product/detail");
        Product product = iProductService.findById(id);
        modelAndView.addObject("product",product);
        return modelAndView;
    }
//    @PostMapping("/search")
//    private ModelAndView searchByName(@RequestParam("search")String name){
//        List<Product> products = iProductService.searchByName(name);
//        ModelAndView modelAndView = new ModelAndView("product/search");
//        modelAndView.addObject("product",products);
//        return modelAndView;
//    }
    @PostMapping("/search")
    public String listProductByName(Model model,@RequestParam(defaultValue = "0")int page,
                              @RequestParam(defaultValue = "5")int size,@RequestParam("search")String name, HttpServletRequest request){
        HttpSession session = request.getSession();
        session.setAttribute("search",name);
        Page<Product> products = iProductService.getAllProductByName(name,page,size);
        model.addAttribute("product", products.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("products", products);
        return "product/search";
    }
    @GetMapping ("/search")
    public String listProductByNamePag(Model model,@RequestParam(defaultValue = "0")int page,
                                    @RequestParam(defaultValue = "5")int size, HttpServletRequest request){
        HttpSession session = request.getSession();
        String name = (String) session.getAttribute("search");
        Page<Product> products = iProductService.getAllProductByName(name,page,size);
        model.addAttribute("product", products.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("products", products);
        return "product/search";
    }
//    @GetMapping("")
//    public String listSearch(Model model,@RequestParam(defaultValue = "0")int page,
//                              @RequestParam(defaultValue = "5")int size){
//        model.addAttribute("product", products.getContent());
//        model.addAttribute("currentPage", page);
//        model.addAttribute("totalPages", products.getTotalPages());
//        model.addAttribute("products", products);
//        return "product/list";
//    }
    @PostMapping("/category")
    private String  searchByCategory(@ModelAttribute("category") Category category, Model model){
        List<Product> products = iProductService.searchByCategory(category.getId());
        model.addAttribute("product", products);
        return "product/search";

    }
}
