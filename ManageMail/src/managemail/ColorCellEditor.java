/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package managemail;

import java.awt.Component;
import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author Administrator
 */
public class ColorCellEditor extends AbstractCellEditor implements TableCellEditor {
    private JLabel newstar = new JLabel();
    private String cellString;
    ImageIcon icon1 = null;
    ImageIcon icon2 = null;
    public ColorCellEditor(){
        icon1 = new ImageIcon(getClass().getResource("/res/test.png"));
        icon2 = new ImageIcon(getClass().getResource("/res/test2.png"));
       //newstar.setIcon(icon);
    }
    public Object getCellEditorValue() {
        return cellString;
    }
    private void changeIcon(String value){
        if(value != null){
            cellString = value;
            if( cellString.equals("T"))
                newstar.setIcon(icon1);
            else
                newstar.setIcon(icon2);
        }
    }
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        changeIcon((String)value);
        return newstar;
    }


}
