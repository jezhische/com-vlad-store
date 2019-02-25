package com.vlad.store.controller;

import com.vlad.store.model.Customer;
import com.vlad.store.service.CustomerService;
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

    private final CustomerService customerService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public HackerController(CustomerService customerService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.customerService = customerService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping(value = "/unlock")
    public ModelAndView unlock() {
        ModelAndView modelAndView = new ModelAndView();
        String currentCustomerName = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer byLogin = customerService.findByLogin(currentCustomerName);
        byLogin.setEnabled(true);
        customerService.update(byLogin);
        modelAndView.setViewName("login");
        return modelAndView;
    }

}
