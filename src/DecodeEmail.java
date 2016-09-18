import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DecodeEmail {

	// Calculate the MD5 of a string, and return the hex representation
	public static String hash_MD5(String input) {
        byte[] plain_text = input.getBytes();
		MessageDigest md;
		StringBuffer hexString = new StringBuffer();
		try {
			md = MessageDigest.getInstance("MD5");
			byte[] digest = md.digest(plain_text);
			// Convert the digest into hex representation
	    	for (int i=0;i<digest.length;i++) {
	    		String hex=Integer.toHexString(0xff & digest[i]);
	    		// toHexString will omit the leading 0, we need to add it back
	   	     	if(hex.length()==1) hexString.append('0');
	   	     hexString.append(hex);
	    	}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hexString.toString();
	}

	public static String decrypt(String input){
		// Split the md5 every 32 chars. http://stackoverflow.com/questions/3760152/
		String[] ciphers = input.split("(?<=\\G.{32})");
		String result="";
		char[] alphabet = "abcdefghijklmnopqrstuvwxyz-_.@+0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
		for (String cipher : ciphers){
			// Decrypt each md5
			String tuple="";
			// Brute force the two characters
			for (char i : alphabet){
				tuple="";
				for (char j : alphabet){
					if (hash_MD5(result+i+j+hash_MD5(result+i+j)).equals(cipher)){
						tuple+=i;
						tuple+=j;
						break;
					}
				}
				if (tuple.length()>0)break;
			}
			if (tuple.length()==0) System.out.println("No text found for "+cipher+"!");
			result+=tuple;
		}
		return result;
	}

	public static String encrypt(String input){
		String result = "";
		for (int i=2;i<=input.length();i+=2){
			result+=hash_MD5(input.substring(0,i)+hash_MD5(input.substring(0,i)));
		}
		return result;
	}

	public static void main(String[] args) {
		String crypt_text = encrypt("resume@example.com");
		System.out.println("crypt_text: "+crypt_text);
		System.out.println("plain_text: "+decrypt(crypt_text));
	}

}
