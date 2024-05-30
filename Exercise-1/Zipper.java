package fi.utu.tech.ooj.exercise4.exercise1;

import fi.utu.tech.ooj.exercise4.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.zip.ZipInputStream;

// WORKAROUND: jos zip-tiedostoa ei löydy, kopioi resources-hakemistosta
// books.zip projektin juureen ja noudata alla olevia kahta ohjetta,
// jotka myös merkitty WORKAROUND-kommentilla

/**
 * Luokka, joka mallintaa unzippailuja (tiivistetyn zip-paketin purku).
 * <p>
 * Idea on, että luokan olion ollessa olemassa levyllä sijaitsee myös
 * oliota varten olion luoma tilapäishakemisto. Kun olio suljetaan,
 * myös hakemisto poistetaan.
 * <p>
 * Miten käytetään? Luo olio. Luonti olettaa, että zip-tiedoston on
 * oltava olemassa. Luokan metodi 'run' aktivoi unzippauksen. Lopuksi
 * sulje olio ('close').
 * <p>
 * Vinkki: sulkeminen on helppoa Javan try-with-resources -toiminnolla.
 */
abstract public class Zipper implements AutoCloseable {
    // zip-tiedosto purkamista varten
    private final String zipFile;

    // java-luokka, jonka paketista etsitään zip-tiedostoa
    private final Class<?> resolver = Main.class;

    // tilapäishakemiston polku
    protected final Path tempDirectory;

    /**
     * Merkitsee muistiin annetun zip-tiedoston ja
     * luo tilapäishakemiston 'tempDirectory'.
     *
     * @param zipFile Zip-tiedostopolku (alkuehto: oltava olemassa ja ei-null)
     * @throws IOException Zip-tiedostoa ei löydy tai tilapäishakemistoa ei voida luoda
     */
    public Zipper(String zipFile) throws IOException {
        // WORKAROUND: jos zip-tiedostoa ei löydy, kommentoi seuraavat 2 riviä
        if (resolver.getResource(zipFile) == null)
            throw new FileNotFoundException(zipFile);

        this.zipFile = zipFile;

        tempDirectory = Files.createTempDirectory("dtek0066");
        System.out.println("Luotu tilapäishakemisto " + tempDirectory);
    }

    /**
     * Poistaa tilapäishakemiston 'tempDirectory' olion sulkemisen yhteydessä.
     *
     * @throws IOException Kaikenlaisten I/O-virheiden sattuessa
     */
    @Override
    public void close() throws IOException {
        try (final var stream = Files.walk(tempDirectory)) {
            stream
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }

        System.out.println("Poistettu tilapäishakemisto " + tempDirectory);
    }

    /**
     * Purkaa 'zipFile'-tiedoston tilapäishakemistoon 'tempDirectory'.
     *
     * @throws IOException Kaikenlaisten I/O-virheiden sattuessa
     */
    private void unzip() throws IOException {
        final var destinationDir = tempDirectory.toFile();

        // WORKAROUND: jos zip-tiedostoa ei löydy, vaihda seuraavaan
        // try (final var inputStream = new FileInputStream(zipFile);
        try (final var inputStream = resolver.getResourceAsStream(zipFile);
             final var stream = new ZipInputStream(inputStream)) {
            var zipEntry = stream.getNextEntry();
            while (zipEntry != null) {
                final var newFile = new File(destinationDir, zipEntry.getName());
                final var destDirPath = destinationDir.getCanonicalPath();
                final var destFilePath = newFile.getCanonicalPath();
                if (!destFilePath.startsWith(destDirPath + File.separator)) {
                    throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
                }
                System.out.println("Puretaan " + newFile);
                if (zipEntry.isDirectory()) {
                    if (!newFile.isDirectory() && !newFile.mkdirs()) {
                        throw new IOException("Failed to create directory: " + newFile);
                    }
                } else {
                    // fix for Windows-created archives
                    final var parent = newFile.getParentFile();
                    if (!parent.isDirectory() && !parent.mkdirs()) {
                        throw new IOException("Failed to create directory: " + parent);
                    }

                    // write file content
                    try (final var fos = new FileOutputStream(newFile)) {
                        stream.transferTo(fos);
                    }
                }
                zipEntry = stream.getNextEntry();
            }
            stream.closeEntry();
        }
    }

    /**
     * Ajaa unzippauksen ja kullekin luodulle tiedostolle käsittelijän.
     *
     * @throws IOException Kaikenlaisten I/O-virheiden sattuessa
     */
    public void run() throws IOException {
        unzip();

        for (final var handler : createHandlers())
            handler.handle();
    }

    protected List<Handler> createHandlers() throws IOException {
        try (final var stream = Files.list(tempDirectory)) {
            return stream.map(this::createHandler).toList();
        }
    }

    /**
     * Käsittelijän luonti.
     *
     * @param file Käsiteltävä tiedosto (alkuehto: oltava olemassa ja ei-null)
     * @return Käsittelijä
     */
    protected abstract Handler createHandler(Path file);

    /**
     * Yksittäisen tiedoston käsittelijä, jonka vastuulla on käsitellä
     * yksittäinen tiedosto.
     */
    protected abstract static class Handler {
        public final Path file;

        /**
         * Käsittelijän alustus.
         *
         * @param file Käsiteltävä tiedosto (alkuehto: oltava olemassa ja ei-null)
         */
        public Handler(Path file) {
            this.file = file;
        }

        /**
         * Käsittelee tiedoston.
         *
         * @throws IOException Kaikenlaisten I/O-virheiden sattuessa
         */
        abstract public void handle() throws IOException;
    }
}

