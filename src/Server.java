import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server{
    public int ClientID = 1;
    public Server() throws IOException {

        ServerSocket server = new ServerSocket(8080);

        CopyOnWriteArrayList<ClientHandler> ClientsConnected = new CopyOnWriteArrayList<>();

        //HashMap<Integer, String> ClientNameClientID = new HashMap<>();

        System.out.println("Server Started");
        System.out.println("Waiting for Clients...");

        while (true) {

            Socket socket = server.accept();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            String ClientName = in.readLine();
            System.out.println(ClientName + " has joined the lobby");

            PrintWriter out = new PrintWriter(
                    socket.getOutputStream(),true
            );

            out.println(ClientID);

            ClientHandler handler = new ClientHandler(socket, ClientsConnected, ClientID, ClientName, in, out);

            ClientsConnected.add(handler);

            ClientID++;

            Thread thread = new Thread(handler);

            thread.start();
        }
    }
    public static void main (String[] args){
        try {
            new Server();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
