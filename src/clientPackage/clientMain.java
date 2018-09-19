package clientPackage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class clientMain {

	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		
		String ip = "192.168.0.13";
		int port = 9110;

		clientWindow cw = new clientWindow(ip,port);
		cw.iniWindow();
	}
}
