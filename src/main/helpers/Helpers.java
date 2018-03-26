package helpers;

import com.google.gson.Gson;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Helpers{
    public static final String MINED_ADDRESS = "0";

    private static Gson gson = new Gson();
    private static MessageDigest md;


    public static String getJSON(Object object){
        String json = gson.toJson(object);
        return json;
    }

    public static String hashString(String str){
        try{
            //hash function not encryption
            md = MessageDigest.getInstance("SHA-256");

        }catch (NoSuchAlgorithmException e){
            return null;
        }
        md.update(str.getBytes(StandardCharsets.UTF_8));
        byte[] digest = md.digest();
        String hex = String.format("%064x", new BigInteger( 1, digest ));
        return hex;
    }

    public static String getEncryptedJSON(Object object){
            return hashString(getJSON(object));
    }
}
