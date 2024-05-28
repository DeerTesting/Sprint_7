package org.example;
import java.util.Random;

public class RandomString {

    // combine all strings
    String Letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnopqrstuvwxyz" + "0123456789";

    // create random string builder
    StringBuilder sb = new StringBuilder();

    // create an object of Random class
    Random random = new Random();

    // specify length of random string
    public String getRandomString(int length) {
        for (int i = 0; i < length; i++) {

            // generate random index number
            int index = random.nextInt(Letters.length());

            // get character specified by index
            // from the string
            char randomChar = Letters.charAt(index);

            // append the character to string builder
             sb.append(randomChar);
        }
        return sb.toString();
    }

}
