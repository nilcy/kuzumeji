// ----------------------------------------------------------------------------
// Copyright (C) Kuzumeji Evolution Laboratory. All rights reserved.
// GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007
// http://www.gnu.org/licenses/gpl-3.0-standalone.html
// ----------------------------------------------------------------------------
package com.kuzumeji.platform.standard;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
/**
 * プロパティ取得サービス
 * <dl>
 * <dt>使用条件
 * <dd>TODO 各操作前後の不変条件を表明すること。(DDD/契約による設計)
 * </dl>
 * @author nilcy
 */
public class PropertyService implements Service {
    /** プロパティ */
    private final Properties property;
    /**
     * コンストラクタ
     * @param name リソース名
     * @throws IOException I/O例外
     */
    public PropertyService(final String name) throws IOException {
        property = new Properties();
        try (InputStream stream = Thread.currentThread().getContextClassLoader()
            .getResourceAsStream(name);) {
            property.load(stream);
        }
    }
    /**
     * {@link #property プロパティ} の取得
     * @return {@link #property プロパティ}
     */
    public Properties getProperty() {
        return property;
    }
}
