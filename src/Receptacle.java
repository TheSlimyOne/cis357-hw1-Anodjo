import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author Jordan Anodjo
 * The <code>Receptacle</code> is a container that holds an array of
 * <code>Item</code>.
 */
public class Receptacle {

    /**
     * Member variable -
     * <code>Item[] items</code> contains an array of <code>Items</code>.
     */
    private Item[] items;

    /**
     * Member variable -
     * <code>length</code> - the lenght of <code>Item[] items</code>.
     */
    private int length;

    /**
     * Constructer -
     * Constructs an empty <code>Receptacle</code>.
     */
    public Receptacle() {
        this.length = 0;
        items = new Item[length];
    }

    /**
     * Constructer -
     * Constructs a <code>Receptacle</code> from file.
     * 
     * @param path The path to the file that holds a collection of item data.
     */
    public Receptacle(String path) throws FileNotFoundException {
        this();

        // Open the file path
        File file = new File(path);
        Scanner input = new Scanner(file);

        // Go through each line in the file.
        // Create an Item from the data.
        // Then insert it into the Receptacle.
        while (input.hasNext()) {
            // Split the data up
            String[] str = input.nextLine().split(",");

            // Create the parameters for the new Item.
            int code = Integer.parseInt(str[0]);
            String name = str[1];
            Float price = Float.parseFloat(str[2]);

            // Create the Item.
            Item item = new Item(code, name, price, 0);

            // Push the Item into this Receptacle.
            this.addItem(item);
        }

        // Close the scanner.
        input.close();
    }

    /**
     * Member Function -
     * This function retrieves a reference to an <code>Item</code> at a specified
     * index.
     * 
     * @param index The index of the <code>Item</code> in the
     *              <code>Receptacle</code>.
     * @return Reference to the <code>Item</code> at the index specified.
     */
    public Item getItem(int index) {
        checkIndex(index); // Check if the index is valid
        return items[index];
    }

    /**
     * Member Function -
     * This function returns the length of the <code>Receptacle</code>
     * 
     * @return The lenght of the <code>Receptacle</code>
     */
    public int getLength() {
        return length;
    }

    /**
     * Member Function -
     * This function sets the amount for a specified index.
     * 
     * @param index  The index of the <code>Item</code> in the
     *               <code>Receptacle</code>.
     * @param amount The value to set the amount to.
     */
    public void setItemAmount(int index, int amount) {
        items[index].setAmount(amount);
    }

    /**
     * Member Function -
     * This function takes an <code>Item</code> and adds it to the this
     * <code>Receptacle</code>.
     * 
     * @param item The <code>Item</code> that will be inserted.
     */
    public void addItem(Item item) {

        // Make a new array 1 larger than the array that stores the Items.
        Item[] temp = new Item[length + 1];

        // Copy over the old array to the new array.
        System.arraycopy(items, 0, temp, 0, length);

        // Update the this Receptacle's array of objects.
        items = temp;

        // Increase the length since the array is now larger.
        length++;

        // Add the Item into the array.
        items[length - 1] = item;
    }

    /**
     * Member Function -
     * This function creates a clone of the <code>Item</code> at a specified index.
     * 
     * @param index The index of the <code>Item</code> in the
     *              <code>Receptacle</code>.
     * @return A copy of the <code>Item</code> at the specified index.
     */
    public Item copyItem(int index) {
        checkIndex(index); // Check if the index is valid

        // Clone all the values in the Item.
        int code = items[index].getCode();
        String name = items[index].getName();
        float price = items[index].getPrice();
        int amount = items[index].getAmount();

        // Create a new Item with the same values
        Item item = new Item(code, name, price, amount);

        // Return that new Item.
        return item;
    }

    /**
     * Member Function -
     * This function checks if the item given exists in this
     * <code>Receptacle</code>.
     * 
     * @param item The <code>Item</code> that is being searched for.
     * @return <code>True</code> if the item exist <code>False</code> otherwise.
     */
    public boolean exist(Item item) {
        return (getIndex(item) != -1);
    }

    /**
     * Member Function -
     * This function takes an <code>Item</code> and returns its index in the
     * <code>Receptacle</code>.
     * 
     * @param item The <code>Item</code> that is being searched for.
     * @return The index of the item, if the item doesnt exist return -1
     */
    public int getIndex(Item item) {

        for (int i = 0; i < length; i++) {
            if (items[i].getName() == item.getName() && items[i].getCode() == item.getCode()) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Member Function -
     * This function checks if the given index is appropriate for this
     * <code>Receptacle</code>.
     * 
     * @param index The index of a <code>Item</code> in the
     *              <code>Receptacle</code>.
     * @throws IndexOutOfBoundsException Invalid item at the index.
     */
    private void checkIndex(int index) {
        // If the index is less than 0 or greater than the length then throw an error.
        if (index < 0 || index >= length)
            throw new IndexOutOfBoundsException("Invalid item at index " + index);
    }

    /**
     * Member Function -
     * Sorts the <code>Receptacle</code> by the length of each <code>Item</code>'s
     * name.
     */
    public void sort() {
        // For each Item compare it with the other Items and swap them if needed.
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                if (items[i].getName().length() < items[j].getName().length()) {
                    Item temp = items[i];
                    items[i] = items[j];
                    items[j] = temp;
                }
            }
        }
    }

    /**
     * Member Function -
     * Calcualates the cost of each <code>Item</code> in the
     * <code>Receptacle</code>.
     * 
     * @return The total cost of the <code>Receptacle</code>
     */
    public float getTotalCost() {
        float totalCost = 0;
        for (int i = 0; i < length; i++) {
            totalCost += items[i].calculateAmountTotal();
        }
        return totalCost;
    }

    /**
     * {@inheritDoc}
     * Member Function -
     * Prints each <code>Item</code> in the <code>Receptacle</code>.
     */
    public String toString() {
        StringBuilder strbldr = new StringBuilder();
        for (int i = 0; i < items.length; i++) {
            strbldr.append(items[i].toString());

            // Dont print the last newline.
            if (!(i == items.length - 1))
                strbldr.append("\n");
        }

        return strbldr.toString();
    }
}
