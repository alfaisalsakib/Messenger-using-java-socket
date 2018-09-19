package clientPackage;

import javax.swing.*;
import javax.swing.text.BadLocationException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*;
import java.io.*;
import java.util.Scanner;


public class clientWindow implements ActionListener
{
	private String ip;
	private int port;
	private  Socket s;
	private Thread t1;
	private String lastLine;
	clientText ct;
	Scanner input = new Scanner(System.in);
	private JFrame frame = new JFrame("Client");
	
	private Font font = new Font("Serif",Font.BOLD,18);
	
	private JPanel serverPanel = new JPanel();
	private JPanel clientPanel = new JPanel();
	private JPanel buttonPanel = new JPanel();
	private JPanel labelPanel = new JPanel();
	
	private JButton exit = new JButton("Exit");
	private JButton send = new JButton("Send Massage");
	
	private JLabel serverLabel = new JLabel("      Server");
	private JLabel clientLabel = new JLabel("      Client");
		
	private JTextArea serverTextArea = new JTextArea(15,18);
	private JTextArea clientTextArea = new JTextArea(15,18);
	
	private JScrollPane serverScrollPane = new JScrollPane(serverTextArea);
	private JScrollPane clientScrollPane = new JScrollPane(clientTextArea);
	
	public clientWindow(String ip,int port) 
	{
		this.ip = ip;
		this.port = port;
		
		try
		{
			s = new Socket(ip,port);
			System.out.println(ip);
		}
		catch(Exception e)
		{
			//System.out.println(e.toString());
		}
	}
	
	public clientWindow() 
	{
	
	}
	
	public void iniWindow()
	{
		BorderLayout bl = new BorderLayout();
		GridLayout gl = new GridLayout(1,2,60,10);
		
		labelPanel.setLayout(gl);
		labelPanel.add(serverLabel);
		labelPanel.add(clientLabel);
		
		serverScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		serverScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		serverTextArea.setBackground(Color.green);
		serverTextArea.setFont(font);
		serverTextArea.setEditable(false);
		serverPanel.add(serverScrollPane);
		
		
		clientScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		clientScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		clientTextArea.setBackground(Color.yellow);
		clientTextArea.setFont(font);
		clientTextArea.setEditable(true);
		clientTextArea.append("You : ");
		clientPanel.add(clientScrollPane);
		
		
		send.setFont(font);
		exit.setFont(font);
		buttonPanel.setLayout(gl);
		buttonPanel.add(exit);
		buttonPanel.add(send);
		
		frame.setLayout(bl);
		frame.add(labelPanel,bl.NORTH);
		frame.add(serverPanel,bl.WEST);
		frame.add(clientPanel,bl.EAST);
		frame.add(buttonPanel,bl.SOUTH);
		frame.setSize(650, 500);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);		
	
		exit.addActionListener(this);
		send.addActionListener(this);			
	}
		
	public void recieveData()
	{
		try
		{
			String recieve;
			InputStreamReader is = new InputStreamReader(s.getInputStream());
			BufferedReader br = new BufferedReader(is);
			recieve = br.readLine();
			serverTextArea.append("server :  " + recieve + "\n");
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}
		
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == send)
		{		
			try 
			{
				int last = clientTextArea.getLineCount()-1;
				int start = clientTextArea.getLineStartOffset(last);
				int end = clientTextArea.getLineEndOffset(last);
				
				lastLine = clientTextArea.getText().substring(start+6, end);
			} 
			catch (BadLocationException e1) 
			{
				e1.printStackTrace();
			}
			
			int i=1;
			String key = "stop";
			try
			{
				t1 = new Thread(new Runnable(){

					@Override
					public void run() 
					{
						while(i==1)
						{
							try
							{
								OutputStreamWriter os = new OutputStreamWriter(s.getOutputStream());
								PrintWriter pw = new PrintWriter(os);
								System.out.print("Enter Message : ");
								String msg = input.nextLine();
								
								if(msg.equals(key))
								{
									pw.println("bye");
									pw.flush();
									s.close();
									break;
								}
								else
								{
									pw.println(msg);
									pw.flush();
								}							
											
								
								
							}
							catch(Exception ex)
							{
								System.out.println(ex.toString());
							}
						}
					}				
				});
				
				Thread t2 = new Thread(new Runnable(){

					@Override
					public void run() 
					{
						while(i==1)
						{
							try
							{
								String receive;
								InputStreamReader is = new InputStreamReader(s.getInputStream());
								BufferedReader br = new BufferedReader(is);
								receive = br.readLine();
								
								if(receive.equals("bye"))
								{
									System.out.println("server :  " + receive);
									s.close();
									break;
								}
								System.out.println("server :  " + receive);					
																	
							}
							catch(Exception ex)
							{
								System.out.println(ex.toString());
							}
						}
					}				
				});
				
				
				t2.start();
				t1.start();
			}
			catch(Exception exs)
			{
				
			}
			
			
//			t1 = new Thread(new Runnable(){
//
//				@Override
//				public void run() 
//				{
//					{
//						try
//						{
//							OutputStreamWriter os = new OutputStreamWriter(s.getOutputStream());
//							PrintWriter pw = new PrintWriter(os);
//							pw.println(lastLine);
//							pw.flush();
//											
//						}
//						catch(Exception ex)
//						{
//							System.out.println(ex.toString());
//						}
//					}
//				}				
//			});
//			
//			t1.start();
			
			recieveData();
			clientTextArea.append("\nYou : ");
		}
		
		else if(e.getSource() == exit)
		{
			frame.dispose();
		}
		
	}
}
