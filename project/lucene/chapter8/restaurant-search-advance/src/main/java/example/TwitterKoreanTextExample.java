package example;

import java.util.List;

import com.twitter.penguin.korean.TwitterKoreanProcessorJava;
import com.twitter.penguin.korean.phrase_extractor.KoreanPhraseExtractor;
import com.twitter.penguin.korean.tokenizer.KoreanTokenizer;
import scala.collection.Seq;

public class TwitterKoreanTextExample {
    public static void main(String[] args) {
        String sentenceString = "마산을 대표하는 음식 중에 하나가 바로 '아구찜'이다. 마산 오동동에 가면 마산의 명물인 아구거리가 형성되어 있는데, 거리의 음식점 중 한 곳이 이곳이다. "
            + "갈분가루를 쓰지 않아 걸죽하지 않다는 이 집만의 양념과 호텔에서의 경력이 밑바탕이 된 철저한 서비스 정신과 친절이 바로 이 곳의 인기비결. 알싸한 양념과 구수한 아구의 고기맛이 제대로 어우러진 마산아구찜의 진수를 맛 볼 수 있는 곳이다.";

        // Normalize
        CharSequence normalized = TwitterKoreanProcessorJava.normalize(sentenceString);
        System.out.println("=== normalize 결과 === \n + " + normalized);
        // 한국어를 처리하는 예시입니다ㅋㅋ #한국어


        // Tokenize
        Seq<KoreanTokenizer.KoreanToken> tokens = TwitterKoreanProcessorJava.tokenize(normalized);
        System.out.println("=== tokenize 결과 ===\n" + TwitterKoreanProcessorJava.tokensToJavaStringList(tokens));
        // [한국어, 를, 처리, 하는, 예시, 입니, 다, ㅋㅋ, #한국어]
        System.out.println("=== tokens ===\n" + TwitterKoreanProcessorJava.tokensToJavaKoreanTokenList(tokens));
        // [한국어(Noun: 0, 3), 를(Josa: 3, 1),  (Space: 4, 1), 처리(Noun: 5, 2), 하는(Verb: 7, 2),  (Space: 9, 1), 예시(Noun: 10, 2), 입니(Adjective: 12, 2), 다(Eomi: 14, 1), ㅋㅋ(KoreanParticle: 15, 2),  (Space: 17, 1), #한국어(Hashtag: 18, 4)]


        // Stemming
        Seq<KoreanTokenizer.KoreanToken> stemmed = TwitterKoreanProcessorJava.stem(tokens);
        System.out.println("=== stemming string ===\n" + TwitterKoreanProcessorJava.tokensToJavaStringList(stemmed));
        // [한국어, 를, 처리, 하다, 예시, 이다, ㅋㅋ, #한국어]
        System.out.println("=== stemming token ===\n" + TwitterKoreanProcessorJava.tokensToJavaKoreanTokenList(stemmed));
        // [한국어(Noun: 0, 3), 를(Josa: 3, 1),  (Space: 4, 1), 처리(Noun: 5, 2), 하다(Verb: 7, 2),  (Space: 9, 1), 예시(Noun: 10, 2), 이다(Adjective: 12, 3), ㅋㅋ(KoreanParticle: 15, 2),  (Space: 17, 1), #한국어(Hashtag: 18, 4)]


        // Phrase extraction
        List<KoreanPhraseExtractor.KoreanPhrase> phrases = TwitterKoreanProcessorJava.extractPhrases(tokens, true, true);
        System.out.println("=== Phase 추출 ===\n" + phrases);
        // [한국어(Noun: 0, 3), 처리(Noun: 5, 2), 처리하는 예시(Noun: 5, 7), 예시(Noun: 10, 2), #한국어(Hashtag: 18, 4)]
    }
}
