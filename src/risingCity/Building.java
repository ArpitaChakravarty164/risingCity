public class Building
{
	int buildingNum;
	int executedTime;
	int totalTime;
	
	Building()
	{
		buildingNum = 0;
		executedTime = 0;
		totalTime = 0;
	}

	Building(int bNum, int eTime, int tTime)
	{
		this.buildingNum = bNum;
		this.executedTime = eTime;
		this.totalTime = tTime;
	}

	int getBuildingNum()
	{
		return this.buildingNum;
	}


	int getExecutedTime()
	{
		return this.executedTime;
	}

	void setExecutedTime(int executed_time)
	{
		this.executedTime = executed_time;
	}

	int getTotalTime()
	{
		return this.totalTime;
	}

}
