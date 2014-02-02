// ----------------------------------------------------------------------------
// Copyright (C) Kuzumeji Evolution Laboratory. All rights reserved.
// GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007
// http://www.gnu.org/licenses/gpl-3.0-standalone.html
// ----------------------------------------------------------------------------
package com.kuzumeji.platform.standard;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import java.util.Iterator;
import org.junit.Test;
/**
 * @see PlatformException
 * @author nilcy
 */
@SuppressWarnings({ "static-method", "boxing" })
public class PlatformExceptionTest {
    /**
     * @see PlatformException#PlatformException(String, Object...)
     * @see PlatformException#getCauseMessages()
     * @see PlatformException#getLocalizedMessage()
     */
    @Test
    public final void test() {
        final PlatformException testee = new PlatformException("KPSERR01", "testee");
        assertThat(testee.getCauseMessages().size(), is(1));
        final String expected = "該当データが検索できませんでした。(品番=testee)";
        assertThat(testee.getCauseMessages().iterator().next(), is(expected));
        assertThat(testee.getLocalizedMessage(), is(expected));
    }
    /**
     * @see PlatformException#PlatformException(String, Object...)
     * @see PlatformException#getCauseMessages()
     * @see PlatformException#getLocalizedMessage()
     */
    @Test
    public final void test2() {
        final PlatformException testee = new PlatformException("KPSERR01", "testee");
        testee.addCause("KPSERR02", "testee");
        assertThat(testee.getCauseMessages().size(), is(2));
        final String expected = "該当データが検索できませんでした。(品番=testee)";
        final String expected2 = "該当データが一意制約のため登録できませんでした。(品番=testee)";
        final Iterator<String> iter = testee.getCauseMessages().iterator();
        assertThat(iter.next(), is(expected));
        assertThat(iter.next(), is(expected2));
        assertThat(testee.getLocalizedMessage().startsWith(expected), is(true));
        assertThat(testee.getLocalizedMessage().endsWith(expected2), is(true));
    }
}
