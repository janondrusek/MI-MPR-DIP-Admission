/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.mi_mpr_dip.admission.web.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.personal.Document;
import cz.cvut.fit.mi_mpr_dip.admission.domain.personal.DocumentType;
import cz.cvut.fit.mi_mpr_dip.admission.domain.personal.Person;

/**
 *
 * @author DavidCh
 */
public class DataLoader {

    private Collection<Admission> admList = new ArrayList<Admission>();
    private Admission adm;
    private Person person;
    private Document pDoc;
    private DocumentType dt;
    
    public static final void main(String[] args) throws Exception {
		DataLoader dl = new DataLoader();
		dl.readDataFromFile(new File("_data/POCHOVA03022409(1).csv"));
    }

    public void readDataFromFile(File f) {
        try {
            /*  Sets up a file reader to read the file passed on the command
            line one character at a time */
            FileReader fr = new FileReader(f);
            /* Filter FileReader through a Buffered read to read a line at a
            time */
            BufferedReader br = new BufferedReader(fr);
            String line;    // String that holds current file line
            
            // Read headers
            String[] headers = br.readLine().split(",");
            
            for (int i = 0; i < headers.length; i++) {
				System.out.println("h: " + headers[i]);
			}
            
            // Read first admission data line
            line = br.readLine();

            // Read through file one line at time. Print line # and line
            while (line != null) {
            	adm = new Admission();
            	person = new Person();
            	pDoc = new Document();
            	dt = new DocumentType();
            	dt.setName("op");
            	
            	String[] a = line.split(",");
            	adm.setCode(a[0]);
            	adm.setAdmissionId(Long.valueOf(a[1]));
            	person.setFirstname(a[2]);
            	person.setLastname(a[3]);
            	person.setPrefix(a[4]);
            	person.setSuffix(a[5]);
            	person.setBirthIdentificationNumber(a[6]);
            	pDoc.setNumber(a[7]);
            	pDoc.setDocumentType(dt);
            	person.setPhone(a[8]);
            	person.setEmail(a[9]);
            	adm.setCode(a[0]);
            	adm.setCode(a[0]);
            	adm.setCode(a[0]);
            	adm.setCode(a[0]);
            	adm.setCode(a[0]);
            	adm.setCode(a[0]);
            	adm.setCode(a[0]);
            	adm.setCode(a[0]);
            	
                line = br.readLine(); // next clauses line
                break; // only first instance of file
            }

            br.close();
        } catch (ArrayIndexOutOfBoundsException e) {
            /* If no file was passed on the command line, this expception is
            generated. A message indicating how to the class should be
            called is displayed */
            System.out.println("Usage: java ReadFile filename\n");
        } catch (IOException e) {
            // If another exception is generated, print a stack trace
            e.printStackTrace();
        }
    }
}