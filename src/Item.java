/**
 * @author Jordan Anodjo
 * The <code>Item</code> is a container that hold all the data about
 * an item.
 */
public class Item {

    /**
     * Member variable -
     * The code/identification for an <code>Item</code>.
     */
    private int code;

    /**
     * Member variable -
     * The name for an <code>Item</code>.
     */
    private String name;

    /**
     * Member variable -
     * The price for an <code>Item</code>.
     */
    private float price;

    /**
     * Member variable -
     * The amount of <code>Item</code> stored/ordered.
     */
    private int amount;

    /**
     * Constructor -
     * Constructs an <code>Item</code> with all memeber variables filled.
     * 
     * @param code
     * @param name
     * @param price
     * @param amount the quanity of the <code>Item</code>
     */
    public Item(int code, String name, float price, int amount) {
        this.code = code;
        this.name = name;
        this.price = price;

        // Check if the amount is acceptable.
        setAmount(amount);
    }

    /**
     * Member Function -
     * Getter for the <code>Item</code> code.
     * 
     * @return Item's code.
     */
    public int getCode() {
        return code;
    }

    /**
     * Member Function -
     * Getter for the <code>Item</code> name.
     * 
     * @return The <code>Item</code>'s name.
     */
    public String getName() {
        return name;
    }

    /**
     * Member Function -
     * Getter for the <code>Item</code> price.
     * 
     * @return The <code>Item</code>'s price.
     */
    public float getPrice() {
        return price;
    }

    /**
     * Member Function -
     * Getter for amount of this <code>Item</code>.
     * 
     * @return The <code>Item</code> amount.
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Member Function -
     * This function sets the amount for this <code>Item</code>'s.
     * The amount must be greater than or equal to 0.
     * Cannot logically have a negative <code>Item</code>'s so the function will
     * throw an error.
     * 
     * @param amount what to set Item amount to.
     */
    public void setAmount(int amount) {
        if (amount >= 0)
            this.amount = amount;
        else
            throw new IllegalArgumentException("Cannot have a negative amount of item.");

    }

    /**
     * Member Function -
     * This function increments amount for this <code>Item</code>'s.
     * The amount added must be greater than 0.
     * Cannot logically add a negative <code>Item</code>'s or add 0 of an
     * <code>Item</code>'s so the function will
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
     * Member Function -
     * Calculates the full cost of <code>Item</code>.
     * 
     * @return The price of the <code>Item</code>'s times its amount.
     */
    public float calculateAmountTotal() {
        return price * amount;
    }

    /**
     * {@inheritDoc}
     * Member Function -
     * Prints <code>Item</code> in the format of: amount, name, and total price.
     */
    @Override
    public String toString() {
        return String.format("%5d %-13s $ %6.2f", amount, name, calculateAmountTotal());
    }

}
