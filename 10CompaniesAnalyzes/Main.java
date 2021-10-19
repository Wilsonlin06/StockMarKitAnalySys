import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
class Reset{
	private String [][] storage = new String[11][8];
	private double Bul_Sum = 0,Bul_unitSum = 0, ttlBul_Sum = 0,
			Bear_Sum = 0, Bear_unitSum = 0 , ttlBear_Sum = 0, bear_cumul = 0,
			bull_cumul = 0;
	private String [][] ttl_Bullish = new String[900][8];
	private String [][] ttl_Bearish = new String[900][8];
	private int bul_row, bea_row, win = 0, lose = 0, ttlbul_win = 0, ttlbul_lose = 0, ttlbea_win = 0, ttlbea_lose = 0;
	private int ttl_Bulcount = 0, ttl_Bearcount;
	
	public Reset(){
		ttl_Bullish[0][0] = ttl_Bearish[0][0] = storage[0][0] = "Company:";
		ttl_Bullish[1][0] = ttl_Bearish[1][0] = storage[1][0] = "Case:";
		ttl_Bullish[2][0] = ttl_Bearish[2][0] = storage[2][0] = "Activity:";
		ttl_Bullish[1][1] = "Bullish";
		ttl_Bullish[2][1] = "Buy";
		ttl_Bearish[1][1] = "Bearish";
		ttl_Bearish[2][1] = "Sell";
		bul_row = 3;
		bea_row = 3;
		for(int i = 0; i < 3; i++){
			for(int j = 2; j < 8; j++){
				ttl_Bullish[i][j] = ttl_Bearish[i][j] = storage[i][j] = "	";
			}
		}
	}
	public void reset(){
		
	}
	public void setBullish(String Date, double [][] nums, String comp, int stoi, int numi){
			storage[stoi][1] = Date;
			storage[stoi][3] = Double.toString(nums[numi][0]);
			storage[stoi][5] = Double.toString(nums[numi][1]);
			Bul_Sum = ((nums[numi][1]-nums[numi][0])/nums[numi][0])*100;
			storage[stoi][7] = Double.toString(Bul_Sum)+" %";
			Bul_unitSum += Bul_Sum;
			ttl_Bullish[0][1] = comp.substring(0, comp.indexOf('.'));
			if(nums[numi][1]>=nums[numi][0]){
				win++;
				ttlbul_win++;
			}else{
				lose++;
				ttlbul_lose++;
			}
	}
	public void setBearish(String Date, double [][] nums, String comp, int stoi, int numi){
			storage[stoi][1] = Date;
			storage[stoi][3] = Double.toString(nums[numi][0]);
			storage[stoi][5] = Double.toString(nums[numi][1]);
			Bear_Sum = ((nums[numi][0]-nums[numi][1])/nums[numi][0])*100;
			storage[stoi][7] = Double.toString(Bear_Sum)+" %";
			Bear_unitSum += Bear_Sum;
			ttl_Bearish[0][1] = comp.substring(0, comp.indexOf('.'));
			if(nums[numi][1]<=nums[numi][0]){
				win++;
				ttlbea_win++;
			}else{
				lose++;
				ttlbea_lose++;
			}
	}
	public void setBu_found(String date){
		ttl_Bullish[bul_row][0] = "=====================================================================================================================================";
		for(int i = 1; i<8;i++)	ttl_Bullish[bul_row][i] ="";
		bul_row++;
		ttl_Bulcount++;
		ttl_Bullish[bul_row][0] = "Number: ";
		ttl_Bullish[bul_row][1] = Integer.toString(ttl_Bulcount);
		for(int i = 2; i<8;i++)	ttl_Bullish[bul_row][i] ="";
		bul_row++;
		ttl_Bullish[bul_row][0] = "Found Date: ";
		ttl_Bullish[bul_row][1] = date;
		for(int i = 2; i < 8; i++) ttl_Bullish[bul_row][i] = "	";
		bul_row++;
	}
	public void setBe_found(String date){
		ttl_Bearish[bea_row][0] = "=====================================================================================================================================";
		for(int i = 1; i<8;i++)	ttl_Bearish[bea_row][i] ="";
		bea_row++;
		ttl_Bearcount++;
		ttl_Bearish[bea_row][0] = "Number: ";
		ttl_Bearish[bea_row][1] = Integer.toString(ttl_Bearcount);
		for(int i = 2; i<8;i++)	ttl_Bearish[bea_row][i] ="";
		bea_row++;
		ttl_Bearish[bea_row][0] = "Found Date: ";
		ttl_Bearish[bea_row][1] = date;
		for(int i = 2; i < 8; i++) ttl_Bearish[bea_row][i] = "	";
		bea_row++;
	}
	public void setBu_final(String Date){
		storage[8][1] = Date;
		storage[8][3] = storage[9][3];
		storage[8][5] = storage[9][5];
		storage[8][7] = storage[9][7]+" %";
	}
	public void fnlBullish(){
		ttl_Bullish[bul_row][0] = "Exit 5th day:";
		ttl_Bullish[bul_row][1] = storage[8][1];
		ttl_Bullish[bul_row][2] = "Enter price:";
		ttl_Bullish[bul_row][3] = storage[8][3];
		ttl_Bullish[bul_row][4] = "Exit price:";
		ttl_Bullish[bul_row][5] = storage[8][5];
		ttl_Bullish[bul_row][6] = "Profit rate:";
		ttl_Bullish[bul_row][7] = storage[8][7];
		ttl_Bullish[bul_row+1][2] = "Win:";
		ttl_Bullish[bul_row+1][3] = Integer.toString(win);
		ttl_Bullish[bul_row+1][4] = "Lose:";
		ttl_Bullish[bul_row+1][5] = Integer.toString(lose);
		ttl_Bullish[bul_row+1][0] = ttl_Bullish[bul_row+1][1]
		= ttl_Bullish[bul_row+1][6] = ttl_Bullish[bul_row+1][7] = "";
		win = lose = 0;
	}
	public void setBear_final(String Date){
		storage[8][1] = Date;
		storage[8][3] = storage[10][3];
		storage[8][5] = storage[10][5];
		storage[8][7] = storage[10][7]+" %";
	}
	public void fnlBearish(){
		ttl_Bearish[bea_row][0] = "Exit 5th day:";
		ttl_Bearish[bea_row][1] = storage[8][1];
		ttl_Bearish[bea_row][2] = "Enter price:";
		ttl_Bearish[bea_row][3] = storage[8][3];
		ttl_Bearish[bea_row][4] = "Exit price:";
		ttl_Bearish[bea_row][5] = storage[8][5];
		ttl_Bearish[bea_row][6] = "Profit rate:";
		ttl_Bearish[bea_row][7] = storage[8][7];
		ttl_Bearish[bea_row+1][2] = "Win:";
		ttl_Bearish[bea_row+1][3] = Integer.toString(win);
		ttl_Bearish[bea_row+1][4] = "Lose:";
		ttl_Bearish[bea_row+1][5] = Integer.toString(lose);
		ttl_Bearish[bea_row+1][0] = ttl_Bearish[bea_row+1][1]
		= ttl_Bearish[bea_row+1][6] = ttl_Bearish[bea_row+1][7] = "";
		win = lose = 0;
	}
	public void comBullish(int row, int stoi, int k){
		ttl_Bullish[bul_row+row][0] = "Enter Date"+Integer.toString(k)+":";
		ttl_Bullish[bul_row+row][2] = "Enter price:";
		ttl_Bullish[bul_row+row][4] = "Exit price:";
		ttl_Bullish[bul_row+row][6] = "Profit rate:";
		ttl_Bullish[bul_row+row][1] = storage[stoi][1];
		ttl_Bullish[bul_row+row][3] = storage[stoi][3];
		ttl_Bullish[bul_row+row][5] = storage[stoi][5];
		ttl_Bullish[bul_row+row][7] = storage[stoi][7];
	}
	public void setBu_Cumul(double cumul, double ttl_cumul){
		ttl_Bullish[bul_row][4] = "Cumulative for 5 days:";
		ttl_Bullish[bul_row][6] = "Profit rate:";
		ttl_Bullish[bul_row][7] = Double.toString(cumul)+" %";
		ttl_Bullish[bul_row][0] = ttl_Bullish[bul_row][1] = ttl_Bullish[bul_row][2] = ttl_Bullish[bul_row][3] = "	";
		ttl_Bullish[bul_row][5] = "";
		bul_row++;
		ttl_Bullish[bul_row][4] = "Total cumulative:";
		ttl_Bullish[bul_row][6] = "Profit rate:";
		ttl_Bullish[bul_row][7] = Double.toString(ttl_cumul)+" %";
		ttl_Bullish[bul_row][0] = ttl_Bullish[bul_row][1] = ttl_Bullish[bul_row][2] = ttl_Bullish[bul_row][3] = "	";
		ttl_Bullish[bul_row][5] = "";
		bul_row++;
	}
	public void comBearish(int row, int stoi, int k){
		ttl_Bearish[bea_row+row][0] = "Enter Date"+Integer.toString(k)+":";
		ttl_Bearish[bea_row+row][2] = "Enter price:";
		ttl_Bearish[bea_row+row][4] = "Exit price:";
		ttl_Bearish[bea_row+row][6] = "Profit rate:";
		ttl_Bearish[bea_row+row][1] = storage[stoi][1];
		ttl_Bearish[bea_row+row][3] = storage[stoi][3];
		ttl_Bearish[bea_row+row][5] = storage[stoi][5];
		ttl_Bearish[bea_row+row][7] = storage[stoi][7];
	}
	public void setBea_Cumul(double cumul, double ttl_cumul){
		ttl_Bearish[bea_row][4] = "Cumulative for 5 days:";
		ttl_Bearish[bea_row][6] = "Profit rate:";
		ttl_Bearish[bea_row][7] = Double.toString(cumul)+" %";
		ttl_Bearish[bea_row][0] = ttl_Bearish[bea_row][1] = ttl_Bearish[bea_row][2] = ttl_Bearish[bea_row][3] = "	";
		ttl_Bearish[bea_row][5] = "";
		bea_row++;
		ttl_Bearish[bea_row][4] = "Total cumulative:";
		ttl_Bearish[bea_row][6] = "Profit rate:";
		ttl_Bearish[bea_row][7] = Double.toString(ttl_cumul)+" %";
		ttl_Bearish[bea_row][0] = ttl_Bearish[bea_row][1] = ttl_Bearish[bea_row][2] = ttl_Bearish[bea_row][3] = "	";
		ttl_Bearish[bea_row][5] = "";
		bea_row++;
	}
	public void addBulrow(){
		bul_row += 5; 
	}
	public void addTwoBulrow(){
		bul_row += 2;
	}
	public void addBearow(){
		bea_row += 5; 
	}
	public void addTwoBearow() {
		bea_row += 2;
		
	}
//	public void addbul_Ave(){
//		ttl_Bullish[bul_row][0] = "Average:";
//		ttl_Bullish[bul_row][1] = "Days:";
//		ttl_Bullish[bul_row][2] = "5";
//		ttl_Bullish[bul_row][6] = "ProfitRate:";
//		ttl_Bullish[bul_row][7] = Double.toString(Bul_unitSum/5)+" %";
//		for(int i = 3; i < 6; i++) ttl_Bullish[bul_row][i] = "	";
//		++bul_row;
//		ttlBul_Sum += Bul_unitSum;
//	}
//	public void addbea_Ave(){
//		ttl_Bearish[bea_row][0] = "Average:";
//		ttl_Bearish[bea_row][1] = "Days:";
//		ttl_Bearish[bea_row][2] = "5";
//		ttl_Bearish[bea_row][6] = "ProfitRate:";
//		ttl_Bearish[bea_row][7] = Double.toString(Bear_unitSum/5)+" %";
//		for(int i = 3; i < 6; i++) ttl_Bearish[bea_row][i] = "	";
//		++bea_row;
//		ttlBear_Sum += Bear_unitSum;
//	}
	public void addbul_ttlAve(){
		ttl_Bullish[bul_row][0] = "Total Win:";
		ttl_Bullish[bul_row][1] = Integer.toString(ttlbul_win);
		ttl_Bullish[bul_row][2] = "Total Lose:";
		ttl_Bullish[bul_row][3] = Integer.toString(ttlbul_lose);
		ttl_Bullish[bul_row][4] = ttl_Bullish[bul_row][5]
		= ttl_Bullish[bul_row][6] = ttl_Bullish[bul_row][7] = "";
//		ttl_win = ttl_lose = 0;
		ttl_Bullish[bul_row+1][0] = "Total Profit:";
		ttl_Bullish[bul_row+1][1] = "Found:";
		ttl_Bullish[bul_row+1][2] = Integer.toString(ttl_Bulcount)+" Patterns.";
		ttl_Bullish[bul_row+1][6] = "Total ProfitRate:";
		ttl_Bullish[bul_row+1][7] = Double.toString(ttlBul_Sum)+" %";
		for(int i = 3; i < 6; i++) ttl_Bullish[bul_row+1][i] = "	";
		ttl_Bullish[bul_row+2][0] = "Average Profit:";
		ttl_Bullish[bul_row+2][1] = "Days:";
		ttl_Bullish[bul_row+2][2] = Integer.toString(ttl_Bulcount)+" Patterns.";
		ttl_Bullish[bul_row+2][6] = "Total Ave. ProfitRate:";
		ttl_Bullish[bul_row+2][7] = Double.toString(ttlBul_Sum/ttl_Bulcount)+" %";
		for(int i = 3; i < 6; i++) ttl_Bullish[bul_row+2][i] = "	";
		bul_row += 3;
		ttl_Bulcount = 0;
	}
	public void addbea_ttlAve(){
		ttl_Bearish[bea_row][0] = "Total Win:";
		ttl_Bearish[bea_row][1] = Integer.toString(ttlbea_win);
		ttl_Bearish[bea_row][2] = "Total Lose:";
		ttl_Bearish[bea_row][3] = Integer.toString(ttlbea_lose);
		ttl_Bearish[bea_row][4] = ttl_Bearish[bea_row][5]
		= ttl_Bearish[bea_row][6] = ttl_Bearish[bea_row][7] = "";
		ttl_Bearish[bea_row+1][0] = "Total Profit:";
		ttl_Bearish[bea_row+1][1] = "Found:";
		ttl_Bearish[bea_row+1][2] = Integer.toString(ttl_Bearcount)+" Patterns.";
		ttl_Bearish[bea_row+1][6] = "Total ProfitRate:";
		ttl_Bearish[bea_row+1][7] = Double.toString(ttlBear_Sum)+" %";
		for(int i = 3; i < 6; i++) ttl_Bearish[bea_row+1][i] = "	";
		ttl_Bearish[bea_row+2][0] = "Average Profit:";
		ttl_Bearish[bea_row+2][1] = "Days:";
		ttl_Bearish[bea_row+2][2] = Integer.toString(ttl_Bearcount)+" Patterns.";
		ttl_Bearish[bea_row+2][6] = "Total Ave. ProfitRate:";
		ttl_Bearish[bea_row+2][7] = Double.toString(ttlBear_Sum/ttl_Bearcount)+" %";
		for(int i = 3; i < 6; i++) ttl_Bearish[bea_row+2][i] = "	";
		bea_row += 3;
	}
	public void setUnitSumZero(){
		Bul_unitSum = 0;
		Bear_unitSum = 0;
	}
	public double getBulAve(){
		return Bul_Sum;
	}
	public double getBeaAve(){
		return Bear_Sum;
	}
	public void displayBul(double a, int b){
		for(int i = 0; i < bul_row; i++){
			for(int j = 0; j < 8; j++){
				System.out.print(ttl_Bullish[i][j]+"	");
			}
			System.out.println();
		}
	}
	public void displayBea(double a, int b){
		for(int i = 0; i < bea_row; i++){
			for(int j = 0; j < 8; j++){
				System.out.print(ttl_Bearish[i][j]+"	");
			}
			System.out.println();
		}
	}
	public void printBul(String comp){
		try (PrintWriter p = new PrintWriter(new FileOutputStream(comp+"-Bullish.csv", true))) {
			for(int i = 0; i < bul_row; i++){
				for(int j = 0; j < 8; j++){
					if(ttl_Bullish[i][0].contains("==")){
						p.print("======================================================================================");
						break;
					}
					else p.print(ttl_Bullish[i][j]+",");
				}
				p.println();
			}
			p.close();
		} catch (FileNotFoundException e1) {
		    e1.printStackTrace();
		}
	}
	public void printBear(String comp){
		try (PrintWriter p = new PrintWriter(new FileOutputStream(comp+"-Bearish.csv", true))) {
			for(int i = 0; i < bea_row; i++){
				for(int j = 0; j < 8; j++){
					if(ttl_Bearish[i][0].contains("==")) {
						p.print("======================================================================================");
						break;
					}
					else p.print(ttl_Bearish[i][j]+",");
				}
				p.println();
			}
			p.close();
		} catch (FileNotFoundException e1) {
		    e1.printStackTrace();
		}
	}
	public void printBoth(String comp){
		try (PrintWriter p = new PrintWriter(new FileOutputStream(comp+"-Both.csv", true))) {
			for(int i = 0; i < bul_row; i++){
				for(int j = 0; j < 8; j++){
					if(ttl_Bullish[i][0].contains("==")){
						p.print("======================================================================================");
						break;
					}
					else p.print(ttl_Bullish[i][j]+",");
				}
				p.println();
			}
			p.println("=======================================End Bullish=======================================");
			p.println("======================================Start Bearish======================================");
			for(int i = 0; i < bea_row; i++){
				for(int j = 0; j < 8; j++){
					if(ttl_Bearish[i][0].contains("==")) {
						p.print("======================================================================================");
						break;
					}
					else p.print(ttl_Bearish[i][j]+",");
				}
				p.println();
			}
			p.close();
		} catch (FileNotFoundException e1) {
		    e1.printStackTrace();
		}
	}
	public void setfnlBulStorage1(double[][] data2) {
		storage[9][3] = Double.toString(data2[0][0]);
		
	}
	public void setfnlBulStorage2(double[][] data2) {
		storage[9][5] = Double.toString(data2[4][1]);
		storage[9][7] = Double.toString(((data2[4][1]-data2[0][0])/data2[0][0])*100);
		ttlBul_Sum += ((data2[4][1]-data2[0][0])/data2[0][0])*100;
	}
	public void setfnlBearStorage1(double[][] data2) {
		storage[10][3] = Double.toString(data2[0][0]);
		
	}
	public void setfnlBearStorage2(double[][] data2) {
		storage[10][5] = Double.toString(data2[4][1]);
		storage[10][7] = Double.toString(((data2[0][0]-data2[4][1])/data2[0][0])*100);
		ttlBear_Sum += ((data2[0][0]-data2[4][1])/data2[0][0])*100;
	}
}
class Check{
	private double fst_high, fst_low, fst_open, fst_close;
	private String fst_date;
	private String [] Blh_date = new String[10000];
	private String [] Brh_date = new String[10000];
	public int count = 0;
	public Check(double open, double high, double low, double close, String date){
		fst_high = high;
		fst_low = low;
		fst_open = open;
		fst_close = close;
		fst_date = date;
	}
	public boolean Bullish(double open, double high, double low, double close){
		if(open < close){
			if(high >= fst_high && low <= fst_low){
				if(((fst_open > fst_close)&&(open <= fst_close && close >= fst_open))||((fst_open < fst_close)&&(open <= fst_open && close >= fst_close))){
					Blh_date[count] = fst_date;
					count++;
					return true;
				}else return false;
			}else return false;
		}else return false;
	}
	public boolean Bearish(double open, double high, double low, double close){
		if(open > close){
			if(high >= fst_high && low <= fst_low){
				if(((fst_open > fst_close)&&(open >= fst_open && close <= fst_close))||((fst_close > fst_open)&&(open >= fst_close && close <= fst_open))){
					Brh_date[count] = fst_date;
					count++;
					return true;
				}else return false;
			}else return false;
		}else return false;
	}
}
class Menu{
	Scanner input = new Scanner(System.in);
	private int chs, chk = 0, cas;
	private String name;
	private double [][] nums = new double[10000][6];
	private String [] date = new String[10000];
	private int row = 0;
	public Menu(){		//First menu--Selections of companies
		System.out.println("Select a company below: ");
		System.out.println("-Tech Company-\n1.Alphabet(GOOG)\n2.NVIDIA(NVDA)");
		System.out.println("-Finance Company-\n3.JP Morgan(JPM)\n4.Bank of America(BAC)");
		System.out.println("-Metals Company-\n5.Freeport Mcmoran(FCX)\n6.Heckler(HL)");
		System.out.println("-Retail Company-\n7.Best Buy(BBY)\n8.Costco(COST)");
		System.out.println("-Game Manufacturers-\n9.Electronic Arts(EA)\n10.Activision(ATVI)\n");
		do{
			try{
				System.out.print("Please enter a number(1~10): ");
				chs = Integer.parseInt(input.next());
				if(chs > 0 && chs < 11) chk = 1;
			}catch(Exception e){
			}
		}while(chk != 1);
	}
	public void menu(){		//Store company's file into BufferReader
		switch(chs){
		case 1:
			name = "GOOG.csv";
			break;
		case 2:
			name = "NVDA.csv";
			break;
		case 3:
			name = "JPM.csv";
			break;
		case 4:
			name = "BAC.csv";
			break;
		case 5:
			name = "FCX.csv";
			break;
		case 6:
			name = "HL.csv";
			break;
		case 7:
			name = "BBY.csv";
			break;
		case 8:
			name = "COST.csv";
			break;
		case 9:
			name = "EA.csv";
			break;
		case 10:
			name = "ATVI.csv";
			break;
		default:
			break;
		}
	}
	public int cas(){		//Second menu--Options of case
		System.out.println("Select a case below:");
		System.out.println("1.Bullish\n2.Bearish\n3.Both\n");
		chk = 0;
		do{
			try{
				System.out.print("Please enter a number(1~3): ");
				cas = Integer.parseInt(input.next());
				if(cas > 0 && cas < 4){
					switch(cas){
					case 1:
						return 1;
					case 2:
						return 2;
					case 3:
						return 3;
					default:
						break;
					}
					chk = 1;
				}
			}catch(Exception e){
			}
		}while(chk != 1);
		return 0;
	}
	public int getRow(){	//Return how many rows
		return row;
	}
	public String[] getDate(){
		return date;
	}
	public double[][] getData(){
		return nums;
	}
	public String getComp(){
		return name;
	}
	public void sort(){		//read the file and store the data into arrays
		try{
			BufferedReader in = new BufferedReader(new FileReader(new File(name)));
			String line;
			in.readLine();
			while((line = in.readLine()) != null){
				date[row] = (line.substring(0,line.indexOf(",")));
				line = line.substring(line.indexOf(",")+1);
				for(int i = 0; i < 6; i++){
					if(i == 5) nums[row][5] = Double.parseDouble(line);
					else
					nums[row][i] = Double.parseDouble(line.substring(0,line.indexOf(",")));
					line = line.substring(line.indexOf(",")+1);
				}
				row++;
			}
			in.close();
			}catch(IOException e){
				System.out.println("File I/O failed!");
			}
	}
}

public class Main {
	public static void main(String[] args){
		double [][] Data = new double[10000][6];
		double [][] Data2 = new double[5][2];
		double Ave1 = 0.0, Ave2 = 0.0, bull_cumul = 0.0, bear_cumul = 0.0, ttl_bul_cum = 0.0, ttl_bea_cum = 0.0;
		int cas, count1 = 0, count2 = 0;
		Menu menu = new Menu();
		menu.menu();
		menu.sort();
		cas = menu.cas();
		Data = menu.getData();
		String [] Date = new String[menu.getRow()];
		String [] Date2 = new String[5];
		Date = menu.getDate();
		Boolean value;
		Reset reset = new Reset();
		for(int i = 0; i < menu.getRow()-6; i++){
			Check Case = new Check(Data[i][0],Data[i][1],Data[i][2],Data[i][3],Date[i]);
				switch(cas){
					case 1:
						value = Case.Bullish(Data[i+1][0], Data[i+1][1], Data[i+1][2], Data[i+1][3]);
						if(value == true){
							for(int k = 0; k < 5; k++){
								Data2[k][0] = Data[i+k+2][0];
								Data2[k][1] = Data[i+k+2][3];
								Date2[k] = Date[i+k+2];
								bull_cumul += ((Data2[k][1]-Data2[k][0])/Data2[k][0])*100;
							}
							for(int k = 0; k < 5; k++){
							reset.setBullish(Date2[k], Data2, menu.getComp(), k+3, k);
							if(k == 0) reset.setfnlBulStorage1(Data2);
							if(k == 4) reset.setfnlBulStorage2(Data2);
							Ave1 += reset.getBulAve();
							count1++;
							}
							reset.setBu_found(Date[i+1]);
							for(int k = 0; k < 5; k++){
								reset.comBullish(k, k+3, k+1);
							}
							reset.addBulrow();
							ttl_bul_cum += bull_cumul;
							reset.setBu_Cumul(bull_cumul,ttl_bul_cum);
							bull_cumul = 0;
							String DateRange = Date2[0]+"\n		    to\n		"+Date2[4];
							reset.setBu_final(DateRange);
							reset.fnlBullish();
							reset.addTwoBulrow();
							reset.setUnitSumZero();
						}
						break;
					case 2:
						value = Case.Bearish(Data[i+1][0], Data[i+1][1], Data[i+1][2], Data[i+1][3]);
						if(value == true){
							for(int k = 0; k < 5; k++){
								Data2[k][0] = Data[i+k+2][0];
								Data2[k][1] = Data[i+k+2][3];
								Date2[k] = Date[i+k+2];
								bear_cumul += ((Data2[k][0]-Data2[k][1])/Data2[k][0])*100;
							}
							for(int k = 0; k < 5; k++){
							reset.setBearish(Date2[k], Data2, menu.getComp(), k+3, k);
							if(k == 0) reset.setfnlBearStorage1(Data2);
							if(k == 4) reset.setfnlBearStorage2(Data2);
							Ave2 += reset.getBeaAve();
							count2++;
							}
							reset.setBe_found(Date[i+1]);
							for(int k = 0; k < 5; k++){
								reset.comBearish(k, k+3, k+1);
							}
							reset.addBearow();
							ttl_bea_cum += bear_cumul;
							reset.setBea_Cumul(bear_cumul,ttl_bea_cum);
							bear_cumul = 0;
							String DateRange = Date2[0]+"\n		    to\n		"+Date2[4];
							reset.setBear_final(DateRange);
							reset.fnlBearish();
							reset.addTwoBearow();
							reset.setUnitSumZero();
						}
						break;
					case 3:
						value = Case.Bullish(Data[i+1][0], Data[i+1][1], Data[i+1][2], Data[i+1][3]);
						if(value == true){
							for(int k = 0; k < 5; k++){
								Data2[k][0] = Data[i+k+2][0];
								Data2[k][1] = Data[i+k+2][3];
								Date2[k] = Date[i+k+2];
								bull_cumul += ((Data2[k][1]-Data2[k][0])/Data2[k][0])*100;
							}
							for(int k = 0; k < 5; k++){
							reset.setBullish(Date2[k], Data2, menu.getComp(), k+3, k);
							if(k == 0) reset.setfnlBulStorage1(Data2);
							if(k == 4) reset.setfnlBulStorage2(Data2);
							Ave1 += reset.getBulAve();
							count1++;
							}
							reset.setBu_found(Date[i+1]);
							for(int k = 0; k < 5; k++){
								reset.comBullish(k, k+3, k+1);
							}
							reset.addBulrow();
							ttl_bul_cum += bull_cumul;
							reset.setBu_Cumul(bull_cumul,ttl_bul_cum);
							bull_cumul = 0;
							String DateRange = Date2[0]+"\n		    to\n		"+Date2[4];
							reset.setBu_final(DateRange);
							reset.fnlBullish();
							reset.addTwoBulrow();
						}
						reset.setUnitSumZero();
						value = Case.Bearish(Data[i+1][0], Data[i+1][1], Data[i+1][2], Data[i+1][3]);
						if(value == true){
							for(int k = 0; k < 5; k++){
								Data2[k][0] = Data[i+k+2][0];
								Data2[k][1] = Data[i+k+2][3];
								Date2[k] = Date[i+k+2];
								bear_cumul += ((Data2[k][0]-Data2[k][1])/Data2[k][0])*100;
							}
							for(int k = 0; k < 5; k++){
							reset.setBearish(Date2[k], Data2, menu.getComp(), k+3, k);
							if(k == 0) reset.setfnlBearStorage1(Data2);
							if(k == 4) reset.setfnlBearStorage2(Data2);
							Ave2 += reset.getBeaAve();
							count2++;
							}
							reset.setBe_found(Date[i+1]);
							for(int k = 0; k < 5; k++){
								reset.comBearish(k, k+3, k+1);
							}
							reset.addBearow();
							ttl_bea_cum += bear_cumul;
							reset.setBea_Cumul(bear_cumul,ttl_bea_cum);
							bear_cumul = 0;
							String DateRange = Date2[0]+"\n		    to\n		"+Date2[4];
							reset.setBear_final(DateRange);
							reset.fnlBearish();
							reset.addTwoBearow();
						}
				}
		}
		switch(cas){
		case 1:
			reset.addbul_ttlAve();
			reset.displayBul(Ave1, count1);
			reset.printBul(menu.getComp());
			break;
		case 2:
			reset.addbea_ttlAve();
			reset.displayBea(Ave2, count2);
			reset.printBear(menu.getComp());
			break;
		case 3:
			reset.addbul_ttlAve();
			reset.displayBul(Ave1, count1);
			System.out.println("===============================================================End Bullish===========================================================");
			System.out.println("==============================================================Start Bearish==========================================================");
			reset.addbea_ttlAve();
			reset.displayBea(Ave2, count2);
			reset.printBoth(menu.getComp());
			break;
		}
		
	}
}
