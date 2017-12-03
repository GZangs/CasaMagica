package CasaMagica;

import java.util.HashMap;

public class Room {

    private String description;
    private HashMap<String, Room> exits;
    private Item item;

    /**
     * Create a room described "description". Initially, it has no exits.
     * "description" is something like "a kitchen" or "an open court yard".
     *
     * @param description The room's description.
     * @param item
     */
    public Room(String description) {
        this.description = description;
        exits = new HashMap<>();
        this.item = null;
    }

    public Room(String description, Item item) {
        this(description);
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    /**
     * Define the exits of this room. Every direction either leads to another
     * room or is null (no exit there).
     *
     * @param direction A direção da saída.
     * @param neighbor A sala vizinha.
     */
    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    /**
     * @return The description of the room.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Retorna uma das saídas da sala, a partir de uma String.
     *
     * @param direction Uma string com a direção a retornar
     * @return A sala
     */
    public Room getExit(String direction) {
        return exits.get(direction);
    }

    /**
     * Retorna uma descrição das saídas da sala, por exemplo: "Saídas: norte
     * oeste".
     *
     * @return Uma descrição das saídas disponíveis
     */
    public String getExitString() {
        String exitString = "Saídas:";
        for (String exit : exits.keySet()) {
            exitString += " " + exit;
        }
        return exitString;
    }

    /**
     * Retorna uma descrição longa dessa sala. Na forma Você está na cozinha.
     * Saídas: norte oeste
     *
     * @return Uma descrição da sala, incluindo saídas.
     */
    public String getLongDescription() {
        String itemStr = (item != null)
                ? "Que contém " + item.getDescription() + ".\n"
                : "";

        return "Você está " + description + ".\n"
                + itemStr
                + getExitString();
    }
}
