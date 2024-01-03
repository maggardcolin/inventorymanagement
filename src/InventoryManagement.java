import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class InventoryManagement {

  private ArrayList<Item> database;
  private int numberOfItems;
  private double sales;
  Scanner scnr;
  private ArrayList<Integer> changelog;

  public InventoryManagement() {
    this.numberOfItems = 0;
    this.database = new ArrayList<Item>();
    this.sales = 0;
    this.scnr = new Scanner(System.in);
    this.changelog = new ArrayList<Integer>();
  }

  public void menuLoop() {
    System.out.println("Welcome to the inventory management system.\nCurrent number of items: "
        + this.numberOfItems);
    boolean valid = true;
    while (valid) {
      System.out.println(
          "What would you like to do?\n1: Print inventory\n2: Search inventory\n3: Manage inventory\n4: Quit");
      int input = scnr.nextInt();
      scnr.nextLine();
      switch (input) {
        case 1:
          printInventory();
          break;
        case 2:
          searchInventory();
          break;
        case 3:
          manageInventory();
          break;
        case 4:
          valid = false;
          saveDatabase();
          break;
        default:
          break;
      }
    }
  }

  public void searchInventory() {

  }

  public void manageInventory() {
    boolean valid = true;
    while (valid) {
      System.out.println(
          "What would you like to do?\n1: Add new item\n2: Restock item\n3: Item sold\n4: Remove item\n5: Back");
      int input = scnr.nextInt();
      scnr.nextLine();
      switch (input) {
        case 1:
          addNewItem();
          break;
        case 2:
          restock();
          break;
        case 3:
          sellItem();
        case 4:
          removeItem();
        case 5:
          valid = false;
          break;
        default:
          break;
      }
    }
  }

  public void addNewItem() {
    System.out.println("What is the name of the new item?");
    String name = scnr.nextLine();
    System.out.println("What is the id number of the new item?");
    int id = scnr.nextInt();
    scnr.nextLine();
    System.out.println("What is the price of the new item?");
    double price = scnr.nextInt();
    scnr.nextLine();
    System.out.println("What is the quantity of the new item?");
    int quantity = scnr.nextInt();
    scnr.nextLine();
    this.database.add(new Item(name, id, price, quantity));
  }

  public void restock() {
    boolean valid = false;
    while (!valid) {
      System.out.println("What is the id of the item you wish to restock?");
      int id = scnr.nextInt();
      scnr.nextLine();
      for (Item i: this.database) {
        if 
      }
    }

  }

  public void sellItem() {

  }

  public void removeItem() {

  }

  public void printInventory() {
    System.out.println("Total sales: $" + this.sales);
    for (Item item : this.database) {
      if (item.quantity > 0) {
        System.out.println(item.name + " (" + item.id + "), Price: $"
            + String.format("%.2f", item.price) + ", Quantity: " + item.quantity);
      }
    }
    System.out.println("Press enter to continue.");
    String s = scnr.nextLine();

  }

  public void sortInventory(int criteria) {
    switch (criteria) {
      case 0:
        Collections.sort(this.database, (i1, i2) -> i1.name.compareTo(i2.name));
        break;
      case 1:
        Collections.sort(this.database, (i1, i2) -> Integer.compare(i1.id, i2.id));
        break;
      case 2:
        Collections.sort(this.database, (i1, i2) -> Double.compare(i1.price, i2.price));
        break;
      case 3:
        Collections.sort(this.database, (i1, i2) -> Integer.compare(i1.quantity, i2.quantity));
        break;
    }
  }

  public void saveDatabase() {

  }

  public static void main(String[] args) {
    Scanner fileScnr = new Scanner(System.in);
    try {
      String filename = "inventory.csv";
      File file = new File(filename);
      fileScnr = new Scanner(file);
      InventoryManagement inventory = new InventoryManagement();
      fileScnr.nextLine();
      String[] salesLine = fileScnr.nextLine().split(":");
      inventory.sales = Double.parseDouble(salesLine[1]);
      while (fileScnr.hasNextLine()) {
        String[] contents = fileScnr.nextLine().split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        inventory.database.add(new Item(contents[0], Integer.parseInt(contents[1]),
            Double.parseDouble(contents[2]), Integer.parseInt(contents[3])));
        inventory.numberOfItems++;
      }
      inventory.menuLoop();
    } catch (FileNotFoundException e) {
      System.out.println("File not found, inventory system boot failed.");
    } finally {
      fileScnr.close();
    }
  }
}


class Item {

  protected String name;
  protected int id;
  protected double price;
  protected int quantity;

  public Item(String name, int id, double price, int quantity) {
    this.name = name;
    this.id = id;
    this.price = price;
    this.quantity = quantity;
  }
}
