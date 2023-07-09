package heatbug;

import java.util.*;
import java.math.*;

//�ȳ���
class HeatBug{
	Random rdm=new Random();
	public int x; //x����
	public int y; //y����
	public int idealTemp; //�����¶�
	public int outputHeat; //�ͷ�����
	public int unhappiness; //һ�����ӵ�unhappinessֵ

	//��������¶�
	public void getIdealTemp(int minIT,int maxIT) {
		idealTemp=rdm.nextInt(maxIT-minIT)+minIT; //���û��������С����������¶ȷ�Χ���������
	}
	
	//����ͷ�����
	public void getOutputHeat(int minOH,int maxOH) {
		outputHeat=rdm.nextInt(maxOH-minOH)+minOH; //���û��������С������ͷ�������Χ���������	
	}
	
	//���unhappinessֵ
	public int getUnhappiness(int[][] temp) {
		return Math.abs(this.idealTemp-temp[this.x][this.y]); //unhappiness=�����¶������ڸ����¶Ȳ�ֵ�ľ���ֵ
	}
	
	//�˶������������˶�����
	public int move(int[][] temp) {
		int nearbyTemp[]=new int[4]; //��ų������ܣ��������ң��¶ȵ�����
		int minimum=100; //�����¶������ܸ����¶Ȳ�����Сֵ
		int movement=4; //�˶�����
		int count=1; //����ͬ�¶ȵĸ������������ܣ�
		int[] a= {5,5,5,5}; //���и����¶���ͬ�����䷽�����ڸ�������
		int k=0;
		Random rdm=new Random();
		//������ڸ����¶������ǳ��������¶ȣ���ԭ�ز���
		if(temp[this.x][this.y]==this.idealTemp)
			return 4; 
		//������ڸ����¶Ȳ��ǳ��������¶ȣ�����Ҫ�ƶ�
		else {
			//�����ܸ����¶ȴ������飬������ӱ���λ�ڱ߽磬����Ӧλ�õ��¶���Ϊ-200��ʹ���Ӳ��������÷����ƶ�
			nearbyTemp[0]=(this.y==0)?(-200):temp[this.x][this.y-1];
			nearbyTemp[1]=(this.x==0)?(-200):temp[this.x-1][this.y];
			nearbyTemp[2]=(this.x==29)?(-200):temp[this.x+1][this.y];
			nearbyTemp[3]=(this.y==29)?(-200):temp[this.x][this.y+1];
			int delta[]=new int[4]; //��ȡ�����¶��������¶Ȳ�ֵ������
			//ѭ��4������
			for(int i=0;i<4;i++) {
				delta[i]=Math.abs(this.idealTemp-nearbyTemp[i]);
				//����¶Ȳ�С����Сֵ
				if(delta[i]<minimum) {
					k=0;
					movement=i;
					minimum=delta[i]; //��������Сֵ
					count=1;
					a[k]=i;
					k++;
				}
				//����¶Ȳ������Сֵ
				else if(delta[i]==minimum) {
					count++; 
					a[k]=i;
					k++;
				}
			}
			//4����������������ɸ������¶���ͬ��������Щ���������ѡȡһ��
			if(count!=1) {
				int m=rdm.nextInt(count);
				movement=a[m];
			}
		}
		return movement;		
	}
	
	//�ͷ���������
	public void spreadHeat(int[][] temp) {
		//��������Ͻ�
		if(this.x==0&&this.y==0) {
			temp[this.x][this.y+1]+=this.outputHeat/4;		
			temp[this.x+1][this.y]+=this.outputHeat/4;		
		}
		//��������Ͻ�
		else if(this.x==0&&this.y==29) {
			temp[this.x][this.y-1]+=this.outputHeat/4;		
			temp[this.x+1][this.y]+=this.outputHeat/4;		
		}
		//��������½�
		else if(this.x==29&&this.y==0) {
			temp[this.x-1][this.y]+=this.outputHeat/4;		
			temp[this.x][this.y+1]+=this.outputHeat/4;		
		}
		//��������½�
		else if(this.x==29&&this.y==29) {
			temp[this.x-1][this.y]+=this.outputHeat/4;		
			temp[this.x][this.y-1]+=this.outputHeat/4;		
		}
		//�������߽�
		else if(this.x==0&&this.y!=0&&this.y!=29) {
			temp[this.x][this.y-1]+=this.outputHeat/4;		
			temp[this.x+1][this.y]+=this.outputHeat/4;
			temp[this.x][this.y+1]+=this.outputHeat/4;
		}
		//������ұ߽�
		else if(this.x==29&&this.y!=0&&this.y!=29) {
			temp[this.x][this.y-1]+=this.outputHeat/4;		
			temp[this.x-1][this.y]+=this.outputHeat/4;
			temp[this.x][this.y+1]+=this.outputHeat/4;
		}
		//������ϱ߽�
		else if(this.y==0&&this.x!=0&&this.x!=29) {
			temp[this.x-1][this.y]+=this.outputHeat/4;		
			temp[this.x+1][this.y]+=this.outputHeat/4;
			temp[this.x][this.y+1]+=this.outputHeat/4;
		}
		//������±߽�
		else if(this.y==29&&this.x!=0&&this.x!=29) {
			temp[this.x-1][this.y]+=this.outputHeat/4;		
			temp[this.x+1][this.y]+=this.outputHeat/4;
			temp[this.x][this.y-1]+=this.outputHeat/4;
		}
		//������������м䣬���ܶ��и��ӣ�
		else {
			temp[this.x][this.y-1]+=this.outputHeat/4;		
			temp[this.x][this.y+1]+=this.outputHeat/4;
			temp[this.x-1][this.y]+=this.outputHeat/4;
			temp[this.x+1][this.y]+=this.outputHeat/4;
		}
	}
}