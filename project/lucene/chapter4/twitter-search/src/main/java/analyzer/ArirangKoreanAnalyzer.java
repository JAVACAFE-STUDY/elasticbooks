package analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.ko.KoreanTokenizer;

public class ArirangKoreanAnalyzer extends Analyzer {
    @Override
    protected Analyzer.TokenStreamComponents createComponents(String fieldName) {
        // 아리랑 한글 형태소 분석기 사용
        return new Analyzer.TokenStreamComponents(new KoreanTokenizer());
    }
}
