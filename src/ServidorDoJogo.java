import java.io.*;
import java.net.*;
import java.util.*;

public class ServidorDoJogo {
    private static final int QTD_PLAYERS = 2;
    private static List<Socket> jogadores = new ArrayList<>();
    private static String[] nomes = new String[QTD_PLAYERS];

    public static void main(String[] args) {
        System.out.println("Digite a porta do servidor:");
        Scanner scanner = new Scanner(System.in);
        int porta = scanner.nextInt();

        try (ServerSocket servidorSocket = new ServerSocket(porta)) {
            System.out.println("Servidor iniciado. Conectado na porta " + porta);

            while (jogadores.size() < QTD_PLAYERS) {
                Socket jogadorSocket = servidorSocket.accept();
                jogadores.add(jogadorSocket);
                System.out.println("Jogador " + jogadores.size() + " conectado: " + jogadorSocket.getInetAddress());
            }

            iniciarJogo();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void iniciarJogo() {
        try {
            BufferedReader[] entradas = new BufferedReader[QTD_PLAYERS];
            PrintWriter[] saidas = new PrintWriter[QTD_PLAYERS];
            for (int i = 0; i < QTD_PLAYERS; i++) {
                Socket jogadorSocket = jogadores.get(i);
                entradas[i] = new BufferedReader(new InputStreamReader(jogadorSocket.getInputStream()));
                saidas[i] = new PrintWriter(jogadorSocket.getOutputStream(), true);
            }

            while (true) {
                // Recebe escolhas dos jogadores
                String[] mensagens = new String[QTD_PLAYERS];
                int[] escolhas = new int[QTD_PLAYERS];
                for (int i = 0; i < QTD_PLAYERS; i++) {
                    String mensagem = entradas[i].readLine();
                    String[] partes = mensagem.split(":");
                    nomes[i] = partes[0];
                    escolhas[i] = Integer.parseInt(partes[1]);
                    mensagens[i] = mensagem;
                }

                // Determina o resultado do jogo
                int resultado = obterResultado(escolhas);

                // Envia o resultado para os jogadores
                if (resultado == 0) {
                    for (int i = 0; i < QTD_PLAYERS; i++) {
                        saidas[i].println("EMPATE!");
                    }
                } else if (resultado == 1) {
                    saidas[0].println(nomes[0] + " VENCEU!");
                    saidas[1].println(nomes[1] + " PERDEU!");
                } else if (resultado == 2) {
                    saidas[1].println(nomes[1] + " VENCEU!");
                    saidas[0].println(nomes[0] + " PERDEU!");
                } else if (resultado == 3) {
                    saidas[0].println("ALGUEM ENCERROU A CONEXAO. REINICIE O JOGO");
                    saidas[1].println("ALGUEM ENCERROU A CONEXAO. REINICIE O JOGO");

                    System.exit(0);
                } else {
                    for (int i = 0; i < QTD_PLAYERS; i++) {
                        saidas[i].println("ALGUEM FEZ UMA JOGADA INVALIDA!");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int obterResultado(int[] escolhas) {
        if (escolhas[0] == escolhas[1] && escolhas[0] <= 2 && escolhas[1] <= 2) {
            return 0; // Empate
        } else if ((escolhas[0] == 0 && escolhas[1] == 2) || (escolhas[0] == 1 && escolhas[1] == 0) || (escolhas[0] == 2 && escolhas[1] == 1)) {
            return 1; // jogador 1 vence
        } else if ((escolhas[1] == 0 && escolhas[0] == 2) || (escolhas[1] == 1 && escolhas[0] == 0) || (escolhas[1] == 2 && escolhas[0] == 1)) {
            return 2; // jogador 2 vence
        } else if (escolhas[0] == 3 || escolhas[1] == 3) {
            return 3; // Fechar jogo
        } else {
            return 4; // Jogada fora do padrão
        }
    }
}
