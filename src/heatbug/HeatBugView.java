package heatbug;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.geom.Ellipse2D;
import java.util.*;


/*
   *郑雅璇、杨诺彤
 *2020年12月
 */

//公有类HeatBugView
public class HeatBugView extends JFrame implements ActionListener{
	
	JFrame frame;
	JPanel panel1; //输入实验参数
	JPanel panel2; //操作
	JPanel panel3; //光栅图
	JPanel panel4; //unhappiness变化图
	int numBugs; //热虫数量
	int minIdealTemp; //最低理想温度
	int maxIdealTemp; //最高理想温度
	int minOutputHeat; //最小释放热量
	int maxOutputHeat; //最大释放热量
	int time; //热虫运动时间（次数）
	JButton startBtn; //“开始”按钮
	JButton quitBtn; //“退出”按钮
	HeatBug[] heatbugs; //热虫数组
	JTextField numBugsTf; //输入“热虫数量”的文本框
	JTextField minIdealTempTf; //输入“最低理想温度”的文本框
	JTextField maxIdealTempTf; //输入“最高理想温度”的文本框
	JTextField minOutputHeatTf; //输入“最小释放热量”的文本框
	JTextField maxOutputHeatTf; //输入“最大释放热量”的文本框
	JTextField timeTf; //输入“热虫运动次数”的文本框

	//构造函数
	public HeatBugView() {		
		frame=new JFrame("热虫模拟");
		frame.setVisible(true);
		frame.setSize(600, 600);
		frame.setLayout(new GridLayout(2,2)); //将frame划分成4块
				
		//panel1
		JLabel numBugsLbl;
		JLabel minIdealTempLbl;
		JLabel maxIdealTempLbl;
		JLabel minOutputHeatLbl;
		JLabel maxOutputHeatLbl;
		JLabel timeLbl;
		panel1=new JPanel();
		frame.add(panel1);
		panel1.setLayout(new GridLayout(6,2));
		panel1.setBorder(BorderFactory.createTitledBorder("输入实验参数"));
		//控件实例化
		numBugsLbl=new JLabel("numBugs");
		minIdealTempLbl=new JLabel("minIdealTemp");
		maxIdealTempLbl=new JLabel("maxIdealTemp");
		minOutputHeatLbl=new JLabel("minOutputHeat");
		maxOutputHeatLbl=new JLabel("maxOutputHeat");
		timeLbl=new JLabel("time");
		numBugsTf=new JTextField();
		minIdealTempTf=new JTextField();
		maxIdealTempTf=new JTextField();
		minOutputHeatTf=new JTextField();
		maxOutputHeatTf=new JTextField();
		timeTf=new JTextField();
		//将控件加入到panel1中
		panel1.add(numBugsLbl);
		panel1.add(numBugsTf);
		panel1.add(minIdealTempLbl);
		panel1.add(minIdealTempTf);
		panel1.add(maxIdealTempLbl);
		panel1.add(maxIdealTempTf);
		panel1.add(minOutputHeatLbl);
		panel1.add(minOutputHeatTf);
		panel1.add(maxOutputHeatLbl);
		panel1.add(maxOutputHeatTf);
		panel1.add(timeLbl);
		panel1.add(timeTf);
				
		//panel2
		panel2=new JPanel();
		frame.add(panel2);
		panel2.setLayout(null);
		panel2.setBorder(BorderFactory.createTitledBorder("操作"));
	    startBtn=new JButton("start");
	    startBtn.setBounds(50, 60, 200, 70); //设置“开始”按钮大小
	    startBtn.addActionListener(this);
	    quitBtn=new JButton("quit");
	    quitBtn.setBounds(50, 170, 200, 70); //设置“退出”按钮大小
	    quitBtn.addActionListener(this);
		panel2.add(startBtn);
		panel2.add(quitBtn);
		
		//panel3
		panel3=new JPanel();
		frame.add(panel3);
		panel3.setLayout(new GridLayout(30,30)); //将panel3划分成30*30块
		
		//panel4
		panel4=new JPanel();
		frame.add(panel4);
	}
		
	int temp[][]=new int[30][30]; //存放每个格子温度的二维数组
	JLabel[][] labels=new JLabel[30][30]; //存放30*30个label的二维数组，代表30*30个格子
	
	//初始化，将所有label设成黑色，加入到panel3中
	public void init() {			
		for(int i=0;i<30;i++) 
			for(int j=0;j<30;j++) {
				labels[i][j]=new JLabel();
				labels[i][j].setOpaque(true);
				labels[i][j].setBackground(Color.black);
				panel3.add(labels[i][j]);
			}
	}

	//监听事件
	public void actionPerformed(ActionEvent e) {
		//放到单独的线程中，避免主线程阻塞，以实现界面刷新
		new Thread(new Runnable() {
			@Override
			public void run() {
				//点击“退出”按钮
				if(e.getSource()==quitBtn) {
					System.exit(0);
				}
				
				//点击“开始”按钮
				int movement=0; //虫子运动方向，0代表上，1代表左，2代表右，3代表下，4代表原地不动
				int evaporation=0; //系统散热
				Random rdm=new Random();
				if(e.getSource()==startBtn) {
					//获取用户输入数据检验				
					try {
						numBugs=Integer.parseInt(numBugsTf.getText());
					} 
					catch (Exception e2) {
						// TODO: handle exception
						JOptionPane.showMessageDialog(null, "numBugs必须为整数！","提示",JOptionPane.PLAIN_MESSAGE);
						return;
					}
					try {
						minIdealTemp=Integer.parseInt(minIdealTempTf.getText());
					} 
					catch (Exception e2) {
						// TODO: handle exception
						JOptionPane.showMessageDialog(null, "minIdealTemp必须为整数！","提示",JOptionPane.PLAIN_MESSAGE);
						return;
					}
					try {
						maxIdealTemp=Integer.parseInt(maxIdealTempTf.getText());
					} 
					catch (Exception e2) {
						// TODO: handle exception
						JOptionPane.showMessageDialog(null, "maxIdealTemp必须为整数！","提示",JOptionPane.PLAIN_MESSAGE);
						return;
					}
					try {
						minOutputHeat=Integer.parseInt(minOutputHeatTf.getText());
					} 
					catch (Exception e2) {
						// TODO: handle exception
						JOptionPane.showMessageDialog(null, "minOutputHeat必须为整数！","提示",JOptionPane.PLAIN_MESSAGE);
						return;
					}
					try {
						maxOutputHeat=Integer.parseInt(maxOutputHeatTf.getText());
					} 
					catch (Exception e2) {
						// TODO: handle exception
						JOptionPane.showMessageDialog(null, "maxOutputHeat必须为整数！","提示",JOptionPane.PLAIN_MESSAGE);
						return;
					}
					try {
						time=Integer.parseInt(timeTf.getText());
					} 
					catch (Exception e2) {
						// TODO: handle exception
						JOptionPane.showMessageDialog(null, "time必须为整数！","提示",JOptionPane.PLAIN_MESSAGE);
						return;
					}
					if(minIdealTemp>=maxIdealTemp||minOutputHeat>=maxOutputHeat) {
						JOptionPane.showMessageDialog(null, "最大值必须大于最小值！","提示",JOptionPane.PLAIN_MESSAGE);
						return;
					}
					if(time>100||time<1) {
						JOptionPane.showMessageDialog(null, "time取值必须在1~100！","提示",JOptionPane.PLAIN_MESSAGE);
						return;
					}
					if(numBugs>300||numBugs<1) {
						JOptionPane.showMessageDialog(null, "numBugs取值必须在1~300！","提示",JOptionPane.PLAIN_MESSAGE);
						return;
					}
			
					heatbugs=new HeatBug[numBugs]; //根据用户输入的热虫数实例化一个HeatBug类型的数组
					drawLineXY(panel4); //画unhappiness曲线图的XY轴
					drawXScale(panel4,time); //画X轴刻度
					
					//生成相应数量的热虫并初始化
					for(int t=0;t<numBugs;t++) {
						heatbugs[t]=new HeatBug();						
						//随机生成热虫在网格中的位置
						heatbugs[t].x=rdm.nextInt(30); 
						heatbugs[t].y=rdm.nextInt(30);	
						//热虫的理想温度和释放热量均在最小最大范围内随机生成
						heatbugs[t].getIdealTemp(minIdealTemp, maxIdealTemp);
						heatbugs[t].getOutputHeat(minOutputHeat, maxOutputHeat);
						//将虫子所在的格子显示为绿色
						labels[heatbugs[t].x][heatbugs[t].y].setBackground(Color.green);
						evaporation+=heatbugs[t].outputHeat; 
					} 
					evaporation/=900; //计算每个格子的散热
					
					//循环，使热虫运动time次
					int i=0;
					for(i=0;i<time;i++) {			
						drawPoint(panel4,temp,i); //每次运动前在unhappiness曲线图中画点
						
						//每个热虫的运动
						for(int j=0;j<numBugs;j++) {	
							heatbugs[j].spreadHeat(temp); //向四周散热							
							movement=heatbugs[j].move(temp); //获得运动方向
							
							//根据运动方向来改变网格中label的颜色来显示热虫的运动
							switch(movement) {
							//向上移动，原来格子变黑，上方格子变绿
							case 0:{ 
								labels[heatbugs[j].x][heatbugs[j].y].setBackground(Color.black);
								labels[heatbugs[j].x][heatbugs[j].y-1].setBackground(Color.green);
								heatbugs[j].y--;
								break;
							}
							//向左移动，原来格子变黑，左方格子变绿
							case 1:{
								labels[heatbugs[j].x][heatbugs[j].y].setBackground(Color.black);
								labels[heatbugs[j].x-1][heatbugs[j].y].setBackground(Color.green);
								heatbugs[j].x--;
								break;
							}
							//向右移动，原来格子变黑，右方格子变绿
							case 2:{
								labels[heatbugs[j].x][heatbugs[j].y].setBackground(Color.black);
								labels[heatbugs[j].x+1][heatbugs[j].y].setBackground(Color.green);
								heatbugs[j].x++;
								break;
							}
							//向下移动，原来格子变黑，下方格子变绿
							case 3:{
								labels[heatbugs[j].x][heatbugs[j].y].setBackground(Color.black);
								labels[heatbugs[j].x][heatbugs[j].y+1].setBackground(Color.green);
								heatbugs[j].y++;
								break;
							}
							//原地不动
							case 4:{
								break;
							}
							}
						}
						//系统散热
						for(int m=0;m<30;m++)
							for(int n=0;n<30;n++) 
								temp[m][n]-=evaporation;
						
						//每次运动后让线程暂停片刻，使运动状况方便观察
						try{
							Thread.sleep(200);
						}
						catch(InterruptedException e2) {
							e2.printStackTrace();
						}
						
					}
					drawPoint(panel4,temp,i); //最后一次运动后在曲线图画最终的unhappiness点
				}
			}
		}).start();
	}
	
	//获得所有热虫总体unhappiness值
	public double getOverallUnhappiness() {
		double overallUnhappiness=0;
		for(int i=0;i<numBugs;i++) {
			int itsUnhappiness=heatbugs[i].getUnhappiness(temp); //获取每个虫子自己的unhappiness值
			overallUnhappiness+=itsUnhappiness;	//加和
		}
		overallUnhappiness/=numBugs; //求均值
		return overallUnhappiness;
	}

	//画unhappiness曲线图的XY轴
	public void drawLineXY(JPanel panel) {		
		Graphics g=panel.getGraphics();
		Graphics2D g2d=(Graphics2D)g;
		g2d.clearRect(0, 0, panel.getWidth(), panel.getHeight()); //当前背景色清空画板
		g2d.setColor(Color.BLACK); //画笔颜色											
		g2d.drawLine(5, 5, 5, panel.getHeight()-5); //画纵轴
		g2d.drawLine(5, panel.getHeight()-5, panel.getWidth()-5, panel.getHeight()-5); //画横轴
		g2d.dispose();
	} 
	
	//画X轴刻度
	public void drawXScale(JPanel panel, int time) {
		Graphics g=panel.getGraphics();
		Graphics2D g2d=(Graphics2D)g;
		g2d.setColor(Color.BLACK); //画笔颜色
		int a=panel.getWidth()/time; //根据运动次数计算两个刻度之间的距离
		//循环画刻度
		for (int i=0; i<(time+1); i++) 
			g2d.drawLine(5+i*a, panel.getHeight()-5, 5+i*a, panel.getHeight()-5-3);
		g2d.dispose();
	}
	
	//获取最初unhappiness值
	public double getInitialUnhappiness() {
		double initialUnhappiness=0;
		for(int i=0;i<numBugs;i++) 
			initialUnhappiness+=heatbugs[i].idealTemp; //最初每个虫子的unhappiness值就是其理想温度值（因为初始格子温度为0）
		initialUnhappiness/=numBugs;
		return initialUnhappiness;
	}
	
	//在unhappiness图中画点
	public void drawPoint(JPanel panel,int[][] temp,int i) {
		Graphics g=panel.getGraphics();
		Graphics2D g2d=(Graphics2D)g;
		g2d.setColor(Color.BLACK);
		double unhappiness=getOverallUnhappiness(); //获得总体unhappiness均值
		int a=(panel.getWidth()-10)/time;
		double k=(panel.getHeight()-10)/getInitialUnhappiness(); //纵轴长度与最大unhappiness值（初始时）之间的比例
		unhappiness*=k; //根据比例将unhappiness值换成在纵轴上对应的长度
		Ellipse2D ellipse2d=new Ellipse2D.Double(5+i*a-1, panel.getHeight()-5-unhappiness-1, 2, 2); //圆圈
		g2d.draw(ellipse2d);
		g2d.fill(ellipse2d); //涂成实心					
		g2d.dispose();
	}
	
	public static void main(String args[]) {
		HeatBugView myView=new HeatBugView(); //此类实例化
		myView.init(); //初始化
	}
	
	
	
}