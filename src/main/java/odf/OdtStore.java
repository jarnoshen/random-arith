package odf;

import java.io.Closeable;
import java.util.Iterator;
import java.util.List;

import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.text.Paragraph;

import com.google.common.base.Preconditions;

public class OdtStore implements Closeable
{
    private TextDocument odt;
    private String targetPath;
    
    public OdtStore(String targetPath) throws Exception
    {
        odt = TextDocument.loadDocument(
                OdtStore.class.getResourceAsStream("/template.odt"));
        Preconditions.checkArgument(targetPath.endsWith(".odt"));
        this.targetPath = targetPath;
    }
    
    public static void main(String[] args) throws Exception
    {
//        try (OdtStore t = new OdtStore("hello.odt"))
//        {
//            List<String> newContents = Collections.nCopies(72 * 4, "hello");
//            t.replaceContentAndSave(newContents);
//        }
        System.out.println(OdtStore.class.getResource("/template.odt").toURI());
    }
    
    public void replaceContentAndSave(List<String> contents) throws Exception
    {
        Iterator<String> contentIt = contents.iterator();
        Iterator<Paragraph> paraIt = odt.getParagraphIterator();
        
        do
        {
            Preconditions.checkArgument(paraIt.hasNext());
            
            String content = contentIt.next();
            Paragraph para = paraIt.next();
            
            para.removeTextContent();
            para.appendTextContent(content, true);
        } while (contentIt.hasNext());
        
        odt.save(targetPath);
    }


    @Override
    public void close() 
    {
        try
        {
            if (odt != null)
            {
                odt.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
