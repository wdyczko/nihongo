package org.drvad3r.nihongo.manager;

import org.drvad3r.nihongo.define.Path;
import org.drvad3r.nihongo.model.list.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Author: Wiktor
 * Creation: 2015-10-17
 */
public class StorageManager {
    public WordList loadWordDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(WordList.class);
            Unmarshaller um = context.createUnmarshaller();
            return (WordList) um.unmarshal(file);
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void saveWordDataToFile(File file, WordList list) {
        try {
            JAXBContext context = JAXBContext.newInstance(WordList.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(list, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public VerbList loadVerbDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(VerbList.class);
            Unmarshaller um = context.createUnmarshaller();
            return (VerbList) um.unmarshal(file);
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void saveVerbDataToFile(File file, VerbList list) {
        try {
            JAXBContext context = JAXBContext.newInstance(VerbList.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(list, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public AdjectiveList loadAdjectiveDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(AdjectiveList.class);
            Unmarshaller um = context.createUnmarshaller();
            return (AdjectiveList) um.unmarshal(file);
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ModuleList loadModulesDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(ModuleList.class);
            Unmarshaller um = context.createUnmarshaller();
            return (ModuleList) um.unmarshal(file);
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void saveModulesDataToFile(File file, ModuleList moduleList) {
        try {
            JAXBContext context = JAXBContext.newInstance(ModuleList.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(moduleList, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void saveQuestDataToFile(QuestList list) {
        try {
            File file = new File(System.getProperty("user.dir") + Path.QUEST_FILE);
            JAXBContext context = JAXBContext.newInstance(QuestList.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(list, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public QuestList loadQuestsDataFromFile() {
        try {
            File file = new File(System.getProperty("user.dir") + Path.QUEST_FILE);
            JAXBContext context = JAXBContext.newInstance(QuestList.class);
            Unmarshaller um = context.createUnmarshaller();
            return (QuestList) um.unmarshal(file);
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }
}
