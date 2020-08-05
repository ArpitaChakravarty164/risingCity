import java.io.FileWriter;

import java.io.PrintWriter;

import java.io.IOException;


public class ConstructingCity {
	
	MinHeapDesign minHeapDesign;
	RedBlackTreeDesign redblackDesign;
	PrintWriter printWriter;
	
	ConstructingCity() throws IOException
	{
		minHeapDesign = new MinHeapDesign();
		redblackDesign = new RedBlackTreeDesign();
	}



	// If Building construction is completed, this function returns true so that the building can be removed from the data structures
	public boolean buildABuilding(MinHeapNode currentBuildingMinHeapNode, int globalCounter)
	{
		int newExecTime = currentBuildingMinHeapNode.getBuildingDetails().getExecutedTime() + 1;

		currentBuildingMinHeapNode.getBuildingDetails().setExecutedTime(newExecTime);

		currentBuildingMinHeapNode.setNodeNum(newExecTime);

		if(newExecTime== currentBuildingMinHeapNode.getBuildingDetails().getTotalTime())
			return true;
		else 
			return false;
	}


	private void insertBuilding(int newBuildingNumber, int newTotalTime, int glob_c) throws Exception
	{
		Building b = new Building(newBuildingNumber, 0, newTotalTime);
		RedBlackTreeNode found = redblackDesign.searchingInRedBlackTree(newBuildingNumber);
		if(found.build.buildingNum!=0)
			throw new Exception("Building already inserted !!!");
		RedBlackTreeNode newRedBlackTreeNode = redblackDesign.insertRedBlackNode(b);
		minHeapDesign.insertInHeap(b, newRedBlackTreeNode, glob_c);
	}





	public void insertBuilding(String string, int glob_c) throws Exception
	{
		String inputLine[]= string.split("\\(");

		String firstWord = inputLine[0];

		String secondWord = inputLine[1].substring(0, inputLine[1].length() -1);
		
		String indexes[] = secondWord.split(",");

		int newBuildingNumber = Integer.parseInt(indexes[0]);

		int newTotalTime = Integer.parseInt(indexes[1]);

		insertBuilding(newBuildingNumber, newTotalTime, glob_c);
		
	}

	public void insertBuildingMinAgain(MinHeapNode insertMinHeapNode, int glob_c)
	{
		minHeapDesign.insertInHeap(insertMinHeapNode, glob_c);
	}


	private void printBuildingBetweenIndices(int b1, int b2)
	{
		printWriter.println(redblackDesign.buildingPrint(b1, b2));
	}

	public void printBuildingBetweenIndices(String string)
	{
		String inputLine[]= string.split("\\(");
		String firstWord = inputLine[0];
		String secondParam = inputLine[1].substring(0, inputLine[1].length() -1);

		if(secondParam.contains(","))
		{
			String indices[] = secondParam.split(",");
			int b1 = Integer.parseInt(indices[0]);
			int b2 = Integer.parseInt(indices[1]);
			printBuildingBetweenIndices(b1, b2);
		}
		else {
			int index = Integer.parseInt(secondParam);
			printBuildingDetails(index);
		}
	}

	private void printBuildingDetails(int buildingNum)
	{
		printWriter.println(redblackDesign.buildingPrint(buildingNum));
	}

	public void deleteRedBlackTreeNode(int buildingNum)
	{
		redblackDesign.deleteRedBlackNode(buildingNum);
	}

	public void generateOutput(FileWriter outputFile)
	{
		printWriter = new PrintWriter(outputFile);
	}

	public MinHeapNode removeRootMin()
	{
		return minHeapDesign.removeMinRoot();
	}


}
