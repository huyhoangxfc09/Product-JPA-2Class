package com.example.product.controller;

import com.example.product.model.Category;
import com.example.product.service.core.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private ICategoryService iCategoryService;

    @GetMapping("")
    public String listCategoryPag(Model model,@RequestParam(defaultValue = "0")int page,
                               @RequestParam(defaultValue = "5")int size){
        Page<Category> categories = iCategoryService.getAllCategory(page,size);
        model.addAttribute("category", categories.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("categories", categories);
        model.addAttribute("totalPages", categories.getTotalPages());
        return "category/list";
    }
//    @GetMapping("")
//    public String listCategory(Model model,@RequestParam(defaultValue = "0")int page,
//                               @RequestParam(defaultValue = "5")int size){
//        Page<Category> categories = iCategoryService.getAllCategory(page,size);
//        model.addAttribute("category", categories.getContent());
//        model.addAttribute("currentPage", page);
//        model.addAttribute("categories", categories);
//        model.addAttribute("totalPages", categories.getTotalPages());
//        return "category/list";
//    }
//    @GetMapping("")
//    public String listCategory(Model model){
//        model.addAttribute("category", iCategoryService.findAll());
//        return "category/list";
//    }
    @GetMapping("/create")
    private ModelAndView createForm(){
        ModelAndView modelAndView = new ModelAndView("category/form");
        modelAndView.addObject("category", new Category());
        modelAndView.addObject("create","create");
        return modelAndView;
    }
//    @PostMapping("/create")
//    private String createCategory(@ModelAttribute Category category, RedirectAttributes attributes){
//        iCategoryService.save(category);
//        attributes.addFlashAttribute("message","Tạo mới thành công");
//        return "redirect:/categories";
//    }
        @PostMapping("/create")
        private ModelAndView createCategory(@Validated @ModelAttribute Category category,
                                            BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView("category/list");
             if (bindingResult.hasErrors()){
                 return new ModelAndView("category/form");
             }else {

             }
             iCategoryService.save(category);
             modelAndView.addObject("message","Tạo mới thành công");
             return modelAndView;
    }

    @GetMapping("/update/{id}")
    private ModelAndView updateForm(@PathVariable("id")Long id){
        ModelAndView modelAndView = new ModelAndView("category/form");
        modelAndView.addObject("update","update");
        modelAndView.addObject("category",iCategoryService.findById(id));
        return modelAndView;
    }
    @PostMapping("/update/{id}")
    private String updateCategory(@ModelAttribute Category category, RedirectAttributes attributes){
        iCategoryService.save(category);
        attributes.addFlashAttribute("message","Update thành công");
        return "redirect:/categories";
    }
    @GetMapping("/delete/{id}")
    private String deleteCategory(@PathVariable("id")Long id){
        iCategoryService.remove(id);
        return "redirect:/categories";
    }
}
