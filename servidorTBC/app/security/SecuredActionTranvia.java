package security;


import play.libs.F;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import com.avaje.ebean.Model;

import models.*;

public class SecuredActionTranvia extends Action.Simple {
    public F.Promise<Result> call(Http.Context ctx) throws Throwable {
        String token = getTokenFromHeader(ctx);

        if (token != null) {

            Tranvia tranvia = (Tranvia) new Model.Finder(Long.class, Tranvia.class).where().eq("authToken", token).findUnique();
            if (tranvia != null) {
                    ctx.request().setUsername(tranvia.getId()+"");
                    return delegate.call(ctx);
            }


        }
        Result unauthorized = Results.unauthorized("unauthorized");
        return F.Promise.pure(unauthorized);
    }



    private String getTokenFromHeader(Http.Context ctx) {
        String[] authTokenHeaderValues = ctx.request().headers().get("X-AUTH-TOKEN");
        if ((authTokenHeaderValues != null) && (authTokenHeaderValues.length == 1) && (authTokenHeaderValues[0] != null)) {
            return authTokenHeaderValues[0];
        }
        return null;
    }
}