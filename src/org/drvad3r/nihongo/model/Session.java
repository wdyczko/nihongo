package org.drvad3r.nihongo.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sun.security.timestamp.TimestampToken;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.security.Timestamp;
import java.time.LocalTime;

/**
 * Author: Wiktor
 * Creation: 2015-10-26
 */
@XmlRootElement(name = "session")
public class Session {
    private ObservableList<SessionItem> sessionItems;
    private String timestamp;

    public Session()
    {
        sessionItems = FXCollections.observableArrayList();
    }

    @XmlElement(name = "timestamp")
    public String getTimestamp()
    {
        return Long.toString(LocalTime.now().toNanoOfDay());
    }

    @XmlElement(name = "items")
    public ObservableList<SessionItem> getSessionItems()
    {
        return sessionItems;
    }

    public void setSessionItems(ObservableList<SessionItem> sessionItems)
    {
        this.sessionItems = sessionItems;
    }
}
