// ----------------------------------------------------------------------------
// Copyright (C) Kuzumeji Evolution Laboratory. All rights reserved.
// GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007
// http://www.gnu.org/licenses/gpl-3.0-standalone.html
// ----------------------------------------------------------------------------
package com.kuzumeji.platform.standard;
import java.io.IOException;
import java.util.Properties;
/**
 * プラットフォームのプロパティ取得サービス
 * <dl>
 * <dt>使用条件
 * <dd>TODO 各操作前後の不変条件を表明すること。(DDD/契約による設計)
 * </dl>
 * @author nilcy
 */
public class PlatformPropertyService implements Service {
    /** プロパティ取得サービス */
    private final PropertyService service;
    /**
     * コンストラクタ
     * @throws IOException I/O例外
     */
    public PlatformPropertyService() throws IOException {
        service = new PropertyService("platform.properties");
    }
    /**
     * システム基盤プロパティ値の取得
     * <dl>
     * <dt>使用条件
     * <dd>TODO 各操作前後の不変条件を表明すること。(DDD/契約による設計)
     * </dl>
     * @param key システム基盤プロパティのキー
     * @return システム基盤プロパティ値
     */
    public String getProperty(final String key) {
        return getProperty().getProperty(key);
    }
    /**
     * システム基盤プロパティ値の取得
     * <dl>
     * <dt>使用条件
     * <dd>TODO 各操作前後の不変条件を表明すること。(DDD/契約による設計)
     * </dl>
     * @param key システム基盤プロパティのキー
     * @param defaultValue デフォルト値
     * @return システム基盤プロパティ値
     */
    public String getProperty(final String key, final String defaultValue) {
        return getProperty().getProperty(key, defaultValue);
    }
    /**
     * システム基盤プロパティの取得
     * @return システム基盤プロパティ
     */
    private Properties getProperty() {
        return service.getProperty();
    }
}
