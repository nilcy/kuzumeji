// ----------------------------------------------------------------------------
// Copyright (C) Kuzumeji Evolution Laboratory. All rights reserved.
// GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007
// http://www.gnu.org/licenses/gpl-3.0-standalone.html
// ----------------------------------------------------------------------------
package com.github.nilcy.kuzumeji.platform.standard;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @author nilcy
 */
@SuppressWarnings("all")
public class LoggerTest {
    private static final Logger LOG = LoggerFactory.getLogger(LoggerTest.class);
    @Test
    public void test() {
        LOG.trace("{}-{}-{}", "t0", "t1", "t2");
        LOG.debug("{}-{}-{}", "d0", "d1", "d2");
        LOG.info("{}-{}-{}", "i0", "i1", "i2");
        LOG.warn("{}-{}-{}", "w0", "w1", "w2");
        LOG.error("{}-{}-{}", "e0", "e1", "e2");
    }
}
