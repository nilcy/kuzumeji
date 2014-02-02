// ----------------------------------------------------------------------------
// Copyright (C) Kuzumeji Evolution Laboratory. All rights reserved.
// GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007
// http://www.gnu.org/licenses/gpl-3.0-standalone.html
// ----------------------------------------------------------------------------
package com.kuzumeji.platform.standard;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import org.apache.commons.lang3.SystemUtils;
/**
 * プラットフォームのプロパティ
 * <dl>
 * <dt>使用条件
 * <dd>TODO 各操作前後の不変条件を表明すること。(DDD/契約による設計)
 * </dl>
 * @author nilcy
 */
public final class PlatformProperty {
    /** ファイルのエンコーディング */
    public static final String FILE_ENCODING = getProperty().getProperty("FILE_ENCODING",
        SystemUtils.FILE_ENCODING);
    /** ファイルのセパレータ */
    public static final String FILE_SEPARATOR = getProperty().getProperty("FILE_SEPARATOR",
        SystemUtils.FILE_SEPARATOR);
    /** 一時ディレクトリ */
    public static final String JAVA_IO_TMPDIR = getProperty().getProperty("JAVA_IO_TMPDIR",
        SystemUtils.JAVA_IO_TMPDIR);
    /** テキスト行のセパレータ */
    public static final String LINE_SEPARATOR = getProperty().getProperty("LINE_SEPARATOR",
        SystemUtils.LINE_SEPARATOR);
    /** パスのセパレータ */
    public static final String PATH_SEPARATOR = getProperty().getProperty("PATH_SEPARATOR",
        SystemUtils.PATH_SEPARATOR);
    /** 国 */
    public static final String COUNTRY = getProperty().getProperty("COUNTRY",
        SystemUtils.USER_COUNTRY);
    /** 言語 */
    public static final String LANGUAGE = getProperty().getProperty("LANGUAGE",
        SystemUtils.USER_LANGUAGE);
    /** タイムゾーン */
    public static final String TIMEZONE = getProperty().getProperty("TIMEZONE",
        SystemUtils.USER_TIMEZONE);
    /** ロケール */
    public static final Locale LOCALE = new Locale(LANGUAGE, COUNTRY);
    /** プロパティ */
    private static Properties property;
    /** コンストラクタ */
    private PlatformProperty() {
    }
    /**
     * プロパティの取得
     * @return プロパティ
     */
    private static Properties getProperty() {
        try {
            property = new PropertyService("platform.properties").getProperty();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        return property;
    }
}
