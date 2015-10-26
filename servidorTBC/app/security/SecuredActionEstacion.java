package security;


import play.libs.F;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import play.libs.Crypto;
import com.avaje.ebean.Model;
import utils.*;
import models.*;

public class SecuredActionEstacion extends Action.Simple {

    private final static Crypto crypto = play.Play.application().injector().instanceOf(Crypto.class);

    public F.Promise<Result> call(Http.Context ctx) throws Throwable {
        String tokenCifrado = getTokenFromHeader(ctx);
        String tokenFirmado = getSignFronHeader(ctx);
        if (tokenCifrado != null && tokenFirmado != null) {

            String token = crypto.decryptAES(tokenCifrado);
            Estacion estacion = (Estacion) new Model.Finder(Long.class, Estacion.class).where().eq("authToken", token).findUnique();
            if (estacion != null) {
                String llave = estacion.getId()+"";
                String verifica = crypto.sign(token, llave.getBytes());
                if(verifica.equals(tokenFirmado)){
                    ctx.request().setUsername(estacion.getId()+"");
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