package djajkov.app;

public class CapacityOverloadException extends Exception{
    private String error;

    public CapacityOverloadException(String error){
        this.error = error;
    }

    @Override
    public String toString() {
        return "Error " + error;
    }
}
