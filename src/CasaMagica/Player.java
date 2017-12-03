/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CasaMagica;

import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author Gabriel
 */
public class Player {
    private String name;
    private Room currentRoom;
    private ArrayList<Item> items;
    private int maxWeight;

    public Player(){
        items = new ArrayList<Item>();
    }

    public Player(String name, int maxWeight){
        this.name = name;
        this.maxWeight = maxWeight;
        items = new ArrayList<Item>();
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }
    
    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(int maxWeight) {
        this.maxWeight = maxWeight;
    }
    
    public void addItem(Item item){
        items.add(item);
    }
    
    public int getItemsWeight(){
        int weight = 0;
        
        for(Item item : items){
            weight += item.getWeight();
        }
        
        return weight;
    }
    
    public String getItemsDescription(){
        if(items.isEmpty()){
            return "O jogador " + name + " não está carregando nenhum item!";
        }
        
        String str = "O jogador " + name + " está carregando:";
        
        for(Item item : items){
            str += "\n- " + item.getLongDescription();
        }
        
        str += "\nPeso total: " + getItemsWeight();
        
        return str;
    }
}