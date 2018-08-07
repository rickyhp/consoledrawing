Environment Setup:
	- In Windows 10 OS, the console does not support color. To enable color add below entry in registry
		HKEY_CURRENT_USER\Console\VirtualTerminalLevel = 1
		
Running the program:
	- To run the program, exeute below command
		java -jar ConsoleDrawing.jar

Limitations:		
	- Since this is developed in Window 10, Console color is based on ANSI/VT100 Terminal Control Escape http://www.termsys.demon.co.uk/vtansi.htm
	- Available ANSI/VT100 colors are [BLACK, RED, GREEN, YELLOW, BLUE, MAGENTA, CYAN, WHITE]

