/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CasaMagica;

public class Item {

    private String description;
    private int weight;
    private boolean isWithPlayer;

    public Item(String description, int weight, boolean isWithPlayer) {
        this.description = description;
        this.weight = weight;
        this.isWithPlayer = isWithPlayer;
    }

    public String getDescription() {
        return description;
    }

    public String getLongDescription() {
        return description + " que pesa " + weight;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean isIsWithPlayer() {
        return isWithPlayer;
    }

    public void setIsWithPlayer(boolean isWithPlayer) {
        this.isWithPlayer = isWithPlayer;
    }
}
