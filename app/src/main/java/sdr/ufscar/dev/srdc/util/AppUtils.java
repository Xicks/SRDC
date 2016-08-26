package sdr.ufscar.dev.srdc.util;

import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    private static int calcularDigito(String str, int[] peso) {
        int soma = 0;
        for (int indice=str.length()-1, digito; indice >= 0; indice-- ) {
            digito = Integer.parseInt(str.substring(indice,indice+1));
            soma += digito*peso[peso.length-str.length()+indice];
        }
        soma = 11 - soma % 11;
        return soma > 9 ? 0 : soma;
    }

    /**
     * Valida CPF
     * @param cpf
     * @return se o CPF é válido
     */
    public static boolean isCPFValido(String cpf) {
        if ((cpf==null) || (cpf.length()!=11)) return false;

        int[] pesoCPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
        Integer digito1 = calcularDigito(cpf.substring(0,9), pesoCPF);
        Integer digito2 = calcularDigito(cpf.substring(0,9) + digito1, pesoCPF);
        return cpf.equals(cpf.substring(0,9) + digito1.toString() + digito2.toString());
    }

    /**
     * Valida CNS
     * @param cns
     * @return se o CNS é válido
     */
    public static Boolean isCNSValido(String cns) {
        return !cns.trim().matches("");
    }

    /**
     * Converte objeto Date em String no formato dd/MM/yyyy
     * @param data
     * @return string
     */
    public static String converterData(Date data){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(data);
    }

    /**
     * Valida a senha, verificando se a senha possui de 6 a 11 dígitos
     * @param senha
     * @return se a senha é válida
     */
    public static Boolean isSenhaValida(String senha) {
        if(senha.trim().length() < 6 || senha.trim().length() > 11 || senha.trim().isEmpty()) {
            return Boolean.FALSE;
        } else {
            return Boolean.TRUE;
        }
    }

    /**
     * Converte objeto Date em String no formato dd/MM/yyyy
     * @param data
     * @return string
     */
    public static Date converterData(String data){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date retorno = null;
        try{
            retorno = sdf.parse(data);
        } catch(ParseException e) {
            Log.e("AppUtils","Erro ao converter data");
        } finally {
            return retorno;
        }
    }
}
