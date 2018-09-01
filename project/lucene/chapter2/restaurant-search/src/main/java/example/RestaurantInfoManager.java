package example;

import java.util.List;

import index.IndexRestaurantInfo;
import index.LoadRestaurantInfoCsvFile;
import search.SearchRestaurantInfo;
import vo.RestaurantInfoVo;

public class RestaurantInfoManager {
    public static void main(String[] args) throws Exception {
        // 1. CSV 파일에서 음식점 정보를 로딩한다
        LoadRestaurantInfoCsvFile loader = new LoadRestaurantInfoCsvFile();
        // 2. 로딩한 데이터를 RestaurantInfoVo에 저장한다
        List<RestaurantInfoVo> restaurantInfoVos = loader.loadRestaurantInfo();

        // 3. 음식점 정보를 색인할 클래스 객체를 생성한다
        IndexRestaurantInfo indexRestaurantInfo = new IndexRestaurantInfo();
        // 4. 음식점 정보를 색인한다
        indexRestaurantInfo.indexRestaurantInfo(restaurantInfoVos);

        // 5. 색인된 음식점 정보를 검색한 클래스 객체를 생성한다
        SearchRestaurantInfo searchRestaurantInfo = new SearchRestaurantInfo();
        // 6. 인덱스에서 바다으로 음식점 정보를 검색한다
        searchRestaurantInfo.search("바다");
    }
}
