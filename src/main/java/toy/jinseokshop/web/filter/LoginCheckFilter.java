package toy.jinseokshop.web.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;
import toy.jinseokshop.web.login.SessionConst;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class LoginCheckFilter implements Filter {

    private static final String[] whitelist = {"/", "/main", "/login", "/logout", "/join", "/css/**", "/js/**", "/image/**"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            log.info("인증 체크 필터 시작 {}", requestURI);

            if (!isLoginCheckPath(requestURI)) {
                log.info("인증 체크 로직 실행 {}", requestURI);
                HttpSession session = httpRequest.getSession(false);
                if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
                    log.info("미인증 사용자 요청 {}", requestURI);

                    // 로그인 화면으로 redirect
                    httpResponse.sendRedirect("/login?redirectURL=" + requestURI);

                    return; // 리턴을 하지 않으면 미인증 사용자가 정상 로직을 밟게 되므로 오류가 날 수 있다!
                }
            }

            // 사용자의 인가가 끝나면 정상 로직 진행
            chain.doFilter(request, response);
        } catch (Exception e) {
            throw e; // 예외 로깅이 가능하나, 톰캣까지 예외를 보내주어야 함
        } finally {
            log.info("인증 체크 필터 종료 {}", requestURI);
        }
    }

    /**
     * 화이트 리스트일 경우 인증 체크 x
     */
    private boolean isLoginCheckPath(String requestURI) {
        return PatternMatchUtils.simpleMatch(whitelist, requestURI);
        // PatternMatchUtils를 쓰지 않고 구현할 수 있으나 번거롭다.
    }
}
