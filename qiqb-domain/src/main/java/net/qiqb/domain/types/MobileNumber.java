package net.qiqb.domain.types;

import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 手机号
 */

public class MobileNumber implements Serializable {

    private final String number;

    public MobileNumber(String number) {
        if (number == null) {
            throw new IllegalArgumentException("mobile number must not null");
        }
        if (number.length() > 11) {
            // 手机号码不合法
            throw new IllegalArgumentException("Invalid mobile phone number");
        }
        if (!isMobile(number)) {
            throw new IllegalArgumentException("手机号码不正确");
        }
        this.number = number;

    }

    public String getNumber() {
        return this.number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MobileNumber that = (MobileNumber) o;

        return Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return number != null ? number.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "MobileNumber{" +
                "number='" + number + '\'' +
                '}';
    }

    public static boolean isMobile(final String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^1[3,4,5,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }
}
