import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.Key;

public class Main {
    public static void main(String[] args) throws Exception {

        Key key = obtenerClave();
        Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
        byte[] encriptado = cifrar(aes, "Hola Mundo", key);

        // Escribir el texto encriptado en un archivo en el escritorio
        escribirEnArchivo(encriptado, "encriptado.txt");

        // Leer el texto encriptado desde el archivo y descifrarlo
        byte[] textoEncriptadoDesdeArchivo = leerDesdeArchivo("encriptado.txt");
        descifrar(aes, key, textoEncriptadoDesdeArchivo);
    }

    public static Key obtenerClave() throws Exception {
        KeyGenerator kg = KeyGenerator.getInstance("AES");
        kg.init(128);
        return kg.generateKey();
    }

    public static byte[] cifrar(Cipher aes, String texto, Key key) throws Exception {
        aes.init(Cipher.ENCRYPT_MODE, key);
        byte[] encriptado = aes.doFinal(texto.getBytes());

        // Para visualizar la representación hexadecimal del texto encriptado
        for (byte b : encriptado) {
            System.out.print(String.format("%02X", b));
        }
        System.out.println();

        return encriptado;
    }

    public static void descifrar(Cipher aes, Key key, byte[] encriptado) throws Exception {
        aes.init(Cipher.DECRYPT_MODE, key);
        byte[] desencriptado = aes.doFinal(encriptado);

        System.out.println("Texto descifrado: " + new String(desencriptado));
    }

    public static void escribirEnArchivo(byte[] data, String fileName) {
        try (FileOutputStream fos = new FileOutputStream(System.getProperty("user.home") + "/Desktop/" + fileName)) {
            fos.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] leerDesdeArchivo(String fileName) {
        byte[] data = null;
        try (FileInputStream fis = new FileInputStream(System.getProperty("user.home") + "/Desktop/" + fileName)) {
            data = fis.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
