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

import esa.mo.com.impl.consumer.ArchiveConsumerServiceImpl;
import esa.mo.com.impl.provider.ArchivePersistenceObject;
import esa.mo.nmf.ctt.utils.SharedTablePanel;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.structures.Identifier;

/**
 *
 * @author Andreea Pirvulescu
 */
public class LoginTablePanel extends SharedTablePanel {

    public LoginTablePanel(ArchiveConsumerServiceImpl archiveService) {
        super(archiveService);
    }

    @Override
    public void addEntry(Identifier defName, ArchivePersistenceObject comObject) {
        if (comObject == null){
            Logger.getLogger(SharedTablePanel.class.getName()).log(Level.SEVERE, "The table cannot process a null COM Object.");
            return;
        }

        try {
            semaphore.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(SharedTablePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        tableData.addRow(new Object[] {
           defName
        });
        
        semaphore.release();
    }

    @Override
    public void defineTableContent() {
        String[] loginTableCol = new String[] {"User"};
        tableData = new javax.swing.table.DefaultTableModel(new Object[][]{}, loginTableCol) {
            Class[] types = new Class[]{
                java.lang.String.class
            };
        };
        super.getTable().setModel(tableData);
    }
    
}
