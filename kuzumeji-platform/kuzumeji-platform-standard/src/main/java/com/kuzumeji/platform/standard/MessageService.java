// ----------------------------------------------------------------------------
// Copyright (C) Kuzumeji Evolution Laboratory. All rights reserved.
// GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007
// http://www.gnu.org/licenses/gpl-3.0-standalone.html
// ----------------------------------------------------------------------------
package com.kuzumeji.platform.standard;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
/**
 * メッセージ構築サービス
 * <dl>
 * <dt>使用条件
 * <dd>TODO 各操作前後の不変条件を表明すること。(DDD/契約による設計)
 * </dl>
 * @author nilcy
 */
public class MessageService implements Service {
    /** リソースバンドル取得サービス */
    private final ResourceBundleService service;
    /**
     * コンストラクタ
     * @param baseName リソースバンドルのベース名
     */
    public MessageService(final String baseName) {
        service = new ResourceBundleService(baseName);
    }
    /**
     * コンストラクタ
     * @param baseName リソースバンドルのベース名
     * @param locale ロケール
     */
    public MessageService(final String baseName, final Locale locale) {
        service = new ResourceBundleService(baseName, locale);
    }
    /**
     * メッセージ構築
     * <dl>
     * <dt>使用条件
     * <dd>
     * <ol>
     * <li>キーがNULLのとき {@link NullPointerException} がスローされる。</li>
     * <li>メッセージバンドルに該当キーがないとき {@link java.util.MissingResourceException} がスローされる。</li>
     * <li>メッセージテンプレートが不正なとき {@link IllegalArgumentException} がスローされる。</li>
     * </ol>
     * </dl>
     * @param key メッセージバンドルのキー
     * @param arguments メッセージテンプレートへの展開オブジェクト
     * @return メッセージ
     */
    public String build(final String key, final Object... arguments) {
        final String pattern = (String) getBundle().getObject(key);
        return MessageFormat.format(pattern, arguments);
    }
    /**
     * リソースバンドルの取得
     * @return リソースバンドル
     */
    private ResourceBundle getBundle() {
        return service.getBundle();
    }
}
