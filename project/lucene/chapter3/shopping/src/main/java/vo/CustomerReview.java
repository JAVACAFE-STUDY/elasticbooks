package vo;

import com.opencsv.bean.CsvBindByPosition;

public class CustomerReview {

    // 리뷰 ID
    @CsvBindByPosition(position = 0)
    private String reviewId;

    // 상품 ID
    @CsvBindByPosition(position = 1)
    private String clothingId;

    // 리뷰어 나이
    @CsvBindByPosition(position = 2)
    private String age;

    // 상품 후기 제목
    @CsvBindByPosition(position = 3)
    private String title;

    // 상품 후기
    @CsvBindByPosition(position = 4)
    private String reviewText;

    // 상품 후기 점수
    @CsvBindByPosition(position = 5)
    private String rating;

    // 리뷰어 상품 추천 여부
    @CsvBindByPosition(position = 6)
    private String recommendedInd;

    // 리뷰에 대한 '좋아요' 카운트
    @CsvBindByPosition(position = 7)
    private String positiveFeedbackCount;

    // 상품의 분류명
    @CsvBindByPosition(position = 8)
    private String divisionName;

    // 상품 부서명
    @CsvBindByPosition(position = 9)
    private String departmentName;

    // 상품 종류
    @CsvBindByPosition(position = 10)
    private String className;

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getClothingId() {
        return clothingId;
    }

    public void setClothingId(String clothingId) {
        this.clothingId = clothingId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRecommendedInd() {
        return recommendedInd;
    }

    public void setRecommendedInd(String recommendedInd) {
        this.recommendedInd = recommendedInd;
    }

    public String getPositiveFeedbackCount() {
        return positiveFeedbackCount;
    }

    public void setPositiveFeedbackCount(String positiveFeedbackCount) {
        this.positiveFeedbackCount = positiveFeedbackCount;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "CustomerReview{" +
            "reviewId='" + reviewId + '\'' +
            ", clothingId='" + clothingId + '\'' +
            ", age='" + age + '\'' +
            ", title='" + title + '\'' +
            ", reviewText='" + reviewText + '\'' +
            ", rating='" + rating + '\'' +
            ", recommendedInd='" + recommendedInd + '\'' +
            ", positiveFeedbackCount='" + positiveFeedbackCount + '\'' +
            ", divisionName='" + divisionName + '\'' +
            ", departmentName='" + departmentName + '\'' +
            ", className='" + className + '\'' +
            '}';
    }
}
