import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Servidor {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(56800);
        System.out.println("Servidor esperando conexiones...");

        try {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Cliente conectado desde: " + clientSocket.getInetAddress());

            // Generar número aleatorio
            Random random = new Random();
            int numeroAdivinar = random.nextInt(100) + 1;

            // Configurar streams de entrada y salida
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

            // Comunicación con el cliente
            output.println("¡Bienvenido al juego de adivinanza!");
            output.println("Adivina un número entre 1 y 100.");

            String guess;
            int intento;
            boolean adivinado = false;

            do {
                guess = input.readLine();
                intento = Integer.parseInt(guess);

                if (intento > numeroAdivinar) {
                    output.println("El número a adivinar es menor.");
                } else if (intento < numeroAdivinar) {
                    output.println("El número a adivinar es mayor.");
                } else if (intento == numeroAdivinar){
                    output.println("¡Correcto! Has adivinado el número.");
                    adivinado = true;
                }
            } while (!adivinado);

        } finally {
            serverSocket.close();
        }
    }
}
