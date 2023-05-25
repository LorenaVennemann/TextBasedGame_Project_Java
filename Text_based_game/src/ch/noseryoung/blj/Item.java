package ch.noseryoung.blj;


public class Item {
  private String name;
  private String description;
  private boolean usable;
  private int code;

  public Item(String name, String description, boolean usable) {
    this.name = name;
    this.description = description;
    this.usable = usable;
  }

  public Item(String name, String description, boolean usable, int code) {
    this.name = name;
    this.description = description;
    this.usable = usable;
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public boolean isUsable() {
    return usable;
  }

  public int getCode() {
    return code;
  }

  public boolean canUnlock(Room room) {
    if (name.equals("Key") && room.getLocked() && room.getKeyCode() == code) {
      return true;
    } else {
      return false;
    }
  }
  public void setCode(int code) {
    this.code = code;
  }
}
