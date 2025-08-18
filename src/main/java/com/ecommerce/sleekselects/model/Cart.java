package com.ecommerce.sleekselects.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @OneToMany(mappedBy = "cart" ,cascade = CascadeType.ALL , orphanRemoval = true)
    List<CartItem> carItems;



    public void addItem(CartItem item){
        this.carItems.add(item);
        item.setCart(this);
        updateTotalAmount();
    }
    public void removeItem(CartItem item){
        this.carItems.remove(item);
        item.setCart(null);
        updateTotalAmount();

    }

    private void updateTotalAmount() {
        this.totalAmount = carItems.stream().map(cartItem -> {
            BigDecimal unitPrice = cartItem.getUnitPrice();
            if(unitPrice ==null){
                return BigDecimal.ZERO;
            }
            return unitPrice.multiply(BigDecimal.valueOf(cartItem.getQuantity()));
        }).reduce(BigDecimal.ZERO , BigDecimal::add);
    }

}
