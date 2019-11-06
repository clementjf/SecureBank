package Views;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class SecurityWin {

	private JFrame frame;
	
	private JLabel  securityLabel;
	
	private JPanel infoPanel;
	private JPanel buyPanel;
	private JPanel sellPanel;
	private JList<String> stockList;
	private JList<String> boughtList;
	
	private JLabel stockMarket;
	private JLabel boughtStock;
	
	private JButton buyButton;
	private JButton sellButton;
	
	
	
	
	public SecurityWin() {
		
		
		
		setSecurityWin();
		initSecurityWin();
		
		
		
	}
	
	private void setSecurityWin() {
		frame = new JFrame();
		securityLabel = new JLabel("Security Account");
		
		infoPanel = new JPanel();
		buyPanel = new JPanel();
		sellPanel = new JPanel();
		
		stockList = new JList<>();
		boughtList = new JList<>();
		
		stockMarket = new JLabel("Stock Market");
		boughtStock = new JLabel("Your Stock");
		
		buyButton = new JButton("Buy");
		sellButton = new JButton("Sell");
		
		
	}
	
	
	private void initSecurityWin() {
		frame.setLayout(new GridLayout(3, 1));
		
		frame.add(infoPanel);
		frame.add(buyPanel);
		frame.add(sellPanel);
		
		
		infoPanel.add(securityLabel);
		
		buyPanel.add(stockMarket);
		buyPanel.add(stockList);
		buyPanel.add(buyButton);
		
		
		sellPanel.add(boughtStock);
		sellPanel.add(boughtList);
		sellPanel.add(sellButton);
		
		
		
		buyButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		sellButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		
		frame.setTitle("Stock Market");
		frame.setSize(500, 400);
		frame.setLocation(500, 250);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		
	}
	
	
	
	
}

