package AdsProject;

public class Building {
    int buildingNum;
    int executedTime;
    int totalTime;

    public Building() {
        this.buildingNum = -1;
        this.executedTime = -1;
        this.totalTime = -1;
    }

    public Building(int buildingNum, int executedTime, int totalTime) {
        this.buildingNum = buildingNum;
        this.executedTime = executedTime;
        this.totalTime = totalTime;
    }

    public int getBuildingNum() {
        return buildingNum;
    }

    public void setBuildingNum(int buildingNum) {
        this.buildingNum = buildingNum;
    }

    public int getExecutedTime() {
        return executedTime;
    }

    public void setExecutedTime(int executedTime) {
        this.executedTime = executedTime;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }
}
