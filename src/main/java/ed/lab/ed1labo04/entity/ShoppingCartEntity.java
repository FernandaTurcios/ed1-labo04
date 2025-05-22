package ed.lab.ed1labo04.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import java.util.List;

@Entity
public class ShoppingCartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    private List<CartItemEntity> cartItems;

    private Double totalPrice;

    public ShoppingCartEntity() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public List<CartItemEntity> getCartItems() { return cartItems; }
    public void setCartItems(List<CartItemEntity> cartItems) { this.cartItems = cartItems; }

    public Double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(Double totalPrice) { this.totalPrice = totalPrice; }
}
