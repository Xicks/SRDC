package sdr.ufscar.dev.srdc.dao;

import java.util.List;

/**
 * Created by Schick on 8/24/16.
 */
public abstract class GenericDAO<T> {

    /**
     * Metódo de inserção.
     * @param o
     * @return se a operação foi realizada com sucesso
     */
    abstract Boolean insert(T o);

    /**
     * Metódo de remoção.
     * @param id
     * @return se a operação foi realizada com sucesso
     */
    abstract Boolean delete(Integer id);

    /**
     * Método de atualização.
     * @param o
     * @return se a operação foi realizada com sucesso
     */
    abstract Boolean update(T o);

    /**
     * Método de seleção.
     * @param id
     * @return objeto
     */
    abstract T select(Integer id);
}
