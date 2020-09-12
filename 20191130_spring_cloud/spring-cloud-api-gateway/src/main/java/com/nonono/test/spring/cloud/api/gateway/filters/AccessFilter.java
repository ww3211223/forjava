package com.nonono.test.spring.cloud.api.gateway.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求过滤器
 */
@Service
public class AccessFilter extends ZuulFilter {

    /**
     * 过滤器类型
     *
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤器执行顺序
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 判断该过滤器是否需要被执行
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 具体逻辑
     *
     * @return
     */
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        String accessToken = request.getParameter("accessToken");
        if (StringUtils.isEmpty(accessToken)) {
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
        }

        return null;
    }
}
