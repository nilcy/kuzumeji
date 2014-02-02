// ----------------------------------------------------------------------------
// Copyright (C) Kuzumeji Evolution Laboratory. All rights reserved.
// GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007
// http://www.gnu.org/licenses/gpl-3.0-standalone.html
// ----------------------------------------------------------------------------
package com.kuzumeji.platform.standard;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import java.io.IOException;
import java.util.Properties;
import org.junit.Test;
/**
 * @see PropertyService
 * @author nilcy
 */
@SuppressWarnings("javadoc")
public class PropertyServiceTest {
    private final String canonicalName = PropertyServiceTest.class.getCanonicalName();
    /**
     * @see PropertyService#PropertyService(String)
     * @see PropertyService#getProperty()
     */
    @Test
    public final void test() throws IOException {
        final String name = canonicalName.replace(".", "/") + ".properties";
        final PropertyService testee = new PropertyService(name);
        assertThat(testee, is(not(nullValue())));
        final Properties property = testee.getProperty();
        assertThat(property, is(not(nullValue())));
        assertThat(property.getProperty("FOO"), is("foo"));
        assertThat(property.getProperty("BAR"), is("bar"));
        assertThat(property.getProperty("BAZ"), is("baz"));
    }
}
