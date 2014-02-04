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
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Properties;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
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
    /** 公開鍵/モジュラスのキー */
    private static final String KEY_PUBLIC_MODULUS = "%s.public.modulus";
    /** 公開鍵/公開指数のキー */
    private static final String KEY_PUBLIC_EXPONENT = "%s.public.exponent";
    /** 秘密鍵/モジュラスのキー */
    private static final String KEY_PRIVATE_MODULUS = "%s.private.modulus";
    /** 秘密鍵/公開指数のキー */
    private static final String KEY_PRIVATE_EXPONENT = "%s.private.exponent";
    /** RSA方式名 */
    private static final String RSA_ALGO_NAME = "RSA";
    /** 鍵長 */
    private static final int KEYSIZE = 2048;
    /** 乱数生成器 */
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    /** PBKDF2方式名 */
    private static final String PBKDF2_ALGO_NAME = "PBKDF2WithHmacSHA1";
    /** PBE方式のキー長 */
    private static final int PBE_KEY_LENGTH = 128;
    /** PBE方式の繰返し回数 */
    private static final int PBE_ITER_COUNT = 65536;
    /** RSA変換名 */
    private static final String RSA_TRANSFORM_NAME = "RSA/ECB/PKCS1Padding";
    /** AES変換名 */
    private static final String AES_TRANSFORM_NAME = "AES/CBC/PKCS5Padding";
    /** AES方式名 */
    private static final String AES_ALGO_NAME = "AES";
    /** 署名方式名 */
    private static final String SIGN_ALGO_NAME = "SHA512withRSA";
    /** プロパティ名 */
    private static final String PROPERTY_NAME = "security.properties";
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
     * RSA鍵ペアの保存
     * <dl>
     * <dt>使用条件
     * <dd>RSA鍵ペアを保存する。
     * </dl>
     * @param name RSA鍵名
     * @param keyPair RSA鍵ペア
     */
    public void saveKeyPair(final String name, final KeyPair keyPair) {
        try {
            final Properties property = new PropertyService(PROPERTY_NAME).getProperty();
            final RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            property.setProperty(String.format(KEY_PUBLIC_MODULUS, name),
                Hex.encodeHexString(publicKey.getModulus().toByteArray()));
            property.setProperty(String.format(KEY_PUBLIC_EXPONENT, name),
                Hex.encodeHexString(publicKey.getPublicExponent().toByteArray()));
            final RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            property.setProperty(String.format(KEY_PRIVATE_MODULUS, name),
                Hex.encodeHexString(privateKey.getModulus().toByteArray()));
            property.setProperty(String.format(KEY_PRIVATE_EXPONENT, name),
                Hex.encodeHexString(privateKey.getPrivateExponent().toByteArray()));
            try (FileOutputStream stream = new FileOutputStream(Thread.currentThread()
                .getContextClassLoader().getResource(PROPERTY_NAME).getPath());) {
                property.store(stream, "key pair saved.");
            }
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * RSA鍵ペアの復元
     * <dl>
     * <dt>使用条件
     * <dd>RSA鍵ペアを復元する。
     * </dl>
     * @param name RSA鍵名
     * @return RSA鍵ペア
     */
    public KeyPair loadKeyPair(final String name) {
        try {
            final Properties property = new PropertyService(PROPERTY_NAME).getProperty();
            final byte[] publicModulus = Hex.decodeHex(property.getProperty(
                String.format(KEY_PUBLIC_MODULUS, name)).toCharArray());
            final byte[] publicExponent = Hex.decodeHex(property.getProperty(
                String.format(KEY_PUBLIC_EXPONENT, name)).toCharArray());
            final byte[] privateModulus = Hex.decodeHex(property.getProperty(
                String.format(KEY_PRIVATE_MODULUS, name)).toCharArray());
            final byte[] privateExponent = Hex.decodeHex(property.getProperty(
                String.format(KEY_PRIVATE_EXPONENT, name)).toCharArray());
            final RSAPublicKey publicKey = (RSAPublicKey) KeyFactory.getInstance(RSA_ALGO_NAME)
                .generatePublic(
                    new RSAPublicKeySpec(new BigInteger(publicModulus), new BigInteger(
                        publicExponent)));
            final RSAPrivateKey privateKey = (RSAPrivateKey) KeyFactory.getInstance(RSA_ALGO_NAME)
                .generatePrivate(
                    new RSAPrivateKeySpec(new BigInteger(privateModulus), new BigInteger(
                        privateExponent)));
            return new KeyPair(publicKey, privateKey);
        } catch (final IOException | DecoderException | InvalidKeySpecException
            | NoSuchAlgorithmException e) {
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
                final byte[] modulus = key.getModulus().toByteArray();
                dos.writeInt(modulus.length);
                dos.write(modulus);
                final byte[] publicExponent = key.getPublicExponent().toByteArray();
                dos.writeInt(publicExponent.length);
                dos.write(publicExponent);
            }
            return file;
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 共通鍵の作成
     * <dl>
     * <dt>使用条件
     * <dd>PBKDF2方式によりパスワードとソルトで共通鍵を作成する。
     * </dl>
     * @param password パスワード
     * @param salt ソルト
     * @return 共通鍵(秘密鍵)
     */
    public byte[] createCommonKey(final char[] password, final byte[] salt) {
        try {
            return SecretKeyFactory.getInstance(PBKDF2_ALGO_NAME)
                .generateSecret(new PBEKeySpec(password, salt, PBE_ITER_COUNT, PBE_KEY_LENGTH))
                .getEncoded();
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
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, AES_ALGO_NAME));
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
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, AES_ALGO_NAME),
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
    /**
     * 平文データの署名
     * <dl>
     * <dt>使用条件
     * <dd>SHA-512とRSA方式により秘密鍵と平文データを署名する。
     * </dl>
     * @param key 秘密鍵
     * @param plain 平文データ
     * @return 署名
     */
    public byte[] signature(final PrivateKey key, final byte[] plain) {
        try {
            final Signature signatureSign = Signature.getInstance(SIGN_ALGO_NAME);
            signatureSign.initSign(key, SECURE_RANDOM);
            signatureSign.update(plain);
            return signatureSign.sign();
        } catch (final NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 平文データの検証
     * <dl>
     * <dt>使用条件
     * <dd>SHA-512とRSA方式により公開鍵と署名から平文データを検証する。
     * </dl>
     * @param key 公開鍵
     * @param signature 署名
     * @param plain 平文データ
     * @return 検証結果
     */
    public boolean verify(final PublicKey key, final byte[] signature, final byte[] plain) {
        try {
            final Signature verifier = Signature.getInstance(SIGN_ALGO_NAME);
            verifier.initVerify(key);
            verifier.update(plain);
            return verifier.verify(signature);
        } catch (final NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            throw new RuntimeException(e);
        }
    }
}
