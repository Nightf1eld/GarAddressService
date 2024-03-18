package com.tkbbank.address_service.services;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;
import com.tkbbank.address_service.dto.utils.ManageCommand;
import com.tkbbank.address_service.entities.utils.GARDictionary;
import com.tkbbank.address_service.enums.EntitiesFileMatcher;
import com.tkbbank.address_service.enums.TableMatcher;
import com.tkbbank.address_service.repositories.GARIdxAddressRepository;
import com.tkbbank.address_service.repositories.GARObjectRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Log4j2
public class LoaderService {

    @Value("${file_path.gar_archive}")
    private String garArchivePath;

    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private Integer batchSize;

    @Value("${spring.datasource.username}")
    private String userName;

    @Value("${executors.thread_pool}")
    private Integer threadPool;

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @PersistenceContext
    private EntityManager entityManager;

    private final GARObjectRepository garObjectRepository;
    private final GARIdxAddressRepository garIdxAddressRepository;

    private ZipFile zipFile;
    private Enumeration<ZipArchiveEntry> zipEntries;
    private XStream parserFromXMLtoObject;

    public void processZipFile() throws IOException {
        openZipFile(garArchivePath);

        Set<Class> allEntitiesClasses = findAllClasses("com.tkbbank.address_service.entities.utils");
        Class[] allEntitiesClassesArray = allEntitiesClasses.toArray(new Class[0]);
        parserFromXMLtoObject = createParserFromXMLtoObject(allEntitiesClassesArray);

        ExecutorService executorService = Executors.newFixedThreadPool(threadPool);
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        while (zipEntries.hasMoreElements()) {
            ZipArchiveEntry entry = zipEntries.nextElement();

            Runnable runnableTask = () -> {
                try {
                    List<Object> garObjects = new ArrayList<>();
                    if (entry.getName().matches(EntitiesFileMatcher.ALL_OBJECTS.getFileMatcher()) || entry.getName().matches(EntitiesFileMatcher.ALL_DICTIONARIES.getFileMatcher())) {
                        log.info("Processing: " + entry.getName());
                        if (entry.getName().matches(EntitiesFileMatcher.ALL_DICTIONARIES.getFileMatcher())) {
                            setDictionaryAlias(entry.getName());
                        }
                        try (ObjectInputStream objectInputStream = parserFromXMLtoObject.createObjectInputStream(new BufferedInputStream(zipFile.getInputStream(entry)))) {
                            insertEntitiesInBatch(objectInputStream, garObjects, garObjectRepository);
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            };

            CompletableFuture<Void> future = CompletableFuture.runAsync(runnableTask, executorService);
            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        executorService.shutdown();

        closeZipFile();
    }

    private void openZipFile(String filePath) throws IOException {
        zipFile = new ZipFile(filePath);
        zipEntries = zipFile.getEntries();
    }

    private void closeZipFile() throws IOException {
        zipFile.close();
    }

    private Set<Class> findAllClasses(String packageName) {
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

    private <T> void insertEntitiesInBatch(ObjectInputStream objectInputStream, List<T> entities, JpaRepository repository) throws IOException, ClassNotFoundException {
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

    private <T> void saveAllAndFlushEntities(JpaRepository repository, List<T> entities) {
        entities.removeIf(Objects::isNull);
        repository.saveAllAndFlush(entities);
        entityManager.clear();
        entityManager.close();
        entities.clear();
    }

    private List<String> getAllTablesInSchema(String userName, EntityManager em) {
        return em.createNativeQuery("SELECT TABLE_NAME FROM ALL_TABLES WHERE OWNER = '" + userName + "'").getResultList();
    }

    private List<String> getAllColumnNamesInTable(String tableName, EntityManager em) {
        return em.createNativeQuery("SELECT COLUMN_NAME FROM ALL_TAB_COLUMNS WHERE TABLE_NAME = '" + tableName + "'").getResultList();
    }

    private List<String> getAllIndexesInTable(String tableName, EntityManager em) {
        return em.createNativeQuery("SELECT INDEX_NAME FROM ALL_INDEXES WHERE TABLE_NAME = '" + tableName + "'").getResultList();
    }

    private Map<String, List<String>> getSchemaTableColumns(EntityManager em) {
        return getAllTablesInSchema(userName, em).stream().collect(Collectors.toMap(table -> table, columns -> getAllColumnNamesInTable(columns, em)));
    }

    private Map<String, List<String>> getSchemaTableIndexes(EntityManager em) {
        return getAllTablesInSchema(userName, em).stream().collect(Collectors.toMap(table -> table, indexes -> getAllIndexesInTable(indexes, em)));
    }

    private void truncateTable(String tableName, EntityManager em) {
        try {
            em.getTransaction().begin();
            em.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }

    private void createIndex(String indexName, String tableName, String columnNames, EntityManager em) {
        try {
            em.getTransaction().begin();
            em.createNativeQuery("CREATE INDEX " + indexName + " ON " + tableName + "(" + columnNames + ")").executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }

    private void dropIndex(String indexName, EntityManager em) {
        try {
            em.getTransaction().begin();
            em.createNativeQuery("DROP INDEX " + indexName).executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }

    private void truncateAllTables(EntityManager em) {
        getSchemaTableColumns(em).entrySet().stream().forEach(table -> {
            log.info("Truncate table: " + table.getKey());
            truncateTable(table.getKey(), em);
        });
    }


    private void createAllIndexes(EntityManager em, String tableQueue) {
        Map<String, List<String>> indexTableColumns = Stream.of(TableMatcher.values()).filter(queue -> queue.getTableQueue().equals(tableQueue)).collect(Collectors.toMap(table -> table.name(), columns -> columns.getIndexMatcher()));

        indexTableColumns.entrySet().stream().filter(indexKey -> getSchemaTableColumns(em).containsKey(indexKey.getKey().toUpperCase())).forEach(index -> {
            AtomicInteger indexNumber = new AtomicInteger(1);
            index.getValue().stream().forEach(indexColumn -> {
                String indexName = "IDX_" + index.getKey() + "_" + indexNumber;
                log.info("Create index: " + indexName);
                try {
                    createIndex(indexName, index.getKey(), indexColumn, em);
                    indexNumber.getAndIncrement();
                } catch (SQLGrammarException e) {
                    e.printStackTrace();
                }
            });
        });
    }

    private void dropAllIndexes(EntityManager em) {
        getSchemaTableIndexes(em).entrySet().stream().forEach(table -> {
            table.getValue().stream().filter(indexName -> indexName.contains("IDX_")).forEach(indexName -> {
                log.info("Drop index: " + indexName);
                dropIndex(indexName, em);
            });
        });
    }

    public void processDataTables(String command) {
        EntityManager em = entityManagerFactory.createEntityManager();

        switch (ManageCommand.fromText(command)) {
            case TRUNCATE -> {
                truncateAllTables(em);
            }
            case CREATE_INDEX -> {
                createAllIndexes(em, "1");
            }
            case DROP_INDEX -> {
                dropAllIndexes(em);
            }
        }

        em.close();
    }

    public void processIdxTables(String command) {
        switch (ManageCommand.fromText(command)) {
            case CREATE_INDEX -> {
                EntityManager em = entityManagerFactory.createEntityManager();
                createAllIndexes(em, "2");
                em.close();
            }
            case INSERT, UPDATE -> {
                ExecutorService executorService = Executors.newFixedThreadPool(threadPool);
                List<CompletableFuture<Void>> futures = new ArrayList<>();

                Stream.of(TableMatcher.values()).filter(table -> table.name().contains("IDX")).forEach(table -> {
                    Runnable runnableTask = () -> {
                        switch (ManageCommand.fromText(command)) {
                            case INSERT -> {
                                for (String insertParam : table.getInsertParams()) {
                                    List<String> params = Arrays.asList(insertParam.split(", "));
                                    log.info("Insert into: " + table.name() + "(" + params.get(0) + ", " + params.get(1) + ", " + params.get(2) + ")");
                                    garIdxAddressRepository.idxAddressInsert(params.get(0), params.get(1), params.get(2), table.name(), batchSize);
                                }
                            }
                            case UPDATE -> {
                                log.info("Update: " + table.name());
                                garIdxAddressRepository.idxAddressUpdate(table.name(), batchSize);
                            }
                        }
                    };

                    CompletableFuture<Void> future = CompletableFuture.runAsync(runnableTask, executorService);
                    futures.add(future);
                });

                CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
                executorService.shutdown();
            }
        }
    }
}