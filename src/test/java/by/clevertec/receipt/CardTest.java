package by.clevertec.receipt;

import by.clevertec.receipt.model.Card;
import by.clevertec.receipt.model.Product;
import by.clevertec.receipt.model.ProductPosition;
import by.clevertec.receipt.model.discountStrategy.BaseCardDiscountStrategy;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.Set;

public class CardTest {

    public static final int PRODUCT_BARCODE = 1;
    public static final String PRODUCT_NAME = "Product1";
    public static final double PRODUCT_PRICE = 0.99;
    public static final double PRODUCT_DISCOUNTED_PRICE = 0.03;
    public static final long CARD_NUMBER = 1234;

    @Test
    void discountTest() {
        Product product = new Product(PRODUCT_BARCODE, PRODUCT_NAME, PRODUCT_PRICE);
        ProductPosition productPosition = new ProductPosition(product,1);
        Set<ProductPosition> productPositionSet = new HashSet<>();
        productPositionSet.add(productPosition);

        Card card = new Card(CARD_NUMBER, new BaseCardDiscountStrategy());
        card.discount(productPositionSet);

        Assert.isTrue(productPosition.getProduct().getDiscount() == PRODUCT_DISCOUNTED_PRICE, "wrong discounted price calculation");
    }
}
