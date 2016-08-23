package sdr.ufscar.dev.srdc.facade;

import sdr.ufscar.dev.srdc.dao.UsuarioDAO;
import sdr.ufscar.dev.srdc.model.Usuario;
import sdr.ufscar.dev.srdc.util.AppUtils;

/**
 * Created by Schick on 8/23/16.
 */
public class UsuarioFacade {

    /**
     * Cadastra um usuario
     *
     * @param usuario
     * @return resultado da operacao
     */
    public Boolean cadastrarUsuario(Usuario usuario) {
        if(usuario.getUsername() == null || usuario.getSenha() == null) {
            throw new IllegalArgumentException("Dados do Usuario invalido");
        }
        String senha = AppUtils.digest(usuario.getSenha());
        usuario.setSenha(senha);
        return new UsuarioDAO().insert(usuario);
    }

    /**
     * Loga um usuario
     * @param usuario
     * @return 
     */
    public Boolean login(Usuario usuario) {
        if(usuario.getUsername() == null || usuario.getSenha() == null) {
            throw new IllegalArgumentException("Dados do Usuario invalido");
        }
        Usuario u = new UsuarioDAO().select(usuario);
        return u != null;
    }
}
