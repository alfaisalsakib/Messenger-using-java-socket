package clientPackage;

import java.net.*;

import javax.swing.JOptionPane;

import java.io.*;

public class clientText 
{
	private String clientText;
	private String serverText;
	private Socket s;
	private String ip;
	private int port;
	private Thread t1;
	
	public clientText() 
	{
		
	}
	
	public clientText(String ip,int port) 
	{
		this.ip = ip;
		this.port = port;
	}
	
	/*public void connectdiff()
	{
		try
		{
			s = new Socket(ip,port);
			
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			dos.writeUTF("hello");
			dos.flush();
						
			DataInputStream dis = new DataInputStream(s.getInputStream());
			String str = (String)dis.readUTF();
			JOptionPane.showMessageDialog(null, str);			
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
			s = new Socket(ip,port);
			/*
			OutputStreamWriter os = new OutputStreamWriter(s.getOutputStream());
			PrintWriter pw = new PrintWriter(os);
			pw.println("hello");
			pw.flush();
						
			String recieve;
			InputStreamReader is = new InputStreamReader(s.getInputStream());
			BufferedReader br = new BufferedReader(is);
			recieve = br.readLine();
			JOptionPane.showMessageDialog(null, "server :  " + recieve);	
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
						OutputStreamWriter os = new OutputStreamWriter(s.getOutputStream());
						PrintWriter pw = new PrintWriter(os);
						pw.println("hello");
						pw.flush();
									
						String recieve;
						InputStreamReader is = new InputStreamReader(s.getInputStream());
						BufferedReader br = new BufferedReader(is);
						recieve = br.readLine();
						//System.out.println("server :  " + recieve);
						JOptionPane.showMessageDialog(null, "server :  " + recieve);	
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
