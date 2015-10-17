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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.sun.org.apache.xml.internal.security.keys.content.KeyValue;

public class a1
{
	public static void main(String args[])
	{
		ParkingPermitKioskFrame.readStudentDatabase();
		ParkingPermitKioskFrame.readCompanyDatabase();

		ParkingPermitKioskFrame frame = new ParkingPermitKioskFrame();
		frame.setTitle("York University Parking");
		frame.pack();
		frame.setVisible(true);


	}

}

class ParkingPermitKioskFrame extends JFrame implements ActionListener, FocusListener, ChangeListener
{
	// fields
	
	private JLabel studentNumberLabel;
	JTextField studentNumberInput;
	private JLabel PINLabel;
	private JPasswordField PINInput;
	private JButton loginButton;
	public static HashMap<String,HashMap> studentMap = new HashMap<String,HashMap>();
	public static List<String> companyDatabase = new ArrayList<String>();
	JPanel p1;
	JTabbedPane p2;
	JLabel parkingTitle;
	JLabel yorkLogoLabel;
	JLabel incorrectLogin;


	//---------------------Image Source---------------------
	ImageIcon icon = new ImageIcon("images/car-tab-icon.png");
	ImageIcon yorkLogo = new ImageIcon("images/title.jpg");
	ImageIcon login = new ImageIcon("images/LOGIN.png");
	ImageIcon next = new ImageIcon("images/NEXT.jpg");
	ImageIcon previous = new ImageIcon("images/PREVIOUS.jpg");
	ImageIcon submitImage = new ImageIcon("images/SUBMIT.jpg");
	ImageIcon printImage = new ImageIcon("images/PRINT.jpg");
	//------------------------------------------------------





	//ImageIcon yorkLogoLabelEmailPage = new ImageIcon("images/title.jpg");
	JLabel yorkLogoLabelEmailPage;
	JLabel displayName;
	String displayNameString;
	String studentNumber;


	//---------------------Subscription---------------------

	JPanel subscriptionPane;
	JPanel subscriptionKeyboardPanel;
	JLabel emailLabel;
	JTextField emailInput;
	JButton nextOnSubscription;
	JPanel emailInputPanel;
	JPanel buttonPanelOnSubscription;
	JLabel incorrectEmailFormat;

	//------------------------------------------------------

	//---------------------Vehicle Information---------------------

	
	JPanel vehiclePane;
	JPanel vehicleKeyboardPane;

	JLabel vehicleMakeLabel;
	JTextField vehicleMakeInput;
	JLabel vehicleModelLabel;
	JTextField vehicleModelInput;
	JPanel vehicleMakePane;
	JPanel vehicleModelPane;
	JPanel plateNumberPane;
	JLabel plateNumberLabel;
	JTextField plateNumberInput;
	JLabel vehicleTitle;
	JButton nextOnVehicle;
	JButton previousOnVehicle;
	JPanel buttonPaneOnVehicle;
	JLabel displayNameInVehicle;
	
	JLabel errorMessageOnVehicle;
	
	//-------------------------------------------------------------
	
	
	//---------------------Insurance Information---------------------
	JPanel insurancePane;
	JPanel companyInfoPane;
	JPanel policyNumberPane;
	JPanel buttonPaneOnInsurance;
	JPanel insuranceKeyboardPanel;
	
	JLabel insuranceTitle;
	JLabel displayNameOnInsurance;
	
	JLabel insuranceCompanyLabel;
	
	JLabel policyNumberLabel;
	JTextField policyNumberInput;
	
	JButton nextOnInsurance;
	JButton previousOnInsurance;
	JLabel displayNameInInsurance;
	
	JComboBox companies;
	String selectedCompany;
	
	JLabel errorMessageOnInsurance;
	
	//---------------------------------------------------------------
	
	
	
	// ---------------------------Permit---------------------------
		private JPanel expiryDatePanel;
		private JComboBox<Integer> permitDurationBox;
		private JLabel todayLabel;
		private JLabel expiryDateLabel;
		private JLabel priceLabel;
		private JLabel permitTitle;
		JButton previousOnPermit;
		JButton submit;
		JPanel buttonPanelOnPermit;
		JLabel displayNameOnPermit;

		
	//--------------------------------------------------------------
		
	//---------------------------popupMenu---------------------------
		
		String displayMessage;
		String nameString;
		String emailString;
		String vehicleMakeString;
		String vehicleModelString;
		String plateNumberString;
		String companyString;
		String policyNumberString;
		String expiryDateString;
		String permitDurationString;
		String amountPaidString;
		String receiptString;
		
		//JLabel 
		
		
	//---------------------------------------------------------------

	
	
	// keyboard panels
	private JPanel numKeyboardPanel;
	private JPanel letterKeyboard;
	
	// keyboard map
	private List<JButton> letterButtonList;

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
		PINInput = new JPasswordField(4);
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
		p1.setPreferredSize(new Dimension(1400,800));
		p1.setMaximumSize(new Dimension(1400,800));
		p1.setLayout(new GridLayout(5,1));

		setupNumKeyboard();

		p1.add(logoPane);
		p1.add(loginPane);
		p1.add(incorrectLogin);
		
		p1.add(numKeyboardPanel);
		p1.add(loginButton);

		this.setContentPane(p1);
		//------------------------------------------------------

		//---------------------Subscription---------------------
		setupSubscriptionPanel();

		//------------------------------------------------------


		//---------------------Vehicle Information---------------------
		setupVehiclePanel();
		

		//-------------------------------------------------------------
		
		
		//---------------------Insurance Information---------------------
		setupInsurancePanel();		
		
		//---------------------------------------------------------------
		
		
		
		//----------------------------Permit------------------------------
		//displayNameOnPermit = new JLabel();

		setupExpiryDatePanel();
		
		//---------------------------------------------------------------
		
		

		p2 = new JTabbedPane();
		p2.addChangeListener(this);
		//p2.add(PINLabel);
		p2.setPreferredSize(new Dimension(1400,800));
		p2.addTab("Subscribe",icon, subscriptionPane);
//		p2.addTab("Vehicle Information",vehiclePane);
		p2.addTab("My Vehicle",icon,vehiclePane);
		p2.addTab("Insurance",  icon,insurancePane);
		p2.addTab("Expiry Date",icon, expiryDatePanel);
		
		p2.setEnabledAt(1, false);
		p2.setEnabledAt(2, false);
		p2.setEnabledAt(3, false);
		
		
		for (JButton b : letterButtonList)
		{
			int keyboardButtonSize = 20;
			b.setPreferredSize(new Dimension(keyboardButtonSize, keyboardButtonSize));
		}
		
	} // end constructor
	
	private void setupSubscriptionPanel()
	{
		yorkLogoLabelEmailPage = new JLabel(new ImageIcon(yorkLogo.getImage().getScaledInstance(980, 193, Image.SCALE_SMOOTH)));
		subscriptionPane = new JPanel();
		subscriptionPane.setBackground(Color.white);
		subscriptionPane.setLayout(new GridLayout(5,1));
		subscriptionPane.setPreferredSize(new Dimension(1400,800));


		subscriptionPane.add(yorkLogoLabelEmailPage);

		final int INPUTPANELROW = 4;
		emailInputPanel = new JPanel();
		displayName = new JLabel(displayNameString);
		displayName.setForeground(Color.DARK_GRAY);
		displayName.setFont(new Font("Serif", Font.BOLD, 25));
		displayName.setHorizontalAlignment(JLabel.LEFT);
		
		emailInputPanel.add(displayName);
		emailInputPanel.setBackground(Color.white);
		emailInputPanel.setLayout(new GridLayout(INPUTPANELROW,1));
		emailLabel = new JLabel("Would you like to receive parking news at york?\nPlease enter your Email(Optional): ");
		emailLabel.setFont(new Font("Arial", Font.BOLD, 20));
		emailInput = new JTextField(25);
		emailInput.addActionListener(this);
		emailInputPanel.add(emailLabel);
		emailInputPanel.add(emailInput);
		emailInput.addFocusListener(this);
		incorrectEmailFormat = new JLabel("Please enter the correct Email format");
		incorrectEmailFormat.setForeground(Color.RED);
		incorrectEmailFormat.setFont(new Font("Serif", Font.BOLD, 30));
		incorrectEmailFormat.setHorizontalAlignment(JLabel.CENTER);
		
		
		incorrectEmailFormat.setVisible(false);
		emailInputPanel.add(incorrectEmailFormat);

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
		subscriptionKeyboardPanel = new JPanel();
		subscriptionKeyboardPanel.setBackground(Color.WHITE);
		subscriptionKeyboardPanel.add(letterKeyboard);
		subscriptionPane.add(subscriptionKeyboardPanel);
		subscriptionPane.add(buttonPanelOnSubscription);
	} // end method setupSubscriptionPanel
	
	private void setupInsurancePanel()
	{
		insuranceTitle = new JLabel(new ImageIcon(yorkLogo.getImage().getScaledInstance(980, 193, Image.SCALE_SMOOTH)));
		displayNameInInsurance = new JLabel();
		
		errorMessageOnInsurance = new JLabel("Please enter valid 9 digits Policy Number");
		errorMessageOnInsurance.setVisible(false);
		errorMessageOnInsurance.setForeground(Color.RED);
		errorMessageOnInsurance.setFont(new Font("Serif", Font.BOLD, 30));
		errorMessageOnInsurance.setHorizontalAlignment(JLabel.CENTER);
		
		companyInfoPane = new JPanel();
		companyInfoPane.setBackground(Color.white);
		companyInfoPane.setLayout(new GridLayout(2,1));
		
		insuranceCompanyLabel = new JLabel("Please choose your insurance company: ");
		companies = new JComboBox();
		for(int i=0;i<companyDatabase.size();i++)
		{
			companies.addItem(companyDatabase.get(i));
		}
		companies.setSelectedIndex(0);
		companies.addActionListener(this);
		companyInfoPane.add(insuranceCompanyLabel);
		companyInfoPane.add(companies);
		
		
		final int POLICYLIMIT = 9; // assuming the limit of a policy number is 9 digits
		policyNumberPane = new JPanel();
		policyNumberPane.setBackground(Color.white);
		policyNumberPane.setLayout(new GridLayout(2,1));
		policyNumberLabel = new JLabel("Policy Number: ");
		policyNumberInput = new JTextField();
		focusTextLimit.put(policyNumberInput, POLICYLIMIT);
		policyNumberInput.addFocusListener(this);
		policyNumberPane.add(policyNumberLabel);
		policyNumberPane.add(policyNumberInput);
		
		buttonPaneOnInsurance = new JPanel();
		buttonPaneOnInsurance.setBackground(Color.white);
		buttonPaneOnInsurance.setLayout(new GridLayout(1,2));
		
		// save default selection
		selectedCompany = (String)companies.getSelectedItem();
		companyString = selectedCompany;
	
		
		//previous and next buttons
		previousOnInsurance = new JButton(previous);
		previousOnInsurance.setBorder(BorderFactory.createEmptyBorder());
		previousOnInsurance.addActionListener(this);
		nextOnInsurance = new JButton(next);
		nextOnInsurance.addActionListener(this);
		nextOnInsurance.setBorder(BorderFactory.createEmptyBorder());

		buttonPaneOnInsurance.add(previousOnInsurance);
		buttonPaneOnInsurance.add(nextOnInsurance);
		
		insuranceKeyboardPanel = new JPanel();
		insuranceKeyboardPanel.setBackground(Color.WHITE);

		final int INSURANCEPANELROW = 7;
		insurancePane = new JPanel();
		insurancePane.setPreferredSize(new Dimension(1400,800));
		insurancePane.setBackground(Color.white);
		insurancePane.setLayout(new GridLayout(INSURANCEPANELROW,1));
		
		displayNameInInsurance.setForeground(Color.DARK_GRAY);
		displayNameInInsurance.setFont(new Font("Serif", Font.BOLD, 25));
		displayNameInInsurance.setHorizontalAlignment(JLabel.LEFT);
		
		insurancePane.add(insuranceTitle);
		insurancePane.add(displayNameInInsurance);
		insurancePane.add(companyInfoPane);
		insurancePane.add(policyNumberPane);
		insurancePane.add(insuranceKeyboardPanel);
		insurancePane.add(errorMessageOnInsurance);
		insurancePane.add(buttonPaneOnInsurance);

	} // end method setupInsurancePanel
	
	private void setupVehiclePanel()
	{
		
		
		final int VECHILEPANEROW = 5;
		vehiclePane = new JPanel();
		vehiclePane.setPreferredSize(new Dimension(1400,800));
		vehiclePane.setBackground(Color.white);
		/*GridLayout layoutVehiclePane = new GridLayout(VECHILEPANEROW,1);
		layoutVehiclePane.setVgap(50);
		vehiclePane.setLayout(layoutVehiclePane);
		*/	
		
		vehicleTitle = new JLabel(new ImageIcon(yorkLogo.getImage().getScaledInstance(980, 193, Image.SCALE_SMOOTH)));
		
		vehiclePane.add(vehicleTitle);
		
		displayNameInVehicle = new JLabel(displayNameString);
		displayNameInVehicle.setForeground(Color.DARK_GRAY);
		displayNameInVehicle.setFont(new Font("Serif", Font.BOLD, 25));
		displayNameInVehicle.setHorizontalAlignment(JLabel.LEFT);
		vehiclePane.add(displayNameInVehicle);
		
		

		errorMessageOnVehicle = new JLabel("Please complete all three fields");
		errorMessageOnVehicle.setForeground(Color.RED);
		errorMessageOnVehicle.setFont(new Font("Serif", Font.BOLD, 30));
		errorMessageOnVehicle.setHorizontalAlignment(JLabel.CENTER);

		// panel to hold input and related labels
		final int inputPanelROW = 4;
		//GridLayout layoutInputPanel = new GridLayout(inputPanelROW, 1);
		JPanel inputPanel = new JPanel();
		inputPanel.setBackground(Color.WHITE);
		BoxLayout layoutInputPanel = new BoxLayout(inputPanel,BoxLayout.Y_AXIS);

		//layoutInputPanel.setVgap(20);
		
		inputPanel.setLayout(layoutInputPanel);
		
		vehicleMakeLabel = new JLabel("Vehicle Make");
		vehicleMakeLabel.setFont(new Font("Arial", Font.BOLD, 30));
		vehicleMakeInput = new JTextField(15);
		vehicleMakeInput.addActionListener(this);
		vehicleMakeInput.addFocusListener(this);
		
		vehicleModelLabel = new JLabel("Vehicle Model");
		vehicleModelLabel.setFont(new Font("Arial", Font.BOLD, 30));
		vehicleModelInput = new JTextField(15);
		vehicleModelInput.addActionListener(this);
		vehicleModelInput.addFocusListener(this);
		
		plateNumberLabel = new JLabel("Plate Number: ");
		plateNumberLabel.setFont(new Font("Arial", Font.BOLD, 30));
		plateNumberInput = new JTextField(15);
		plateNumberInput.addActionListener(this);
		plateNumberInput.addFocusListener(this);
				
		vehicleMakePane = new JPanel();
		vehicleMakePane.setBackground(Color.white);
		vehicleMakePane.setLayout(new FlowLayout());
		vehicleMakePane.add(vehicleMakeLabel);
		vehicleMakePane.add(vehicleMakeInput);
		
		inputPanel.add(vehicleMakePane);
		
		vehicleModelPane = new JPanel();
		vehicleModelPane.setBackground(Color.white);
		vehicleModelPane.setLayout(new FlowLayout());
		vehicleModelPane.add(vehicleModelLabel);
		vehicleModelPane.add(vehicleModelInput);
		
		inputPanel.add(vehicleModelPane);
		
		plateNumberPane = new JPanel();
		plateNumberPane.setBackground(Color.white);
		plateNumberPane.setLayout(new FlowLayout());
		plateNumberPane.add(plateNumberLabel);
		plateNumberPane.add(plateNumberInput);
		
		inputPanel.add(plateNumberPane);

		errorMessageOnVehicle = new JLabel("Please complete all three fields");
		errorMessageOnVehicle.setVisible(false);
		inputPanel.add(errorMessageOnVehicle);
		
		vehiclePane.add(inputPanel);
		
		// keyboard panel 
		vehicleKeyboardPane = new JPanel();
		vehicleKeyboardPane.setBackground(Color.WHITE);
		vehiclePane.add(vehicleKeyboardPane);
		
		buttonPaneOnVehicle = new JPanel();
		buttonPaneOnVehicle.setBackground(Color.white);
		buttonPaneOnVehicle.setLayout(new GridLayout(1,2));
		
		//previous and next buttons
		previousOnVehicle = new JButton(previous);
		previousOnVehicle.setBorder(BorderFactory.createEmptyBorder());
		previousOnVehicle.addActionListener(this);
		nextOnVehicle = new JButton(next);
		nextOnVehicle.addActionListener(this);
		nextOnVehicle.setBorder(BorderFactory.createEmptyBorder());

		JPanel errorPanelOnVehicle = new JPanel();
		errorPanelOnVehicle.add(errorMessageOnVehicle);
		buttonPaneOnVehicle.add(previousOnVehicle);
		buttonPaneOnVehicle.add(nextOnVehicle);
		errorMessageOnVehicle.setVisible(false);
		vehiclePane.add(errorPanelOnVehicle);
		
		vehiclePane.add(buttonPaneOnVehicle);
	} // end method setupVehiclePanel
		

	
	private void setupPopUp()
	{
		
		
		displayMessage = "Full Name: "+nameString+"\n\n"
				+ "Email: "+emailString+"\n\n"
				+"Vehicle Make: "+vehicleMakeString+"\n"
				+"Vehicle Model : "+vehicleModelString+"\n"
				+"Plate Number: "+plateNumberString+"\n\n"
				+"Insurance Company: "+companyString+"\n"
				+"Policy Number: "+policyNumberString+"\n\n"
				+"Permit Duration: "+permitDurationString+"\n"
				+"Expiry Date: "+expiryDateString+"\n"
				+"Amount Paid: "+amountPaidString+"\n\n"
				+receiptString;
		
		JOptionPane.showMessageDialog(null, displayMessage);	
		
	}


	private void setupExpiryDatePanel()
	{
		// expiry date properties
		final int expiryDatePanelROW = 9;
		final int LONGESTPERMITDATE = 30;
		
		permitTitle = new JLabel(new ImageIcon(yorkLogo.getImage().getScaledInstance(980, 193, Image.SCALE_SMOOTH)));
		displayNameOnPermit = new JLabel();

		// set up expiry date panel 

		if ( expiryDatePanel == null ) expiryDatePanel = new JPanel(new GridLayout(expiryDatePanelROW, 1));
		expiryDatePanel.setPreferredSize(new Dimension(1200,700));


		expiryDatePanel.setBackground(Color.WHITE);

		// set up today label
		Date today = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		String todayStr = "Today: " + format.format(today);
		todayLabel = new JLabel(todayStr);
		todayLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		displayNameOnPermit.setForeground(Color.DARK_GRAY);
		displayNameOnPermit.setFont(new Font("Serif", Font.BOLD, 25));
		displayNameOnPermit.setHorizontalAlignment(JLabel.LEFT);
		
		expiryDatePanel.add(permitTitle);
		expiryDatePanel.add(displayNameOnPermit);
		expiryDatePanel.add(todayLabel);
		
		// set up date selection label
		String dateSelctionLabelStr = "Please select the number of dates you would like to purchase permit for";
		JLabel dateSelctionLabel = new JLabel(dateSelctionLabelStr);
		dateSelctionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		expiryDatePanel.add(dateSelctionLabel);

		// set up select date combo box
		Integer[] dateAvalible = new Integer[LONGESTPERMITDATE];
		for (int i = 0; i < LONGESTPERMITDATE; i++)
			dateAvalible[i] = i + 1;
		permitDurationBox = new JComboBox<>(dateAvalible);
		permitDurationBox.addActionListener(this);
		((JLabel) permitDurationBox.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
		expiryDatePanel.add(permitDurationBox);

		// set up exipry day label
		int dateInt = 1;// since defeault is same day parking
		String exipryDateStr = getExpiryDate(dateInt); 
		expiryDateLabel = new JLabel(exipryDateStr);
		expiryDateLabel.setHorizontalAlignment(SwingConstants.CENTER);
		expiryDatePanel.add(expiryDateLabel);
		
		// setup price Label
		String priceStr = getPriceStr(dateInt);  
		priceLabel = new JLabel(priceStr);
		priceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		expiryDatePanel.add(priceLabel);
		
		// save default price label
		permitDurationString = dateInt+" Days";
		expiryDateString = exipryDateStr;
		amountPaidString = getPriceStr(dateInt);
		
		// setup note label
		String noteStr = "Please note that the billing is automatically applied to your account.";
		JLabel noteLabel = new JLabel(noteStr);
		expiryDatePanel.add(noteLabel);
		
		// setup button
		buttonPanelOnPermit = new JPanel();
		buttonPanelOnPermit.setBackground(Color.white);
		buttonPanelOnPermit.setLayout(new GridLayout(1,2));
		
		previousOnPermit = new JButton(previous);
		previousOnPermit.addActionListener(this);
		previousOnPermit.setBorder(BorderFactory.createEmptyBorder());
		submit = new JButton(submitImage);
		submit.addActionListener(this);
		submit.setBorder(BorderFactory.createEmptyBorder());
		
		buttonPanelOnPermit.add(previousOnPermit);
		buttonPanelOnPermit.add(submit);
		
		expiryDatePanel.add(buttonPanelOnPermit);

	} // end method setupExpiryDatePanel

	private void setKeyboardSymbolEnabled(boolean enable)
	{
		String regex = "[0-9a-zA-Z]+";
		if (enable)
		{
			for (Map.Entry<JButton, String> entry : allKeyboardButtonMap.entrySet())
			{
				JButton b = entry.getKey();
				String text = b.getText();
				if ( !text.matches(regex) )
					b.setEnabled(true);
			}
		}
		else
		{
			for (Map.Entry<JButton, String> entry : allKeyboardButtonMap.entrySet())
			{
				JButton b = entry.getKey();
				String text = b.getText();
				if ( !text.matches(regex) )
					b.setEnabled(false);
			}

		}
		
	} // end method setKeyboardSymbolEnabled
	
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
			if (str.equals("BackSpace"))
			{
				// only backspace when there is text
				if (focusTextLength > 0)
				{
					String text = focusTextValue.substring(0, focusTextLength - 1);
					focusText.setText(text);					
				} // end if, backspace only when there is text

			} // end if, backspace button

			// add digit, only case for student number and pin
			else if (focusText != null && focusTextLimit.get(focusText) != null && focusTextLength < focusTextLimit.get(focusText) )
				focusText.setText(focusTextValue + str);
			// add input to other letter fields
			else if (focusText != null && focusTextLimit.get(focusText) == null)
				focusText.setText(focusTextValue + str);
		}
		// login button press
		else if(ae.getSource().equals(loginButton))
		{
			readStudentDatabase();
			if(studentNumberInDatabase(studentNumberInput.getText())&&studentNumberAndPINMatches(studentNumberInput.getText(),PINInput.getText()))
			{
				// check for outstanding fines
				if ( studentMap.get(studentNumberInput.getText()).get("status").equals("arrears") )
				{
					String title = "Unable to issue permit";
					String message = "Due to outsanding fines on the account, the parking system cannot issue any parking permits. Please pay the account balance first";
					JOptionPane.showMessageDialog(null, message, title, JOptionPane.PLAIN_MESSAGE);
					return;
				} // end if, check out standing fines
				
				studentNumber = studentNumberInput.getText();
				remove(p1);
				setContentPane(p2);
				this.pack();

				displayNameString = "Welcome "+studentMap.get(studentNumber).get("GivenName")+" "+studentMap.get(studentNumber).get("FamilyName");
				nameString = studentMap.get(studentNumber).get("GivenName")+" "+studentMap.get(studentNumber).get("FamilyName");
				displayName.setText(displayNameString);
				displayNameInVehicle.setText(displayNameString);
				displayNameInInsurance.setText(displayNameString);
				displayNameOnPermit.setText(displayNameString);
				
				
				}
			else
			{
				int fontSize = 30;
				incorrectLogin.setVisible(true);
				incorrectLogin.setForeground(Color.RED);
				incorrectLogin.setBackground(Color.WHITE);
				incorrectLogin.setFont(new Font("Serif", Font.BOLD, fontSize));
				incorrectLogin.setHorizontalAlignment(JLabel.CENTER);

			}

		}
		else if(ae.getSource().equals(nextOnSubscription))
		{
			if( emailValid(emailInput.getText()) )
			{
				incorrectEmailFormat.setVisible(false);

				if(emailInput.getText().isEmpty())
				{
					emailString="Not Subscribed";
					receiptString=" ";
				}
				else
				{
					emailString=emailInput.getText();
					receiptString="A copy of receipt will be sent to your Email\nThanks for parking at York University";
				}
				
				subscriptionKeyboardPanel.remove(letterKeyboard);
				vehicleKeyboardPane.add(letterKeyboard);
				setKeyboardSymbolEnabled(false);
				p2.setEnabledAt(1, true);
				revalidate();
				//				remove(subscriptionPane);
				//				setContentPane(vehiclePane);
				p2.setSelectedIndex(1);

				this.pack();

			}
			else
			{
				incorrectEmailFormat.setVisible(true);
			}
		}
		else if(ae.getSource().equals(nextOnVehicle))
		{
			errorMessageOnVehicle.setVisible(false);

			if(vehicleInfoValid(vehicleMakeInput.getText(),vehicleModelInput.getText(),plateNumberInput.getText()))
			{
				

				vehicleMakeString= vehicleMakeInput.getText();
				vehicleModelString = vehicleModelInput.getText();
				plateNumberString = plateNumberInput.getText();
				p2.setEnabledAt(2,true);

				p2.setSelectedIndex(2);
				p1.remove(numKeyboardPanel);
				insuranceKeyboardPanel.add(numKeyboardPanel);
				revalidate();
				this.pack();
			}
			else
			{
				errorMessageOnVehicle.setVisible(true);
			}
			
			
		}
		else if(ae.getSource().equals(previousOnVehicle))
		{
			p2.setSelectedIndex(0);
			vehicleKeyboardPane.remove(letterKeyboard);
			subscriptionKeyboardPanel.add(letterKeyboard);
			setKeyboardSymbolEnabled(true);
			revalidate();
			this.pack();
		}
		else if(ae.getSource().equals(companies))
		{
			selectedCompany = (String)companies.getSelectedItem();
			companyString = selectedCompany;
		}
		else if(ae.getSource().equals(nextOnInsurance))
		{
			if(policyNumberValid(policyNumberInput.getText()))
			{
				errorMessageOnInsurance.setVisible(false);

				policyNumberString = policyNumberInput.getText();
				p2.setEnabledAt(3, true);
				p2.setSelectedIndex(3);
				this.pack();
			}
			else
			{
				errorMessageOnInsurance.setVisible(true);
			}
			
		}
		else if(ae.getSource().equals(previousOnInsurance))
		{
			p2.setSelectedIndex(1);
			this.pack();
		}
		else if(ae.getSource().equals(previousOnPermit))
		{
			p2.setSelectedIndex(2);
		}
		else if(ae.getSource().equals(submit))
		{
			setupPopUp();
			cleanUp();
			this.setContentPane(p1);
			revalidate();
		}
		// case : select the number of days for permit
		else if ( ae.getSource() == permitDurationBox)
		{
			
			// prase Input
			String dateStr = permitDurationBox.getSelectedItem().toString();
			Integer dateInt = Integer.parseInt(dateStr);
			permitDurationString = dateStr+" Days";

			// change exipry date based on slected date
			String output = getExpiryDate(dateInt);
			expiryDateLabel.setText(output);
			expiryDateString = output; 
			
			// update price based on selction
			output = getPriceStr(dateInt);
			priceLabel.setText(output);
			amountPaidString = getPriceStr(dateInt);
		} // end if, number days for permit

	}
	
	private void cleanUp()
	{
		// clear login panel
		studentNumberInput.setText("");
		PINInput.setText("");
		incorrectLogin.setVisible(false);
		
		// clear subscribe panel
		emailInput.setText("");
		incorrectEmailFormat.setVisible(false);
		
		// clear vehicle panel
		vehicleMakeInput.setText("");
		vehicleModelInput.setText("");
		plateNumberInput .setText("");
		errorMessageOnVehicle.setVisible(false);
		
		// clear insurance panel
		policyNumberInput.setText("");
		errorMessageOnInsurance.setVisible(false);
		
		// clear parking panel
		expiryDatePanel.removeAll();
		setupExpiryDatePanel();
		
		// bring tabbed panel back to first page and disable others
		p2.setSelectedIndex(0);
		p2.setEnabledAt(1, false);
		p2.setEnabledAt(2, false);
		p2.setEnabledAt(3, false);
	} // end method cleanUp
	
	private String getPriceStr(int date)
	{
		String priceStr = getPrice(date);
		String output = "Current price is $" + priceStr;
		return output;
	} // end method getPriceStr

	private String getPrice(int date)
	{
		int dailyPrice = 35; // temp increase by a factor 10
		int price = dailyPrice * date;
		String priceStr = Integer.toString(price);
		priceStr = priceStr.substring(0, priceStr.length() - 1) + "." + priceStr.substring(priceStr.length() - 1);
		return priceStr;
	} // end method getPrice
	
	private String getExpiryDate(int date)
	{
		Date today = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		cal.add(Calendar.DATE, date - 1);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		String output = "Permit Expiry Date: " +format.format(cal.getTime());
		
		return output;
	} // end method getExipryDate

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

	public static boolean policyNumberValid(String policyNum)
	{
		String regex = "[0-9]{9}";
		if (policyNum.matches(regex))
			return true;
		else
			return false;
//		if(policyNum.isEmpty())
//			return false;
//		else
//			return true;
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
	
	public static List<String> readCompanyDatabase()
	{
		
		File file = new File("companies.txt");
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
				str = dis.readLine();
				
				companyDatabase.add(str);
				
			}
		}catch(IOException e)
		{
			System.out.println("File Error");
		}
		
		
		return companyDatabase;
		
	}

	private void setupNumKeyboard()
	{

		// define properties of keyboard 
		final int ROW = 4;
		final int COL = 3;

		// define keyboard data
		String[] num = {"1", "2", "3", "4", "5", "6","7", "8", "9", "BOX", "0", "BackSpace"};

		// define keyboard
		numKeyboardPanel = new JPanel(new GridLayout(ROW, COL));
		numKeyboardPanel.setBackground(Color.WHITE);

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
		// create letterButtonList to save everything button
		letterButtonList = new ArrayList<>();
		
		// define keyboard properties
		int rowNum = 6;

		// keyboard data
		String zeroRow[] = {"`", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "-", "+"};
		String firstRow[] = {"~","1","2","3","4","5","6","7","8","9","0","-","+","BackSpace"};
		String secondRow[] = {"Q","W","E","R","T","Y","U","I","O","P","{", "}", "[","]","|", "\\"};
		String thirdRow[] = {"A","S","D","F","G","H","J","K","L",";",":", "\'", "\"","Enter"};
		String fourthRow[] = {"Z","X","C","V","B","N","M","<", ">",",",".", "/", "?"};
		String fifthRow[]={" " ,"<" ,"\\/",">" };

		// main letter keyboard panel
		letterKeyboard = new JPanel(new GridLayout(rowNum, 1));
		letterKeyboard.setBackground(Color.WHITE);

		// subpanel for letter keyboard for the last row
		JPanel letterKeyboardPanel0 = new JPanel(new GridLayout(1, zeroRow.length));
		letterKeyboardPanel0.setBackground(Color.WHITE);
		JPanel letterKeyboardPanel1 = new JPanel(new GridLayout(1, firstRow.length));
		letterKeyboardPanel1.setBackground(Color.WHITE);
		JPanel letterKeyboardPanel2 = new JPanel(new GridLayout(1, secondRow.length));
		letterKeyboardPanel2.setBackground(Color.WHITE);
		JPanel letterKeyboardPanel3 = new JPanel(new GridLayout(1, thirdRow.length));
		letterKeyboardPanel3.setBackground(Color.WHITE);
		JPanel letterKeyboardPanel4 = new JPanel(new GridLayout(1, fourthRow.length));
		letterKeyboardPanel4.setBackground(Color.WHITE);
		JPanel letterKeyboardPanel5 = new JPanel(new GridLayout(1, fifthRow.length));
		letterKeyboardPanel5.setBackground(Color.WHITE);

		// add subpanels to main letter panel
		letterKeyboard.add(letterKeyboardPanel0);
		letterKeyboard.add(letterKeyboardPanel1);
		letterKeyboard.add(letterKeyboardPanel2);
		letterKeyboard.add(letterKeyboardPanel3);
		letterKeyboard.add(letterKeyboardPanel4);
		letterKeyboard.add(letterKeyboardPanel5);

		for (int i = 0; i < zeroRow.length; i++)
		{
			JButton b = new JButton(zeroRow[i]);
			letterKeyboardPanel0.add(b);
			allKeyboardButtonMap.put(b, zeroRow[i]);
			b.addActionListener(this);
			letterButtonList.add(b);
		}
		
		for (int i = 0; i < firstRow.length; i++)
		{
			JButton button = new JButton(firstRow[i]);
			letterKeyboardPanel1.add(button);
			allKeyboardButtonMap.put(button, firstRow[i]);
			button.addActionListener(this);
			letterButtonList.add(button);
		} // end for add first row

		// add second row keyboard
		for (int i = 0; i < secondRow.length; i++)
		{
			JButton b = new JButton(secondRow[i]);
			letterKeyboardPanel2.add(b);
			allKeyboardButtonMap.put(b, secondRow[i]);
			b.addActionListener(this);
			letterButtonList.add(b);
		} // end for add second row

		// add third row keyboard
		for (int i = 0; i < thirdRow.length; i++)
		{
			JButton b = new JButton(thirdRow[i]);
			letterKeyboardPanel3.add(b);
			allKeyboardButtonMap.put(b, thirdRow[i]);
			b.addActionListener(this);
			letterButtonList.add(b);
		} // end for add third row

		// add forth row keyboard
		for (int i = 0; i < fourthRow.length; i++)
		{
			JButton b = new JButton(fourthRow[i]);
			letterKeyboardPanel4.add(b);
			allKeyboardButtonMap.put(b, fourthRow[i]);
			b.addActionListener(this);
			letterButtonList.add(b);
		} // end for add 4th row

		// add 5th row keyboard
		for (int i = 0; i < fifthRow.length; i++)
		{
			JButton b = new JButton(fifthRow[i]);
			letterKeyboardPanel5.add(b);
			allKeyboardButtonMap.put(b, fifthRow[i]);
			b.addActionListener(this);
			letterButtonList.add(b);
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
	
	public static boolean vehicleInfoValid(String model,String make,String plateNum)
	{
		if(!model.isEmpty() && !make.isEmpty() && !plateNum.isEmpty())
			return true;
		else
			return false;
	}



	@Override
	public void focusGained(FocusEvent e) 
	{
		// TODO Auto-generated method stub
		focusText = (JTextField) e.getSource();
	} // end method focusGained

	@Override
	public void focusLost(FocusEvent e) 
	{

	}

	@Override
	public void stateChanged(ChangeEvent e) 
	{
		// index of panels in tab panels
		final int SUBPANELINDEX = 0;
		final int VEHICLEINDEX = 1;
		final int INSURANCEINDEX = 2;
		final int EXPIRYINDEX = 3;
		
		int currentIndex = p2.getSelectedIndex();
		switch (currentIndex)
		{
			case SUBPANELINDEX:
				subscriptionKeyboardPanel.add(letterKeyboard);
				pack();
				break;
			case VEHICLEINDEX:
				vehicleKeyboardPane.add(letterKeyboard);
				pack();
				break;
			case INSURANCEINDEX:
				break;
			case EXPIRYINDEX:
				break;
		} // end switch, do action base on tab change
		
	} // end method stateChanged
	
}
