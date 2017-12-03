package CasaMagica;

import java.util.Stack;

/**
 *  Esta é a classe principal do jogo Casa Mágica. 
 *  
 */

public class Game 
{
    private Parser parser;
    private Player player;
    private Stack<Room> previousRooms;
        
    /**
     * Cria o jogo e inicializa o mapa interno.
     */
    public Game() 
    {
        createPlayer();
        createRooms();
        previousRooms = new Stack<Room>();
        parser = new Parser();
    }

    /**
     * Cria todas as salas e liga suas saídas. Além disso seta a sala atual do jogador
     */
    private void createRooms()
    {
        Room livingRoom, kitchen, singleRoom, doubleRoom, bathroom, backyard, hall;
        Item tv, magicCookie, bed, wardrobe, brush, dog;
      
        // cria os itens
        tv = new Item("uma tv", 20, false);
        magicCookie = new Item("um biscoito mágico", 2, false);
        bed = new Item("uma cama", 50, false);
        wardrobe = new Item("um armário", 100, false);
        brush = new Item("uma escova", 5, false);
        dog = new Item("um cachorro", 20, false);
        
        // create the rooms
        livingRoom = new Room("na sala de estar", tv);
        kitchen = new Room("na cozinha", magicCookie);
        singleRoom = new Room("no quarto de solteiro", bed);
        doubleRoom = new Room("no quarto de casal", wardrobe);
        bathroom = new Room("no banheiro", brush);
        backyard = new Room("no quintal da casa", dog);
        hall = new Room("no corredor da casa");
        
        // initialise room exits
        backyard.setExit("norte", livingRoom);
        
        livingRoom.setExit("sul", backyard);
        livingRoom.setExit("norte", bathroom);
        livingRoom.setExit("oeste", hall);
        
        bathroom.setExit("sul", livingRoom);
        
        hall.setExit("leste", livingRoom);
        hall.setExit("norte", singleRoom);
        hall.setExit("sul", kitchen);
        hall.setExit("oeste", doubleRoom);
        
        singleRoom.setExit("sul", hall);
        
        kitchen.setExit("norte", hall);
        
        doubleRoom.setExit("leste", hall);
        
        if(player != null){
            player.setCurrentRoom(backyard);
        }
    }

    private void createPlayer(){
        player = new Player("Josnei", 100);
    }
    
    /**
     *  A rotina de jogo principal. Faz um loop até o fim do jogo.
     */
    public void play() 
    {            
        printWelcome();

        // Entra o loop principal. Aqui lemos comandos repetidamente e 
        // os executamos até que o jogo termime.
                
        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Obrigado por jogar.  Adeus.");
    }

    /**
     * Imprime a mensagem de boas vindas ao usuário.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Bem-vindo à Casa Mágica!");
        System.out.println("Parece uma casa normal, mas você descobrirá várias coisas estranhas...");
        System.out.println("Digite 'ajuda' se você precisar de ajuda.");
        System.out.println();
        
        printLocationInfo();
        
    }
    
    /**
     * Imprime informação do local atual.
     */
    private void printLocationInfo()
    {
        System.out.println(player.getCurrentRoom().getLongDescription());
    }

    /**
     * Dado um comando, processa (ou seja: executa) o comando.
     * @param command O comando a ser processado.
     * @return true Se o comando finaliza o jogo, senão, falso.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("Não sei o que você quer dizer...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("ajuda"))
            printHelp();
        else if (commandWord.equals("ir_para"))
            goRoom(command);
        else if (commandWord.equals("sair"))
            wantToQuit = quit(command);
        else if (commandWord.equals("examinar"))
            look();
        else if (commandWord.equals("comer"))
            eat();
        else if (commandWord.equals("voltar"))
            returnRoom(command);
        else if(commandWord.equals("itens"))
            printItems();
        else if(commandWord.equals("pegar"))
            pickItem();
        else if(commandWord.equals("largar"))
            dropItem();
        else if(commandWord.equals("comer_biscoito"))
            eatMagicCookie();
                    
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Imprime informações de ajuda.
     * Aqui imprimimos ua mensagem estúpida e listamos os comandos
     * disponíveis.
     */
    private void printHelp() {
        System.out.println("Você está perdido. Você está só. Você caminha");
        System.out.println("pela casa.");
        System.out.println();
        System.out.println("Seus comandos são:");
        System.out.println("   " + parser.getCommandList());
    }
    
    private void returnRoom(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Voltar o quê?");
        } else {
            if (previousRooms.empty()) {
                System.out.println("Você já está no início!");
            } else {
                Room previousRoom = previousRooms.pop();
                player.setCurrentRoom(previousRoom);

                printLocationInfo();
            }
        }         
    }
    
    /** 
     * Tenta ir para uma direção. Se há uma saída, entra na
     * nova sala, senão imprime uma mensagem de erro.
     */
    private void goRoom(Command command) {
        if(!command.hasSecondWord()) {
            // se não há segunda palavra, não sabemos onde ir...
            System.out.println("Ir para onde?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = player.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            System.out.println("Não há uma porta!");
        }
        else {
            previousRooms.push(player.getCurrentRoom());
            player.setCurrentRoom(nextRoom);
            
            printLocationInfo();
        }
    }

    /** 
     * "Sair" foi digitado. Verifica o resto do comando para saber
     * se o usuário quer realmente sair do jogo.
     * @return true, se este comando sair do jogo, falso caso contrário.
     */
    private boolean quit(Command command) {
        if(command.hasSecondWord()) {
            System.out.println("Sair de do quê?");
            return false;
        }
        else {
            return true;  // significa que queremos sair
        }
    }

    private void look() {
        printLocationInfo();
    }
    
    public void eat() {
        System.out.println("Você comeu e agora não está mais com fome.");
    }
    
    public void printItems(){
        System.out.println(player.getItemsDescription());
    }
    
    /**
     * Pega o item caso não ultrapasse o peso máximo
    */
    public void pickItem(){
        int totalWeight = player.getItemsWeight();
        int itemWeight = player.getCurrentRoom().getItem().getWeight();
        
        if(totalWeight + itemWeight > player.getMaxWeight()){
            System.out.println("O jogador " + player.getName() + " não consegue carregar esse item.");
        }else{
            Item itemToPick = player.getCurrentRoom().getItem();
            itemToPick.setIsWithPlayer(true);
            player.addItem(itemToPick);
            
            System.out.println("Agora o jogador " + player.getName() + " está carregando " + itemToPick.getDescription() + ".");
        }
    }
    
    /**
     * Deixa de carregar o item caso estiver carregando
     */
    public void dropItem(){
        Item item = player.getCurrentRoom().getItem();
        
        if(player.getItems().isEmpty()){
            System.out.println("O jogador " + player.getName() + " não está carregando nenhum item.");
        } else if(!item.isIsWithPlayer()){
            System.out.println("O jogador " + player.getName() + " não está carregando esse item.");
        } else {
            player.getItems().remove(item);
            System.out.println("O jogador " + player.getName() + " não está mais carregando " + item.getDescription());
        }
    }
    
    /**
     * Tenta comer um biscoito mágico. Caso consiga o peso que o jogador consegue carregar é aumentado
     */
    private void eatMagicCookie(){
        Item item = player.getCurrentRoom().getItem();
        
        if(!item.getDescription().contains("biscoito mágico")){
            System.out.println("Isso não é um biscoito mágico o_O");
        } else {
            player.setMaxWeight(player.getMaxWeight() + 50);
            System.out.println("Você comeu um biscoito mágico! Agora você consegue carregar mais itens.");
            System.out.println("O peso máximo que você pode carregar é " + player.getMaxWeight());
        }
    }
}
