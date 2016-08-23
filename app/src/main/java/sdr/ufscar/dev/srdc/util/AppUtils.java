package sdr.ufscar.dev.srdc.util;

import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Classe contendo métodos úteis
 *
 * Created by Schick on 8/23/16.
 */
public class AppUtils {

    /**
     * Criptografa utilizando SHA-256
     *
     * @param string string a ser criptografada
     * @return string criptografada
     */
    public static String digest(String string) {
        String retorno = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(string.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            retorno = new String(sb);
        } catch (NoSuchAlgorithmException e) {
            Log.e("AppUtils","Algoritmo de Criptografia Invalida",e);
        } finally {
            return retorno;
        }
    }

    public static Boolean isCPFValido(String cpf) {
        //TODO
        return Boolean.TRUE;
    }

    public static Boolean isCNSValido(String cns) {
        //TODO
        return Boolean.TRUE;
    }
}
