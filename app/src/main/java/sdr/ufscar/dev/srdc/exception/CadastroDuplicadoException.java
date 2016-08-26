package sdr.ufscar.dev.srdc.exception;

import android.database.sqlite.SQLiteConstraintException;

/**
 * Created by Schick on 8/26/16.
 */
public class CadastroDuplicadoException extends RuntimeException {

    private SQLiteConstraintException exception;

    /**
     * Construtor da classe CadastroDuplicadoException
     * @param e
     */
    public CadastroDuplicadoException(SQLiteConstraintException e){
        exception = e;
    }

    /**
     * Retorna a mensagem da exceção
     * @return mensagem da exceção
     */
    public String getMessage() {
        return exception.getMessage();
    }
}
