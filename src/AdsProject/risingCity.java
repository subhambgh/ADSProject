package AdsProject;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
/*
    It is the entering point of the program.
    Maintains the state of two variables, globalTime and the buildingSelected (the current building being worked upon).
 */
public class risingCity {

    public static int globalTime = 0;
    public static Building buildingSelected;

    public static int incrementGlobalTime(int globalTime) {
        WriteFile.writeLineWithNewLine("Day:" + ++globalTime, 0);
        return globalTime;
    }

    public static void run(Map<Integer, String> lines, DEPQ doubleEndedPQ) {

        if (globalTime == 0) {
            //if input file doesn't starts from 0
            while (lines.get(globalTime) == null) {
                globalTime = incrementGlobalTime(globalTime);
            }
            doOperation(lines.get(globalTime), doubleEndedPQ);
            lines.remove(globalTime);
            //globalTime = incrementGlobalTime(globalTime); //start work from day 1, rather than 0
        }
        while (!doubleEndedPQ.minHeap.isEmpty()) {
            globalTime = incrementGlobalTime(globalTime);
            if (lines.get(globalTime) != null && lines.get(globalTime).toLowerCase().contains("insert")) {
                doOperation(lines.get(globalTime), doubleEndedPQ);
                lines.remove(globalTime);  //added to check discontinued days
            }
            int workStartedOn = globalTime;
            MinHeapNode minHeapNode = doubleEndedPQ.extractMin();
            buildingSelected = minHeapNode.building;
            //days to continue work (either 5 or total_time)
            int continueFor = buildingSelected.getExecutedTime() + 5 <= buildingSelected.getTotalTime()
                    ? 5 : buildingSelected.getTotalTime() - buildingSelected.getExecutedTime();
            while (globalTime < workStartedOn + continueFor) {
                buildingSelected.setExecutedTime(buildingSelected.getExecutedTime() + 1);
                WriteFile.writeLineWithNewLine("Still working on Building: " + buildingSelected.buildingNum + ",executed_time:" + buildingSelected.getExecutedTime(), 0);
                doOperation(lines.get(globalTime), doubleEndedPQ);
                lines.remove(globalTime);  //added to check discontinued days
                globalTime = incrementGlobalTime(globalTime);
            }
            --globalTime;
            //if building is still incomplete add it to the DEPQ, otherwise print it's completed
            if (buildingSelected.getExecutedTime() < buildingSelected.getTotalTime()) {
                WriteFile.writeLineWithNewLine("Building: " + buildingSelected.buildingNum + " Incomplete!!", 0);
                doubleEndedPQ.minHeap.insert(minHeapNode); //already there in RBT, so insert only in minHeap
            } else {
                //if completed delete from RBT also
                WriteFile.writeLineWithNewLine("(" + buildingSelected.buildingNum + "," + (globalTime) + ")", 1);
                doubleEndedPQ.redBlackTree.delete(minHeapNode.redBlackTreeNode);
            }
            /* If the input file contains any discontinued day !!
               detect any discontinued day and increment the global time to that day
               & do the desired operation for that day.
               (where, discontinued day is: when no work is done)
            * */
            while (doubleEndedPQ.minHeap.isEmpty() && lines.size() != 0) {
                int nextDisconnectedDay = Collections.min(lines.keySet());
                globalTime = nextDisconnectedDay - 1;
                doOperation(lines.get(nextDisconnectedDay), doubleEndedPQ);
                lines.remove(nextDisconnectedDay);
            }
        }

    }

    private static void doOperation(String line, DEPQ doubleEndedPQ) {
        if (line != null) {
            if (line.substring(0, line.indexOf("(")).trim().equalsIgnoreCase("Insert")) {
                insert(line, doubleEndedPQ);
            } else if (!line.contains(",")) {
                print(line, doubleEndedPQ);
            } else if (line.contains(",")) {
                printFromTo(line, doubleEndedPQ);
            }
        }
    }

    /*
        Creates a new Building() object, inserts into Red-Black-Tree and Min Heap simultaneously with executedTime = 0.
     */
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

    /*
        Calls the findNode(int buildingNum, RedBlackTreeNode node) implantation of
        RedBlackTree class to get the building details of the corresponding building number
        and prints it to the output file. If there is no building with the associated building number prints (0,0,0).
     */
    public static void print(String line, DEPQ doubleEndedPQ) {
        //Print(buildingNum)
        line = line.substring(line.indexOf("(") + 1);
        line = line.substring(0, line.indexOf(")"));
        int buildingNum = Integer.parseInt(line.trim());
        RedBlackTreeNode redBlackTreeNode = doubleEndedPQ.redBlackTree.findNode(buildingNum, doubleEndedPQ.redBlackTree.root);
        if (redBlackTreeNode == null)
            WriteFile.writeLineWithNewLine("(0,0,0)", 1);
        else
            WriteFile.writeLineWithNewLine("(" + redBlackTreeNode.building.getBuildingNum() +
                    "," + redBlackTreeNode.building.getExecutedTime() +
                    "," + redBlackTreeNode.building.getTotalTime() + ")", 1);

    }

    /*
        Similar to the print function above, prints all the buildingNum in increasing order between the specified range.
        Calls the findNode(RedBlackTreeNode node, int buildingFrom, int buildingTo, ArrayList<RedBlackTreeNode> redBlackTreeNodes)
        implantation of RedBlackTree class. If no building is found prints (0,0,0).
     */
    private static void printFromTo(String line, DEPQ doubleEndedPQ) {
        line = line.substring(line.indexOf("(") + 1);
        line = line.substring(0, line.indexOf(")"));
        int buildingNumFrom = Integer.parseInt(line.split(",")[0].trim());
        int buildingNumTo = Integer.parseInt(line.split(",")[1].trim());
        ArrayList<RedBlackTreeNode> redBlackTreeNodes = new ArrayList<RedBlackTreeNode>();
        doubleEndedPQ.redBlackTree.findNode(
                doubleEndedPQ.redBlackTree.root
                , buildingNumFrom
                , buildingNumTo
                , redBlackTreeNodes);
        if (redBlackTreeNodes.size() == 0) {
            WriteFile.writeLineWithNewLine("(0,0,0)", 1);
        } else {
            for (int i = 0; i < redBlackTreeNodes.size(); i++) {
                WriteFile.writeLine("(" + redBlackTreeNodes.get(i).building.getBuildingNum() +
                        "," + redBlackTreeNodes.get(i).building.getExecutedTime() +
                        "," + redBlackTreeNodes.get(i).building.getTotalTime() + ")", 1);
                if (i != redBlackTreeNodes.size() - 1) {
                    WriteFile.writeLine(",", 1);
                } else {
                    WriteFile.writeLineWithNewLine("", 1);
                }
            }
        }
    }

    /*
        Creates an instance of DEPQ, and calls the Main.run() method.
     */
    public static void main(String[] args) throws IOException {
        Map<Integer, String> lines = ReadFile.readFile(args[0]);
        WriteFile.setOut(new BufferedWriter(new FileWriter("output_file.txt")));
        //0 - prints both, 1 - prints higher priority only
        WriteFile.setPriority(1);
        run(lines, new DEPQ());
        WriteFile.close();
    }
}
