package analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.ngram.NGramTokenFilter;
import org.apache.lucene.analysis.ngram.NGramTokenizer;

public class NgramAnalyzer extends Analyzer {
    @Override
    protected Analyzer.TokenStreamComponents createComponents(String fieldName) {
        Tokenizer tokenizer = new NGramTokenizer(2,2);
        NGramTokenFilter nGramTokenFilter = new NGramTokenFilter(tokenizer);
        return new Analyzer.TokenStreamComponents(tokenizer, nGramTokenFilter);
    }
}
