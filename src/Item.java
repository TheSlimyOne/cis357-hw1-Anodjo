public class Item {
    private int code;
    private String name;
    private float price;
    private int amount;

    public Item(int code, String name, float price, int amount) {
        this.code = code;
        this.name = name;
        this.price = price;
        setAmount(amount);
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    public float calculateAmountTotal() {
        return price * amount;
    }

    public void setAmount(int amount) {
        if (amount >= 0){
            this.amount = amount;
        } else {
            throw new IllegalArgumentException("Cannot have negative amount " + amount);
        }
    }

    public void addAmount(int amount) {
        if (amount >= 0){
            this.amount += amount;
        } else {
            throw new IllegalArgumentException("Cannot have negative amount " + amount);
        }
    }

    @Override
    public String toString() {
        return String.format("%5d %-13s $ %6.2f", amount, name, calculateAmountTotal());
    } 

}
