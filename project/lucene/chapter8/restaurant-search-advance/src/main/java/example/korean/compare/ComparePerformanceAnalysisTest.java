package example.korean.compare;

import java.util.List;

import example.korean.compare.common.LoadCsv;

public class ComparePerformanceAnalysisTest {
    // 성능 측정에 공통적으로 사용할 음식점의 소개 글 리스트
    private List<String> contentList;
    // 분석 시간을 기록할 timer
    private Timer timer;

    // 테스트 전 음식점 소개 글을 CSV 파일에서 로딩하고 타이머를 생성한다.
    public ComparePerformanceAnalysisTest() {
        contentList = LoadCsv.loadCsv();
        timer = new Timer();
    }

    // 실제 성능 측정을 하는 메소드
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
        // 리스트에 저장된 음식점 소개 글을 하나씩 형태소 분석한다.
        contentList.stream().forEach(morphemeAnalyzer::analyze);
        timer.end();
        System.out.println(timer.getResult());
    }

    // 시간을 기록할 타이머
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
