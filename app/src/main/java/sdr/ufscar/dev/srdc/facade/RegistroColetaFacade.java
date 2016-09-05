package sdr.ufscar.dev.srdc.facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sdr.ufscar.dev.srdc.dao.RegistroColetaDAO;
import sdr.ufscar.dev.srdc.model.DadosClinicos;
import sdr.ufscar.dev.srdc.model.RegistroColeta;
import sdr.ufscar.dev.srdc.util.AppUtils;

/**
 * Created by Schick on 8/23/16.
 */
public class RegistroColetaFacade {

    private RegistroColetaDAO registroColetaDAO;

    public RegistroColetaFacade(){
        registroColetaDAO = new RegistroColetaDAO();
    }

    public Boolean cadastrarRegistroColeta(RegistroColeta registroColeta, DadosClinicos dadosClinicos) {
        if(dadosClinicos == null || dadosClinicos.getIdDadosClinicos() == null) {
            throw new IllegalArgumentException("Dados Clinicos inválidos");
        }
        registroColeta.setIdDadosClinicos(dadosClinicos.getIdDadosClinicos());
        //registroColeta.setDataColeta(new Date());
        return registroColetaDAO.insert(registroColeta);
    }

    public List<RegistroColeta> pegarGlicemiaMaximaNoIntervalo(Integer idDadosClinicos,
                                                               Date inicio, Date fim) {
        return registroColetaDAO.selectGlicemiaMaximaNoIntervalo(idDadosClinicos,
                AppUtils.converterData(inicio),AppUtils.converterData(fim));
    }

    public List<RegistroColeta> pegarGlicemiaMinimaNoIntervalo(Integer idDadosClinicos,
                                                               Date inicio, Date fim) {
        return registroColetaDAO.selectGlicemiaMinimaNoIntervalo(idDadosClinicos,
                AppUtils.converterData(inicio),AppUtils.converterData(fim));
    }

    public List<RegistroColeta> pegarPressaoMaximaNoIntervalo(Integer idDadosClinicos,
                                                               Date inicio, Date fim) {
        return registroColetaDAO.selectPressaoSistolicaMaximaNoIntervalo(idDadosClinicos,
                AppUtils.converterData(inicio),AppUtils.converterData(fim));
    }

    public List<RegistroColeta> pegarPressaoMinimaNoIntervalo(Integer idDadosClinicos,
                                                               Date inicio, Date fim) {
        return registroColetaDAO.selectPressaoDiastolicaMinimaNoIntervalo(idDadosClinicos,
                AppUtils.converterData(inicio),AppUtils.converterData(fim));
    }

    public List<RegistroColeta> pegarPesoMaximoNoIntervalo(Integer idDadosClinicos,
                                                               Date inicio, Date fim) {
        return registroColetaDAO.selectPesoMaximoNoIntervalo(idDadosClinicos,
                AppUtils.converterData(inicio),AppUtils.converterData(fim));
    }

    public List<RegistroColeta> pegarPesoMinimoNoIntervalo(Integer idDadosClinicos,
                                                               Date inicio, Date fim) {
        return registroColetaDAO.selectPesoMinimoNoIntervalo(idDadosClinicos,
                AppUtils.converterData(inicio),AppUtils.converterData(fim));
    }

    public List<RegistroColeta> pegarGastoCaloricoMaximoNoIntervalo(Integer idDadosClinicos,
                                                           Date inicio, Date fim) {
        return registroColetaDAO.selectGastoCaloricoMaximoNoIntervalo(idDadosClinicos,
                AppUtils.converterData(inicio),AppUtils.converterData(fim));
    }

    public List<RegistroColeta> pegarGastoCaloricoMinimoNoIntervalo(Integer idDadosClinicos,
                                                           Date inicio, Date fim) {
        return registroColetaDAO.selectGastoCaloricoMinimoNoIntervalo(idDadosClinicos,
                AppUtils.converterData(inicio),AppUtils.converterData(fim));
    }

    public List<RegistroColeta> pegarRegistroColetaNoIntervalo(Integer idDadosClinicos,
                                                                    Date inicio, Date fim) {
        return registroColetaDAO.selectRegistroColetaNoIntervalo(idDadosClinicos,
                AppUtils.converterData(inicio),AppUtils.converterData(fim));
    }
}
