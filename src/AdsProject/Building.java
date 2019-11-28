package AdsProject;
/*
    A pojo class containing all the three building records.
    Single building reference is stored in both the Red-Black-Tree and MinHeap node,
    reducing the space complexity of the program.

 */
public class Building {
    int buildingNum;    // unique integer identifier for each building.
    int executedTime;   // total number of days spent so far on this building
    int totalTime;      // total number of days needed to complete the construction of the building


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
