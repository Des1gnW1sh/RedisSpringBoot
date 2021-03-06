package com.wooyoo.learning;

import com.wooyoo.learning.dao.domain.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * 测试修改
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "test")
public class SpringBootMybatisWithRedisApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void test() {
        long productId = 1;
        Product product = restTemplate.getForObject("http://localhost:" + port + "/product/" + productId, Product.class);

      /*  try {
            assertThat(product.getPrice()).isEqualTo(Long.valueOf("4871135969545044666"));
        } catch (Exception e) {
        }*/

        Product insertProduct = new Product();
        long insertPrice = new Random().nextLong();
        insertProduct.setName("new name");
        insertProduct.setPrice(insertPrice);
        System.out.println("-------------------------insert-------------------------");
        restTemplate.put("http://localhost:" + port + "/product/insert/" + 1, insertProduct);




        /*Product newProduct = new Product();
        long newPrice = new Random().nextLong();
        newProduct.setName("new name");
        newProduct.setPrice(newPrice);
        System.out.println("-------------------------"+newPrice+"-------------------------");
        System.out.println("-------------------------update-------------------------");
        restTemplate.put("http://localhost:" + port + "/product/" + productId, newProduct);

        Product testProduct = restTemplate.getForObject("http://localhost:" + port + "/product/" + productId, Product.class);
        assertThat(testProduct.getPrice()).isEqualTo(newPrice);*/
    }
}
