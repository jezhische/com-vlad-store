package com.vlad.store.controller;

import com.sun.org.apache.regexp.internal.RE;
import com.vlad.store.model.ProductImage;
import com.vlad.store.model.dto.ProductImageDTO;
import com.vlad.store.model.dto.ProductJoinProductImageDTO;
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
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        return "Hi! Test response from the server is successful";
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
        ProductImage resource = productImageService.findById(resourceId) // returns Optional<ProductImage>
                .orElseThrow(() -> new NoHandlerFoundException("get", request.getRequestURL().toString(), HttpHeaders.EMPTY)); // returns ProductImage
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(resource.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = "
                        + resource.getFileName() + "\"")
                .body(new ByteArrayResource(resource.getData()));
    }

    @GetMapping(value = "/product-images/{resourceId}")
    public @ResponseBody
    ResponseEntity<ProductImage> getImageData(@PathVariable(value = "resourceId") Long resourceId,
                                                 @Autowired HttpServletRequest request)
            throws NoHandlerFoundException {
        ProductImage resource = productImageService.findById(resourceId) // returns Optional<ProductImage>
                .orElseThrow(() -> new NoHandlerFoundException("get", request.getRequestURL().toString(), HttpHeaders.EMPTY)); // returns ProductImage
        return ResponseEntity.ok()
                .body(resource);
    }

    ResponseEntity<ProductImage> getImagePreviousData(@PathVariable(value = "resourceId") Long resourceId,
                                              @Autowired HttpServletRequest request) {
        return null;
    }

    @GetMapping(value = "/product-join-product-images")
    ResponseEntity<ProductImage> getProductJoinProductImageDTO(@RequestParam(name = "name-part") String namePart,
                                                      @Autowired HttpServletRequest request) {
        List<ProductJoinProductImageDTO> pJPIList = productImageService.selectProductJoinProductImageDTO(namePart);
        // поскольку я пока не пользуюсь селектом, то когда я ввожу в поисковое поле часть имени,я получаю
        // несколько продуктов, у каждого из которых может быть несколько картинок. То есть, я получаю
        // несколько productJoinProductImageDTO с одним продуктом и разными картинками, несколько с другим
        // и т.д. Поэтому для начала отсортируем по product.id
        // lambda to get Comparator instance: Comparator.comparing(productJoinProductImageDTO -> productJoinProductImageDTO.getProductId())
        pJPIList = pJPIList.stream()
                .sorted(Comparator.comparing(ProductJoinProductImageDTO::getProductId))
                .collect(Collectors.toList());
        return null;
    }


//    @GetMapping(value = "/product-images/name", params = {"name"})
//    public @ResponseBody
//    ResponseEntity<List<ProductImage>> getImagesByName(@RequestParam(value = "name") String imgName,
//                                         @Autowired HttpServletRequest request)
//            throws NoHandlerFoundException {
//        ProductImage resource = productImageService.findById(resourceId) // returns Optional<ProductImage>
//                .orElseThrow(() -> new NoHandlerFoundException("get", request.getRequestURL().toString(), HttpHeaders.EMPTY)); // returns ProductImage
//        return ResponseEntity.ok()
////                .contentType(MediaType.parseMediaType(resource.getFileType()))
////                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = "
////                        + resource.getFileName() + "\"")
//                .body(resource);
//    }
}

