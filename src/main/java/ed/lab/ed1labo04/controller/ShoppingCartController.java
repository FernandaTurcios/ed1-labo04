package ed.lab.ed1labo04.controller;

import ed.lab.ed1labo04.entity.ShoppingCartEntity;
import ed.lab.ed1labo04.entity.CartItemEntity;
import ed.lab.ed1labo04.model.CreateCartRequest;
import ed.lab.ed1labo04.model.CartResponse;
import ed.lab.ed1labo04.model.CartItemResponse;
import ed.lab.ed1labo04.service.ShoppingCartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/cart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @PostMapping
    public ResponseEntity<CartResponse> createCart(@RequestBody CreateCartRequest request) {
        try {
            ShoppingCartEntity cart = shoppingCartService.createCart(request);
            CartResponse response = toCartResponse(cart);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartResponse> getCart(@PathVariable Long id) {
        return shoppingCartService.getCartById(id)
                .map(cart -> ResponseEntity.ok(toCartResponse(cart)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    private CartResponse toCartResponse(ShoppingCartEntity cart) {
        CartResponse resp = new CartResponse();
        resp.setId(cart.getId());
        resp.setTotalPrice(cart.getTotalPrice());
        resp.setCartItems(
                cart.getCartItems().stream().map(item -> {
                    CartItemResponse ci = new CartItemResponse();
                    ci.setProductId(item.getProduct().getId());
                    ci.setName(item.getProduct().getName());
                    ci.setPrice(item.getProduct().getPrice());
                    ci.setQuantity(item.getQuantity());
                    return ci;
                }).collect(Collectors.toList())
        );
        return resp;
    }
}
