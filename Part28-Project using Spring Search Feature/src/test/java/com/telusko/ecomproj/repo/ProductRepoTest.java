package com.telusko.ecomproj.repo;

import com.telusko.ecomproj.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ProductRepoTest {

  @Autowired
  private ProductRepo productRepo;

  @BeforeEach
  public void setUp() {
    productRepo.deleteAll();

    // If you annotate the Product model with Lombok's @Builder, you create a
    // product with this instead: Product.builder().name("Apple
    // iPhone").description("Latest
    // model").brand("Apple").category("Electronics").build();
    Product product1 = new Product();
    product1.setName("Apple iPhone");
    product1.setDescription("Latest model");
    product1.setBrand("Apple");
    product1.setCategory("Electronics");

    Product product2 = new Product();
    product2.setName("Samsung Galaxy");
    product2.setDescription("New release");
    product2.setBrand("Samsung");
    product2.setCategory("Electronics");

    productRepo.save(product1);
    productRepo.save(product2);
  }

  @Test
  public void testSearchProducts() {
    List<Product> results = productRepo.searchProducts("Apple");
    assertThat(results).hasSize(1);
    assertThat(results.get(0).getName()).isEqualTo("Apple iPhone");

    results = productRepo.searchProducts("Samsung");
    assertThat(results).hasSize(1);
    assertThat(results.get(0).getName()).isEqualTo("Samsung Galaxy");

    results = productRepo.searchProducts("Electronics");
    assertThat(results).hasSize(2);
  }
}