// ----------------------------------------------------------------------------
// Copyright (C) Kuzumeji Evolution Laboratory. All rights reserved.
// GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007
// http://www.gnu.org/licenses/gpl-3.0-standalone.html
// ----------------------------------------------------------------------------
package com.kuzumeji.platform.standard;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.RSAPublicKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
/**
 * セキュリティサービス
 * <dl>
 * <dt>使用条件
 * <dd>TODO 各操作前後の不変条件を表明すること。(DDD/契約による設計)
 * </dl>
 * @author nilcy
 */
@SuppressWarnings("static-method")
public final class SecurityService implements Service {
    /** RSA方式名 */
    private static final String RSA_ALGO_NAME = "RSA";
    /** PBKDF2方式名 */
    private static final String PBKDF2_ALGO_NAME = "PBKDF2WithHmacSHA1";
    /** RSA変換名 */
    private static final String RSA_TRANSFORM_NAME = "RSA/ECB/PKCS1Padding";
    /** 方式名 */
    private static final String ALGO_NAME = "AES";
    /** AES変換名 */
    private static final String AES_TRANSFORM_NAME = "AES/CBC/PKCS5Padding";
    /** 乱数生成器 */
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    /** 鍵長 */
    private static final int KEYSIZE = 2048;
    /** コンストラクタ */
    public SecurityService() {
    }
    /**
     * RSA鍵ペア(公開鍵/秘密鍵)の作成
     * <dl>
     * <dt>使用条件
     * <dd>RSA鍵ペア(公開鍵/秘密鍵)を作成する。(鍵長=2048ビット)
     * </dl>
     * @return 鍵ペア
     */
    public KeyPair generateKeyPair() {
        try {
            final KeyPairGenerator keygen = KeyPairGenerator.getInstance(RSA_ALGO_NAME);
            keygen.initialize(KEYSIZE, SECURE_RANDOM);
            return keygen.generateKeyPair();
        } catch (final NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * RSA公開鍵ファイルの保存
     * <dl>
     * <dt>使用条件
     * <dd>RSA公開鍵でファイルを保存する。
     * </dl>
     * @param key RSA公開鍵
     * @return RSA公開鍵ファイル
     */
    public File savePublicKeyFile(final RSAPublicKey key) {
        try {
            final File file = File.createTempFile("public", ".key");
            try (FileOutputStream fos = new FileOutputStream(file);
                DataOutputStream dos = new DataOutputStream(fos)) {
                final byte[] byteModules = key.getModulus().toByteArray();
                dos.writeInt(byteModules.length);
                dos.write(byteModules);
                final byte[] bytePublicExponent = key.getPublicExponent().toByteArray();
                dos.writeInt(bytePublicExponent.length);
                dos.write(bytePublicExponent);
            }
            return file;
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * RSA公開鍵の作成
     * <dl>
     * <dt>使用条件
     * <dd>モジュラスと公開指数でRSA公開鍵を作成する。
     * </dl>
     * @param modulus モジュラス
     * @param publicExponent 公開指数
     * @return RSA公開鍵
     */
    public RSAPublicKey createPublicKey(final byte[] modulus, final byte[] publicExponent) {
        try {
            return (RSAPublicKey) KeyFactory.getInstance(RSA_ALGO_NAME).generatePublic(
                new RSAPublicKeySpec(new BigInteger(modulus), new BigInteger(publicExponent)));
        } catch (final NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 共通鍵(秘密鍵)の作成
     * <dl>
     * <dt>使用条件
     * <dd>パスワードとソルトで共通鍵(秘密鍵)を作成する。(反復回数=65536,鍵長=128)
     * </dl>
     * @param password パスワード
     * @param salt ソルト
     * @return 共通鍵(秘密鍵)
     */
    public byte[] createSecretKey(final char[] password, final byte[] salt) {
        try {
            final SecretKeyFactory factory = SecretKeyFactory.getInstance(PBKDF2_ALGO_NAME);
            final KeySpec spec = new PBEKeySpec(password, salt, 65536, 128);
            final SecretKey tmp = factory.generateSecret(spec);
            return tmp.getEncoded();
        } catch (final NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 平文データの暗号
     * <dl>
     * <dt>使用条件
     * <dd>RSA公開鍵で平文データを暗号する。
     * </dl>
     * @param key RSA公開鍵
     * @param plain 平文データ
     * @return 暗号データ
     */
    public byte[] encrypt(final RSAPublicKey key, final byte[] plain) {
        try {
            final Cipher cipher = Cipher.getInstance(RSA_TRANSFORM_NAME);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(plain);
        } catch (final NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
            | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 暗号データの復号
     * <dl>
     * <dt>使用条件
     * <dd>RSA秘密鍵で暗号データを復号する。
     * </dl>
     * @param key RSA秘密鍵
     * @param encrypted 暗号データ
     * @return 平文データ
     */
    public byte[] decrypt(final RSAPrivateKey key, final byte[] encrypted) {
        try {
            final Cipher cipher = Cipher.getInstance(RSA_TRANSFORM_NAME);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(encrypted);
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
            | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 平文データの保護
     * <dl>
     * <dt>使用条件
     * <dd>AES方式により共通鍵で平文データを保護する。
     * </dl>
     * @param key 共通鍵
     * @param plain 平文データ
     * @return {@link SecuredData 保護データ}
     */
    public SecuredData encrypt(final byte[] key, final byte[] plain) {
        try {
            final Cipher cipher = Cipher.getInstance(AES_TRANSFORM_NAME);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, ALGO_NAME));
            try (final ByteArrayOutputStream bos = new ByteArrayOutputStream();) {
                bos.write(cipher.doFinal(plain));
                return new SecuredData(bos.toByteArray(), cipher.getIV());
            }
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
            | IllegalBlockSizeException | BadPaddingException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 保護データの復号
     * <dl>
     * <dt>使用条件
     * <dd>AES方式により共通鍵で保護データを復号する。
     * </dl>
     * @param key 共通鍵
     * @param secured {@link SecuredData 保護データ}
     * @return 平文データ
     */
    public byte[] decrypt(final byte[] key, final SecuredData secured) {
        try {
            final Cipher cipher = Cipher.getInstance(AES_TRANSFORM_NAME);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, ALGO_NAME),
                new IvParameterSpec(secured.getVector()));
            try (final ByteArrayOutputStream stream = new ByteArrayOutputStream();) {
                stream.write(cipher.doFinal(secured.getEncrypted()));
                return stream.toByteArray();
            }
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
            | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException
            | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
