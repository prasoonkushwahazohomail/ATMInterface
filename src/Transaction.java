import java.time.LocalDateTime;

public class Transaction{
    private String type;
    private double ammount;
    private String details;
    private LocalDateTime dateTime;

    public Transaction(String type, double ammount, String details, LocalDateTime dateTime){
        this.type = type;
        this.ammount = ammount;
        this.details = details;
        this.dateTime = LocalDateTime.now();
    }

    public void displayTransaction(){
        System.out.println("============================");
        System.out.println("Type: " + type);
        System.out.println("Ammount Rs. " + ammount);
        System.out.println("Details: " + details);
        System.out.println("Date: " + dateTime);
    }
}