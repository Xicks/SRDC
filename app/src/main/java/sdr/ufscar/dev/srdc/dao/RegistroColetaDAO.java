package sdr.ufscar.dev.srdc.dao;

import java.util.List;

import sdr.ufscar.dev.srdc.model.RegistroColeta;

/**
 * Created by Schick on 8/23/16.
 */
public class RegistroColetaDAO extends GenericDAO<RegistroColeta>{

    @Override
    public Boolean insert(RegistroColeta registroColeta) {
        //TODO
        return Boolean.FALSE;
    }

    @Override
    public Boolean delete(Integer idRegistro) {
        //TODO
        return Boolean.FALSE;
    }

    @Override
    public Boolean update(RegistroColeta registroColeta) {
        //TODO
        return Boolean.FALSE;
    }

    @Override
    public RegistroColeta select(Integer idRegistro) {
        //TODO
        return null;
    }

    public List<RegistroColeta> selectPorExemplo(RegistroColeta registroColeta) {
        //TODO
        return null;
    }
}
