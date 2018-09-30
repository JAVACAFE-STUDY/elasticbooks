package example;

import java.util.List;

import helper.LoadRestaurantInfoCsvLoader;
import service.IndexRestaurantInfoIndexService;
import service.SearchRestaurantInfoSearchService;
import vo.RestaurantInfoVo;

public class RestaurantInfoManager {
    public static void main(String[] args) throws Exception {
        // 1. CSV 파일에서 음식점 정보를 로딩한다
        LoadRestaurantInfoCsvLoader loader = new LoadRestaurantInfoCsvLoader();
        // 2. 로딩한 데이터를 RestaurantInfoVo에 저장한다
        List<RestaurantInfoVo> restaurantInfoVos = loader.loadRestaurantInfo();

        // 3. 음식점 정보를 색인할 클래스 객체를 생성한다
        IndexRestaurantInfoIndexService indexRestaurantInfoIndexService = new IndexRestaurantInfoIndexService();
        // 4. 음식점 정보를 색인한다
        indexRestaurantInfoIndexService.indexRestaurantInfo(restaurantInfoVos);

        // 5. 색인된 음식점 정보를 검색한 클래스 객체를 생성한다
        SearchRestaurantInfoSearchService searchRestaurantInfoSearchService = new SearchRestaurantInfoSearchService();
        // 6. 인덱스에서 바다으로 음식점 정보를 검색한다
        searchRestaurantInfoSearchService.search("바다");
    }
}
