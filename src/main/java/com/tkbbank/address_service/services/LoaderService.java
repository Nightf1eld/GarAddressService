package com.tkbbank.address_service.services;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;
import org.apache.commons.compress.archivers.zip.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import lombok.RequiredArgsConstructor;

import com.tkbbank.address_service.repositories.AddressRepository;
import com.tkbbank.address_service.repositories.AddressRelationRepository;
import com.tkbbank.address_service.repositories.HouseRepository;
import com.tkbbank.address_service.enums.EntitiesFileMatcher;
import com.tkbbank.address_service.entities.Address;
import com.tkbbank.address_service.entities.AddressRelation;
import com.tkbbank.address_service.entities.House;

@Service
@RequiredArgsConstructor
public class LoaderService {

    @Value("${file_path.gar_archive}")
    private String garArchivePath;

    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private Integer batchSize;

    private final AddressRepository addressRepository;
    private final AddressRelationRepository addressRelationRepository;
    private final HouseRepository houseRepository;

    private ZipFile zipFile;
    private Enumeration<ZipArchiveEntry> zipEntries;
    private XStream parserFromXMLtoObject;

    public void processZipFile() throws IOException, ClassNotFoundException {
        openZipFile(garArchivePath);

        Set<Class> allEntitiesClasses = findAllClasses("com.tkbbank.address_service.entities");
        Class[] allEntitiesClassesArray = allEntitiesClasses.toArray(new Class[0]);
        createParserFromXMLtoObject(allEntitiesClassesArray);

        while (zipEntries.hasMoreElements()) {
            ZipArchiveEntry entry = zipEntries.nextElement();
            if (entry.getName().matches(EntitiesFileMatcher.ALL_OBJECTS.getFileMatcher())) {
                InputStream inputStream = new BufferedInputStream(zipFile.getInputStream(entry));
                ObjectInputStream objectInputStream = parserFromXMLtoObject.createObjectInputStream(inputStream);

                if (entry.getName().matches(EntitiesFileMatcher.AS_ADDR_OBJ.getFileMatcher())) {
                    HashSet<Address> addressObjects = new HashSet<>();
                    insertEntitiesInBatch(objectInputStream, addressRepository, addressObjects);
                    closeInputStream(inputStream);
                }

                if (entry.getName().matches(EntitiesFileMatcher.AS_ADM_HIERARCHY.getFileMatcher()) || entry.getName().matches(EntitiesFileMatcher.AS_MUN_HIERARCHY.getFileMatcher())) {
                    HashSet<AddressRelation> addressRelations = new HashSet<>();
                    insertEntitiesInBatch(objectInputStream, addressRelationRepository, addressRelations);
                    closeInputStream(inputStream);
                }

                if (entry.getName().matches(EntitiesFileMatcher.AS_HOUSES.getFileMatcher())) {
                    HashSet<House> houses = new HashSet<>();
                    insertEntitiesInBatch(objectInputStream, houseRepository, houses);
                    closeInputStream(inputStream);
                }

            }
        }

        closeZipFile();
    }

    private void openZipFile(String filePath) throws IOException {
        zipFile = new ZipFile(filePath);
        zipEntries = zipFile.getEntries();
    }

    private void closeZipFile() throws IOException {
        zipFile.close();
    }

    private void closeInputStream(InputStream inputStream) throws IOException {
        inputStream.close();
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

    private void createParserFromXMLtoObject(Class[] annotations) {
        parserFromXMLtoObject = new XStream();
        parserFromXMLtoObject.addPermission(AnyTypePermission.ANY);
        parserFromXMLtoObject.processAnnotations(annotations);
        parserFromXMLtoObject.ignoreUnknownElements();
    }

    private <T> void insertEntitiesInBatch(ObjectInputStream objectInputStream, JpaRepository repository, HashSet<T> entities) throws IOException, ClassNotFoundException {
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
            objectInputStream.close();
        }
    }

    private <T> void saveAllAndFlushEntities(JpaRepository repository, HashSet<T> entities) {
        repository.saveAllAndFlush(entities);
        entities.clear();
    }
}