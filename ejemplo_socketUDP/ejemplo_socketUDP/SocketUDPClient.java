package ejemplo_socketUDP;

import java.io.*;
import java.net.*;

public class SocketUDPClient {
	
	public static void main (String[] args) {
		String strMensaje = "Mensaje enviado desde el cliente";
		DatagramSocket socketUDP;
		try  { 
			System.out.println ("CLIENTE: Estableciendo parámetros de conexión");
			InetAddress hostServidor = InetAddress.getByName("localhost");
			int puertoServidor = 49200;
			
			System.out.println ("CLIENTE: Creando socket ");
			socketUDP = new DatagramSocket();
			
			System.out.println ("CLIENTE: Enviando datagrama ");
			byte[] mensaje = strMensaje.getBytes();
			DatagramPacket peticion = new DatagramPacket(mensaje, mensaje.length, hostServidor, puertoServidor);
			socketUDP.send(peticion);
			
			System.out.println ("CLIENTE: Recibiendo datagrama ");
			byte[] buffer = new byte [64];
			DatagramPacket respuesta = new DatagramPacket(buffer, buffer.length, hostServidor, puertoServidor);
			
			socketUDP.receive(respuesta);
			System.out.println ("CLIENTE: Mensaje recibido:  "+ new String(buffer));
			
			System.out.println ("CLIENTE: Cerrando socket ");		
			socketUDP.close();
			System.out.println ("CLIENTE: Socket cerrado ");
			
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
	    } catch (IOException e) {
			e.printStackTrace();
	    }
			
	 }
}
