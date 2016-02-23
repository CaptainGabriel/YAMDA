package utils;

import com.example.ricardopeixe.moviedb.utils.MD5Util;
import com.example.ricardopeixe.moviedb.utils.Utils;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Unit tests related to the creation of md5 digest messages from a given email address.
 *
 * @version 0.0.1
 */
public class MD5UtilsTest {

    @Test
    public void PedroGravatarHashTest(){
        final String expectedHash = "c01f63cb73ce4b834e7ed68251119192";
        String hash = MD5Util.md5Hex(Utils.PEDRO_EMAIL_ADDR);
        Assert.assertEquals(expectedHash, hash);
    }

    @Test
    public void RicardoGravatarHashTest(){
        final String expectedHash = "07164d51678b78cb0d21107e52d7d93e";
        String hash = MD5Util.md5Hex(Utils.RICARDO_EMAIL_ADDR);
        Assert.assertEquals(expectedHash, hash);
    }

    @Test
    public void JoseGravatarHashTest(){
        final String expectedHash = "95b1d6bc7a105d682a483c36201b1d5e";
        String hash = MD5Util.md5Hex(Utils.JOSE_EMAIL_ADDR);
        Assert.assertEquals(expectedHash, hash);
    }
}
