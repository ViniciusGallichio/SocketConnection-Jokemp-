import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteDois {

    public static void main(String[] args) {
        System.out.println("Digite o endereço IP do servidor: ");
        Scanner scanner = new Scanner(System.in);
        String enderecoServidor = scanner.nextLine();

        System.out.println("Digite a porta do servidor: ");
        int portaServidor = scanner.nextInt();

        System.out.println("    _____          __                                               \n" +
                "   |     \\        |  \\                                              \n" +
                "    \\▓▓▓▓▓ ______ | ▓▓   __  ______  ______ ____   ______   ______  \n" +
                "      | ▓▓/      \\| ▓▓  /  \\/      \\|      \\    \\ /      \\ /      \\ \n" +
                " __   | ▓▓  ▓▓▓▓▓▓\\ ▓▓_/  ▓▓  ▓▓▓▓▓▓\\ ▓▓▓▓▓▓\\▓▓▓▓\\  ▓▓▓▓▓▓\\  ▓▓▓▓▓▓\\\n" +
                "|  \\  | ▓▓ ▓▓  | ▓▓ ▓▓   ▓▓| ▓▓    ▓▓ ▓▓ | ▓▓ | ▓▓ ▓▓  | ▓▓ ▓▓  | ▓▓\n" +
                "| ▓▓__| ▓▓ ▓▓__/ ▓▓ ▓▓▓▓▓▓\\| ▓▓▓▓▓▓▓▓ ▓▓ | ▓▓ | ▓▓ ▓▓__/ ▓▓ ▓▓__/ ▓▓\n" +
                " \\▓▓    ▓▓\\▓▓    ▓▓ ▓▓  \\▓▓\\\\▓▓     \\ ▓▓ | ▓▓ | ▓▓ ▓▓    ▓▓\\▓▓    ▓▓\n" +
                "  \\▓▓▓▓▓▓  \\▓▓▓▓▓▓ \\▓▓   \\▓▓ \\▓▓▓▓▓▓▓\\▓▓  \\▓▓  \\▓▓ ▓▓▓▓▓▓▓  \\▓▓▓▓▓▓ \n" +
                "                                                 | ▓▓               \n" +
                "                                                 | ▓▓               \n" +
                "                                                  \\▓▓               \n");

        System.out.println("No Jokempô, cada jogador escolhe entre Pedra (0), Papel (1) ou Tesoura (2). " +
                "Pedra vence Tesoura, Tesoura vence Papel e Papel vence Pedra. " +
                "\nOs jogadores digitam o número correspondente à sua escolha e o vencedor é determinado " +
                "com base nessas regras");

        try (
                Socket soquete = new Socket(enderecoServidor, portaServidor);
                BufferedReader entrada = new BufferedReader(new InputStreamReader(soquete.getInputStream()));
                PrintWriter saida = new PrintWriter(soquete.getOutputStream(), true);
                Scanner entradaUsuario = new Scanner(System.in);
        ) {
            while (true) {
                System.out.println("\nEscolha: 0 - Pedra, 1 - Papel, 2 - Tesoura");
                int escolha = entradaUsuario.nextInt();

                saida.println(escolha);

                int resultado = Integer.parseInt(entrada.readLine());

                if (resultado == 0) {
                    System.out.println("\nEMPATE!");
                } else if (resultado == 2) {
                    System.out.println("\nVOCÊ VENCEU!");
                } else {
                    System.out.println("\nVOCÊ PERDEU!");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

