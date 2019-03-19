package com.vlad.store.controller;

import com.vlad.store.model.ProductImage;
import com.vlad.store.model.dto.UploadedFileResponse;
import com.vlad.store.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Optional;

@Controller
@RequestMapping(value = "/product-images-uploads")
public class ProductImageUploadsController {

    private ProductImageService productImageService;

    @Autowired
    public ProductImageUploadsController(ProductImageService productImageService) {
        this.productImageService = productImageService;
    }

    @GetMapping(value = "")
    public ModelAndView setImagesUploadsView() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("product-images-uploads");
        return modelAndView;
    }

    @GetMapping(value = "/test")
    public @ResponseBody String uploadsTest() {
        return "Hi! Test is successful";
    }

    @PostMapping(value = "")
    public @ResponseBody
    ResponseEntity<UploadedFileResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        ProductImage productImage = productImageService.saveFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/download/")
                .path(productImage.getId().toString())
                .toUriString();

        UploadedFileResponse response = UploadedFileResponse.builder()
                .fileDownloadUri(fileDownloadUri)
                .fileName(productImage.getFileName())
                .fileType(productImage.getFileType())
                .size(file.getSize())
                .build();

        return ResponseEntity.created(URI.create(fileDownloadUri)).body(response);
    }

    @GetMapping(value = "/download/{resourceId}")
    public @ResponseBody
    ResponseEntity<Resource> downloadFile(@PathVariable(value = "resourceId") Long resourceId,
                                          @Autowired HttpServletRequest request)
            throws NoHandlerFoundException {
        ProductImage resource = productImageService.findById(resourceId)
                .orElseThrow(() -> new NoHandlerFoundException("get", request.getRequestURL().toString(), HttpHeaders.EMPTY));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(resource.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = "
                        + resource.getFileName() + "\"")
                .body(new ByteArrayResource(resource.getData()));
    }
}