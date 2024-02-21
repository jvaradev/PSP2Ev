package intercambio_mensajes_TCP;

import java.io.*;
import java.net.*;

public class SocketTCPServer {

	private ServerSocket serverSocket;
	private Socket socket;
	private InputStream is;
	private OutputStream os;
	
	//Objetos específicos para enviar y recibir cadenas de caracteres
	private InputStreamReader isr;
	private BufferedReader br;
	private PrintWriter pw;
	
	public static void main(String[] args) {
		try {
			SocketTCPServer servidor = new SocketTCPServer (49200);//Crea un socket tipo ServerTCPSocket 
			                                                       //asociando un puerto
			servidor.start();//en este método se indica al socket que se quede a la espera
			                 //de las peticiones del objeto ServerSocket creado en el constructor
			                 //Aquí la ejecuación se detiene hasta que llegue una petición 
			                 //de conexión de un cliente.
			servidor.abrirCanalTexto();//Una vez recibida la petición de conexión se representa
			                           //la conexión entre cliente-servidor (flujos I/O)
			String mensajeRecibido = servidor.leerMensajeTexto();//Recepción del mensaje del cliente
			System.out.println(" SERVIDOR: Mensaje recibido:" + mensajeRecibido);
			servidor.EnviarMensajeTexto("Mensaje enviado desde el servidor");//Envío del mensaje al cliente
			servidor.CerrarCanalTexto();
			servidor.stop();//Se cierran las conexiones (primero los streams del socket antes 
			                //del propio socket
		} catch (IOException ioe) {
			ioe.printStackTrace();
			
		}
	}
	
	public SocketTCPServer (int puerto) throws IOException {
		serverSocket = new ServerSocket (puerto);
	}
	
	public void start() throws IOException {
		System.out.println(" SERVIDOR: Esperando conexiones.");
		socket = serverSocket.accept();
		is = socket.getInputStream();
		os = socket.getOutputStream();
		System.out.println(" SERVIDOR: Conexión establecida.");
	}
	
	public void stop() throws IOException {
		System.out.println(" SERVIDOR: Cerrando conexiones.");	
		is.close();
		os.close();
		socket.close();
		serverSocket.close();
		System.out.println(" SERVIDOR: Conexiones cerradas.");	
	}
	
	public void abrirCanalTexto() {
		System.out.println(" SERVIDOR: Abriendo canales de texto.");
		
		isr = new InputStreamReader(is); //Canales de lectura
		br = new BufferedReader(isr);
		
		pw = new PrintWriter(os, true); //Canales de escritura
		System.out.println(" SERVIDOR: Canales de texto abiertos.");
	}
	
	public void CerrarCanalTexto() throws IOException{
		System.out.println(" SERVIDOR: Cerrando canales de texto.");
		
		br.close(); //Canales de lectura
		isr.close();
		
		pw.close(); //Canales de escritura
		System.out.println(" SERVIDOR: Canales de texto cerrados.");
	}
	
	public String leerMensajeTexto() throws IOException{
		System.out.println(" SERVIDOR: Leyendo mensaje.");
		String mensaje = br.readLine();
		System.out.println(" SERVIDOR: Mensaje leído.");
		return mensaje;
	}
	
	public void EnviarMensajeTexto (String mensaje) throws IOException{
		System.out.println(" SERVIDOR: Enviando mensaje.");
		pw.println(mensaje); 
		System.out.println(" SERVIDOR: Mensaje enviado.");
	}
	
			
}
