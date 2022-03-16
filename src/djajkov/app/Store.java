package djajkov.app;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author filip
 * @version 2.0
 */
public class Store extends Institution implements ILogic, Serializable {

    private String phone;

    public Store(String name, String phone, Location location) {
        super(name, location);
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean productUseable(Product product) throws ProductExpiredException {
        if (!product.getUsableUntil().isAfter(LocalDate.now().plusDays(8)))
            throw new ProductExpiredException("Product is expired!");
        return true;
    }

    @Override
    public boolean offloadProducts(Vehicle vehicle) throws CapacityOverloadException {
        vehicle.offloadProducts();
        return false;
    }
}
