package vn.com.easyjob.util;

public enum RedisKey {
    FORGOT_PASSWORD_KEY("forgot_password");

    private final String key;

    RedisKey(String redisKey) {
        this.key = redisKey;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return key;
    }
}
