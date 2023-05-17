package ch.noseryoung.blj;

import java.util.HashMap;
import java.util.Map;

public class Room {
  private String name_;
  private String[] neighbors_;
  private String description_;
  private HashMap<String, Item> items_ = new HashMap<String, Item>();

  public Room(String name, String description, String[] neighbors, HashMap<String, Item> items){
    name_ = name;
    description_ = description;
    neighbors_ = neighbors;
    
    setItems(items);
  }

  private void setItems(HashMap<String, Item> items) {
    for(Map.Entry<String, Item> elt : items.entrySet()){
      if (elt.getValue().getLocation().equals(name_)){
        items_.put(elt.getKey(), elt.getValue());
      }
      }
    }
  public void look() {
    System.out.println(description_);
    System.out.println("can exit to the ");

    if (!neighbors_[0].equals("-"))
      System.out.println("NORTH, ");

    if (!neighbors_[1].equals("-"))
      System.out.println("SOUTH, ");

    if (!neighbors_[2].equals("-"))
      System.out.println("EAST, ");

    if (!neighbors_[3].equals("-"))
      System.out.println("WEST, ");
  }
}



