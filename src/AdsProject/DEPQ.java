package AdsProject;

import java.util.ArrayList;

public class DEPQ {
    public RedBlackBST redBlackBST;
    public MinHeap minHeap;

    public DEPQ(){
        this.redBlackBST = new RedBlackBST();
        this.minHeap = new MinHeap();
    }

    public void insert(MinHeapNode minHeapNode) {
        /* Insert(buildingNum ,total_time) should produce no output unless buildingNum is a duplicate in which case you should output
           an error and stop. */
        if(findNode(minHeapNode.building.buildingNum)!=null){
            WriteFile.writeLineWithNewLine("Error: Duplicate Building Number !!",1);
            System.exit(0);
        }
        //RBT ops
        RedBlackTreeNode redBlackTreeNode = new RedBlackTreeNode(minHeapNode.building);
        redBlackTreeNode.minHeapNode = minHeapNode;
        redBlackBST.insert(redBlackTreeNode);
        //Min Heap ops
        minHeapNode.redBlackTreeNode = redBlackTreeNode;
        minHeap.insert(minHeapNode);
    }

    public MinHeapNode extractMin(){
        MinHeapNode minHeapNode = minHeap.extractMin();
        //redBlackBST.delete(minHeapNode.redBlackTreeNode);
        return minHeapNode;
    }

    public RedBlackTreeNode findNode(int buildingNum) {
        return redBlackBST.findNode(new RedBlackTreeNode(new Building(buildingNum,-1,-1)),redBlackBST.root);
    }

    public void findNode(int buildingNumFrom,int buildingNumTo, ArrayList<RedBlackTreeNode> redBlackTreeNodes) {
        redBlackBST.findNode(redBlackBST.root,new RedBlackTreeNode(new Building(buildingNumFrom,-1,-1))
                ,new RedBlackTreeNode(new Building(buildingNumTo,-1,-1)),redBlackTreeNodes);

    }
}
