
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;

import flightt.LuggageRefNum;
import testing.userSeatSelection;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class FlightApp extends JFrame {

		private CardLayout cardLayout;
		private JPanel cardPanel;
		private List<UserAcc> userAccount;
		
		 private JComboBox<String> cityComboBox;
		 private JTextArea flightInfoTextArea;
		 
		 private int flightcount = 0;
		
		
		public FlightApp() {
			setTitle("Flight Booking/Tracking App");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	     	setSize(800,800);
	     	
	   
			///PANELS THAT WILL APPEAR
	     	
	     	
			cardLayout = new CardLayout();
			cardPanel = new JPanel(cardLayout);
			
			userAccount = new ArrayList<>();
			
			cardPanel.add(createAccountPanel(), "CREATE ACCOUNT");
			cardPanel.add(createLoginPanel(), "LOGIN");
			cardPanel.add(createHomepagePanel(), "HOME PAGE");
			
			
			
			cardLayout.show(cardPanel, "CREATE ACCOUNT");
			add(cardPanel);
			setVisible(true);
			
			
			

			
			
		}
		
		///class to represent the user accounts
		public static class UserAcc{
			private String username;
			private String password;
			
			public UserAcc(String username, String password) {
				this.username = username;
				this.password = password;
			}
			 
			public String getUser() {
				 return username;
				 
			 }
			 public String getPw() {
				 return password;
			 }
			
		}
		////CHECKING IF USERNAME IS TAKEN
		private boolean isUsernameTaken(String username) {
			for(UserAcc user : userAccount) {
				if (user.getUser().equals(username)) {
					return true;
				}
			}
			return false;
		}
		///// CHECKING FOR VALID LOGIN 
		private boolean ValidLogin(String username, String password) {
			for(UserAcc user : userAccount) {
				if(user.getUser().equals(username) && user.getPw().equals(password)) {
					return true;
				}
			}
			return false;
		}
		
		
		
		///CREATION FOR USER TO MAKE AN ACCOUNT
		
		private JPanel createAccountPanel() {

			JPanel panel = new JPanel(new GridLayout(4,2));
			
			JLabel userlabel = new JLabel("Create a username: ");
			JTextField usertextField = new JTextField();
			JLabel pwLabel =  new JLabel("Create a password: ");
			JPasswordField pwField = new JPasswordField();
			
			JButton createAccButton = new JButton("Create Account");
			createAccButton.addActionListener(new ActionListener(){
				
				public void actionPerformed(ActionEvent e) {
					
					String username = usertextField.getText();
					String password = new String(pwField.getPassword());
					
					///checking for already made accounts
					if(isUsernameTaken(username)) {
						JOptionPane.showMessageDialog(null, "Username taken. Choose another.");
						
					}else {
						UserAcc newUser = new UserAcc(username, password);
						userAccount.add(newUser);
					}
					
					///letting user know account was made and switching to home page
					System.out.println("Account has been created.");
					
					cardLayout.show(cardPanel, "HOME PAGE");
				}

				
			});
			panel.add(userlabel);
			panel.add(usertextField);
			panel.add(pwLabel);
			panel.add(pwField);
			panel.add(createAccButton);
						
			return panel;
			
		}
		
		//// CREATION FOR USERS TO LOGIN TO THEIR ACCOUNT

		private JPanel createLoginPanel() {
			JPanel panel = new JPanel(new GridLayout(3,2));
			
			JLabel userlabel = new JLabel("Username: ");
			JTextField usertextField = new JTextField();
			JLabel pwLabel =  new JLabel("Password: ");
			JPasswordField pwField = new JPasswordField();
			
			JButton loginButton = new JButton("Log in");
			
			loginButton.addActionListener(new ActionListener(){
				
				public void actionPerformed(ActionEvent e) {
				String username = usertextField.getText();
				String password = new String(pwField.getPassword());
				
				if(ValidLogin(username, password)) {
					
					cardLayout.show(cardPanel, "HOME PAGE");
					
				}else {
					JOptionPane.showMessageDialog(null, "Invalid Username/Password");
				}
				
			}
		});
				
			panel.add(userlabel);
			panel.add(usertextField);
			panel.add(pwLabel);
			panel.add(pwField);
			panel.add(loginButton);
			
			return panel;
		

		}
		
		/// HOME PAGE OF THE FLIGHT APP

		private JPanel createHomepagePanel() {
			
			
			JPanel panel = new JPanel();
			JLabel label = new JLabel("Flight Home Page");
			
			///user input Field being created
			JTextField userInput = new JTextField();
			userInput.setPreferredSize(new Dimension(250,40));
			
			
			
			///CREATING CITY BOX DISPLAY
			cityComboBox = new JComboBox<>();
	        populateCityComboBox();

	        flightInfoTextArea = new JTextArea(10, 30);
	        flightInfoTextArea.setEditable(false);
	        
	        JLabel chooseFlightlabel = new JLabel("Choose the city you want to go to:");

	        JButton chooseCityButton = new JButton("Select");
	        
	        
	        chooseCityButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                String selectedCity = (String) cityComboBox.getSelectedItem();
	                if (selectedCity != null) {
	                    displayFlights(selectedCity);
	                }
	            }
	        });
	        
	    	///BUTTON CREATION FOR HOME PAGE
			JButton enterButton = new JButton("Enter");
			enterButton.addActionListener(new ActionListener() {
		        public void actionPerformed(ActionEvent e) {
		            String selectedFlight = userInput.getText();
		            
		            try {
		            	int flightNum = Integer.parseInt(selectedFlight);
		            	if(flightNum > 0 && flightNum <= flightcount ) {
		            		
		            int paymentNumber = LuggageRefNum.getInstance().generatePaymentNum();
		            System.out.println("Your payment number is: " + paymentNumber);
		            	} else {
		            		JOptionPane.showMessageDialog(null, "Invalid Flight Number");
		            	}
		        }catch (NumberFormatException ex) {
		        	JOptionPane.showMessageDialog(null, "Enter Numeric Value");
		        }
		           }
		        }); 
		    
			JButton logoutButton = new JButton("Logout");
			
			logoutButton.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					
					cardLayout.show(cardPanel, "LOGIN");
					
				}
				
			});
			
			panel.add(label);
			panel.add(chooseFlightlabel);
			
			panel.add(cityComboBox);
	        panel.add(chooseCityButton);
	        panel.add(flightInfoTextArea);
			
			panel.add(logoutButton);
		;
			

			panel.add(userInput);
			panel.add(enterButton);
			
			
			
			return panel;
			
		}
	
		private void populateCityComboBox() {
	       ////change path if needed to run on your computer
			
	        String filePath = "/Users/squidtemm/git/Flight_Data/departure.csv";

	        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	                String[] row = line.split(",");
	                if (row.length > 2) {
	                    cityComboBox.addItem(row[2]);
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    private void displayFlights(String selectedCity) {
	       
	        String filePath = "/Users/squidtemm/git/Flight_Data/departure.csv";

	        StringBuilder flightsInfo = new StringBuilder();

	        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
	            String line;
	            
	            int flightNum = 1;
	           
	            while ((line = reader.readLine()) != null) {
	                String[] row = line.split(",");
	                if (row.length > 2 && row[2].equalsIgnoreCase(selectedCity)) {
	                	flightsInfo.append("Flight: ").append(flightNum).append("\n");
	                    flightsInfo.append("Scheduled Time: ").append(row[0]).append("\n");
	                    flightsInfo.append("Updated Time: ").append(row[1]).append("\n");
	                    flightsInfo.append("Airline: ").append(row[3]).append("\n\n");
	                    flightNum++;
	                    flightcount++;
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        flightInfoTextArea.setText(flightsInfo.toString());
	    }
		
		
		
		///RUNNING THE FLIGHT APP
		public static void main(String[] args) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					new FlightApp();
				}
			});
		}
		
	}	
	
	
