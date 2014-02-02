// ----------------------------------------------------------------------------
// Copyright (C) Kuzumeji Evolution Laboratory. All rights reserved.
// GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007
// http://www.gnu.org/licenses/gpl-3.0-standalone.html
// ----------------------------------------------------------------------------
package com.kuzumeji.platform.standard;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Test;
/**
 * @see PlatformPropertyService
 * @author nilcy
 */
@SuppressWarnings({ "static-method", "javadoc" })
public class PlatformPropertyServiceTest {
    /**
     * @see PlatformPropertyService#PlatformPropertyService()
     * @see PlatformPropertyService#getProperty(String)
     */
    @Test
    public final void test() throws IOException {
        final PlatformPropertyService testee = new PlatformPropertyService();
        assertThat(testee.getProperty("line.separator"), is("\n"));
        assertThat(testee.getProperty("file.separator"), is("/"));
        assertThat(testee.getProperty("path.separator"), is(":"));
    }
}
