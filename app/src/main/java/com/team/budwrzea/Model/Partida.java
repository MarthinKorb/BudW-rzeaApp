package com.team.budwrzea.Model;

import java.sql.Time;
import java.util.Date;

public class Partida {

    private String idTime;
    private  String nomeTime;
    private String localPartida;
    private String dataPartida;
    private String horarioPartida;
    private long timeStamp;

    public Partida() {
    }

    public String getIdTime() {
        return idTime;
    }

    public void setIdTime(String idTime) {
        this.idTime = idTime;
    }

    public String getNomeTime() {
        return nomeTime;
    }

    public void setNomeTime(String nomeTime) {
        this.nomeTime = nomeTime;
    }

    public String getLocalPartida() {
        return localPartida;
    }

    public void setLocalPartida(String localPartida) {
        this.localPartida = localPartida;
    }

    public String getDataPartida() {
        return dataPartida;
    }

    public void setDataPartida(String dataPartida) {
        this.dataPartida = dataPartida;
    }

    public String getHorarioPartida() {
        return horarioPartida;
    }

    public void setHorarioPartida(String horarioPartida) {
        this.horarioPartida = horarioPartida;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "Partida:" +
                "Equipe: '" + nomeTime + '\'' +
                ", Data da Partida=" + dataPartida +
                ", Horário da Partida='" + horarioPartida + '\'';
    }
}
