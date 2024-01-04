import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.io.FileOutputStream;
import java.io.IOException;

public class InventoryManagement {

  private ArrayList<Item> database;
  private int numberOfItems;
  private double sales;
  Scanner scnr;

  public InventoryManagement() {
    this.numberOfItems = 0;
    this.database = new ArrayList<Item>();
    this.sales = 0;
    this.scnr = new Scanner(System.in);
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
    boolean valid = false;
    while (!valid) {
      System.out.println("What is the id of the item you wish to search for? (0 to quit)");
      int id = scnr.nextInt();
      scnr.nextLine();
      if (id == 0) {
        break;
      }
      for (Item i : this.database) {
        if (i.id == id) {
          System.out.println(i.toFormattedString());
          valid = true;
          System.out.println("Press enter to continue.");
          String s = scnr.nextLine();
          break;
        }
      }
      if (valid) {
        break;
      } else {
        System.out.println("Invalid id, please try again.");
      }
    }
  }

  public void manageInventory() {
    boolean valid = true;
    while (valid) {
      System.out.println(
          "What would you like to do?\n1: Add new item\n2: Restock item\n3: Item sold\n4: Remove item\n5: Sort inventory\n6: Back");
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
          break;
        case 4:
          removeItem();
          break;
        case 5:
          sortInventory();
          break;
        case 6:
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
    double price = scnr.nextDouble();
    scnr.nextLine();
    System.out.println("What is the quantity of the new item?");
    int quantity = scnr.nextInt();
    scnr.nextLine();
    this.database.add(new Item(name, id, price, quantity));
    saveDatabase();
  }

  public void restock() {
    boolean valid = false;
    while (!valid) {
      System.out.println("What is the id of the item you wish to restock? (0 to quit)");
      int id = scnr.nextInt();
      scnr.nextLine();
      if (id == 0) {
        break;
      }
      for (Item i : this.database) {
        if (i.id == id) {
          System.out.println("How many more of this item have been added?");
          int quantity = scnr.nextInt();
          scnr.nextLine();
          i.quantity += quantity;
          valid = true;
          saveDatabase();
          break;
        }
      }
      if (valid) {
        break;
      } else {
        System.out.println("Invalid id, please try again.");
      }
    }

  }

  public void sellItem() {
    boolean valid = false;
    while (!valid) {
      System.out.println("What is the id of the item you wish to sell? (0 to quit)");
      int id = scnr.nextInt();
      scnr.nextLine();
      if (id == 0) {
        break;
      }
      for (Item i : this.database) {
        if (i.id == id) {
          System.out.println("How many of this item have been sold?");
          int quantity = scnr.nextInt();
          scnr.nextLine();
          i.quantity -= quantity;
          this.sales += quantity * i.price;
          valid = true;
          saveDatabase();
          break;
        }
      }
      if (valid) {
        break;
      } else {
        System.out.println("Invalid id, please try again.");
      }
    }
  }

  public void removeItem() {
    boolean valid = false;
    while (!valid) {
      System.out.println("What is the id of the item you wish to remove items of? (0 to quit)");
      int id = scnr.nextInt();
      scnr.nextLine();
      if (id == 0) {
        break;
      }
      for (Item i : this.database) {
        if (i.id == id) {
          System.out.println("How many of this item have been removed?");
          int quantity = scnr.nextInt();
          scnr.nextLine();
          i.quantity -= quantity;
          valid = true;
          saveDatabase();
          break;
        }
      }
      if (valid) {
        break;
      } else {
        System.out.println("Invalid id, please try again.");
      }
    }
  }

  public void printInventory() {
    System.out.println("Total sales: $" + String.format("%.2f", this.sales));
    for (Item item : this.database) {
      if (item.quantity > 0) {
        System.out.println(item.toFormattedString());
      }
    }
    System.out.println("Press enter to continue.");
    String s = scnr.nextLine();

  }

  public void sortInventory() {
    System.out.println("Sort by what? (0 to quit):\n1: Name\n2: Id number\n3: Price\n4: Quantity");
    boolean valid = false;
    int criteria = scnr.nextInt();
    scnr.nextLine();
    while (!valid) {
      switch (criteria) {
        case 0:
          valid = true;
          break;
        case 1:
          Collections.sort(this.database, (i1, i2) -> i1.name.compareTo(i2.name));
          valid = true;
          saveDatabase();
          break;
        case 2:
          Collections.sort(this.database, (i1, i2) -> Integer.compare(i1.id, i2.id));
          valid = true;
          saveDatabase();
          break;
        case 3:
          Collections.sort(this.database, (i1, i2) -> Double.compare(i1.price, i2.price));
          valid = true;
          saveDatabase();
          break;
        case 4:
          Collections.sort(this.database, (i1, i2) -> Integer.compare(i2.quantity, i1.quantity));
          valid = true;
          saveDatabase();
          break;
        default:
          break;
      }
    }
  }

  public void saveDatabase() {
    String fileName = "inventory.csv";
    try {
      boolean fileExists = new File(fileName).exists();

      try (FileOutputStream overwrite = new FileOutputStream(fileName, false);
          FileOutputStream append = new FileOutputStream(fileName, true);
          DataOutputStream dataStream = new DataOutputStream(append)) {

        byte[] headerBytes = "name,id,price,quantity\nsales:".getBytes();
        overwrite.write(headerBytes);

        dataStream.write(("" + String.format("%.2f", this.sales) + "\n").getBytes());

        for (Item i : this.database) {
          String output =
              i.name + "," + i.id + "," + String.format("%.2f", i.price) + "," + i.quantity + "\n";
          append.write(output.getBytes());
        }

        System.out.println("Data overwritten in the file.");
      } catch (IOException e) {
        e.printStackTrace();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
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

  public String toFormattedString() {
    String output = "";
    output += this.name + " (id: " + this.id + "), Price: $" + String.format("%.2f", this.price)
        + ", Quantity: " + this.quantity;
    return output;
  }
}
