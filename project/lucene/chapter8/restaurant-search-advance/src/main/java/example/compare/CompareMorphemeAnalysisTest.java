package example.compare;

public class CompareMorphemeAnalysisTest {
    private static final String targetStr =
        "제주도의 별미로는 오분자기 뚝배기, 옥돔구이가 유명한데, 제주도 서귀포시에서 오분자기 뚝배기, 옥돔구이 등 제주 토속음식으로 가장 유명한 식당이 진주식당이다. "
            + "또한 진주식당에서는 오분작젓, 전복젓, 갈치젓, 조기젓 등 젓갈류도 포장 판매해 제주청정해역의 맛깔난 해산물의 풍미를 여행에서 돌아와서도 즐길 수 있게 한다. "
            + "근처에 천지연폭포가 위치하고 있어 관광과 더불어 식도락을 즐기기에는 제격인 곳이다.";

    public void arirangIndexTest() {
        KoreanMorphemeAnalyzerTest.ArirangMorphemeAnalyzer arirangMorphemeAnalyzer = new KoreanMorphemeAnalyzerTest.ArirangMorphemeAnalyzer();
        arirangMorphemeAnalyzer.analyze(targetStr);
        arirangMorphemeAnalyzer.printResult();
    }

    public void hannanumAanalyzeTest() {
        KoreanMorphemeAnalyzerTest.HannanumMorphemeAnalyzer hannanumMorphemeAnalyzer = new KoreanMorphemeAnalyzerTest.HannanumMorphemeAnalyzer();
        hannanumMorphemeAnalyzer.analyze(targetStr);
        hannanumMorphemeAnalyzer.printResult();

    }
    public void eunjeonTest() {
        KoreanMorphemeAnalyzerTest.EunjeonMorphemeAnalyzer eunjeonMorphemeAnalyzer = new KoreanMorphemeAnalyzerTest.EunjeonMorphemeAnalyzer();
        eunjeonMorphemeAnalyzer.analyze(targetStr);
        eunjeonMorphemeAnalyzer.printResult();
    }

    public void kkmaTest() {
        KoreanMorphemeAnalyzerTest.KkmaMorphemeAnalyzer kkmaMorphemeAnalyzer = new KoreanMorphemeAnalyzerTest.KkmaMorphemeAnalyzer();
        kkmaMorphemeAnalyzer.analyze(targetStr);
        kkmaMorphemeAnalyzer.printResult();
    }

    public void twitterTest() {
        KoreanMorphemeAnalyzerTest.TwitterMorphemeAnalyzer twitterMorphemeAnalyzer = new KoreanMorphemeAnalyzerTest.TwitterMorphemeAnalyzer();
        twitterMorphemeAnalyzer.analyze(targetStr);
        twitterMorphemeAnalyzer.printResult();
    }
}
