package searchextra.lucene.analysis.charfilter;
import com.kohlschutter.boilerpipe.BoilerpipeExtractor;
import com.kohlschutter.boilerpipe.extractors.CommonExtractors;
import junit.framework.Assert;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.StringReader;
import java.util.HashMap;

/**
 * Created by Ameer Albahem on 17/08/2016.
 */
public class BoilerPipeCharFilterTest {

    @Test
    public void extract() throws Exception {

        final BoilerpipeExtractor extractor = CommonExtractors.ARTICLE_EXTRACTOR;
        Document document = Jsoup.connect("http://lucene.apache.org").get();
        String text = extractor.getText(document.html());
        StringReader reader = new StringReader(document.html());
        Analyzer analyzer = CustomAnalyzer.builder().addCharFilter(BolierPipeCharFilterFactory.class,new HashMap<String, String>()).withTokenizer("keyword").build();
        TokenStream ts = analyzer.tokenStream("text",reader);
        StringBuilder tokenList = new StringBuilder();
        ts.reset();
        while (ts.incrementToken()) {

            CharTermAttribute charTermAttribute = ts.getAttribute(CharTermAttribute.class);

            tokenList.append(charTermAttribute.toString());
        }

        Assert.assertEquals(text,tokenList.toString());
    }
}