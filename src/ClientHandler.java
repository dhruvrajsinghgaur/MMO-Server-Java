import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ClientHandler implements Runnable {
    public Socket socket;
    public CopyOnWriteArrayList<ClientHandler> Clients;
    public int clientID;
    public String clientName;
    private BufferedReader in;
    private PrintWriter out;

    public Player player;
    public Room currentRoom;

    //private static AtomicInteger Counter = new AtomicInteger(0);

    public ClientHandler(Socket socket, CopyOnWriteArrayList<ClientHandler> Clients, int ClientID, String ClientName, BufferedReader in, PrintWriter out) throws IOException {
        this.socket = socket;
        this.Clients = Clients;
        this.clientID = ClientID;
        this.clientName = ClientName;
        this.in = in;
        this.out = out;

        this.player = new Player();
        this.currentRoom = GameWorld.getInstance().startingRoom;
        currentRoom.clientsInTheRoom.add(this);
    }

    @Override
    public void run() {
        try {
            String msg;
            while ((msg = in.readLine()) != null) {
                handleCommand(msg);
            }

        }catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            currentRoom.clientsInTheRoom.remove(this);
            Clients.remove(this);
                broadcast(clientName + " has left the lobby");
            try {
                socket.close();
            } catch (IOException ignore) {}
        }
    }

    // sends message to a specific client.
    public void sendMessage(String mgs){
        out.println(mgs);
    }

    // sends message to every client.
    public void broadcast(String msg) {
        for (int i = 0; i < Clients.size(); i++) {
            ClientHandler Client = Clients.get(i);
            Client.sendMessage(msg);
        }
    }

    public void handleCommand(String msg){

        String[] str = msg.split(" ", 2);

        if (!player.isAlive()){
            if (msg.equalsIgnoreCase("RESPAWN")){
                player.hp = player.maxHp/2;
                currentRoom.clientsInTheRoom.remove(this);
                currentRoom = GameWorld.getInstance().startingRoom;
                currentRoom.clientsInTheRoom.add(this);
                sendMessage("You respawned at " + currentRoom.name + " with " + player.hp + " HP!");
                sendMessage("=== " + currentRoom.name + " ===\n" + currentRoom.description + "\n" + "Exits: " + currentRoom.getExit());
                broadcast(clientName + " has respawned");
            }
            else {
                sendMessage("You're Dead!! Type RESPAWN to respawn.");
            }
        }

        else if (msg.equalsIgnoreCase("Look")) {
            sendMessage("=== " + currentRoom.name + " ===\n"
                    + currentRoom.description + "\n"
                    + "Exits: " + currentRoom.getExit() + "\n"
                    + currentRoom.getPlayerList()
            );
        }

        else if (msg.equalsIgnoreCase("WHO")){
            StringBuilder who = new StringBuilder("====ONLINE PLAYERS====\n");
            for (int i = 0; i < Clients.size(); i++) {
                ClientHandler c = Clients.get(i);
                who.append(c.clientName)
                        .append(" | Room: ").append(c.currentRoom.name)
                        .append(" | HP: ").append(c.player.hp)
                        .append("\n");
            }
            sendMessage(who.toString());
        }

        else if (str[0].equalsIgnoreCase("Attack")){
            if (str.length < 2 || str[1].isEmpty()) {
                sendMessage("Enter proper name");;
                return;
            }
            boolean found = false;
            for (int i = 0; i < currentRoom.clientsInTheRoom.size(); i++) {
                ClientHandler clientDamaged = currentRoom.clientsInTheRoom.get(i);
                if (str[1].equalsIgnoreCase(clientDamaged.clientName)){
                    found = true;
                    if (clientDamaged == this){
                        sendMessage("You can't Attack yourself!!");
                        return;
                    }
                    int damage = clientDamaged.player.takeDamage(this.player.attack, this.player.armorDamage);
                    broadcast(clientName + " attacked " + clientDamaged.clientName
                            + " for " + damage + " damage!");
                    if (!clientDamaged.player.isAlive()){
                        broadcast(clientDamaged.clientName + "is killed by " + clientName + " !");
                    }
                }

            }
            if (!found) sendMessage("Player not found in this room!!");
        }

        else if (msg.equalsIgnoreCase("NORTH")) move(currentRoom.north);

        else if (msg.equalsIgnoreCase("SOUTH")) move(currentRoom.south);

        else if (msg.equalsIgnoreCase("WEST")) move(currentRoom.west);

        else if (msg.equalsIgnoreCase("EAST")) move(currentRoom.east);

        else if (str[0].equalsIgnoreCase("SAY")){
            if (str.length < 2 || str[1].isEmpty()) {
                sendMessage("Enter proper message");
                return;
            }
            broadcast(clientName + ": " + str[1]);
        }

        else if (msg.equalsIgnoreCase("STATS")) sendMessage(player.getStats());

        else sendMessage("invalid command");
    }

    public void move(Room room){
        if (room != null){
            currentRoom.clientsInTheRoom.remove(this);
            currentRoom = room;
            currentRoom.clientsInTheRoom.add(this);
            sendMessage("Now you are in \n");
            sendMessage("=== " + currentRoom.name + " ===\n"
                    + currentRoom.description + "\n"
                    + "Exits: " + currentRoom.getExit() + "\n"
                    + currentRoom.getPlayerList()
            );
        }
        else {
            sendMessage("No Exit in North" + "\n" + "Exits Available: " + currentRoom.getExit());
        }
    }
}
