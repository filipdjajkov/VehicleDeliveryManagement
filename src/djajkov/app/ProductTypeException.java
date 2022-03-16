package djajkov.app;

public class ProductTypeException extends Exception{
    private String error;

    public ProductTypeException(String error){
        this.error = error;
    }

    @Override
    public String toString() {
        return "Error " + error;
    }
}
