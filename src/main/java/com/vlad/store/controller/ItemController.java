package com.vlad.store.controller;

import com.vlad.store.blobTest.BlobTestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/uploads")
public class ItemController {
    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

    private BlobTestService service;

    public ItemController(BlobTestService service) {
        this.service = service;
    }


    @GetMapping(value = "/test")
    public String testit() {
        return "test successfull";
    }

}
