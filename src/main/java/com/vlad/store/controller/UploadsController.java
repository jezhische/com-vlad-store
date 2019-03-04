package com.vlad.store.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UploadsController {

    @RequestMapping(value = "/uploads")
    public ModelAndView setUploadsView() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("uploads");
        return modelAndView;
    }
}
