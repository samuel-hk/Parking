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
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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
import javax.swing.JTabbedPane;
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
		ParkingPermitKioskFrame.readStudentDatabase();
		
		ParkingPermitKioskFrame frame = new ParkingPermitKioskFrame();
		frame.setTitle("York University Parking");
		frame.pack();
		frame.setVisible(true);
		
		
	}
	
}

class ParkingPermitKioskFrame extends JFrame implements ActionListener, FocusListener
{
	private JLabel studentNumberLabel;
	JTextField studentNumberInput;
	private JLabel PINLabel;
	private JTextField PINInput;
	private JButton loginButton;
	public static HashMap<String,HashMap> studentMap = new HashMap<String,HashMap>();
	JPanel p1;
	JTabbedPane p2;
	JLabel parkingTitle;
	JLabel yorkLogoLabel;
	JLabel incorrectLogin;
	
	
	//---------------------Image Source---------------------
	ImageIcon yorkLogo = new ImageIcon("images/title.jpg");
	ImageIcon login = new ImageIcon("images/LOGIN.png");
	ImageIcon next = new ImageIcon("images/NEXT.jpg");
	ImageIcon previous = new ImageIcon("images/PREVIOUS.jpg");
	//------------------------------------------------------
	
	
	
	
	
	//ImageIcon yorkLogoLabelEmailPage = new ImageIcon("images/title.jpg");
	JLabel yorkLogoLabelEmailPage;
	JLabel displayName;
	String displayNameString;
	String studentNumber;
	
	
	//---------------------Subscription---------------------
	
	JPanel subscriptionPane;
	JLabel emailLabel;
	JTextField emailInput;
	JButton nextOnSubscription;
	JPanel emailInputPanel;
	JPanel buttonPanelOnSubscription;
			
	//------------------------------------------------------
	
	//---------------------Vehicle Information---------------------
	
	JPanel vehiclePane;
	
	//-------------------------------------------------------------
	
	// keyboard panels
	private JPanel numKeyboardPanel;
	private JPanel letterKeyboard;
	
	// to save the current focus input field
	private JTextField focusText;
	private Map<JTextField, Integer> focusTextLimit;
	
	private Map<JButton, String> allKeyboardButtonMap;
	
	public ParkingPermitKioskFrame()
	{
		// input field properties
		int inputWidth = 200;
		int inputHeight = 50;
		
		// set up student input text field
		studentNumberInput = new JTextField(9);
		studentNumberInput.addActionListener(this);
		studentNumberInput.setPreferredSize(new Dimension(inputWidth, inputHeight));
		studentNumberInput.addFocusListener(this);
		
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
		PINInput.addFocusListener(this);
		
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
		
		// set up limit of each text field
		focusTextLimit = new HashMap<>();
		focusTextLimit.put(PINInput, PINLIMIT);
		focusTextLimit.put(studentNumberInput, STUDENTLIMIT);
		
		
		//---------------------Login---------------------		
		// set up signal labels
		int fontSize =30;
		studentNumberLabel = new JLabel("Student Number: ");
		PINLabel = new JLabel("PIN: ");
		studentNumberLabel.setFont(new Font("Arial", Font.BOLD, fontSize));
		PINLabel.setFont(new Font("Arial", Font.BOLD, fontSize));
		incorrectLogin = new JLabel("Student Number or PIN Incorrect");
		incorrectLogin.setVisible(false);
		
		// read login button image and set it as button
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

		inputPanel.add(new JLabel("")); // placeholder to position other elements
		inputPanel.add(studentNumberLabel);
		inputPanel.add(studentNumberInput);
		inputPanel.add(new JLabel("")); // placeholder to position other elements
		inputPanel.add(new JLabel("")); // placeholder to position other elements
		inputPanel.add(PINLabel);
		inputPanel.add(PINInput);
		inputPanel.add(new JLabel("")); // placeholder to position other elements
		inputPanel.setBackground(Color.white);
		loginPane.add(inputPanel);
		
		p1 = new JPanel();
		p1.setBackground(Color.white);
		p1.setPreferredSize(new Dimension(1200,700));
		p1.setMaximumSize(new Dimension(1200,700));
		p1.setLayout(new GridLayout(5,1));
		
		setupNumKeyboard();
		
		p1.add(logoPane);
		p1.add(loginPane);
		p1.add(incorrectLogin);
		p1.add(loginButton);
		p1.add(numKeyboardPanel);
		
		this.setContentPane(p1);
		//------------------------------------------------------
		
		//---------------------Subscription---------------------
		yorkLogoLabelEmailPage = new JLabel(new ImageIcon(yorkLogo.getImage().getScaledInstance(980, 193, Image.SCALE_SMOOTH)));
		subscriptionPane = new JPanel();
		subscriptionPane.setBackground(Color.white);
		subscriptionPane.setLayout(new GridLayout(5,1));
		subscriptionPane.setPreferredSize(new Dimension(1200,700));
		
		displayName = new JLabel(displayNameString);
		
		subscriptionPane.add(yorkLogoLabelEmailPage);
		subscriptionPane.add(displayName);
		
		emailInputPanel = new JPanel();
		emailInputPanel.setBackground(Color.white);
		emailInputPanel.setLayout(new GridLayout(2,1));
		emailLabel = new JLabel("Would you like to receive parking news at york?\nPlease enter your Email(Optional): ");
		emailInput = new JTextField(25);
		emailInput.addActionListener(this);
		emailInputPanel.add(emailLabel);
		emailInputPanel.add(emailInput);
		
		buttonPanelOnSubscription = new JPanel();
		buttonPanelOnSubscription.setBackground(Color.white);
		next.getImage().getScaledInstance(280, 116, Image.SCALE_SMOOTH);
		nextOnSubscription = new JButton(next);
		nextOnSubscription.addActionListener(this);
		nextOnSubscription.setBorder(BorderFactory.createEmptyBorder());
		buttonPanelOnSubscription.add(nextOnSubscription);


		
		subscriptionPane.add(emailInputPanel);

		// setup letter keyboard for subscription pane
		setupLetterKeyboard();
		subscriptionPane.add(letterKeyboard);
		subscriptionPane.add(buttonPanelOnSubscription);
		
		
		
		//------------------------------------------------------
		
		
		//---------------------Vehicle Information---------------------
		
		vehiclePane = new JPanel();
		vehiclePane.setPreferredSize(new Dimension(1200,700));
		vehiclePane.setBackground(Color.white);
		
		
		//-------------------------------------------------------------

		
		
		
		
		p2 = new JTabbedPane();
		//p2.add(PINLabel);
		p2.setPreferredSize(new Dimension(1200,700));
		p2.addTab("Subscribe", subscriptionPane);
		p2.addTab("Vehicle Information",vehiclePane);
		
		
		
		
		
	} // end constructor
	
	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		// TODO Auto-generated method stub
		
		// number keyboard press
		String str = allKeyboardButtonMap.get(ae.getSource());
		if ( str != null)
		{
//			JOptionPane.showMessageDialog(null, allKeyboardButtonMap.get(ae.getSource()), "TITLE", JOptionPane.PLAIN_MESSAGE);
			String focusTextValue = focusText.getText();
			int focusTextLength = focusTextValue.length();
			
			// Backspace button
			if (str.equals("Bk"))
			{
				// only backspace when there is text
				if (focusTextLength > 0)
				{
					String text = focusTextValue.substring(0, focusTextLength - 1);
					focusText.setText(text);					
				} // end if, backspace only when there is text
				
			} // end if, backspace button
			
			// add digit
			else if (focusText != null && focusTextLength < focusTextLimit.get(focusText) )
				focusText.setText(focusTextValue + str);
		}
		// login button press
		else if(ae.getSource().equals(loginButton))
		{
			readStudentDatabase();
			if(studentNumberInDatabase(studentNumberInput.getText())&&studentNumberAndPINMatches(studentNumberInput.getText(),PINInput.getText()))
			{
				studentNumber = studentNumberInput.getText();
				remove(p1);
				setContentPane(p2);
				this.pack();
				
				displayNameString = "Welcome "+studentMap.get(studentNumber).get("GivenName")+" "+studentMap.get(studentNumber).get("FamilyName");
				displayName.setText(displayNameString);
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
		else if(ae.getSource().equals(nextOnSubscription))
		{
			if(emailValid(emailInput.getText()))
			{
				remove(subscriptionPane);
				setContentPane(vehiclePane);

				this.pack();
				
			}
			else
			{
				
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
	
	private void setupNumKeyboard()
	{
		
		// define properties of keyboard 
		final int ROW = 4;
		final int COL = 3;
		
		// define keyboard data
		String[] num = {"1", "2", "3", "4", "5", "6","7", "8", "9", "BOX", "0", "Bk"};

		// define keyboard
		numKeyboardPanel = new JPanel(new GridLayout(ROW, COL));
		
		// create map to hold keyboad data
		allKeyboardButtonMap = new HashMap<>();

		for (int i = 0 ; i < num.length; i++)
		{
			JButton b = new JButton(num[i]);
			numKeyboardPanel.add(b);
			
			// create empty box to position 90 in middle
			if (num[i].equals("BOX"))
			{
				b.setEnabled(false);
				b.setText("");
			} // end if create empty box
			
			b.addActionListener(this);
			
			
			allKeyboardButtonMap.put(b, num[i]);
		} // end for add keys into keyboard
		
	} // end method setupNumKeyboard
	
	private void setupLetterKeyboard()
	{
		// define keyboard properties
		int rowNum = 5;
		
		// keyboard data
		String firstRow[] = {"~","1","2","3","4","5","6","7","8","9","0","-","+","BackSpace"};
		String secondRow[] = {"Tab","Q","W","E","R","T","Y","U","I","O","P","[","]","\\"};
		String thirdRow[] = {"A","S","D","F","G","H","J","K","L",":","\"","Enter"};
		String fourthRow[] = {"Shift","Z","X","C","V","B","N","M",",",".","?"};
		String fifthRow[]={" " ,"<" ,"\\/",">" };
		
		// main letter keyboard panel
		letterKeyboard = new JPanel(new GridLayout(rowNum, 1));

		// subpanel for letter keyboard for the last row
		JPanel letterKeyboardPanel1 = new JPanel(new GridLayout(1, firstRow.length));
		JPanel letterKeyboardPanel2 = new JPanel(new GridLayout(1, secondRow.length));
		JPanel letterKeyboardPanel3 = new JPanel(new GridLayout(1, thirdRow.length));
		JPanel letterKeyboardPanel4 = new JPanel(new GridLayout(1, fourthRow.length));
		JPanel letterKeyboardPanel5 = new JPanel(new GridLayout(1, fifthRow.length));
		
		// add subpanels to main letter panel
		letterKeyboard.add(letterKeyboardPanel1);
		letterKeyboard.add(letterKeyboardPanel2);
		letterKeyboard.add(letterKeyboardPanel3);
		letterKeyboard.add(letterKeyboardPanel4);
		letterKeyboard.add(letterKeyboardPanel5);
		
		for (int i = 0; i < firstRow.length; i++)
		{
			JButton button = new JButton(firstRow[i]);
			letterKeyboardPanel1.add(button);
			allKeyboardButtonMap.put(button, firstRow[i]);
			button.addActionListener(this);
		} // end for add first row
		
		// add second row keyboard
		for (int i = 0; i < secondRow.length; i++)
		{
			JButton b = new JButton(secondRow[i]);
			letterKeyboardPanel2.add(b);
			allKeyboardButtonMap.put(b, secondRow[i]);
			b.addActionListener(this);
		} // end for add second row
		
		// add third row keyboard
		for (int i = 0; i < thirdRow.length; i++)
		{
			JButton b = new JButton(thirdRow[i]);
			letterKeyboardPanel3.add(b);
			allKeyboardButtonMap.put(b, thirdRow[i]);
			b.addActionListener(this);
		} // end for add third row
		
		// add forth row keyboard
		for (int i = 0; i < fourthRow.length; i++)
		{
			JButton b = new JButton(fourthRow[i]);
			letterKeyboardPanel4.add(b);
			allKeyboardButtonMap.put(b, fourthRow[i]);
			b.addActionListener(this);
		} // end for add 4th row
		
		// add 5th row keyboard
		for (int i = 0; i < fifthRow.length; i++)
		{
			JButton b = new JButton(fifthRow[i]);
			letterKeyboardPanel5.add(b);
			allKeyboardButtonMap.put(b, fifthRow[i]);
			b.addActionListener(this);
		} // end for add 4th row
		
		
	} // end method setupLetterKeyboard
	
	public static boolean emailValid(String emailInput)
	{
		Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		Matcher matcher = pattern.matcher(emailInput.trim());
		if(emailInput.isEmpty())
		{
			System.out.println("Email Empty: true");
			return true;
		}
		else if(matcher.matches())
		{
			System.out.println("Email maches: true");
			return true;
		}
		else
		{
			System.out.println("Email Input Error");
			return false;
		}
	}



	@Override
	public void focusGained(FocusEvent e) 
	{
		// TODO Auto-generated method stub
		focusText = (JTextField) e.getSource();
	} // end method focusGained

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}
}
