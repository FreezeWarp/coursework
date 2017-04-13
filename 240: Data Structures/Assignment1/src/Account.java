/**
 * Models and account with a certain amount of money and a yearly interest rate.
 */
public class Account {
    /**
     * The money currently in the account.
     */
    private double balance;

    /**
     * The interest rate on the account.
     */
    private double rate;


    /* Constructors */
    public Account(double balance, double rate)
    {
        this.balance = balance;
        this.rate = rate;
    }

    public Account() {
        this(0, 0);
    }


    /* Methods */
    /**
     * Deposits the amount into the user's account.
     * @param amount
     */
    public void deposit(double amount)
    {
        this.balance += amount;
    }

    /**
     * Withdraws the amount from the user's account.
     * @param amount
     */
    public void withdraw(double amount)
    {
        this.deposit(-amount);
    }

    /**
     * Increases the users balance based on the account's interest rate and the specified number of years, calculated using simple interest.
     * @param years
     */
    public void addInterest(double years) {
        this.deposit(this.getBalance() * this.getRate() * years);
    }


    /* Getters and Setters */
    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getRate() {
        return rate;
    }
    public void setRate(double rate) {
        this.rate = rate;
    }


    /* Java Overrides */
    public String toString() {
        return "Account {balance=" + balance +
                ", rate=" + rate + "}";
    }

    public boolean equals(Account o) {
        // Equals to self.
        if (this == o)
            return true;

        // Values equals
        return this.balance == o.balance && this.rate == o.rate;
    }
}
