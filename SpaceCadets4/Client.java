import java.io.*;
import java.net.Socket;
public class Client {
    private static Socket clientSocket;
    private static PrintWriter clientOutput;
    private static BufferedReader clientInput;
    public Client(String ip, int port) throws IOException{
        clientSocket = new Socket(ip, port);
        clientOutput = new PrintWriter(clientSocket.getOutputStream(), true);
        clientInput = new BufferedReader(new InputStreamReader(System.in));
    }

    public void shutClient() throws IOException {
        clientSocket.close();
    }

    public String sendMessage() throws IOException {
        return clientInput.readLine();
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client("0.0.0.0", 3000);
        while (true) {
            System.out.println("Enter your message or press enter to exit");
            String message = clientInput.readLine();
            clientOutput.println(message);
            if (message.equals("exit")) {
                System.exit(130);
            }
        }
    }
}
