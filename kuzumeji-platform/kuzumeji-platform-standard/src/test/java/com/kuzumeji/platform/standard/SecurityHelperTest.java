// ----------------------------------------------------------------------------
// Copyright (C) Kuzumeji Evolution Laboratory. All rights reserved.
// GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007
// http://www.gnu.org/licenses/gpl-3.0-standalone.html
// ----------------------------------------------------------------------------
package com.kuzumeji.platform.standard;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import java.security.KeyPair;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;
/**
 * @see SecurityHelper
 * @author nilcy
 */
@SuppressWarnings("all")
public class SecurityHelperTest {
    @Test
    public void testDigest() {
        final byte[] message = "ふがふがほげほげぶろろろろ".getBytes();
        final byte[] digest = SecurityHelper.digest(message);
        assertThat(
            Hex.encodeHexString(digest),
            is("ffd0ed718ee77320095dfaa71217525a3d1ada061f14635904cf2c10bcc6c36932ee9a8b513f902e8a4b277c2e6c1e408a6077b0c0d774fa283faafa0138a7ff"));
    }
    @Test
    public void testSignature() {
        final byte[] message = "ふがふがほげほげぶろろろろ".getBytes();
        final KeyPair keyPair = SecurityHelper.generateKeyPair();
        final byte[] signature = SecurityHelper.signature(keyPair.getPrivate(), message);
        assertThat(SecurityHelper.verify(keyPair.getPublic(), signature, message), is(true));
    }
}
