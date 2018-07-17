package vo;

import com.opencsv.bean.CsvBindByPosition;

/**
 * Created by hwang on 2018. 4. 8..
 */

//걷는길 명칭,코스명,지역1,난이도,상세거리(km),소요시간(시),소개
public class TourInfo {
    //걷는길 명칭
    @CsvBindByPosition(position = 0)
    private String pathName;

    //코스명
    @CsvBindByPosition(position = 1)
    private String courseName;

    //지역
    @CsvBindByPosition(position = 2)
    private String area;

    //난이도
    @CsvBindByPosition(position = 3)
    private String level;

    //상세거리
    @CsvBindByPosition(position = 4)
    private String distance;

    //소요시간
    @CsvBindByPosition(position = 5)
    private String hour;

    //소개
    @CsvBindByPosition(position = 6)
    private String description;

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
