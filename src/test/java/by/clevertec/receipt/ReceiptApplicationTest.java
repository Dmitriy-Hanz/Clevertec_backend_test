package by.clevertec.receipt;

import by.clevertec.receipt.ex.WrongBarcodeException;
import by.clevertec.receipt.ex.WrongCardNumberException;
import by.clevertec.receipt.model.Card;
import by.clevertec.receipt.model.Product;
import by.clevertec.receipt.model.discountStrategy.BaseCardDiscountStrategy;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.*;

public class ReceiptApplicationTest {

    public static final int PRODUCT_BARCODE = 1;
    public static final int PRODUCT_WRONG_BARCODE = 228;
    public static final int CARD_NUMBER = 1234;
    public static final int WRONG_CARD_NUMBER = 228;
    public static final String PRODUCT_ARG = "1-1";
    public static final String WRONG_PRODUCT_ARG = "1--11";

    private static Set<Product> productSet;
    private static Set<Card> cardSet;

    @BeforeAll
    static void preset() {
        productSet = new HashSet<>();
        productSet.add(new Product(PRODUCT_BARCODE, "product1", 1.55));

        cardSet = new HashSet<>();
        cardSet.add(new Card(CARD_NUMBER, new BaseCardDiscountStrategy()));
    }

    @Test
    void checkArgsFormatPositiveTest() {
        Assert.isTrue(ReceiptApplication.checkArgsFormat(new String[]{PRODUCT_ARG}), "checkArgs function with \"product\" argument fail");
    }
    @Test
    void checkArgsFormatNegativeTest() {
        Assert.isTrue(!ReceiptApplication.checkArgsFormat(new String[]{WRONG_PRODUCT_ARG}), "checkArgs function with wrong \"product\" argument fail");
    }

    @Test
    void addProductPositiveTest() {
        List<Product> productList = new ArrayList<>();

        ReceiptApplication.addProduct(productList, productSet, PRODUCT_BARCODE);

        Assert.notEmpty(productList, "product list is empty");
        Assert.isTrue(productList.get(0).getBarcode() == PRODUCT_BARCODE, "products mismatch");
    }

    @Test
    void addProductNegativeTest() {
        try {
            ReceiptApplication.addProduct(new ArrayList<>(), productSet, PRODUCT_WRONG_BARCODE);
        } catch (WrongBarcodeException ex) {
            assert true;
            return;
        }
        assert false;
    }

    @Test
    void getCardPositiveTest() {
        Card card = ReceiptApplication.getCard(cardSet, CARD_NUMBER);

        Assert.notNull(card, "card is null");
        Assert.isTrue(card.getNumber() == CARD_NUMBER, "cards mismatch");
    }
    @Test
    void getCardNegativeTest() {
        try {
            ReceiptApplication.getCard(cardSet, WRONG_CARD_NUMBER);
        } catch (WrongCardNumberException ex) {
            assert true;
            return;
        }
        assert false;
    }
}
