import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Receptacle {

    private Item[] items;
    private int length;

    public Receptacle() {
        this.length = 0;
        items = new Item[length];
    }

    /** 
     * 
     * 
     * @param item
     */
    public void addItem(Item item) {

        Item[] temp = new Item[length + 1];

        System.arraycopy(items, 0, temp, 0, length);

        items = temp;
        length++;

        items[length - 1] = item;
    }

    public void setItemAmount(int index, int amount) {
        items[index].setAmount(amount);
    }

    public void setLastItemAmount(int amount) {
        setItemAmount(length - 1, amount);
    }

    public void replaceAt(int index, Item item) {
        checkIndex(index);
        items[index] = item;
    }

    public void replaceLast(Item item) {
        replaceAt(length - 1, item);
    }

    public Item getItem(int index) {
        checkIndex(index);
        return items[index];
    }

    public Item copyItem(int index) {
        checkIndex(index);

        int code = items[index].getCode();
        String name = items[index].getName();
        float price = items[index].getPrice();
        int amount = items[index].getAmount();

        Item item = new Item(code, name, price, amount);

        return item;
    }

    public boolean exist(Item item) {

        if (getIndex(item) != -1) {
            return true;
        } else {
            return false;
        }

    }

    public int getIndex(Item item) {

        for (int i = 0; i < length; i++) {
            if (items[i].getName() == item.getName() && items[i].getCode() == item.getCode()) {
                return i;
            }
        }
        return -1;
    }

    public Item getLastItem() {
        return copyItem(length - 1);
    }

    public int getLength() {
        return length;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= length)
            throw new IndexOutOfBoundsException("Invalid item at index " + index);
    }

    public static Receptacle fillReceptacleFromFile(String path) throws FileNotFoundException {

        File file = new File(path);
        Scanner input = new Scanner(file);

        Receptacle container = new Receptacle();

        while (input.hasNext()) {
            String[] str = input.nextLine().split(",");
            int code = Integer.parseInt(str[0]);
            String name = str[1];
            Float price = Float.parseFloat(str[2]);

            Item item = new Item(code, name, price, 0);

            container.addItem(item);
        }

        input.close();

        return container;
    }

    @Override
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

    public void sort() {
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

    public float getTotalCost() {

        float totalCost = 0;
        for (int i = 0; i < length; i++) {
            totalCost += items[i].calculateAmountTotal();
        }

        return totalCost;
    }

    public static void main(String[] args) throws java.io.FileNotFoundException {
        String path = "C:\\Users\\xcree\\OneDrive\\Desktop\\School Folder\\CIS 357\\cis357-hw1-Anodjo\\input.txt";
        final Receptacle stock = Receptacle.fillReceptacleFromFile(path);
        System.out.println("--------------------");
        ;
        System.out.println("Item list:");
        float subTotal = 0;
        for (int i = 0; i < stock.getLength(); i++) {
            System.out.println(stock.copyItem(i));
            subTotal += stock.copyItem(i).getPrice();

        }

        System.out.printf("Subtotal %12s %6.2f", "$", subTotal);
    }
}
