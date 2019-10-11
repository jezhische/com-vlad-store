package com.vlad.store.controller;

import com.vlad.store.model.ProductImage;
import com.vlad.store.model.dto.ProductJoinProductImageDTO;
import com.vlad.store.model.dto.UploadedFileResponse;
import com.vlad.store.model.util.ControllerUtils;
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
import java.io.IOException;
import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/product-images-uploads")
public class ProductImageUploadsController {

    private static final int PREVIEW_IMAGE_HEIGHT = 200;
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
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = "
                        + resource.getFileName() + "\"")
                .body(resource);
    }



//    ResponseEntity<ProductImage> getImagePreviewData(@PathVariable(value = "resourceId") Long resourceId,
//                                              @Autowired HttpServletRequest request) {
//        return null;
//    }

    @GetMapping(value = "/product-join-product-images")
    ResponseEntity<List<ProductJoinProductImageDTO>> getProductJoinProductImageDTO(@RequestParam(name = "name-part") String namePart,
                                                      @Autowired HttpServletRequest request) {
        return ResponseEntity.ok(
// get list of ProductJoinProductImageDTO instances
                productImageService.selectProductJoinProductImageDTO(namePart)
                        .stream()
// as the result from the search input may be only a part of the product name, the request result gives me
// a list of productJoinProductImageDTO, and some items from this list could contain the same product
// with different image ids, while other ones could contain other product with several image ids.
// So first I need to sort result list by products
                        .sorted(Comparator.comparing(ProductJoinProductImageDTO::getProductId))
// then add for each item byte[] productImageData with addProductImageData(). As that method throws exception, I
// need to create wrapper for map(Function f) argument, consider to name it ThrowingFunction.
// NB: ThrowingFunction.applyUnchecked() takes to parameters
// new ThrowingFunction<ProductJoinProductImageDTO, ProductJoinProductImageDTO, Throwable>() {...}
// So lambda item -> addProductImageData(item, request) creates new ThrowingFunction instance with its
// R apply(T t) throws E method implemented as addProductImageData() method, and in turn applyUnchecked() method
// creates new Function instance.
                        .map(ThrowingFunction.applyUnchecked(item -> addProductImageData(item, request)))
                        .collect(Collectors.toList())
        );
    }

// ====================================================================================================== U T I L

    /**
     * get {@code ProductImage} instance by id from {@code ProductJoinProductImageDTO item},
     * then resize it for preview and set to the item
     * @param item {@code ProductJoinProductImageDTO} instance that handled here
     * @param request
     * @return set {@code fileType} and {@code productImageData} fields to {@code ProductJoinProductImageDTO item} and return it
     * @throws NoHandlerFoundException
     * @throws IOException
     */
    private ProductJoinProductImageDTO addProductImageData(ProductJoinProductImageDTO item, HttpServletRequest request)
            throws NoHandlerFoundException, IOException {
        ProductImage productImage = productImageService.findById(item.getProductImageId())
                .orElseThrow(() -> new NoHandlerFoundException("get", request.getRequestURL().toString(), HttpHeaders.EMPTY));
// get fileType and set it to ProductJoinProductImageDTO instance
        String fileType = productImage.getFileType();
        item.setFileType(fileType);
// get image data from db, resize image and set scaled image data to ProductJoinProductImageDTO instance
        item.setProductImageData(
                ControllerUtils.scaleImageFromByteArray(productImage.getData(), fileType, PREVIEW_IMAGE_HEIGHT)
        );

        return item;
    }

    /**
     * A wrapper to create {@link Function} instance with exception handling.
     * Represents a function that accepts one argument and produces a result, so
     * this is a functional interface whose functional method is {@link #apply(Object)} throws {@link Throwable}.
     * @param <T> the type of the input to the function
     * @param <R> the type of the result of the function
     * @param <E> the type of the exception handled in {@link #applyUnchecked(ThrowingFunction)}
     * @see <a href=https://dzone.com/articles/how-to-handle-checked-exception-in-lambda-expressi>
     *     How to Handle Checked Exceptions With Lambda Expression</a>
     * @see "task.txt"
     */
    @FunctionalInterface
    interface ThrowingFunction<T, R, E extends Throwable> {
        R apply(T t) throws E;

        /**
         * create a {@link Function} instance and handle checked Exception by throwing an unchecked one.
         * @param f
         * @param <T>
         * @param <R>
         * @param <E>
         * @return new Function instance with its R apply(T t) method implemented as R apply(T t) throws E
         */
        static <T, R, E extends Throwable> Function<T, R> applyUnchecked(ThrowingFunction<T, R, E> f) {
// create Function instance by implementation of its R apply(T t) method.
// NB: this equals a following expression: return new Function<T, R>(){@Override  public R apply(T t){...return f.apply(t);...}}
            return t -> {
                try {
                    return f.apply(t);
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            };
        }
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

