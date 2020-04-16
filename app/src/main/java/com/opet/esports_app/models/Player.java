package com.opet.esports_app.models;

public class Player {
    private long id;
    private String playerName;
    private String nickName;
    private String teamName;
    private String role;
    private long totalKills;
    private long totalAssists;
    private long totalDeaths;
    private long totalMatchs;
    private long totalVictories;
    private Double kda;
    private Double winningRate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public long getTotalKills() {
        return totalKills;
    }

    public void setTotalKills(long totalKills) {
        this.totalKills = totalKills;
    }

    public long getTotalAssists() {
        return totalAssists;
    }

    public void setTotalAssists(long totalAssists) {
        this.totalAssists = totalAssists;
    }

    public long getTotalDeaths() {
        return totalDeaths;
    }

    public void setTotalDeaths(long totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    public long getTotalMatchs() {
        return totalMatchs;
    }

    public void setTotalMatchs(long totalMatchs) {
        this.totalMatchs = totalMatchs;
    }

    public long getTotalVictories() {
        return totalVictories;
    }

    public void setTotalVictories(long totalVictories) {
        this.totalVictories = totalVictories;
    }

    public Double getKda() {
        return kda;
    }

    public void setKda(Double kda) {
        this.kda = kda;
    }

    public Double getWinningRate() {
        return winningRate;
    }

    public void setWinningRate(Double winningRate) {
        this.winningRate = winningRate;
    }

    @Override
    public String toString(){
        return playerName;
    }
}
