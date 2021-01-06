package utilities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;

public class MixUtilities {
	
	//This function returns text of selected RadioButton
	public static String getSelectedButtonText(ButtonGroup buttonGroup) {
	    for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
	        AbstractButton button = (AbstractButton) buttons.nextElement();

	        if (button.isSelected()) {
	            return button.getText();
	        }
	    }

	    return null;
	}
	
	//Function for resizing Image
	public static BufferedImage resize(BufferedImage img, int newW, int newH) { 
	    Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
	    BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2d = dimg.createGraphics();
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();

	    return dimg;
	}
	
	
	//For Designing JTable UI
	public static void TableDesinger(JTable table) {
		
		table.setFocusable(false);
		table.setShowVerticalLines(false);
		table.setDefaultEditor(Object.class, null);
		table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		table.setRowHeight(25);
		table.setIntercellSpacing(new Dimension(0,0));
		table.setSelectionBackground(Color.LIGHT_GRAY);
		UIManager.getDefaults().put("TableHeader.cellBorder" , BorderFactory.createEmptyBorder(0,0,0,0));
		table.getTableHeader().setOpaque(false);
		table.getTableHeader().setPreferredSize(new Dimension(table.getWidth(),30));
		table.getTableHeader().setFont(new Font("Segoe UI",Font.BOLD,12));
		table.getTableHeader().setBackground(new Color(244,81,105));
		table.getTableHeader().setForeground(Color.WHITE);
		
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.CENTER);
		
		for(int i = 0 ; i < table.getColumnCount() ; i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
		}
		
	}
}
