// ----------------------------------------------------------------------------
// Copyright (C) Kuzumeji Evolution Laboratory. All rights reserved.
// GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007
// http://www.gnu.org/licenses/gpl-3.0-standalone.html
// ----------------------------------------------------------------------------
package com.kuzumeji.platform.standard;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
/**
 * ファイルI/Oヘルパー
 * <dl>
 * <dt>使用条件
 * <dd>TODO 各操作前後の不変条件を表明すること。(DDD/契約による設計)
 * </dl>
 * @author nilcy
 */
public final class FileHelper {
    /** コンストラクタ */
    private FileHelper() {
    }
    /**
     * ファイル入力
     * <dl>
     * <dt>使用条件
     * <dd>TODO 事前条件(必要事項)と事後条件(保証要件)を表明すること。(DDD/契約による設計)
     * </dl>
     * @param path ファイルパス
     * @return バイト列
     * @throws IOException I/O例外
     */
    public static byte[] read(final Path path) throws IOException {
        return Files.readAllBytes(path);
    }
    /**
     * テキストファイル入力
     * <dl>
     * <dt>使用条件
     * <dd>TODO 事前条件(必要事項)と事後条件(保証要件)を表明すること。(DDD/契約による設計)
     * </dl>
     * @param path ファイルパス
     * @param cs 文字セット
     * @return テキスト集合
     * @throws IOException I/O例外
     */
    public static Collection<String> read(final Path path, final Charset cs) throws IOException {
        return Files.readAllLines(path, cs);
    }
}
