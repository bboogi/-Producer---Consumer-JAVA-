import java.util.LinkedList; 
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;


//********************************************* Shared Memory ********************************************
class Generators{
	
	//<-----------사칙연산 기호 및 발생 가능한 정수 구성범위 ----------->
	String[] symbol = {"+", "-", "*", "/"};
	int[] three = new int[3];
	int[] four = new int[4];
	int[] five = new int[5];
	int[] six = new int [6];
	
	//<------------------------저장소------------------------->
	static Queue<String> DataBase = new LinkedList<String>();
		//숫자 + 기호 합치는 배열
	String [] threeData = new String[5];
	String [] fourData = new String[7]; 
	String [] fiveData = new String[9]; 
	String [] sixData = new String[11];
		//합친 배열을 문자열로 저장할 변수
	String real; 
	
	//버퍼 크기
	int size;
	public int getSize() {return size;}
	public void setSize(int size) {this.size = size;}
	
	//사칙연산 개수
	int cnt;
	public int getCnt() {return cnt;}
	public void setCnt(int cnt) {this.cnt = cnt;}
	
	
	//<------------------ buffer------------------->
	public  synchronized void buffer(int size) {
		
				System.out.println("");
				System.out.println("** buffer : " + DataBase);
				System.out.println("");
	}//buffer


	//<-------------------무작위 사칙연산 생성 함수------------------->
	public synchronized void generator() {
		//무작위로, 정수 3, 4, 5, 6 중 선택해서 정수 발생
		Random random = new Random();
		int pickOne = random.nextInt(4)+1; 
			
		switch (pickOne){
		case 1://<-----------------정수 3개인 경우------------------->
			int count1 = 0;
			for(int i=0; i<3; i++) {
				
				//<--무작위 정수 출력-->
				int integerNum = random.nextInt(99)+1;
				three[i] = integerNum;
				System.out.print(three[i]);
				
				//<--배열에 저장-->
				threeData[count1] =  Integer.toString(three[i]);
				
				//<--무작위 기호 출력-->
				if(i == 2) break;
				else {
						count1++;
						int randomSymbol = random.nextInt(4); 
						System.out.print(symbol[randomSymbol]);
						//<--배열에 저장-->
						threeData[count1] = symbol[randomSymbol];
					}
				count1++;
				}
			//배열 -> 문자열로 변환 후, 큐에 저장
			real = String.join("", threeData);
			DataBase.add(real);
		break;
		
		case 2://<-----------------정수 4개인 경우------------------->
			int count2 = 0;
			for(int i=0; i<4; i++) {
				
				//<--무작위 정수 출력-->
				int integerNum = random.nextInt(99)+1;
				four[i] = integerNum;
				System.out.print(four[i]);
				
				//<--배열에 저장-->
				fourData[count2] =  Integer.toString(four[i]);
				
				//<--무작위 기호 출력-->
				if(i == 3) break;
				else {
					count2++;
					int randomSymbol = random.nextInt(4); 
					System.out.print(symbol[randomSymbol]);
					//<--배열에 저장-->
					fourData[count2] = symbol[randomSymbol];	
				}
				count2++;
			}
			//배열 -> 문자열로 변환 후, 큐에 저장
			real = String.join("", fourData);
			DataBase.add(real);
		break;
		
		case 3://<-----------------정수 5개인 경우------------------->
			int count3 = 0;
			for(int i=0; i<5; i++) {
				
				//<--무작위 정수 출력-->
				int integerNum = random.nextInt(99)+1; 
				five[i] = integerNum;
				System.out.print(five[i]);
				
				//<--배열에 저장-->
				fiveData[count3] =  Integer.toString(five[i]);
				
				//<--무작위 기호 출력-->
				if(i == 4) break;
				else {	
					count3++;
					int randomSymbol = random.nextInt(4);
					System.out.print(symbol[randomSymbol]);
					//<--배열에 저장-->
					fiveData[count3] = symbol[randomSymbol];
					}
				count3++;
				}
			//배열 -> 문자열로 변환 후, 큐에 저장
			real = String.join("", fiveData);
			DataBase.add(real);
		break;
		
		case 4://<-----------------정수 6개인 경우------------------->
			int count4 = 0;
			for(int i=0; i<6; i++) {
				
				//<--무작위 정수 출력-->
				int integerNum = random.nextInt(99)+1; 
				six[i] = integerNum;
				System.out.print(six[i]);
				
				//<--배열에 저장-->
				sixData[count4] =  Integer.toString(six[i]);
				
				//<--무작위 기호 출력-->
				if(i == 5) break;
				else {
					count4++;
					int randomSymbol = random.nextInt(4); 
					System.out.print(symbol[randomSymbol]);
					//<--배열에 저장-->
					sixData[count4] = symbol[randomSymbol];
				}//if
				count4++;
			}//for
			//배열 -> 문자열로 변환 후, 큐에 저장
			real = String.join("", sixData);
			DataBase.add(real);
		break;
		}//switch
		System.out.println(" ");
	}
	
	
	//<-------------------무작위 사칙연산 생성 끝------------------->
	 public synchronized void generator_end() { 
	            notify();
	            try {Thread.sleep(1000);} catch (InterruptedException e){e.printStackTrace();}//try
	    }//generator_end
	
	
	//<-------------------무작위 사칙연산 계산 함수------------------->
	public synchronized void calculation() {

		try {
			wait();
			System.out.println(" ");
			System.out.println("<------* 계산시작 *------>");
			System.out.println(" ");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		try {
			int num = 1; //consumer 개수
			while(true) {
				for(int i =0; i< size ; i++) {
					String reals = DataBase.peek();
					if(DataBase.isEmpty()) {break;} 
					StringTokenizer r_token = new StringTokenizer(reals, "+-*/", true);
					double Value = Double.parseDouble(r_token.nextToken());
		
					//사칙연산 우선순위를 고려하지 못한 계산 알고리즘 입니다.
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
		
		System.out.print("사칙연산 식 개수 입력 >");int count = sc.nextInt();
		System.out.print("버퍼 크기 입력 >");int size = sc.nextInt();
		System.out.println("");
		System.out.println("<------* 생성시작 *------>");
		System.out.println("");
		g.setSize(size); g.setCnt(count);
		int key =0; //기준 키
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
		
		//스레드 객체 생성 및 실행
		Thread p = new Thread(new Producer(g)); p.start();
		Thread c = new Thread(new Consumer(g)); c.start();
		
	}
}

