# 쇼핑몰 후기로 알아보는 루씬 인덱스

## 예제 소개
고객이 쇼핑몰에서 상품을 구매한 후 남긴 후기의 데이터로 루씬에서 색인 과정이 어떻게 이루어지는지 알아봅니다.

## 예제 실행 방법
1. 자바 설치
2. 그레이들 라이브러리 확인
3. 프로젝트 실행

## 폴더 구조 소개

~~~
    ㄴ example : 샘플 예제를 담고 있는 패키지
        ㄴ ClothesReviewExample : 쇼핑올 후기로 알아보는 파일럿 프로젝트
    ㄴ service : 색인과 검색에 관한 서비스를 하는 패키지
        ㄴ IndexService.java : 색인을 담당하는 서비스
        ㄴ SearchService.java : 검색을 담당하는 서비스
    ㄴ util : 유틸
        ㄴ CsvLoader.java : CSV에서 데이터를 가져오는 역할을 담당하는 유틸
    ㄴ vo : 객체
        ㄴ CustomerReview.java : 고객 후기
~~~

## 예제 실행환경

* 자바(Java) 1.8 이상
* 빌드 툴(Build Tool) : Gradle

## 예제 출처
* Kaggle : https://www.kaggle.com/nicapotato/womens-ecommerce-clothing-reviews/data