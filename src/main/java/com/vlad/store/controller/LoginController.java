package com.vlad.store.controller;

import com.vlad.store.model.Customer;
import com.vlad.store.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class LoginController {

    private final CustomerService customerService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public LoginController(CustomerService customerService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.customerService = customerService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping(value = "/free-access")
    public ModelAndView letInAnonimous() {
        ModelAndView modelAndView = new ModelAndView();
        Customer guest = Customer.builder()
                .login("guest")
                .password("111")
                .id(1L)
                .build();
        modelAndView.addObject(guest);
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping(value={"/login"})
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
    public ModelAndView createNewUser(@Valid @ModelAttribute(value = "customer") Customer customer, BindingResult bindingResult) {
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
        Customer customer = customerService.findByLogin(auth.getName());
        modelAndView.addObject("customer", customer);
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @RequestMapping(value = "/access-denied")
    public ModelAndView setAccessDeniedView() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("access-denied");
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
