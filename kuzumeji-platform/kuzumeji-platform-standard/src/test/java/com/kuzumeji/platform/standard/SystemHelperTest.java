// ----------------------------------------------------------------------------
// Copyright (C) Kuzumeji Evolution Laboratory. All rights reserved.
// GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007
// http://www.gnu.org/licenses/gpl-3.0-standalone.html
// ----------------------------------------------------------------------------
package com.kuzumeji.platform.standard;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import java.util.Map.Entry;
import java.util.Properties;
import org.apache.commons.lang3.SystemUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @see SystemUtils
 * @author nilcy
 */
@SuppressWarnings({ "all" })
public class SystemHelperTest {
    private static final Logger LOG = LoggerFactory.getLogger(SystemHelperTest.class);
    @Test
    public final void testSystem() {
        final Properties property = System.getProperties();
        for (final Entry<Object, Object> entry : property.entrySet()) {
            LOG.debug("{} : {}", entry.getKey(), entry.getValue());
        }
    }
    @Test
    public final void test() {
        assertThat(SystemUtils.FILE_ENCODING, is("UTF-8"));
    }
}
