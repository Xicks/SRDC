package sdr.ufscar.dev.srdc.dao;

import java.util.List;

import sdr.ufscar.dev.srdc.model.Registro;

/**
 * Created by Schick on 8/23/16.
 */
public class RegistroDAO extends GenericDAO<Registro>{

    @Override
    public Boolean insert(Registro registro) {
        //TODO
        return Boolean.FALSE;
    }

    @Override
    public Boolean delete(Integer idRegistro) {
        //TODO
        return Boolean.FALSE;
    }

    @Override
    public Boolean update(Registro registro) {
        //TODO
        return Boolean.FALSE;
    }

    @Override
    public Registro select(Integer idRegistro) {
        //TODO
        return null;
    }

    public List<Registro> selectPorExemplo(Registro registro) {
        //TODO
        return null;
    }
}
