package by.clevertec.receipt;

import by.clevertec.receipt.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

public class ProductTest {

    public static final int PRODUCT_BARCODE = 1;
    public static final String PRODUCT_NAME = "Product1";
    public static final double PRODUCT_PRICE = 0.99;
    public static final int PRODUCT_DISCOUNT_PERCENT = 10;
    public static final double PRODUCT_DISCOUNTED_PRICE = 0.09;

    @Test
    void discountTest() {
        Product product = new Product(PRODUCT_BARCODE, PRODUCT_NAME, PRODUCT_PRICE);

        product.discount(PRODUCT_DISCOUNT_PERCENT);

        Assert.isTrue(product.getDiscount() == PRODUCT_DISCOUNTED_PRICE, "wrong discounted price calculation");
    }
}
