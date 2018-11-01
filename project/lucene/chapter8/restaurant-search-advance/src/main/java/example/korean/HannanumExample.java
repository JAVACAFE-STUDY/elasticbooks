package example.korean;

import java.util.LinkedList;

import kr.ac.kaist.swrc.jhannanum.comm.Eojeol;
import kr.ac.kaist.swrc.jhannanum.comm.Sentence;
import kr.ac.kaist.swrc.jhannanum.hannanum.Workflow;
import kr.ac.kaist.swrc.jhannanum.hannanum.WorkflowFactory;

public class HannanumExample {
    public static void main(String[] args) throws Exception{
        String sentenceString = "마산을 대표하는 음식 중에 하나가 바로 '아구찜'이다. 마산 오동동에 가면 마산의 명물인 아구거리가 형성되어 있는데, 거리의 음식점 중 한 곳이 이곳이다. "
            + "갈분가루를 쓰지 않아 걸죽하지 않다는 이 집만의 양념과 호텔에서의 경력이 밑바탕이 된 철저한 서비스 정신과 친절이 바로 이 곳의 인기비결. 알싸한 양념과 구수한 아구의 고기맛이 제대로 어우러진 마산아구찜의 진수를 맛 볼 수 있는 곳이다.";

        // workflow 정의
        Workflow workflow = WorkflowFactory.getPredefinedWorkflow(WorkflowFactory.WORKFLOW_NOUN_EXTRACTOR);

        // thread 모드를 매개변수로 설정 가능
        workflow.activateWorkflow(true);

        // workflow를 이용해 문장을 분석
        workflow.analyze(sentenceString);

        // 분석 결과를 문장으로 가져온다.
        LinkedList<Sentence> resultList = workflow.getResultOfDocument(new Sentence(0, 0, false));
        // 형태소 출력 메소드 호출
        printMorphemes(resultList);

        workflow.close();

        /* Shutdown the work flow */
        workflow.close();
    }

    // 형태소 출력 메소드
    public static void printMorphemes(LinkedList<Sentence> resultList) {
        for (Sentence s : resultList) {
            Eojeol[] eojeolArray = s.getEojeols();
            for (int i = 0; i < eojeolArray.length; i++) {
                if (eojeolArray[i].length > 0) {
                    String[] morphemes = eojeolArray[i].getMorphemes();
                    for (int j = 0; j < morphemes.length; j++) {
                        System.out.print(morphemes[j]);
                    }
                    System.out.print(", ");
                }
            }
        }
    }
}
