import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class GenerateKeyPair {

    public static void main(String[] args) {
        try {
            // Cria um objeto KeyPairGenerator para o algoritmo RSA
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            
            // Inicializa o gerador de chaves com o tamanho da chave (por exemplo, 2048 bits)
            keyGen.initialize(2048);
            
            // Gera o par de chaves pública e privada
            KeyPair keyPair = keyGen.generateKeyPair();
            
            // Obtém as chaves pública e privada
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();
            
            // Imprime as chaves (você pode armazená-las em arquivos, banco de dados, etc.)
            System.out.println("Chave pública:");
            System.out.println(convertToPublicKey(Base64.getEncoder().encodeToString(publicKey.getEncoded())));
            
            System.out.println("\nChave privada:");
            System.out.println(convertToPublicKey(Base64.getEncoder().encodeToString(privateKey.getEncoded())));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static String convertToPublicKey(String key){
		StringBuilder result = new StringBuilder();
		result.append("-----BEGIN PUBLIC KEY-----\n");
		result.append(key);
		result.append("\n-----END PUBLIC KEY-----");
		return result.toString();
	}
}
