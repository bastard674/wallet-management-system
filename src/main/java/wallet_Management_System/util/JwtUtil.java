package wallet_Management_System.util;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

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
}
