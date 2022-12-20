package by.clevertec.receipt.model.discountStrategy;

import by.clevertec.receipt.model.Product;
import by.clevertec.receipt.model.ProductPosition;

import java.util.List;

public class BaseProductDiscountStrategy implements IDiscountStrategy{

    public static final int DISCOUNT_PERCENT = 10;
    public static final int PRODUCT_AMOUNT = 5;

    @Override
    public void discount(ProductPosition productPosition) {
        if (productPosition.canDiscount() & productPosition.getCount() >= 5) {
            productPosition.getProduct().discount(DISCOUNT_PERCENT);
        }
    }
}
