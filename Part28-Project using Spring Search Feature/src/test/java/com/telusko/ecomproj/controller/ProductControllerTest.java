package com.telusko.ecomproj.controller;

import com.telusko.ecomproj.model.Product;
import com.telusko.ecomproj.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class ProductControllerTest {

  @Mock
  private ProductService productService;

  @InjectMocks
  private ProductController productController;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testGetAllProducts() {
    List<Product> products = Arrays.asList(new Product(), new Product());
    when(productService.getAllProducts()).thenReturn(products);

    ResponseEntity<List<Product>> response = productController.getAllProducts();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(products, response.getBody());
  }

  @Test
  public void testGetProduct() {
    Product product = new Product();
    when(productService.getProduct(anyInt())).thenReturn(product);

    ResponseEntity<Product> response = productController.getProduct(1);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(product, response.getBody());
  }

  @Test
  public void testGetProductNotFound() {
    when(productService.getProduct(anyInt())).thenReturn(null);

    ResponseEntity<Product> response = productController.getProduct(1);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  public void testAddProduct() throws IOException {
    Product product = new Product();
    MultipartFile imageFile = mock(MultipartFile.class);
    Product savedProduct = new Product();
    when(productService.addProduct(any(Product.class), any(MultipartFile.class))).thenReturn(savedProduct);

    ResponseEntity<?> response = productController.addProduct(product, imageFile);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(savedProduct, response.getBody());
  }

  @Test
  public void testAddProductException() throws IOException {
    Product product = new Product();
    MultipartFile imageFile = mock(MultipartFile.class);
    when(productService.addProduct(any(Product.class), any(MultipartFile.class))).thenThrow(new IOException("Error"));

    ResponseEntity<?> response = productController.addProduct(product, imageFile);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals("Error", response.getBody());
  }

  @Test
  public void testGetImageByProductId() {
    Product product = new Product();
    product.setImageData(new byte[] { 1, 2, 3 });
    product.setImageType("image/png");
    when(productService.getProduct(anyInt())).thenReturn(product);

    ResponseEntity<byte[]> response = productController.getImageByProductId(1);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(product.getImageData(), response.getBody());
  }

  @Test
  public void testUpdateProduct() throws IOException {
    Product product = new Product();
    MultipartFile imageFile = mock(MultipartFile.class);
    Product updatedProduct = new Product();
    when(productService.updateProduct(anyInt(), any(Product.class), any(MultipartFile.class)))
        .thenReturn(updatedProduct);

    ResponseEntity<String> response = productController.updateProduct(1, product, imageFile);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("updated", response.getBody());
  }

  @Test
  public void testUpdateProductException() throws IOException {
    Product product = new Product();
    MultipartFile imageFile = mock(MultipartFile.class);
    when(productService.updateProduct(anyInt(), any(Product.class), any(MultipartFile.class)))
        .thenThrow(new IOException("Error"));

    ResponseEntity<String> response = productController.updateProduct(1, product, imageFile);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("Failed to update", response.getBody());
  }

  @Test
  public void testDeleteProduct() {
    Product product = new Product();
    when(productService.getProduct(anyInt())).thenReturn(product);

    ResponseEntity<String> response = productController.deleteProduct(1);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Deleted", response.getBody());
  }

  @Test
  public void testDeleteProductNotFound() {
    when(productService.getProduct(anyInt())).thenReturn(null);

    ResponseEntity<String> response = productController.deleteProduct(1);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Product not found", response.getBody());
  }

  @Test
  public void testSearchProducts() {
    List<Product> products = Arrays.asList(new Product(), new Product());
    when(productService.searchProducts(anyString())).thenReturn(products);

    ResponseEntity<List<Product>> response = productController.searchProducts("keyword");

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(products, response.getBody());
  }
}