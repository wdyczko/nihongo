package org.drvad3r.nihongo.manager;

import javafx.collections.ObservableList;
import org.drvad3r.nihongo.define.Path;
import org.drvad3r.nihongo.define.SessionKeys;
import org.drvad3r.nihongo.model.Session;
import org.drvad3r.nihongo.model.SessionItem;
import org.drvad3r.nihongo.model.list.WordList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Author: Wiktor
 * Creation: 2015-10-26
 */
public class SessionManager {
    private static SessionManager instance;
    private static Session session;

    private SessionManager() {
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
            session = new Session();
            if (!loadSession()) {
                saveSession();
            }
        }
        return instance;
    }

    public static boolean sessionExists() {
        File file = new File(Path.SESSION_FILE);
        return file.exists();
    }

    public static boolean loadSession() {
        try {
            File file = new File(Path.SESSION_FILE);
            if (!file.exists()) return false;
            JAXBContext context = JAXBContext.newInstance(Session.class);
            Unmarshaller um = context.createUnmarshaller();
            session = (Session) um.unmarshal(file);
            return true;
        } catch (JAXBException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void saveSession() {
        try {
            File file = new File(Path.SESSION_FILE);
            JAXBContext context = JAXBContext.newInstance(Session.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(session, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public static void destroySession() {
        File file = new File(Path.SESSION_FILE);
        if (file.exists()) {
            try {
                Files.deleteIfExists(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setSessionItem(String key, String value) {
        ObservableList<SessionItem> items = session.getSessionItems();
        boolean found = false;
        for (SessionItem item : items) {
            if (item.getKey().equals(key)) {
                item.setValue(value);
                found = true;
                break;
            }
        }
        if (!found) {
            session.getSessionItems().add(new SessionItem(key, value));
        }
        saveSession();
    }

    public static String getSessionItem(String key) {
        ObservableList<SessionItem> items = session.getSessionItems();
        for (SessionItem item : items) {
            if (item.getKey().equals(key)) {
                return item.getValue();
            }
        }
        return null;
    }

    public static boolean isSessionKeyExists(String key) {
        return sessionExists() && getSessionItem(key) != null;
    }

    public static boolean removeSessionItem(String key) {
        ObservableList<SessionItem> items = session.getSessionItems();
        SessionItem toRemove = null;
        for (SessionItem item : items) {
            if (item.getKey().equals(key)) {
                toRemove = item;
                break;
            }
        }

        if (toRemove != null) {
            session.getSessionItems().remove(toRemove);
            saveSession();
            return true;
        } else {
            return false;
        }
    }

    public static WordList loadWordList() {
        StorageManager storageManager = new StorageManager();
        String indices = SessionManager.getInstance().getSessionItem(SessionKeys.CURRENT_MODULE_INDICES);
        String moduleName = SessionManager.getInstance().getSessionItem(SessionKeys.CURRENT_MODULE_PATH);
        WordList wordList;

        File file = new File(System.getProperty("user.dir") + Path.MODULE_RESOURCE_PATH + moduleName);
        if (indices != null) {
            WordList list = storageManager.loadWordDataFromFile(file);
            indices = indices.replaceAll("\\[|\\]", "");
            String[] splitted = indices.split(", ");
            if (splitted.length > 1) {
                WordList rangeList = new WordList();
                for (String s : splitted) {
                    rangeList.getWords().add(list.getWords().get(Integer.parseInt(s)));
                }
                wordList = rangeList;
            } else {
                wordList = list;
            }
        } else {
            wordList = storageManager.loadWordDataFromFile(file);
        }
        return wordList;
    }
}
