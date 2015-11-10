package org.drvad3r.test.manager;

import org.drvad3r.nihongo.manager.StorageManager;
import org.drvad3r.nihongo.model.Word;
import org.drvad3r.nihongo.model.list.WordList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;

import static org.junit.Assert.*;

/**
 * Author: Wiktor
 * Creation: 2015-10-17
 */
@SuppressWarnings("FieldCanBeLocal")
public class StorageManagerTest
{
    private StorageManager storageManager;
    private String resourceDataPath;
    private String resourceFileName;
    private String fileWithPath;
    private String tempDataPath;
    private String tempFile;

    @Before
    public void setUp() throws Exception
    {
        storageManager = new StorageManager();
        resourceDataPath = System.getProperty("user.dir") + "\\resources\\data\\";
        resourceFileName = "template\\template.xml";
        fileWithPath = resourceDataPath + resourceFileName;
        tempDataPath = System.getProperty("user.dir") + "\\out\\test\\";
        File file = new File(tempDataPath);
        if(!file.exists())
        {
            Files.createDirectories(file.toPath());
        }
        tempFile = tempDataPath + "tmp.xml";
    }

    @After
    public void tearDown() throws Exception
    {
        File file = new File(tempFile);
        if(file.exists())
        {
            Files.delete(file.toPath());
        }
    }

    @Test
    public void testLoadWordDataFromFile() throws Exception
    {
        File file = new File(fileWithPath);
        assertTrue(file.exists());
        WordList wordList = storageManager.loadWordDataFromFile(file);
        assertTrue("Word list is empty!", wordList.getWords().size() > 0);
        Word.print(wordList.getWords().get(0));
    }

    @Test
    public void testSaveWordDataToFile() throws Exception
    {
        Word word = new Word();
        word.setEnglish("car"); word.setOriginal("車"); word.setPronounce("くるま"); word.setLocal("samochód");
        WordList wordList = new WordList();
        wordList.getWords().add(word);
        wordList.setDescription("This is simple description for test purposes");
        assertTrue("World list is empty!", wordList.getWords().size() > 0);
        File file = new File(tempFile);
        assertFalse("File exists but should not!", file.exists());
        storageManager.saveWordDataToFile(file, wordList);
        assertTrue("File does not exists but should!", file.exists());
    }
}