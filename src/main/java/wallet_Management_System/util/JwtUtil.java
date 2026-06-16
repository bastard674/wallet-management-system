package wallet_Management_System.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {
    private static final String SECRET =
            "walletmanagementsystemsecretkeywalletmanagementsystem";

    public String generateToken(String phoneNumber){
        return Jwts.builder().
                subject(phoneNumber)
                .issuedAt(new Date())
                .expiration(
                        new Date(System.currentTimeMillis()+ 1000*60*60)
                )
                .signWith(getSignKey())
                .compact();
    }

    private Key getSignKey(){
        byte[] keyBytes = Decoders.BASE64.decode(Base64.getEncoder().
                encodeToString(SECRET.getBytes()));
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser().verifyWith((SecretKey) getSignKey()).build().
                parseSignedClaims(token).getPayload();
    }

    public String extractPhoneNumber(String token){
        return extractAllClaims(token).getSubject();
    }

    public Date extractExpiration(String token){
        return extractAllClaims(token).getExpiration();
    }

    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
    public boolean isTokenValid(String token,
                                String phoneNumber) {

        String extractedPhoneNumber =
                extractPhoneNumber(token);

        return extractedPhoneNumber.equals(phoneNumber)
                && !isTokenExpired(token);
    }

}
