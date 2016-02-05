/*
 * ManageMailView.java
 */

package managemail;

import com.jtattoo.plaf.mcwin.McWinLookAndFeel;
import java.awt.Dimension;
import java.awt.Toolkit;
import org.jdesktop.application.Action;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Properties;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.MetalLookAndFeel;

/**
 * The application's main frame.
 */
public class ManageMailView extends FrameView {

    private Properties props;
    public Account curUser;

    public NewMail newMail;
    public InboxMail inbox;
    public OutboxMail outbox;
    public Login login;
    

    public String stateInbox;
    public Mail selectedMail;

    public Account[] users;
    public int usersCnt;

    public AccountContact[] contacts;
    public int contactCnt;

    public String contactString;
    
    public ManageMailView(SingleFrameApplication app) {
        super(app);
        try{
            try
            {
                UIManager.setLookAndFeel(new McWinLookAndFeel());
            }
            catch(UnsupportedLookAndFeelException e)
            {
                System.err.println("Cannot change to Synth");
                UIManager.setLookAndFeel(new MetalLookAndFeel());
            }
        }catch(Exception ex){}
        initComponents();

        java.awt.Image img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/res/icon.png"));
        getFrame().setIconImage(img);

        ImageIcon icon = new ImageIcon(getClass().getResource("/res/banner.jpg"));
        this.jLabel1.setIcon(icon);
        
        icon = new ImageIcon(getClass().getResource("/res/new.png"));
        this.jButton1.setIcon(icon);

        icon = new ImageIcon(getClass().getResource("/res/inbox.png"));
        this.jButton2.setIcon(icon);

        icon = new ImageIcon(getClass().getResource("/res/outbox.png"));
        this.jButton3.setIcon(icon);

        icon = new ImageIcon(getClass().getResource("/res/user.png"));
        this.jButton4.setIcon(icon);

        icon = new ImageIcon(getClass().getResource("/res/contact.png"));
        this.jButton5.setIcon(icon);

        Toolkit tk = getFrame().getToolkit();
        Dimension screenSize = new Dimension(832, tk.getScreenSize().height - 150);
        getFrame().setPreferredSize(screenSize);
        getFrame().setResizable(false);


        
        initWindow();
        initSettings();
        curUser = new Account();
        //System.out.println(System.getProperty("user.dir") + "/" + "mailAccount.stat");
        //readAccountAndDirect();

        this.jButton1.setEnabled(false);
        this.jButton2.setEnabled(false);
        this.jButton3.setEnabled(false);
        this.jButton5.setEnabled(false);
        
        login = new Login(this);
        this.jScrollPane1.setViewportView(this.login);
        //inbox = new InboxMail(curUser, this);
        //this.jScrollPane1.setViewportView(this.inbox);
       
    }

    @Action
    
    private void initSettings(){
        stateInbox = "Read";
        selectedMail = null;

        users = new Account[100];
        usersCnt = -1;
        
        readUserInfo();

        contacts = new AccountContact[100];
        contactCnt = -1;

        readAccountContact();

        contactString = "";

    }
    public void readUserInfo(){
        
        File ff = new File(System.getProperty("user.dir"));
        File[] fl = ff.listFiles();
        for( int inx = 0 ; inx < fl.length ; inx++ ){
            File ff1 = fl[inx];
            if( !ff1.isDirectory() && ff1.getName().indexOf(".usr") > -1){
                UserInfo temp = UserInfo.load(ff1.getAbsolutePath());
                Account tempuser = new Account();

                tempuser.setId((String)temp.get(temp.USER_ID));
                tempuser.setPwd((String)temp.get(temp.USER_PWD));
                tempuser.setName((String)temp.get(temp.USER_NAME));
                tempuser.setPosition((String)temp.get(temp.USER_DEPT));
                tempuser.setServer((String)temp.get(temp.MAIL_HOST));
                tempuser.setPath((String)temp.get(temp.MAIL_DIR));
                tempuser.setFileinfo((String)temp.get(temp.FILE_INFO));
                
                usersCnt++;
                users[usersCnt] = tempuser;
            }
        }
    }
    public void writeContact(){
        
    }
    private void readAccountContact(){

        
        File fl = new File(System.getProperty("user.dir") + "/" + "mailAccount.stat");
        if( fl.exists() ) {
            
            try {
                props = new Properties();
                props.load(new FileInputStream(System.getProperty("user.dir") + "/" + "mailAccount.stat"));
                contactCnt = new Integer(((String) props.getProperty("row"))).intValue();

                for (int r = 0; r <= contactCnt; ++r) {
                    contacts[r] = new AccountContact();
                    contacts[r].setId((String) props.getProperty(r + "_0"));
                    contacts[r].setServer((String) props.getProperty(r + "_1"));
                    contacts[r].setName((String) props.getProperty(r + "_2"));
                    contacts[r].setPosition((String) props.getProperty(r + "_3"));                    
                }

            } catch (Exception E) {
                System.out.println(E.toString());
            }
            
        }

        for( int inx = 0 ; inx <= usersCnt ; inx++ ){
            Account acc = users[inx];
            String id1 = acc.getId();
            String server1 = acc.getServer();
            boolean existf  = false;
            for( int jnx = 0 ; jnx <= contactCnt ; jnx++ ){
                AccountContact con = contacts[jnx];
                String id2 = con.getId();
                String server2 = con.getServer();
                if( id1.equals(id2) && server1.equals(server2) ){
                    existf = true;
                    break;
                }
            }
            if( !existf ){
                contactCnt++;
                contacts[contactCnt] = new AccountContact();
                contacts[contactCnt].setId(acc.getId());
                contacts[contactCnt].setServer(acc.getServer());
                contacts[contactCnt].setName(acc.getName());
                contacts[contactCnt].setPosition(acc.getPosition());
            }
        }


        try {            
            OutputStream propOut = new FileOutputStream(fl, false);
            Properties props = new Properties();
            props.setProperty("row", String.valueOf(contactCnt));

            for( int inx = 0 ; inx <= contactCnt ; inx++ ){
                props.setProperty( inx + "_0", contacts[inx].getId());
                props.setProperty( inx + "_1", contacts[inx].getServer());
                props.setProperty( inx + "_2", contacts[inx].getName());
                props.setProperty( inx + "_3", contacts[inx].getPosition());
            }

            props.store(propOut, "Account Contact");
            propOut.close();
            propOut = null;
            props = null;


        } catch (Exception e) {
        }

    }
    private void initWindow(){
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jLabel1 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        mainPanel.setName("mainPanel"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(managemail.ManageMailApp.class).getContext().getResourceMap(ManageMailView.class);
        jButton1.setFont(resourceMap.getFont("jButton1.font")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setActionCommand(resourceMap.getString("jButton1.actionCommand")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(resourceMap.getFont("jButton2.font")); // NOI18N
        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setName("jButton2"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(resourceMap.getFont("jButton3.font")); // NOI18N
        jButton3.setText(resourceMap.getString("jButton3.text")); // NOI18N
        jButton3.setName("jButton3"); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jLabel1.setName("jLabel1"); // NOI18N

        jButton4.setFont(resourceMap.getFont("jButton4.font")); // NOI18N
        jButton4.setText(resourceMap.getString("jButton4.text")); // NOI18N
        jButton4.setActionCommand(resourceMap.getString("jButton4.actionCommand")); // NOI18N
        jButton4.setName("jButton4"); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setFont(resourceMap.getFont("jButton5.font")); // NOI18N
        jButton5.setText(resourceMap.getString("jButton5.text")); // NOI18N
        jButton5.setName("jButton5"); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE))
                        .addGap(10, 10, 10))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 653, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 796, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        setComponent(mainPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        newMail = new NewMail(curUser, "NEW", null, this);
        this.jScrollPane1.setViewportView(this.newMail);
}//GEN-LAST:event_jButton1ActionPerformed
public void replyAndforward(){
        if( stateInbox.equals("Reply")){
            newMail = new NewMail(curUser, "REPLY", selectedMail, this);
            this.jScrollPane1.setViewportView(this.newMail);
        }else if(stateInbox.equals("Forward")){
            newMail = new NewMail(curUser, "FORWARD", selectedMail, this);
            this.jScrollPane1.setViewportView(this.newMail);
        }
    }
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        inbox = new InboxMail(curUser, this);
        this.jScrollPane1.setViewportView(this.inbox);

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        outbox = new OutboxMail(curUser);
        this.jScrollPane1.setViewportView(this.outbox);
}//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        this.jButton1.setEnabled(false);
        this.jButton2.setEnabled(false);
        this.jButton3.setEnabled(false);
        this.jButton5.setEnabled(false);
        login = new Login(this);
        this.jScrollPane1.setViewportView(this.login);
        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        ContactDlg contact = new ContactDlg(null, true, this, null, false);
        contact.setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton jButton1;
    public javax.swing.JButton jButton2;
    public javax.swing.JButton jButton3;
    public javax.swing.JButton jButton4;
    public javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    public javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel mainPanel;
    // End of variables declaration//GEN-END:variables

    
}
