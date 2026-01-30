package spring.boot.nextbuy.entities.pk;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import spring.boot.nextbuy.entities.Product;
import spring.boot.nextbuy.entities.ShopCart;

import java.util.Objects;

@Embeddable
public class CartItemsPk {

    @ManyToOne
    @JoinColumn(name = "shop_cart_id")
    private ShopCart shopCart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public ShopCart getShopCart() {
        return shopCart;
    }

    public void setShopCart(ShopCart shopCart) {
        this.shopCart = shopCart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CartItemsPk that = (CartItemsPk) o;
        return Objects.equals(shopCart, that.shopCart) && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shopCart, product);
    }
}
