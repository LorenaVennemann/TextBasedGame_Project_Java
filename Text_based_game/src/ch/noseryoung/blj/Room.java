package ch.noseryoung.blj;

import java.util.ArrayList;

public class Room {
  private String name;
  private String description;
  private boolean locked;

  private  int keyCode;
  private ArrayList<Room> exits;
  private ArrayList<Item> items;

  public Room(String name, String description) {
    this.name = name;
    this.description = description;
    this.locked = false;
    this.exits = new ArrayList<>();
    this.items = new ArrayList<>();
  }

  public Room(String name, String description, boolean locked, int keyCode) {
    this.name = name;
    this.description = description;
    this.locked = locked;
    this.keyCode = keyCode;
    this.exits = new ArrayList<>();
    this.items = new ArrayList<>();
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public boolean getLocked() {
    return locked;
  }

  public int getKeyCode() {
    return keyCode;
  }

  public ArrayList<Room> getExits() {
    return exits;
  }

  public void addExit(Room room) {
    exits.add(room);
  }

  public ArrayList<Item> getItems() {
    return items;
  }

  public void addItem(Item item) {
    items.add(item);
  }

  public void removeItem(Item item) {
    items.remove(item);
  }

  public boolean canUnlock(Item item) {
    return item.canUnlock(this);
  }

  public void setLocked(boolean locked) {
    this.locked = locked;
  }

  public void setKeyCode(int code) {
    this.keyCode = code;
  }
}