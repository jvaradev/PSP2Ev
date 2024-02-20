import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Servidor {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345); // Puerto del servidor
            System.out.println("Servidor esperando conexiones...");

            while (true) {
                Socket clienteSocket = serverSocket.accept(); // Esperar conexión de un cliente
                System.out.println("Cliente conectado: " + clienteSocket);

                // Crear un hilo para atender al cliente
                Thread hiloCliente = new Thread(new GestorPeticiones(clienteSocket));
                hiloCliente.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}