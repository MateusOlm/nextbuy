package spring.boot.nextbuy.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import spring.boot.nextbuy.entities.pk.CartItemsPk;

import java.util.Objects;

@Entity
@Table(name = "tb_cart_items")
public class CartItems {

    @EmbeddedId
    private CartItemsPk id = new CartItemsPk();
    private Integer cartQuantity;

    public CartItems() {}

    public CartItems(ShopCart shopCart, Product product, Integer cartQuantity) {
        id.setShopCart(shopCart);
        id.setProduct(product);
        this.cartQuantity = cartQuantity;
    }

    @JsonIgnore
    public ShopCart getShopCart() {
        return id.getShopCart();
    }

    public void setShopCart(ShopCart shopCart) {id.setShopCart(shopCart);}

    public Product getProduct() {
        return id.getProduct();
    }

    public void setProduct(Product product) {id.setProduct(product);}

    public Integer getCartQuantity() {
        return cartQuantity;
    }

    public void setCartQuantity(Integer cartQuantity) {
        this.cartQuantity = cartQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CartItems cartItems = (CartItems) o;
        return Objects.equals(id, cartItems.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
