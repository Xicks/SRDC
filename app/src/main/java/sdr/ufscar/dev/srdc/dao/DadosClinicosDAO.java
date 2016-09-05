package sdr.ufscar.dev.srdc.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import sdr.ufscar.dev.srdc.model.DadosClinicos;
import sdr.ufscar.dev.srdc.enumeration.DoencaEnum;
import sdr.ufscar.dev.srdc.util.AppUtils;

/**
 * Created by bento on 28/08/16.
 */
public class DadosClinicosDAO implements GenericDAO<DadosClinicos>{

    /**
     * Insere um novo registro no banco da entidade Dados Clinicos
     * @param dadosClinicos
     * @return se a operação foi realizada com sucesso
     */
    @Override
    public Boolean insert(DadosClinicos dadosClinicos) {
        SQLiteDatabase db = DatabaseHelper.getInstance().openReadWrite();
        ContentValues cv = new ContentValues();
        cv.put("cns_profissional",dadosClinicos.getCnsProfissional());
        cv.put("cnes",dadosClinicos.getCnes());
        String data = AppUtils.converterData(dadosClinicos.getDataRegistro());
        cv.put("data_registro",data);
        cv.put("altura",dadosClinicos.getAltura());
        cv.put("dias_coleta",dadosClinicos.getDiasColeta());

        ArrayList<DoencaEnum> doencas = dadosClinicos.getDoencas();
        for(DoencaEnum doenca: doencas) {
            cv.put(doenca.getDoenca(),1);
        }

        cv.put("observacoes",dadosClinicos.getObservacoes());

        if(Boolean.TRUE.equals(dadosClinicos.getEnviarNotificacao()))
            cv.put("enviar_notificacao",1);

        // Retorno id do novo registro ou -1 caso haja erro
        long id = db.insert("dados_clinicos", null, cv);
        Boolean retorno = Boolean.FALSE;
        if(id != -1) {
            dadosClinicos.setIdDadosClinicos((int) id);
            // Adiciona horas de coleta
            for(Integer horaColeta: dadosClinicos.getHorasColeta()) {
                cv = new ContentValues();
                cv.put("hora_coleta_id_dados_clinicos",(int) id);
                cv.put("hora",horaColeta);
                db.insert("hora_coleta",null,cv);
            }
            retorno = Boolean.TRUE;
        }
        db.close();
        return retorno;
    }


    /**
     * Remove um registro do banco da entidade Dados Clinicos
     * @param idDadosClinicos
     * @return se a operação foi realizada com sucesso
     */
    @Override
    public Boolean delete(Integer idDadosClinicos) {
        SQLiteDatabase db = DatabaseHelper.getInstance().openReadWrite();
        // Retorna quantos registros foram alteradas
        int i = db.delete("dados_clinicos","dados_clinicos_id_dados_clinicos = ?",
                new String[] {idDadosClinicos.toString()});
        Boolean retorno = Boolean.FALSE;
        if(i != 0) {
            // Apaga as horas de coleta
            db.delete("hora_coleta","coleta_hora_id_dados_clinicos = ?",
                    new String[] {idDadosClinicos.toString()});
            retorno = Boolean.TRUE;
        }
        db.close();
        return retorno;
    }

    /**
     * Atualiza um registro do banco da entidade Dados Clinicos
     * @param dadosClinicos
     * @return se a operação foi realizada com sucesso
     */
    @Override
    public Boolean update(DadosClinicos dadosClinicos) {
        SQLiteDatabase db = DatabaseHelper.getInstance().openReadWrite();
        ContentValues cv = new ContentValues();
        cv.put("cns_profissional",dadosClinicos.getCnsProfissional());
        cv.put("cnes",dadosClinicos.getCnes());
        String data = AppUtils.converterData(dadosClinicos.getDataRegistro());
        cv.put("data_registro",data);
        cv.put("altura",dadosClinicos.getAltura());
        cv.put("dias_coleta",dadosClinicos.getDiasColeta());

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
        int i = db.update("dados_clinicos",cv,"dados_clinicos_id_dados_clinicos = ?",
                new String[] {dadosClinicos.getIdDadosClinicos().toString()});

        // Apaga as horas de coleta
        db.delete("hora_coleta","coleta_hora_id_dados_clinicos = ?",
                new String[] {dadosClinicos.getIdDadosClinicos().toString()});
        // Adiciona as novas horas de coleta
        for(Integer horaColeta: dadosClinicos.getHorasColeta()) {
            cv = new ContentValues();
            cv.put("hora_coleta_id_dados_clinicos", dadosClinicos.getIdDadosClinicos());
            cv.put("hora",horaColeta);
            db.insert("hora_coleta",null,cv);
        }
        db.close();
        return i != 0;
    }

    /**
     * Retorna objeto Cidadao correspondente ao IdDadosClinicos
     * @param IdDadosClinicos
     * @return dadosClinicos
     */
    @Override
    public DadosClinicos select(Integer IdDadosClinicos) {
        SQLiteDatabase db = DatabaseHelper.getInstance().openReadOnly();
        Cursor c = db.rawQuery("SELECT * FROM dados_clinicos WHERE dados_clinicos_id_dados_clinicos = ?",
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
            dadosClinicos.setDiasColeta(c.getString(9));
            dadosClinicos.setEnviarNotificacao(c.getInt(10) == 1);

            c.close();

            // Recupera horarios de coleta
            ArrayList<Integer> horasColeta = new ArrayList<>(8);
            c = db.rawQuery("SELECT * FROM hora_coleta WHERE hora_coleta_id_dados_clinicos = ?",
                    new String[]{IdDadosClinicos.toString()});
            if(c != null){
                while(c.moveToNext()){
                    horasColeta.add(Integer.parseInt(c.getString(2)));
                }
            }
            dadosClinicos.setHorasColeta(horasColeta);
            db.close();
            return dadosClinicos;
        } else {
            db.close();
            return null;
        }
    }
}
