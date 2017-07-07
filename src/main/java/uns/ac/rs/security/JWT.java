package uns.ac.rs.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;


public class JWT {

    private static String signature = "srb123";

    //Sample method to construct a JWT
    public static String createJWT(String username,  String role) {

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("role", role);

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(username)
                .setClaims(claims)
                .signWith(signatureAlgorithm, signature);

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }


    //Sample method to validate and read the JWT
    public static JSONObject parseJWT(String jwt) {

        //This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parser()
                .setSigningKey(signature)
                .parseClaimsJws(jwt).getBody();

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user", claims.getSubject());
        jsonObject.put("role", claims.get("role"));

        return jsonObject;

    }


    public static String parseRequest(HttpServletRequest request, String data){
        String response = "";
        if((request.getHeader("authorization")) == null || request.getHeader("authorization").equalsIgnoreCase("")){
            response = "Guest";
        }else{

            String token = request.getHeader("authorization");
            JSONObject json = JWT.parseJWT(token);
            response = json.get(data).toString();
        }

        return response;
    }

}