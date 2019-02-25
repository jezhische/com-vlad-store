package com.vlad.store.controller;

import javax.validation.Valid;

import com.vlad.store.model.Customer;
import com.vlad.store.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    private final CustomerService customerService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public LoginController(CustomerService customerService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.customerService = customerService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @GetMapping(value={"/", "/login"})
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }


    @GetMapping(value="/registration")
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        Customer customer = new Customer();
        // create a model attached to page
        modelAndView.addObject("customer", customer);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @PostMapping(value = "/registration")
    public ModelAndView createNewUser(@Valid Customer customer, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        Customer saved = customerService.findByLogin(customer.getLogin());
        if (saved != null) {
            bindingResult
                    .rejectValue("login", "error.customer",
                            "There is already a customer registered with the login provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            customerService.save(customer);
            modelAndView.addObject("successMessage", "Customer has been registered successfully");
            modelAndView.addObject("customer", new Customer());
            modelAndView.setViewName("registration");

        }
        return modelAndView;
    }

    @GetMapping(value="/index")
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        // NB the way to get access to user data
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

//        UserDetails userDetails = null;
//        if (!(auth instanceof AnonymousAuthenticationToken)) {
//            userDetails = ((UserDetails) auth.getPrincipal());
//        }
//        System.out.println("*************** USERNAME: " + userDetails.getUsername());
//        System.out.println("*************** PASSWORD: " + userDetails.getPassword()); // = null since protected
//        System.out.println("*************** USERDETAILS: " + userDetails);
//        System.out.println("*************** CREDENTIALS: " + auth.getCredentials().toString());

        Customer customer = customerService.findByLogin(auth.getName());
        modelAndView.addObject("customer", customer);
        modelAndView.setViewName("index");
        return modelAndView;
    }

//    =============================================================================================================

    // It's very bad practise to send get request with the password parameter.
    // However this is the "hacker attack" imitation and hackers do not care security ))
    @GetMapping(value = "/index", params = "pswd")
    @ResponseBody
    public boolean hackPassword(@RequestParam(value = "pswd") String password) {
        Customer byLogin = customerService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        boolean matches = bCryptPasswordEncoder.matches(password, byLogin.getPassword());
        if (matches) {
            byLogin.setEnabled(false);
            customerService.update(byLogin);
        }
        return matches;
    }
}
