package by.clevertec.receipt;

import by.clevertec.receipt.model.Product;
import by.clevertec.receipt.model.ProductPosition;
import by.clevertec.receipt.model.discountStrategy.BaseProductDiscountStrategy;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

public class ProductPositionTest {

    public static final int PRODUCT_BARCODE = 1;
    public static final String PRODUCT_NAME = "Product1";
    public static final double PRODUCT_PRICE = 0.99;
    public static final int PRODUCT_DISCOUNT_PERCENT = 20;
    public static final double PRODUCT_DISCOUNTED_PRICE = 0.19;

    private static Product product;
    private static Product promotionalProduct;
    private static Product discountedProduct;

    @BeforeAll
    static void preset() {
        product = new Product(PRODUCT_BARCODE, PRODUCT_NAME, PRODUCT_PRICE);
        promotionalProduct = new Product(PRODUCT_BARCODE, PRODUCT_NAME, PRODUCT_PRICE,true);
        discountedProduct = new Product(PRODUCT_BARCODE, PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_DISCOUNT_PERCENT);
    }

    @Test
    void productPositionConstructorWithProductPositiveTest() {
        ProductPosition productPosition = new ProductPosition(product,1, new BaseProductDiscountStrategy());

        Assert.isTrue(productPosition.getProduct().getDiscount() == 0, "unexpected discounted price calculation");
    }
    @Test
    void productPositionTestWithPromotionalProductPositiveTest() {
        ProductPosition productPosition = new ProductPosition(promotionalProduct,BaseProductDiscountStrategy.PRODUCT_AMOUNT, new BaseProductDiscountStrategy());

        Assert.isTrue(productPosition.getProduct().getDiscount() == 0.09, "wrong discounted price calculation");
    }
    @Test
    void productPositionTestWithDiscountedProductPositiveTest() {
        ProductPosition productPosition = new ProductPosition(discountedProduct,1, new BaseProductDiscountStrategy());

        Assert.isTrue(productPosition.getProduct().getDiscount() == PRODUCT_DISCOUNTED_PRICE, "wrong discounted price calculation");
    }

    @Test
    void productPositionConstructorNegativeTest() {
        try {
            ProductPosition productPosition = new ProductPosition(promotionalProduct,BaseProductDiscountStrategy.PRODUCT_AMOUNT, null);
        } catch (RuntimeException ex){
            assert true;
            return;
        }
        assert false;
    }
}
