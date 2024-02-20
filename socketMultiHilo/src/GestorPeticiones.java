import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;

class GestorPeticiones implements Runnable {
    private final Socket clienteSocket;

    public GestorPeticiones(Socket clienteSocket) {
        this.clienteSocket = clienteSocket;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = clienteSocket.getInputStream();
            OutputStream outputStream = clienteSocket.getOutputStream();

            // Generar un número aleatorio
            Random random = new Random();
            int tiempoEspera = random.nextInt(10) + 1; // Número aleatorio entre 1 y 10
            System.out.println("Esperando " + tiempoEspera + " segundos...");

            // Dormir el hilo durante el tiempo aleatorio
            Thread.sleep(tiempoEspera * 1000);

            // Enviar el número aleatorio al cliente
            outputStream.write(tiempoEspera);

            // Cerrar conexiones
            outputStream.close();
            inputStream.close();
            clienteSocket.close();

            System.out.println("Cliente atendido: " + clienteSocket);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
