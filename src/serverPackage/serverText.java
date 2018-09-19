package serverPackage;

import java.net.*;

import javax.swing.JOptionPane;

import java.io.*;
import java.lang.*;

public class serverText
{
	private String clientText;
	private String serverText;
	private Socket s;
	private ServerSocket ss;
	private int port;
	private Thread t1;
	
	public serverText()
	{
		
	}
	public serverText(int port)
	{
		this.port = port;
	}
	
	/*public void connectdiff()
	{
		try
		{
			ServerSocket ss = new ServerSocket(port);
			System.out.println("Server is Waiting");
			Socket s = ss.accept();
			System.out.println("Server is connected");
			System.out.println();
			
			DataInputStream dis = new DataInputStream(s.getInputStream());
			String str = (String)dis.readUTF();
			System.out.println(str);
			
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			dos.writeUTF(":hi");
			dos.flush();
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}*/
	
	public void connect()
	{
		try
		{
			ss = new ServerSocket(port);
			System.out.println("Server is Waiting");
			s = ss.accept();
			System.out.println("Server is connected");
			System.out.println();
			
			/*
			InputStreamReader is = new InputStreamReader(s.getInputStream());
			BufferedReader br = new BufferedReader(is);
			System.out.println("client : " + br.readLine());
			
			OutputStreamWriter os = new OutputStreamWriter(s.getOutputStream());
			PrintWriter pw = new PrintWriter(os);			
			pw.println("hi");
			pw.flush();
			*/							
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
		//communicate();
		
	}
	
	public void communicate()
	{
		t1 = new Thread(new Runnable(){
			
			@Override
			public void run()
			{
				//for(int i=1;i<=3;i++)
				{
					try
					{
						
																		
						String recieve;
						InputStreamReader is = new InputStreamReader(s.getInputStream());
						BufferedReader br = new BufferedReader(is);
						recieve = br.readLine();
						System.out.println("client :  " + recieve);
						//JOptionPane.showMessageDialog(null, "server :  " + recieve);
						
						OutputStreamWriter os = new OutputStreamWriter(s.getOutputStream());
						PrintWriter pw = new PrintWriter(os);
						pw.println("hi");
						pw.flush();
						Thread.sleep(3000);
					}
					catch(Exception ex)
					{
						System.out.println(ex.toString());
					}
				}
				
			}				
		});
		
		t1.start();
	}

	public String getClientText() {
		return clientText;
	}

	public void setClientText(String clientText) {
		this.clientText = clientText;
	}

	public String getServerText() {
		return serverText;
	}

	public void setServerText(String serverText) {
		this.serverText = serverText;
	}
}
