import java.io.IOException;
import java.net.Socket;

public class Cliente {

    public static void main(String[] args) {
        try {
            Socket clienteSocket = new Socket("localhost", 12345); // Conectar al servidor en el localhost y puerto 12345

            // Crear un hilo para gestionar las peticiones del cliente
            Thread hiloCliente = new Thread(new GestorPeticiones(clienteSocket));
            hiloCliente.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}