package Utils;

import java.math.BigInteger;
import java.security.MessageDigest;


/** L'oggetto SHA512 ha il compito di codificare una stringa*/

public class SHA512 {

    /**Questo metodo ritorna una stringa codificata
     *
     * @param input stringa da codificare
     * @return la stringa codificata
     *
     * */

    public static String getSHA512(String input) {

        String toReturn = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            digest.reset();
            digest.update(input.getBytes("utf8"));
            toReturn = String.format("%0128x", new BigInteger(1, digest.digest()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return toReturn;
    }

}
