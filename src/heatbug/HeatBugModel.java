package heatbug;

import java.util.*;
import java.math.*;

//热虫类
class HeatBug{
	Random rdm=new Random();
	public int x; //x坐标
	public int y; //y坐标
	public int idealTemp; //理想温度
	public int outputHeat; //释放热量
	public int unhappiness; //一个虫子的unhappiness值

	//获得理想温度
	public void getIdealTemp(int minIT,int maxIT) {
		idealTemp=rdm.nextInt(maxIT-minIT)+minIT; //在用户输入的最小、最大理想温度范围中随机生成
	}
	
	//获得释放热量
	public void getOutputHeat(int minOH,int maxOH) {
		outputHeat=rdm.nextInt(maxOH-minOH)+minOH; //在用户输入的最小、最大释放热量范围中随机生成	
	}
	
	//获得unhappiness值
	public int getUnhappiness(int[][] temp) {
		return Math.abs(this.idealTemp-temp[this.x][this.y]); //unhappiness=理想温度与所在格子温度差值的绝对值
	}
	
	//运动函数，返回运动方向
	public int move(int[][] temp) {
		int nearbyTemp[]=new int[4]; //存放虫子四周（上下左右）温度的数组
		int minimum=100; //理想温度与四周格子温度差距的最小值
		int movement=4; //运动方向
		int count=1; //有相同温度的格子数量（四周）
		int[] a= {5,5,5,5}; //若有格子温度相同，则其方向存放在该数组中
		int k=0;
		Random rdm=new Random();
		//如果所在格子温度正好是虫子理想温度，则原地不动
		if(temp[this.x][this.y]==this.idealTemp)
			return 4; 
		//如果所在格子温度不是虫子理想温度，则需要移动
		else {
			//将四周格子温度存入数组，如果虫子本身位于边界，则将相应位置的温度设为-200，使虫子不可能往该方向移动
			nearbyTemp[0]=(this.y==0)?(-200):temp[this.x][this.y-1];
			nearbyTemp[1]=(this.x==0)?(-200):temp[this.x-1][this.y];
			nearbyTemp[2]=(this.x==29)?(-200):temp[this.x+1][this.y];
			nearbyTemp[3]=(this.y==29)?(-200):temp[this.x][this.y+1];
			int delta[]=new int[4]; //存取四周温度与理想温度差值的数组
			//循环4个方向
			for(int i=0;i<4;i++) {
				delta[i]=Math.abs(this.idealTemp-nearbyTemp[i]);
				//如果温度差小于最小值
				if(delta[i]<minimum) {
					k=0;
					movement=i;
					minimum=delta[i]; //重新设最小值
					count=1;
					a[k]=i;
					k++;
				}
				//如果温度差等于最小值
				else if(delta[i]==minimum) {
					count++; 
					a[k]=i;
					k++;
				}
			}
			//4个格子中如果有若干个格子温度相同，则在这些方向中随机选取一个
			if(count!=1) {
				int m=rdm.nextInt(count);
				movement=a[m];
			}
		}
		return movement;		
	}
	
	//释放热量函数
	public void spreadHeat(int[][] temp) {
		//如果在左上角
		if(this.x==0&&this.y==0) {
			temp[this.x][this.y+1]+=this.outputHeat/4;		
			temp[this.x+1][this.y]+=this.outputHeat/4;		
		}
		//如果在右上角
		else if(this.x==0&&this.y==29) {
			temp[this.x][this.y-1]+=this.outputHeat/4;		
			temp[this.x+1][this.y]+=this.outputHeat/4;		
		}
		//如果在左下角
		else if(this.x==29&&this.y==0) {
			temp[this.x-1][this.y]+=this.outputHeat/4;		
			temp[this.x][this.y+1]+=this.outputHeat/4;		
		}
		//如果在右下角
		else if(this.x==29&&this.y==29) {
			temp[this.x-1][this.y]+=this.outputHeat/4;		
			temp[this.x][this.y-1]+=this.outputHeat/4;		
		}
		//如果靠左边界
		else if(this.x==0&&this.y!=0&&this.y!=29) {
			temp[this.x][this.y-1]+=this.outputHeat/4;		
			temp[this.x+1][this.y]+=this.outputHeat/4;
			temp[this.x][this.y+1]+=this.outputHeat/4;
		}
		//如果靠右边界
		else if(this.x==29&&this.y!=0&&this.y!=29) {
			temp[this.x][this.y-1]+=this.outputHeat/4;		
			temp[this.x-1][this.y]+=this.outputHeat/4;
			temp[this.x][this.y+1]+=this.outputHeat/4;
		}
		//如果靠上边界
		else if(this.y==0&&this.x!=0&&this.x!=29) {
			temp[this.x-1][this.y]+=this.outputHeat/4;		
			temp[this.x+1][this.y]+=this.outputHeat/4;
			temp[this.x][this.y+1]+=this.outputHeat/4;
		}
		//如果靠下边界
		else if(this.y==29&&this.x!=0&&this.x!=29) {
			temp[this.x-1][this.y]+=this.outputHeat/4;		
			temp[this.x+1][this.y]+=this.outputHeat/4;
			temp[this.x][this.y-1]+=this.outputHeat/4;
		}
		//其他情况（在中间，四周都有格子）
		else {
			temp[this.x][this.y-1]+=this.outputHeat/4;		
			temp[this.x][this.y+1]+=this.outputHeat/4;
			temp[this.x-1][this.y]+=this.outputHeat/4;
			temp[this.x+1][this.y]+=this.outputHeat/4;
		}
	}
}