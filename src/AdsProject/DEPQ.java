package AdsProject;

import java.util.ArrayList;
/*
    This class is used to instantiate single instances of RBT and MinHeap both simultaneously.
    And is also used for simultaneous insertion (keeps correspondence between nodes of RBT and MinHeap containing the same building).
 */
public class DEPQ {
    public RedBlackTree redBlackTree;
    public MinHeap minHeap;

    public DEPQ(){
        this.redBlackTree = new RedBlackTree();
        this.minHeap = new MinHeap();
    }

    /*
        Function is used for the simultaneous insertion of RBT and MinHeap nodes.
        Also checks and stops the program in case of a duplicate building number.
     */
    public void insert(MinHeapNode minHeapNode) {
        /* Insert(buildingNum ,total_time) should produce no output unless buildingNum is a duplicate in which case you should output
           an error and stop. */
        if(redBlackTree.findNode(minHeapNode.building.getBuildingNum(),redBlackTree.root)!=null){
            WriteFile.writeLineWithNewLine("Error: Duplicate Building Number !!",1);
            System.exit(0);
        }
        //RBT ops
        RedBlackTreeNode redBlackTreeNode = new RedBlackTreeNode(minHeapNode.building);
        redBlackTreeNode.minHeapNode = minHeapNode;
        redBlackTree.insert(redBlackTreeNode);
        //Min Heap ops
        minHeapNode.redBlackTreeNode = redBlackTreeNode;
        minHeap.insert(minHeapNode);
    }

    public MinHeapNode extractMin(){
        MinHeapNode minHeapNode = minHeap.extractMin();
        //redBlackBST.delete(minHeapNode.redBlackTreeNode);
        return minHeapNode;
    }
}
