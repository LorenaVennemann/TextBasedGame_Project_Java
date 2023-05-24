package ch.noseryoung.blj;
import java.util.ArrayList;

public class Player {
  private String name;
  private Room currentRoom;
  private ArrayList<Item> items;

  public Player(String name) {
    this.name = name;
    this.items = new ArrayList<>();
  }

  public void setCurrentRoom(Room room) {
    this.currentRoom = room;
  }

  public Room getCurrentRoom() {
    return this.currentRoom;
  }

  public String getName() {
    return this.name;
  }

  public void addItem(Item item) {
    this.items.add(item);
  }

  public void removeItem(Item item) {
    this.items.remove(item);
  }

  public ArrayList<Item> getItems() {
    return this.items;
  }
}