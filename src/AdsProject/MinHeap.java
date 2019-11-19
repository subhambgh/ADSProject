package AdsProject;

import java.util.ArrayList;

class MinHeapNode {
    Building building;
    RedBlackTreeNode redBlackTreeNode;

    MinHeapNode(Building building) {
        this.building = building;
    }
}

public class MinHeap {

    public ArrayList<MinHeapNode> list;

    public MinHeap() {

        this.list = new ArrayList<MinHeapNode>();
    }

    public MinHeap(ArrayList<MinHeapNode> items) {

        this.list = items;
        buildHeap();
    }

    public void insert(MinHeapNode node) {
        list.add(node);
        int i = list.size() - 1;
        int parent = parent(i);

        while (parent != i && list.get(i).building.getExecutedTime() <= list.get(parent).building.getExecutedTime()) {
            if (list.get(i).building.getExecutedTime() < list.get(parent).building.getExecutedTime()) {
                swap(i, parent);
                i = parent;
                parent = parent(i);
            } else {
                //if equal
                if(list.get(i).building.getBuildingNum() < list.get(parent).building.getBuildingNum()){
                    swap(i, parent);
                    i = parent;
                    parent = parent(i);
                }
                else{break;}
            }
        }
        //insert
        System.out.println("-----------------------------------------");
        System.out.println("inserting building no:"+node.building.getBuildingNum());
        print();
    }

    public void buildHeap() {

        for (int i = list.size() / 2; i >= 0; i--) {
            minHeapify(i);
        }
    }

    public MinHeapNode extractMin() {

        if (list.size() == 0) {

            throw new IllegalStateException("MinHeap is EMPTY");
        } else if (list.size() == 1) {

            MinHeapNode min = list.remove(0);
            return min;

        }

        // remove the last item ,and set it as new root
        MinHeapNode min = list.get(0);
        MinHeapNode lastItem = list.remove(list.size() - 1);
        list.set(0, lastItem);

        // bubble-down until heap property is maintained
        minHeapify(0);

        // return min key
        return min;
    }

    public void decreaseKey(int i, int key) {

        if (list.get(i).building.getExecutedTime() < key) {

            throw new IllegalArgumentException("Key is larger than the original key");
        }
        list.get(i).building.setExecutedTime(key);
        int parent = parent(i);

        // bubble-up until heap property is maintained
        while (i > 0 && list.get(parent).building.getExecutedTime() >= list.get(i).building.getExecutedTime()) {
            if (list.get(i).building.getExecutedTime() == list.get(parent).building.getExecutedTime()
                    && list.get(i).building.getBuildingNum() < list.get(parent).building.getBuildingNum()) {
                swap(i, parent);
                i = parent;
                parent = parent(i);
            } else {
                swap(i, parent);
                i = parent;
                parent = parent(parent);
            }
        }
    }

    private void minHeapify(int i) {

        int left = left(i);
        int right = right(i);
        int smallest = -1;

        // find the smallest key between current node and its children.
        if (left <= list.size() - 1 && list.get(left).building.getExecutedTime() <= list.get(i).building.getExecutedTime()) {
            if(list.get(left).building.getExecutedTime() == list.get(i).building.getExecutedTime()
                && list.get(left).building.getBuildingNum() > list.get(i).building.getBuildingNum()){
                smallest = i;
            } else {
                smallest = left;
            }
        } else {
            smallest = i;
        }

        if (right <= list.size() - 1 && list.get(right).building.getExecutedTime() <= list.get(smallest).building.getExecutedTime()) {
            if(list.get(right).building.getExecutedTime() < list.get(smallest).building.getExecutedTime()){
                smallest = right;
            }else {
                //if equal - check building number
                if(list.get(right).building.getBuildingNum() < list.get(smallest).building.getBuildingNum()){
                    smallest = right;
                }
            }
        }

        // if the smallest key is not the current key then bubble-down it.
        if (smallest != i) {

            swap(i, smallest);
            minHeapify(smallest);
        }
    }

    public MinHeapNode getMin() {

        return list.get(0);
    }

    public boolean isEmpty() {

        return list.size() == 0;
    }

    private int right(int i) {

        return 2 * i + 2;
    }

    private int left(int i) {

        return 2 * i + 1;
    }

    private int parent(int i) {

        if (i % 2 == 1) {
            return i / 2;
        }

        return (i - 1) / 2;
    }

    private void swap(int i, int parent) {

        MinHeapNode temp = list.get(parent);
        list.set(parent, list.get(i));
        list.set(i, temp);
    }

    public void print() {
        for (MinHeapNode minHeapNode : list) {
            System.out.println("BuildingNum:" + minHeapNode.building.getBuildingNum() +
                    " ExecutedTime:" + minHeapNode.building.getExecutedTime() +
                    " TotalTime:" + minHeapNode.building.getTotalTime());
        }
        System.out.println("-------------------------------------");
        /*
        for (int i = 1; i <= list.size() / 2; i++) {
            System.out.print(" PARENT : " + list.get(i).executed_time
                    + " LEFT CHILD : " + list.get(2 * i).executed_time
                    + " RIGHT CHILD :" + list.get(2 * i + 1).executed_time);
            System.out.println();
        }*/
    }

}

