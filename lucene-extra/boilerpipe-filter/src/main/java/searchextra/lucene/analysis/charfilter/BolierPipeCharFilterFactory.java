package searchextra.lucene.analysis.charfilter;

import org.apache.lucene.analysis.CharFilter;
import org.apache.lucene.analysis.util.CharFilterFactory;

import java.io.Reader;
import java.util.Map;

public class BolierPipeCharFilterFactory extends CharFilterFactory {

    public BolierPipeCharFilterFactory(Map<String,String> args) {
        super(args);
    }

    @Override
    public CharFilter create(Reader input) {
        CharFilter charFilter = new BoilerPipeCharFilter(input);
        return charFilter;
    }
}
