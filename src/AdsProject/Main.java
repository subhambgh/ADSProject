package AdsProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Map;

public class Main {

    public static int globalTime = 0;

    public static int incrementGlobalTime(int globalTime){
        System.out.println("Day:"+ ++globalTime);
        return globalTime;
    }

    public static void run(DEPQ doubleEndedPQ) {
        Map<Integer, String> lines = ReadFile.
                readFile("C:\\Users\\Stefan\\Desktop\\ADS\\input.txt");
        if (globalTime == 0) {
            String line = lines.get(0);
            if (line.substring(0, line.indexOf("(")).trim().equalsIgnoreCase("Insert")) {
                insert(line, doubleEndedPQ);
            } else if (!line.contains(",")) {
                print(line, doubleEndedPQ);
            } else {

            }
            incrementGlobalTime(globalTime);
            lines.remove(0);
        }
        while (!doubleEndedPQ.minHeap.isEmpty()) {
            int workStartedOn = globalTime;
            MinHeapNode minHeapNode = doubleEndedPQ.extractMin();
            Building buildingSelected = minHeapNode.building;
            int continueFor = buildingSelected.getExecutedTime() + 5 < buildingSelected.getTotalTime()
                    ? 5 : buildingSelected.getTotalTime() - buildingSelected.getExecutedTime();
            while (globalTime < workStartedOn + continueFor) {
                globalTime = incrementGlobalTime(globalTime);
                buildingSelected.setExecutedTime(buildingSelected.getExecutedTime()+1);
                System.out.println("Working on Building Number:"+buildingSelected.buildingNum+",executed_time:"+buildingSelected.getExecutedTime());
                doOperation(lines.get(globalTime), doubleEndedPQ);
            }
            if(buildingSelected.getExecutedTime() < buildingSelected.getTotalTime()){
                System.out.println("work incomplete:");
                doubleEndedPQ.insert(minHeapNode);
            }else {
                System.out.println("work completed for:"+buildingSelected.buildingNum);
            }

        }
        //rbtRedBlackBST.printTree(rbtRedBlackBST.root);
        doubleEndedPQ.minHeap.print();
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
        int executed_time = 0;
        int total_time = 0;
        RedBlackTreeNode redBlackTreeNode = doubleEndedPQ.findNode(buildingNum);
        System.out.println("-------------------------------------------------------");
        System.out.println("Printing:");
        System.out.println("BuildingNum:"+redBlackTreeNode.building.getBuildingNum()+
                " ExecutedTime:"+redBlackTreeNode.building.getExecutedTime()+
                " TotalTime:"+redBlackTreeNode.building.getTotalTime());
        System.out.println("-------------------------------------------------------");
        //Print (buildingNum1, buildingNum2)
    }

    private static void printFromTo(String line, DEPQ doubleEndedPQ) {
        line = line.substring(line.indexOf("(") + 1);
        line = line.substring(0, line.indexOf(")"));
        int buildingNumFrom = Integer.parseInt(line.split(",")[0].trim());
        int buildingNumTo = Integer.parseInt(line.split(",")[1].trim());
        doubleEndedPQ.findNode(buildingNumFrom,buildingNumTo);
    }

    public static void main(String[] args) throws FileNotFoundException {
        PrintStream o = new PrintStream(new File("C:\\Users\\Stefan\\Desktop\\ADS\\output.txt"));
        PrintStream console = System.out;
        System.setOut(o);

        DEPQ doubleEndedPQ = new DEPQ();
        run(doubleEndedPQ);
    }
}
