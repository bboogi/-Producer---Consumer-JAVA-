import java.util.LinkedList; 
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;


//********************************************* Shared Memory ********************************************
class Generators{
	
	//<-----------��Ģ���� ��ȣ �� �߻� ������ ���� �������� ----------->
	String[] symbol = {"+", "-", "*", "/"};
	int[] three = new int[3];
	int[] four = new int[4];
	int[] five = new int[5];
	int[] six = new int [6];
	
	//<------------------------�����------------------------->
	static Queue<String> DataBase = new LinkedList<String>();
		//���� + ��ȣ ��ġ�� �迭
	String [] threeData = new String[5];
	String [] fourData = new String[7]; 
	String [] fiveData = new String[9]; 
	String [] sixData = new String[11];
		//��ģ �迭�� ���ڿ��� ������ ����
	String real; 
	
	//���� ũ��
	int size;
	public int getSize() {return size;}
	public void setSize(int size) {this.size = size;}
	
	//��Ģ���� ����
	int cnt;
	public int getCnt() {return cnt;}
	public void setCnt(int cnt) {this.cnt = cnt;}
	
	
	//<------------------ buffer------------------->
	public  synchronized void buffer(int size) {
		
				System.out.println("");
				System.out.println("** buffer : " + DataBase);
				System.out.println("");
	}//buffer


	//<-------------------������ ��Ģ���� ���� �Լ�------------------->
	public synchronized void generator() {
		//��������, ���� 3, 4, 5, 6 �� �����ؼ� ���� �߻�
		Random random = new Random();
		int pickOne = random.nextInt(4)+1; 
			
		switch (pickOne){
		case 1://<-----------------���� 3���� ���------------------->
			int count1 = 0;
			for(int i=0; i<3; i++) {
				
				//<--������ ���� ���-->
				int integerNum = random.nextInt(99)+1;
				three[i] = integerNum;
				System.out.print(three[i]);
				
				//<--�迭�� ����-->
				threeData[count1] =  Integer.toString(three[i]);
				
				//<--������ ��ȣ ���-->
				if(i == 2) break;
				else {
						count1++;
						int randomSymbol = random.nextInt(4); 
						System.out.print(symbol[randomSymbol]);
						//<--�迭�� ����-->
						threeData[count1] = symbol[randomSymbol];
					}
				count1++;
				}
			//�迭 -> ���ڿ��� ��ȯ ��, ť�� ����
			real = String.join("", threeData);
			DataBase.add(real);
		break;
		
		case 2://<-----------------���� 4���� ���------------------->
			int count2 = 0;
			for(int i=0; i<4; i++) {
				
				//<--������ ���� ���-->
				int integerNum = random.nextInt(99)+1;
				four[i] = integerNum;
				System.out.print(four[i]);
				
				//<--�迭�� ����-->
				fourData[count2] =  Integer.toString(four[i]);
				
				//<--������ ��ȣ ���-->
				if(i == 3) break;
				else {
					count2++;
					int randomSymbol = random.nextInt(4); 
					System.out.print(symbol[randomSymbol]);
					//<--�迭�� ����-->
					fourData[count2] = symbol[randomSymbol];	
				}
				count2++;
			}
			//�迭 -> ���ڿ��� ��ȯ ��, ť�� ����
			real = String.join("", fourData);
			DataBase.add(real);
		break;
		
		case 3://<-----------------���� 5���� ���------------------->
			int count3 = 0;
			for(int i=0; i<5; i++) {
				
				//<--������ ���� ���-->
				int integerNum = random.nextInt(99)+1; 
				five[i] = integerNum;
				System.out.print(five[i]);
				
				//<--�迭�� ����-->
				fiveData[count3] =  Integer.toString(five[i]);
				
				//<--������ ��ȣ ���-->
				if(i == 4) break;
				else {	
					count3++;
					int randomSymbol = random.nextInt(4);
					System.out.print(symbol[randomSymbol]);
					//<--�迭�� ����-->
					fiveData[count3] = symbol[randomSymbol];
					}
				count3++;
				}
			//�迭 -> ���ڿ��� ��ȯ ��, ť�� ����
			real = String.join("", fiveData);
			DataBase.add(real);
		break;
		
		case 4://<-----------------���� 6���� ���------------------->
			int count4 = 0;
			for(int i=0; i<6; i++) {
				
				//<--������ ���� ���-->
				int integerNum = random.nextInt(99)+1; 
				six[i] = integerNum;
				System.out.print(six[i]);
				
				//<--�迭�� ����-->
				sixData[count4] =  Integer.toString(six[i]);
				
				//<--������ ��ȣ ���-->
				if(i == 5) break;
				else {
					count4++;
					int randomSymbol = random.nextInt(4); 
					System.out.print(symbol[randomSymbol]);
					//<--�迭�� ����-->
					sixData[count4] = symbol[randomSymbol];
				}//if
				count4++;
			}//for
			//�迭 -> ���ڿ��� ��ȯ ��, ť�� ����
			real = String.join("", sixData);
			DataBase.add(real);
		break;
		}//switch
		System.out.println(" ");
	}
	
	
	//<-------------------������ ��Ģ���� ���� ��------------------->
	 public synchronized void generator_end() { 
	            notify();
	            try {Thread.sleep(1000);} catch (InterruptedException e){e.printStackTrace();}//try
	    }//generator_end
	
	
	//<-------------------������ ��Ģ���� ��� �Լ�------------------->
	public synchronized void calculation() {

		try {
			wait();
			System.out.println(" ");
			System.out.println("<------* ������ *------>");
			System.out.println(" ");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		try {
			int num = 1; //consumer ����
			while(true) {
				for(int i =0; i< size ; i++) {
					String reals = DataBase.peek();
					if(DataBase.isEmpty()) {break;} 
					StringTokenizer r_token = new StringTokenizer(reals, "+-*/", true);
					double Value = Double.parseDouble(r_token.nextToken());
		
					//��Ģ���� �켱������ ������� ���� ��� �˰��� �Դϴ�.
					while(r_token.hasMoreElements()) {
						String symbol = r_token.nextToken();
						if(symbol.equals("*")) {
							symbol = r_token.nextToken();
							Value *= Integer.parseInt(symbol);
						}
						if(symbol.equals("/")) {
							symbol = r_token.nextToken();
							Value /= Integer.parseInt(symbol);
						}
						if(symbol.equals("+")) {
							symbol = r_token.nextToken();
							Value += Integer.parseInt(symbol);
						}
						if(symbol.equals("-")) {
							symbol = r_token.nextToken();
							Value -= Integer.parseInt(symbol);
						}		
					}//while
					Thread.sleep(1000);
					System.out.println( "["+num+"] "+"consumer : " +reals + " = " + String.format("%.1f", Value));
					num++;
					DataBase.remove();
				}//for
				Thread.sleep(1000);
				System.out.println("");
				System.out.println("** buffer : " + DataBase);
				System.out.println("");
				if(DataBase.isEmpty()) {break;} 
			}//while
		
		}catch(InterruptedException e) {
			return;
		}//try
	}//calculation
}


//*********************************************** Producer Thread ***********************************************
class Producer implements Runnable {
	Scanner sc = new Scanner(System.in);
	Generators g = new Generators();
	Producer(Generators g) {this.g = g;}
	
	@Override
	public void run() {
		
		System.out.print("��Ģ���� �� ���� �Է� >");int count = sc.nextInt();
		System.out.print("���� ũ�� �Է� >");int size = sc.nextInt();
		System.out.println("");
		System.out.println("<------* �������� *------>");
		System.out.println("");
		g.setSize(size); g.setCnt(count);
		int key =0; //���� Ű
		try {
			for(int i=0; i < count ; i++ ) {
					Thread.sleep(1000);
					key++;
					if(size < key) {
						g.buffer(size); 
						key = 0;
						Thread.sleep(1000);
						i--;	
					}else {
						System.out.print( "[" + (i+1) +"]" + " producer : ");
						g.generator();	
					}//if
				}//for	
			Thread.sleep(1000);
			g.buffer(size);
			}catch(InterruptedException e) {		
					return;
			}//try
		sc.close();	
		g.generator_end();
	}//run
}//producer


//*********************************************** Consumer Thread ***********************************************
class Consumer implements Runnable{
	Generators g = new Generators();
	Consumer(Generators g) {this.g = g;}

	@Override
	public void run() {
			g.calculation();
			System.out.println("");
	}//run	
}//consumer


//**************************************************** Main *****************************************************
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Generators g = new Generators();
		
		//������ ��ü ���� �� ����
		Thread p = new Thread(new Producer(g)); p.start();
		Thread c = new Thread(new Consumer(g)); c.start();
		
	}
}

