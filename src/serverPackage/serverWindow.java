package serverPackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import java.net.*;
import java.io.*;


public class serverWindow implements ActionListener
{
	private int port;
	private Socket s;
	private ServerSocket ss;
	private Thread t1;
	private String lastLine;
	serverText st;
	
	private JFrame frame = new JFrame("Server");
	
	private Font font = new Font("Serif",Font.BOLD,18);
	
	private JPanel labelPanel = new JPanel();
	private JPanel serverPanel = new JPanel();
	private JPanel clientPanel = new JPanel();
	private JPanel buttonPanel = new JPanel();
	
	private JButton exit = new JButton("Exit");
	private JButton send = new JButton("Send Massage");
	
	private JLabel serverLabel = new JLabel("      Server");
	private JLabel clientLabel = new JLabel("      Client");
		
	private JTextArea serverTextArea = new JTextArea(15,18);
	private JTextArea clientTextArea = new JTextArea(15,18);
	
	private JScrollPane serverScrollPane = new JScrollPane(serverTextArea);
	private JScrollPane clientScrollPane = new JScrollPane(clientTextArea);
	
	public serverWindow(int port) 
	{
		this.port = port;
		
		try
		{
			ss = new ServerSocket(port);
			System.out.println("Server is Waiting");
			s = ss.accept();
			System.out.println("Server is connected");
			System.out.println();	
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}
	
	public serverWindow() 
	{
		
	}
	
	public void iniWindow()
	{
		BorderLayout bl = new BorderLayout();
		GridLayout gl = new GridLayout(1,2,60,10);
		
		labelPanel.setLayout(gl);
		labelPanel.add(clientLabel);
		labelPanel.add(serverLabel);
		
		serverScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		serverScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		serverTextArea.setBackground(Color.green);
		serverTextArea.setFont(font);
		serverTextArea.setEditable(true);
		serverTextArea.append("You : ");
		serverPanel.add(serverScrollPane);
		
		
		clientScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		clientScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		clientTextArea.setBackground(Color.yellow);
		clientTextArea.setFont(font);
		clientTextArea.setEditable(false);
		clientPanel.add(clientScrollPane);
				
		send.setFont(font);
		exit.setFont(font);
		buttonPanel.setLayout(gl);
		buttonPanel.add(exit);
		buttonPanel.add(send);
		
		frame.setLayout(bl);
		frame.add(labelPanel,bl.NORTH);
		frame.add(serverPanel,bl.EAST);
		frame.add(clientPanel,bl.WEST);
		frame.add(buttonPanel,bl.SOUTH);
		frame.setSize(650, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		exit.addActionListener(this);
		send.addActionListener(this);
		
		recieveData();
	}	
	
	public void recieveData()
	{
		try
		{
			String recieve;
			InputStreamReader is = new InputStreamReader(s.getInputStream());
			BufferedReader br = new BufferedReader(is);
			recieve = br.readLine();
			clientTextArea.append("client :  " + recieve + "\n");
		}
		catch(Exception ex)
		{
			System.out.println(ex.toString());
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		
		if(e.getSource() == send)
		{
			try
			{						
				int last = serverTextArea.getLineCount()-1;
				int start = serverTextArea.getLineStartOffset(last);
				int end = serverTextArea.getLineEndOffset(last);
				
				lastLine = serverTextArea.getText().substring(start+6, end);

			}
			catch(Exception ex)
			{
				System.out.println(ex.toString());
			}
			
			
			t1 = new Thread(new Runnable(){
				
				@Override
				public void run()
				{
					{
						try
						{
							OutputStreamWriter os = new OutputStreamWriter(s.getOutputStream());
							PrintWriter pw = new PrintWriter(os);
							pw.println(lastLine);
							pw.flush();
						}
						catch(Exception ex)
						{
							System.out.println(ex.toString());
						}
					}					
				}				
			});
			
			t1.start();
			
			recieveData();
			
			serverTextArea.append("\nYou : ");
		}
		else if(e.getSource() == exit)
		{
			frame.dispose();
		}		
	}
}
