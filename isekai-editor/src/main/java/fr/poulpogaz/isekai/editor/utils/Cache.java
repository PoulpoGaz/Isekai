package fr.poulpogaz.isekai.editor.utils;

import java.io.*;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.stream.Stream;

/**
 * {@link Files#createTempFile(String, String, FileAttribute[])}
 * {@link Files#createTempDirectory(String, FileAttribute[])}
 * {@link Files#createSymbolicLink(Path, Path, FileAttribute[])}
 * {@link Files#createLink(Path, Path)}
 * {@link Files#readSymbolicLink(Path)}
 * {@link Files#isSymbolicLink(Path)}
 * methods aren't overridden
 */
public class Cache {

    public static Path ROOT = Path.of(System.getProperty("java.io.tmpdir") + "/Java");

    public static void setRoot() {
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            Cache.setRoot(System.getenv("APPDATA"), "/Isekai-Editor");
        } else {
            Cache.setRoot(System.getProperty("user.home"), "/Isekai-Editor");
        }
    }

    public static void setRoot(Path root) {
        ROOT = root;
    }

    public static void setRoot(File root) {
        ROOT = root.toPath();
    }

    public static void setRoot(String root) {
        ROOT = Path.of(root);
    }

    public static void setRoot(String root, String... more) {
        ROOT = Path.of(root, more);
    }

    public static Path of(String path) {
        return resolve(Path.of(path));
    }

    public static Path of(String path, String more) {
        return resolve(Path.of(path, more));
    }

    public static Path resolve(Path path) {
        return ROOT.resolve(path);
    }

    /**
     * @see Files#newInputStream(Path, OpenOption...)
     */
    public static InputStream newInputStream(Path path, OpenOption... options)
            throws IOException {
        return Files.newInputStream(resolve(path), options);
    }

    /**
     * @see Files#newOutputStream(Path, OpenOption...)
     */
    public static OutputStream newOutputStream(Path path, OpenOption... options)
            throws IOException {
        return Files.newOutputStream(resolve(path), options);
    }

    /**
     * @see Files#newByteChannel(Path, Set, FileAttribute[])
     */
    public static SeekableByteChannel newByteChannel(Path path,
                                                     Set<? extends OpenOption> options,
                                                     FileAttribute<?>... attrs)
            throws IOException {
        return Files.newByteChannel(resolve(path), options, attrs);
    }

    /**
     * @see Files#newByteChannel(Path, OpenOption...)
     */
    public static SeekableByteChannel newByteChannel(Path path, OpenOption... options)
            throws IOException {
        return Files.newByteChannel(resolve(path), options);
    }

    /**
     * @see Files#newDirectoryStream(Path)
     */
    public static DirectoryStream<Path> newDirectoryStream(Path dir)
            throws IOException {
        return Files.newDirectoryStream(resolve(dir));
    }

    /**
     * @see Files#newDirectoryStream(Path, String)
     */
    public static DirectoryStream<Path> newDirectoryStream(Path dir, String glob)
            throws IOException {
        return Files.newDirectoryStream(resolve(dir), glob);
    }

    /**
     * @see Files#newDirectoryStream(Path, DirectoryStream.Filter)
     */
    public static DirectoryStream<Path> newDirectoryStream(Path dir,
                                                           DirectoryStream.Filter<? super Path> filter)
            throws IOException {
        return Files.newDirectoryStream(resolve(dir), filter);
    }

    /**
     * @see Files#createFile(Path, FileAttribute[])
     */
    public static Path createFile(Path path, FileAttribute<?>... attrs)
            throws IOException {
        return Files.createFile(resolve(path), attrs);
    }

    /**
     * @see Files#createDirectory(Path, FileAttribute[])
     */
    public static Path createDirectory(Path dir, FileAttribute<?>... attrs)
            throws IOException {
        return Files.createDirectories(resolve(dir), attrs);
    }

    /**
     * @see Files#createDirectories(Path, FileAttribute[])
     */
    public static Path createDirectories(Path dir, FileAttribute<?>... attrs)
            throws IOException {
        return Files.createDirectories(resolve(dir), attrs);
    }

    /**
     * @see Files#createTempFile(Path, String, String, FileAttribute[])
     */
    public static Path createTempFile(Path dir,
                                      String prefix,
                                      String suffix,
                                      FileAttribute<?>... attrs)
            throws IOException {
        return Files.createTempFile(resolve(dir), prefix, suffix, attrs);
    }

    /**
     * @see Files#createTempDirectory(Path, String, FileAttribute[])
     */
    public static Path createTempDirectory(Path dir,
                                           String prefix,
                                           FileAttribute<?>... attrs)
            throws IOException {
        return Files.createTempDirectory(resolve(dir), prefix, attrs);
    }

    /**
     * @see Files#delete(Path)
     */
    public static void delete(Path path) throws IOException {
        Files.delete(resolve(path));
    }

    /**
     * @see Files#deleteIfExists(Path)
     */
    public static boolean deleteIfExists(Path path) throws IOException {
        return Files.deleteIfExists(resolve(path));
    }

    /**
     * @see Files#copy(Path, Path, CopyOption...)
     */
    public static Path copy(Path source, Path target, CopyOption... options)
            throws IOException {
        return Files.copy(resolve(source), resolve(target), options);
    }

    public static Path copyFromCache(Path source, Path target, CopyOption... options)
            throws IOException {
        return Files.copy(resolve(source), target, options);
    }

    public static Path copyToCache(Path source, Path target, CopyOption... options)
            throws IOException {
        return Files.copy(source, resolve(target), options);
    }

    /**
     * @see Files#move(Path, Path, CopyOption...)
     */
    public static Path move(Path source, Path target, CopyOption... options)
            throws IOException {
        return Files.move(resolve(source), resolve(target), options);
    }

    public static Path moveFromCache(Path source, Path target, CopyOption... options)
            throws IOException {
        return Files.move(resolve(source), target, options);
    }

    public static Path moveToCache(Path source, Path target, CopyOption... options)
            throws IOException {
        return Files.move(source, resolve(target), options);
    }

    /**
     * @see Files#getFileStore(Path)
     */
    public static FileStore getFileStore(Path path) throws IOException {
        return Files.getFileStore(resolve(path));
    }

    /**
     * @see Files#isSameFile(Path, Path)
     */
    public static boolean isSameFile(Path path, Path path2) throws IOException {
        return Files.isSameFile(resolve(path), resolve(path2));
    }

    // TODO: rename
    public static boolean isSameFileCachePath(Path cachePAth, Path path) throws IOException {
        return Files.isSameFile(resolve(cachePAth), path);
    }

    // TODO: rename
    public static boolean isSameFilePathCache(Path path, Path cachePAth) throws IOException {
        return Files.isSameFile(path, resolve(cachePAth));
    }

    /**
     * @see Files#mismatch(Path, Path)
     */
    public static long mismatch(Path path, Path path2) throws IOException {
        return Files.mismatch(resolve(path), resolve(path2));
    }

    public static long mismatchFromCache(Path path, Path path2) throws IOException {
        return Files.mismatch(resolve(path), path2);
    }

    public static long mismatchToCache(Path path, Path path2) throws IOException {
        return Files.mismatch(path, resolve(path2));
    }

    /**
     * @see Files#isHidden(Path)
     */

    public static boolean isHidden(Path path) throws IOException {
        return Files.isHidden(resolve(path));
    }

    /**
     * @see Files#probeContentType(Path)
     */
    public static String probeContentType(Path path)
            throws IOException {
        return Files.probeContentType(resolve(path));
    }

    /**
     * @see Files#getFileAttributeView(Path, Class, LinkOption...)
     */
    public static <V extends FileAttributeView> V getFileAttributeView(Path path,
                                                                       Class<V> type,
                                                                       LinkOption... options) {
        return Files.getFileAttributeView(resolve(path), type, options);
    }

    /**
     * @see Files#readAttributes(Path, Class, LinkOption...)
     */
    public static <A extends BasicFileAttributes> A readAttributes(Path path,
                                                                   Class<A> type,
                                                                   LinkOption... options)
            throws IOException {
        return Files.readAttributes(resolve(path), type, options);
    }

    /**
     * @see Files#setAttribute(Path, String, Object, LinkOption...)
     */
    public static Path setAttribute(Path path, String attribute, Object value,
                                    LinkOption... options)
            throws IOException {
        return Files.setAttribute(resolve(path), attribute, value, options);
    }

    /**
     * @see Files#getAttribute(Path, String, LinkOption...)
     */
    public static Object getAttribute(Path path, String attribute,
                                      LinkOption... options)
            throws IOException {
        return Files.getAttribute(resolve(path), attribute, options);
    }

    /**
     * @see Files#readAttributes(Path, Class, LinkOption...)
     */
    public static Map<String,Object> readAttributes(Path path, String attributes,
                                                    LinkOption... options)
            throws IOException {
        return Files.readAttributes(resolve(path), attributes, options);
    }

    /**
     * @see Files#getPosixFilePermissions(Path, LinkOption...)
     */
    public static Set<PosixFilePermission> getPosixFilePermissions(Path path,
                                                                   LinkOption... options)
            throws IOException {
        return Files.getPosixFilePermissions(resolve(path), options);
    }

    /**
     * @see Files#setPosixFilePermissions(Path, Set)
     */
    public static Path setPosixFilePermissions(Path path,
                                               Set<PosixFilePermission> perms)
            throws IOException {
        return Files.setPosixFilePermissions(resolve(path), perms);
    }

    /**
     * @see Files#getOwner(Path, LinkOption...)
     */
    public static UserPrincipal getOwner(Path path, LinkOption... options)
            throws IOException {
        return Files.getOwner(resolve(path), options);
    }

    /**
     * @see Files#setOwner(Path, UserPrincipal) 
     */
    public static Path setOwner(Path path, UserPrincipal owner)
            throws IOException {
        return Files.setOwner(resolve(path), owner);
    }

    /**
     * @see Files#isDirectory(Path, LinkOption...)
     */
    public static boolean isDirectory(Path path, LinkOption... options) {
        return Files.isDirectory(resolve(path), options);
    }

    /**
     * @see Files#isRegularFile(Path, LinkOption...)
     */
    public static boolean isRegularFile(Path path, LinkOption... options) {
        return Files.isRegularFile(resolve(path), options);
    }

    /**
     * @see Files#getLastModifiedTime(Path, LinkOption...)
     */
    public static FileTime getLastModifiedTime(Path path, LinkOption... options)
            throws IOException {
        return Files.getLastModifiedTime(resolve(path), options);
    }

    /**
     * @see Files#setLastModifiedTime(Path, FileTime)
     */
    public static Path setLastModifiedTime(Path path, FileTime time)
            throws IOException {
        return Files.setLastModifiedTime(resolve(path), time);
    }

    /**
     * @see Files#size(Path)
     */
    public static long size(Path path) throws IOException {
        return Files.size(resolve(path));
    }

    /**
     * @see Files#exists(Path, LinkOption...)
     */
    public static boolean exists(Path path, LinkOption... options) {
        return Files.exists(resolve(path), options);
    }

    /**
     * @see Files#notExists(Path, LinkOption...)
     */
    public static boolean notExists(Path path, LinkOption... options) {
        return Files.notExists(resolve(path), options);
    }

    /**
     * @see Files#isReadable(Path)
     */
    public static boolean isReadable(Path path) {
        return Files.isRegularFile(resolve(path));
    }

    /**
     * @see Files#isWritable(Path)
     */
    public static boolean isWritable(Path path) {
        return Files.isWritable(resolve(path));
    }

    /**
     * @see Files#isExecutable(Path)
     */
    public static boolean isExecutable(Path path) {
        return Files.isExecutable(resolve(path));
    }

    /**
     * @see Files#walkFileTree(Path, Set, int, FileVisitor)
     */
    public static Path walkFileTree(Path start,
                                    Set<FileVisitOption> options,
                                    int maxDepth,
                                    FileVisitor<? super Path> visitor)
            throws IOException {
        return Files.walkFileTree(resolve(start), options, maxDepth, visitor);
    }

    /**
     * @see Files#walkFileTree(Path, FileVisitor)
     */
    public static Path walkFileTree(Path start, FileVisitor<? super Path> visitor)
            throws IOException {
        return Files.walkFileTree(resolve(start), visitor);
    }

    /**
     * @see Files#newBufferedReader(Path, Charset)
     */
    public static BufferedReader newBufferedReader(Path path, Charset cs)
            throws IOException {
        return Files.newBufferedReader(resolve(path), cs);
    }

    /**
     * @see Files#newBufferedReader(Path)
     */
    public static BufferedReader newBufferedReader(Path path) throws IOException {
        return Files.newBufferedReader(resolve(path));
    }

    /**
     * @see Files#newBufferedWriter(Path, Charset, OpenOption...)
     */
    public static BufferedWriter newBufferedWriter(Path path, Charset cs,
                                                   OpenOption... options)
            throws IOException {
        return Files.newBufferedWriter(resolve(path), cs, options);
    }

    /**
     * @see Files#newBufferedWriter(Path, OpenOption...)
     */
    public static BufferedWriter newBufferedWriter(Path path, OpenOption... options)
            throws IOException {
        return Files.newBufferedWriter(resolve(path), options);
    }

    /**
     * @see Files#copy(InputStream, Path, CopyOption...)
     */
    public static long copy(InputStream in, Path target, CopyOption... options)
            throws IOException {
        return Files.copy(in, resolve(target), options);
    }

    /**
     * @see Files#copy(Path, OutputStream)
     */
    public static long copy(Path source, OutputStream out)
            throws IOException {
        return Files.copy(resolve(source), out);
    }

    /**
     * @see Files#readAllBytes(Path)
     */
    public static byte[] readAllBytes(Path path) throws IOException {
        return Files.readAllBytes(resolve(path));
    }

    /**
     * @see Files#readString(Path)
     */
    public static String readString(Path path) throws IOException {
        return Files.readString(resolve(path));
    }

    /**
     * @see Files#readString(Path, Charset)
     */
    public static String readString(Path path, Charset cs) throws IOException {
        return Files.readString(resolve(path), cs);
    }

    /**
     * @see Files#readAllLines(Path, Charset)
     */
    public static List<String> readAllLines(Path path, Charset cs) throws IOException {
        return Files.readAllLines(resolve(path), cs);
    }

    /**
     * @see Files#readAllLines(Path)
     */
    public static List<String> readAllLines(Path path) throws IOException {
        return Files.readAllLines(resolve(path));
    }

    /**
     * @see Files#write(Path, byte[], OpenOption...) 
     */
    public static Path write(Path path, byte[] bytes, OpenOption... options)
            throws IOException {
        return Files.write(resolve(path), bytes, options);
    }

    /**
     * @see Files#write(Path, Iterable, Charset, OpenOption...)
     */
    public static Path write(Path path, Iterable<? extends CharSequence> lines,
                             Charset cs, OpenOption... options)
            throws IOException {
        return Files.write(resolve(path), lines, cs, options);
    }

    /**
     * @see Files#write(Path, Iterable, OpenOption...)
     */
    public static Path write(Path path,
                             Iterable<? extends CharSequence> lines,
                             OpenOption... options)
            throws IOException {
        return Files.write(resolve(path), lines, options);
    }

    /**
     * @see Files#writeString(Path, CharSequence, OpenOption...)
     */
    public static Path writeString(Path path, CharSequence csq, OpenOption... options)
            throws IOException {
        return Files.writeString(resolve(path), csq, options);
    }

    /**
     * @see Files#writeString(Path, CharSequence, Charset, OpenOption...)
     */
    public static Path writeString(Path path, CharSequence csq, Charset cs, OpenOption... options)
            throws IOException {
        return Files.writeString(resolve(path), csq, cs, options);
    }

    /**
     * @see Files#list(Path)
     */
    public static Stream<Path> list(Path dir) throws IOException {
        return Files.list(resolve(dir));
    }

    /**
     * @see Files#walk(Path, int, FileVisitOption...)
     */
    public static Stream<Path> walk(Path start,
                                    int maxDepth,
                                    FileVisitOption... options)
            throws IOException {
        return Files.walk(resolve(start), maxDepth, options);
    }

    /**
     * @see Files#walk(Path, FileVisitOption...)
     */
    public static Stream<Path> walk(Path start, FileVisitOption... options) throws IOException {
        return Files.walk(resolve(start), options);
    }

    /**
     * @see Files#find(Path, int, BiPredicate, FileVisitOption...)
     */
    public static Stream<Path> find(Path start,
                                    int maxDepth,
                                    BiPredicate<Path, BasicFileAttributes> matcher,
                                    FileVisitOption... options)
            throws IOException {
        return Files.find(resolve(start), maxDepth, matcher, options);
    }

    /**
     * @see Files#lines(Path, Charset) s
     */
    public static Stream<String> lines(Path path, Charset cs) throws IOException {
        return Files.lines(resolve(path), cs);
    }

    /**
     * @see Files#lines(Path)
     */
    public static Stream<String> lines(Path path) throws IOException {
        return Files.lines(resolve(path));
    }
}