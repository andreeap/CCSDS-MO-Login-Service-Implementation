/* ----------------------------------------------------------------------------
 * Copyright (C) 2015      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : ESA NanoSat MO Framework
 * ----------------------------------------------------------------------------
 * Licensed under the European Space Agency Public License, Version 2.0
 * You may not use this file except in compliance with the License.
 *
 * Except as expressly set forth in this License, the Software is provided to
 * You on an "as is" basis and without warranties of any kind, including without
 * limitation merchantability, fitness for a particular purpose, absence of
 * defects or errors, accuracy or non-infringement of intellectual property rights.
 * 
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 * ----------------------------------------------------------------------------
 */
package esa.mo.nmf.ctt.services.common;

import esa.mo.common.impl.consumer.LoginConsumerServiceImpl;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.common.login.body.LoginResponse;
import org.ccsds.moims.mo.common.login.structures.Profile;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.structures.Identifier;

/**
 *
 * @author Andreea Pirvulescu
 */
public class LoginConsumerPanel extends javax.swing.JPanel {

    private final LoginConsumerServiceImpl serviceCommonLogin;
    private final LoginTablePanel loginTable;
    private boolean authenticationStatus = false;
    public String[] data = new String[3];

    /**
     * Creates new form LoginConsumerPanel
     *
     * @param serviceCommonLogin
     */
    public LoginConsumerPanel(LoginConsumerServiceImpl serviceCommonLogin) {
        initComponents();
        this.serviceCommonLogin = serviceCommonLogin;
        loginTable = new LoginTablePanel(serviceCommonLogin.getCOMServices().getArchiveService());
        this.logoutButton.setVisible(false);
    }

    public boolean isAuthenticated() {
        return authenticationStatus;
    }

    public void checkData() {
        Identifier username = new Identifier(this.data[0]);
        Long role = Long.valueOf(this.data[1]);
        String password = this.data[2];

        Profile profile = new Profile(username, role);

        LoginResponse loginResponse;
        try {
            loginResponse = this.serviceCommonLogin.getLoginStub().login(profile, password);
            if (loginResponse != null) {
                authenticationStatus = true;
            }
        } catch (MALInteractionException ex) {
            Logger.getLogger(LoginConsumerPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MALException ex) {
            Logger.getLogger(LoginConsumerPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (this.isAuthenticated()) {
            this.loginButton.setVisible(false);
            this.logoutButton.setVisible(true);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        loginButton = new javax.swing.JButton();
        logoutButton = new javax.swing.JButton();

        loginButton.setText("Login");
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });

        logoutButton.setText("Logout");
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addComponent(loginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(logoutButton)
                .addContainerGap(213, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(loginButton)
                    .addComponent(logoutButton))
                .addContainerGap(266, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
        // TODO add your handling code here: 
        LoginFrame lfr = new LoginFrame(this);
        lfr.setVisible(true);
    }//GEN-LAST:event_loginButtonActionPerformed

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButtonActionPerformed
        
        try {
            this.serviceCommonLogin.getLoginStub().logout();
        } catch (MALInteractionException ex) {
            Logger.getLogger(LoginConsumerPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MALException ex) {
            Logger.getLogger(LoginConsumerPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.authenticationStatus = false;
        this.serviceCommonLogin.closeConnection();
        this.getParent().removeAll();
        
    }//GEN-LAST:event_logoutButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton loginButton;
    private javax.swing.JButton logoutButton;
    // End of variables declaration//GEN-END:variables
}
