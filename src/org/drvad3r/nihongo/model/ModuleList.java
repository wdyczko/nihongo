package org.drvad3r.nihongo.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

/**
 * Author: Wiktor
 * Creation: 2015-10-21
 */

@XmlRootElement(name = "modules")
public class ModuleList
{
    private ObservableList<Module> moduleList;

    public ModuleList()
    {
        this.moduleList = FXCollections.observableArrayList();
    }

    @XmlElement(name = "module")
    public ObservableList<Module> getModuleList()
    {
        return moduleList;
    }

    public void setModuleList(ObservableList<Module> moduleList)
    {
        this.moduleList = moduleList;
    }
}
