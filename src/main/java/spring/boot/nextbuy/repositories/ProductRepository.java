package spring.boot.nextbuy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;
import spring.boot.nextbuy.entities.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @NativeQuery(value = "SELECT * FROM tb_product WHERE id IN (SELECT MIN(id) FROM tb_product GROUP BY brand) LIMIT 10")
    List<Product> searchForEachCategory();
}
