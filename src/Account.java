public class Account{
    private String userId;
    private int pin;
    private String accountId;
    private double balance;

    // create a new account
    public Account(String userId, int pin, String accountId, double balance){
        this.userId = userId;
        this.pin = pin;
        this.accountId = accountId;
        this.balance = balance;
    }

    // getter methods
    public String getUserId(){
        return userId;
    }
    public int getPin(){
        return pin;
    }
    public String getAccountId(){
        return accountId;
    }
    public double getBalance(){
        return balance;
    }

    // money deposite method
    public void deposite(double ammount){
        balance = balance+ammount;          // add ammount to the balance
    }

    // money withdrawal method
    public boolean withdraw(double ammount){
        // entered ammount should be greater than 0 and less than or equal to balance
        if(ammount>0 && balance>=ammount){  
            balance = balance - ammount;    // subtract ammount from the balance
            return true;    // withdrawal successfull
        }
        return false;   // withdrawal failed 
    }
}