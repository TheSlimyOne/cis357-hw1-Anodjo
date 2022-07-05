public class Item {
    private int code;
    private String name;
    private float price;
    private int amount;

    /**
     * Constructor for Item.
     * 
     * @param code the code/ID of the item.
     * @param name the name of the item.
     * @param price the price of the item.
     * @param amount the amount of the item.
     */
    public Item(int code, String name, float price, int amount) {
        this.code = code;
        this.name = name;
        this.price = price;
        // Check if the amount is acceptable. 
        setAmount(amount);
    }

    /**
     * Getter for the item's code.
     * 
     * @return item's code.
     */
    public int getCode() {
        return code;
    }

    /**
     * Getter for the item's name.
     * 
     * @return item's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the item's price.
     * 
     * @return item's price.
     */
    public float getPrice() {
        return price;
    }

    /**
     * Getter for amount of this item.
     * 
     * @return item's amount.
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Calculates the worth of all the amount of items together.
     * 
     * @return the price of the item times its amount.
     */
    public float calculateAmountTotal() {
        return price * amount;
    }

    /**
     * This function sets the amount for this item.
     * The amount must be greater than or equal to 0.
     * Cannot logically have a negative item so the function will throw an error.
     * 
     * @param amount what to set item amount to.
     */
    public void setAmount(int amount) {
        if (amount >= 0) {
            this.amount = amount;
        } else {
            throw new IllegalArgumentException("Cannot have a negative amount of item.");
        }
    }

    /**
     * This function increments amount for this item.
     * The amount added must be greater than 0.
     * Cannot logically add a negative item or add 0 of an item so the function will
     * throw an error.
     * 
     * @param amount
     */
    public void addAmount(int amount) {
        if (amount >= 0) {
            this.amount += amount;
        } else {
            throw new IllegalArgumentException("Cannot add a non positive item.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format("%5d %-13s $ %6.2f", amount, name, calculateAmountTotal());
    }

}
