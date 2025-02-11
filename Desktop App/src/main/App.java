package main;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.zkteco.biometric.FingerprintSensorErrorCode;
import com.zkteco.biometric.FingerprintSensorEx;

public class App extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JButton btnOpen = null;
	JButton btnEnroll = null;
	JButton btnVerify = null;

	JTextField usernameTextField = null;
	JTextField deleteTextField = null;

	JButton timeIn = null;
	JButton timeOut = null;
	JButton dailyRaport = null;
	JButton btnClose = null;
	JButton btnImg = null;
	JButton deleteUserBtn = null;

	private JTable employeeTable;
	private UserTableModel userTableModel;
	Users users;
	Employee employee;
	List<Employee> employeeList;
	private JTextArea textArea;

	// the width of fingerprint image
	int fpWidth = 0;
	// the height of fingerprint image
	int fpHeight = 0;
	// for verify test
	private byte[] lastRegTemp = new byte[2048];
	// the length of lastRegTemp
	private int cbRegTemp = 0;
	// pre-register template
	private byte[][] regtemparray = new byte[3][2048];
	// Register
	private boolean bRegister = false;
	// Identify
	private boolean identifyIn = true;
	private boolean identifyOut = false;
	// finger id
	private int iFid = 1;

	private int nFakeFunOn = 1;
	// must be 3
	static final int enroll_cnt = 3;
	// the index of pre-register function
	private int enroll_idx = 0;

	private byte[] imgbuf = null;
	private byte[] template = new byte[2048];
	private int[] templateLen = new int[1];

	private boolean mbStop = true;
	private long mhDevice = 0;
	private long mhDB = 0;
	private WorkThread workThread = null;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				try {
					App frame = new App();
					frame.setVisible(true);
					frame.launchFrame();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public App() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		// contentPane = new JPanel();
		contentPane = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				// Load your image (replace "path/to/your/image.jpg" with the actual path)
				ImageIcon backgroundImage = new ImageIcon(
						"C:/Users/user.DESKTOP-A9VSLE1/eclipseWorkSpace2/WindowBuilder/src/main/fingerprint.jpg");
				// Draw the image
				g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), null);
			}
		};
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		// BufferedImage backgroundImage = ImageIO.read(new
		// File("C:/Users/user.DESKTOP-A9VSLE1/eclipseWorkSpace2/WindowBuilder/src/main/fingerprint.jpg"));
		// // Provide the correct path

		setContentPane(contentPane);
	}

	public void launchFrame() {

		users = new Users();
		this.setLayout(null);
		btnOpen = new JButton("Open");
		this.add(btnOpen);
		int nRsize = 20;
		btnOpen.setBounds(30, 10 + nRsize, 140, 30);

		usernameTextField = new JTextField("username");
		usernameTextField.setBounds(30, 110 + nRsize, 140, 30);
		this.add(usernameTextField);

		btnEnroll = new JButton("Enroll");
		this.add(btnEnroll);

		btnClose = new JButton("Close");
		this.add(btnClose);
		btnClose.setBounds(30, 60 + nRsize, 140, 30);

		btnVerify = new JButton("Verify");
		this.add(btnVerify);

		timeIn = new JButton("Time in");
		this.add(timeIn);
		btnEnroll.setBounds(30, 160 + nRsize, 140, 30);

		btnVerify.setBounds(30, 210 + nRsize, 140, 30);

		timeIn.setBounds(30, 260 + nRsize, 140, 30);

		timeOut = new JButton("Time out");
		this.add(timeOut);
		timeOut.setBounds(30, 310 + nRsize, 140, 30);

		dailyRaport = new JButton("Daily Raport");
		this.add(dailyRaport);
		dailyRaport.setBounds(30, 360 + nRsize, 140, 30);

		deleteTextField = new JTextField("id");
		deleteTextField.setBounds(30, 410 + nRsize, 140, 30);
		this.add(deleteTextField);

		deleteUserBtn = new JButton("Delete");
		this.add(deleteUserBtn);
		deleteUserBtn.setBounds(30, 460 + nRsize, 140, 30);

		// radioANSI.setBounds(30, 360 + nRsize, 60, 30);

		// radioISO.setBounds(120, 360 + nRsize, 60, 30);

		// radioZK.setBounds(210, 360 + nRsize, 60, 30);

		// For End

		btnImg = new JButton();
		btnImg.setBounds(200, 30, 288, 375);
		btnImg.setDefaultCapable(false);
		this.add(btnImg);

		textArea = new JTextArea();
		this.add(textArea);
		textArea.setBounds(200, 430, 288, 150);
		// btnImg.setBounds(200, 30, 288, 375);
		textArea.setLineWrap(true);
		textArea.setCaretColor(Color.blue);
		
		textArea.setSelectedTextColor(Color.RED);

		// this.setSize(850, 620);
		this.setSize(1300, 700);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setTitle("fingerprint attendance");
		this.setResizable(false);

		btnOpen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Open();
				refreshData();
			}
		});

		btnClose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				FreeSensor();

				textArea.setText("Close succ!\n");
			}
		});

		btnEnroll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Enroll();

			}
		});

		btnVerify.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Verify();
				
			}
		});

		timeIn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				IdentifyTimeIn();

			}
		});

		timeOut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				IdentifyTimeOut();

			}
		});

		dailyRaport.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// generateRaport();

			}
		});

		deleteUserBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

			if (users.DeleteUser(deleteTextField.getText())) {
				 
				 FingerprintSensorEx.DBDel(mhDB  ,Integer.parseInt(deleteTextField.getText()) );
				refreshData();
				
				
			}else {
				
				textArea.setText("user that have the ID " + deleteTextField.getText() + " doesn't exist " );
			}
				

			}
		});

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				FreeSensor();
			}
		});

		
		
		// Creating sample Employee objects
		// Employee employee1 = new Employee(1, "John Doe",null
		// ,Timestamp.valueOf("2023-01-01 08:00:00"), Timestamp.valueOf("2023-01-01
		// 17:00:00"));
		// Adding Employee objects to the list
		// employeeList.add(employee1);
		// employeeList.add(users.GetUser("3"));

		
		
		// @todo add employees
	//	employeeList = users.GetAllUsers();
	//	for (Employee employee : employeeList) {
	//		int ret = FingerprintSensorEx.DBAdd(mhDB, employee.getUserId(), employee.getTemplateData());
	//		System.out.print(employee.getUserId() + " " + ret + " " + "thisistheuser");
	//	}
		employeeList = users.GetAllUsers();
		users.printEmployeeList(employeeList);
		userTableModel = new UserTableModel(employeeList);
		employeeTable = new JTable(userTableModel);
		
		employeeTable.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        int row = employeeTable.rowAtPoint(e.getPoint());
		        
		        if (e.getClickCount() == 2) { // Detect double-click
		           

		            if (row >= 0 ) {
		            	String userId =String.valueOf(employeeTable.getValueAt(row, 0)) ;
		                String userName =String.valueOf(employeeTable.getValueAt(row, 1)) ;
		                PopupTableManager.showPopupTable(userId,userName,users.getDetails(userId));
		            }
		        }else { // Detect one-click
		        	
		        	 if (row >= 0 ) {
		 		        
				            deleteTextField.setText(String.valueOf(employeeTable.getValueAt(row, 0)));
				        }
		        }
		       
		    }
		});

		
		
		JScrollPane scrollPane = new JScrollPane(employeeTable);
		
		// scrollPane.setBounds(500, 30, 288, 375);
		scrollPane.setBounds(520, 30, 700, 610);
		this.add(scrollPane);
		//refreshData() ;
	}

	protected void refreshData() {
		employeeList = users.GetAllUsers();
		FingerprintSensorEx.DBInit();
		for (Employee employee : employeeList) {
			//FingerprintSensorEx.DBInit();
			int results = FingerprintSensorEx.DBAdd(mhDB, employee.getUserId(), employee.getTemplateData());
			 System.out.println("Processing employee: " + employee.getUserId() + ", Result: " + results);
			 System.out.println("Processing employee: " + employee.getUserId() + ", CheckIn: " + employee.getCheckIn() + ", CheckOut: " + employee.getCheckOut());
			 
			
			
		}
		if (!employeeList.isEmpty()) {
		    Employee lastEmployee = employeeList.get(employeeList.size() - 1);
		    Integer lastEmployeeId = lastEmployee.getUserId();
		    iFid = lastEmployeeId ;
		    // Now 'lastEmployeeId' contains the ID of the last employee in the list
		    System.out.println("Last Employee ID: " + lastEmployeeId);
		} 
		userTableModel.setUserList(employeeList);	
		
	}

	protected void generateRaport() {
		// TODO Auto-generated method stub

	}

	protected void IdentifyTimeOut() {
		if (0 == mhDevice) {
			textArea.setText("Please Open device first!\n");
			return;
		}
		if (bRegister) {
			enroll_idx = 0;
			bRegister = false;
		}
		if (!identifyOut) {
			identifyOut = true;
			identifyIn = false;
		}

	}

	protected void IdentifyTimeIn() {
		if (0 == mhDevice) {
			textArea.setText("Please Open device first!\n");
			return;
		}
		if (bRegister) {
			enroll_idx = 0;
			bRegister = false;
		}
		if (!identifyIn) {
			identifyIn = true;
			identifyOut = false;
		}

	}

	protected void Enroll() {
		if (0 == mhDevice) {
			textArea.setText("Please Open device first!\n");
			return;
		}
		if (!bRegister) {
			if (!usernameTextField.getText().isEmpty()) {
				enroll_idx = 0;
				bRegister = true;
				textArea.setText("Please your finger 3 times!\n");
			} else {
				textArea.setText("Please write the user name");
			}
		}

	}

	protected void Verify() {
		if (0 == mhDevice) {
			textArea.setText("Please Open device first!\n");
			return;
		}
		if (bRegister) {
			enroll_idx = 0;
			bRegister = false;
		}
		if (identifyIn) {
			identifyIn = false;
		}

	}

	protected void Open() {
		// TODO Auto-generated method stub
		if (0 != mhDevice) {
			// already inited
			textArea.setText("Please close device first!\n");
			return;
		}
		int ret = FingerprintSensorErrorCode.ZKFP_ERR_OK;
		// Initialize
		cbRegTemp = 0;
		bRegister = false;
		identifyIn = false;
		identifyOut = false;
		iFid = 1;
		enroll_idx = 0;
		if (FingerprintSensorErrorCode.ZKFP_ERR_OK != FingerprintSensorEx.Init()) {
			textArea.setText("Init failed!\n");
			return;
		}
		ret = FingerprintSensorEx.GetDeviceCount();
		if (ret < 0) {
			textArea.setText("No devices connected!\n");
			FreeSensor();
			return;
		}
		if (0 == (mhDevice = FingerprintSensorEx.OpenDevice(0))) {
			textArea.setText("Open device fail, ret = " + ret + "!\n");
			FreeSensor();
			return;
		}
		if (0 == (mhDB = FingerprintSensorEx.DBInit())) {
			textArea.setText("Init DB fail, ret = " + ret + "!\n");
			FreeSensor();
			return;
		}

		// For ISO/Ansi
		int nFmt = 1; // iso

		FingerprintSensorEx.DBSetParameter(mhDB, 5010, nFmt);

		// set fakefun off
		// FingerprintSensorEx.SetParameter(mhDevice, 2002, changeByte(nFakeFunOn), 4);

		byte[] paramValue = new byte[4];
		int[] size = new int[1];
		// GetFakeOn
		// size[0] = 4;
		// FingerprintSensorEx.GetParameters(mhDevice, 2002, paramValue, size);
		// nFakeFunOn = byteArrayToInt(paramValue);

		size[0] = 4;
		FingerprintSensorEx.GetParameters(mhDevice, 1, paramValue, size);
		fpWidth = byteArrayToInt(paramValue);
		size[0] = 4;
		FingerprintSensorEx.GetParameters(mhDevice, 2, paramValue, size);
		fpHeight = byteArrayToInt(paramValue);

		imgbuf = new byte[fpWidth * fpHeight];
		// btnImg.resize(fpWidth, fpHeight);
		mbStop = false;
		workThread = new WorkThread();
		workThread.start();// 线程启动
		textArea.setText("Open succ! Finger Image Width:" + fpWidth + ",Height:" + fpHeight + "\n");
		
	}

	private void FreeSensor() {
		mbStop = true;
		try { // wait for thread stopping
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (0 != mhDB) {
			FingerprintSensorEx.DBFree(mhDB);
			mhDB = 0;
		}
		if (0 != mhDevice) {
			FingerprintSensorEx.CloseDevice(mhDevice);
			mhDevice = 0;
		}
		FingerprintSensorEx.Terminate();
	}

	public static void writeBitmap(byte[] imageBuf, int nWidth, int nHeight, String path) throws IOException {
		java.io.FileOutputStream fos = new java.io.FileOutputStream(path);
		java.io.DataOutputStream dos = new java.io.DataOutputStream(fos);

		int w = (((nWidth + 3) / 4) * 4);
		int bfType = 0x424d;
		int bfSize = 54 + 1024 + w * nHeight;
		int bfReserved1 = 0;
		int bfReserved2 = 0;
		int bfOffBits = 54 + 1024;
		dos.writeShort(bfType); 
		dos.write(changeByte(bfSize), 0, 4); 
		dos.write(changeByte(bfReserved1), 0, 2);
		dos.write(changeByte(bfReserved2), 0, 2);
		dos.write(changeByte(bfOffBits), 0, 4);
		int biSize = 40;
		int biWidth = nWidth;
		int biHeight = nHeight;
		int biPlanes = 1; 
		int biBitcount = 8;
		int biCompression = 0;
		int biSizeImage = w * nHeight;
		int biXPelsPerMeter = 0;
		int biYPelsPerMeter = 0;
		int biClrUsed = 0;
		int biClrImportant = 0;

		dos.write(changeByte(biSize), 0, 4);
		dos.write(changeByte(biWidth), 0, 4);
		dos.write(changeByte(biHeight), 0, 4);
		dos.write(changeByte(biPlanes), 0, 2);
		dos.write(changeByte(biBitcount), 0, 2);
		dos.write(changeByte(biCompression), 0, 4);
		dos.write(changeByte(biSizeImage), 0, 4);
		dos.write(changeByte(biXPelsPerMeter), 0, 4);
		dos.write(changeByte(biYPelsPerMeter), 0, 4);
		dos.write(changeByte(biClrUsed), 0, 4);
		dos.write(changeByte(biClrImportant), 0, 4);

		for (int i = 0; i < 256; i++) {
			dos.writeByte(i);
			dos.writeByte(i);
			dos.writeByte(i);
			dos.writeByte(0);
		}

		byte[] filter = null;
		if (w > nWidth) {
			filter = new byte[w - nWidth];
		}

		for (int i = 0; i < nHeight; i++) {
			dos.write(imageBuf, (nHeight - 1 - i) * nWidth, nWidth);
			if (w > nWidth)
				dos.write(filter, 0, w - nWidth);
		}
		dos.flush();
		dos.close();
		fos.close();
	}

	public static byte[] changeByte(int data) {
		return intToByteArray(data);
	}

	public static byte[] intToByteArray(final int number) {
		byte[] abyte = new byte[4];
		abyte[0] = (byte) (0xff & number);
		abyte[1] = (byte) ((0xff00 & number) >> 8);
		abyte[2] = (byte) ((0xff0000 & number) >> 16);
		abyte[3] = (byte) ((0xff000000 & number) >> 24);
		return abyte;
	}

	public static int byteArrayToInt(byte[] bytes) {
		int number = bytes[0] & 0xFF;
		number |= ((bytes[1] << 8) & 0xFF00);
		number |= ((bytes[2] << 16) & 0xFF0000);
		number |= ((bytes[3] << 24) & 0xFF000000);
		return number;
	}

	private class WorkThread extends Thread {
		@Override
		public void run() {
			super.run();
			int ret = 0;
			while (!mbStop) {
				templateLen[0] = 2048;
				if (0 == (ret = FingerprintSensorEx.AcquireFingerprint(mhDevice, imgbuf, template, templateLen))) {
					if (nFakeFunOn == 1) {
						byte[] paramValue = new byte[4];
						int[] size = new int[1];
						size[0] = 4;
						int nFakeStatus = 0;
						// GetFakeStatus
						ret = FingerprintSensorEx.GetParameters(mhDevice, 2004, paramValue, size);
						nFakeStatus = byteArrayToInt(paramValue);
						System.out.println("ret = " + ret + ",nFakeStatus=" + nFakeStatus);
						if (0 == ret && (byte) (nFakeStatus & 31) != 31) {
							textArea.setText("Is a fake finger?\n");
							return;
						}
					}
					OnCatpureOK(imgbuf);
					OnExtractOK(template, templateLen[0]);
				}
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}
	}

	private void OnCatpureOK(byte[] imgBuf) {
		try {
			writeBitmap(imgBuf, fpWidth, fpHeight, "fingerprint.bmp");
			btnImg.setIcon(new ImageIcon(ImageIO.read(new File("fingerprint.bmp"))));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void OnExtractOK(byte[] template, int len) {
		if (bRegister) {
			int[] fid = new int[1];
			int[] score = new int[1];
			//refreshData();
			
			
			
			
			
			
			
		//	usernameTextField.setText( String.valueOf(mhDB));
			int ret = FingerprintSensorEx.DBIdentify(mhDB, template, fid, score);
			
			if (ret == 0) {
				textArea.setText("the finger already enroll by " + fid[0] + ",cancel enroll\n");
				bRegister = false;
				enroll_idx = 0;
				return;
			}
			if (enroll_idx > 0 && FingerprintSensorEx.DBMatch(mhDB, regtemparray[enroll_idx - 1], template) <= 0) {
				textArea.setText("please press the same finger 3 times for the enrollment\n");
				return;
			}
			System.arraycopy(template, 0, regtemparray[enroll_idx], 0, 2048);
			enroll_idx++;
			if (enroll_idx == 3) {
				int[] _retLen = new int[1];
				_retLen[0] = 2048;
				byte[] regTemp = new byte[_retLen[0]];
				iFid++;
				//@todo
				if (0 == (ret = FingerprintSensorEx.DBMerge(mhDB, regtemparray[0], regtemparray[1], regtemparray[2],
						regTemp, _retLen)) && 0 == (ret = FingerprintSensorEx.DBAdd(mhDB, iFid, regTemp))) {
					
					cbRegTemp = _retLen[0];
					System.arraycopy(regTemp, 0, lastRegTemp, 0, cbRegTemp);
					// Base64 Template
					textArea.setText("enroll succ:\n");
					
					if (users.AddUser(iFid, usernameTextField.getText(), regTemp)) {
						System.out.print( " Add User " + iFid + " succ ");
						
					}else {
						System.out.print( " Add User " + iFid + " faild ");
					}
					refreshData();
				} else {
					textArea.setText("enroll fail, error code=" + ret + "\n");
				}
				bRegister = false;
			} else {
				textArea.setText("You need to press the " + (3 - enroll_idx) + " times fingerprint\n");
			}
		} else {
			if (identifyIn) {
				int[] fid = new int[1];
				int[] score = new int[1];
				
				int ret = FingerprintSensorEx.DBIdentify(mhDB, template, fid, score);
				if (ret == 0) {
					textArea.setText("Identify succ, fid=" + fid[0] + ",score=" + score[0] + "\n" + textArea.getText());
					
					 Employee employee = users.GetUser(String.valueOf(fid[0]));
					employee.setCheckIn(users.getTime());
					
					 users.UpdateUser(employee);
					 System.out.print("time is " + users.getTime());
					 refreshData();
				} else {
					textArea.setText("Identify fail, errcode=" + ret + "\n");
				}

			}
			if (identifyOut) {
				int[] fid = new int[1];
				int[] score = new int[1];
				int ret = FingerprintSensorEx.DBIdentify(mhDB, template, fid, score);
				if (ret == 0) {
					textArea.setText("Identify succ, fid=" + fid[0] + ",score=" + score[0] + "\n" + textArea.getText());

					 Employee employee = users.GetUser(String.valueOf(fid[0]));
						employee.setCheckOut(users.getTime());
						
						 users.UpdateUser(employee);
						 refreshData();

				} else {
					textArea.setText("Identify fail, errcode=" + ret + "\n");
				}

			} 
			//@todo
			//else {
			//	if (cbRegTemp <= 0) {
			//		textArea.setText("Please register first!\n");
				//	refreshData();
			//	} else {
			//		int ret = FingerprintSensorEx.DBMatch(mhDB, lastRegTemp, template);
			//		if (ret > 0) {
			//			textArea.setText("Verify succ, score=" + ret + "\n");
			//		} else {
			//			textArea.setText("Verify fail, ret=" + ret + "\n");
			//		}
			//		refreshData();
			//	}
		//	}
		}
	}
}