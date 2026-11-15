/*
It handles:
    1. Pin authentication (maximum 3 attempts).
    2. Menu display.
    3. Withdrawals.
    4. Deposite.
    5. Transaction history.
 */
import java.util.Scanner;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ATM{
    private Bank bank;
    private Scanner sc;
    private Account currentAccount;
    
    private ArrayList<Transaction> history;

    public ATM(Bank bank){
        this.bank = bank;
        this.sc = new Scanner(System.in);
        this.history = new ArrayList<>();
    }

    public void start(){
        System.out.println("================================");
        System.out.println("       WELCOME TO ATM");
        System.out.println("================================");

        int attempts = 0;

        while(attempts < 3){
            System.out.println("Enter user Id: ");
            String userId = sc.nextLine();

            System.out.println("Enter your PIN: ");
            int pin;

            try{
                pin = Integer.parseInt(sc.nextLine());
            }catch(NumberFormatException e){
                System.out.println("Invailid PIN format!");
                attempts++;
                continue;
            }

            currentAccount = bank.authenticate(userId, pin);

            if(currentAccount != null){
                System.out.println("\nLogin successfull!");
                showMenu();
                return;
            }

            attempts++;
            System.out.println("Invalid User Id or PIN!");
            System.out.println("Attempts remaining: " + (3 - attempts));
        }
        System.out.println("\nAccount access denied!");
    }

    private void showMenu(){
        int choice = 0;

        do{
            System.out.println("\n========== ATM MENU ==========");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.println("================================");

            System.out.print("Enter your choice: ");

            try{
                choice = Integer.parseInt(sc.nextLine());
            }catch(NumberFormatException e){
                System.out.println("Please enter a valid menu option!");
                continue;
            }

            switch (choice) {

                case 1:
                    showHistory();
                    break;

                case 2:
                    withdraw();
                    break;

                case 3:
                    deposit();
                    break;
                
                case 4:
                    transfer();
                    break;

                case 5:
                    System.out.println("Thank you for using our ATM!");
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }
        }while(choice != 5);
    }

    private void showHistory(){
        System.out.println("\n==========TRANSACTION HISTORY==========");
        if(history.isEmpty()){
            System.out.println("No transactions found!");
            return;
        }

        for(Transaction transaction : history){
            transaction.displayTransaction();
        }
    }

    private void withdraw(){
        System.out.print("Enter withdrawal amount: ");
        double amount = readAmount();

        if(amount <= 0){
            System.out.println("Please enter a valid positive amount.");
            return;
        }
        if(currentAccount.getBalance() < amount){
            System.out.println("Insufficient balance!");
            return;
        }

        currentAccount.withdraw(amount);
        history.add(new Transaction("Withdrawal", amount, "Cash withdrawn", LocalDateTime.now()));
        System.out.println("Withdrawal successfull!");
        System.out.println("Remaining balance: " + currentAccount.getBalance() );
    }

    private void deposit(){
        System.out.print("Enter deposit amount: ");

        double amount = readAmount();

        if (amount <= 0) {
            System.out.println("Enter a valid positive amount.");
            return;
        }

        currentAccount.deposite(amount);

        history.add(new Transaction("Deposit", amount, "Cash deposited", LocalDateTime.now()));

        System.out.println("Deposit successful!");
        System.out.println("Updated balance: " + currentAccount.getBalance());
    }

    private void transfer(){
        System.out.print("Enter recipient account ID: ");

        String recipientId = sc.nextLine();

        System.out.print("Enter transfer amount: ");

        double amount = readAmount();

        if (amount <= 0) {
            System.out.println("Enter a valid positive amount.");
            return;
        }
        if(currentAccount.getBalance() < amount){
            System.out.println("Insufficient Funds!");
            return;
        }

        boolean success = bank.transfer(currentAccount, recipientId, amount);

        if(success){
            history.add(new Transaction("Transfer", amount, " Transfereed to account: "+ recipientId, LocalDateTime.now()));
            System.out.println("Transfer successful!");
            System.out.println("Remaining Balance: Rs. " + currentAccount.getBalance());
        }else {
            System.out.println("Invalid recipient account!");
        }
    }

    private double readAmount() {

        try {
            return Double.parseDouble(sc.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}