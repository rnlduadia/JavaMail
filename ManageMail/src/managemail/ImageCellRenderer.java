/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package managemail;

import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author LarryHoffman
 */
public class ImageCellRenderer extends JLabel implements TableCellRenderer {
    private JLabel newstar = new JLabel();
    private String cellString;
    ImageIcon icon1 = null;
    ImageIcon icon2 = null;
    
    public ImageCellRenderer(){
        icon1 = new ImageIcon(getClass().getResource("/res/close.png"));
        icon2 = new ImageIcon(getClass().getResource("/res/open.png"));
        setHorizontalAlignment(SwingConstants.CENTER);
    }

    private void chageIcon(String value) {
        if(value != null){
            cellString = value;
            if( cellString.equals("T"))
                newstar.setIcon(icon1);
            else
                newstar.setIcon(icon2);
        }
        
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        chageIcon((String)value);        
        return newstar;
    }
    
    
    
}