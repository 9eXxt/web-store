package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/locale")
public class LocaleServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String language = req.getParameter("lang");
        req.getSession().setAttribute("lang",language);

        Cookie langCookie = getLangCookie(req, language);
        resp.addCookie(langCookie);

        resp.sendRedirect(req.getHeader("referer"));
//        String prevPage = req.getHeader("referer");
//        String page = prevPage != null ? prevPage : "/shop/login";
//        if(!page.contains("lang")){
//            resp.sendRedirect(page + (page.contains("?") ? "&lang=" + language : "?lang=" + language));
//        } else {
//            resp.sendRedirect(page.replaceAll("lang=[^&]*", "lang=" + language));
//        }
    }

    private static Cookie getLangCookie(HttpServletRequest req, String language) {
        Cookie langCookie = null;
        Cookie[] cookies = req.getCookies();
        if(cookies != null) {
            for(Cookie cookie : cookies) {
                if("userLang".equals(cookie.getName())) {
                    langCookie = cookie;
                    break;
                }
            }
        }

        if (langCookie == null) {
            langCookie = new Cookie("userLang", language);
            langCookie.setMaxAge(60 * 60 * 24 * 30);
        } else {
            langCookie.setValue(language);
        }
        return langCookie;
    }
}
