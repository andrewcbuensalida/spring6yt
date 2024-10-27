package com.telusko.ecomproj.service;

import com.telusko.ecomproj.model.Product;
import com.telusko.ecomproj.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService implements HealthIndicator {

  private Boolean isProductServiceHealthGood() {
    return true;
  };

  // so when going to /actuator/health, it will show the health of the product
  // service
  @Override
  public Health health() {
    if (isProductServiceHealthGood()) {
      return Health.up().withDetail("Product Service", "Service is running!!").build();
    }
    return Health.down().withDetail("Product Service", "Service is not available!!").build();
  }

  @Autowired
  private ProductRepo repo;

  public List<Product> getAllProducts() {
    return repo.findAll();
  }

  public Product getProduct(int id) {
    return repo.findById(id).orElse(null);
  }

  public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
    product.setImageName(imageFile.getOriginalFilename());
    product.setImageType(imageFile.getContentType());
    product.setImageData(imageFile.getBytes());

    return repo.save(product);
  }

  public Product updateProduct(int id, Product product, MultipartFile imageFile) throws IOException {
    // TODO Need to check if the product exists first based on the id
    product.setImageName(imageFile.getOriginalFilename());
    product.setImageType(imageFile.getContentType());
    product.setImageData(imageFile.getBytes());
    return repo.save(product);
  }

  public void deleteProduct(int id) {
    repo.deleteById(id);
  }

  public List<Product> searchProducts(String keyword) {
    return repo.searchProducts(keyword);
  }
}
