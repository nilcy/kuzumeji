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
 * @see PlatformProperty
 * @author nilcy
 */
@SuppressWarnings("all")
public class PlatformPropertyTest {
    @Test
    public final void test() {
        assertThat(PlatformProperty.FILE_ENCODING, is("UTF-8"));
        assertThat(PlatformProperty.FILE_SEPARATOR, is("/"));
        assertThat(PlatformProperty.JAVA_IO_TMPDIR, is("/tmp"));
        assertThat(PlatformProperty.LINE_SEPARATOR, is("\n"));
        assertThat(PlatformProperty.PATH_SEPARATOR, is(":"));
        assertThat(PlatformProperty.COUNTRY, is("JP"));
        assertThat(PlatformProperty.LANGUAGE, is("ja"));
        assertThat(PlatformProperty.TIMEZONE, is("Asia/Tokyo"));
        assertThat(PlatformProperty.LOCALE, is(Locale.JAPAN));
    }
}
