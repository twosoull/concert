package io.hhplus.concert.domain.entity;

import io.hhplus.concert.common.enums.TokenStatus;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Entity
@SqlResultSetMapping(
        name = "CheckTokenResultMapping",
        classes = @ConstructorResult(
                targetClass = io.hhplus.concert.domain.command.TokenCommand.CheckTokenResultDto.class,
                columns = {
                        @ColumnResult(name = "status", type = TokenStatus.class),
                        @ColumnResult(name = "token_id", type = Long.class),
                        @ColumnResult(name = "count", type = int.class)
                }
        )
)
@NamedNativeQuery(
        name = "Token.findByActiveTokenAndCountAndTopId",
        query = "SELECT t1_0.status, t1_0.token_id, (SELECT COUNT(*) FROM token t1_1 WHERE t1_1.status = 'ACTIVE') AS count " +
                "FROM token t1_0 WHERE t1_0.status = 'ACTIVE' GROUP BY t1_0.status, t1_0.user_id ORDER BY t1_0.user_id DESC LIMIT 1",
        resultSetMapping = "CheckTokenResultMapping"
)
@Getter
public class Token {

    @Id @GeneratedValue
    @Column(name = "token_id")
    private Long id;

    private String token;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String url;
    private LocalDateTime accessTime; //접속시간

    @Enumerated(EnumType.STRING)
    private TokenStatus status;
    private LocalDateTime createAt; //생성시간
    private LocalDateTime updateAt; //수정시간

    public void setStatus(TokenStatus status) {
        this.status = status;
    }

    public void setAccessTime(LocalDateTime accessTime) {
        this.accessTime = accessTime;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public Token() {
    }

    public Token(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public Token(String token, User user, String url, LocalDateTime accessTime, TokenStatus status, LocalDateTime createAt, LocalDateTime updateAt) {
        this.token = token;
        this.user = user;
        this.url = url;
        this.accessTime = accessTime;
        this.status = status;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public Token(Long id, String token, User user, String url, LocalDateTime accessTime, TokenStatus status, LocalDateTime createAt, LocalDateTime updateAt) {
        this.id = id;
        this.token = token;
        this.user = user;
        this.url = url;
        this.accessTime = accessTime;
        this.status = status;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public static String create(){
        UUID uuid = generateType1UUID();
        String uuidString = uuid.toString();

        return uuidString;
    };

    public void expiration(LocalDateTime now) {
        this.status = TokenStatus.EXPIRATION;
        this.updateAt = now;
    }

    /**
     * [공통함수] UUID v1을 생성하여 반환합니다.(MAC Address, TimeStamp 조합)
     *
     * @return
     */
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
