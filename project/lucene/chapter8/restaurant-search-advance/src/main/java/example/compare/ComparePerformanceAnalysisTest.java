package example.compare;

import java.util.List;

import example.compare.common.LoadCsv;

public class ComparePerformanceAnalysisTest {
    private List<String> contentList;
    private Timer timer;

    public ComparePerformanceAnalysisTest() {
        contentList = LoadCsv.loadCsv();
        timer = new Timer();
    }

    public void arirangIndexTest() {
        KoreanMorphemeAnalyzerTest.ArirangMorphemeAnalyzer arirangMorphemeAnalyzer = new KoreanMorphemeAnalyzerTest.ArirangMorphemeAnalyzer();
        testRun(timer, contentList, arirangMorphemeAnalyzer);
    }

    public void hannanumAanalyzeTest() {
        KoreanMorphemeAnalyzerTest.HannanumMorphemeAnalyzer hannanumMorphemeAnalyzer = new KoreanMorphemeAnalyzerTest.HannanumMorphemeAnalyzer();
        testRun(timer, contentList, hannanumMorphemeAnalyzer);

    }
    public void eunjeonTest() {
        KoreanMorphemeAnalyzerTest.EunjeonMorphemeAnalyzer eunjeonMorphemeAnalyzer = new KoreanMorphemeAnalyzerTest.EunjeonMorphemeAnalyzer();
        testRun(timer, contentList, eunjeonMorphemeAnalyzer);
    }

    public void twitterTest() {
        KoreanMorphemeAnalyzerTest.TwitterMorphemeAnalyzer twitterMorphemeAnalyzer = new KoreanMorphemeAnalyzerTest.TwitterMorphemeAnalyzer();
        testRun(timer, contentList, twitterMorphemeAnalyzer);
    }

    public void kkmaTest() {
        KoreanMorphemeAnalyzerTest.KkmaMorphemeAnalyzer kkmaMorphemeAnalyzer = new KoreanMorphemeAnalyzerTest.KkmaMorphemeAnalyzer();
        testRun(timer, contentList, kkmaMorphemeAnalyzer);
    }

    private static void testRun(Timer timer, List<String> contentList, KoreanMorphemeAnalyzerTest.KoreanMorphemeAnalyzer morphemeAnalyzer){
        System.out.println("########### start " + morphemeAnalyzer.getName());
        timer.init();
        timer.start();
        contentList.stream().forEach(morphemeAnalyzer::analyze);
        timer.end();
        System.out.println(timer.getResult());
    }

    public static class Timer {
        long startTime = 0;
        long endTime = 0;

        public void start() {
            startTime = System.currentTimeMillis();
        }

        public void end() {
            endTime = System.currentTimeMillis();
        }

        public void init() {
            startTime = 0;
            endTime = 0;
        }

        public String getResult() {
            return String.valueOf(endTime - startTime) + "milliseconds, start=" + startTime + ", end=" + endTime;
        }
    }
}
