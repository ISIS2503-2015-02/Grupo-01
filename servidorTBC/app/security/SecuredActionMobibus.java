package security;


import play.libs.F;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import com.avaje.ebean.Model;

import models.*;

public class SecuredActionMobibus extends Action.Simple {
    public F.Promise<Result> call(Http.Context ctx) throws Throwable {
        String tokenCifrado = getTokenFromHeader(ctx);
        String tokenFirmado = getSignFronHeader(ctx);
        if (tokenCifrado != null && tokenFirmado != null) {

            String token = Crypto.decryptAES(tokenCifrado);

            Mobibus mobibus = (Mobibus) new Model.Finder(Long.class, Mobibus.class).where().eq("authToken", token).findUnique();
            if (mobibus != null) {
                String llave = mobibus.id;
                String verifica = Crypto.sign(token, llave.getBytes());
                if(verifica.equals(tokenFirmado)){
                    ctx.request().setUsername(mobibus.getId()+"");
                    return delegate.call(ctx);
                }
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

    private String getSignFronHeader(Http.Context ctx){
        String[]  authTokenHeaderValues = ctx.request().headers().get("X-SIGN-TOKEN");
        if((authTokenHeaderValues != null) && (authTokenHeaderValues.length == 1) && (authTokenHeaderValues[0] != null)){
            return authTokenHeaderValues[0];
        }
        return null;
    }
}