package sdr.ufscar.dev.srdc.facade;

import java.util.Date;

import sdr.ufscar.dev.srdc.dao.RegistroAtividadeFisicaDAO;
import sdr.ufscar.dev.srdc.model.DadosClinicos;
import sdr.ufscar.dev.srdc.model.RegistroAtividadeFisica;

/**
 * Created by Schick on 8/31/16.
 */
public class RegistroAtividadeFisicaFacade {

    private RegistroAtividadeFisicaDAO registroAtividadeFisicaDAO;

    public RegistroAtividadeFisicaFacade(){
        registroAtividadeFisicaDAO = new RegistroAtividadeFisicaDAO();
    }

    public Boolean cadastrarRegistroAtividadeFisica(RegistroAtividadeFisica registroAtividadeFisica,
                                                    DadosClinicos dadosClinicos) {
        if(dadosClinicos == null || dadosClinicos.getIdDadosClinicos() == null) {
            throw new IllegalArgumentException("Dados Clinicos inválidos");
        }
        registroAtividadeFisica.setIdDadosClinicos(dadosClinicos.getIdDadosClinicos());
        registroAtividadeFisica.setDataRegistro(new Date());
        return registroAtividadeFisicaDAO.insert(registroAtividadeFisica);
    }
}
