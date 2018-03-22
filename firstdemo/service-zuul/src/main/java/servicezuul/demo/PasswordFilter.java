package servicezuul.demo;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class PasswordFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 2;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        return (boolean) ctx.get("isSuccess");// 如果前一个过滤器的结果为true，则说明上一个过滤器成功了，需要进入当前的过滤，如果前一个过滤器的结果为false，则说明上一个过滤器没有成功，则无需进行下面的过滤动作了，直接跳过后面的所有过滤器并返回结果
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String password = request.getParameter("password");
        if("123456".equals(password)){
            ctx.setSendZuulResponse(true);
            ctx.setResponseStatusCode(200);
            ctx.set("isSuccess", true);
        } else{
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            ctx.set("isSuccess", false);
            try {
                ctx.getResponse().getWriter().write("密码错误");// 返回错误内容
            }catch (Exception e){}
        }
        return null;
    }
}
