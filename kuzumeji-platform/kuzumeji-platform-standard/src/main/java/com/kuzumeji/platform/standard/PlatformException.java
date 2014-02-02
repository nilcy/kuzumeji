// ----------------------------------------------------------------------------
// Copyright (C) Kuzumeji Evolution Laboratory. All rights reserved.
// GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007
// http://www.gnu.org/licenses/gpl-3.0-standalone.html
// ----------------------------------------------------------------------------
package com.kuzumeji.platform.standard;
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
}
