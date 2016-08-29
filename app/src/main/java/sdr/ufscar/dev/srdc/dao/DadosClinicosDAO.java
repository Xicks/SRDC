package sdr.ufscar.dev.srdc.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import sdr.ufscar.dev.srdc.model.DadosClinicos;
import sdr.ufscar.dev.srdc.model.DoencaEnum;
import sdr.ufscar.dev.srdc.util.AppUtils;

/**
 * Created by bento on 28/08/16.
 */
public class DadosClinicosDAO extends GenericDAO<DadosClinicos>{

    /**
     * Insere um novo registro no banco da entidade Dados Clinicos
     * @param dadosClinicos
     * @return se a operação foi realizada com sucesso
     */
    @Override
    public Boolean insert(DadosClinicos dadosClinicos) {
        SQLiteDatabase bd = DatabaseHelper.getInstance().openReadWrite();
        ContentValues cv = new ContentValues();
        cv.put("cns_profissional",dadosClinicos.getCnsProfissional());
        cv.put("cnes",dadosClinicos.getCnes());
        String data = AppUtils.converterData(dadosClinicos.getDataRegistro());
        cv.put("data_registro",data);
        cv.put("altura",dadosClinicos.getAltura());

        ArrayList<DoencaEnum> doencas = dadosClinicos.getDoencas();
        for(DoencaEnum doenca: doencas) {
            cv.put(doenca.getDoenca(),1);
        }

        cv.put("observacoes",dadosClinicos.getObservacoes());

        if(Boolean.TRUE.equals(dadosClinicos.getEnviarNotificacao()))
            cv.put("enviar_notificacao",1);

        // Retorno id do novo registro ou -1 caso haja erro
        long id = bd.insert("dados_clinicos", null, cv);
        if(id != -1) {
            dadosClinicos.setIdDadosClinicos((int) id);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }


    /**
     * Remove um registro do banco da entidade Dados Clinicos
     * @param idDadosClinicos
     * @return se a operação foi realizada com sucesso
     */
    @Override
    public Boolean delete(Integer idDadosClinicos) {
        SQLiteDatabase bd = DatabaseHelper.getInstance().openReadWrite();
        // Retorna quantos registros foram alteradas
        int i = bd.delete("dados_clinicos","dados_clinicos_id_dados_clinicos = ?",
                new String[] {idDadosClinicos.toString()});
        bd.close();
        return i != 0;
    }

    /**
     * Atualiza um registro do banco da entidade Dados Clinicos
     * @param dadosClinicos
     * @return se a operação foi realizada com sucesso
     */
    @Override
    public Boolean update(DadosClinicos dadosClinicos) {
        SQLiteDatabase bd = DatabaseHelper.getInstance().openReadWrite();
        ContentValues cv = new ContentValues();
        cv.put("cns_profissional",dadosClinicos.getCnsProfissional());
        cv.put("cnes",dadosClinicos.getCnes());
        String data = AppUtils.converterData(dadosClinicos.getDataRegistro());
        cv.put("data_registro",data);
        cv.put("altura",dadosClinicos.getAltura());

        ArrayList<DoencaEnum> doencas = dadosClinicos.getDoencas();

        for(DoencaEnum doenca: DoencaEnum.values()) {
            if(doencas.contains(doenca)) {
                cv.put(doenca.getDoenca(), 1);
            } else {
                cv.put(doenca.getDoenca(),0);
            }
        }

        cv.put("observacoes",dadosClinicos.getObservacoes());

        if(Boolean.TRUE.equals(dadosClinicos.getEnviarNotificacao()))
            cv.put("enviar_notificacao",1);
        else
            cv.put("enviar_notificacao",0);

        // Retorna quantos registros foram alteradas
        int i = bd.update("dados_clinicos",cv,"dados_clinicos_id_dados_clinicos = ?",
                new String[] {dadosClinicos.getIdDadosClinicos().toString()});
        bd.close();
        return i != 0;
    }

    /**
     * Retorna objeto Cidadao correspondente ao IdDadosClinicos
     * @param IdDadosClinicos
     * @return dadosClinicos
     */
    @Override
    public DadosClinicos select(Integer IdDadosClinicos) {
        SQLiteDatabase bd = DatabaseHelper.getInstance().openReadOnly();
        Cursor c = bd.rawQuery("SELECT * FROM dados_clinicos WHERE dados_clinicos_id_dados_clinicos = ?",
                new String[]{IdDadosClinicos.toString()});
        if (c != null) {
            c.moveToFirst();
            DadosClinicos dadosClinicos = new DadosClinicos();
            dadosClinicos.setIdDadosClinicos(c.getInt(0));
            dadosClinicos.setCnsProfissional(c.getString(1));
            dadosClinicos.setCnes(c.getString(2));
            dadosClinicos.setDataRegistro(AppUtils.converterData(c.getString(3)));
            dadosClinicos.setAltura(c.getInt(4));

            ArrayList<DoencaEnum> doencas = new ArrayList<>();
            if(c.getInt(5) == 1)
                doencas.add(DoencaEnum.I10_15_DOENÇAS_HIPERTENSIVAS);
            if(c.getInt(6) == 1)
                doencas.add(DoencaEnum.E10_E14_DIABETES_MELLITUS);
            if(c.getInt(7) == 1)
                doencas.add(DoencaEnum.E65_E68_OBESIDADE_HIPERALIMENTAÇÃO);

            dadosClinicos.setDoencas(doencas);

            dadosClinicos.setObservacoes(c.getString(8));
            dadosClinicos.setEnviarNotificacao(c.getInt(9) == 1);

            c.close();
            bd.close();
            return dadosClinicos;
        } else {
            bd.close();
            return null;
        }
    }
}
