package com.vlad.store.controller;

import com.vlad.store.model.Customer;
import com.vlad.store.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HackerController {

    final LoginController loginController;

    private final CustomerService customerService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public HackerController(CustomerService customerService, BCryptPasswordEncoder bCryptPasswordEncoder, LoginController loginController) {
        this.customerService = customerService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.loginController = loginController;
    }

    @PostMapping(value = "/unlock")
    public ModelAndView unlock() {
        ModelAndView modelAndView = new ModelAndView();
        String currentCustomerName = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer byLogin = customerService.findByLogin(currentCustomerName);
        byLogin.setEnabled(true);
        customerService.update(byLogin);
//        SecurityContextHolder.getContext().getAuthentication().
        modelAndView.setViewName("redirect:/login");
        return modelAndView;
    }

}
