package by.clevertec.receipt.model.discountStrategy;

import by.clevertec.receipt.model.ProductPosition;

public class BaseCardDiscountStrategy implements IDiscountStrategy{

    public static final int DISCOUNT_PERCENT = 3;

    @Override
    public void discount(ProductPosition productPosition) {
        if (!productPosition.canDiscount()) {
            productPosition.getProduct().discount(DISCOUNT_PERCENT);
        }
    }
}
