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




## SOGI SHOP의 UML

![엔티티 UML](https://user-images.githubusercontent.com/88177646/150324602-a3a9dd38-d04c-4ec1-97d8-c24042d6126b.jpg)
