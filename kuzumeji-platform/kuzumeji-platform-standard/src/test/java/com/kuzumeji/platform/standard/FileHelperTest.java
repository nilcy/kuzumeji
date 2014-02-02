// ----------------------------------------------------------------------------
// Copyright (C) Kuzumeji Evolution Laboratory. All rights reserved.
// GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007
// http://www.gnu.org/licenses/gpl-3.0-standalone.html
// ----------------------------------------------------------------------------
package com.kuzumeji.platform.standard;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import org.junit.Test;
/**
 * @see FileHelper
 * @author nilcy
 */
@SuppressWarnings({ "static-method", "javadoc", "boxing" })
public class FileHelperTest {
    /**
     * @see FileHelper#read(Path)
     */
    @Test
    public final void testPath() throws IOException {
        final Path path = Paths.get("src/test/resources/FileHelperTest.txt");
        final byte[] bytes = FileHelper.read(path);
        final String content = new String(bytes, StandardCharsets.UTF_8);
        assertThat(content, is("ふがふがほげほげぶろろろろ"));
    }
    /**
     * @see FileHelper#read(Path)
     */
    @Test
    public final void testPathCharset() throws IOException {
        final Path path = Paths.get("src/test/resources/FileHelperTest.txt");
        final Collection<String> texts = FileHelper.read(path, StandardCharsets.UTF_8);
        assertThat(texts.size(), is(1));
        assertThat(texts.iterator().next(), is("ふがふがほげほげぶろろろろ"));
    }
}
