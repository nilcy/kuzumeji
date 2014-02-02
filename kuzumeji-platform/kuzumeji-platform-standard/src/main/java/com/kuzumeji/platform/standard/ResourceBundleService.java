// ----------------------------------------------------------------------------
// Copyright (C) Kuzumeji Evolution Laboratory. All rights reserved.
// GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007
// http://www.gnu.org/licenses/gpl-3.0-standalone.html
// ----------------------------------------------------------------------------
package com.kuzumeji.platform.standard;
import java.util.Locale;
import java.util.ResourceBundle;
/**
 * リソースバンドル取得サービス
 * <dl>
 * <dt>使用条件
 * <dd>TODO 各操作前後の不変条件を表明すること。(DDD/契約による設計)
 * </dl>
 * @author nilcy
 */
public class ResourceBundleService implements Service {
    /** リソースバンドル */
    private final ResourceBundle bundle;
    /**
     * コンストラクタ
     * @param baseName リソースバンドルのベース名
     */
    public ResourceBundleService(final String baseName) {
        bundle = ResourceBundle.getBundle(baseName, Locale.getDefault(), Thread.currentThread()
            .getContextClassLoader());
    }
    /**
     * コンストラクタ
     * @param baseName リソースバンドルのベース名
     * @param locale ロケール
     */
    public ResourceBundleService(final String baseName, final Locale locale) {
        bundle = ResourceBundle.getBundle(baseName, locale, Thread.currentThread()
            .getContextClassLoader());
    }
    /**
     * {@link #bundle リソースバンドル} の取得
     * @return {@link #bundle リソースバンドル}
     */
    public ResourceBundle getBundle() {
        return bundle;
    }
}
