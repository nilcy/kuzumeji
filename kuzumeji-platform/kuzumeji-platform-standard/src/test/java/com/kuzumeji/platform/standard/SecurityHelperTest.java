// ----------------------------------------------------------------------------
// Copyright (C) Kuzumeji Evolution Laboratory. All rights reserved.
// GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007
// http://www.gnu.org/licenses/gpl-3.0-standalone.html
// ----------------------------------------------------------------------------
package com.kuzumeji.platform.standard;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import java.security.KeyPair;
import java.util.UUID;
import org.junit.Test;
/**
 * @see SecurityHelper
 * @author nilcy
 */
@SuppressWarnings("all")
public class SecurityHelperTest {
    @Test
    public void test() {
        final byte[] message = UUID.randomUUID().toString().getBytes();
        final KeyPair keyPair = SecurityHelper.generateKeyPair();
        final byte[] signature = SecurityHelper.signature(keyPair.getPrivate(), message);
        assertThat(SecurityHelper.verify(keyPair.getPublic(), signature, message), is(true));
    }
}
