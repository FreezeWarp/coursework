/**
 * Models a customer who has a name, a checking account, and a savings account.
 */
public class Customer {
    /**
     * The customer's name.
     */
    public String name;

    /**
     * The checking account owned by the customer, if any.
     */
    public Account checkingAccount;

    /**
     * The savings account owned by the customer, if any.
     */
    public Account savingAccount;


    /* Constructors */
    public Customer(String name)
    {
        this(name, 0, 0, 0,0);
    }

    public Customer(String name,
                    double checkingBalance, double checkingRate,
                    double savingBalance,   double savingRate)
    {
        this.name = name;
        this.checkingAccount = new Account(checkingBalance, checkingRate);
        this.savingAccount =   new Account(savingBalance,   savingRate);
    }

    /**
     * Calculates the combined balance of the customer's checking and savings accounts.
     * @return balance
     */
    public double getTotalBalance()
    {
        return this.checkingAccount.getBalance() +
               this.savingAccount.getBalance();
    }

    /**
     * Credits a user's account with a specified amount of money.
     * @param accountType The account to credit.
     * @param amount the amount to credit.
     */
    public void creditAccount(AccountTypeEnum accountType, double amount)
    {
        switch (accountType) {
            case CHECKING_ACCOUNT: this.checkingAccount.deposit(amount); break;
            case SAVINGS_ACCOUNT:  this.savingAccount.deposit(amount);   break;
        }
    }

    /**
     * Debits a user's account with a specified amount of money.
     * @param accountType The account to debit.
     * @param amount the amount to debit.
     */
    public void debitAccount(AccountTypeEnum accountType, double amount)
    {
        this.creditAccount(accountType, -amount);
    }

    /**
     * Adds interest to a user's account.
     * @param accountType The account to add interest to.
     * @param years The number of years interest has accumulated for.
     */
    public void addInterest(AccountTypeEnum accountType, double years)
    {
        switch (accountType) {
            case CHECKING_ACCOUNT: this.checkingAccount.addInterest(years); break;
            case SAVINGS_ACCOUNT:  this.savingAccount.addInterest(years);   break;
        }
    }


    /* Java Overrides */
    public String toString() {
        return "Customer \"" + this.name + "\" Has a Total Balance of " + this.getTotalBalance() + " {Checking Account: " + this.checkingAccount.toString() + ", " +
                "Savings Account: " + this.savingAccount.toString() + "}";
    }
}
