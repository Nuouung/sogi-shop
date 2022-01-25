# SOGI SHOP
## 안녕하세요, SOGI SHOP에 오신 것을 환영합니다!

> SOGI SHOP은 상품을 등록, 조회하고 구매할 수 있는 온라인 쇼핑몰입니다. 
> 
> SOGI SHOP은 스프링과 JPA를 국비지원 교육기관에서 공부하던 중, 배우는 내용을 체화하고 싶은 마음에서 시작한 간단한 쇼핑몰 프로젝트였습니다. 이후, 학습하는 내용을 조금 더 방대하게 담고 싶다는 욕심과 프로젝트의 확장성을 고려한 코딩을 해 보고 싶다는 생각에 하나의 취업 포트폴리오로 녹여낼 수 있는 프로젝트로 탄생했습니다.
> 
> SOGI SHOP은 다양한 기능을 담고 있습니다. SOGI SHOP은 판매자 등록을 통해 상품을 판매하고, 이를 통해 등록된 상품을 구매할 수 있습니다. SOGI SHOP은 가상의 통화 '열정'을 사용합니다. 상품 구매의 부족한 열정은 내 정보 란에서 무료로 충전이 가능합니다.



[SOGI SHOP 바로가기](http://ec2-52-78-149-159.ap-northeast-2.compute.amazonaws.com:8080/)


```
* About Me에서는 개발자인 저에 대한 정보를 볼 수 있습니다.
* 서기샵에서는 상품 등록, 상품 조회, 상품 구매가 가능합니다.
* 내 정보는 로그인 시 노출됩니다.
```



## SOGI SHOP 기능 설명


### 핵심 기능
1. 회원가입 / 인증 및 인가
2. 상품 등록, 상품 조회(전체조회, 카테고리별 조회)
3. 상품 구매
4. 리뷰 작성, 평점 반영
5. 검증 기능


### 부가 기능
1. 판매자, 일반 회원별 차별화된 접근 권한 <- 일반회원은 상품 등록을 할 수 없습니다.
2. 파일 업로드 시 이미지를 제외한 파일은 업로드하지 못하도록 한 기능
3. SOGI SHOP의 통화인 열정을 충전할 수 있는 기능
4. 로그인하지 않은 사용자는 About me, 서기샵에 접근할 수 없도록 한 기능



## 프로젝트 상세


### 아키텍처

![화면 캡처 2022-01-25 115322](https://user-images.githubusercontent.com/88177646/150902284-6fa7e4d4-92ed-43b0-bf5b-4409a195d1e3.jpg)



### 페이징

```java
@Component
public class PagingManager <T> {

    public int[] getPageIndexes(int queryParam) {
        ...
    }
    
    public Map<String, Object> createPage(List<T> tempList, List<T> resultList) {
        ...
    }
    
    public void storePageToModel(Map<String, Object> pageMap, int page, Model model) {
        ...
    }
    
}
```

* PagingManager은 페이징 기능을 총괄하는 클래스로 페이징 기능이 필요한 클래스는 이 클래스를 호출하여 페이징 기능을 구현할 수 있습니다.
* PagingManager에 들어가는 데이터 타입을 제네릭으로 설정해두었기 때문에 데이터타입에 제한받지 않는 구현이 가능합니다.
* PagingManger에는 총 세 메소드가 존재하며 각각의 메소드는 위에서부터 차례로 service, repository, controller에서 호출이 가능합니다.


### 프로젝트 내 검증 기능

* 회원가입
  * 회원의 이름은 빈 값일 수 없고, 10글자를 초과할 수 없습니다.
  * 회원의 비밀번호는 빈 값일 수 없고, 6글자 미만으로 설정할 수 없습니다.
  * 아이디는 중복되지 않습니다.

* 파일
  * 파일은 사진만 업로드 가능합니다.
  * 허용되는 확장자는 jpg, png, jpeg, bmg, gif입니다.

* 상품
  * 상품명은 최소 1자, 최대 29자로 설정 가능합니다.
  * 상품가격은 0 이상의 정수만 설정 가능합니다.
  * 상품갯수는 0 이상의 정수만 설정 가능합니다.
  * 혹, 웹브라우저의 자바스크립트를 조작하여 악의적인 접근을 하는 사용자가 있을 경우, 이를 검증하기 위해 ItemController 클래스 내 maliciousClientApproachCatcher 메소드가 존재합니다.

```java
// optionA와 optionB는 두개일 수 없습니다.
// 이 메소드는 optionA와 optionB가 두개 이상 들어왔는지 검증합니다.
private boolean maliciousClientApproachCatcher(String optionA, String optionB) {
        int indexA = optionA.lastIndexOf(",");
        int indexB = optionB.lastIndexOf(",");
        int lastIndexA = optionA.length() - 1;
        int lastIndexB = optionB.length() - 1;

        // 가능한 String 형태
        // 1. [정상 접근] xxxx,
        // 2. [정상 접근] ,xxxx
        // 3. [비정상 접근] xxxx,xxxx
        if ((indexA != 0 && indexA != lastIndexA) ||
                (indexB != 0 && indexB != lastIndexB)) {
            log.warn("MALICIOUS APPROACH DETECTED");
            return true;
        }
        return false;
    }
```

* 주문
  * 주문갯수는 0 이상, 50 이하의 정수만 설정 가능합니다.


### 카테고리 기능

카테고리 기능은 기본적으로 페이징 기능과 엮여서 동작합니다.
SOGI SHOP에서 카테고리는 책, 강의, 기타로 각각의 카테고리를 캐치할 수 있는 switch문을 ItemService 내에 배치했습니다.

```java
Map<String, Object> itemMap = null;
switch (type) {
        case "main":
            itemMap = itemRepository.findPage(indexes[0], indexes[1]);
            break;
        case ItemConst.BOOK:
            itemMap = itemRepository.findPageWhereBook(indexes[0], indexes[1]);
            break;
        case ItemConst.LECTURE:
            itemMap = itemRepository.findPageWhereLecture(indexes[0], indexes[1]);
            break;
        case ItemConst.ETC:
            itemMap = itemRepository.findPageWhereEtc(indexes[0], indexes[1]);
        }
```

## SOGI SHOP의 UML

![엔티티 UML](https://user-images.githubusercontent.com/88177646/150324602-a3a9dd38-d04c-4ec1-97d8-c24042d6126b.jpg)
