package sdr.ufscar.dev.srdc.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

import sdr.ufscar.dev.srdc.model.Cidadao;
import sdr.ufscar.dev.srdc.model.DadosClinicos;
import sdr.ufscar.dev.srdc.util.AppUtils;

/**
 * Created by Schick on 8/23/16.
 */
public class CidadaoDAO extends GenericDAO<Cidadao>{

    /**
     * Insere um novo registro no banco da entidade Cidadao
     * @param cidadao
     * @return se a operação foi realizada com sucesso
     */
    @Override
    public Boolean insert(Cidadao cidadao) {
        SQLiteDatabase bd = DatabaseHelper.getInstance().openReadWrite();
        ContentValues cv = new ContentValues();
        cv.put("nome",cidadao.getNome());
        cv.put("cpf_cns",cidadao.getCpfCns());
        cv.put("cidade",cidadao.getCidade());
        cv.put("estado",cidadao.getEstado());
        String data = AppUtils.converterData(cidadao.getDataNascimento());
        cv.put("data_nascimento",data);
        cv.put("cidadao_id_usuario",cidadao.getUsuario().getIdUsuario());
        // Retorno id do novo registro ou -1 caso haja erro
        long id = bd.insert("cidadao", null, cv);
        bd.close();
        return id != -1;
    }

    /**
     * Remove um registro do banco da entidade Cidadao
     * @param idCidadao
     * @return se a operação foi realizada com sucesso
     */
    @Override
    public Boolean delete(Integer idCidadao) {
        SQLiteDatabase bd = DatabaseHelper.getInstance().openReadWrite();
        // Retorna quantos registros foram alteradas
        int i = bd.delete("cidadao","cidadao_id_cidadao = ?",new String[] {idCidadao.toString()});
        bd.close();
        return i != 0;
    }

    /**
     * Atualiza um registro do banco da entidade Cidadao
     * @param cidadao
     * @return se a operação foi realizada com sucesso
     */
    @Override
    public Boolean update(Cidadao cidadao) {
        SQLiteDatabase bd = DatabaseHelper.getInstance().openReadWrite();
        ContentValues cv = new ContentValues();
        cv.put("nome",cidadao.getNome());
        cv.put("cpf_cns",cidadao.getCpfCns());
        cv.put("cidade",cidadao.getCidade());
        cv.put("estado",cidadao.getEstado());
        String data = AppUtils.converterData(cidadao.getDataNascimento());
        cv.put("data_nascimento",data);
        cv.put("cidadao_id_usuario",cidadao.getUsuario().getIdUsuario());
        cv.put("cidadao_id_dados_clinicos",cidadao.getDadosClinicos().getIdDadosClinicos());
        // Retorna quantos registros foram alteradas
        int i = bd.update("cidadao",cv,"cidadao_id_cidadao = ?",
                new String[] {cidadao.getIdCidadao().toString()});
        bd.close();
        return i != 0;
    }

    /**
     * Retorna objeto Cidadao correspondente ao idCidadao
     * @param idCidadao
     * @return cidadao
     */
    @Override
    public Cidadao select(Integer idCidadao) {
        SQLiteDatabase bd = DatabaseHelper.getInstance().openReadOnly();
        Cursor c = bd.rawQuery("SELECT * FROM cidadao WHERE cidadao_id_cidadao = ?",
                new String[]{idCidadao.toString()});
        if (c != null) {
            c.moveToFirst();
            Cidadao cidadao = new Cidadao();

            cidadao.setIdCidadao(c.getInt(0));

            if(!c.isNull(2)) {
                DadosClinicos dc = new DadosClinicos();
                dc.setIdDadosClinicos(c.getInt(2));
                cidadao.setDadosClinicos(dc);
            }

            cidadao.setNome(c.getString(3));
            cidadao.setCpfCns(c.getString(4));
            cidadao.setDataNascimento(AppUtils.converterData(c.getString(5)));
            cidadao.setCidade(c.getString(6));
            cidadao.setEstado(c.getString(7));
            c.close();
            bd.close();
            return cidadao;
        } else {
            bd.close();
            return null;
        }
    }

    /**
     * Retorna o objeto Cidadao correspondente ao usuario
     * @param idUsuario
     * @return
     */
    public Cidadao selectPorUsuario(Integer idUsuario) {
        SQLiteDatabase bd = DatabaseHelper.getInstance().openReadOnly();
        Cursor c = bd.rawQuery("SELECT * FROM cidadao WHERE cidadao_id_usuario = ?",
                new String[]{idUsuario.toString()});
        if (c != null) {
            c.moveToFirst();

            Cidadao cidadao = new Cidadao();
            cidadao.setIdCidadao(c.getInt(0));

            if(!c.isNull(2)) {
                DadosClinicos dc = new DadosClinicos();
                dc.setIdDadosClinicos(c.getInt(2));
                cidadao.setDadosClinicos(dc);
            }

            cidadao.setNome(c.getString(3));
            cidadao.setCpfCns(c.getString(4));
            cidadao.setDataNascimento(AppUtils.converterData(c.getString(5)));
            cidadao.setCidade(c.getString(6));
            cidadao.setEstado(c.getString(7));
            c.close();
            bd.close();
            return cidadao;
        } else {
            bd.close();
            return null;
        }
    }
}
