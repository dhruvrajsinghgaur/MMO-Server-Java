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

    private static AtomicInteger Counter = new AtomicInteger(0);

    public ClientHandler(Socket socket, CopyOnWriteArrayList<ClientHandler> Clients, int ClientID, String ClientName, BufferedReader in, PrintWriter out) throws IOException {
        this.socket = socket;
        this.Clients = Clients;
        this.clientID = ClientID;
        this.clientName = ClientName;
        this.in = in;
        this.out = out;
    }

    @Override
    public void run() {
        try {
            String msg;

            while ((msg = in.readLine()) != null) {
                if (msg.equalsIgnoreCase("INC")) {
                    int newValue = Counter.incrementAndGet();
                    broadcast(clientName + " has increased counter to " + newValue + " by using the command " + msg);
                }
                else if (msg.equalsIgnoreCase("DEC")){
                    int newValue = Counter.decrementAndGet();
                    broadcast(clientName + " has decreased counter to " + newValue + " by using the command " + msg);
                }
                else {
                    sendMessage("invalid command");
                }
            }

        }catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            Clients.remove(this);
            try {
                broadcast(clientName + " has left the lobby");
            } catch (IOException ignore) {}
            try {
                socket.close();
            } catch (IOException ignore) {}
        }
    }

    // sends message to a specific client.
    public void sendMessage(String mgs) throws IOException {
        out.println(mgs);
    }

    // sends message to every client.
    public void broadcast(String msg) throws IOException {
        for (int i = 0; i < Clients.size(); i++) {
            ClientHandler Client = Clients.get(i);
            Client.sendMessage(msg);
        }
    }
}
