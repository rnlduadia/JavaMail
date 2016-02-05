/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * InboxMail.java
 *
 * Created on 2013. 6. 16, 오전 12:58:11
 */

package managemail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Administrator
 */
public class OutboxMail extends javax.swing.JPanel {

    private Account acc;
    private DefaultTableModel tableModel;
    Vector fileV;
    private String downAttach;
    
    /** Creates new form InboxMail */
    public OutboxMail(Account _acc) {
        initComponents();
        initSettings();
        this.acc = _acc;
        
        int cnt = 0;
        
        cnt = readOutboxList();
        this.jTextField1.setText(String.valueOf(cnt));
        downAttach = "";
    }

    private void initSettings(){
        ImageIcon icon = new ImageIcon(getClass().getResource("/res/delete.png"));
        this.jButton1.setIcon(icon);
        icon = new ImageIcon(getClass().getResource("/res/save.png"));
        this.jButton2.setIcon(icon);
        this.jButton2.setEnabled(false);

        TableColumn column = jTable1.getColumnModel().getColumn(0);
        column.setPreferredWidth(8);
        column.setCellRenderer(new CenterRenderer());
        
        column = jTable1.getColumnModel().getColumn(1);
        column.setPreferredWidth(28);
        column.setCellRenderer(new CenterRenderer());
        column.setResizable(false);
        
        
        column = jTable1.getColumnModel().getColumn(2);
        column.setPreferredWidth(100);

        column = jTable1.getColumnModel().getColumn(3);
        column.setPreferredWidth(205);

        column = jTable1.getColumnModel().getColumn(4);
        column.setPreferredWidth(90);

        column = jTable1.getColumnModel().getColumn(5);
        column.setPreferredWidth(20);
        
        this.jTextField1.setText("");
        this.jTextField2.setText("");
        this.jTextField3.setText("");
        this.jTextArea1.setText("");

        tableModel = (DefaultTableModel)this.jTable1.getModel();
        while( tableModel.getRowCount() != 0 ) {
            tableModel.removeRow(0);
        }

        fileV = new Vector();
        
    }

    private int readOutboxList(){
        String path = acc.getPath();
        File outPath = new File(path + "/" + acc.getId() + "_Mail/outbox/");
        if( !outPath.exists() ) return 0;

        fileV.clear();
        try{
            File[] outs = outPath.listFiles();
            for( int inx = 0 ; inx < outs.length ; inx++ ){
                File out = outs[inx];
                if( out.isDirectory()) continue;
                if( !out.getName().substring(out.getName().lastIndexOf(".")).equals(".mail")) continue;

                Properties props = new Properties();
                Mail mm = new Mail();
                FileInputStream fis = new FileInputStream(out);
                props.load(fis);
                mm.from = (String) props.getProperty("from");
                mm.to = (String) props.getProperty("to");
                mm.subject = (String) props.getProperty("subject");
                mm.content = (String) props.getProperty("content");
                mm.attach1 = (String) props.getProperty("attach1");
                mm.attach2 = (String) props.getProperty("attach2");
                mm.time = (String) props.getProperty("time");
                mm.name = (String) props.getProperty("name");
                fis.close();
                fileV.add(mm);
                
            }
        }catch(Exception ex){
            System.out.println(ex.toString());
        }

        //arrange
        Mail[] mailList = new Mail[fileV.size()];
        
        for( int inx = 0 ; inx < fileV.size() ; inx++ ){
            Mail mm = (Mail)fileV.get(inx);
            mailList[inx] = mm;
        }

        for( int inx = 0 ; inx < mailList.length-1 ; inx++ ){
            Mail t1 = mailList[inx];
            for( int jnx = inx + 1 ; jnx < mailList.length ; jnx++ ){
                Mail t2 = mailList[jnx];
                if( t1.time.compareTo(t2.time) < 0 ){
                    Mail temp = mailList[inx]; mailList[inx] = mailList[jnx]; mailList[jnx] = temp;
                }
            }
        }
        
        fileV.clear();

        for( int inx = 0 ; inx < mailList.length ; inx++ ){
            fileV.add(mailList[inx]);
        }
       
        //display
        for( int inx = 0 ; inx < fileV.size() ; inx++ ){
            Mail mm = (Mail)fileV.get(inx);
            Vector tmp = new Vector();            
            tmp.add(inx + 1);
            if( !mm.attach1.equals("") )
                tmp.add("첨부");
            else
                tmp.add("");
            tmp.add(mm.to);
            tmp.add(mm.subject);
            tmp.add(mm.time);

            tableModel.addRow(tmp);
        }
        return fileV.size();
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("WKLChongbong", 0, 12));
        jLabel1.setText("전체:");

        jTextField1.setEditable(false);
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel2.setFont(new java.awt.Font("WKLChongbong", 0, 12));
        jLabel2.setText("개");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "첨부파일", "받는 사람", "편지제목", "보낸 날자", "삭제"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel5.setFont(new java.awt.Font("WKLChongbong", 0, 12));
        jLabel5.setText("편지제목");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        jLabel6.setFont(new java.awt.Font("WKLChongbong", 0, 12));
        jLabel6.setText("첨부파일");

        jTextField3.setEditable(false);

        jButton1.setFont(new java.awt.Font("WKLChongbong", 0, 12));
        jButton1.setText("삭제");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextField2.setEditable(false);

        jButton2.setText("내리적재");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 581, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 432, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 581, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2)
                    .addComponent(jLabel6))
                .addContainerGap(25, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        JTable tbl = (JTable)evt.getSource();
        int selrow = tbl.getSelectedRow();
        Mail mm = (Mail)fileV.get(selrow);
        this.jTextArea1.setText(mm.content);
        this.jTextField2.setText(mm.subject);
        this.jTextField3.setText(mm.attach1);
        if( mm.attach1.equals("") )
            this.jButton2.setEnabled(false);
        else
            this.jButton2.setEnabled(true);
        downAttach = mm.attach2;
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        for( int inx = tableModel.getRowCount() -1  ; inx > -1 ; inx-- ){
            Object fl = tableModel.getValueAt(inx, 5);
            if( fl == null ) continue;
            String ff = fl.toString();
            if( ff.equals("true")){
                tableModel.removeRow(inx);
                Mail mm = (Mail)fileV.get(inx);
                File m = new File(acc.getPath() + "/" + acc.getId() + "_Mail/outbox/" + mm.name);
                
                if( m.exists() ) m.delete();                    
                if( !mm.attach1.equals("") ){
                    m = new File(acc.getPath() + "/" + acc.getId() + "_Mail/outbox/attach/" + mm.attach2);
                    if( m.exists() ) m.delete();
                }
                fileV.remove(inx);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        if( this.jTextField2.getText().trim().equals("") ) return;
        if( downAttach.equals("") ) return;

        try{
            String ext = downAttach.substring(downAttach.lastIndexOf(".") +1);
            JFileChooser fc = new JFileChooser();
            File outF = null;
            FileFilter filter =  new ExtensionFilter(null, ext);

            fc.addChoosableFileFilter(filter);
            int returnVal = fc.showSaveDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                outF = fc.getSelectedFile();
            }

            if( outF != null ){

                File srcFile = new File(acc.getPath() + "/" + acc.getId() + "_Mail/outbox/attach/" + downAttach);
                    FileInputStream fis = new FileInputStream(srcFile);
                    FileOutputStream fos = new FileOutputStream(outF + "." +ext);
                    int byt;
                    while((byt = fis.read()) != -1){
                        fos.write(byt);
                    }

                    fos.close();
                    fis.close();
            }
        }catch(Exception ex){
            System.out.println(ex.toString());
        }



    }//GEN-LAST:event_jButton2MouseClicked



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables

}
