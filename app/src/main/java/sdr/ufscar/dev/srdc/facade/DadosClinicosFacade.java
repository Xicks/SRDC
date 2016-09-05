package sdr.ufscar.dev.srdc.facade;

import java.util.ArrayList;

import sdr.ufscar.dev.srdc.dao.CidadaoDAO;
import sdr.ufscar.dev.srdc.dao.DadosClinicosDAO;
import sdr.ufscar.dev.srdc.dao.RegistroAtividadeFisicaDAO;
import sdr.ufscar.dev.srdc.dao.RegistroColetaDAO;
import sdr.ufscar.dev.srdc.model.Cidadao;
import sdr.ufscar.dev.srdc.model.DadosClinicos;
import sdr.ufscar.dev.srdc.model.RegistroAtividadeFisica;
import sdr.ufscar.dev.srdc.model.RegistroColeta;

/**
 * Created by Schick on 8/26/16.
 */
public class DadosClinicosFacade {

    private DadosClinicosDAO dadosClinicosDAO;

    public DadosClinicosFacade(){
        dadosClinicosDAO = new DadosClinicosDAO();
    }

    /**
     * Faz o cadastro do dado clinico e relaciona ao cidadao
     * @param dadosClinicos
     * @return se a operação foi realizada com sucesso
     */
    public Boolean cadastrarDadosClinicos(DadosClinicos dadosClinicos, Cidadao cidadao) {
        Boolean sucesso = dadosClinicosDAO.insert(dadosClinicos);
        if (Boolean.TRUE.equals(sucesso)){
            CidadaoDAO cidadaoDAO = new CidadaoDAO();
            cidadaoDAO.select(cidadao.getIdCidadao());
            cidadao.setDadosClinicos(dadosClinicos);
            return cidadaoDAO.update(cidadao);
        }
        return Boolean.FALSE;
    }

    /**
     * Altera o dado clinico
     * @param dadosClinicos
     * @return se a operação foi realizada com sucesso
     */
    public Boolean alterarDadosClinicos(DadosClinicos dadosClinicos) {
        return dadosClinicosDAO.update(dadosClinicos);
    }

    /**
     * Remove o DadosClinico.
     * @param idDadosClinicos
     * @return se a operação foi realizada com sucesso
     */
    public Boolean removerDadosClinicos(Integer idDadosClinicos,Cidadao cidadao) {
        DadosClinicos dadosClinicos = dadosClinicosDAO.select(idDadosClinicos);
        if(dadosClinicos != null) {
            RegistroColetaDAO registroColetaDAO = new RegistroColetaDAO();
            for (RegistroColeta registroColeta : dadosClinicos.getRegistrosColeta()){
                registroColetaDAO.delete(registroColeta.getIdRegistroColeta());
            }
            RegistroAtividadeFisicaDAO registroAtividadeFisicaDAO = new RegistroAtividadeFisicaDAO();
            for (RegistroAtividadeFisica registroAtividadeFisica :
                    dadosClinicos.getRegistrosAtividadeFisica()){
                registroAtividadeFisicaDAO.delete(registroAtividadeFisica.getIdRegistroAtividadeFisica());
            }
            Boolean sucesso = dadosClinicosDAO.delete(idDadosClinicos);
            if (Boolean.TRUE.equals(sucesso)) {
                CidadaoDAO cidadaoDAO = new CidadaoDAO();
                cidadaoDAO.select(cidadao.getIdCidadao());
                cidadao.setDadosClinicos(null);
                return cidadaoDAO.update(cidadao);
            }
        }
        return Boolean.FALSE;
    }

}
