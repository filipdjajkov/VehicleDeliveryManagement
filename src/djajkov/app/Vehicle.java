package djajkov.app;

import java.io.Serializable;

public abstract class Vehicle implements Serializable {
    private String registrationPlateNumber;
    private String brand;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    private Product[] products;

    public Vehicle() {

    }

    Vehicle(String registrationPlateNumber) {
        this.registrationPlateNumber = registrationPlateNumber;
    }

    Vehicle(String name, int capacity) {
        if(capacity<0){
            throw new IllegalArgumentException();
        }
        this.name = name;
        this.products = new Product[capacity];
    }

    public Product[] getProducts() {
        return products;
    }

    public void setProducts(Product[] products) {
        this.products = products;
    }

    public void loadProducts(Product product) throws CapacityOverloadException, ProductTypeException {
        for (int i = 0; i < products.length; ++i) {
            if (products[i] == null) {
                products[i] = product;
                return;
            }
        }
    }

    public void loadProducts(Product[] products) throws CapacityOverloadException, ProductTypeException {
        for (int j = 0; j < products.length; ++j) {
            if(products[j]==null)
                break;
            loadProducts(products[j]);
        }
    }

    public void offloadProducts() {
        for (int i = 0; i < products.length; ++i)
            if (products[i] != null)
                products[i]=null;
    }

    public double getCapacity() {
        for (int i = 0; i <= products.length; ++i) {
            if(i== products.length)
                return 100;
            if (products[i] == null)
                return ((double) i / (double) products.length) * 100;
        }
        return 0.0d;
    }
}
