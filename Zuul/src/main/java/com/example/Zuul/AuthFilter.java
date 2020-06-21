package com.example.Zuul;

import com.example.Zuul.client.AuthClient;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

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
        System.out.println("Usao sam u zularu");
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        System.out.println("Uzeo sam kontekst haaa");

        if(request.getHeader("Authorization") == null || request.getHeader("Auth") != null) {
            return null;
        }
        try {
            String accessToken = authClient.verify(request.getHeader("Authorization"));

            System.out.println("U zuul filteru");
            System.out.println(accessToken);

            ctx.addZuulRequestHeader("Auth", accessToken);
        } catch(Exception e) {

            System.out.println("Upadoh u catch");

            e.printStackTrace();
            setFailedRequest(e.getMessage(), 400);
        }
        System.out.println("Prosao sam filter");

        return null;
    }

    private void setFailedRequest(String body, int code) {
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.setResponseStatusCode(code);
        if (ctx.getResponseBody() == null) {
            ctx.setResponseBody(body);
            ctx.setSendZuulResponse(false);
        }
    }
}
