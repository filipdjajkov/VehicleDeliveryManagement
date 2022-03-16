package djajkov.app;

import java.io.Serializable;
import java.time.LocalDate;

public class Product implements Serializable {
    private int serialNumber;
    private LocalDate usableUntil;
    private boolean moreSamples;
    private ProductType productType;

    public Product() {
    }

    public Product(int serialNumber, LocalDate usableUntil) {
        this.serialNumber = serialNumber;
        this.usableUntil = usableUntil;
    }

    public Product(int serialNumber, LocalDate usableUntil, ProductType productType, boolean moreSamples) {
        this(serialNumber, usableUntil);
        this.productType = productType;
        this.moreSamples = moreSamples;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public LocalDate getUsableUntil() {
        return usableUntil;
    }

    public void setUsableUntil(LocalDate usableUntil) {
        this.usableUntil = usableUntil;
    }

    public boolean isMoreSamples() {
        return moreSamples;
    }

    public void setMoreSamples(boolean moreSamples) {
        this.moreSamples = moreSamples;
    }
}
