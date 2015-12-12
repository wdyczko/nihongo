package org.drvad3r.nihongo.model.list;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.drvad3r.nihongo.model.Quest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Author: Wiktor
 * Creation date: 2015-12-05.
 */
@XmlRootElement(name = "quests")
public class QuestList {
    private ObservableList<Quest> questList;

    public QuestList()
    {
        this.questList = FXCollections.observableArrayList();
    }

    @XmlElement(name = "quest")
    public ObservableList<Quest> getQuestList()
    {
        return questList;
    }

    public void setQuestList(ObservableList<Quest> questList)
    {
        this.questList = questList;
    }

    public Quest getQuest(String title)
    {
        for (Quest quest :
                questList)
        {
            if(quest.getTitle().equals(title))
                return quest;
        }
        return null;
    }
}
