import java.io.*;
import java.net.*;
import java.util.Scanner;


public class ClienteDois {

    public static void main(String[] args) {
        System.out.println("Digite o endereço IP do servidor:");
        Scanner scanner = new Scanner(System.in);
        String serverAddress = scanner.nextLine();

        System.out.println("Digite a porta do servidor:");
        int serverPort = scanner.nextInt();

        try (
                Socket socket = new Socket(serverAddress, serverPort);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                Scanner userInput = new Scanner(System.in);
        ) {
            while (true) {
                System.out.println("Escolha: 0 - Pedra, 1 - Papel, 2 - Tesoura");
                int choice = userInput.nextInt();

                out.println(choice);

                int result = Integer.parseInt(in.readLine());

                if (result == 0) {
                    System.out.println("Empate!");
                } else if (result == 2) {
                    System.out.println("Você venceu!");
                } else {
                    System.out.println("Você perdeu!");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
