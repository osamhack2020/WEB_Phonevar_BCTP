package kr.osam.phonevar.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import kr.osam.phonevar.domain.User;
import kr.osam.phonevar.domain.UserMinified;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class JwtFactory {
    public String encodeJwtWithUser(User user) {
        byte[] bytes = user.getServiceNumber().getBytes(StandardCharsets.UTF_8);

        return JWT.create()
                .withClaim("name", user.getName())
                .withClaim("serviceNumber", user.getServiceNumber())
                .sign(Algorithm.HMAC256(Base64.getEncoder().encodeToString(bytes)));
    }

    public UserMinified decodeJwt(String jwt) {
        UserMinified userMinified = new UserMinified();
        try {
            DecodedJWT decodedJWT = JWT.decode(jwt);
            String serviceNumber = decodedJWT.getClaim("serviceNumber").asString();
            byte[] bytes = serviceNumber.getBytes(StandardCharsets.UTF_8);

            Algorithm algorithm = Algorithm.HMAC256(Base64.getEncoder().encode(bytes));
            Verification jwtVerifier = JWT.require(algorithm);

            userMinified.setName(decodedJWT.getClaim("name").asString());
            userMinified.setServiceNumber(serviceNumber);
        }
        catch (JWTVerificationException ignored) { }

        return userMinified;
    }
}
