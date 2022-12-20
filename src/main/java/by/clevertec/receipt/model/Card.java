package by.clevertec.receipt.model;

import by.clevertec.receipt.model.discountStrategy.IDiscountStrategy;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Card {
    private long number;

    private IDiscountStrategy discountStrategy;

    public Card(long number, IDiscountStrategy discountStrategy) {
        this.number = number;
        this.discountStrategy = discountStrategy;
    }

    public long getNumber() {
        return number;
    }

    public void discount(Set<ProductPosition> productPositions) {
        productPositions.forEach(pp -> discountStrategy.discount(pp));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return number == card.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
