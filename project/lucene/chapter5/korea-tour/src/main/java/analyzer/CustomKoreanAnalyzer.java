package analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.ko.KoreanTokenizer;

public class CustomKoreanAnalyzer extends Analyzer {
    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        // 아리랑 한글 형태소 분석기 사용
        return new TokenStreamComponents(new KoreanTokenizer());
    }
}
