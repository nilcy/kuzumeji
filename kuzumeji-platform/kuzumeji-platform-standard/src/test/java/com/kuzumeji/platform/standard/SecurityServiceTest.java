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
    public void testGenerateKeyPair() {
        // RSA鍵ペアの作成
        final KeyPair keyPair = testee.generateKeyPair();
        final RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        final RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        LOG.debug("秘密鍵->{}", dumpKeyPair(privateKey));
        LOG.debug("公開鍵->{}", dumpKeyPair(privateKey));
        // 公開鍵ファイルの保存(対向システム配布用)
        final File file = testee.savePublicKeyFile(publicKey);
        LOG.debug("公開鍵ファイルパス : {}", file.getPath());
        // 公開鍵データ
        final byte[] modules = publicKey.getModulus().toByteArray();
        final byte[] exponent = publicKey.getPublicExponent().toByteArray();
        // RSA公開鍵の作成
        assertThat(testee.createPublicKey(modules, exponent), is(publicKey));
        // final RSAPublicKey publicKey2 = testee.createPublicKey(modules, exponent);
        // SecurityService.logKeyPair("RSA公開鍵-> ", publicKey2);
        // 共通鍵の作成
        final char[] password = "hello, world!!".toCharArray();
        final byte[] salt = new byte[] { 1 };
        final byte[] secretKey = testee.createSecretKey(password, salt);
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
        final byte[] decryptedMessage = testee.decrypt(secretKey, context);
        assertThat(decryptedMessage, is(text.getBytes()));
        LOG.debug("テキスト復号 : {}", new String(decryptedMessage));
    }
    private static String dumpKeyPair(final Key key) {
        return MessageFormat.format("方式:{0} 形式:{1} 鍵:{2}", key.getAlgorithm(), key.getFormat(),
            Hex.encodeHexString(key.getEncoded()));
    }
}
