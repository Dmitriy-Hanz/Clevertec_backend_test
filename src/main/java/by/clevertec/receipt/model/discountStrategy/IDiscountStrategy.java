package by.clevertec.receipt.model.discountStrategy;

import by.clevertec.receipt.model.Product;
import by.clevertec.receipt.model.ProductPosition;

import java.util.List;

public interface IDiscountStrategy {
    void discount(ProductPosition productPosition);
}
