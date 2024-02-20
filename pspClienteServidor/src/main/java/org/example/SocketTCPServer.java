package org.example;

import java.io.*;
import java.net.*;

public class SocketTCPServer {

	private int port;
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private InputStream is;
	private OutputStream os;

	// Objetos específicos para enviar y recibir objetos
	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	public static void main(String[] args) {
		SocketTCPServer server = new SocketTCPServer(49200);
		try {
			server.start();
			server.abrirCanalObjetos();
			server.atenderPeticiones();
			server.CerrarCanalObjetos();
			server.stop();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public SocketTCPServer(int port) {
		this.port = port;
	}

	public void start() throws IOException {
		System.out.println("SERVIDOR: Iniciando servidor.");
		serverSocket = new ServerSocket(port);
		System.out.println("SERVIDOR: Servidor iniciado en el puerto " + port);
		clientSocket = serverSocket.accept();
		os = clientSocket.getOutputStream();
		is = clientSocket.getInputStream();
		System.out.println("SERVIDOR: Conexión establecida con el cliente.");
	}

	public void stop() throws IOException {
		System.out.println("SERVIDOR: Cerrando servidor.");
		is.close();
		os.close();
		clientSocket.close();
		serverSocket.close();
		System.out.println("SERVIDOR: Servidor cerrado.");
	}

	public void abrirCanalObjetos() throws IOException {
		System.out.println("SERVIDOR: Abriendo canales de objetos.");

		oos = new ObjectOutputStream(os); // Canales de escritura de objetos
		ois = new ObjectInputStream(is); // Canales de lectura de objetos

		System.out.println("SERVIDOR: Canales de objetos abiertos.");
	}

	public void CerrarCanalObjetos() throws IOException {
		System.out.println("SERVIDOR: Cerrando canales de objetos.");

		oos.close(); // Canales de escritura de objetos
		ois.close(); // Canales de lectura de objetos

		System.out.println("SERVIDOR: Canales de objetos cerrados.");
	}

	public void atenderPeticiones() throws IOException {
		try {
			Object obj = ois.readObject();

			if (obj instanceof byte[]) {
				recibirArchivo((byte[]) obj);
			} else {
				System.out.println("SERVIDOR: Tipo de objeto no esperado.");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void recibirArchivo(byte[] fileBytes) throws IOException {
		System.out.println("SERVIDOR: Recibiendo archivo.");

		String filePath = "C:\\Users\\Instalador\\Desktop\\archivo_recibido.txt";
		try (FileOutputStream fos = new FileOutputStream(filePath)) {
			fos.write(fileBytes);
		}

		System.out.println("SERVIDOR: Archivo recibido y guardado como " + filePath);
	}
}