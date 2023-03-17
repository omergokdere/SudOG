package bingyan.net.demonsudoku.model;

/**
 * Created by Demon on 2015/2/15.
 *
 */
public class SaveInfo {

    private int difficulty;
    private int customPass;
    private int id;
    private String currentTime, record;
    private long usedTime;

    public SaveInfo(int difficulty, int customPass, long usedTime, String currentTime, String record) {
        this.difficulty = difficulty;
        this.customPass = customPass;
        this.usedTime = usedTime;
        this.currentTime = currentTime;
        this.record = record;
    }

    public SaveInfo(){

    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void setCustomPass(int customPass) {
        this.customPass = customPass;
    }

    public void setUsedTime(long  usedTime) {
        this.usedTime = usedTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int getCustomPass() {
        return customPass;
    }

    public long getUsedTime() {

        return usedTime;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public String getRecord() {
        return record;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
