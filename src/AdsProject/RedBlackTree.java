package AdsProject;

import java.util.ArrayList;
/*
    A Red Black Tree implementation based on buildingNum, contains two classes
    1.	RedBlackTreeNode
    2.	RedBlackTree
    RedBlackTreeNode also has a compareTo implementation – which compares two RedBlackTreeNode based on the buildingNum.
 */
class RedBlackTreeNode {

    Building building;
    MinHeapNode minHeapNode;
    int color = RedBlackTree.BLACK_NODE;
    RedBlackTreeNode left = RedBlackTree.nil, right = RedBlackTree.nil, parent = RedBlackTree.nil;

    RedBlackTreeNode(Building building) {
        this.building = building;
    }

    public int compareTo(RedBlackTreeNode otherRedBlackTreeNode) {
        int thisBuilding = this.building.getBuildingNum();
        int otherBuilding = otherRedBlackTreeNode.building.getBuildingNum();
        //ascending order
        //returns true if building_number of this is greater than other building
        return thisBuilding - otherBuilding;
    }
}

public class RedBlackTree {

    public static final int RED_NODE = 0;
    public static final int BLACK_NODE = 1;

    public static final RedBlackTreeNode nil = new RedBlackTreeNode(new Building(-1,-1,-1));
    public RedBlackTreeNode root = nil;

    public void printRBT(RedBlackTreeNode node) {
        if (node == nil) {
            return;
        }
        printRBT(node.left);
        WriteFile.writeLineWithNewLine(((node.color == RED_NODE) ? "Color: Red " : "Color: Black ") + "Key: " + node.building.getBuildingNum() + " Parent: " + node.parent.building.getBuildingNum() + "\n",0);
        printRBT(node.right);
    }

    /*
        Takes two parameters buildingNum (building to find) and node (root) and
        returns the RedBlackTreeNode with same building number as buildingNum.
     */
    public RedBlackTreeNode findNode(int buildingNum, RedBlackTreeNode node) {
        if (root == nil) {
            return null;
        }
        if (buildingNum < node.building.getBuildingNum()) {
            if (node.left != nil) {
                return findNode(buildingNum, node.left);
            }
        } else if (buildingNum > node.building.getBuildingNum()) {
            if (node.right != nil) {
                return findNode(buildingNum, node.right);
            }
        } else if (buildingNum == node.building.getBuildingNum()) {
            return node;
        }
        return null;
    }

    /*
        Takes four parameters buildingFrom < buildingTo (range of building to find)
        and node (root) and returns the arraylist of RedBlackTreeNode with building numbers in the range.
     */
    public void findNode(RedBlackTreeNode node, int buildingFrom, int buildingTo, ArrayList<RedBlackTreeNode> redBlackTreeNodes) {
        if (root == nil || buildingFrom >buildingTo) {
            return;
        }
        if (buildingFrom < node.building.getBuildingNum()) {
            if (node.left != nil) {
                findNode(node.left, buildingFrom, buildingTo,redBlackTreeNodes);
            }
        }
        if (buildingFrom <= node.building.getBuildingNum()
                && buildingTo >= node.building.getBuildingNum()) {
            redBlackTreeNodes.add(node);
        }
        if (buildingTo > node.building.getBuildingNum()) {
            if (node.right != nil) {
                findNode(node.right, buildingFrom, buildingTo,redBlackTreeNodes);
            }
        }
    }

    /*
        if root is nil - inserts the node as black root
        else, insert it as a red node, like a binary search tree insertion
        and calls fixTree() on the inserted node.
     */
    public void insert(RedBlackTreeNode node) {
        RedBlackTreeNode temp = root;
        if (root == nil) {
            root = node;
            node.color = BLACK_NODE;
            node.parent = nil;
        } else {
            node.color = RED_NODE;
            while (true) {
                if (node.compareTo(temp)<0) {
                    if (temp.left == nil) {
                        temp.left = node;
                        node.parent = temp;
                        break;
                    } else {
                        temp = temp.left;
                    }
                } else if (node.compareTo(temp)>=0) {
                    if (temp.right == nil) {
                        temp.right = node;
                        node.parent = temp;
                        break;
                    } else {
                        temp = temp.right;
                    }
                }
            }
            fixTree(node);
        }
    }

    /*
    Takes as argument newly inserted RedBlackTreeNode node and evaluate two cases if node’s parent is red –
    Case 1.	 If node’s parent’s sibling is black or null
    Case 2. If node’s parent’s sibling is red
     */
    private void fixTree(RedBlackTreeNode node) {
        while (node.parent.color == RED_NODE) {
            RedBlackTreeNode parentsSibling = nil;
            if (node.parent == node.parent.parent.left) { //if nodes's parent is a left node
                parentsSibling = node.parent.parent.right;

                if (parentsSibling != nil && parentsSibling.color == RED_NODE) {
                    node.parent.color = BLACK_NODE;
                    parentsSibling.color = BLACK_NODE;
                    node.parent.parent.color = RED_NODE;
                    node = node.parent.parent;
                    continue;
                }
                if (node == node.parent.right) {
                    //Double rotation needed
                    node = node.parent;
                    rotateLeft(node);
                }
                node.parent.color = BLACK_NODE;
                node.parent.parent.color = RED_NODE;
                rotateRight(node.parent.parent);
            } else {
                parentsSibling = node.parent.parent.left;
                if (parentsSibling != nil && parentsSibling.color == RED_NODE) {
                    node.parent.color = BLACK_NODE;
                    parentsSibling.color = BLACK_NODE;
                    node.parent.parent.color = RED_NODE;
                    node = node.parent.parent;
                    continue;
                }
                if (node == node.parent.left) {
                    //Double rotation needed
                    node = node.parent;
                    rotateRight(node);
                }
                node.parent.color = BLACK_NODE;
                node.parent.parent.color = RED_NODE;
                rotateLeft(node.parent.parent);
            }
        }
        root.color = BLACK_NODE;
    }

    /*
        Takes a RedBlackTreeNode and rotates the corresponding Red-Black tree to left.
     */
    void rotateLeft(RedBlackTreeNode node) {
        if (node.parent != nil) {
            if (node == node.parent.left) { //if node is left node
                node.parent.left = node.right;
            } else {//else if node is right node
                node.parent.right = node.right;
            }
            node.right.parent = node.parent;
            node.parent = node.right;
            if (node.right.left != nil) {
                node.right.left.parent = node;
            }
            node.right = node.right.left;
            node.parent.left = node;
        } else {
            RedBlackTreeNode right = root.right;
            root.right = right.left;
            right.left.parent = root;
            root.parent = right;
            right.left = root;
            right.parent = nil;
            root = right;
        }
    }

    /*
        Takes a RedBlackTreeNode and rotates the corresponding Red-Black tree to right.
     */
    void rotateRight(RedBlackTreeNode node) {
        if (node.parent != nil) {
            if (node == node.parent.left) {
                node.parent.left = node.left;
            } else {
                node.parent.right = node.left;
            }

            node.left.parent = node.parent;
            node.parent = node.left;
            if (node.left.right != nil) {
                node.left.right.parent = node;
            }
            node.left = node.left.right;
            node.parent.right = node;
        } else {//Need to rotate root
            RedBlackTreeNode left = root.left;
            root.left = root.left.right;
            left.right.parent = root;
            root.parent = left;
            left.right = root;
            left.parent = nil;
            root = left;
        }
    }

    void transplant(RedBlackTreeNode target, RedBlackTreeNode with) {
        if (target.parent == nil) {
            root = with;
        } else if (target == target.parent.left) {
            target.parent.left = with;
        } else
            target.parent.right = with;
        with.parent = target.parent;
    }

    /*
        Takes a RedBlackTreeNode z, replace it with the minimum element in its right subtree
        and calls delete() on the replaced node.
     */
    boolean delete(RedBlackTreeNode z) {
        RedBlackTreeNode x;
        RedBlackTreeNode y = z;
        int y_original_color = y.color;

        if (z.left == nil) {
            x = z.right;
            transplant(z, z.right);
        } else if (z.right == nil) {
            x = z.left;
            transplant(z, z.left);
        } else {
            y = treeMinimum(z.right);
            y_original_color = y.color;
            x = y.right;
            if (y.parent == z)
                x.parent = y;
            else {
                transplant(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }
            transplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color;
        }
        if (y_original_color == BLACK_NODE)
            deleteFixup(x);
        return true;
    }

    /*
    Is called when a black node is deleted from the RBT, with the replaced RBT node x.
    Recursively  fixes until RBT property is maintained in  the whole tree.
     */
    void deleteFixup(RedBlackTreeNode x) {
        while (x != root && x.color == BLACK_NODE) {
            if (x == x.parent.left) {
                RedBlackTreeNode w = x.parent.right;
                if (w.color == RED_NODE) {
                    w.color = BLACK_NODE;
                    x.parent.color = RED_NODE;
                    rotateLeft(x.parent);
                    w = x.parent.right;
                }
                if (w.left.color == BLACK_NODE && w.right.color == BLACK_NODE) {
                    w.color = RED_NODE;
                    x = x.parent;
                    continue;
                } else if (w.right.color == BLACK_NODE) {
                    w.left.color = BLACK_NODE;
                    w.color = RED_NODE;
                    rotateRight(w);
                    w = x.parent.right;
                }
                if (w.right.color == RED_NODE) {
                    w.color = x.parent.color;
                    x.parent.color = BLACK_NODE;
                    w.right.color = BLACK_NODE;
                    rotateLeft(x.parent);
                    x = root;
                }
            } else {
                RedBlackTreeNode w = x.parent.left;
                if (w.color == RED_NODE) {
                    w.color = BLACK_NODE;
                    x.parent.color = RED_NODE;
                    rotateRight(x.parent);
                    w = x.parent.left;
                }
                if (w.right.color == BLACK_NODE && w.left.color == BLACK_NODE) {
                    w.color = RED_NODE;
                    x = x.parent;
                    continue;
                } else if (w.left.color == BLACK_NODE) {
                    w.right.color = BLACK_NODE;
                    w.color = RED_NODE;
                    rotateLeft(w);
                    w = x.parent.left;
                }
                if (w.left.color == RED_NODE) {
                    w.color = x.parent.color;
                    x.parent.color = BLACK_NODE;
                    w.left.color = BLACK_NODE;
                    rotateRight(x.parent);
                    x = root;
                }
            }
        }
        x.color = BLACK_NODE;
    }

    RedBlackTreeNode treeMinimum(RedBlackTreeNode subTreeRoot) {
        while (subTreeRoot.left != nil) {
            subTreeRoot = subTreeRoot.left;
        }
        return subTreeRoot;
    }

}