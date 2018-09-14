package org.apache.lucene.analysis.ko;

import org.apache.lucene.analysis.charfilter.BaseCharFilter;
import org.openkoreantext.processor.OpenKoreanTextProcessor;

import java.io.IOException;
import java.io.Reader;

/**
 * A character filter for normalizing input text.
 * For normalizing text, it delegates input to {@link OpenKoreanTextProcessor}.
 *

 */
public class OpenKoreanTextNormalizer extends BaseCharFilter {
    private static final int READER_BUFFER_SIZE = 2048;

    private boolean preparedToRead;
    private char[] inputText;
    private int cursor;

    public OpenKoreanTextNormalizer(Reader in) {
        super(in);
        initAttributes();
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        if (off < 0) throw new IllegalArgumentException("off < 0");
        if (off >= cbuf.length) throw new IllegalArgumentException("off >= cbuf.length");
        if (len <= 0) throw new IllegalArgumentException("len <= 0");

        if (!this.preparedToRead) {
            prepareToRead();
        }

        int copyLen = this.inputText.length - cursor;
        if(copyLen < 1){
            initAttributes();
            return -1;
        }

        copyLen = copyLen > len ? len : copyLen;
        System.arraycopy(inputText, cursor, cbuf, off, copyLen);
        cursor += copyLen;
        return copyLen;
    }

    private void initAttributes(){
        this.preparedToRead = false;
        this.inputText = null;
        this.cursor = -1;
    }

    private void prepareToRead() throws IOException {
        this.preparedToRead = true;
        this.inputText = normalizeInput().toCharArray();
        this.cursor = 0;
    }

    private String normalizeInput() throws IOException {
        StringBuilder text = new StringBuilder();
        char[] tmp = new char[READER_BUFFER_SIZE];
        int len = -1;
        while ((len = input.read(tmp)) != -1) {
            text.append(new String(tmp, 0, len));
        }
        return OpenKoreanTextProcessor.normalize(text).toString();
    }
}
