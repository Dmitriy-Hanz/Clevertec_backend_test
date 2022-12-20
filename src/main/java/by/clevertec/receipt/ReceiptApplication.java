package by.clevertec.receipt;

import by.clevertec.receipt.model.Card;
import by.clevertec.receipt.model.Product;
import by.clevertec.receipt.model.Receipt;
import by.clevertec.receipt.model.discountStrategy.BaseCardDiscountStrategy;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@SpringBootApplication
public class ReceiptApplication implements CommandLineRunner {
    public static final String PRODUCT_PATTERN = "\\d+-\\d+";
    public static final String CARD_PATTERN = "card-\\d+";
    public static final String FILE_PATTERN = "file-[^\\\\/?:*\"><|]+\\.[a-z0-9]+";
    public static final String FILEPATH_PATTERN = "filepath-[a-zA-Z]:(\\\\[^\\\\/?:*\"><|]+)+\\.[a-z0-9]+";

    public static void main(String[] args) {
        SpringApplication.run(ReceiptApplication.class, args);
    }

    @Override
    public void run(String... args){
        if (args.length == 0){
            throw new RuntimeException("No args");
        }

        Set<Product> productCatalog = new HashSet<>();
        productCatalog.add(new Product(1,"product1",1.55));
        productCatalog.add(new Product(2,"product2",2.34, true));
        productCatalog.add(new Product(3,"product3",3.32, true));
        productCatalog.add(new Product(4,"product4",10.50, 20));
        productCatalog.add(new Product(5,"product5",3.12));
        productCatalog.add(new Product(6,"product6",0.45));
        productCatalog.add(new Product(7,"product7",37.34, 30));
        productCatalog.add(new Product(8,"product8",17.43, 15));
        productCatalog.add(new Product(9,"product9",1.50, true));
        productCatalog.add(new Product(10,"product10",0.99));
        productCatalog.add(new Product(11,"product11",4.23));

        Set<Card> cardCatalog = new HashSet<>();
        cardCatalog.add(new Card(1234,new BaseCardDiscountStrategy()));
        cardCatalog.add(new Card(2345,new BaseCardDiscountStrategy()));
        cardCatalog.add(new Card(3456,new BaseCardDiscountStrategy()));

        List<Product> products = new ArrayList<>();

        String[] userArgs = args;

        if (args[0].matches(FILE_PATTERN) || args[0].matches(FILEPATH_PATTERN)){
            if (userArgs.length > 1){
                throw new RuntimeException("Wrong args format");
            }
            String filepath = args[0].split("-")[1];
            try (FileReader fr = new FileReader(filepath);BufferedReader bfr = new BufferedReader(fr)) {
                List<String> fileArgs = new ArrayList<>();
                while (bfr.ready()){
                    fileArgs.add(bfr.readLine());
                }
                userArgs = fileArgs.toArray(userArgs);
            }
            catch (FileNotFoundException ex){
                throw new RuntimeException("File not found");
            }
            catch (IOException ex){
                ex.printStackTrace();
                throw new RuntimeException(ex.getMessage());
            }
        }

        if(!checkArgs(userArgs)){
            throw new RuntimeException("Wrong args format");
        }

        String[] UserArgument;
        Card userCard = null;
        if (userArgs[userArgs.length-1].contains("card")){
            userCard = getCard(cardCatalog,Long.parseLong(userArgs[userArgs.length-1].split("-")[1]));
        }
        for (int i = 0; i < userArgs.length-1 + (userCard == null ? 1:0); i++) {
            UserArgument = userArgs[i].split("-");
            for (int j = 0; j < Integer.parseInt(UserArgument[1]); j++) {
                addProduct(products,productCatalog,Long.parseLong(UserArgument[0]));
            }
        }

        Receipt receipt;
        if (userArgs[userArgs.length-1].contains("card")) {
            receipt = Receipt.builder()
                    .storeName("Supermarket 123")
                    .address("12, MILKYWAY Galaxy/ Earth")
                    .phoneNumber("123-456-7890")
                    .cashierNumber(1520)
                    .products(products)
                    .card(userCard)
                    .build();
        } else {
            receipt = Receipt.builder()
                    .storeName("Supermarket 123")
                    .address("12, MILKYWAY Galaxy/ Earth")
                    .phoneNumber("123-456-7890")
                    .cashierNumber(1520)
                    .products(products)
                    .build();
        }

        receipt.print();
        receipt.printToFile("receipt.txt");
    }

    public static void addProduct(List<Product> productList, Set<Product> productCatalog, long productBarcode){
        try {
            Product temp = productCatalog.stream()
                    .filter(p -> p.getBarcode() == productBarcode)
                    .findFirst()
                    .orElseThrow();

            productList.add(temp.clone());
        }
        catch (NoSuchElementException ex){
            System.out.println("There is no product with this barcode \"" + productBarcode + "\"");
        }
        catch (CloneNotSupportedException ex){
            ex.printStackTrace();
        }
    }

    public static Card getCard(Set<Card> cardCatalog, long cardNumber){
        try {
            return cardCatalog.stream()
                    .filter(p -> p.getNumber() == cardNumber)
                    .findFirst()
                    .orElseThrow();
        }
        catch (NoSuchElementException ex){
            System.out.println("There is no card with this number \"" + cardNumber + "\"");
            return null;
        }
    }

    public static boolean checkArgs(String[] args) {
        if (!args[args.length - 1].matches(PRODUCT_PATTERN) && !args[args.length - 1].matches(CARD_PATTERN)) {
            return false;
        }
        for (int i = 0; i < args.length - 1; i++) {
            if (!args[i].matches(PRODUCT_PATTERN)) {
                return false;
            }
        }
        return true;
    }
}
