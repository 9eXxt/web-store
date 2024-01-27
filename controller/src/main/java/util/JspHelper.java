package util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JspHelper {
    private static final String JSP_PATH = "/jsp/%s.jsp";
    public static String getPath(String jspName) {
        return JSP_PATH.formatted(jspName);
    }
}
