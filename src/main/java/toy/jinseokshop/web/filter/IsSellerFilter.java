package toy.jinseokshop.web.filter;

import lombok.extern.slf4j.Slf4j;
import toy.jinseokshop.web.login.SessionConst;
import toy.jinseokshop.web.login.SessionLoginDto;
import toy.jinseokshop.web.member.MemberConst;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class IsSellerFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String requestURI = httpRequest.getRequestURI();

        try {
            log.info("판매자 체크 필터 시작 {}", requestURI);

            // 접근한 사용자의 세션 정보를 가져온다 -> 판매자면 접근을 허가하고, 판매자가 아니면 / 로 리다이렉트 할 예정
            HttpSession session = httpRequest.getSession(false);
            SessionLoginDto sessionDto = (SessionLoginDto) session.getAttribute(SessionConst.LOGIN_MEMBER);
            String isSeller = sessionDto.getIsSeller(); // 판매자 - YES , 판매자 x - NO

            if (!isSeller.equals(MemberConst.IS_SELLER)) {
                log.info("미판매자 경로 접근 시도 감지 {}", requestURI);

                // root 화면으로 리다이렉트
                httpResponse.sendRedirect("/");

                return; // 리턴을 하지 않으면 미판매자가 정상 로직을 밟게 돼 오류가 날 수 있다!
            }

            // 정상 로직 -> chain.doFilter을 사용하지 않으면 다음 필터(이 경우는 다음 필터가 없으니 DispatcherServlet??)로 이동하지 않아 진행되지 않음
            // chain.doFilter는 반드시 해야한다!
            chain.doFilter(request, response);
        } catch (Exception e) {
            throw e;
        } finally {
            // 이 로직은 DispatcherServlet 종료 후 호출되는 로직
            log.info("판매자 체크 필터 종료 {}", requestURI);
        }
    }
}
