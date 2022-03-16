package djajkov.app;

import java.io.Serializable;

public class Car extends Vehicle implements Serializable {
    private ProductType productType;

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public Car(String name, int capacity, ProductType productType) {
        super(name, capacity);
        this.productType = productType;
    }

    public Car(){

    }

    public int returnCapacity() {
        return this.getProducts().length;
    }

    @Override
    public String toString() {
        return "Avto{" +
                super.toString() +
                ", tipCepiva='" + productType + '\'' +
                '}';
    }

    @Override
    public void loadProducts(Product products) throws CapacityOverloadException, ProductTypeException {
        if (products.getProductType() != productType)
            throw new ProductTypeException("The car supports other type of products");
        super.loadProducts(products);
    }

    @Override
    public void loadProducts(Product[] products) throws CapacityOverloadException, ProductTypeException {
        for (int j = 0; j < products.length; ++j) {
            if(products[j]==null)
                break;
            if (products[j].getProductType() != productType)
                throw new ProductTypeException("The car supports other type of products");
        }
        super.loadProducts(products);
    }
}