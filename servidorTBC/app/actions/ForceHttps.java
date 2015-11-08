package actions;

import exceptions.StatusMessages;
import play.Play;
import play.Logger;
import play.libs.F;
import play.mvc.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class ForceHttps  {

    @With(HttpsAction.class)
    @Target({ ElementType.TYPE, ElementType.METHOD })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Https {
        String value() default "*";
    }

    public static class HttpsAction extends Action<Https> {
        private static final String SSL_HEADER = "X-Forwarded-Proto";

        @Override
        public F.Promise<Result> call(Http.Context context) throws Throwable {
            Logger.info("PROD: " + Play.isProd());
            Logger.info("IS REQUEST: " + !isHttpsRequest(context.request()));
            if (Play.isProd() && !isHttpsRequest( context.request() )) {
                return F.Promise.promise(() -> status(StatusMessages.C_SSL_REQUIRED,StatusMessages.M_SSL_REQUIRED));
            }

            // let request proceed
            return this.delegate.call(context);
        }

        private static boolean isHttpsRequest(Http.Request request) {
            // heroku passes header on
            Logger.info("SSL HEADER: " + request.getHeader(SSL_HEADER));
            Logger.info("SSL CONTIENE: " + request.getHeader(SSL_HEADER).contains("https"));
            return request.getHeader(SSL_HEADER) != null && request.getHeader(SSL_HEADER).contains("https");
        }
    }
}