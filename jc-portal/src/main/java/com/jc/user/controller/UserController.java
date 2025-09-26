package com.jc.user.controller;


import com.jc.user.entity.User;
import com.jc.user.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Api(tags = "用户管理")
@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    @ApiOperation(value = "查询所有用户", notes = "查询所有用户信息")
    @GetMapping(path = "/list")
    public ModelAndView list(Model model) {
        model.addAttribute("userList", userService.findAll());
        return new ModelAndView("user/list", "userModel", model);
    }

    @ApiOperation(value = "保存用户", notes = "保存用户并返回到用户列表页面")
    @PostMapping(path = "/save")
    public ModelAndView save(User user, Errors errors, Model model) {
        if (errors.hasErrors()){
            model.addAttribute("user",user);
            if (errors.getFieldError("name")!=null) {
                model.addAttribute("nameError", errors.getFieldError("name").getDefaultMessage());
            }
            if (errors.getFieldError("email")!=null) {
                model.addAttribute("emailError", errors.getFieldError("email").getDefaultMessage());
            }
            return new ModelAndView("register","userModel",model);
        }
        userService.save(user);
        return new ModelAndView("redirect:/user/list");//重定向到list页面
    }

    @ApiOperation(value = "删除用户", notes = "删除用户")
    @GetMapping(path = "/del")
    public ModelAndView del(@RequestParam(name = "id") Long id) {
        userService.deleteById(id);
        return new ModelAndView("redirect:/user/list");
    }

    @ApiOperation(value = "查询用户", notes = "查询用户")
    @PostMapping("/search")
    public ModelAndView search(@ModelAttribute User user, Model model) {
        model.addAttribute("userList", userService.findByName(user.getName()));
        return new ModelAndView("list", "userModel", model);
    }

    @ApiOperation(value = "跳转用户表单页面", notes = "跳转用户表单页面")
    @GetMapping("/form")
    public ModelAndView createForm(Model model) {
        model.addAttribute("user",new User());
        return new ModelAndView("user/form","userModel", model);
    }

}
