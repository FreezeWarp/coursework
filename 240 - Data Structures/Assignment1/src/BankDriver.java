import java.lang.System;

public class BankDriver {
    public static void main(String[] args) {
        Customer customer1 = new Customer("Bobby McGee");
        customer1.creditAccount(AccountTypeEnum.CHECKING_ACCOUNT, 100);
        customer1.debitAccount(AccountTypeEnum.CHECKING_ACCOUNT, 50);
        customer1.creditAccount(AccountTypeEnum.SAVINGS_ACCOUNT, 100);
        customer1.checkingAccount.setRate(.025);
        customer1.savingAccount.setRate(.03);

        Customer customer2 = new Customer("Jerry McJames", 1000, .01, 10000, .015);
        Customer customer3 = new Customer("Sarah McBobby", 10000, .1, 10000, .105);

        System.out.println("Customers, Before Interest:");
        System.out.println(customer1);
        System.out.println(customer2);
        System.out.println(customer3);

        System.out.println();
        System.out.println("Customer 1 Checking Account Equals Account(50, .025)?:");
        System.out.println(customer1.checkingAccount.equals(new Account(50, .025)));

        // ... I should have made this a foreach/array deal, but I'm tired. I'll get more fancy in the future.
        customer1.addInterest(AccountTypeEnum.CHECKING_ACCOUNT, 1);
        customer2.addInterest(AccountTypeEnum.CHECKING_ACCOUNT, 1);
        customer3.addInterest(AccountTypeEnum.CHECKING_ACCOUNT, 1);

        customer1.addInterest(AccountTypeEnum.SAVINGS_ACCOUNT, 3);
        customer2.addInterest(AccountTypeEnum.SAVINGS_ACCOUNT, 3);
        customer3.addInterest(AccountTypeEnum.SAVINGS_ACCOUNT, 3);

        System.out.println();
        System.out.println("Customers, After Interest:");
        System.out.println(customer1);
        System.out.println(customer2);
        System.out.println(customer3);

        System.out.println();
        System.out.println("Customer 1 Checking Account Equals Account(50, .025)?:");
        System.out.println(customer1.checkingAccount.equals(new Account(50, .025)));
    }
}
