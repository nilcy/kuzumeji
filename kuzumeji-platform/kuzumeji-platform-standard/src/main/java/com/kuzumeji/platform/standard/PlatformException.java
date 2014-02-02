// ----------------------------------------------------------------------------
// Copyright (C) Kuzumeji Evolution Laboratory. All rights reserved.
// GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007
// http://www.gnu.org/licenses/gpl-3.0-standalone.html
// ----------------------------------------------------------------------------
package com.kuzumeji.platform.standard;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
/**
 * プラットフォームのキャッチ例外
 * <dl>
 * <dt>使用条件
 * <dd>TODO 各操作前後の不変条件を表明すること。(DDD/契約による設計)
 * </dl>
 * @author nilcy
 */
public class PlatformException extends Exception {
    /** 識別番号 */
    private static final long serialVersionUID = -5801793177483331423L;
    /** 原因マップ(登録順を保証) */
    private final Map<String, Object[]> causeMap = new LinkedHashMap<>();
    /**
     * コンストラクタ
     * @see Exception#Exception()
     */
    public PlatformException() {
    }
    /**
     * コンストラクタ
     * @param message 例外メッセージ
     * @see Exception#Exception(String)
     */
    public PlatformException(final String message) {
        super(message);
    }
    /**
     * コンストラクタ
     * @param cause 例外オブジェクト
     * @see Exception#Exception(Throwable)
     */
    public PlatformException(final Throwable cause) {
        super(cause);
    }
    /**
     * コンストラクタ
     * @param message 例外メッセージ
     * @param cause 例外オブジェクト
     * @see Exception#Exception(String, Throwable)
     */
    public PlatformException(final String message, final Throwable cause) {
        super(message, cause);
    }
    /**
     * コンストラクタ
     * @param message 例外メッセージ
     * @param cause 例外オブジェクト
     * @param enableSuppression 抑制の有効性
     * @param writableStackTrace スタックトレース書込みの有効性
     * @see Exception#Exception(String, Throwable, boolean, boolean)
     */
    public PlatformException(final String message, final Throwable cause,
        final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    /**
     * コンストラクタ
     * @param key メッセージバンドルのキー
     * @param arguments メッセージテンプレートへの展開オブジェクト
     * @see Exception#Exception(String)
     */
    public PlatformException(final String key, final Object... arguments) {
        super(key);
        addCause(key, arguments);
    }
    /**
     * 原因コレクション追加
     * <dl>
     * <dt>使用条件
     * <dd>TODO 事前条件(必要事項)と事後条件(保証要件)を表明すること。(DDD/契約による設計)
     * </dl>
     * @param key メッセージバンドルのキー
     * @param arguments メッセージテンプレートへの展開オブジェクト
     */
    public void addCause(final String key, final Object... arguments) {
        causeMap.put(key, arguments);
    }
    /**
     * 原因メッセージ集合の取得
     * <dl>
     * <dt>使用条件
     * <dd>TODO 事前条件(必要事項)と事後条件(保証要件)を表明すること。(DDD/契約による設計)
     * </dl>
     * @return 原因メッセージ集合
     */
    public Collection<String> getCauseMessages() {
        final MessageService service = new MessageService("exception-message");
        final Collection<String> messages = new LinkedList<>();
        for (final Entry<String, Object[]> cause : causeMap.entrySet()) {
            messages.add(service.build(cause.getKey(), cause.getValue()));
        }
        return messages;
    }
    /**
     * {@inheritDoc}
     * <dl>
     * <dt>使用条件
     * <dd>TODO 事前条件(必要事項)と事後条件(保証要件)を表明すること。(DDD/契約による設計)
     * </dl>
     */
    @Override
    public String getLocalizedMessage() {
        if (causeMap.isEmpty()) {
            return super.getLocalizedMessage();
        }
        final StringBuilder builder = new StringBuilder();
        for (final String message : getCauseMessages()) {
            if (builder.length() > 0) {
                builder.append("\n");
            }
            builder.append(message);
        }
        return builder.toString();
    }
}
