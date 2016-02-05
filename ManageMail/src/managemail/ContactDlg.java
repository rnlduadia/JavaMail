/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ContactDlg.java
 *
 * Created on 2013. 7. 9, 오후 12:18:18
 */

package managemail;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Properties;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;

public class ContactDlg extends javax.swing.JDialog {

    private ManageMailView view;
    private NewMail newMail;
    
    private DefaultListModel listModel1;
    private DefaultListModel listModel2;

    private int selContactCnt;
    private AccountContact[] selContact;

    private boolean newFlag;
    public int selRow;
    
    /** Creates new form ContactDlg */
    public ContactDlg(java.awt.Frame parent, boolean modal, ManageMailView _view, NewMail _newMail, boolean _new) {
        super(parent, modal);
        initComponents();
        this.view = _view;
        this.newFlag = _new;
        this.newMail = _newMail;
        initWindow();
        initSettings();
        
    }
    private void initWindow(){
        int wndWidth = 530;//425;
        int wndHeight = 473;//300;

        Toolkit tk = getToolkit();
        Dimension screenSize = tk.getScreenSize();
        Dimension windowSize = getSize();

        setBounds((screenSize.width - wndWidth) / 2,
                  (screenSize.height - wndHeight) / 2,
                  wndWidth,
                  wndHeight);
    }
    private void initSettings(){
        this.jTextField4.setText("second");

        selContactCnt = -1;
        selContact = new AccountContact[100];
        selRow = -1;

        arrageList1();
        arrageList2();

        if( view.contactCnt == -1 ){
            this.jButton2.setEnabled(false);
            this.jButton3.setEnabled(false);
            this.jButton4.setEnabled(false);
            this.jButton5.setEnabled(false);
        }

        if( !newFlag ) {
            this.jButton2.setEnabled(false);
            this.jButton3.setEnabled(false);
            this.jButton4.setEnabled(false);
        }
    }
    private void arrageList1(){

        listModel1 = (DefaultListModel)this.jList1.getModel();
        listModel1.removeAllElements();

        for( int inx = 0 ; inx <= view.contactCnt ; inx++ ){
            String str = view.contacts[inx].getName() + "[" + view.contacts[inx].getPosition() + "]";
            listModel1.addElement(str);
        }
    }

    private void arrageList2(){

        listModel2 = (DefaultListModel)this.jList2.getModel();
        listModel2.removeAllElements();

        for( int inx = 0 ; inx <= selContactCnt ; inx++ ){
            String str = selContact[inx].getName() + "[" + selContact[inx].getPosition() + "]";
            listModel2.addElement(str);
        }
    }

    private void makeAccountContact(){
        try {
            File fl = new File(System.getProperty("user.dir") + "/" + "mailAccount.stat");
            OutputStream propOut = new FileOutputStream(fl, false);
            Properties props = new Properties();
            props.setProperty("row", String.valueOf(view.contactCnt));

            for( int inx = 0 ; inx <= view.contactCnt ; inx++ ){
                props.setProperty( inx + "_0", view.contacts[inx].getId());
                props.setProperty( inx + "_1", view.contacts[inx].getServer());
                props.setProperty( inx + "_2", view.contacts[inx].getName());
                props.setProperty( inx + "_3", view.contacts[inx].getPosition());
            }

            props.store(propOut, "Account Contact");
            propOut.close();
            propOut = null;
            props = null;


        } catch (Exception e) {
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jTextField1 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(managemail.ManageMailApp.class).getContext().getResourceMap(ContactDlg.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jList1.setFont(resourceMap.getFont("jList2.font")); // NOI18N
        jList1.setModel(new javax.swing.DefaultListModel());
        jList1.setName("jList1"); // NOI18N
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setName("jButton2"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText(resourceMap.getString("jButton3.text")); // NOI18N
        jButton3.setName("jButton3"); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jList2.setFont(resourceMap.getFont("jList2.font")); // NOI18N
        jList2.setModel(new javax.swing.DefaultListModel());
        jList2.setName("jList2"); // NOI18N
        jList2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jList2);

        jLabel1.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jButton1.setFont(resourceMap.getFont("jButton4.font")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton5.setFont(resourceMap.getFont("jButton4.font")); // NOI18N
        jButton5.setText(resourceMap.getString("jButton5.text")); // NOI18N
        jButton5.setName("jButton5"); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton4.setFont(resourceMap.getFont("jButton4.font")); // NOI18N
        jButton4.setText(resourceMap.getString("jButton4.text")); // NOI18N
        jButton4.setName("jButton4"); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jTextField2.setName("jTextField2"); // NOI18N

        jTextField1.setName("jTextField1"); // NOI18N

        jTextField3.setName("jTextField3"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jTextField4.setText(resourceMap.getString("jTextField4.text")); // NOI18N
        jTextField4.setName("jTextField4"); // NOI18N

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        jButton6.setFont(resourceMap.getFont("jButton4.font")); // NOI18N
        jButton6.setText(resourceMap.getString("jButton6.text")); // NOI18N
        jButton6.setName("jButton6"); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(16, 16, 16)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton3, 0, 0, Short.MAX_VALUE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(99, 99, 99)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButton6)
                                    .addComponent(jButton1)
                                    .addComponent(jButton5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(106, 106, 106)
                                .addComponent(jButton2)
                                .addGap(113, 113, 113)
                                .addComponent(jButton3)))))
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        JList list = (JList)evt.getSource();
        int sel = list.getSelectedIndex();
        int clickCnt = evt.getClickCount();
        if( clickCnt == 1){
            AccountContact acc = view.contacts[sel];
            this.jTextField1.setText(acc.getName());
            this.jTextField2.setText(acc.getPosition());
            this.jTextField3.setText(acc.getId());
            this.jTextField4.setText(acc.getServer());
            selRow = sel;
            
            
        }
        if( clickCnt == 2 ){
            AccountContact acc = view.contacts[sel];
            String id1 = acc.getId();
            String server1 = acc.getServer();
            boolean exist = false;
            for( int jnx = 0 ; jnx <= selContactCnt ; jnx++){
                AccountContact acc1 = selContact[jnx];
                String id2 = acc1.getId();
                String server2 = acc1.getServer();
                if( id1.equals(id2) && server1.equals(server2)){
                    exist = true;
                    break;
                }
            }
            if( !exist ){
                selContactCnt++;
                selContact[selContactCnt] = new AccountContact();
                selContact[selContactCnt].setId(acc.getId());
                selContact[selContactCnt].setServer(acc.getServer());
                selContact[selContactCnt].setName(acc.getName());
                selContact[selContactCnt].setPosition(acc.getPosition());

                arrageList2();
            }
        }
}//GEN-LAST:event_jList1MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int[] selInx = this.jList1.getSelectedIndices();
        if( selInx.length == 0 ) return;

        for( int inx = 0 ; inx < selInx.length ; inx++ ){
            int sel = selInx[inx];
            AccountContact acc = view.contacts[sel];
            String id1 = acc.getId();
            String server1 = acc.getServer();
            boolean exist = false;
            for( int jnx = 0 ; jnx <= selContactCnt ; jnx++){
                AccountContact acc1 = selContact[jnx];
                String id2 = acc1.getId();
                String server2 = acc1.getServer();
                if( id1.equals(id2) && server1.equals(server2)){
                    exist = true;
                    break;
                }
            }
            if( !exist ){
                selContactCnt++;
                selContact[selContactCnt] = new AccountContact();
                selContact[selContactCnt].setId(acc.getId());
                selContact[selContactCnt].setServer(acc.getServer());
                selContact[selContactCnt].setName(acc.getName());
                selContact[selContactCnt].setPosition(acc.getPosition());

                arrageList2();
            }

        }
}//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int[] selInx = this.jList2.getSelectedIndices();
        if( selInx.length == 0 ) return;

        AccountContact[] tempac = new AccountContact[selContactCnt - selInx.length + 1];
        for( int inx = 0 ; inx < selInx.length ; inx++ ){
            int ii = selInx[inx];
            selContact[ii].setId("");
        }
        int c = -1;
        for( int inx = 0 ; inx <= selContactCnt ; inx++ ){
            if( selContact[inx].getId().equals("") ) continue;
            AccountContact acc = selContact[inx];
            c++;
            tempac[c] = new AccountContact();
            tempac[c].setId(acc.getId());
            tempac[c].setServer(acc.getServer());
            tempac[c].setName(acc.getName());
            tempac[c].setPosition(acc.getPosition());
        }

        selContactCnt = -1;
        selContact = new AccountContact[100];
        for( int inx = 0 ; inx < tempac.length ; inx++ ){
            selContactCnt++;
            selContact[selContactCnt] = new AccountContact();
            selContact[selContactCnt].setId(tempac[inx].getId());
            selContact[selContactCnt].setServer(tempac[inx].getServer());
            selContact[selContactCnt].setName(tempac[inx].getName());
            selContact[selContactCnt].setPosition(tempac[inx].getPosition());
        }
        arrageList2();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jList2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList2MouseClicked
        JList list = (JList)evt.getSource();
        int sel = list.getSelectedIndex();
        int clickCnt = evt.getClickCount();
        if( clickCnt == 2 ){
            AccountContact[] tempac = new AccountContact[selContactCnt ];

            selContact[sel].setId("");

            int c = -1;
            for( int inx = 0 ; inx <= selContactCnt ; inx++ ){
                if( selContact[inx].getId().equals("") ) continue;
                AccountContact acc = selContact[inx];
                c++;
                tempac[c] = new AccountContact();
                tempac[c].setId(acc.getId());
                tempac[c].setServer(acc.getServer());
                tempac[c].setName(acc.getName());
                tempac[c].setPosition(acc.getPosition());
            }

            selContactCnt = -1;
            selContact = new AccountContact[100];
            for( int inx = 0 ; inx < tempac.length ; inx++ ){
                selContactCnt++;
                selContact[selContactCnt] = new AccountContact();
                selContact[selContactCnt].setId(tempac[inx].getId());
                selContact[selContactCnt].setServer(tempac[inx].getServer());
                selContact[selContactCnt].setName(tempac[inx].getName());
                selContact[selContactCnt].setPosition(tempac[inx].getPosition());
            }
            arrageList2();
        }
}//GEN-LAST:event_jList2MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String name = this.jTextField1.getText().trim();
        String pos = this.jTextField2.getText().trim();
        String id = this.jTextField3.getText().trim();
        String server = this.jTextField4.getText().trim();
        if( name.equals("") || pos.equals("") || id.equals("") || server.equals("") ){
            JOptionPane.showMessageDialog(null,"정확히 입력하십시오.", "알림",JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if( selRow == -1 ){
            for( int inx = 0 ; inx <= view.contactCnt ; inx++ ){
                String idd = view.contacts[inx].getId();
                String serverd = view.contacts[inx].getServer();
                if( idd.equals(id) && serverd.equals(server)){
                    JOptionPane.showMessageDialog(null,"사용자ID가 중복되였습니다.", "알림",JOptionPane.INFORMATION_MESSAGE);
                    this.jTextField3.setText("");
                    this.jTextField4.setText("second");
                    return;
                }
            }
            view.contactCnt++;
            view.contacts[view.contactCnt] = new AccountContact();
            view.contacts[view.contactCnt].setName(name);
            view.contacts[view.contactCnt].setId(id);
            view.contacts[view.contactCnt].setServer(server);
            view.contacts[view.contactCnt].setPosition(pos);

            makeAccountContact();

            String str = view.contacts[view.contactCnt].getName() + "[" + view.contacts[view.contactCnt].getPosition() + "]";
            listModel1.addElement(str);
        }else{
                view.contacts[selRow].setName(name);
                view.contacts[selRow].setId(id);
                view.contacts[selRow].setServer(server);
                view.contacts[selRow].setPosition(pos);

                this.makeAccountContact();
                arrageList1();
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        int[] selInx = this.jList1.getSelectedIndices();
        if( selInx.length == 0 ) return;

        AccountContact[] tempac = new AccountContact[view.contactCnt - selInx.length + 1];
        for( int inx = 0 ; inx < selInx.length ; inx++ ){
            int ii = selInx[inx];
            view.contacts[ii].setId("");
        }
        int c = -1;
        for( int inx = 0 ; inx <= view.contactCnt ; inx++ ){
            if( view.contacts[inx].getId().equals("") ) continue;
            AccountContact acc = view.contacts[inx];
            c++;
            tempac[c] = new AccountContact();
            tempac[c].setId(acc.getId());
            tempac[c].setServer(acc.getServer());
            tempac[c].setName(acc.getName());
            tempac[c].setPosition(acc.getPosition());
        }

        view.contactCnt = -1;
        view.contacts = new AccountContact[100];
        for( int inx = 0 ; inx < tempac.length ; inx++ ){
            view.contactCnt++;
            view.contacts[view.contactCnt] = new AccountContact();
            view.contacts[view.contactCnt].setId(tempac[inx].getId());
            view.contacts[view.contactCnt].setServer(tempac[inx].getServer());
            view.contacts[view.contactCnt].setName(tempac[inx].getName());
            view.contacts[view.contactCnt].setPosition(tempac[inx].getPosition());
        }

        arrageList1();
        makeAccountContact();
}//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if( selContactCnt > -1 ) {
            String[] str = new String[selContactCnt+1];
            for( int inx = 0 ; inx <= selContactCnt ; inx++ ){
                AccountContact acc = selContact[inx];
                str[inx] = (acc.getId() + "@" + acc.getServer() + ".or.kp");
                
            }

            this.newMail.writeContact(str);
        }

        this.setVisible(false);
}//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        selRow = -1;
         this.jTextField1.setText("");
        this.jTextField2.setText("");
        this.jTextField3.setText("");
        this.jTextField4.setText("second");
        this.jTextField3.setEditable(true);
        this.jTextField4.setEditable(true);
    }//GEN-LAST:event_jButton6ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ContactDlg dialog = new ContactDlg(new javax.swing.JFrame(), true, null, null, false);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JList jList1;
    private javax.swing.JList jList2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables

}