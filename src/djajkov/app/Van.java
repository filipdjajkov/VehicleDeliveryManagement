package djajkov.app;

import java.io.Serializable;

public class Van extends Vehicle implements Serializable {
    boolean extendableCapacity;
    int capacity;

    public Van(){

    }

    public Van(String name, int capacity, boolean extendableCapacity) {
        super(name, capacity);
        this.capacity = capacity;
        this.extendableCapacity = extendableCapacity;
        if(extendableCapacity)
            super.setProducts(new Product[capacity * 2]);
    }

    public boolean getExtendableCapacity() {
        return extendableCapacity;
    }

    public void setExtendableCapacity(boolean extendableCapacity) {
        this.extendableCapacity = extendableCapacity;
    }

    public int returnCapacity() {
        return this.getProducts().length;
    }
}
