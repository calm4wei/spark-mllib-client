package com.cstor.doc;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created on 2016/10/20
 *
 * @author feng.wei
 */
public class ImportWord {

    public void read(String path) throws IOException {
        HWPFDocument hwpfd = new HWPFDocument(new POIFSFileSystem(new FileInputStream(new File(path))));
        Range range = hwpfd.getRange();
        TableIterator it = new TableIterator(range);

        while (it.hasNext()) {
            Table table = it.next();
            for (int i = 0; i < table.numRows(); i++) {
                System.out.println("=========row " + i + "===========");
                TableRow tr = table.getRow(i);
                for (int j = 0; j < tr.numCells(); j++) {
                    TableCell td = tr.getCell(j);

                    for (int k = 0; k < td.numParagraphs(); k++) {
                        Paragraph para = td.getParagraph(k);
                        String s = para.text().trim();
                        if ("".equals(s)) {
                            s = "空白";
                        }
                        System.out.print(s + "  ");
                    }

                }
                System.out.println();
            }
        }
    }


}
