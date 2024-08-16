package io.hhplus.concert.domain.token;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Random;
import java.util.UUID;
public class RedisToken {

    @JsonProperty
    private String token;

    @JsonProperty
    private Long userId;


    public RedisToken(String token, Long userId) {
        this.token = token;
        this.userId = userId;
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }

    public static RedisToken fromJson(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, RedisToken.class);
    }
    public static String create(){
        UUID uuid = generateType1UUID();
        String uuidString = uuid.toString();

        return uuidString;
    };

    public static UUID generateType1UUID() {
        long most64SigBits = get64MostSignificantBitsForVersion1();
        long least64SigBits = get64LeastSignificantBitsForVersion1();
        return new UUID(most64SigBits, least64SigBits); // 62dd98f0-bd8e-11ed-93ab-325096b39f47
    }

    /**
     * [MAC Address] MAC 주소 대신에 임의의 48비트 숫자를 생성합니다.(보안 우려로 이를 대체합니다)
     *
     * @return
     */
    private static long get64LeastSignificantBitsForVersion1() {
        Random random = new Random();
        long random63BitLong = random.nextLong() & 0x3FFFFFFFFFFFFFFFL;
        long variant3BitFlag = 0x8000000000000000L;
        return random63BitLong | variant3BitFlag;
    }
    /*
     * [TimeStamp] 타임스템프를 이용하여 64개의 최상위 비트를 생성합니다.
     */
    private static long get64MostSignificantBitsForVersion1() {
        final long currentTimeMillis = System.currentTimeMillis();
        final long time_low = (currentTimeMillis & 0x0000_0000_FFFF_FFFFL) << 32;
        final long time_mid = ((currentTimeMillis >> 32) & 0xFFFF) << 16;
        final long version = 1 << 12;
        final long time_hi = ((currentTimeMillis >> 48) & 0x0FFF);
        return time_low | time_mid | version | time_hi;
    }
}
