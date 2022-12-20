package by.clevertec.receipt.model;

import by.clevertec.receipt.model.discountStrategy.IDiscountStrategy;

import java.util.Objects;

public class ProductPosition {
    private Product product;
    private int count;
    private double totalPrice;
    private double totalDiscount;
    private IDiscountStrategy discountStrategy;

    public ProductPosition(Product product, int count) {
        try {
            this.product = product.clone();
        } catch (CloneNotSupportedException | NullPointerException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex.getMessage());
        }
        this.count = Math.max(count, 1);
        totalPrice = (double)Math.round((product.getPrice()*count) * 100)/100;
        if (product.getDiscount() != 0){
            totalDiscount = product.getDiscount()*count;
        }
    }

    public ProductPosition(Product product, int count, IDiscountStrategy discountStrategy) {
        try {
            this.product = product.clone();
        } catch (CloneNotSupportedException | NullPointerException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex.getMessage());
        }
        this.count = count;
        totalPrice = this.product.getPrice()*count;
        if (canDiscount()){
            if (discountStrategy == null){
                throw new RuntimeException("discountStrategy mustn't be null");
            }
            this.discountStrategy = discountStrategy;
            discountStrategy.discount(this);
        }
        totalDiscount = this.product.getDiscount()*count;
    }

    public Product getProduct() {
        return product;
    }

    public int getCount() {
        return count;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public double getTotalDiscount() {
        return totalDiscount;
    }

    public void addProducts(int count){
        this.count += count;
    }

    public boolean canDiscount(){
        return product.isPromotional() && product.getDiscount() == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductPosition that = (ProductPosition) o;
        return Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product);
    }
}
