import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ServidorDoJogo {
    public static void main(String[] args) {

        System.out.println("Digite a porta do servidor:");
        Scanner scanner = new Scanner(System.in);
        int PORT = scanner.nextInt();

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor iniciado. Conectado na porta " + PORT);

            // Espera dois jogadores se conectarem
            Socket player1Socket = serverSocket.accept();
            System.out.println("Jogador 1 conectado: " + player1Socket.getInetAddress());
            Socket player2Socket = serverSocket.accept();
            System.out.println("Jogador 2 conectado: " + player2Socket.getInetAddress());

            // Inicia um jogo entre os dois jogadores
            startGame(player1Socket, player2Socket);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void startGame(Socket player1Socket, Socket player2Socket) {
        try (
                BufferedReader player1In = new BufferedReader(new InputStreamReader(player1Socket.getInputStream()));
                PrintWriter player1Out = new PrintWriter(player1Socket.getOutputStream(), true);
                BufferedReader player2In = new BufferedReader(new InputStreamReader(player2Socket.getInputStream()));
                PrintWriter player2Out = new PrintWriter(player2Socket.getOutputStream(), true);
        ) {
            while (true) {
                // Recebe escolhas dos jogadores
                int player1Choice = Integer.parseInt(player1In.readLine());
                int player2Choice = Integer.parseInt(player2In.readLine());

                // Determina o resultado do jogo
                int result = getResult(player1Choice, player2Choice);

                // Envia o resultado para os jogadores
                player1Out.println(result);
                player2Out.println(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                player1Socket.close();
                player2Socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static int getResult(int choice1, int choice2) {
        // Lógica para determinar o resultado do jogo
        // 0 - Empate, 1 - Jogador 1 vence, 2 - Jogador 2 vence
        if (choice1 == choice2) {
            return 0; // Empate
        } else if ((choice1 == 0 && choice2 == 2) || (choice1 == 1 && choice2 == 0) || (choice1 == 2 && choice2 == 1)) {
            return 1; // Jogador 1 vence
        } else {
            return 2; // Jogador 2 vence
        }
    }
}
