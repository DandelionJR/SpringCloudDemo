package servicezuul.demo;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;


@Component
public class NameFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";// 前置过滤器
    }

    @Override
    public int filterOrder() {
        return 1;// 优先级为0，数字越大，优先级越低
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        return (boolean) ctx.get("isSuccess");// 是否执行该过滤器，此处为true，说明需要过滤
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String username = request.getParameter("name");//获取请求的参数
        if("haha".equals(username)){
            ctx.setSendZuulResponse(true);// 对该请求进行路由
            ctx.setResponseStatusCode(200);
            ctx.set("isSuccess",true);// 设值，让下一个Filter看到上一个Filter的状态
        } else{
            ctx.setSendZuulResponse(false);// 过滤该请求，不对其进行路由
            ctx.setResponseStatusCode(401);// 返回错误码
            ctx.set("isSuccess",false);
            try {
                ctx.getResponse().getWriter().write("用户名错误");// 返回错误内容
            }catch (Exception e){}
        }
        return null;
    }
}
