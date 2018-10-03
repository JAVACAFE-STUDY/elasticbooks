package example;

import java.util.List;

import helper.RestaurantInfoCsvLoader;
import service.RestaurantInfoIndexService;
import service.RestaurantInfoSearchService;
import vo.RestaurantInfoVo;

public class RestaurantInfoManager {
    public static void main(String[] args) throws Exception {
        // 1. CSV 파일에서 음식점 정보를 로딩한다
        RestaurantInfoCsvLoader loader = new RestaurantInfoCsvLoader();
        // 2. 로딩한 데이터를 RestaurantInfoVo에 저장한다
        List<RestaurantInfoVo> restaurantInfoVos = loader.loadRestaurantInfo();

        // 3. 음식점 정보를 색인할 클래스 객체를 생성한다
        RestaurantInfoIndexService restaurantInfoIndexService = new RestaurantInfoIndexService();
        // 4. 음식점 정보를 색인한다
        restaurantInfoIndexService.indexRestaurantInfo(restaurantInfoVos);

        // 5. 색인된 음식점 정보를 검색한 클래스 객체를 생성한다
        RestaurantInfoSearchService restaurantInfoSearchService = new RestaurantInfoSearchService();
        // 6. 색인에서 바다으로 음식점 정보를 검색한다
        restaurantInfoSearchService.search("바다");
    }
}
