package merge_pdf_sep;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Merge a list of pdf files inserting a separator page
 * <br>
 * Its a front/end to doMerge code (found on Internet and GitHub) to insert separator pages when merging pdf files. Used to insert marker pages into piles of production printer printouts to separate jobs
 * <p>
 * Usage:
 * <br>
 * java -jar merge_pdf_sep.jar {@literal <}filelist.txt{@literal >} {@literal <}separator filename{@literal >} x
 * <br>
 * Parameters:
 * <br> {@literal <}filelist.txt{@literal >} = text file containing filename list of pdf files to be merged (must end with an empty line)
 * <br> {@literal <}separator filename{@literal >} = page separator pdf file, same size as files to be merged
 * <br>
 * x = insert separator every x files merged.
 * <br>
 *
 * @see <a https://github.com/gios-asu/html-validator/blob/master/src/com/asu/gios/PDFMerge.java>https://github.com/gios-asu/html-validator/blob/master/src/com/asu/gios/PDFMerge.java</a>
 * @author fulvio aprile 2017
 * @version 1.0 Updated to insert it in GitHub on January 2018
 */
public class Merge_pdf_sep {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {

        String merged = "";
        int insert = 0;
    
        if (args.length < 3) {
            System.out.println("**********************************************");
            System.out.println("* Java program to merge pdf with separators  *");
            System.out.println("*********************************************");
            System.out.println("Proper Usage is:");
            System.out.println("       java -jar merge_pdf_sep.jar filelist.txt \"separator filename\" x");
	    System.out.println("       where:");
	    System.out.println("      	    filelist.txt          = filelist of pdf to be merged");
	    System.out.println("            separator filename    = pdf separator");
	    System.out.println("            x                     = separator inserting step");
	    System.out.println("            outputfilname         = merged output filename (optional)");
	    System.exit(0);
        } else if  (args.length == 4){
           merged = args[3];  // Output filename
        }

        String filelist = args[0];     // pdf file list
        String separator = args[1];    // separator
        String Insert_each = args[2];  // insert after each args[2] files


        // echo args[] params
        System.out.println("Filename list : " + filelist);
        System.out.println("Separator page: " + separator);
        System.out.println("Insert every  : " + Insert_each+" files");

        // Open input list filenames and get number of input files to be processed
        File listfile= new File(filelist);
        int num_input_filenames = countLines(listfile);
                
        List<InputStream> list = new ArrayList<InputStream>();
        try {
            // Create an internal array with the list of filenames and the inserted separator filename as required
            try (BufferedReader input_filename = new BufferedReader(new FileReader(filelist))) {
                String file;
                int i;
                for (i = 1; i < num_input_filenames; i++) {
                    // get line filename
                    file = input_filename.readLine();
                    System.out.println("file: " + file);
                    
                    // strip .pdf extension
                    String[] parts = file.split(".pdf");
                    if (args.length <= 3){
                        merged += parts[0];
                    }
                    
                    // add to array
                    list.add(new FileInputStream(new File(file)));
                    insert = insert + 1;

                    // if required insert separator filename into array
                    if (insert == Integer.valueOf(Insert_each)) {
                        list.add(new FileInputStream(new File(separator)));
                        insert = 0;
                    }
                }
                file = input_filename.readLine();
                System.out.println("file: " + file);
                String[] parts = file.split(".pdf");
                // Compose output filename
                if (args.length <= 3) {
                    merged += "_" + parts[0];
                }
                list.add(new FileInputStream(new File(file)));
            }
            // Initialize output merged pdf file
            OutputStream out = new FileOutputStream(new File(merged + ".pdf"));
            // Merge files
            doMerge(list, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
    }
   }

    /**
     * Merge multiple pdf into one pdf
     * <P>
     * I've found this code on Internet also on GitHub at https://github.com/gios-asu/html-validator/blob/master/src/com/asu/gios/PDFMerge.java
     * @param list list of pdf filenames to be merged
     * @param outfile output merged pdf file
     * @throws DocumentException
     * @throws IOException
     */
    public static void doMerge(List<InputStream> list, OutputStream outfile)
            throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, outfile);
        document.open();
        PdfContentByte cb = writer.getDirectContent();

        for (InputStream in : list) {
            PdfReader reader = new PdfReader(in);
            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                document.newPage();
                //import the page from source pdf
                PdfImportedPage page = writer.getImportedPage(reader, i);
                //add the page to the destination pdf
                cb.addTemplate(page, 0, 0);
            }
        }
        outfile.flush();
        document.close();
        outfile.close();
    }

    /**
     *
     * @param aFile file to count lines
     * @return number of lines of aFile
     * @throws IOException
     */
    @SuppressWarnings("empty-statement")
    public static int countLines(File aFile) throws IOException {
        LineNumberReader reader = null;
        try {
            reader = new LineNumberReader(new FileReader(aFile));
            while ((reader.readLine()) != null);
            return reader.getLineNumber();
        } catch (Exception ex) {
            return -1;
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

}
