package RC;

import java.io.Serializable;

public class model implements Serializable{

    private final int uid;
    private final String team1,team2,dtg;
    private final boolean ss;
    private final String toss;
    private final String wt;
    private final Boolean squad;

    public model(int uid, String team1, String team2, String dtg, boolean ss, String t, String win_team, Boolean squad) {
        this.uid = uid;
        this.team1 = team1;
        this.team2 = team2;
        this.dtg = dtg;
        this.ss = ss;
        this.toss = t;
        this.wt = win_team;
        this.squad = squad;
    }


    public boolean isSs() {
        return ss;
    }

    public int getUid() {
        return uid;
    }

    public String getTeam1() {
        return team1;
    }

    public  String getTeam2() {
        return team2;
    }

    public String getDtg() {
        return dtg;
    }

    public String getWt() {
        return wt;
    }

    public String getToss() {
        return toss;
    }

    public Boolean getSquad() {
        return squad;
    }
}
