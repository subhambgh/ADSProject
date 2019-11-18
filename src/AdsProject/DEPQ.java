package AdsProject;

public class DEPQ {
    RedBlackBST redBlackBST;
    MinHeap minHeap;

    public DEPQ(){
        this.redBlackBST = new RedBlackBST();
        this.minHeap = new MinHeap();
    }

    public void insert(MinHeapNode minHeapNode){
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
        redBlackBST.delete(minHeapNode.redBlackTreeNode);
        return minHeapNode;
    }

    public RedBlackTreeNode findNode(int buildingNum) {
        return redBlackBST.findNode(new RedBlackTreeNode(new Building(buildingNum,-1,-1)),redBlackBST.root);
    }

    public void findNodeFromTo(int buildingNumFrom,int buildingNumTo) {
        redBlackBST.findNode(new RedBlackTreeNode(new Building(buildingNumFrom,-1,-1))
                ,new RedBlackTreeNode(new Building(buildingNumFrom,-1,-1)));
    }
}
