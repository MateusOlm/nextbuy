package spring.boot.nextbuy.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "tb_cart")
public class ShopCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @OneToOne
    @MapsId
    private User user;

    @OneToMany(mappedBy = "id.shopCart")
    private Set<CartItems> cartItems = new HashSet<>();

    public ShopCart() {
    }

    public ShopCart(User user) {
        this.user = user;
    }

    public User getFk_idUser() {
        return user;
    }

    public void setFk_idUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ShopCart shopCart = (ShopCart) o;
        return Objects.equals(id, shopCart.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}