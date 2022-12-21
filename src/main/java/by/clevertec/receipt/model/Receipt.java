package by.clevertec.receipt.model;

import by.clevertec.receipt.model.discountStrategy.BaseProductDiscountStrategy;
import by.clevertec.receipt.model.discountStrategy.IDiscountStrategy;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Receipt {
    private String storeName;
    private String address;
    private String phoneNumber;
    private int cashierNumber;
    private LocalDate date;
    private LocalTime time;
    private double totalDiscount;
    private double totalPrice;
    private double taxSum;
    private double taxedTotalPrice;
    private Set<ProductPosition> productPositions;
    private Card card;

    IDiscountStrategy productDiscountStrategy;

    private String stringReceipt;

    private Receipt(){}
    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private Receipt receipt;

        public Builder() {
            this.receipt = new Receipt();
        }
        public Receipt build() {
            StringBuilder receiptSB = new StringBuilder();
            receipt.date = LocalDate.now();
            receipt.time = LocalTime.now();

            receiptSB
                    .append("\t\tCASH RECEIPT\n")
                    .append("\t\t" + receipt.storeName + "\n")
                    .append("\t" + receipt.address + "\n")
                    .append("\t\tTel :" + receipt.phoneNumber + "\n")
                    .append("\n")
                    .append("Cashier: №" + receipt.cashierNumber + "\t\t   Date: " + receipt.date + "\n")
                    .append("\t\t\t   Time: " + receipt.time.format(DateTimeFormatter.ofPattern("hh:mm:ss")) + "\n")
                    .append("--------------------------------------------\n")
                    .append("QTY\tDESCRIPTION\t\tPRICE\tTOTAL\n")
                    .append("\n");

            if (receipt.card != null) {
                receipt.card.discount(receipt.productPositions);
            }
            for (ProductPosition item : receipt.productPositions) {
                receiptSB.append(item.getCount() + "\t" + item.getProduct().getName() + "\t\t" + item.getProduct().getPrice() + "\t" + String.format("%.2f", item.getTotalPrice()) + "\n");
                if (item.getProduct().getDiscount() != 0) {
                    receiptSB.append("\tDISCOUNT\t\t" + String.format("%.2f", item.getProduct().getDiscount()) + "\t" + String.format("%.2f", item.getTotalDiscount()) + "\n");
                    receiptSB.append("\t" + item.getProduct().getBarcode() + "\n");
                    receipt.totalDiscount += item.getTotalDiscount();
                }
                receipt.totalPrice += item.getTotalPrice();
            }
            receipt.totalPrice -= receipt.totalDiscount;
            receipt.taxSum = receipt.totalPrice * 17 / 100;
            receipt.taxedTotalPrice = receipt.totalPrice + receipt.taxSum;

            receiptSB
                    .append("\n")
                    .append("============================================\n")
                    .append("\n");
            if (receipt.totalDiscount != 0) {
                receiptSB.append("DISCOUNT TOT.\t\t\t\t" + String.format("%.2f", receipt.totalDiscount) + "\n");
            }
            receiptSB.append("TAXABLE TOT.\t\t\t\t" + String.format("%.2f",receipt.totalPrice) + "\n")
                    .append("VAT17%\t\t\t\t\t" + String.format("%.2f",receipt.taxSum) + "\n")
                    .append("TOTAL\t\t\t\t\t" + String.format("%.2f",receipt.taxedTotalPrice));

            receipt.stringReceipt = receiptSB.toString();
            return receipt;
        }

        public Builder storeName(String value){
            receipt.storeName = value;
            return this;
        }
        public Builder address(String value){
            receipt.address = value;
            return this;
        }
        public Builder phoneNumber(String value){
            receipt.phoneNumber = value;
            return this;
        }
        public Builder cashierNumber(int value){
            receipt.cashierNumber = value;
            return this;
        }
        public Builder products(List<Product> value){
            if (value == null || value.isEmpty()){
                throw new RuntimeException("list of products mustn't be null or empty");
            }
            receipt.productPositions = new HashSet<>();
            int tempCount;
            for (Product item : value.stream().distinct().toList()) {
                tempCount = (int)value.stream()
                        .filter(p -> p.getBarcode() == item.getBarcode())
                        .count();
                receipt.productPositions.add(new ProductPosition(item, tempCount, new BaseProductDiscountStrategy()));
            }
            return this;
        }
        public Builder card(Card value){
            receipt.card = value;
            return this;
        }
    }

    public void print(){
        if (stringReceipt != null){
            System.out.println("\n" + stringReceipt);
        }
    }

    public void printToFile(String filename){
        if (stringReceipt != null) {
            try (FileWriter fw = new FileWriter(filename); BufferedWriter bw = new BufferedWriter(fw)) {
                bw.write(stringReceipt);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException("File \"" + filename + "\" not found");
            } catch (IOException ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex.getMessage());
            }
        }
    }

    @Override
    public String toString() {
        return stringReceipt;
    }
}

