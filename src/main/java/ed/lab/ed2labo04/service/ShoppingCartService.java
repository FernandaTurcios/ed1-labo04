package ed.lab.ed2labo04.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ed.lab.ed2labo04.repository.ShoppingCartRepository;
import ed.lab.ed2labo04.entity.ShoppingCartEntity;
import ed.lab.ed2labo04.entity.CartItemEntity;
import ed.lab.ed2labo04.model.CreateCartRequest;
import ed.lab.ed2labo04.model.CartItemRequest;
import ed.lab.ed1labo04.repository.ProductRepository;
import ed.lab.ed1labo04.entity.ProductEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductRepository productRepository;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository,
                               ProductRepository productRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public ShoppingCartEntity createCart(CreateCartRequest request) {
        // Transform requests into entities, updating inventory
        List<CartItemEntity> items = request.getCartItems().stream().map(itemReq -> {
            if (itemReq.getQuantity() <= 0) {
                throw new IllegalArgumentException("Quantity must be greater than 0");
            }
            ProductEntity product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + itemReq.getProductId()));
            if (product.getQuantity() < itemReq.getQuantity()) {
                throw new IllegalArgumentException("Insufficient inventory for product id: " + itemReq.getProductId());
            }
            // Deduct inventory
            product.setQuantity(product.getQuantity() - itemReq.getQuantity());
            productRepository.save(product);

            CartItemEntity cartItem = new CartItemEntity();
            cartItem.setProduct(product);
            cartItem.setQuantity(itemReq.getQuantity());
            return cartItem;
        }).collect(Collectors.toList());

        // Calculate total price
        double total = items.stream()
                .mapToDouble(ci -> ci.getProduct().getPrice() * ci.getQuantity())
                .sum();

        ShoppingCartEntity cart = new ShoppingCartEntity();
        cart.setCartItems(items);
        cart.setTotalPrice(total);

        return shoppingCartRepository.save(cart);
    }

    public Optional<ShoppingCartEntity> getCartById(Long id) {
        return shoppingCartRepository.findById(id);
    }
}
