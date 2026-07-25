package pl.teksusik.kick4j.kicks;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class KicksLeaderboardEntry {
    private final Integer giftedAmount;
    private final Integer rank;
    private final Integer userId;
    private final String username;

    @JsonCreator
    public KicksLeaderboardEntry(@JsonProperty("gifted_amount") Integer giftedAmount,
                                 @JsonProperty("rank") Integer rank,
                                 @JsonProperty("user_id") Integer userId,
                                 @JsonProperty("username") String username) {
        this.giftedAmount = giftedAmount;
        this.rank = rank;
        this.userId = userId;
        this.username = username;
    }

    public Integer getGiftedAmount() {
        return giftedAmount;
    }

    public Integer getRank() {
        return rank;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }
}
