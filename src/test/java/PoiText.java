import com.cstor.doc.ImportWord;
import org.junit.Test;

import java.io.IOException;

/**
 * Created on 2016/10/20
 *
 * @author feng.wei
 */
public class PoiText {

    @Test
    public void test_poi_doc() throws IOException {
        long t1 = System.currentTimeMillis();
        ImportWord importWord = new ImportWord();
        importWord.read("E:\\personal\\junligong\\d.doc");
        long t2 = System.currentTimeMillis();
        System.out.println("t2-t1=" + (t2 - t1));
    }

    @Test
    public void test_rn(){
        String s = "\r\n";
        System.out.println(s);
    }
}
