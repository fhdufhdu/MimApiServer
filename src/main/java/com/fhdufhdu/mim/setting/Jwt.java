package com.fhdufhdu.mim.setting;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Base64;

public class Jwt {
    private static String secretKey = null;
    public static final String CLAIMES_KEY_ROLES = "roles";
    public static final String CLAIMES_KEY_IP = "ip";
    public static final String ACCESS_HEADER = "X-ACCESS-TOKEN";
    public static final String REFRESH_HEADER = "X-REFRESH-TOKEN";

    public static String getSecretKey() {
        if (secretKey == null) {
            try (
                    FileReader fr = new FileReader("./.secret-key");
                    BufferedReader br = new BufferedReader(fr)) {
                secretKey = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

}
