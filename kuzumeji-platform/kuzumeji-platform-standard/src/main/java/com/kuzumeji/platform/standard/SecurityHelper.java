// ----------------------------------------------------------------------------
// Copyright (C) Kuzumeji Evolution Laboratory. All rights reserved.
// GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007
// http://www.gnu.org/licenses/gpl-3.0-standalone.html
// ----------------------------------------------------------------------------
package com.kuzumeji.platform.standard;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
/**
 * セキュリティヘルパー
 * <dl>
 * <dt>使用条件
 * <dd>TODO 各操作前後の不変条件を表明すること。(DDD/契約による設計)
 * </dl>
 * @author nilcy
 */
public final class SecurityHelper {
    /** キーペアのアルゴリズム */
    private static final String KEY_ALGORITHM = "EC";
    /** 署名のアルゴリズム */
    private static final String SIGN_ALGORITHM = "NONEwithECDSA";
    /** 乱数生成器 */
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    /** 鍵長 */
    private static final int KEYSIZE = 256;
    /** 非公開コンストラクタ */
    private SecurityHelper() {
    }
    /**
     * メッセージダイジェストの作成
     * <dl>
     * <dt>使用条件
     * <dd>TODO 事前条件(必要事項)と事後条件(保証要件)を表明すること。(DDD/契約による設計)
     * </dl>
     * @param message メッセージ
     * @return メッセージダイジェスト
     */
    public static byte[] digest(final byte[] message) {
        try {
            final MessageDigest md = MessageDigest.getInstance("SHA-512");
            return md.digest(message);
        } catch (final NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * キーペアの作成
     * <dl>
     * <dt>使用条件
     * <dd>TODO 事前条件(必要事項)と事後条件(保証要件)を表明すること。(DDD/契約による設計)
     * </dl>
     * @return キーペア(公開鍵と秘密鍵)
     */
    public static KeyPair generateKeyPair() {
        try {
            final KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            keyPairGenerator.initialize(KEYSIZE, SECURE_RANDOM);
            return keyPairGenerator.generateKeyPair();
        } catch (final NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * メッセージの署名
     * <dl>
     * <dt>使用条件
     * <dd>TODO 事前条件(必要事項)と事後条件(保証要件)を表明すること。(DDD/契約による設計)
     * </dl>
     * @param privateKey 秘密鍵
     * @param message メッセージ
     * @return 署名
     */
    public static byte[] signature(final PrivateKey privateKey, final byte[] message) {
        try {
            final Signature signatureSign = Signature.getInstance(SIGN_ALGORITHM);
            signatureSign.initSign(privateKey, SECURE_RANDOM);
            signatureSign.update(message);
            return signatureSign.sign();
        } catch (final NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (final InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (final SignatureException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * メッセージの検証
     * <dl>
     * <dt>使用条件
     * <dd>TODO 事前条件(必要事項)と事後条件(保証要件)を表明すること。(DDD/契約による設計)
     * </dl>
     * @param publicKey 公開鍵
     * @param message メッセージ
     * @param signature 署名
     * @return 検証結果
     */
    public static boolean verify(final PublicKey publicKey, final byte[] signature,
        final byte[] message) {
        try {
            final Signature verifier = Signature.getInstance(SIGN_ALGORITHM);
            verifier.initVerify(publicKey);
            verifier.update(message);
            return verifier.verify(signature);
        } catch (final NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (final InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (final SignatureException e) {
            throw new RuntimeException(e);
        }
    }
}
