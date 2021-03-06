// Copyright 2017 Sourcerer Inc. All Rights Reserved.
// Author: Anatoly Kislov (anatoly@sourcerer.io)

package app.utils

import java.io.File
import java.net.URLDecoder
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.Path

/*
 * Wrapper around Java Path and File classes to work the sourcerer's files.
 */
object FileHelper {
    private val dirName = "data"
    private val jarPath = getJarPath()
    private val settingsPath = jarPath.resolve(dirName)

    fun getPath(name: String, vararg parts: String): Path {
        val path = settingsPath.resolve(Paths.get("", *parts))
        if (Files.notExists(path)) {
            Files.createDirectories(path)
        }
        return path.resolve(name)
    }

    fun getFile(name: String, vararg parts: String): File {
        return getPath(name, *parts).toFile()
    }

    fun notExists(name:String, vararg parts: String): Boolean {
        return Files.notExists(getPath(name, *parts))
    }

    fun getFileExtension(path: String): String {
        val fileName = Paths.get(path).fileName.toString()
        return fileName.substringAfterLast(delimiter = '.',
                                           missingDelimiterValue = "")
    }

    fun getJarPath(): Path {
        val fullPathString = URLDecoder.decode(FileHelper::class.java
            .protectionDomain.codeSource.location.toURI().path, "UTF-8")
        val fullPath = Paths.get(fullPathString)
        val root = fullPath.root
        // Removing jar filename.
        return root.resolve(fullPath.subpath(0, fullPath.nameCount - 1))
    }
}
