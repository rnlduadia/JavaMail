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
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;
import javax.mail.*;
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
public class InboxMail extends javax.swing.JPanel {

    private Account acc;
    private DefaultTableModel tableModel;
    Vector fileV;
    int allCnt;
    int newCnt;

    private ManageMailView dlg;
    private Mail currentMail;
    private String downAttach;

    private String[] newName = new String[10000];
    private int newNameCnt = -1;
    
    /** Creates new form InboxMail */
    public InboxMail(Account _acc, ManageMailView _dlg) {
        initComponents();
        initSettings();
        this.acc = _acc;
        this.dlg = _dlg;

        downAttach = "";
        allCnt = 0;
        newCnt = 0;
        this.jTextField1.setText(String.valueOf(allCnt));
        this.jTextField2.setText(String.valueOf(newCnt));
        
        boolean flag = readInbox();
        //if( !flag ) return;

        int existCnt = readInboxList();        
        this.jTextField2.setText(String.valueOf(newCnt));
        this.jTextField1.setText(String.valueOf(existCnt));
    }
    private int readInboxList(){
        String path = acc.getPath();
        File outPath = new File(path + "/" + acc.getId() + "_Mail/inbox/");
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
                mm.content = (String) props.getProperty("content") ;
                mm.attach1 = (String) props.getProperty("attach1");
                mm.attach2 = (String) props.getProperty("attach2");
                mm.time = (String) props.getProperty("time");
                mm.name = (String) props.getProperty("name");
                mm.newflag = "F";
                for( int knx = 0 ; knx <= newNameCnt ; knx++ ){
                    String cp = newName[knx];
                    if( cp.equals(mm.name) ) {
                        mm.newflag = "T";
                        break;
                    }
                }
                
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
        
        for( int inx = 0 ; inx < fileV.size() ; inx++ ){
            Mail mm = (Mail)fileV.get(inx);
            Vector tmp = new Vector();
            tmp.add(mm.newflag);
            tmp.add(inx + 1);
            if( !mm.attach1.equals("") )
                tmp.add("첨부");
            else
                tmp.add("");
            tmp.add(mm.from);
            tmp.add(mm.subject);
            tmp.add(mm.time);

            tableModel.addRow(tmp);
        }
        return fileV.size();
    }
    private String prefixStr(int inp, int dec){
        String ret = String.valueOf(inp);
        if( ret.length() < dec ){
            for( int inx = 0 ; inx < dec - ret.length() ; inx++ ) ret = "0" + ret;
        }
        return ret;
    }
    private boolean readInbox(){
        try{
           Properties props1 = new Properties();
           
           Session session = Session.getDefaultInstance(props1, null);
           session.setDebug(true);

           Store store = session.getStore("pop3");
           //store.connect("190.1.100.215", acc.getId()+"xyz",acc.getId()+acc.getId());
           store.connect(acc.getHost(), acc.getId()+"xyz",acc.getId()+acc.getId());

           //store.connect("190.1.100.215", "sica6", "sica6sica6");
           Folder folder = store.getFolder("INBOX");
           folder.open(Folder.READ_WRITE);

           Message message[] = folder.getMessages();
           for (int i = 0, n = message.length; i < n; i++) {
               newCnt++;
               Mail mm = new Mail();
               String from =  message[i].getFrom()[0].toString();
               mm.from = from.replace("xyz","");

               mm.to = acc.getId() + "@" + acc.getServer() + ".or.kp";
               mm.subject = message[i].getSubject();

               String fromStr = from.substring(0, from.indexOf("xyz@"));
               Date rd = message[i].getSentDate();
               
               String name = String.valueOf(rd.getYear() + 1900) + "-" +
                    prefixStr(rd.getMonth() + 1, 2) + "-" + prefixStr(rd.getDate(), 2) + " " +
                    prefixStr(rd.getHours(), 2) + "-" + prefixStr(rd.getMinutes(),2) + "-" +
                    prefixStr(rd.getSeconds(), 2) + " " + fromStr + ".mail";
               mm.name = name;
               newNameCnt++;
               newName[newNameCnt] = name;
               
               mm.time = String.valueOf(rd.getYear() + 1900) + "/" +
                    prefixStr(rd.getMonth() + 1, 2) + "/" + prefixStr(rd.getDate(), 2) + " " +
                    prefixStr(rd.getHours(), 2) + ":" + prefixStr(rd.getMinutes(),2) + ":" +
                    prefixStr(rd.getSeconds(), 2);
               mm.attach1 = "";
               mm.attach2 = String.valueOf(rd.getYear() + 1900) + "-" +
                    prefixStr(rd.getMonth() + 1, 2) + "-" + prefixStr(rd.getDate(), 2) + " " +
                    prefixStr(rd.getHours(), 2) + "-" + prefixStr(rd.getMinutes(),2) + "-" +
                    prefixStr(rd.getSeconds(), 2);
               String filen = String.valueOf(rd.getYear() + 1900) + "-" +
                    prefixStr(rd.getMonth() + 1, 2) + "-" + prefixStr(rd.getDate(), 2) + " " +
                    prefixStr(rd.getHours(), 2) + "-" + prefixStr(rd.getMinutes(),2) + "-" +
                    prefixStr(rd.getSeconds(), 2) + " " + fromStr + ".mail";
               mm.newflag = "T";
               
               if(message[i].isMimeType("multipart/*")){
                    Multipart multipart = (Multipart)message[i].getContent();

                    String fn = "";
                    for(int j = 0; j <multipart.getCount(); j++){
                        Part p = multipart.getBodyPart(j);
                        if(p.isMimeType("text/plain") || p.isMimeType("text/html")){
                            mm.content = p.getContent().toString();
                            
                        }else{
                            String filename =  p.getFileName();
                            
                            if(filename != null){
                                String ss =  convertUTF(mm.content);
                                int ind1 = ss.indexOf("<attachfiletemp>");
                                int ind2 = ss.indexOf("</attachfiletemp>");
                                
                                fn = ss.substring(ind1 +16 , ind2);

                                mm.attach1 =fn;
                                String ext = fn.substring(fn.lastIndexOf("."));
                                
                                InputStream in = p.getInputStream();
                                String filepath = acc.getPath() + "/" + acc.getId() + "_Mail/inbox/attach/" + mm.attach2 + ext;
                                FileOutputStream fout = new FileOutputStream(new File(filepath));
                                int c = in.read();
                                while(c!=-1){
                                  fout.write(c);
                                  c=in.read();
                                }

                                fout.close();
                                in.close();
                                mm.attach2 += ext;
                            }
                        }
                    }
                    
                }else{
                    mm.content = message[i].getContent().toString();
                    mm.attach1 = "";
                    mm.attach2 = "";
                    
                }
                message[i].setFlag(javax.mail.Flags.Flag.DELETED, true);

                    OutputStream propOut = new FileOutputStream(acc.getPath() + "/" + acc.getId() + "_Mail/inbox/" + filen);
                    Properties props = new Properties();
                    props.setProperty("name", mm.name);
                    props.setProperty("from", mm.from);
                    props.setProperty("to", mm.to);
                    props.setProperty("subject", convertUTF(mm.subject));
                    //props.setProperty("subject", mm.subject);
                    String ss = convertUTF(mm.content);
                    int ind1 = ss.indexOf("<attachfiletemp>");
                    if( ind1 > -1 ){
                        ss = ss.substring(0, ind1);
                    }
                    props.setProperty("content", ss);
                    //props.setProperty("content", mm.content);
                    props.setProperty("attach1", mm.attach1);
                    props.setProperty("attach2", mm.attach2);
                    props.setProperty("time",mm.time);

                    props.store(propOut, "ReceivedMail");
                    propOut.close();
                    propOut = null;
                    props = null;
          }
          
          folder.close(true);
          store.close();
        }catch(Exception e){
          System.out.println(e.toString());
          return false;
        } 
        return true;
    }
    private String convertUTF(String inpStr){
        StringBuffer ret = new StringBuffer("");
        for( int inx = 0 ; inx < inpStr.length() ; inx = inx + 4 ){
            String temp = inpStr.substring(inx, inx + 4);
            int cod = convert10(temp);
            ret.appendCodePoint(cod);
        }
        return ret.toString();
    }
    private int convert10(String inp){
        int ret = 0;
        for( int inx = 0; inx < 4 ; inx++ ){
            int val = 0;
            int p = inp.charAt(inx);
            if( p > 64 ){
                val = p - 55;
            }else{
                val = p - 48;
            }
            ret += (val * Math.pow(16, 3 - inx));            

        }

        return ret;
    }
    
    private void initSettings(){

        ImageIcon icon = new ImageIcon(getClass().getResource("/res/delete.png"));
        this.jButton1.setIcon(icon);

        icon = new ImageIcon(getClass().getResource("/res/reply.png"));
        this.jButton2.setIcon(icon);

        icon = new ImageIcon(getClass().getResource("/res/forward.png"));
        this.jButton3.setIcon(icon);

        icon = new ImageIcon(getClass().getResource("/res/save.png"));
        this.jButton4.setIcon(icon);
        this.jButton4.setEnabled(false);


       
        TableColumn column = jTable1.getColumnModel().getColumn(0);
        column.setMaxWidth(40);
        column.setPreferredWidth(25);
        column.setResizable(false);
        column.setCellRenderer(new ImageCellRenderer());
        
        column = jTable1.getColumnModel().getColumn(1);
        column.setPreferredWidth(10);
        column.setCellRenderer(new CenterRenderer());
        
        column = jTable1.getColumnModel().getColumn(2);
        column.setPreferredWidth(25);
        column.setCellRenderer(new CenterRenderer());

        column = jTable1.getColumnModel().getColumn(3);
        column.setPreferredWidth(100);

        column = jTable1.getColumnModel().getColumn(4);
        column.setPreferredWidth(205);

        column = jTable1.getColumnModel().getColumn(5);
        column.setPreferredWidth(90);

        column = jTable1.getColumnModel().getColumn(6);
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
        currentMail = null;

        for(int inx = 0 ; inx < 10000 ; inx++ ) newName[inx] = "";
        newNameCnt = -1;
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
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jTextField4 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("WKLChongbong", 0, 12));
        jLabel1.setText("전체:");

        jTextField1.setEditable(false);
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel2.setFont(new java.awt.Font("WKLChongbong", 0, 12));
        jLabel2.setText("개");

        jLabel3.setFont(new java.awt.Font("WKLChongbong", 0, 12));
        jLabel3.setText("새 편지:");

        jTextField2.setEditable(false);
        jTextField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel4.setFont(new java.awt.Font("WKLChongbong", 0, 12));
        jLabel4.setText("개");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "No", "첨부파일", "보낸 사람", "편지제목", "보낸 날자", "삭제"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true
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

        jButton1.setFont(new java.awt.Font("WKLChongbong", 0, 12)); // NOI18N
        jButton1.setText("삭제");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("WKLChongbong", 0, 12));
        jButton2.setText("회답");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("WKLChongbong", 0, 12));
        jButton3.setText("전달");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTextField4.setEditable(false);

        jButton4.setText("내리적재");
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 625, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 625, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addGap(164, 164, 164)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 183, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(10, 10, 10)
                        .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jButton4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        for( int inx = tableModel.getRowCount() -1  ; inx > -1 ; inx-- ){
            Object fl = tableModel.getValueAt(inx, 6);
            if( fl == null ) continue;
            String ff = fl.toString();
            if( ff.equals("true")){
                tableModel.removeRow(inx);
                Mail mm = (Mail)fileV.get(inx);
                File m = new File(acc.getPath() + "/" + acc.getId() + "_Mail/inbox/" + mm.name);

                if( m.exists() ) m.delete();
                if( !mm.attach1.equals("") ){
                    m = new File(acc.getPath() + "/" + acc.getId() + "_Mail/inbox/attach/" + mm.attach2);
                    if( m.exists() ) m.delete();
                }
                fileV.remove(inx);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        JTable tbl = (JTable)evt.getSource();
        int selrow = tbl.getSelectedRow();
        Mail mm = (Mail)fileV.get(selrow);
        currentMail = mm;
        this.jTextArea1.setText(mm.content);
        this.jTextField4.setText(mm.subject);
        this.jTextField3.setText(mm.attach1);
        if( mm.attach1.equals("") )
            this.jButton4.setEnabled(false);
        else
            this.jButton4.setEnabled(true);
        downAttach = mm.attach2;
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        
    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        
    }//GEN-LAST:event_jButton3MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if( this.jTextField4.getText().trim().equals("") ) return;
        this.dlg.stateInbox = "Reply";
        this.dlg.selectedMail = currentMail;
        //this.setVisible(false);
        this.dlg.replyAndforward();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if( this.jTextField4.getText().trim().equals("") ) return;
          this.dlg.stateInbox = "Forward";
        this.dlg.selectedMail = currentMail;
        //this.setVisible(false);
        this.dlg.replyAndforward();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked
        if( this.jTextField3.getText().trim().equals("") ) return;
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

                File srcFile = new File(acc.getPath() + "/" + acc.getId() + "_Mail/inbox/attach/" + downAttach);
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
    }//GEN-LAST:event_jButton4MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables

}
