package sdr.ufscar.dev.srdc.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * Created by Schick on 8/26/16.
 */
public class DadosClinicos {

    private Integer idDadosClinicos;
    private String cnsProfissional;
    private String cnes;
    private Date dataRegistro;
    private Integer altura;
    private ArrayList<DoencaEnum> doencas;
    private ArrayList<Registro> registros;
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

    public ArrayList<DoencaEnum> getDoencas() {
        return doencas;
    }

    public void setDoencas(ArrayList<DoencaEnum> doencas) {
        this.doencas = doencas;
    }

    public ArrayList<Registro> getRegistros() {
        return registros;
    }

    public void setRegistros(ArrayList<Registro> registros) {
        this.registros = registros;
    }

    public ArrayList<DiasEnum> getDiasColetaEnum() {
        StringTokenizer tokenizer = new StringTokenizer(diasColeta);
        ArrayList<DiasEnum> dias = new ArrayList<>(7);
        while(tokenizer.hasMoreTokens()){
            dias.add(DiasEnum.valueOf(tokenizer.nextToken()));
        }
        return dias;
    }

    public String getDiasColeta() {
        return diasColeta;
    }

    public void setDiasColeta(ArrayList<DiasEnum> diasColeta) {
        StringBuilder sb = new StringBuilder();
        for(DiasEnum dia: diasColeta) {
            sb.append(dia.getDia());
            sb.append(" ");
        }
        this.diasColeta = new String(sb);
    }

    public void setDiasColeta(String diasColeta) {
        this.diasColeta = diasColeta;
    }

    public ArrayList<Integer> getHorasColeta() {
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
