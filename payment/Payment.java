package payment;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.formdev.flatlaf.FlatLightLaf;

public class Payment extends JFrame implements MouseListener{
	
	private static final long serialVersionUID = -3641410302364614309L;
	private JLabel bg;
	private JTextField card_number;
	private JButton next,close,proceed,proceed2;
	private JScrollPane viewport;
	private JPanel choosePanel,cardPayment,UPIPayment;
	final private String optionsCategory[] = {"UPI Payement","Card Payment"};
	private JComboBox<String> options;
	public boolean paymentdone = false;
	
	public Payment() {
		
		initializeComponents();
		configureJFrame();
		
	}
	
	private void initializeComponents() {
		
		bg = new JLabel(new ImageIcon("res/payment/payment.png"));
		bg.setBounds(0,0,300,400);
		
		choosePanel = chooseOptionPanel();
		cardPayment = cardPayment();
		UPIPayment = UPIPayment();
		
		viewport = new JScrollPane(choosePanel);
		viewport.setBounds(0,40,300,360);
		viewport.setBorder(null);
		viewport.getViewport().setOpaque(false);
		viewport.setOpaque(false);
		
		close = new JButton(new ImageIcon("res/payment/close.png"));
		close.setBounds(270,10,20,20);
		close.setBorder(null);
		close.addMouseListener(this);
		
		bg.add(close);
		bg.add(viewport);
		
		add(bg);
		
	}
	
	private void configureJFrame() {
		
		setTitle("Payment Gateway");
		setSize(new Dimension(300,400));
		setLocationRelativeTo(null);
		setUndecorated(true);
		setLayout(null);
		setVisible(true);
	}
	
	public JPanel chooseOptionPanel() {
		
		JPanel pnl = new JPanel();
		
		pnl.setPreferredSize(new Dimension(300,360));
		pnl.setOpaque(false);
		pnl.setLayout(null);
		
		JLabel label = new JLabel("Choose an option");
		label.setBounds(30,75,100,20);
		
		options = new JComboBox<>(optionsCategory);
		options.setBounds(30,100,240,40);
		
		next = new JButton("Next");
		next.setBounds(98,160,100,40);
		next.addMouseListener(this);

		pnl.add(label);
		pnl.add(options);
		pnl.add(next);
		
		return pnl;
	}
	
	public JPanel cardPayment() {
		
		JPanel pnl = new JPanel();
		pnl.setPreferredSize(new Dimension(300,360));
		pnl.setLayout(null);
		
		pnl.setOpaque(false);
		
		card_number = new JTextField();
		card_number.setBounds(20,20,260,50);
		card_number.setOpaque(false);
		card_number.setForeground(Color.white);
		
		card_number.setBorder(BorderFactory.createTitledBorder(
				   BorderFactory.createEtchedBorder(), "Debit/Credit Card Number", TitledBorder.LEFT, TitledBorder.TOP,null, Color.WHITE));
		
		JTextField date = new JTextField();
		date.setBounds(20,80,130,50);
		date.setOpaque(false);
		date.setForeground(Color.white);
		
		date.setBorder(BorderFactory.createTitledBorder(
				   BorderFactory.createEtchedBorder(), "Expiry Date", TitledBorder.LEFT, TitledBorder.TOP,null, Color.WHITE));
		
		JTextField cvv = new JTextField();
		cvv.setBounds(160,80,120,50);
		cvv.setOpaque(false);
		cvv.setForeground(Color.white);
		
		cvv.setBorder(BorderFactory.createTitledBorder(
				   BorderFactory.createEtchedBorder(), "CVV", TitledBorder.LEFT, TitledBorder.TOP,null, Color.WHITE));
		
		proceed = new JButton("Proceed");
		proceed.setBounds(100,140,100,40);
		proceed.addMouseListener(this);
		
		
		pnl.add(card_number);
		pnl.add(date);
		pnl.add(cvv);
		pnl.add(proceed);
		
		return pnl;
	}
	
	public JPanel UPIPayment() {
		JPanel pnl = new JPanel();
		
		pnl.setPreferredSize(new Dimension(300,360));
		pnl.setLayout(null);
		pnl.setOpaque(false);
		
		JTextField upi = new JTextField();
		upi.setBounds(20,20,260,50);
		upi.setOpaque(false);
		upi.setForeground(Color.WHITE);
		
		JTextField pin = new JTextField();
		pin.setBounds(20,80,260,50);
		pin.setOpaque(false);
		pin.setForeground(Color.white);
		
		upi.setBorder(BorderFactory.createTitledBorder(
				   BorderFactory.createEtchedBorder(), "UPI ID", TitledBorder.LEFT, TitledBorder.TOP,null, Color.WHITE));
		pin.setBorder(BorderFactory.createTitledBorder(
				   BorderFactory.createEtchedBorder(), "UPI PIN", TitledBorder.LEFT, TitledBorder.TOP,null, Color.WHITE));
		
		proceed2 = new JButton("Proceed");
		proceed2.setBounds(100,140,100,40);
		proceed2.addMouseListener(this);
		
		pnl.add(upi);
		pnl.add(pin);
		pnl.add(proceed2);
		
		return pnl;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() ==  close) {
			this.dispose();
		} else if (e.getSource() == next) {
			if(options.getSelectedIndex() == 0) {
				viewport.setViewportView(UPIPayment);
			} else {
				viewport.setViewportView(cardPayment);
			}
		} else if (e.getSource() == proceed || e.getSource() == proceed2) {
			int result = JOptionPane.showConfirmDialog(this,"Proceed ?");
			if(result == JOptionPane.YES_OPTION) {
				paymentdone = true;
				System.out.println("Payment Done");
				this.dispose();
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		//Do Nothing
	}

	@Override
	public void mouseExited(MouseEvent e) {
		//Do Nothing
	}
	
	public static void main(String args[]) {
		FlatLightLaf.install();
		new Payment();
	}
	
}
