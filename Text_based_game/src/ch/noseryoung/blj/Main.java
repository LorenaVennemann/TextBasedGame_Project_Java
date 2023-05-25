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
    Room diningroom = new Room("diningroom", "You are in the diningroom.");
    Room outside = new Room("Outside", "You are outside the house. Congratulations, you won!");

    livingRoom.addExit(kitchen);
    livingRoom.addExit(bathroom);
    livingRoom.addExit(bedroom);
    kitchen.addExit(livingRoom);
    kitchen.addExit(diningroom);
    bathroom.addExit(livingRoom);
    bedroom.addExit(livingRoom);
    diningroom.addExit(kitchen);
    kitchen.addExit(outside);

    kitchen.setLocked(true);
    outside.setLocked(true);
    Item key = new Item("Key", "A key to unlock a door.", true);
    key.setCode(1);
    kitchen.setKeyCode(key.getCode());
    outside.setKeyCode(key.getCode());

    livingRoom.addItem(new Item("TV_Remote", "A remote for the TV.", true));
    kitchen.addItem(new Item("Knife", "A sharp knife.", true));
    bathroom.addItem(new Item("Toothbrush", "A toothbrush.", true));
    bedroom.addItem(key);
    diningroom.addItem(new Item("Apple", "A Apple, you can eat it.", true));

    player.setCurrentRoom(livingRoom);

    System.out.println("\n" +
        "   ____      _      __  __  U _____ u \n" +
        "U /\"___|uU  /\"\\  uU|' \\/ '|u\\| ___\"|/ \n" +
        "\\| |  _ / \\/ _ \\/ \\| |\\/| |/ |  _|\"   \n" +
        " | |_| |  / ___ \\  | |  | |  | |___   \n" +
        "  \\____| /_/   \\_\\ |_|  |_|  |_____|  \n" +
        "  _)(|_   \\\\    >><<,-,,-.   <<   >>  \n" +
        " (__)__) (__)  (__)(./  \\.) (__) (__) \n");
    System.out.println("Your goal is to find the key and escape the house");
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
            if (exit.getLocked()) {
              Item keyInInventory = null;
              for (Item item : player.getItems()) {
                if (item.getName().equals("Key") && item.getCode() == exit.getKeyCode()) {
                  keyInInventory = item;
                  break;
                }
              }
              if (keyInInventory != null) {
                exit.setLocked(false);
                System.out.println("You unlock the " + exit.getName() + " with the " + keyInInventory.getName() + ".");
              } else {
                System.out.println("The " + exit.getName() + " is locked. You need a key to unlock it.");
                continue;
              }
            }
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

            if (itemName.equalsIgnoreCase("Key")) {
              for (Room exit : player.getCurrentRoom().getExits()) {
                if (exit.getName().equals("Outside") && exit.getLocked() && item.getCode() == exit.getKeyCode()) {
                  exit.setLocked(false);
                  System.out.println("You unlock the " + exit.getName() + " with the " + itemName + ".");
                  System.out.println("You win!");
                  System.exit(0);
                }
              }
            }
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

      if (player.getCurrentRoom().getName().equals("Outside")) {
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
