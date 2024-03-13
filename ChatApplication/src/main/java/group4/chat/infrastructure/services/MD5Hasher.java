/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package group4.chat.infrastructure.services;
import java.security.MessageDigest;
import group4.chat.usecases.adapters.Hasher;

/**
 *
 * @author Asus
 */

public class MD5Hasher implements Hasher {
    public String hash(String text) throws Exception {
        byte[] hashByte;
        String hashString;
        
        MessageDigest md = MessageDigest.getInstance("MD5");
        hashByte = md.digest(text.getBytes());
        hashString = new String(hashByte);
        
        return hashString;
    }
}
