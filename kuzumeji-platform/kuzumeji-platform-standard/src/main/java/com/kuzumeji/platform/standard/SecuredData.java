// ----------------------------------------------------------------------------
// Copyright (C) Kuzumeji Evolution Laboratory. All rights reserved.
// GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007
// http://www.gnu.org/licenses/gpl-3.0-standalone.html
// ----------------------------------------------------------------------------
package com.kuzumeji.platform.standard;
/**
 * 保護データ
 * <dl>
 * <dt>使用条件
 * <dd>暗号データと初期化ベクトル(IV)の値オブジェクト。
 * </dl>
 * @author nilcy
 */
public class SecuredData {
    /** 暗号データ */
    private final byte[] encrypted;
    /** 初期化ベクトル(IV) */
    private final byte[] vector;
    /**
     * コンストラクタ
     * @param encrypted {@link #encrypted 暗号データ}
     * @param vector {@link #vector 初期化ベクトル(IV)}
     */
    public SecuredData(final byte[] encrypted, final byte[] vector) {
        this.encrypted = encrypted;
        this.vector = vector;
    }
    /**
     * {@link #encrypted 暗号データ} の取得
     * @return {@link #encrypted 暗号データ}
     */
    public byte[] getEncrypted() {
        return encrypted;
    }
    /**
     * {@link #vector 初期化ベクトル(IV)} の取得
     * @return {@link #vector 初期化ベクトル(IV)}
     */
    public byte[] getVector() {
        return vector;
    }
}
