package com.zen.exercise.starhub;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

import com.zen.exercise.starhub.ConsoleColors.ConsoleColorEnum;
public class ConsoleDrawing{
	private Console console;
	private static final char ESC_CODE = 0x1B;
	private int latestheight;
	private List<String> history;
	
	public static void main(String[] args) {

        ConsoleDrawing consoleDrawing = new ConsoleDrawing();
        try {
			consoleDrawing.startDrawing();
		} catch (InterruptedException ie) {
			consoleDrawing.getConsole().printf("Interrupted exception has occurred: %s", ie.getMessage());
		} catch (Exception e){
			consoleDrawing.getConsole().printf("Unhandled exception has occurred: %s", e.getMessage());
		}
	}
	
	public void startDrawing() throws InterruptedException{
        label_while:
		while(true){
			getConsole().printf(String.format("%c[%d;%df\033[2K",ESC_CODE,9,0));
			String command = console.readLine(ConsoleColors.GREEN + "Enter drawing command: " + ConsoleColors.RESET);
			if(validCommand(command)){
				this.getHistory().add(command);
				String[] canvasArr = command.trim().split(" ");
				char c = canvasArr[0].toUpperCase().charAt(0);
				switch(c){
				case 'C':
					drawCanvas(console, canvasArr);				
					break;
				case 'L':
					drawLine(console, canvasArr);				
					break;		
				case 'R':
					drawRectangle(console, canvasArr);				
					break;
				case 'B':
					bucketFill(console, canvasArr);		
				case 'E':
					eraseDrawing(console, canvasArr);					
					break;					
				case 'Q':
					break label_while;					
				}
				printHistory();
			}
			
		}
		getConsole().printf(String.format("%c[%d;%df",ESC_CODE, getLatestheight() + 12,0));		
	}
	
	private boolean validCommand(String command){
		boolean valid = false;
		if(command!=null && command.length()>0){
			valid = true;
		}
		return valid;
	}
	
	private void drawCanvas(Console console, String[] canvasArr) throws InterruptedException{
		int width = 0;
		int height = 0;
		final int DELAY = 2;
		if(canvasArr.length==3){
			getConsole().printf(String.format("%c[%d;%df\033[J",ESC_CODE,10,0));
			width = Integer.parseInt(canvasArr[1]);
			height = Integer.parseInt(canvasArr[2]);
			for(int w=0;w<width;w++){
				console.printf(String.format("%c[%d;%dfx",ESC_CODE,10,w));
				Thread.sleep(DELAY);
			}		
			for(int h=10;h<height+11;h++){
				console.printf(String.format("%c[%d;%dfx",ESC_CODE,h,width));
				Thread.sleep(DELAY);
			}
			for(int w=width;w>0;w--){
				console.printf(String.format("%c[%d;%dfx",ESC_CODE,height+10,w));
				Thread.sleep(DELAY);
			}					
			for(int h=height+9;h>9;h--){
				console.printf(String.format("%c[%d;%dfx",ESC_CODE,h,0));
				Thread.sleep(DELAY);
			}			
		}		
		this.setLatestheight(height);
	}
	
	private void drawLine(Console console, String[] canvasArr) throws InterruptedException{
		int x1, y1, x2, y2 = 0;
		final int DELAY = 2;
		if(canvasArr.length==5){
			x1 = Integer.parseInt(canvasArr[1]);
			y1 = Integer.parseInt(canvasArr[2]);
			x2 = Integer.parseInt(canvasArr[3]);
			y2 = Integer.parseInt(canvasArr[4]);
			if(x1==x2){
				for(int y=y1+10;y<y2+11;y++){
					console.printf(String.format("%c[%d;%dfx",ESC_CODE,y,x2));
					Thread.sleep(DELAY);
				}				
				//draw vertical line y1 --> y2
			}else{
				//draw horizontal line x1 --> x2
				for(int x=x1;x<x2;x++){
					console.printf(String.format("%c[%d;%dfx",ESC_CODE,y1+ 10,x));
					Thread.sleep(DELAY);
				}						
			}
		
		}		
	}
	
	private void drawRectangle(Console console, String[] canvasArr) throws InterruptedException{
		int x1, y1, x2, y2 = 0;
		final int DELAY = 2;
		if(canvasArr.length==5){
			x1 = Integer.parseInt(canvasArr[1]);
			y1 = Integer.parseInt(canvasArr[2]);
			x2 = Integer.parseInt(canvasArr[3]);
			y2 = Integer.parseInt(canvasArr[4]);
			for(int x=x1;x<x2;x++){
				console.printf(String.format("%c[%d;%dfx",ESC_CODE,y1 + 11,x));
				Thread.sleep(DELAY);
			}		
			for(int y=y1+11;y<y2+12;y++){
				console.printf(String.format("%c[%d;%dfx",ESC_CODE,y,x2));
				Thread.sleep(DELAY);
			}
			for(int x=x2;x>x1;x--){
				console.printf(String.format("%c[%d;%dfx",ESC_CODE,y2+11,x));
				Thread.sleep(DELAY);
			}					
			for(int y=y2+11;y>y1+11;y--){
				console.printf(String.format("%c[%d;%dfx",ESC_CODE,y,x1));
				Thread.sleep(DELAY);
			}	
		}
		if(this.getLatestheight() < (y2 + 12)){
			this.setLatestheight(y2 + 12);
		}
	}	
	
	private void bucketFill(Console console, String[] canvasArr) throws InterruptedException{
		int x1, y1, x2, y2 = 0;
		if(canvasArr.length==6){
			x1 = Integer.parseInt(canvasArr[1]);
			y1 = Integer.parseInt(canvasArr[2]);
			x2 = Integer.parseInt(canvasArr[3]);
			y2 = Integer.parseInt(canvasArr[4]);
			
			if(ConsoleColorEnum.containsColor(canvasArr[5].toUpperCase())){
				console.printf(ConsoleColorEnum.valueOf(canvasArr[5].toUpperCase()).getColor());
				for(int x=x1;x<x2+1;x++){
					for(int y=y1+10;y<y2+12;y++){
						console.printf(String.format("%c[%d;%df ",ESC_CODE,y,x));
					}
				}			
				console.printf(ConsoleColors.RESET);
			}
		}
	}
	
	private void eraseDrawing(Console console, String[] canvasArr) throws InterruptedException{
		int x1, y1, x2, y2 = 0;
		if(canvasArr.length==5){
			x1 = Integer.parseInt(canvasArr[1]);
			y1 = Integer.parseInt(canvasArr[2]);
			x2 = Integer.parseInt(canvasArr[3]);
			y2 = Integer.parseInt(canvasArr[4]);
			console.printf(ConsoleColors.RESET);
			for(int x=x1;x<x2+1;x++){
				for(int y=y1+10;y<y2+12;y++){
					console.printf(String.format("%c[%d;%df ",ESC_CODE,y,x));
				}
			}			
		}
	}	
	
	public ConsoleDrawing(){
		this.init();
	}
	
	private void init(){
        this.setConsole(System.console());
        if (this.getConsole() == null) {
            System.err.println("No console.");
            System.exit(1);
        }		
        printInfo(this.getConsole());
        setHistory(new ArrayList<String>());		
	}
	
	private void printHistory(){
		getConsole().printf(String.format("%c[%d;%df",ESC_CODE,2,143));
		getConsole().printf(ConsoleColors.YELLOW_UNDERLINED + "History:" + ConsoleColors.RESET);
		int count = 1;
        for(int i= this.getHistory().size()-1; i >= 0; i-- ){
        	StringBuilder sb = new StringBuilder(ConsoleColors.MAGENTA_BOLD_BRIGHT);
        	sb.append("["+ count + "] " + this.getHistory().get(i) + repeatStr(" ", 25 - this.getHistory().get(i).length()));
        	getConsole().printf(String.format("%c[%d;%df%s",ESC_CODE,2 + count,143, sb.toString()));
        	count++;
        	if(count>5) break;
        }
        getConsole().printf(ConsoleColors.RESET);
	}
	
	private void printInfo(Console console){
        StringBuilder sb = new StringBuilder();
        sb.append(ConsoleColors.RESET)
        .append(ConsoleColors.YELLOW_BOLD + "Console Drawing Program Exercise\n" + ConsoleColors.RESET)
        .append(ConsoleColors.YELLOW_UNDERLINED + "Available commands:\n" + ConsoleColors.RESET)
        .append(ConsoleColors.YELLOW)
		.append("  C w h            Create a canvas of width w and height h.\n")
		.append("  L x1 y1 x2 y2    Create a line from (x1,y1) to (x2,y2).\n")
		.append("  R x1 y1 x2 y2    Create a rectangle, whose upper left corner is (x1,y1) and lower right corner is (x2,y2).\n")
		.append("  B x1 y1 x2 y2 c  Fill the entire area connected to (x1,y1) and (x2,y2) with colour [BLACK, RED, GREEN, YELLOW, BLUE, MAGENTA, CYAN, WHITE].\n")
		.append("  E x1 y1 x2 y2 c  Erase drawing area connected to (x1,y1) and (x2,y2) with colour [BLACK, RED, GREEN, YELLOW, BLUE, MAGENTA, CYAN, WHITE].\n")
		.append("  Q                 Quit\n")
        .append(ConsoleColors.RESET);
        console.printf(String.format("\033[2J"));
		console.printf(sb.toString());
	}
	
	public Console getConsole() {
		return console;
	}
	public void setConsole(Console console) {
		this.console = console;
	}
	public int getLatestheight() {
		return latestheight;
	}
	public void setLatestheight(int latestheight) {
		this.latestheight = latestheight;
	}
	public List<String> getHistory() {
		return history;
	}

	public void setHistory(List<String> history) {
		this.history = history;
	}
	
	private String repeatStr(String s, int n){
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<n;i++){
			sb.append(s);
		}
		return sb.toString();
	}
}
