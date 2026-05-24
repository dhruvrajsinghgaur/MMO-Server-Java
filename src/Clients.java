import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Clients {
    public Clients() throws IOException {
        Socket socket = new Socket("localhost", 8080);
        Scanner scanner = new Scanner(System.in);


        System.out.println("Enter your name");
        String ClientName = scanner.nextLine();

        PrintWriter out = new PrintWriter(
                socket.getOutputStream(), true
        );

        out.println(ClientName);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
        );

        String ClientID = in.readLine();

        Thread readThread = new Thread(() -> {
            try {
                String msg;
                while ((msg = in.readLine()) != null) {
                    System.out.println(msg);
                }
                System.out.println("Srever Disconnected");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        readThread.start();

        Thread writeThread = new Thread(() -> {
            System.out.println("start commanding");
            while (true) {
                out.println(scanner.nextLine());
            }
        });
        writeThread.start();
    }

    public static void main(String[] args){
        try{
            new Clients();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
