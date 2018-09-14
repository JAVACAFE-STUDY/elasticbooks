import java.text.Normalizer;

public class Main {
    public static void main(String[] args){
        String hanguleSuggest = "ㅅㅏㅁㅅㅓㅇㅈㅓㄴㅈㅏ";
        String nfc = Normalizer.normalize(hanguleSuggest, Normalizer.Form.NFC);
        printIt(nfc);

    }
    private static void printIt(String string) {
        System.out.println(string);
        for (int i = 0; i < string.length(); i++) {
            System.out.print(String.format("U+%04X ", string.codePointAt(i)));
        }
        System.out.println();
    }
}
