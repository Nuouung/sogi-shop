package toy.jinseokshop.domain.paging;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.*;

@Component
public class PagingManager <T> {

    /**
     * 본격적인 페이징 기능 적용 이전에 서비스단에서 수행되는 로직.
     * 웹 클라이언트로부터 쿼리파라미터(페이지 위치)가 전송되면 출력해야 하는 페이지 내 db 출력값 갯수와
     * 출력을 시작해야 하는 정수를 생성한다.
     * 각각 maxResult, startPosition으로 두고 1차원 int 배열을 반환한다.
     */
    public int[] getPageIndexes(int queryParam) {
        int maxResult = PageConst.NUMBER_PER_PAGE;
        int startPosition = (queryParam - 1) * maxResult;

        return new int[]{startPosition, maxResult};
    }

    /**
     * 실제 페이지를 만들어주는 로직.
     * 반환 값은 Map으로 들어 있는 값은 둘
     * 첫 번째 값은 totalPage number. 즉, 총 페이지 수
     * 두 번째 값은 resultList. PageConst.NUMBER_PER_PAGE의 갯수만큼의 db 출력 값이 들어 있다.
     * PageConst.NUMBER_PER_PAGE는 정수인데, 관리하기 편하게 상수로 빼 두었다.
     */
    public Map<String, Object> createPage(List<T> tempList, List<T> resultList) {

        // 1. 총 페이지 갯수를 tempList를 통해 구한다.
        int totalPage = tempList.size() / PageConst.NUMBER_PER_PAGE;

        if ((tempList.size() % PageConst.NUMBER_PER_PAGE) != 0) {
            totalPage++;
        }

        // 2. resultList와 총 페이지 갯수를 Map에 담고 리턴한다.
        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put(PageConst.TOTAL_PAGE, totalPage);
        pageMap.put(PageConst.LIST, resultList);

        return pageMap;
    }

    /**
     * 컨트롤러 단에서 호출되는 로직
     * Model 객체에 페이징에 필요한 정보를 담는다.
     * createPage로부터 반환받은 Map이 필요하다.
     */
    public void storePageToModel(Map<String, Object> pageMap, int page, Model model) {
        int totalPage = (int) pageMap.get(PageConst.TOTAL_PAGE);
        List<?> list = new ArrayList<>((Collection<?>) pageMap.get(PageConst.LIST));

        model.addAttribute(PageConst.TOTAL_PAGE, totalPage);
        model.addAttribute(PageConst.PAGE, page);
        model.addAttribute(PageConst.LIST, list);
    }

}
