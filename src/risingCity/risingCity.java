
import java.io.FileWriter;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.BufferedReader;

import java.io.FileReader;

public class risingCity {

	MinHeapNode currentMinHeapNode;
	boolean isCompleted;
	int counterD;

	private static int checkInputCommand(String string) {
		if(string.charAt(0)=='I') return 1;
		else if(string.charAt(0)=='P') return 2;
		else return 0;
	}

	public static void main(String args[]) throws Exception{
		
		if(args.length>1)
		{
			System.out.println("ERROR: Argument Overflow!");
			return;
		}
		
		Path currPath = Paths.get("");
		String currAbsPath = currPath.toAbsolutePath().toString();
		
		String fileName = currAbsPath+"/"+args[0];
		File ipFile = new File(fileName);
		FileWriter opFileWriter = new FileWriter(currAbsPath+"/output.txt");
		risingCity rising = new risingCity();
		rising.buildingTheCity(ipFile, opFileWriter);
	}
	


	private int buildCurrentBuilding(ConstructingCity constructingCity, int globalCounter, String prs) {
		Building currentBuilding;
		
		if(counterD ==0 || currentMinHeapNode ==null) {
			currentMinHeapNode = constructingCity.removeRootMin();
		}
		if(currentMinHeapNode !=null) {
			currentBuilding = currentMinHeapNode.getBuildingDetails();
			counterD++;
			isCompleted = constructingCity.buildABuilding(currentMinHeapNode, globalCounter);
			
			if(isCompleted ==true) {
				counterD = 0;
				if(prs.length()>0) {
					constructingCity.printBuildingBetweenIndices(prs);
					prs="";
				}
				constructingCity.deleteRedBlackTreeNode(currentBuilding.buildingNum);
				currentMinHeapNode = null;
				String detailsToPrint = "("+currentBuilding.getBuildingNum()+","+globalCounter+")";
				constructingCity.printWriter.println(detailsToPrint);
			}
			if(counterD ==5) {
				counterD = 0;
				constructingCity.insertBuildingMinAgain(currentMinHeapNode, globalCounter);
			}
			if(prs.length()>0) {
				constructingCity.printBuildingBetweenIndices(prs);
				prs="";
			}
		}
		
		return counterD;
	}


	public void buildingTheCity(File ipFile, FileWriter opFileWriter)  throws Exception
	{

		String currReadLine ="";
		ConstructingCity constructingCity = new ConstructingCity();
		constructingCity.generateOutput(opFileWriter);
		int globalTime = 1;
		int buildingDayCount = 0;
		currentMinHeapNode = null;
		String inputLine[];
		int countOfDay;

		BufferedReader buffRead = new BufferedReader(new FileReader(ipFile));

		while(((currReadLine = buffRead.readLine()) != null)) {

			if(currReadLine.contentEquals("")) continue;
			inputLine = currReadLine.split(": ");
			countOfDay = Integer.parseInt(inputLine[0]);

			while(countOfDay > globalTime) {
				globalTime++;
				buildingDayCount = buildCurrentBuilding(constructingCity, globalTime,"");
			}

			int executeCommand = checkInputCommand(inputLine[1]);

			if(executeCommand ==1) {
				constructingCity.insertBuilding(inputLine[1], globalTime);
				buildingDayCount = buildCurrentBuilding(constructingCity, globalTime, "");
				globalTime++;
			}

			else if(executeCommand ==2) {
				buildingDayCount = buildCurrentBuilding(constructingCity, globalTime, inputLine[1]);
				globalTime++;

			}
			else System.out.println("ERROR: Command Invalid");
		}

		while((constructingCity.minHeapDesign.storedBuildings>0)||(currentMinHeapNode !=null)) {
			buildingDayCount = buildCurrentBuilding(constructingCity, globalTime, "");
			globalTime++;
		}

		constructingCity.printWriter.close();

	}



}
