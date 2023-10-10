package com.roshan.linkshortener.utilities;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Date;

import com.google.common.base.Strings;
import org.apache.commons.codec.digest.DigestUtils;
import org.bitcoinj.base.Base58;

public class ShortName {

    public static String generateShortLink(String actualLink) {
        String code = actualLink;
        String hashed = DigestUtils.sha256Hex(code.getBytes());
        String hash = Base58.encode(hashed.getBytes());

        return hash.substring(0, 8);
    }

}
