package intercambio_mensajes_TCP;

import java.io.*;
import java.net.*;

public class SocketTCPClient {

	private String  serverIP;
	private int serverPort;
	private Socket socket;
	private InputStream is;
	private OutputStream os;
	
	//Objetos específicos para enviar y recibir cadenas de caracteres
	private InputStreamReader isr;
	private BufferedReader br;
	private PrintWriter pw;
	
	public static void main(String[] args) {
		SocketTCPClient cliente = new SocketTCPClient ("localhost",49200);
		try {
			cliente.start();
			cliente.abrirCanalTexto();
			cliente.EnviarMensajeTexto("Mensaje enviado desde el cliente");//Envío del mensaje al servidor
			String mensajeRecibido = cliente.leerMensajeTexto();//Recepción del mensaje del servidor
			System.out.println(" CLIENTE: Mensaje recibido:" + mensajeRecibido);
			cliente.CerrarCanalTexto();
			cliente.stop();
		} catch (UnknownHostException e) {
			e.printStackTrace();		
		} catch (IOException ioe) {
			ioe.printStackTrace();	
		}
	}
	
	public SocketTCPClient (String serverIP, int serverPort) {
		this.serverIP = serverIP;
		this.serverPort = serverPort;
	}
	
	public void start() throws IOException {
		System.out.println(" CLIENTE: Estableciendo conexión.");
		socket = new Socket(serverIP, serverPort);
		os = socket.getOutputStream();
		is = socket.getInputStream();
		System.out.println(" CLIENTE: Conexión establecida.");
	}
	
	public void stop() throws IOException {
		System.out.println(" CLIENTE: Cerrando conexiones.");	
		is.close();
		os.close();
		socket.close();
		System.out.println(" CLIENTE: Conexiones cerradas.");	
	}
	
	public void abrirCanalTexto() {
		System.out.println(" CLIENTE: Abriendo canales de texto.");
		
		isr = new InputStreamReader(is); //Canales de lectura
		br = new BufferedReader(isr);
		
		pw = new PrintWriter(os, true); //Canales de escritura
		System.out.println(" CLIENTE: Canales de texto abiertos.");
	}
	
	public void CerrarCanalTexto() throws IOException{
		System.out.println(" CLIENTE: Cerrando canales de texto.");
		
		br.close(); //Canales de lectura
		isr.close();
		
		pw.close(); //Canales de escritura
		System.out.println(" CLIENTE: Canales de texto cerrados.");
	}
	
	public String leerMensajeTexto() throws IOException{
		System.out.println(" CLIENTE: Leyendo mensaje.");
		String mensaje = br.readLine();
		System.out.println(" CLIENTE: Mensaje leído.");
		return mensaje;
	}
	
	public void EnviarMensajeTexto (String mensaje) throws IOException{
		System.out.println(" CLIENTE: Enviando mensaje.");
		pw.println(mensaje); 
		System.out.println(" CLIENTE: Mensaje enviado.");
	}
			
}
