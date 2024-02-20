package org.example;

import java.io.*;
import java.net.*;

public class SocketTCPClient {

	private String serverIP;
	private int serverPort;
	private Socket clientSocket;
	private InputStream is;
	private OutputStream os;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	public static void main(String[] args) {
		SocketTCPClient client = new SocketTCPClient("localhost", 49200); // Especifica la IP del servidor y el puerto
		try {
			client.connect();
			client.abrirCanalObjetos();
			client.enviarArchivo("C:\\Users\\javiv\\Desktop\\hola.txt");
			client.CerrarCanalObjetos();
			client.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public SocketTCPClient(String serverIP, int serverPort) {
		this.serverIP = serverIP;
		this.serverPort = serverPort;
	}

	public void connect() throws IOException {
		System.out.println("CLIENTE: Conectando al servidor.");
		clientSocket = new Socket("172.17.203.48", 49200);
		os = clientSocket.getOutputStream();
		is = clientSocket.getInputStream();
		System.out.println("CLIENTE: Conexión establecida con el servidor.");
	}

	public void disconnect() throws IOException {
		System.out.println("CLIENTE: Desconectando del servidor.");
		os.close();
		is.close();
		clientSocket.close();
		System.out.println("CLIENTE: Desconexión exitosa.");
	}

	public void abrirCanalObjetos() throws IOException {
		System.out.println("CLIENTE: Abriendo canales de objetos.");

		oos = new ObjectOutputStream(os); // Canales de escritura de objetos
		ois = new ObjectInputStream(is); // Canales de lectura de objetos

		System.out.println("CLIENTE: Canales de objetos abiertos.");
	}

	public void CerrarCanalObjetos() throws IOException {
		System.out.println("CLIENTE: Cerrando canales de objetos.");

		oos.close(); // Canales de escritura de objetos
		ois.close(); // Canales de lectura de objetos

		System.out.println("CLIENTE: Canales de objetos cerrados.");
	}

	public void enviarArchivo(String filePath) throws IOException {
		System.out.println("CLIENTE: Enviando archivo.");

		byte[] fileBytes;
		try (FileInputStream fis = new FileInputStream(filePath);
			 ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

			byte[] buffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = fis.read(buffer)) != -1) {
				baos.write(buffer, 0, bytesRead);
			}

			fileBytes = baos.toByteArray();
		}

		oos.writeObject(fileBytes);
		oos.flush();

		System.out.println("CLIENTE: Archivo enviado con éxito.");
	}
}