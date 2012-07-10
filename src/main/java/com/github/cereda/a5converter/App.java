package com.github.cereda.a5converter;

import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * A5 converter.
 * 
 * @author Paulo Roberto Massa Cereda
 */
public class App {
    
    public static void main( String[] args ) throws FileNotFoundException, IOException, DocumentException {
        System.out.println( "A5 converter");
        if (args.length != 1) {
            System.err.println("I need a PDF file.");
            System.exit(1);
        }
        else {
            if (!args[0].toLowerCase().endsWith(".pdf")) {
                System.err.println("I need a PDF file.");
                System.exit(1);
            }
        }
        createA5(args[0]);
    }
    
    public static void createA5(String input) throws FileNotFoundException, IOException, DocumentException {
        
        PdfRectangle page1 = new PdfRectangle(PageSize.A5);
        PdfRectangle page2 = new PdfRectangle(PageSize.A5.getWidth(),0,PageSize.A5.getWidth()*2, PageSize.A5.getHeight());
        
        getPages(input, extractName(input) + "-1.pdf", page1);
        getPages(input, extractName(input) + "-2.pdf", page2);
        
    }
    
    private static void getPages(String input, String output, PdfRectangle page) throws IOException, DocumentException {
        
        PdfReader reader = new PdfReader(new FileInputStream(input));
        int pages = reader.getNumberOfPages();
        
        PdfDictionary dictionary;
        
        for (int i = 1; i <= pages; i++) {
            dictionary = reader.getPageN(i);
            dictionary.put(PdfName.CROPBOX, page);
            
        }
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(output));
        stamper.close();
        reader.close();
    }
    
    private static String extractName(String filename) {
        return (filename.substring(0, filename.length() - 4));
    }
    
}
