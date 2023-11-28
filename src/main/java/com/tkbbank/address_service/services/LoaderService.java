package com.tkbbank.address_service.services;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;
import jakarta.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.compress.archivers.zip.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import lombok.RequiredArgsConstructor;

import com.tkbbank.address_service.entities.utils.GARObject;
import com.tkbbank.address_service.repositories.GARObjectRepository;
import com.tkbbank.address_service.enums.EntitiesFileMatcher;

@Service
@RequiredArgsConstructor
@Log4j2
public class LoaderService {

    @Value("${file_path.gar_archive}")
    private String garArchivePath;

    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private Integer batchSize;

    @Autowired
    private EntityManager entityManager;

    private final GARObjectRepository GARObjectRepository;

    private ZipFile zipFile;
    private Enumeration<ZipArchiveEntry> zipEntries;
    private XStream parserFromXMLtoObject;

    public void processZipFile() throws IOException, ClassNotFoundException {
        log.info("Start process zip file");
        openZipFile(garArchivePath);

        Set<Class> allEntitiesClasses = findAllClasses("com.tkbbank.address_service.entities.utils");
        Class[] allEntitiesClassesArray = allEntitiesClasses.toArray(new Class[0]);
        parserFromXMLtoObject = createParserFromXMLtoObject(allEntitiesClassesArray);

        HashSet<GARObject> garObjects = new HashSet<>();
        ZipArchiveEntry entry;

        while (zipEntries.hasMoreElements()) {
            entry = zipEntries.nextElement();
            if (entry.getName().matches(EntitiesFileMatcher.ALL_OBJECTS.getFileMatcher())) {
                log.info("Process: " + entry.getName());
                ObjectInputStream objectInputStream = parserFromXMLtoObject.createObjectInputStream(new BufferedInputStream(zipFile.getInputStream(entry)));
                insertEntitiesInBatch(objectInputStream, garObjects, GARObjectRepository);
            }
        }

        closeZipFile();
        log.info("End process zip file");
    }

    private void openZipFile(String filePath) throws IOException {
        zipFile = new ZipFile(filePath);
        zipEntries = zipFile.getEntries();
    }

    private void closeZipFile() throws IOException {
        zipFile.close();
    }

    public Set<Class> findAllClasses(String packageName) {
        InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(packageName.replaceAll("[.]", "/"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        return reader.lines()
                .filter(line -> line.endsWith(".class"))
                .map(line -> getClass(line, packageName))
                .collect(Collectors.toSet());
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

    private void insertEntitiesInBatch(ObjectInputStream objectInputStream, HashSet<GARObject> entities, JpaRepository repository) throws IOException, ClassNotFoundException {
        try {
            for (; ; ) {
                entities.add((GARObject) objectInputStream.readObject());
                if (entities.size() % batchSize == 0) {
                    saveAllAndFlushEntities(repository, entities);
                }
            }
        } catch (EOFException e) {
            if (entities.size() > 0) {
                saveAllAndFlushEntities(repository, entities);
            }
            objectInputStream.close();
            entityManager.close();
            entityManager.getEntityManagerFactory().getCache().evictAll();
        }
    }

    private void saveAllAndFlushEntities(JpaRepository repository, HashSet<GARObject> entities) {
        repository.saveAllAndFlush(entities);
        entityManager.clear();
        entities.clear();
    }
}