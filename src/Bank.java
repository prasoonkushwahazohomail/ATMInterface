import java.util.ArrayList;

public class Bank{
    private ArrayList<Account> accounts; 

    public Bank(){
        // open some accounts.
        accounts.add(new Account("user1", 1234, "1001", 10000));
        accounts.add(new Account("user2", 5678, "1002", 5000));
        accounts.add(new Account("user3", 9999, "1003", 8000));
    }

    public Account authenticate(String userId, int pin){
        for(Account account : accounts){
            if(account.getUserId().equals(userId) && account.getPin()==pin){
                return account;
            }
        }

        return null;
    }

    public Account findAccount(String accountId){
        for(Account account : accounts){
            if(account.getAccountId().equals(accountId)){
                return account;
            }
        }
        return null;
    }

    public boolean transfer(Account sender, String recipientId, double amount){
        Account recipient = findAccount(recipientId);
        /*
        1. recipient should not be null.
        2. sender's account and recipient's account must be different.
        3. sender's ammount must be greater than zero.
        */
        if(recipient == null || sender == recipient || amount <= 0){
            return false;
        }
        if(sender.getBalance()<amount){
            return false;
        }

        sender.withdraw(amount); //ammount subtracted from the sender's account
        recipient.deposite(amount); ////ammount added to the recipient's account

        return true;
    }
}