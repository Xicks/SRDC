package sdr.ufscar.dev.srdc.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import sdr.ufscar.dev.srdc.exception.CadastroDuplicadoException;
import sdr.ufscar.dev.srdc.model.Usuario;

/**
 * Created by Schick on 8/23/16.
 */
public class UsuarioDAO extends GenericDAO<Usuario>{

    /**
     * Insere um novo registro no banco da entidade Usuario.
     * @param usuario
     * @throws CadastroDuplicadoException
     * @return se a operação foi realizada com sucesso
     */
    @Override
    public Boolean insert(Usuario usuario) {
        SQLiteDatabase bd = DatabaseHelper.getInstance().openReadWrite();
        ContentValues cv = new ContentValues();
        cv.put("username",usuario.getUsername());
        cv.put("senha",usuario.getSenha());
        // Retorno id do novo registro ou -1 caso haja erro
        try {
            long id = bd.insertOrThrow("usuario", null, cv);
            bd.close();
            if(id != -1) {
                usuario.setIdUsuario((int) id);
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        } catch(SQLiteConstraintException e) {
            throw new CadastroDuplicadoException(e);
        }
    }

    /**
     * Remove um registro do banco da entidade Usuario.
     * @param idUsuario
     * @return se a operação foi realizada com sucesso
     */
    @Override
    public Boolean delete(Integer idUsuario) {
        SQLiteDatabase bd = DatabaseHelper.getInstance().openReadWrite();
        // Retorna quantos registros foram alteradas
        int i = bd.delete("usuario","usuario_id_usuario = ?",new String[] {idUsuario.toString()});
        bd.close();
        return i != 0;
    }

    /**
     * Atualiza um registro do banco da entidade Usuario.
     * @param usuario
     * @throws CadastroDuplicadoException
     * @return se a operação foi realizada com sucesso
     */
    @Override
    public Boolean update(Usuario usuario) {
        //TODO
        return Boolean.FALSE;
    }

    /**
     * Retorna o objeto Usuario correspondente ao idUsuario.
     * @param idUsuario
     * @return usuario
     */
    @Override
    public Usuario select(Integer idUsuario) {
        //TODO
        return null;
    }

    /**
     * Retorna objeto Usuario correspondente ao username e senha.
     * @param usuario
     * @return usuario
     */
    public Usuario selectPorExemplo(Usuario usuario) {
        SQLiteDatabase db = DatabaseHelper.getInstance().openReadOnly();
        Cursor c = db.rawQuery("SELECT usuario_id_usuario from usuario" +
                " WHERE username = ? AND senha = ?", new String[]{usuario.getUsername(), usuario.getSenha()});
        Usuario u = null;
        if (c != null) {
            while (c.moveToNext()) {
                u = new Usuario();
                u.setIdUsuario(c.getInt(0));
            }
            c.close();
        }
        return u;
    }
}
