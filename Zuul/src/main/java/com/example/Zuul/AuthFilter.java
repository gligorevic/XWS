package com.example.Zuul;

import com.example.Zuul.client.AuthClient;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class AuthFilter extends ZuulFilter {

    @Autowired
    private AuthClient authClient;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        if(request.getHeader("Authorization") == null) {
            return null;
        }

        String bearerToken = authClient.verify(request.getHeader("Authorization"));

        ctx.addZuulRequestHeader("Authorization", bearerToken);

        System.out.println(request.getHeader("Authorization"));
        System.out.println("Unutar filtera");

        return null;
    }
}
