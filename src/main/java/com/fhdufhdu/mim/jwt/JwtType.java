package com.fhdufhdu.mim.jwt;

public enum JwtType {
    ACCESS("JWT-ACCESS-TOKEN"), REFRESH("JWT-REFRESH-TOKEN");

    private String type;

    private JwtType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static JwtType getEnum(String value) {
        if (value.equals(ACCESS.getType())) {
            return ACCESS;
        }
        return REFRESH;
    }
}
