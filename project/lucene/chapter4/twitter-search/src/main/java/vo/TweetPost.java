package vo;

import com.opencsv.bean.CsvBindByPosition;

public class TweetPost {
    // 등록일시
    @CsvBindByPosition(position = 0)
    private String createdAt;

    // 트위터 글 아이디
    @CsvBindByPosition(position = 1)
    private String id;

    // 언어 종류
    @CsvBindByPosition(position = 2)
    private String lang;

    // 트위터 글
    @CsvBindByPosition(position = 3)
    private String text;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "TweetPost{" +
            "createdAt='" + createdAt + '\'' +
            ", id='" + id + '\'' +
            ", lang='" + lang + '\'' +
            ", text='" + text + '\'' +
            '}';
    }
}
