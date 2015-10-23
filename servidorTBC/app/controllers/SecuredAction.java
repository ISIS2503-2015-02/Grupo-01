package controllers;

import play.libs.F;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import com.avaje.ebean.Model;

import models.Usuario;

public class SecuredAction extends Action.Simple {
    public F.Promise<Result> call(Http.Context ctx) throws Throwable {
        String token = getTokenFromHeader(ctx);

        if (token != null) {

            //DESENCRIPTACION TODO

            String[] roltok = token.split(",");
            String rol = roltok[0];
            String tok = roltok[1];

            //Verificación rol

            //Usuario y admin
            if(rol.equals(Cons.ROL_USUARIO) || rol.equals(Cons.ROL_ADMIN)){
                Usuario user = (Usuario) new Model.Finder(Long.class, Usuario.class).where().eq("authToken", token).findUnique();
                if (user != null) {
                    if(user.getRol().equals(rol)){
                        ctx.request().setUsername(user.getUsuario;
                        return delegate.call(ctx);
                    }
                }
            }   

            //Tranvia
            Tranvia tranvia = (Tranvia) new Model.Finder(Long.class, Tranvia.class).where().eq("authToken", token).findUnique();
            if (tranvia != null) {
                    ctx.request().setUsername(tranvia.getId()+"");
                    return delegate.call(ctx);
            }
            //Mobibus
            Mobibus mobibus = (Mobibus) new Model.Finder(Long.class, Mobibus.class).where().eq("authToken", token).findUnique();
            if (mobibus != null) {
                    ctx.request().setUsername(mobibus.getId()+"");
                    return delegate.call(ctx);
            }

            //Estacion
            Estacion estacion = (Estacion) new Model.Finder(Long.class, Estacion.class).where().eq("authToken", token).findUnique();
            if (estacion != null) {
                    ctx.request().setUsername(estacion.getId()+"");
                    return delegate.call(ctx);
            }

            //Vcub
            Vcub vcub = (Vcub) new Model.Finder(Long.class, Vcub.class).where().eq("authToken", token).findUnique();
            if (vcub != null) {
                    ctx.request().setUsername(vcub.getId()+"");
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