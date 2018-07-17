package example;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;

public class StopFilterPositionIncrementAttributeExample {
    public static void main(String[] args) throws Exception{
        //String s = "a sky love the mind cat";
        String s = "blue is the sky";

        StandardAnalyzer analyzer = new StandardAnalyzer();
        TokenStream tokenStream = analyzer.tokenStream("string", s);
        CharTermAttribute cta = tokenStream.addAttribute(CharTermAttribute.class);
        PositionIncrementAttribute pa = tokenStream.addAttribute(PositionIncrementAttribute.class);
        try {
            tokenStream.reset();
            while (tokenStream.incrementToken()) {
                System.out.println("CharTerm: " + cta
                    + " | " + "PositionIncrement: " + pa.getPositionIncrement());
            }
            tokenStream.end();
        } finally {
            tokenStream.close();
        }
    }
}
