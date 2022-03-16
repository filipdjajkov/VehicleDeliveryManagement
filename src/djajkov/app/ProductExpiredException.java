package djajkov.app;

public class ProductExpiredException extends Exception{
    private String error;

    public ProductExpiredException(String error){
        this.error = error;
    }

    @Override
    public String toString() {
        return "Error " + error;
    }
}
