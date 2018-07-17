package analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;

/**
 * Created by hwang on 2018. 5. 28..
 */
public class MyAnalyzer extends Analyzer {
    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        //WhitespaceTokenizer 결과의 tokenstream 객체를 리턴한다
        return new TokenStreamComponents(new WhitespaceTokenizer());
    }
}
