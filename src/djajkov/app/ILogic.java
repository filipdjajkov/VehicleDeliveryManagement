package djajkov.app;

public interface ILogic {
    public boolean offloadProducts(Vehicle vehicle) throws CapacityOverloadException, ProductTypeException;
}
