package ejemplo_socketUDP;

import java.io.*;
import java.net.*;

public class SocketUDPServer {
	public static void main (String[] args) {
		DatagramSocket socket;
		
		try  { 
			System.out.println ("SERVIDOR: Creando socket ");
			socket = new DatagramSocket(49200);
			
			System.out.println ("SERVIDOR: Recibiendo datagrama ");
			byte[] bufferLectura = new byte [64];
			
			DatagramPacket datagramaEntrada = new DatagramPacket(bufferLectura, bufferLectura.length);
			socket.receive(datagramaEntrada);
			System.out.println ("SERVIDOR: Mensaje recibido:  "+ new String(bufferLectura));
			
			System.out.println ("SERVIDOR: Enviando datagrama ");
			byte[] mensajeEnviado = new String("Mensaje enviado desde el servidor").getBytes();
			DatagramPacket datagramaSalida = new DatagramPacket(mensajeEnviado, mensajeEnviado.length, datagramaEntrada.getAddress(),datagramaEntrada.getPort());
			socket.send(datagramaSalida);
			
			System.out.println ("SERVIDOR: Cerrando socket ");
			socket.close();
			System.out.println ("SERVIDOR: Socket cerrado ");
			
		} catch (SocketException e) {
			e.printStackTrace();
	    } catch (IOException e) {
			e.printStackTrace();
	    }
			
	 }			
}
