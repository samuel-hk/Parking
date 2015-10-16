import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class a1
{
	public static void main(String args[])
	{
		ParkingPermitKioskFrame frame = new ParkingPermitKioskFrame();
		frame.setTitle("York University Parking");
		frame.pack();
		frame.setVisible(true);
		
	}
	
}

class ParkingPermitKioskFrame extends JFrame implements ActionListener
{
	private JLabel studentNumberLabel;
	JTextField studentNumberInput;
	private JLabel PINLabel;
	private JTextField PINInput;
	private JButton loginButton;
	private static HashMap<String,HashMap> studentMap = new HashMap<String,HashMap>();
	JPanel p1;
	JPanel p2;
	JLabel parkingTitle;
	JLabel yorkLogoLabel;
	JLabel incorrectLogin;
	ImageIcon yorkLogo = new ImageIcon("images/title.jpg");
	ImageIcon login = new ImageIcon("images/LOGIN.png");
	
	private JPanel numKeyboardPanel;
	
	public ParkingPermitKioskFrame()
	{
		// input field properties
		int inputWidth = 200;
		int inputHeight = 50;
		
		// set up student input text field
		studentNumberInput = new JTextField(9);
		studentNumberInput.addActionListener(this);
		studentNumberInput.setPreferredSize(new Dimension(inputWidth, inputHeight));
		
		// limit student input to 4 digits
		final int STUDENTLIMIT= 9;
		studentNumberInput.setDocument(new PlainDocument(){
			@Override
			public void insertString(int offs, String str, AttributeSet a)
					throws BadLocationException {
				if(getLength() + str.length()<= STUDENTLIMIT)
					super.insertString(offs, str, a);
			}
		});
		
		// set up PIN input field
		PINInput = new JTextField(4);
		PINInput.addActionListener(this);
		PINInput.setPreferredSize(new Dimension(inputWidth, inputHeight));
		
		// limit PIN input to 4 digits
		final int PINLIMIT = 4;
		PINInput.setDocument(new PlainDocument(){
			@Override
			public void insertString(int offs, String str, AttributeSet a)
					throws BadLocationException {
				if(getLength() + str.length()<= PINLIMIT)
					super.insertString(offs, str, a);
			}
		});
		
		// set up signal labels
		int fontSize =30;
		studentNumberLabel = new JLabel("Student Number: ");
		PINLabel = new JLabel("PIN: ");
		studentNumberLabel.setFont(new Font("Arial", Font.BOLD, fontSize));
		PINLabel.setFont(new Font("Arial", Font.BOLD, fontSize));
		incorrectLogin = new JLabel("Student Number or PIN Incorrect");
		incorrectLogin.setVisible(false);
		
		//read login button image and set it as button
		loginButton = new JButton(login);
		
		
		loginButton.setBorder(BorderFactory.createEmptyBorder());
		
		  //add action listener to the loginButton button
		loginButton.addActionListener(this);
		
		JPanel logoPane = new JPanel();
		logoPane.setBackground(Color.white);
		logoPane.setLayout(new GridLayout(1,2));
		yorkLogoLabel = new JLabel(new ImageIcon(yorkLogo.getImage().getScaledInstance(980, 193, Image.SCALE_SMOOTH)));
		yorkLogoLabel.setSize(100, 100);
		logoPane.add(yorkLogoLabel);
		
		
		JPanel loginsub1 = new JPanel();
		loginsub1.setBackground(Color.white);
		loginsub1.setLayout(new FlowLayout());
		loginsub1.add(studentNumberLabel);
		loginsub1.add(studentNumberInput);
		
		JPanel loginsub2 = new JPanel();
		loginsub2.setBackground(Color.white);
		loginsub2.setLayout(new FlowLayout());
		loginsub2.add(PINLabel);
		loginsub2.add(PINInput);
		
		//put input field for student number and pin as a sub panel
		JPanel loginPane = new JPanel();
		loginPane.setBackground(Color.white);
		loginPane.setLayout(new GridLayout(2,1));
//		loginPane.add(loginsub1);
//		loginPane.add(loginsub2);
		
		JPanel inputPanel = new JPanel(new GridLayout(2, 4));

		inputPanel.add(new JLabel(""));
		
		inputPanel.add(studentNumberLabel);
		inputPanel.add(studentNumberInput);

		inputPanel.add(new JLabel(""));
		inputPanel.add(new JLabel(""));
		
		inputPanel.add(PINLabel);
		inputPanel.add(PINInput);
		
		inputPanel.add(new JLabel(""));
		
		inputPanel.setBackground(Color.white);
		loginPane.add(inputPanel);

		//
		
		//main panel for login page
		p1 = new JPanel();
		p1.setBackground(Color.white);
		p1.setPreferredSize(new Dimension(1200,700));
		p1.setMaximumSize(new Dimension(1200,700));
		p1.setLayout(new GridLayout(4,1));
		
		p1.add(logoPane);
		p1.add(loginPane);
		p1.add(incorrectLogin);
		p1.add(loginButton);
		
		this.setContentPane(p1);
		
		p2 = new JPanel();
		//p2.add(PINLabel);
		p2.setPreferredSize(new Dimension(1200,700));
		
		
		
	}

	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		// TODO Auto-generated method stub
		
		if(ae.getSource().equals(loginButton))
		{
			readStudentDatabase();
			if(studentNumberInDatabase(studentNumberInput.getText())&&studentNumberAndPINMatches(studentNumberInput.getText(),PINInput.getText()))
			{
				remove(p1);
				setContentPane(p2);
				this.pack();
			}
			else
			{
				int fontSize = 30;
				incorrectLogin.setVisible(true);
				incorrectLogin.setForeground(Color.RED);
				incorrectLogin.setBackground(Color.WHITE);
				incorrectLogin.setFont(new Font("Serif", Font.BOLD, fontSize));
				incorrectLogin.setHorizontalAlignment(JLabel.CENTER);
				
//				JOptionPane.showMessageDialog(null, PINInput.getText(), "TITLE", JOptionPane.PLAIN_MESSAGE);
			}
			
		}
		
		
	}
	
	
	public static HashMap<String,HashMap> readStudentDatabase()
	{
		File file = new File("students.txt");
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		DataInputStream dis = null;
		
		try
		{
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			dis = new DataInputStream(bis);
			
			String str;
			while(dis.available() != 0)
			{
				
				HashMap<String,String> informationMap = new HashMap<String,String>();
				str = dis.readLine();
				
				String[] line = str.split(",");
				informationMap.put("PIN", line[1].trim());
				informationMap.put("FamilyName",line[2].trim());
				informationMap.put("GivenName", line[3].trim());
				informationMap.put("status", line[4].trim());
				studentMap.put(line[0].trim(), informationMap);
			}
		}catch(IOException e)
		{
			System.out.println("File Error");
		}
		
		System.out.println(studentMap.toString());

		return studentMap;
		
	}
	
	public static boolean studentNumberInDatabase(String studentNumberInput)
	{
		Pattern pattern = Pattern.compile("^[0-9]{9}$");
		Matcher matcher = pattern.matcher(studentNumberInput);
		
		if(studentMap.containsKey(studentNumberInput.trim())&&matcher.matches())
		{
			System.out.println("studenNumberInDatabase: true");
			return true;
		}
		else
		{
			System.out.println("studenNumberInDatabase: false");
			return false;
		}
	}
	
	public static boolean studentNumberAndPINMatches(String studentNumberInput,String PINInput)
	{
		if(studentNumberInDatabase(studentNumberInput) && studentMap.get(studentNumberInput).get("PIN").equals(PINInput))
		{
			System.out.println("studentNumberAndPINMatches: true");
			return true;			
		}
		else
		{
			System.out.println("studentNumberAndPINMatches: false");
			return false;
		}
	}
}
