package com.vlad.store.controller;

import javax.validation.Valid;

import com.vlad.store.model.Customer;
import com.vlad.store.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }


    @RequestMapping(value="/registration", method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        Customer customer = new Customer();
        // create a model attached to page
        modelAndView.addObject("customer", customer);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
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

    @RequestMapping(value="/index", method = RequestMethod.GET)
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerService.findByLogin(auth.getName());
        modelAndView.addObject("userName", "Welcome " + customer.getLogin() + ". You passed the assay.");
        modelAndView.addObject("adminMessage","Content Available Only for Customers with Admin Role");
        modelAndView.setViewName("index");
        return modelAndView;
    }


}
