package searchextra.lucene.analysis.charfilter;

import com.kohlschutter.boilerpipe.BoilerpipeExtractor;
import com.kohlschutter.boilerpipe.extractors.CommonExtractors;
import org.apache.lucene.analysis.charfilter.BaseCharFilter;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * Created by Ameer Albahem on 17/08/2016.
 *
 *
 * A char filter that uses BiolerPipe to extract the main content of a html page.
 */
public final class BoilerPipeCharFilter extends BaseCharFilter {

    private static BoilerpipeExtractor extractor = CommonExtractors.ARTICLE_EXTRACTOR;

    private Reader transformedInput;
    /**
     * Create a new CharFilter wrapping the provided reader.
     *
     * @param input a Reader, can also be a CharFilter for chaining.
     */
    public BoilerPipeCharFilter(Reader input) {
        super(input);
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        // Buffer all input on the first call.
        if (transformedInput == null) {
            try {
                fill();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return transformedInput.read(cbuf, off, len);
    }


    private void fill() throws Exception {
        StringBuilder buffered = new StringBuilder();
        char [] temp = new char [1024];
        for (int cnt = input.read(temp); cnt > 0; cnt = input.read(temp)) {
            buffered.append(temp, 0, cnt);
        }
        transformedInput = new StringReader(extract(buffered.toString()));
    }

    public String extract(String text) throws Exception {

        return extractor.getText(text);
    }


    public static void main(String[] args) throws IOException {

    }

    protected int correct(int currentOff) {
        return 0;
    }
}