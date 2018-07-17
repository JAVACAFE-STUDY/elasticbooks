package analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.synonym.SynonymGraphFilter;
import org.apache.lucene.analysis.synonym.SynonymMap;

public class SynonymAnalyzer extends Analyzer {
    private SynonymMap synonymMap;
    public SynonymAnalyzer(SynonymMap synonymMap){
        this.synonymMap = synonymMap;
    }

    /**
     * 기존 SynonymFilter가 deprecated 되어 SynonymGraphFilter를 대체로 사용해야 함
     * 방식 변경으로 SynonymFilter는 index 타임과 search 타임 모두 사용할 수 있었으나
     * SynonymGraphFilter은 search 타임에만 사용할 수 있고 index 타임에 사용하려면 FlattenGraphFilter을 사용해야 함
     */
    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        Tokenizer tokenizer = new StandardTokenizer();
        SynonymGraphFilter synonymGraphFilter = null;
        try {
            synonymGraphFilter = new SynonymGraphFilter(tokenizer, synonymMap, true);
            // search 타임에 SynonymGraphFilter만 설정하여 검색하였으니 동의어 처리가 되지 않음
            // 혹시나 하여 FlattenGraphFilter를 곂쳐 써보았지만 여전히 동의어 처리가 안됨
            //FlattenGraphFilter flattenFilter = new FlattenGraphFilter(synonymGraphFilter);
            return new TokenStreamComponents(tokenizer, synonymGraphFilter);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
