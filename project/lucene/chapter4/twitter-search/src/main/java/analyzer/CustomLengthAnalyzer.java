package analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;

/**
 * Created by hwang on 2018. 5. 28..
 */
public class CustomLengthAnalyzer extends Analyzer{

    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        final Tokenizer source = new WhitespaceTokenizer();
        TokenStream result = new CustomLengthFilter( source, 3, Integer.MAX_VALUE);
        return new TokenStreamComponents(source, result);
    }
}
