import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private static BufferedReader clientInput;
    private static PrintWriter serverOutput;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        clientSocket = serverSocket.accept();
        serverOutput = new PrintWriter(clientSocket.getOutputStream(), true);
        clientInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server(3000);
        while (true) {
            System.out.println(clientInput.readLine());
        }
    }
}