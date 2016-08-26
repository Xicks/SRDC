package sdr.ufscar.dev.srdc.facade;

import java.util.List;

import sdr.ufscar.dev.srdc.dao.CidadaoDAO;
import sdr.ufscar.dev.srdc.dao.UsuarioDAO;
import sdr.ufscar.dev.srdc.exception.CadastroDuplicadoException;
import sdr.ufscar.dev.srdc.model.Cidadao;
import sdr.ufscar.dev.srdc.model.Usuario;
import sdr.ufscar.dev.srdc.util.AppUtils;

/**
 * Created by Schick on 8/23/16.
 */
public class UsuarioFacade {

    private UsuarioDAO usuarioDAO;

    public UsuarioFacade() {
        usuarioDAO = new UsuarioDAO();
    }

    /**
     * Cadastra um usuário.
     *
     * @param usuario
     * @return se a operação foi realizada com sucesso
     */
    public Boolean cadastrarUsuario(Usuario usuario) {
        if(usuario.getUsername() == null || usuario.getSenha() == null) {
            throw new IllegalArgumentException("Dados do Usuario invalido");
        }
        String senha = AppUtils.digest(usuario.getSenha());
        usuario.setSenha(senha);
        try {
            return usuarioDAO.insert(usuario);
        }catch(CadastroDuplicadoException e) {
            throw e;
        }
    }

    public Boolean removerUsuario(Integer idUsuario) {
        return usuarioDAO.delete(idUsuario);
    }

    /**
     * Verifica se a senha e nome de usuário sao correspondentes e adiciona o idUsuario no objeto
     * passado como argumento.
     * @param usuario
     * @return se a operação foi realizada com sucesso
     */
    public Boolean login(Usuario usuario) {
        if(usuario.getUsername() == null || usuario.getSenha() == null) {
            throw new IllegalArgumentException("Dados do Usuario invalido");
        }
        usuario.setSenha(AppUtils.digest(usuario.getSenha()));
        Usuario u = usuarioDAO.selectPorExemplo(usuario);
        if(u != null) {
            usuario.setIdUsuario(u.getIdUsuario());
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }
}
