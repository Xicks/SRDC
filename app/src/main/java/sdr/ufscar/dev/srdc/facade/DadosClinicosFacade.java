package sdr.ufscar.dev.srdc.facade;

import sdr.ufscar.dev.srdc.dao.DadosClinicosDAO;
import sdr.ufscar.dev.srdc.exception.CadastroDuplicadoException;
import sdr.ufscar.dev.srdc.model.DadosClinicos;

/**
 * Created by Schick on 8/26/16.
 */
public class DadosClinicosFacade {

    private DadosClinicosDAO dadosClinicosDAO;

    public DadosClinicosFacade(){
        dadosClinicosDAO = new DadosClinicosDAO();
    }

    /**
     * Faz o cadastro do dado clinico.
     * @param dadosClinicos
     * @throws CadastroDuplicadoException
     * @return se a operação foi realizada com sucesso
     */
    public Boolean cadastrarDadosClinicos(DadosClinicos dadosClinicos) {
        try {
            //TODO Eventuais regras
            return  dadosClinicosDAO.insert(dadosClinicos);

        }catch(CadastroDuplicadoException e) {
            return Boolean.FALSE;
        }
    }

    /**
     * Remove o DadosClinico.
     * @param idDadosClinicos
     * @return se a operação foi realizada com sucesso
     */
    public Boolean removerDadosClinicos(Integer idDadosClinicos) {
        //TODO Eventuais regras
        return  dadosClinicosDAO.delete(idDadosClinicos);
    }
}
