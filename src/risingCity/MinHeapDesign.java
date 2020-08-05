class MinHeapNode
{
    int nodeNum;

	Building building;

	RedBlackTreeNode rbnode;
  
    public MinHeapNode(int item, Building b, RedBlackTreeNode newRedBlackTreeNode)
    { 
        nodeNum = item;
        building = b;
        rbnode = newRedBlackTreeNode;
    }

    void setNodeNum(int nodeNum) {
    	this.nodeNum = nodeNum;
    }

    Building getBuildingDetails() {
    	return building;
    }

    int getNodeNum() {
    	return nodeNum;
    }
}


public class MinHeapDesign {

	int storedBuildings;
	MinHeapNode heapArray[];

	MinHeapDesign() {
		heapArray = new MinHeapNode[2000];
		storedBuildings = 0;
	}

	MinHeapDesign(int data, int totalTime) {
		Building b = new Building(data, 0, totalTime);
	}

	public void heapifying(int childNode)
	{

		int parentNode;

		if (childNode != 0) {

			parentNode = (childNode - 1) / 2;

			MinHeapNode parentMinHeapNode = heapArray[parentNode];

			MinHeapNode childMinHeapNode = heapArray[childNode];

			if (parentMinHeapNode.getNodeNum() > childMinHeapNode.getNodeNum())
			{

				heapArray[parentNode] = childMinHeapNode;

				heapArray[childNode] = parentMinHeapNode;

				heapifying(parentNode);

			} else if (parentMinHeapNode.getNodeNum() == childMinHeapNode.getNodeNum()) {

				if (parentMinHeapNode.getBuildingDetails().getBuildingNum() > childMinHeapNode.getBuildingDetails().getBuildingNum())
				{
					heapArray[parentNode] = childMinHeapNode;
					heapArray[childNode] = parentMinHeapNode;
					heapifying(parentNode);
				}
			}
		}
	}

	public void buildH(int curr)
	{
		int minimum = curr;
		int lc = 2 * curr + 1;
		int rc = 2 * curr + 2;

		if (lc < storedBuildings) {
			if (heapArray[lc].getNodeNum() < heapArray[minimum].getNodeNum())
				minimum = lc;
			else if ((heapArray[lc].getNodeNum() == heapArray[minimum].getNodeNum()) && (heapArray[lc].getBuildingDetails().getBuildingNum() < heapArray[minimum].getBuildingDetails().getBuildingNum()))
				minimum = lc;
		}


		if (rc < storedBuildings) {
			if (heapArray[rc].getNodeNum() < heapArray[minimum].getNodeNum())
				minimum = rc;
			else if (heapArray[rc].getNodeNum() == heapArray[minimum].getNodeNum()) {
				if (heapArray[rc].getBuildingDetails().getBuildingNum() < heapArray[minimum].getBuildingDetails().getBuildingNum())
					minimum = rc;
			}
		}
		if (minimum != curr) {
			MinHeapNode temp = heapArray[curr];
			heapArray[curr] = heapArray[minimum];
			heapArray[minimum] = temp;

			buildH(minimum);
		}
	}


	public MinHeapNode removeMinRoot() {
		if (storedBuildings == 0)
		{
			return null;
		}
		MinHeapNode temp = heapArray[0];

		storedBuildings--;
		if (storedBuildings > 0) {
			heapArray[0] = heapArray[storedBuildings];
			buildH(0);
		} else heapArray[0] = null;
		return temp;
	}

	public void insertInHeap(Building b, RedBlackTreeNode newRedBlackTreeNode, int glob_c) {
		MinHeapNode newMinHeapNode = new MinHeapNode(0, b, newRedBlackTreeNode);
		storedBuildings++;
		int i = storedBuildings - 1;
		heapArray[i] = newMinHeapNode;

		heapifying(storedBuildings - 1);
	}


//Re-insert node and call heapifying again to maintain heap property

	public void insertInHeap(MinHeapNode prev, int glob_c) {
		if (prev != null) {

			storedBuildings++;

			int i = storedBuildings - 1;
			heapArray[i] = prev;
			heapifying(storedBuildings - 1);

		}
	}
}



