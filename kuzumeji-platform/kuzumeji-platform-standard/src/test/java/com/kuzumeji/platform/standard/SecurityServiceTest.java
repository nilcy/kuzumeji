// ----------------------------------------------------------------------------
// Copyright (C) Kuzumeji Evolution Laboratory. All rights reserved.
// GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007
// http://www.gnu.org/licenses/gpl-3.0-standalone.html
// ----------------------------------------------------------------------------
package com.kuzumeji.platform.standard;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import java.io.File;
import java.security.Key;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.MessageFormat;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @see SecurityService
 * @author nilcy
 */
@SuppressWarnings("all")
public class SecurityServiceTest {
    SecurityService testee = new SecurityService();
    private static final Logger LOG = LoggerFactory.getLogger(SecurityServiceTest.class);
    @Test
    public void testKeyPair() {
        // RSA鍵ペアの作成
        final KeyPair keyPair = testee.generateKeyPair();
        final RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        final RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        LOG.debug("公開鍵->{}", dumpKeyPair(publicKey));
        LOG.debug("秘密鍵->{}", dumpKeyPair(privateKey));
        // RSA鍵ペアの保存/復元
        testee.saveKeyPair("testee", keyPair);
        final KeyPair keyPair2 = testee.loadKeyPair("testee");
        assertThat(keyPair2.getPublic().getAlgorithm(), is(publicKey.getAlgorithm()));
        assertThat(keyPair2.getPublic().getFormat(), is(publicKey.getFormat()));
        assertThat(keyPair2.getPublic().getEncoded(), is(publicKey.getEncoded()));
        assertThat(keyPair2.getPrivate().getAlgorithm(), is(privateKey.getAlgorithm()));
        assertThat(keyPair2.getPrivate().getFormat(), is(privateKey.getFormat()));
        assertThat(keyPair2.getPrivate().getEncoded(), is(privateKey.getEncoded()));
        // 公開鍵ファイルの保存(対向システム配布用)
        final File file = testee.savePublicKeyFile(publicKey);
        LOG.debug("公開鍵ファイルパス : {}", file.getPath());
    }
    @Test
    public void testEncryptDecrypt() {
        // RSA鍵ペアの作成
        final KeyPair keyPair = testee.generateKeyPair();
        final RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        final RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        // 共通鍵の作成
        final char[] password = "hello, world!!".toCharArray();
        final byte[] salt = new byte[] { 1 };
        final byte[] secretKey = testee.createCommonKey(password, salt);
        LOG.debug("共通鍵 : {}", Hex.encodeHexString(secretKey));
        // 共通鍵の暗号
        final byte[] encryptedSecretKey = testee.encrypt(publicKey, secretKey);
        LOG.debug("共通鍵の暗号 : {}", Hex.encodeHexString(encryptedSecretKey));
        // 共通鍵の復号
        final byte[] decryptedSecretKey = testee.decrypt(privateKey, encryptedSecretKey);
        assertThat(decryptedSecretKey, is(secretKey));
        LOG.debug("共通鍵の復号 : {}", Hex.encodeHexString(decryptedSecretKey));
        // 共通鍵でテキスト暗号
        final String text = "ふがふがほげほげぶろろろろ";
        LOG.debug("テキスト : {}", text);
        final SecuredData context = testee.encrypt(secretKey, text.getBytes());
        LOG.debug("テキスト暗号のデータ={}", Hex.encodeHexString(context.getEncrypted()));
        LOG.debug("テキスト暗号の初期化ベクトル(IV)={}", Hex.encodeHexString(context.getVector()));
        // 共通鍵でテキスト復号
        final byte[] decryptedMessage = testee.decrypt(secretKey, context);
        assertThat(decryptedMessage, is(text.getBytes()));
        LOG.debug("テキスト復号 : {}", new String(decryptedMessage));
    }
    private static String dumpKeyPair(final Key key) {
        return MessageFormat.format("方式:{0} 形式:{1} 鍵:{2}", key.getAlgorithm(), key.getFormat(),
            Hex.encodeHexString(key.getEncoded()));
    }
    @Test
    public void testSignature() {
        final KeyPair keyPair = testee.generateKeyPair();
        final RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        final RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        final byte[] message = "ふがふがほげほげぶろろろろ".getBytes();
        final byte[] signature = testee.signature(keyPair.getPrivate(), message);
        assertThat(testee.verify(keyPair.getPublic(), signature, message), is(true));
    }
}
