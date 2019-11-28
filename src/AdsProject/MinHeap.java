package AdsProject;

import java.util.ArrayList;
/*
    A Min Heap implementation based on executedTime, contains two classes
    1.	MinHeapNode
    2.	MinHeap
    MinHeapNode also has a compareTo implementation – which compares two MinHeapNode based on the executedTime.
    It is also used to break ties, comparing buildingNum when executedTime are equal.
    MinHeap class is implemented using Arraylist<MinHeapNode>.

 */
class MinHeapNode {
    Building building;
    RedBlackTreeNode redBlackTreeNode;

    MinHeapNode(Building building) {
        this.building = building;
    }

    public int compareTo(MinHeapNode otherMinHeap) {
        int thisBuilding = this.building.getExecutedTime();
        int otherBuilding = otherMinHeap.building.getExecutedTime();
        //breaking ties
        if (thisBuilding == otherBuilding) {
            thisBuilding = this.building.getBuildingNum();
            otherBuilding = otherMinHeap.building.getBuildingNum();
        }
        //ascending order
        //returns true if execution_time/building_number of this is greater than other building
        return thisBuilding - otherBuilding;
    }
}

public class MinHeap {

    public ArrayList<MinHeapNode> listOfNodes;

    public MinHeap() {
        this.listOfNodes = new ArrayList<MinHeapNode>();
    }

    /*
        Adds an element at the end of the arraylist, recursively compares itself
        with its parent element.If parent is greater, swaps the element and its parent.
        Continues the same until parent is smaller than child.
     */
    public void insert(MinHeapNode node) {
        listOfNodes.add(node);
        int currentPos = listOfNodes.size() - 1;
        int parentPos = parentPos(currentPos);

        while (parentPos != currentPos && listOfNodes.get(currentPos).compareTo(listOfNodes.get(parentPos))<0) {
            //swapNodes
            MinHeapNode temp = listOfNodes.get(parentPos);
            listOfNodes.set(parentPos, listOfNodes.get(currentPos));
            listOfNodes.set(currentPos, temp);
            //
            currentPos = parentPos;
            parentPos = parentPos(currentPos);
        }

        //insert
        WriteFile.writeLineWithNewLine("-----------------------------------------",0);
        WriteFile.writeLineWithNewLine("inserting building no:" + node.building.getBuildingNum(),0);
        printMinHeap();
    }

    /*
        Removes the 0th element (smallest executedTime), replace it with the last
        element of the arraylist, calls heapify(0). Also, returns the removed Node.
     */
    public MinHeapNode extractMin() {

        if (listOfNodes.size() == 0) {
            throw new RuntimeException("No building in the queue.");
        } else if (listOfNodes.size() == 1) {
            MinHeapNode min = listOfNodes.remove(0);
            return min;
        }

        MinHeapNode minNode = listOfNodes.get(0);
        MinHeapNode lastNode = listOfNodes.remove(listOfNodes.size() - 1);
        listOfNodes.set(0, lastNode);

        heapify(0);

        // return min key
        return minNode;
    }

    /*
        Starts at input i, find the smallest element between the current node and its children.
        If the smallest key is not the current key, then swap it with the smallest, and calls heapify(smallest).
        Bubble down until the min heap property is maintained.
     */
    private void heapify(int i) {

        int leftPos = leftChildPos(i);
        int rightPos = rightChildPos(i);
        int smallestNodePos = -1;

        if (leftPos <= listOfNodes.size() - 1 && listOfNodes.get(leftPos).compareTo(listOfNodes.get(i))<0) {
            smallestNodePos = leftPos;
        } else {
            smallestNodePos = i;
        }

        if (rightPos <= listOfNodes.size() - 1 && listOfNodes.get(rightPos).compareTo(listOfNodes.get(smallestNodePos))<0) {
            smallestNodePos = rightPos;
        }

        if (smallestNodePos != i) {
            //swapNodes
            MinHeapNode temp = listOfNodes.get(smallestNodePos);
            listOfNodes.set(smallestNodePos, listOfNodes.get(i));
            listOfNodes.set(i, temp);
            //
            heapify(smallestNodePos);
        }
    }

    public boolean isEmpty() {
        return listOfNodes.size() == 0;
    }

    private int rightChildPos(int pos) {
        return 2 * pos + 2;
    }

    private int leftChildPos(int pos) {
        return 2 * pos + 1;
    }

    private int parentPos(int pos) {
        if (pos % 2 == 1) {
            return pos / 2;
        }
        return (pos - 1) / 2;
    }

    public void printMinHeap() {
        for (MinHeapNode minHeapNode : listOfNodes) {
            WriteFile.writeLineWithNewLine("BuildingNum:" + minHeapNode.building.getBuildingNum() +
                    " ExecutedTime:" + minHeapNode.building.getExecutedTime() +
                    " TotalTime:" + minHeapNode.building.getTotalTime(),0);
        }
        WriteFile.writeLineWithNewLine("-------------------------------------",0);
    }

}

