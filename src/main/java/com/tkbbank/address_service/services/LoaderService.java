package com.tkbbank.address_service.services;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;
import com.tkbbank.address_service.entities.utils.GARDictionary;
import com.tkbbank.address_service.enums.EntitiesFileMatcher;
import com.tkbbank.address_service.repositories.GARObjectRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class LoaderService {

    @Value("${file_path.gar_archive}")
    private String garArchivePath;

    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private Integer batchSize;

    @PersistenceContext
    private EntityManager entityManager;

    private final GARObjectRepository garObjectRepository;

    private ZipFile zipFile;
    private Enumeration<ZipArchiveEntry> zipEntries;
    private XStream parserFromXMLtoObject;

    public void processZipFile() throws IOException, ClassNotFoundException {
        log.info("Start processing zip file");
        openZipFile(garArchivePath);

        Set<Class> allEntitiesClasses = findAllClasses("com.tkbbank.address_service.entities.utils");
        Class[] allEntitiesClassesArray = allEntitiesClasses.toArray(new Class[0]);
        parserFromXMLtoObject = createParserFromXMLtoObject(allEntitiesClassesArray);

        HashSet<Object> garObjects = new HashSet<>();
        ZipArchiveEntry entry;

        while (zipEntries.hasMoreElements()) {
            entry = zipEntries.nextElement();
            if (entry.getName().matches(EntitiesFileMatcher.ALL_OBJECTS.getFileMatcher()) || entry.getName().matches(EntitiesFileMatcher.ALL_DICTIONARIES.getFileMatcher())) {
                log.info("Processing: " + entry.getName());
                if (entry.getName().matches(EntitiesFileMatcher.ALL_DICTIONARIES.getFileMatcher())) {
                    setDictionaryAlias(entry.getName());
                }
                try (ObjectInputStream objectInputStream = parserFromXMLtoObject.createObjectInputStream(new BufferedInputStream(zipFile.getInputStream(entry)))) {
                    insertEntitiesInBatch(objectInputStream, garObjects, garObjectRepository);
                }
            }
        }

        closeZipFile();
        log.info("End processing zip file");
    }

    private void openZipFile(String filePath) throws IOException {
        zipFile = new ZipFile(filePath);
        zipEntries = zipFile.getEntries();
    }

    private void closeZipFile() throws IOException {
        zipFile.close();
    }

    public Set<Class> findAllClasses(String packageName) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(ClassLoader.getSystemClassLoader().getResourceAsStream(packageName.replaceAll("[.]", "/"))))) {
            return reader.lines()
                    .filter(line -> line.endsWith(".class"))
                    .map(line -> getClass(line, packageName))
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Class getClass(String className, String packageName) {
        try {
            return Class.forName(packageName + "." + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private XStream createParserFromXMLtoObject(Class[] annotations) {
        XStream xStream = new XStream(new StaxDriver());
        xStream.addPermission(AnyTypePermission.ANY);
        xStream.processAnnotations(annotations);
        xStream.ignoreUnknownElements();
        return xStream;
    }

    private void setDictionaryAlias(String fileName) {
        for (EntitiesFileMatcher matcher : EntitiesFileMatcher.values()) {
            if (fileName.matches(matcher.getFileMatcher()) && matcher.getAliasMatcher() != null) {
                parserFromXMLtoObject.alias(matcher.getAliasMatcher(), GARDictionary.class);
            }
        }
    }

    private <T> void insertEntitiesInBatch(ObjectInputStream objectInputStream, HashSet<T> entities, JpaRepository repository) throws IOException, ClassNotFoundException {
        try {
            for (; ; ) {
                entities.add((T) objectInputStream.readObject());
                if (entities.size() % batchSize == 0) {
                    saveAllAndFlushEntities(repository, entities);
                }
            }
        } catch (EOFException e) {
            if (entities.size() > 0) {
                saveAllAndFlushEntities(repository, entities);
            }
        }
    }

    private <T> void saveAllAndFlushEntities(JpaRepository repository, HashSet<T> entities) {
        repository.saveAllAndFlush(entities);
        entityManager.clear();
        entityManager.close();
        entities.clear();
    }
}