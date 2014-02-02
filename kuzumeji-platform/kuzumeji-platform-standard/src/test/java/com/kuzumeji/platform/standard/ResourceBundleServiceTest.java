// ----------------------------------------------------------------------------
// Copyright (C) Kuzumeji Evolution Laboratory. All rights reserved.
// GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007
// http://www.gnu.org/licenses/gpl-3.0-standalone.html
// ----------------------------------------------------------------------------
package com.kuzumeji.platform.standard;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import java.util.Locale;
import java.util.ResourceBundle;
import org.junit.Test;
/**
 * @see ResourceBundleService
 * @author nilcy
 */
public class ResourceBundleServiceTest {
    /** クラス名 */
    private final String canonicalName = ResourceBundleServiceTest.class.getCanonicalName();
    /** @see ResourceBundleService#getBundle() */
    @Test
    public final void testPlatform() {
        assertThat(canonicalName, is("com.kuzumeji.platform.standard.ResourceBundleServiceTest"));
        assertThat(Locale.getDefault().getLanguage(), is("ja"));
    }
    /**
     * @see ResourceBundleService#ResourceBundleService(String)
     * @see ResourceBundleService#getBundle()
     */
    @Test
    public final void testDefaultGet() {
        final ResourceBundleService testee = new ResourceBundleService(canonicalName);
        final ResourceBundle bundle = testee.getBundle();
        assertThat(bundle.getString("lang"), is("日本語"));
        assertThat(bundle.getString("country"), is("日本"));
    }
    /**
     * @see ResourceBundleService#ResourceBundleService(String, Locale)
     * @see ResourceBundleService#getBundle()
     */
    @Test
    public final void testLocaleGet() {
        final ResourceBundleService testee = new ResourceBundleService(canonicalName,
            Locale.ENGLISH);
        final ResourceBundle bundle = testee.getBundle();
        assertThat(bundle.getString("lang"), is("English"));
        assertThat(bundle.getString("country"), is("USA"));
    }
}
