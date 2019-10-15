package com.vlad.store.service;

import com.vlad.store.model.Product;
import com.vlad.store.model.ProductImage;
import com.vlad.store.model.dto.ProductJoinProductImageDTO;
import com.vlad.store.repository.ProductImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductImageRepository repository;

    @Autowired
    public ProductImageServiceImpl(ProductImageRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProductImage saveFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileType = file.getContentType();
// TODO: это костыль, поскольку иначе тип файла пропишется в бд как, например, image/jpeg. В этом случае, когда я
//  пользуюсь методом ControllerUtils.scaleImageFromByteArray() для создания превью, стрый класс ImageIO не может
//  правильно определить тип файла
        if(fileType != null && fileType.substring(0, 6).matches("image/")) fileType = fileType.substring(6);

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new RuntimeException("Sorry! Filename contains invalid path sequence " + fileName);
            }
//            System.out.println("********************************************************************* + " +
//                    "ProductImage saveFile(MultipartFile file): file.getContentType() = " + file.getContentType());
            ProductImage image = new ProductImage(fileName, fileType /*file.getContentType()*/, file.getBytes());
            return repository.saveAndFlush(image);
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    @Override
    public ProductImage saveFile(ProductImage productImage) {
        return repository.saveAndFlush(productImage);
    }

    @Override
    public ProductImage updateFile(ProductImage stored, MultipartFile update) {
        String newFileName = StringUtils.cleanPath(update.getOriginalFilename());

        try {
            if(newFileName.contains("..")) {
                throw new RuntimeException("Sorry! Filename contains invalid path sequence " + newFileName);
            }

            stored.setFileName(newFileName);
            stored.setFileType(update.getContentType());
            stored.setData(update.getBytes());

            return repository.saveAndFlush(stored);
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + newFileName + ". Please try again!", ex);
        }
    }

    @Override
    public ProductImage updateFile(ProductImage stored, ProductImage update) {
        stored.setFileName(update.getFileName());
        stored.setFileType(update.getFileType());
        stored.setData(update.getData());
        return repository.saveAndFlush(stored);
    }

    @Override
    public Optional<ProductImage> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<ProductImage> findAllByProductDetails(Long productDetailId) {
        return repository.findAllByProductDetails(productDetailId);
    }

//    @Override
//    public List<ProductImage> findAllByProductDetailsContainsProductNameOrderByData(ProductDetail detail) {
//        return repository.findAllByProductNameOrderByData(detail);
//    }

    @Override
    public List<ProductImage> findAll() {
        return repository.findAll();
    }

    @Override
    public List<ProductImage> findByFileName(String fileName) {
        return repository.findByFileName(fileName);
    }

    @Override
    public void delete(ProductImage productImage) {
        repository.delete(productImage);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<ProductJoinProductImageDTO> selectProductJoinProductImageDTO(String productNamePart) {
        return repository.selectProductJoinProductImageDTO(productNamePart);
    }

    // ---------------------------------------------------------------------------------------------------------------------
//    @Autowired
////    private EntityManagerFactory emf;
//        EntityManager entityManager;
//
//    @Override
//    public void findAllProductImageIdByProductName() {
////        EntityManager entityManager = emf.createEntityManager();
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
//        Root<Product> root = criteriaQuery.from(Product.class);
//        criteriaQuery.select(root);
//        TypedQuery<Product> query = entityManager.createQuery(criteriaQuery);// qlString + Class resultClass
//        List<Product> resultList = query.getResultList();
//        resultList.forEach(result -> System.out.println("*************************************************** " + result));
//    }


//    /**
//     * returns result of the NamedNativeQuery "ProductImage.selectProductJoinProductImageDTO" defined
//     * in the {@code ProductImage} class
//     * @param productNamePart a part or a whole name of the product
//     * @return  list of the query results as {@code ProductImageDTO} objects
//     * @see ProductJoinProductImageDTO
//     * @see ProductImage
//     */
//    @Override
//    public List<ProductJoinProductImageDTO> test1test(String productNamePart) {
//        // NB: this query creates a TypedQuery result. See ProductImage class for appropriate NamedNativeQuery
//        return entityManager.createNamedQuery("ProductImage.selectProductJoinProductImageDTO", ProductJoinProductImageDTO.class)
//                .setParameter("product_name_part", productNamePart)
//                .getResultList();
//    }

}
