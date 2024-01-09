package hello.mylogin.filter;

import hello.mylogin.config.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class LoginCheckFilter implements Filter {

    //default가 들어가 있어서 init과 destroy를 반드시 구현할 필요는 없다.
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        Filter.super.init(filterConfig);
//    }

    private static final String[] whiteList = {"/","/members/sign_up","/login","/logout","/css/*","/favicon.ico"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try{
            log.info("로그인 체크 필터 시작 {}",requestURI);

            if(isLoginCheckPath(requestURI)) {
                log.info("인증 체크 로직 실행 {}",requestURI);
                HttpSession session = httpRequest.getSession(false);
                if(session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {

                    log.info("미인증 사용자 요청 {}",requestURI);
                    httpResponse.sendRedirect("/login?redirectURL="+requestURI);
                    return;
                }
            }

            chain.doFilter(request,response);
        } catch (Exception e) {
            throw e;
        } finally {
            log.info("인증 체크 필터 종료 {}",requestURI);
        }

    }

    //화이트 리스트의 경우 인증 체크 X
    private boolean isLoginCheckPath(String requestURI) {
        //match가 맞으면 true기 때문에, false를 반환하기 위해서 not을 붙인다.
        return !PatternMatchUtils.simpleMatch(whiteList,requestURI);
    }


//
//    @Override
//    public void destroy() {
//        Filter.super.destroy();
//    }
}
