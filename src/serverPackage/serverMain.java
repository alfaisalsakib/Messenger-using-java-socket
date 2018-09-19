package serverPackage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class serverMain {

	public static void main(String[] args) 
	{
		//Scanner input = new Scanner(System.in);
		
		int port = 9910;
	
		serverWindow sw = new serverWindow(port);
		sw.iniWindow();
		
	}

}
