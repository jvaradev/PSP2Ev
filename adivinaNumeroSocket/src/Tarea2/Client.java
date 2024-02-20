package Tarea2;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        try {
            boolean primero = true;

            Socket socket = new Socket("172.17.203.48", 49200);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println(reader.readLine());
            System.out.println("El servidor a generado el numero que hay que adivinar "+"Un numero entre 1 y 10");

            while (true) {
                if (!primero) {
                    System.out.print("Ingrese su numero: ");
                    String guess = consoleReader.readLine();
                    writer.println(guess);

                    String response = reader.readLine();
                    System.out.println(response);

                    if (response.contains("Felicidades")) {
                        break;
                    }
                } else {
                    primero = false;
                }
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}