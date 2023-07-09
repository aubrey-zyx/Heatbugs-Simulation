package heatbug;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.geom.Ellipse2D;
import java.util.*;


/*
   *֣��诡���ŵͮ
 *2020��12��
 */

//������HeatBugView
public class HeatBugView extends JFrame implements ActionListener{
	
	JFrame frame;
	JPanel panel1; //����ʵ�����
	JPanel panel2; //����
	JPanel panel3; //��դͼ
	JPanel panel4; //unhappiness�仯ͼ
	int numBugs; //�ȳ�����
	int minIdealTemp; //��������¶�
	int maxIdealTemp; //��������¶�
	int minOutputHeat; //��С�ͷ�����
	int maxOutputHeat; //����ͷ�����
	int time; //�ȳ��˶�ʱ�䣨������
	JButton startBtn; //����ʼ����ť
	JButton quitBtn; //���˳�����ť
	HeatBug[] heatbugs; //�ȳ�����
	JTextField numBugsTf; //���롰�ȳ����������ı���
	JTextField minIdealTempTf; //���롰��������¶ȡ����ı���
	JTextField maxIdealTempTf; //���롰��������¶ȡ����ı���
	JTextField minOutputHeatTf; //���롰��С�ͷ����������ı���
	JTextField maxOutputHeatTf; //���롰����ͷ����������ı���
	JTextField timeTf; //���롰�ȳ��˶����������ı���

	//���캯��
	public HeatBugView() {		
		frame=new JFrame("�ȳ�ģ��");
		frame.setVisible(true);
		frame.setSize(600, 600);
		frame.setLayout(new GridLayout(2,2)); //��frame���ֳ�4��
				
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
		panel1.setBorder(BorderFactory.createTitledBorder("����ʵ�����"));
		//�ؼ�ʵ����
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
		//���ؼ����뵽panel1��
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
		panel2.setBorder(BorderFactory.createTitledBorder("����"));
	    startBtn=new JButton("start");
	    startBtn.setBounds(50, 60, 200, 70); //���á���ʼ����ť��С
	    startBtn.addActionListener(this);
	    quitBtn=new JButton("quit");
	    quitBtn.setBounds(50, 170, 200, 70); //���á��˳�����ť��С
	    quitBtn.addActionListener(this);
		panel2.add(startBtn);
		panel2.add(quitBtn);
		
		//panel3
		panel3=new JPanel();
		frame.add(panel3);
		panel3.setLayout(new GridLayout(30,30)); //��panel3���ֳ�30*30��
		
		//panel4
		panel4=new JPanel();
		frame.add(panel4);
	}
		
	int temp[][]=new int[30][30]; //���ÿ�������¶ȵĶ�ά����
	JLabel[][] labels=new JLabel[30][30]; //���30*30��label�Ķ�ά���飬����30*30������
	
	//��ʼ����������label��ɺ�ɫ�����뵽panel3��
	public void init() {			
		for(int i=0;i<30;i++) 
			for(int j=0;j<30;j++) {
				labels[i][j]=new JLabel();
				labels[i][j].setOpaque(true);
				labels[i][j].setBackground(Color.black);
				panel3.add(labels[i][j]);
			}
	}

	//�����¼�
	public void actionPerformed(ActionEvent e) {
		//�ŵ��������߳��У��������߳���������ʵ�ֽ���ˢ��
		new Thread(new Runnable() {
			@Override
			public void run() {
				//������˳�����ť
				if(e.getSource()==quitBtn) {
					System.exit(0);
				}
				
				//�������ʼ����ť
				int movement=0; //�����˶�����0�����ϣ�1������2�����ң�3�����£�4����ԭ�ز���
				int evaporation=0; //ϵͳɢ��
				Random rdm=new Random();
				if(e.getSource()==startBtn) {
					//��ȡ�û��������ݼ���				
					try {
						numBugs=Integer.parseInt(numBugsTf.getText());
					} 
					catch (Exception e2) {
						// TODO: handle exception
						JOptionPane.showMessageDialog(null, "numBugs����Ϊ������","��ʾ",JOptionPane.PLAIN_MESSAGE);
						return;
					}
					try {
						minIdealTemp=Integer.parseInt(minIdealTempTf.getText());
					} 
					catch (Exception e2) {
						// TODO: handle exception
						JOptionPane.showMessageDialog(null, "minIdealTemp����Ϊ������","��ʾ",JOptionPane.PLAIN_MESSAGE);
						return;
					}
					try {
						maxIdealTemp=Integer.parseInt(maxIdealTempTf.getText());
					} 
					catch (Exception e2) {
						// TODO: handle exception
						JOptionPane.showMessageDialog(null, "maxIdealTemp����Ϊ������","��ʾ",JOptionPane.PLAIN_MESSAGE);
						return;
					}
					try {
						minOutputHeat=Integer.parseInt(minOutputHeatTf.getText());
					} 
					catch (Exception e2) {
						// TODO: handle exception
						JOptionPane.showMessageDialog(null, "minOutputHeat����Ϊ������","��ʾ",JOptionPane.PLAIN_MESSAGE);
						return;
					}
					try {
						maxOutputHeat=Integer.parseInt(maxOutputHeatTf.getText());
					} 
					catch (Exception e2) {
						// TODO: handle exception
						JOptionPane.showMessageDialog(null, "maxOutputHeat����Ϊ������","��ʾ",JOptionPane.PLAIN_MESSAGE);
						return;
					}
					try {
						time=Integer.parseInt(timeTf.getText());
					} 
					catch (Exception e2) {
						// TODO: handle exception
						JOptionPane.showMessageDialog(null, "time����Ϊ������","��ʾ",JOptionPane.PLAIN_MESSAGE);
						return;
					}
					if(minIdealTemp>=maxIdealTemp||minOutputHeat>=maxOutputHeat) {
						JOptionPane.showMessageDialog(null, "���ֵ���������Сֵ��","��ʾ",JOptionPane.PLAIN_MESSAGE);
						return;
					}
					if(time>100||time<1) {
						JOptionPane.showMessageDialog(null, "timeȡֵ������1~100��","��ʾ",JOptionPane.PLAIN_MESSAGE);
						return;
					}
					if(numBugs>300||numBugs<1) {
						JOptionPane.showMessageDialog(null, "numBugsȡֵ������1~300��","��ʾ",JOptionPane.PLAIN_MESSAGE);
						return;
					}
			
					heatbugs=new HeatBug[numBugs]; //�����û�������ȳ���ʵ����һ��HeatBug���͵�����
					drawLineXY(panel4); //��unhappiness����ͼ��XY��
					drawXScale(panel4,time); //��X��̶�
					
					//������Ӧ�������ȳ沢��ʼ��
					for(int t=0;t<numBugs;t++) {
						heatbugs[t]=new HeatBug();						
						//��������ȳ��������е�λ��
						heatbugs[t].x=rdm.nextInt(30); 
						heatbugs[t].y=rdm.nextInt(30);	
						//�ȳ�������¶Ⱥ��ͷ�����������С���Χ���������
						heatbugs[t].getIdealTemp(minIdealTemp, maxIdealTemp);
						heatbugs[t].getOutputHeat(minOutputHeat, maxOutputHeat);
						//���������ڵĸ�����ʾΪ��ɫ
						labels[heatbugs[t].x][heatbugs[t].y].setBackground(Color.green);
						evaporation+=heatbugs[t].outputHeat; 
					} 
					evaporation/=900; //����ÿ�����ӵ�ɢ��
					
					//ѭ����ʹ�ȳ��˶�time��
					int i=0;
					for(i=0;i<time;i++) {			
						drawPoint(panel4,temp,i); //ÿ���˶�ǰ��unhappiness����ͼ�л���
						
						//ÿ���ȳ���˶�
						for(int j=0;j<numBugs;j++) {	
							heatbugs[j].spreadHeat(temp); //������ɢ��							
							movement=heatbugs[j].move(temp); //����˶�����
							
							//�����˶��������ı�������label����ɫ����ʾ�ȳ���˶�
							switch(movement) {
							//�����ƶ���ԭ�����ӱ�ڣ��Ϸ����ӱ���
							case 0:{ 
								labels[heatbugs[j].x][heatbugs[j].y].setBackground(Color.black);
								labels[heatbugs[j].x][heatbugs[j].y-1].setBackground(Color.green);
								heatbugs[j].y--;
								break;
							}
							//�����ƶ���ԭ�����ӱ�ڣ��󷽸��ӱ���
							case 1:{
								labels[heatbugs[j].x][heatbugs[j].y].setBackground(Color.black);
								labels[heatbugs[j].x-1][heatbugs[j].y].setBackground(Color.green);
								heatbugs[j].x--;
								break;
							}
							//�����ƶ���ԭ�����ӱ�ڣ��ҷ����ӱ���
							case 2:{
								labels[heatbugs[j].x][heatbugs[j].y].setBackground(Color.black);
								labels[heatbugs[j].x+1][heatbugs[j].y].setBackground(Color.green);
								heatbugs[j].x++;
								break;
							}
							//�����ƶ���ԭ�����ӱ�ڣ��·����ӱ���
							case 3:{
								labels[heatbugs[j].x][heatbugs[j].y].setBackground(Color.black);
								labels[heatbugs[j].x][heatbugs[j].y+1].setBackground(Color.green);
								heatbugs[j].y++;
								break;
							}
							//ԭ�ز���
							case 4:{
								break;
							}
							}
						}
						//ϵͳɢ��
						for(int m=0;m<30;m++)
							for(int n=0;n<30;n++) 
								temp[m][n]-=evaporation;
						
						//ÿ���˶������߳���ͣƬ�̣�ʹ�˶�״������۲�
						try{
							Thread.sleep(200);
						}
						catch(InterruptedException e2) {
							e2.printStackTrace();
						}
						
					}
					drawPoint(panel4,temp,i); //���һ���˶���������ͼ�����յ�unhappiness��
				}
			}
		}).start();
	}
	
	//��������ȳ�����unhappinessֵ
	public double getOverallUnhappiness() {
		double overallUnhappiness=0;
		for(int i=0;i<numBugs;i++) {
			int itsUnhappiness=heatbugs[i].getUnhappiness(temp); //��ȡÿ�������Լ���unhappinessֵ
			overallUnhappiness+=itsUnhappiness;	//�Ӻ�
		}
		overallUnhappiness/=numBugs; //���ֵ
		return overallUnhappiness;
	}

	//��unhappiness����ͼ��XY��
	public void drawLineXY(JPanel panel) {		
		Graphics g=panel.getGraphics();
		Graphics2D g2d=(Graphics2D)g;
		g2d.clearRect(0, 0, panel.getWidth(), panel.getHeight()); //��ǰ����ɫ��ջ���
		g2d.setColor(Color.BLACK); //������ɫ											
		g2d.drawLine(5, 5, 5, panel.getHeight()-5); //������
		g2d.drawLine(5, panel.getHeight()-5, panel.getWidth()-5, panel.getHeight()-5); //������
		g2d.dispose();
	} 
	
	//��X��̶�
	public void drawXScale(JPanel panel, int time) {
		Graphics g=panel.getGraphics();
		Graphics2D g2d=(Graphics2D)g;
		g2d.setColor(Color.BLACK); //������ɫ
		int a=panel.getWidth()/time; //�����˶��������������̶�֮��ľ���
		//ѭ�����̶�
		for (int i=0; i<(time+1); i++) 
			g2d.drawLine(5+i*a, panel.getHeight()-5, 5+i*a, panel.getHeight()-5-3);
		g2d.dispose();
	}
	
	//��ȡ���unhappinessֵ
	public double getInitialUnhappiness() {
		double initialUnhappiness=0;
		for(int i=0;i<numBugs;i++) 
			initialUnhappiness+=heatbugs[i].idealTemp; //���ÿ�����ӵ�unhappinessֵ�����������¶�ֵ����Ϊ��ʼ�����¶�Ϊ0��
		initialUnhappiness/=numBugs;
		return initialUnhappiness;
	}
	
	//��unhappinessͼ�л���
	public void drawPoint(JPanel panel,int[][] temp,int i) {
		Graphics g=panel.getGraphics();
		Graphics2D g2d=(Graphics2D)g;
		g2d.setColor(Color.BLACK);
		double unhappiness=getOverallUnhappiness(); //�������unhappiness��ֵ
		int a=(panel.getWidth()-10)/time;
		double k=(panel.getHeight()-10)/getInitialUnhappiness(); //���᳤�������unhappinessֵ����ʼʱ��֮��ı���
		unhappiness*=k; //���ݱ�����unhappinessֵ�����������϶�Ӧ�ĳ���
		Ellipse2D ellipse2d=new Ellipse2D.Double(5+i*a-1, panel.getHeight()-5-unhappiness-1, 2, 2); //ԲȦ
		g2d.draw(ellipse2d);
		g2d.fill(ellipse2d); //Ϳ��ʵ��					
		g2d.dispose();
	}
	
	public static void main(String args[]) {
		HeatBugView myView=new HeatBugView(); //����ʵ����
		myView.init(); //��ʼ��
	}
	
	
	
}