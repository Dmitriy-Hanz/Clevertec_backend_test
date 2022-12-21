package by.clevertec.receipt.model;

import by.clevertec.receipt.model.discountStrategy.IDiscountStrategy;

import java.util.Objects;

public class Product implements Cloneable {
    private long barcode;
//    private enum group;
    private String name;
    private double price;
    private boolean isPromotional;
    private double discount;

    public Product(long barcode, String name, double price) {
        this.barcode = barcode;
        this.name = name;
        this.price = price;
    }

    public Product(long barcode, String name, double price, boolean isPromotional) {
        this.barcode = barcode;
        this.name = name;
        this.price = price;
        this.isPromotional = isPromotional;
    }

    public Product(long barcode, String name, double price, int discountPercent) {
        this.barcode = barcode;
        this.name = name;
        this.price = price;
        this.discount = Double.parseDouble(String.format("%.3f",price * discountPercent / 100).replace(',','.').replaceFirst(".$","") );
        isPromotional = true;
    }

    public long getBarcode() {
        return barcode;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getDiscount() {
        return discount;
    }

    public boolean isPromotional() {
        return isPromotional;
    }

    public void discount(int discountPercent) {
        discount = Double.parseDouble(String.format("%.3f",price * (double)discountPercent / 100).replace(',','.').replaceFirst(".$","") );
        if (!isPromotional) {
            isPromotional = true;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return barcode == product.barcode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(barcode);
    }

    @Override
    public Product clone() throws CloneNotSupportedException {
        Product result = new Product(this.barcode,this.name,this.price);
        result.isPromotional = this.isPromotional;
        result.discount = this.discount;
        return result;
    }
}
