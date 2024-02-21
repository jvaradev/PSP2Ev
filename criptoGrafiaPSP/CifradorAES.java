package AES;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;


public class CifradorAES {

   public static void main(String[] args) throws Exception {

      // Generamos una clave de 128 bits para AES
      KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
      keyGenerator.init(128);
      Key key = keyGenerator.generateKey();
      
      // SecretKeySpec construye una clave secreta a partir de la matriz de bytes dada, 
      //utilizando los primeros bytes de longitud de la clave, 
      //comenzando en el desplazamiento inclusive.
      //Una clave que queramos que tenga al menos 16 bytes
      // y nos quedamos con los bytes 0 a 15
      key = new SecretKeySpec("una clave de 16 bytes".getBytes(),  0, 16, "AES");
      
      // Texto a encriptar
      String textoEnClaro = "Este es el texto que vamos a encriptar";

      // Se obtiene un cifrador AES
      Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");

      // Se inicializa para encriptacion y se encripta el texto,
      // que debemos pasar como bytes
      aes.init(Cipher.ENCRYPT_MODE, key);
      byte[] encriptado = aes.doFinal(textoEnClaro.getBytes());

      // Se escribe byte a byte en hexadecimal el texto
      // encriptado para ver cómo queda
      for (byte b : encriptado) {
         System.out.print(Integer.toHexString(0xFF & b));
      }
      System.out.println();

      // Se iniciliza el cifrador para desencriptar, con la
      // misma clave y se desencripta
      aes.init(Cipher.DECRYPT_MODE, key);
      byte[] desencriptado = aes.doFinal(encriptado);

      // Texto obtenido coincide con el original.
      System.out.println(new String(desencriptado));
   }
}