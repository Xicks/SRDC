package sdr.ufscar.dev.srdc.facade;

import sdr.ufscar.dev.srdc.dao.CidadaoDAO;
import sdr.ufscar.dev.srdc.exception.CadastroDuplicadoException;
import sdr.ufscar.dev.srdc.model.Cidadao;

/**
 * Created by Schick on 8/23/16.
 */
public class CidadaoFacade {

    private CidadaoDAO cidadaoDAO;

    public CidadaoFacade(){
        cidadaoDAO = new CidadaoDAO();
    }

    /**
     * Faz o cadastro do cidadão e do usuario relacionado a ele.
     * @param cidadao
     * @throws CadastroDuplicadoException
     * @return se a operação foi realizada com sucesso
     */
    public Boolean cadastrarCidadao(Cidadao cidadao) {
        UsuarioFacade usuarioFacade = new UsuarioFacade();
        if(cidadao.getUsuario() == null) {
            throw new IllegalArgumentException("Cidadao sem usuario");
        }
        try {
            Boolean sucesso = usuarioFacade.cadastrarUsuario(cidadao.getUsuario());

            // Verifica se foi possivel adicionao o usuario
            if(Boolean.TRUE.equals(sucesso)) {
                sucesso = cidadaoDAO.insert(cidadao);
                // Verifica se foi possivel adicionar o cidadao
                if(!Boolean.TRUE.equals(sucesso)) {
                    // Remove o usuario caso nao seja possível cadastrar o cidadao
                    usuarioFacade.removerUsuario(cidadao.getUsuario().getIdUsuario());
                } else {
                    return Boolean.TRUE;
                }
            }
        }catch(CadastroDuplicadoException e) {
            throw e;
        }
        return Boolean.FALSE;
    }

    /**
     * Remove o cidadão, assim como o usuário e dados clínicos relacionados a ele.
     * @param cidadao
     * @return se a operação foi realizada com sucesso
     */
    public Boolean removerCidadao(Cidadao cidadao) {
        new UsuarioFacade().removerUsuario(cidadao.getUsuario().getIdUsuario());
        new DadosClinicosFacade().removerDadosClinicos(cidadao.getDadosClinicos().getIdDadosClinicos());
        cidadaoDAO.delete(cidadao.getIdCidadao());
        return Boolean.TRUE;
    }

    /**
     * Retorna um cidadão a partir de um usuário.
     * @param idUsuario
     * @return cidadao
     */
    public Cidadao procurarCidadaoPorUsuario(Integer idUsuario) {
        return cidadaoDAO.selectPorUsuario(idUsuario);
    }
}
