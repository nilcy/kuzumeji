// ----------------------------------------------------------------------------
// Copyright (C) Kuzumeji Evolution Laboratory. All rights reserved.
// GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007
// http://www.gnu.org/licenses/gpl-3.0-standalone.html
// ----------------------------------------------------------------------------
package com.kuzumeji.platform.standard;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import java.util.Locale;
import org.junit.Test;
/**
 * @see MessageService
 * @author nilcy
 */
public class MessageServiceTest {
    /** クラス名 */
    private final String canonicalName = MessageServiceTest.class.getCanonicalName();
    /**
     * @see MessageService#MessageService(String)
     * @see MessageService#build(String, Object...)
     */
    @Test
    public final void testDefaultBuild() {
        final MessageService testee = new MessageService(canonicalName);
        assertThat(testee.build("hello", "織田信長"), is("こんにちは 織田信長 さん"));
    }
    /**
     * @see MessageService#MessageService(String)
     * @see MessageService#build(String, Object...)
     */
    @Test
    public final void testLocaleBuild() {
        final MessageService testee = new MessageService(canonicalName, new Locale("en", "US"));
        assertThat(testee.build("hello", "Steve Vai"), is("Hello, Steve Vai."));
    }
}
