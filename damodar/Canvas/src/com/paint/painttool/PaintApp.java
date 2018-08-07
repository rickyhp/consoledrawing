package com.paint.painttool;

import java.util.Scanner;

class Paint {
	char[][] paintArray;
	int w, h;
	public Paint(int w, int h) {
		h+=2;
		w+=2;
		this.w = w;
		this.h = h;
		paintArray = new char[h][w];		
		drawLine(0, 0, this.w-1, 0, '-');
		drawLine(0, this.h-1, this.w-1, this.h-1, '-');
		drawLine(0, 1, 0, this.h-2, '|');
		drawLine(this.w-21, 1, this.w-1, this.h-2, '|');	
		drawLine(this.w-21, 1, this.w-2, this.h-2, ' ');
	} 
	
	public void render() {		
		for(int i=0;i<this.h;i++) {
			for(int j=0;j<this.w;j++) {				 
				System.out.print(paintArray[i][j]);
			}
			System.out.println();
		}
	}
	
	public void drawLine(int x1, int y1, int x2, int y2, char mchar) {
		for(int i=y1; i<=y2; i++) {
			for(int j=x1; j<=x2; j++) {
				paintArray[i][j] = mchar;				
			}
		}		 
  	} 
	
	public void drawRectangle(int x1, int y1, int x2, int y2, char mchar) {
		drawLine(x1,y1, x2, y1, mchar);
		drawLine(x1,y1, x1, y2, mchar);
		drawLine(x2,y1, x2, y2, mchar);
		drawLine(x1,y2, x2, y2, mchar);
	}
	
	public void bucketFill(int x, int y, char mchar) throws InterruptedException {
		if(paintArray[y][x] != ' ') {
			return;
		}
		
		if(x > 0 || x < this.h || y > 0 || y  < this.w) {
			if(paintArray[y][x] == ' ')
				paintArray[y][x] = mchar;
			bucketFill(x+1,y, mchar);
			bucketFill(x-1,y, mchar);
			bucketFill(x,y-1, mchar);
			bucketFill(x,y+1, mchar);			
		}
	}
}

public class PaintApp {	
	static Paint paint;
	public static void main(String[] args) throws NumberFormatException, InterruptedException {		
		Scanner scan = new Scanner(System.in);
		String command = new String();
		while(!command.equals("Q")) {
			System.out.print("enter command:");
			command = scan.nextLine();
			draw(command);
		}
		System.out.println("Program Exited!");
		scan.close();
	}
	
	private static void draw(String command) throws NumberFormatException, InterruptedException {
		char ch = command.charAt(0);
		String[] cmd;
		try {
			switch(ch) {
				case 'C' :
					cmd = command.split(" ");
					paint = new Paint(Integer.parseInt(cmd[1]),Integer.parseInt(cmd[2]));
					paint.render();
				break;
				case 'L' :
					cmd = command.split(" ");
					if(paint == null){
						System.err.println("Draw a canvas first");
						return;
					}
					paint.drawLine(Integer.parseInt(cmd[1]),Integer.parseInt(cmd[2]),Integer.parseInt(cmd[3]),Integer.parseInt(cmd[4]),'X');
					paint.render();
				break;
				case 'R' :
					cmd = command.split(" ");
					if(paint == null){
						System.err.println("Draw a canvas first");
						return;
					}
					paint.drawRectangle(Integer.parseInt(cmd[1]),Integer.parseInt(cmd[2]),Integer.parseInt(cmd[3]),Integer.parseInt(cmd[4]),'X');
					paint.render();
				break;
				case 'B' :
					cmd = command.split(" ");
					if(paint == null){
						System.err.println("Draw a canvas first");
						return;
					}
					paint.bucketFill(Integer.parseInt(cmd[1]),Integer.parseInt(cmd[2]),cmd[3].charAt(0));
					paint.render();
				break;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Invalid command. Try again!!\n");
			//e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error Occured\n");
			e.printStackTrace();
			System.exit(1);
		}
	}
}

