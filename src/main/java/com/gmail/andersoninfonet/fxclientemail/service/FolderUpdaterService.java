package com.gmail.andersoninfonet.fxclientemail.service;

import java.util.List;

import javax.mail.Folder;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class FolderUpdaterService extends Service {

    private List<Folder> foldersList;

    /**
     * @param foldersList
     */
    public FolderUpdaterService(List<Folder> foldersList) {
        this.foldersList = foldersList;
    }

    @Override
    protected Task createTask() {
        
        return new Task(){
            @Override
            protected Object call() throws Exception {
                while (true) {
                    try {
                        Thread.sleep(5000);
                        for(Folder folder : foldersList) {
                            if(folder.getType() != Folder.HOLDS_FOLDERS && folder.isOpen()) {
                                folder.getMessageCount();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }
    
}
