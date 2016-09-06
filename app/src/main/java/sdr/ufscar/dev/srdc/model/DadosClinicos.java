package sdr.ufscar.dev.srdc.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import sdr.ufscar.dev.srdc.enumeration.DiaEnum;
import sdr.ufscar.dev.srdc.enumeration.DoencaEnum;

/**
 * Created by Schick on 8/26/16.
 */
public class DadosClinicos {

    private Integer idDadosClinicos;
    private String cnsProfissional;
    private String cnes;
    private Date dataRegistro;
    private Integer altura;
    private Integer peso;
    private ArrayList<DoencaEnum> doencas;
    private ArrayList<RegistroColeta> registrosColeta;
    private ArrayList<RegistroAtividadeFisica> registrosAtividadeFisica;
    private String diasColeta;
    private ArrayList<Integer> horasColeta;
    private String observacoes;
    private Boolean enviarNotificacao;

    public Integer getIdDadosClinicos() {
        return idDadosClinicos;
    }

    public void setIdDadosClinicos(Integer idDadosClinicos) {
        this.idDadosClinicos = idDadosClinicos;
    }

    public String getCnsProfissional() {
        return cnsProfissional;
    }

    public void setCnsProfissional(String cnsProfissional) {
        this.cnsProfissional = cnsProfissional;
    }

    public String getCnes() {
        return cnes;
    }

    public void setCnes(String cnes) {
        this.cnes = cnes;
    }

    public Date getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(Date dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public Integer getAltura() {
        return altura;
    }

    public void setAltura(Integer altura) {
        this.altura = altura;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    public ArrayList<DoencaEnum> getDoencas() {
        return doencas;
    }

    public void setDoencas(ArrayList<DoencaEnum> doencas) {
        this.doencas = doencas;
    }

    public ArrayList<RegistroColeta> getRegistrosColeta() {
        return registrosColeta;
    }

    public void setRegistrosColeta(ArrayList<RegistroColeta> registrosColeta) {
        this.registrosColeta = registrosColeta;
    }

    public ArrayList<RegistroAtividadeFisica> getRegistrosAtividadeFisica() {
        return registrosAtividadeFisica;
    }

    public void setRegistrosAtividadeFisica(ArrayList<RegistroAtividadeFisica> registrosAtividadeFisica) {
        this.registrosAtividadeFisica = registrosAtividadeFisica;
    }

    public ArrayList<DiaEnum> getDiasColetaEnum() {
        if(diasColeta != null) {
            StringTokenizer tokenizer = new StringTokenizer(diasColeta);
            ArrayList<DiaEnum> dias = new ArrayList<>(7);
            while (tokenizer.hasMoreTokens()) {
                dias.add(DiaEnum.valueOf(tokenizer.nextToken()));
            }
            return dias;
        } else {
            return new ArrayList<DiaEnum>(0);
        }

    }

    public String getDiasColeta() {
        return diasColeta;
    }

    public void setDiasColeta(ArrayList<DiaEnum> diasColeta) {
        StringBuilder sb = new StringBuilder();
        for(DiaEnum dia: diasColeta) {
            sb.append(dia.getDia());
            sb.append(" ");
        }
        this.diasColeta = new String(sb);
    }

    public void setDiasColeta(String diasColeta) {
        this.diasColeta = diasColeta;
    }

    public ArrayList<Integer> getHorasColeta(){
        return horasColeta;
    }

    public void setHorasColeta(ArrayList<Integer> horasColeta) {
        this.horasColeta = horasColeta;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Boolean getEnviarNotificacao() {
        return enviarNotificacao;
    }

    public void setEnviarNotificacao(Boolean enviarNotificacao) {
        this.enviarNotificacao = enviarNotificacao;
    }
}
