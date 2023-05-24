package ch.noseryoung.blj;


import java.util.ArrayList;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Player player = new Player("Player");
    Score score = new Score();


    Room livingRoom = new Room("Living Room", "You are in the living room.");
    Room kitchen = new Room("Kitchen", "You are in the kitchen.");
    Room bathroom = new Room("Bathroom", "You are in the bathroom.");
    Room bedroom = new Room("Bedroom", "You are in the bedroom.");

    livingRoom.addExit(kitchen);
    livingRoom.addExit(bathroom);
    livingRoom.addExit(bedroom);
    kitchen.addExit(livingRoom);
    bathroom.addExit(livingRoom);
    bedroom.addExit(livingRoom);

    livingRoom.addItem(new Item("TV_Remote", "A remote for the TV.", true));
    kitchen.addItem(new Item("Knife", "A sharp knife.", true));
    bathroom.addItem(new Item("Toothbrush", "A toothbrush.", true));
    bedroom.addItem(new Item("Key", "A key to unlock a door.", true));

    player.setCurrentRoom(livingRoom);

    System.out.println("Welcome!");

    while (true) {
      System.out.println("\n" + player.getCurrentRoom().getDescription());

      if (player.getCurrentRoom().getItems().size() > 0) {
        System.out.print("You see: ");
        for (Item item : player.getCurrentRoom().getItems()) {
          System.out.print(item.getName() + " ");
        }
        System.out.println();
      }

      System.out.println("What do you want to do?");
      String input = scanner.nextLine();

      if (input.startsWith("go")) {
        String destination = input.substring(3);

        boolean foundExit = false;
        for (Room exit : player.getCurrentRoom().getExits()) {
          if (exit.getName().equalsIgnoreCase(destination)) {
            player.setCurrentRoom(exit);
            System.out.println("You go to the " + exit.getName() + ".");
            foundExit = true;
            break;
          }
        }

        if (!foundExit) {
          System.out.println("There is no exit to the " + destination + ".");
        }
      } else if (input.startsWith("take")) {
        String itemName = input.split(" ")[1];

        for (Item item : player.getCurrentRoom().getItems()) {
          if (item.getName().equalsIgnoreCase(itemName)) {
            player.getCurrentRoom().removeItem(item);
            System.out.println("You take the " + itemName + ".");
            if (item.isUsable()) {
              score.increaseScore(10);
              System.out.println("You gain 10 points.");
            }
            player.getItems().add(item);
            break;
          }
        }
      } else if (input.startsWith("use")) {
        String itemName = input.split(" ")[1];

        for (Item item : player.getItems()) {
          if (item.getName().equalsIgnoreCase(itemName) && item.isUsable()) {
            System.out.println("You use the " + itemName + ".");
            score.increaseScore(20);
            System.out.println("You gain 20 points.");
            player.getItems().remove(item);
            break;
          }
        }
      } else if (input.equals("look")) {
        System.out.println(player.getCurrentRoom().getDescription());
        System.out.println("You can:");

        for (Room exit : player.getCurrentRoom().getExits()) {
          System.out.println("- go to the " + exit.getName());
        }

        for (Item item : player.getCurrentRoom().getItems()) {
          System.out.println("- take the " + item.getName());
        }

        boolean hasUsableItems = false;
        for (Item item : player.getItems()) {
          if (item.isUsable()) {
            System.out.println("- use the " + item.getName());
            for (Room exit : player.getCurrentRoom().getExits()) {
              if (item.getName().equals("Key") && exit.getLocked() && exit.getKeyCode() == item.getCode()) {
                System.out.println("  (can unlock the " + exit.getName() + ")");
              }
            }
            hasUsableItems = true;
          }
        }

        if (!hasUsableItems) {
          System.out.println("You don't have any usable items.");
        }
      } else if (input.equals("inventory")) {
        ArrayList<Item> items = player.getItems();
        if (items.isEmpty()) {
          System.out.println("Your inventory is empty.");
        } else {
          System.out.println("Your inventory contains:");
          for (Item item : items) {
            System.out.println("- " + item.getName());
          }
        }
      } else if (input.equals("score")) {
        System.out.println("Your score is " + score.getScore() + ".");
      } else if (input.equals("quit")) {
        System.out.println("Thanks for playing!");
        break;
      } else {
        System.out.println("Invalid input.");
      }

      if (player.getCurrentRoom().getName().equals("Kitchen") && player.getItems().contains(new Item("Knife", "", false))) {
        System.out.println("You win!");
        break;
      }

      if (score.getScore() < 0) {
        System.out.println("You lose!");
        break;
      }
    }

    scanner.close();
  }
}