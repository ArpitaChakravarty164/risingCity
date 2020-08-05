import java.util.List;


import java.util.ArrayList;

class RedBlackTreeNode {
    Building build;                                          // building object
    int nodeNum;                                            // holds the building id
    RedBlackTreeNode parentNode;                           // parent of RedBlackTreeNode
    RedBlackTreeNode leftChild;                           // left child of RedBlackTreeNode
    RedBlackTreeNode rightChild;                         // right child of RedBlackTreeNode
    int color;                                          // Color representation: 1 for Red and 0 for Black
    
    RedBlackTreeNode(int nodeNum) {
    	build = new Building(0,0,0);
    }
    
    RedBlackTreeNode(){}

    RedBlackTreeNode(Building build)
    {
        this.build = build;
        this.nodeNum = build.buildingNum;
    }
}

public class RedBlackTreeDesign {
    private RedBlackTreeNode root;
    private RedBlackTreeNode isLeaf;

    private RedBlackTreeNode searchingRedBlackHelp(RedBlackTreeNode rbnode, int nodeVal) {
        if (rbnode == isLeaf || nodeVal == rbnode.nodeNum) {
            if(rbnode == isLeaf)
            {
                Building building = new Building(0, 0, 0);
                RedBlackTreeNode temp = new RedBlackTreeNode();
                temp.build = building;
                return temp;
            }

            return rbnode;
        }

        if (nodeVal < rbnode.nodeNum) {
            return searchingRedBlackHelp(rbnode.leftChild, nodeVal);
        }
        return searchingRedBlackHelp(rbnode.rightChild, nodeVal);
    }

    private void fixingRedBlackTreeAfterDelete(RedBlackTreeNode delNode) {
        RedBlackTreeNode temp;
        while (delNode != root && delNode.color == 0) {
            if (delNode == delNode.parentNode.leftChild) {
                temp = delNode.parentNode.rightChild;
                if (temp.color == 1) {
                    temp.color = 0;
                    delNode.parentNode.color = 1;
                    leftRotateTree(delNode.parentNode);
                    temp = delNode.parentNode.rightChild;
                }
                if(temp== isLeaf)
                	return;
                

                if (temp.leftChild.color == 0 && temp.rightChild.color == 0) {
                    temp.color = 1;
                    delNode = delNode.parentNode;
                } else {
                    if (temp.rightChild.color == 0) {
                        temp.leftChild.color = 0;
                        temp.color = 1;
                        rightRotateTree(temp);
                        temp = delNode.parentNode.rightChild;
                    }

                    temp.color = delNode.parentNode.color;
                    delNode.parentNode.color = 0;
                    temp.rightChild.color = 0;
                    leftRotateTree(delNode.parentNode);
                    delNode = root;
                }
            } else {
                temp = delNode.parentNode.leftChild;
                if (temp.color == 1) {
                    temp.color = 0;
                    delNode.parentNode.color = 1;
                    rightRotateTree(delNode.parentNode);
                    temp = delNode.parentNode.leftChild;
                }

                if(temp== isLeaf)
                	return;
                if (temp.rightChild.color == 0 && temp.rightChild.color == 0) {
                    temp.color = 1;
                    delNode = delNode.parentNode;
                } 
                else {
                    if (temp.leftChild.color == 0) {
                        temp.rightChild.color = 0;
                        temp.color = 1;
                        leftRotateTree(temp);
                        temp = delNode.parentNode.leftChild;
                    }

                    temp.color = delNode.parentNode.color;
                    delNode.parentNode.color = 0;
                    temp.leftChild.color = 0;
                    rightRotateTree(delNode.parentNode);
                    delNode = root;
                }
            }
        }
        delNode.color = 0;
    }


    private void repositionRedBlackTree(RedBlackTreeNode nodeIsX, RedBlackTreeNode nodeIsY) {
        if (nodeIsX.parentNode == null) {
            root = nodeIsY;
        } else if (nodeIsX == nodeIsX.parentNode.leftChild){
            nodeIsX.parentNode.leftChild = nodeIsY;
        } else {
            nodeIsX.parentNode.rightChild = nodeIsY;
        }
        nodeIsY.parentNode = nodeIsX.parentNode;
    }

    private void deleteRedBlackNodeHelp(RedBlackTreeNode delNode, int nodeVal) {
        RedBlackTreeNode nodeIsZ = isLeaf;
        RedBlackTreeNode nodeIsX, nodeIsY;
        while (delNode != isLeaf){
            if (delNode.nodeNum == nodeVal) {
                nodeIsZ = delNode;
            }

            if (delNode.nodeNum <= nodeVal) {
                delNode = delNode.rightChild;
            } else {
                delNode = delNode.leftChild;
            }
        }

        if (nodeIsZ == isLeaf) {
            return;
        }

        nodeIsY = nodeIsZ;
        int yOriginalColor = nodeIsY.color;
        if (nodeIsZ.leftChild == isLeaf) {
            nodeIsX = nodeIsZ.rightChild;
            repositionRedBlackTree(nodeIsZ, nodeIsZ.rightChild);
        } else if (nodeIsZ.rightChild == isLeaf) {
            nodeIsX = nodeIsZ.leftChild;
            repositionRedBlackTree(nodeIsZ, nodeIsZ.leftChild);
        } else {
            nodeIsY = findMinNode(nodeIsZ.rightChild);
            yOriginalColor = nodeIsY.color;
            nodeIsX = nodeIsY.rightChild;
            if (nodeIsY.parentNode == nodeIsZ) {
                nodeIsX.parentNode = nodeIsY;
            } else {
                repositionRedBlackTree(nodeIsY, nodeIsY.rightChild);
                nodeIsY.rightChild = nodeIsZ.rightChild;
                nodeIsY.rightChild.parentNode = nodeIsY;
            }

            repositionRedBlackTree(nodeIsZ, nodeIsY);
            nodeIsY.leftChild = nodeIsZ.leftChild;
            nodeIsY.leftChild.parentNode = nodeIsY;
            nodeIsY.color = nodeIsZ.color;
        }
        if (yOriginalColor == 0){
            fixingRedBlackTreeAfterDelete(nodeIsX);
        }
    }

    private void fixingRedBlackTreeAfterInsert(RedBlackTreeNode nodeIsX) {
        RedBlackTreeNode nodeIsY;
        while (nodeIsX.parentNode.color == 1) {
            if (nodeIsX.parentNode == nodeIsX.parentNode.parentNode.rightChild) {
                nodeIsY = nodeIsX.parentNode.parentNode.leftChild;
                if (nodeIsY.color == 1) {
                    nodeIsY.color = 0;
                    nodeIsX.parentNode.color = 0;
                    nodeIsX.parentNode.parentNode.color = 1;
                    nodeIsX = nodeIsX.parentNode.parentNode;
                } else {
                    if (nodeIsX == nodeIsX.parentNode.leftChild) {
                        nodeIsX = nodeIsX.parentNode;
                        rightRotateTree(nodeIsX);
                    }
                    nodeIsX.parentNode.color = 0;
                    nodeIsX.parentNode.parentNode.color = 1;
                    leftRotateTree(nodeIsX.parentNode.parentNode);
                }
            } else {
                nodeIsY = nodeIsX.parentNode.parentNode.rightChild;

                if (nodeIsY.color == 1) {
                    nodeIsY.color = 0;
                    nodeIsX.parentNode.color = 0;
                    nodeIsX.parentNode.parentNode.color = 1;
                    nodeIsX = nodeIsX.parentNode.parentNode;
                } else {
                    if (nodeIsX == nodeIsX.parentNode.rightChild) {
                        nodeIsX = nodeIsX.parentNode;
                        leftRotateTree(nodeIsX);
                    }
                    nodeIsX.parentNode.color = 0;
                    nodeIsX.parentNode.parentNode.color = 1;
                    rightRotateTree(nodeIsX.parentNode.parentNode);
                }
            }
            if (nodeIsX == root) {
                break;
            }
        }
        root.color = 0;
    }

    public RedBlackTreeDesign() {
        isLeaf = new RedBlackTreeNode(0);
        isLeaf.color = 0;
        isLeaf.leftChild = null;
        isLeaf.rightChild = null;
        root = isLeaf;
    }

    public RedBlackTreeNode searchingInRedBlackTree(int n) {
        return searchingRedBlackHelp(this.root, n);
    }
    public List<RedBlackTreeNode> treeList(int l, int h)
    {
        List<RedBlackTreeNode> finalTrees = new ArrayList<>();
        searchInorderRangeOfTree(this.root, finalTrees, l, h);

        return  finalTrees;
    }

    private void searchInorderRangeOfTree(RedBlackTreeNode rb_node, List<RedBlackTreeNode> finalTrees, int l, int h) {
        if (rb_node == isLeaf)
            return;

        int low = l - rb_node.build.buildingNum;
        int high = h - rb_node.build.buildingNum;

        if(low < 0)
            searchInorderRangeOfTree(rb_node.leftChild, finalTrees, l, h);

        if (low <= 0 && high >= 0)
            finalTrees.add(rb_node);
        
        if (high > 0)
            searchInorderRangeOfTree(rb_node.rightChild, finalTrees, l, h);
    }

    public RedBlackTreeNode findMinNode(RedBlackTreeNode rb_node) {
        while (rb_node.leftChild != isLeaf) {
            rb_node = rb_node.leftChild;
        }
        return rb_node;
    }



    public void leftRotateTree(RedBlackTreeNode nodeA) {
        RedBlackTreeNode nodeB = nodeA.rightChild;
        nodeA.rightChild = nodeB.leftChild;
        if (nodeB.leftChild != isLeaf) {
            nodeB.leftChild.parentNode = nodeA;
        }
        nodeB.parentNode = nodeA.parentNode;
        if (nodeA.parentNode == null) {
            this.root = nodeB;
        } else if (nodeA == nodeA.parentNode.leftChild) {
            nodeA.parentNode.leftChild = nodeB;
        } else {
            nodeA.parentNode.rightChild = nodeB;
        }
        nodeB.leftChild = nodeA;
        nodeA.parentNode = nodeB;
    }

    public void rightRotateTree(RedBlackTreeNode nodeA) {
        RedBlackTreeNode nodeB = nodeA.leftChild;
        nodeA.leftChild = nodeB.rightChild;
        if (nodeB.rightChild != isLeaf) {
            nodeB.rightChild.parentNode = nodeA;
        }
        nodeB.parentNode = nodeA.parentNode;
        if (nodeA.parentNode == null) {
            this.root = nodeB;
        } else if (nodeA == nodeA.parentNode.rightChild) {
            nodeA.parentNode.rightChild = nodeB;
        } else {
            nodeA.parentNode.leftChild = nodeB;
        }
        nodeB.rightChild = nodeA;
        nodeA.parentNode = nodeB;
    }

    public RedBlackTreeNode insertRedBlackNode(Building b) {
        RedBlackTreeNode node = new RedBlackTreeNode();


        // put building details in node
        node.build = b;
        node.parentNode = null;
        node.nodeNum = b.getBuildingNum();
        node.leftChild = isLeaf;
        node.rightChild = isLeaf;

        // New inserted node is always Red colored
        node.color = 1;

        RedBlackTreeNode nodeB = null;
        RedBlackTreeNode nodeA = this.root;

        while (nodeA != isLeaf) {
            nodeB = nodeA;
            if (node.nodeNum < nodeA.nodeNum) {
                nodeA = nodeA.leftChild;
            } else {
                nodeA = nodeA.rightChild;
            }
        }

        node.parentNode = nodeB;
        if (nodeB == null) {
            root = node;
        } else if (node.nodeNum < nodeB.nodeNum) {
            nodeB.leftChild = node;
        } else {
            nodeB.rightChild = node;
        }

        if (node.parentNode == null){
            node.color = 0;
            return node;
        }

        if (node.parentNode.parentNode == null) {
            return node;
        }

        fixingRedBlackTreeAfterInsert(node);
        
        return node;
    }



    public void deleteRedBlackNode(int nodeNum) {
        deleteRedBlackNodeHelp(this.root, nodeNum);
    }

    public String buildingPrint(int buildingNum) {
        RedBlackTreeNode result = searchingInRedBlackTree(buildingNum);
        String output = "(" + result.build.buildingNum + "," + result.build.executedTime + "," + result.build.totalTime + ")";
        return output;
    }

    public String buildingPrint(int bn1, int bn2) {
        List<RedBlackTreeNode> searchRangeResult =  treeList(bn1, bn2);
        StringBuilder sb = new StringBuilder();
        for(RedBlackTreeNode i : searchRangeResult) {
            sb.append("(").append(i.build.buildingNum).append(",").append(i.build.executedTime).append(",").append(i.build.totalTime).append(")").append(",");
        }
        String ans = sb.substring(0, sb.length() - 1);
        return ans;
    }
}
