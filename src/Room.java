
import java.util.concurrent.CopyOnWriteArrayList;

public class Room{
    String name;
    String description;
    Room north;
    Room south;
    Room west;
    Room east;
    CopyOnWriteArrayList<ClientHandler> clientsInTheRoom = new CopyOnWriteArrayList<>();

    public Room(String name, String description){
        this.name = name;
        this.description = description;
    }

    public void broadcast(String msg){
        for (int i = 0; i < clientsInTheRoom.size(); i++) {
            clientsInTheRoom.get(i).sendMessage(msg);
        }
    }
    public String getPlayerList(){
        StringBuilder sb = new StringBuilder("Players here: ");
        for (int i = 0; i < clientsInTheRoom.size(); i++) {
            sb.append(clientsInTheRoom.get(i).clientName).append(", ");
        }
        return sb.toString();
    }

    public String getExit(){
        StringBuilder sb = new StringBuilder();
        if (north != null) sb.append("North ");
        if (south != null) sb.append("South ");
        if (west != null) sb.append("West ");
        if (east != null) sb.append("East ");
        if (sb.toString().isEmpty()) return "No Exits";
        return sb.toString();
    }
}
