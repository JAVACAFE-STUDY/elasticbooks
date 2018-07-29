package example.korean.compare;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.lucene.analysis.ko.morph.AnalysisOutput;
import org.apache.lucene.analysis.ko.morph.MorphException;
import org.apache.lucene.analysis.ko.morph.WordSegmentAnalyzer;
import org.bitbucket.eunjeon.seunjeon.Analyzer;
import org.bitbucket.eunjeon.seunjeon.LNode;
import org.bitbucket.eunjeon.seunjeon.Morpheme;

import com.twitter.penguin.korean.KoreanTokenJava;
import com.twitter.penguin.korean.TwitterKoreanProcessorJava;
import com.twitter.penguin.korean.tokenizer.KoreanTokenizer;
import kr.ac.kaist.swrc.jhannanum.comm.Eojeol;
import kr.ac.kaist.swrc.jhannanum.comm.SetOfSentences;
import kr.ac.kaist.swrc.jhannanum.exception.ResultTypeException;
import kr.ac.kaist.swrc.jhannanum.hannanum.Workflow;
import kr.ac.kaist.swrc.jhannanum.hannanum.WorkflowFactory;
import scala.collection.Seq;

public class KoreanMorphemeAnalyzerTest {

    public interface KoreanMorphemeAnalyzer {
        void analyze(String contents);
        void printResult();
        String getName();
    }

    public static class ArirangMorphemeAnalyzer implements KoreanMorphemeAnalyzer {
        private WordSegmentAnalyzer wordSegmentAnalyzer;
        private List<List<AnalysisOutput>> resultList;

        public ArirangMorphemeAnalyzer() {
            wordSegmentAnalyzer = new WordSegmentAnalyzer();
        }

        @Override
        public void analyze(String contents) {
            try {
                resultList = wordSegmentAnalyzer.analyze(contents);
            } catch (MorphException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void printResult() {
            System.out.println(resultList);
        }

        @Override
        public String getName() {
            return "ArirangMorphemeAnalyzer";
        }
    }

    public static class HannanumMorphemeAnalyzer implements KoreanMorphemeAnalyzer {
        private Workflow workflow;
        private LinkedList<SetOfSentences> resultOfDocument;

        public HannanumMorphemeAnalyzer() {
            workflow = WorkflowFactory.getPredefinedWorkflow(WorkflowFactory.WORKFLOW_MORPH_ANALYZER);
            try {
                workflow.activateWorkflow(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void analyze(String contents) {
            workflow.analyze(contents);
            try {
                resultOfDocument = workflow.getResultOfDocument(new SetOfSentences(0, 0, false));
            } catch (ResultTypeException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void printResult() {
            System.out.println(resultOfDocument);
        }

        @Override
        public String getName() {
            return "HannanumMorphemeAnalyzer";
        }

        public static void printMorphemes(LinkedList<SetOfSentences> resultList) {
            for (SetOfSentences s : resultList) {
                ArrayList<Eojeol[]> eojeolArrayList = s.getEojeolSetArray();
                eojeolArrayList.forEach(eojeolArray -> {
                    for(Eojeol eojeol : eojeolArray) {
                        String[] morphemes = eojeol.getMorphemes();
                        String[] tags = eojeol.getTags();
                        for (int j = 0; j < morphemes.length; j++) {
                            System.out.print(morphemes[j]);
                            System.out.print("[" + tags[j] + "]");
                        }
                    }
                });
            }
        }
    }

    public static class EunjeonMorphemeAnalyzer implements KoreanMorphemeAnalyzer {
        private List<org.bitbucket.eunjeon.seunjeon.Eojeol> eojeols;

        @Override
        public void analyze(String contents) {
            eojeols = Analyzer.parseEojeolJava(contents);

        }

        @Override
        public void printResult() {
            for (org.bitbucket.eunjeon.seunjeon.Eojeol eojeol: eojeols) {
                System.out.println(eojeol.surface());
                eojeol.toString();
                for (LNode node: eojeol.nodesJava()) {
                    Morpheme morpheme = node.morpheme();
                    System.out.println("\t" + morpheme.surface() + ":" + morpheme.poses());
                }
            }
        }

        @Override
        public String getName() {
            return "EunjeonMorphemeAnalyzer";
        }
    }

    public static class KkmaMorphemeAnalyzer implements KoreanMorphemeAnalyzer {
        private org.snu.ids.ha.ma.MorphemeAnalyzer analyzer;
        private List<org.snu.ids.ha.ma.MExpression> resultList;

        public KkmaMorphemeAnalyzer() {
            analyzer = new org.snu.ids.ha.ma.MorphemeAnalyzer();
            analyzer.createLogger(null);
        }

        @Override
        public void analyze(String contents) {
            try {
                resultList = analyzer.analyze(contents);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void printResult() {
            List<org.snu.ids.ha.ma.Sentence> sentences = null;
            try {
                sentences = analyzer.divideToSentences(resultList);
            } catch (Exception e) {
                e.printStackTrace();
            }
            sentences.stream().forEach(sentence -> sentence.forEach(eojeol -> eojeol.stream().forEach(morpheme -> System.out.println(morpheme.getSmplStr()))));
        }

        @Override
        public String getName() {
            return "KkmaMorphemeAnalyzer";
        }
    }

    public static class TwitterMorphemeAnalyzer implements KoreanMorphemeAnalyzer {
        private List<KoreanTokenJava> koreanTokenJavas;

        @Override
        public void analyze(String contents) {
            CharSequence normalized = TwitterKoreanProcessorJava.normalize(contents);
            Seq<KoreanTokenizer.KoreanToken> tokens = TwitterKoreanProcessorJava.tokenize(normalized);
            koreanTokenJavas = TwitterKoreanProcessorJava.tokensToJavaKoreanTokenList(tokens);
        }

        @Override
        public void printResult() {
            koreanTokenJavas.stream().forEach(token -> System.out.println(token.getText() + ":" + token.getPos()));
        }

        @Override
        public String getName() {
            return "TwitterMorphemeAnalyzer";
        }
    }

}

