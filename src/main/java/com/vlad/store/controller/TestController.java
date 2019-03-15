package com.vlad.store.controller;

import com.vlad.store.model.test.TestModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {

 @GetMapping("/test")
    public ModelAndView getTestPage() {
     ModelAndView modelAndView = new ModelAndView();
     TestModel model = new TestModel();
     modelAndView.addObject("model", model);
     modelAndView.setViewName("test");
     return modelAndView;
 }

//    @PostMapping("test")
//    public @ResponseBody ResponseEntity<TestModel> getTestModelJson(@RequestBody TestModel model) {
//        return ResponseEntity.ok(model);
//    }

    @PostMapping("test")
    public String getTestModel(@ModelAttribute String name) {
     return name;
    }
}
