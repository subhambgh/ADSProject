package AdsProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class Main {

    public static int globalTime = 0;
    public static Building buildingSelected;

    public static int incrementGlobalTime(int globalTime){
        System.out.println("Day:"+ ++globalTime);
        return globalTime;
    }

    public static void run(DEPQ doubleEndedPQ) {
        Map<Integer, String> lines = ReadFile.
                readFile("C:\\Users\\Stefan\\Desktop\\ADS\\input.txt");
        if (globalTime == 0) {
            //if input file doesn't starts from 0
            while(lines.get(globalTime)==null){
                globalTime = incrementGlobalTime(globalTime);
            }
            doOperation(lines.get(globalTime),doubleEndedPQ);
            lines.remove(globalTime);
            globalTime = incrementGlobalTime(globalTime); //start work from day 1, rather than 0
        }
        while (!doubleEndedPQ.minHeap.isEmpty()) {
            int workStartedOn = globalTime;
            doOperation(lines.get(globalTime), doubleEndedPQ);
            lines.remove(globalTime); //added to check discontinued days
            MinHeapNode minHeapNode = doubleEndedPQ.extractMin();
            buildingSelected = minHeapNode.building;
            //days to continue work (either 5 or total_time)
            int continueFor = buildingSelected.getExecutedTime() + 5 <= buildingSelected.getTotalTime()
                    ? 5 : buildingSelected.getTotalTime() - buildingSelected.getExecutedTime();
            while (globalTime < workStartedOn + continueFor) {
                buildingSelected.setExecutedTime(buildingSelected.getExecutedTime()+1);
                System.out.println("Still working on Building: "+buildingSelected.buildingNum+",executed_time:"+buildingSelected.getExecutedTime());
                doOperation(lines.get(globalTime), doubleEndedPQ);
                lines.remove(globalTime);  //added to check discontinued days
                globalTime = incrementGlobalTime(globalTime);
            }
            //if building is still incomplete add it to the DEPQ, otherwise print it's completed
            if(buildingSelected.getExecutedTime() < buildingSelected.getTotalTime()){
                System.out.println("Building: "+buildingSelected.buildingNum+" Incomplete!!");
                doubleEndedPQ.insert(minHeapNode);
            }else {
                System.out.println("Building: "+buildingSelected.buildingNum+" Completed!!");
            }
            /* If the input file contains any discontinued day !!
               detect any discontinued day and increment the global time to that day
               & do the desired operation on that day.
               where, discontinued day is: when no work is done
            * */
            if(doubleEndedPQ.minHeap.isEmpty() && lines.size()!=0){
                int nextDisconnectedDay = Collections.min(lines.keySet());
                globalTime = nextDisconnectedDay;
                doOperation(lines.get(nextDisconnectedDay),doubleEndedPQ);
                lines.remove(nextDisconnectedDay);
            }
        }

    }

    private static void doOperation(String line, DEPQ doubleEndedPQ) {
        if(line!=null){
            if (line.substring(0, line.indexOf("(")).trim().equalsIgnoreCase("Insert")) {
                insert(line, doubleEndedPQ);
            } else if (!line.contains(",")) {
                print(line, doubleEndedPQ);
            } else if (line.contains(",")) {
                printFromTo(line,doubleEndedPQ);
            }
        }
    }

    public static void insert(String line, DEPQ doubleEndedPQ) {
        //Insert(buildingNum, total_time)
        line = line.substring(line.indexOf("(") + 1);
        line = line.substring(0, line.indexOf(")"));
        String[] buildingNum_totalTime = line.split(",");
        //
        int buildingNum = Integer.parseInt(buildingNum_totalTime[0].trim());
        int executed_time = 0;
        int total_time = Integer.parseInt(buildingNum_totalTime[1].trim());
        //
        Building newBuilding = new Building(buildingNum, executed_time, total_time);
        MinHeapNode minHeapNode = new MinHeapNode(newBuilding);
        doubleEndedPQ.insert(minHeapNode);
    }

    public static void print(String line, DEPQ doubleEndedPQ) {
        //Print(buildingNum)
        line = line.substring(line.indexOf("(") + 1);
        line = line.substring(0, line.indexOf(")"));
        int buildingNum = Integer.parseInt(line.trim());
        //if building number is the building currently worked upon
        if(buildingNum == buildingSelected.getBuildingNum()){
            System.out.println("(" + buildingSelected.getBuildingNum() +
                    "," + buildingSelected.getExecutedTime() +
                    "," + buildingSelected.getTotalTime() + ")");
            return;
        }
        RedBlackTreeNode redBlackTreeNode = doubleEndedPQ.findNode(buildingNum);
        if(redBlackTreeNode == null)
            System.out.println("(0,0,0)");
        else
            System.out.println("(" + redBlackTreeNode.building.getBuildingNum() +
                "," + redBlackTreeNode.building.getExecutedTime() +
                "," + redBlackTreeNode.building.getTotalTime() + ")");

    }

    //Print (buildingNum1, buildingNum2)
    private static void printFromTo(String line, DEPQ doubleEndedPQ) {
        line = line.substring(line.indexOf("(") + 1);
        line = line.substring(0, line.indexOf(")"));
        int buildingNumFrom = Integer.parseInt(line.split(",")[0].trim());
        int buildingNumTo = Integer.parseInt(line.split(",")[1].trim());
        ArrayList<RedBlackTreeNode> redBlackTreeNodes = new ArrayList<RedBlackTreeNode>();
        doubleEndedPQ.findNode(buildingNumFrom,buildingNumTo,redBlackTreeNodes);
        //if building range also includes the building currently worked upon
        if(buildingSelected.getBuildingNum() >= buildingNumFrom
            && buildingSelected.getBuildingNum() <= buildingNumTo){
            redBlackTreeNodes.add(new RedBlackTreeNode(buildingSelected));
        }
        if(redBlackTreeNodes.size()==0){
            System.out.println("(0,0,0)");
        }else{
            for (int i=0;i<redBlackTreeNodes.size();i++){
                System.out.print("(" + redBlackTreeNodes.get(i).building.getBuildingNum() +
                        "," + redBlackTreeNodes.get(i).building.getExecutedTime() +
                        "," + redBlackTreeNodes.get(i).building.getTotalTime() + ")");
                if(i!=redBlackTreeNodes.size()-1){
                    System.out.print(",");
                }else{
                    System.out.println("");
                }
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        PrintStream o = new PrintStream(new File("C:\\Users\\Stefan\\Desktop\\ADS\\output.txt"));
        PrintStream console = System.out;
        System.setOut(o);
        run(new DEPQ());
    }
}
