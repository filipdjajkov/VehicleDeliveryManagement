package djajkov.app;

import java.io.Serializable;

public class ProductsCenter extends Institution implements ILogic, Serializable {
    private String email;
    private Product[] products;

    public ProductsCenter(){
    }

    public ProductsCenter(String name, String email, Location city, Product[] product) {
        super(name, city);
        this.email = email;
        this.products = product;
    }

    public ProductsCenter(String name, String email, Location city, int capacity) {
        super(name, city);
        this.email = email;
        this.products = new Product[capacity];
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Product[] getProducts() {
        return products;
    }

    public void setProducts(Product[] products) {
        this.products = products;
    }

    public boolean addProduct(Product product) {
        for (int i = 0; i < products.length; ++i) {
            if (products[i] == null) {
                products[i] = product;
                return true;
            }
        }
        return false;
    }

    public void removeProduct(Product product) {
        for (int i = 0; i < products.length; ++i) {
            if (products[i] == null)
                return;
            if (products[i].equals(product)) {
                products[i] = null;
                while (i < products.length - 1) {
                    if (products[i + 1] == null)
                        break;
                    products[i] = products[i + 1];
                    products[i + 1] = null;
                    ++i;
                }
                return;
            }
        }
    }

    public boolean removeProduct(int serialNumber) {
        for (int i = 0; i < products.length; ++i) {
            if (products[i] == null)
                return false;
            if (products[i].getSerialNumber() == serialNumber) {
                products[i] = null;
                while (i < products.length - 1) {
                    if (products[i + 1] == null)
                        break;
                    products[i] = products[i + 1];
                    products[i + 1] = null;
                    ++i;
                }
                return true;
            }
        }
        return false;
    }

    public int returnNumberOfProducts() {
        for (int i = 0; i <= products.length; ++i) {
            if (products[i] == null)
                return i;
        }
        return 0;
    }

    public boolean returnProductExists(int serialNumber) {
        for (int i = 0; i < products.length; ++i) {
            if (products[i] == null)
                return false;
            if (products[i].getSerialNumber() == serialNumber)
                return true;
        }
        return false;
    }

    @Override
    public boolean offloadProducts(Vehicle vehicle) throws CapacityOverloadException, ProductTypeException {
        int productsRealLength=0;
        for (; productsRealLength < products.length; ++productsRealLength) {
            if(products[productsRealLength]==null)
                break;
        }
        int productRealLength=0;
        for (; productRealLength < vehicle.getProducts().length; ++productRealLength) {
            if(vehicle.getProducts()[productRealLength]==null)
                break;
        }
        if(productsRealLength > (vehicle.getProducts().length - productRealLength))
            throw new CapacityOverloadException("Capacity full!");

        if(vehicle instanceof Car){
            try {
                vehicle.loadProducts(products);
                products =new Product[products.length];
                return true;
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if(vehicle instanceof Van) {
            for (int i = 0; i< products.length; ) {
                if(products[i] == null)
                    return true;
                try {
                    vehicle.loadProducts(products[i]);
                    removeProduct(products[i]);
                }catch (Exception e){
                    e.printStackTrace();
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
