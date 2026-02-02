package spring.boot.nextbuy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;
import spring.boot.nextbuy.entities.Product;
import spring.boot.nextbuy.entities.dto.CategoyResponse;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @NativeQuery(value = "SELECT * FROM (SELECT *, ROW_NUMBER() OVER (PARTITION BY brand ORDER BY id) rn FROM tb_product) t WHERE rn <= 2;")
    List<Product> searchForEachCategory();

    @NativeQuery(value = "SELECT img_path, category, total_products  FROM ( SELECT p.*, COUNT(*) OVER (PARTITION BY category) AS total_products, ROW_NUMBER() OVER ( PARTITION BY category ORDER BY RAND() ) AS rn FROM tb_product p ) t WHERE rn = 1;")
    List<CategoyResponse> onceProductForCategoryAndQuantity();

    @NativeQuery(value = "SELECT * FROM tb_product ORDER BY price DESC LIMIT 8;")
    List<Product> featuredProducts();
}
