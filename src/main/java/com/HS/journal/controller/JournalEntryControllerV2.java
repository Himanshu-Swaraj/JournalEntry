package com.HS.journal.controller;

import com.HS.journal.entity.JournalEntry;
import com.HS.journal.service.JournalEntryService;
import org.springframework.web.bind.annotation.*;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {
    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping("/getAll")
    public List<JournalEntry> getAll() {
        return  journalEntryService.getAll();
    }

    @PostMapping("/create")
    public boolean createEntry(@RequestBody JournalEntry myEntry){
        myEntry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(myEntry);
        return  true;
    }
    @GetMapping("/get_by_id/{myId}")
    public JournalEntry getById(@PathVariable ObjectId myId){
        return journalEntryService.findById(myId).orElse(null);
    }
    @PutMapping("/update/{myId}")
    public JournalEntry updateEntry(@PathVariable ObjectId myId ,@RequestBody JournalEntry newEntry){
        JournalEntry old = journalEntryService.findById(myId).orElse(null);
        if(old!=null){
            old.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().equals("") ? newEntry.getTitle():old.getTitle());
            old.setContent(newEntry.getContent()!=null && !newEntry.equals("")? newEntry.getContent():old.getContent());;
        }
        journalEntryService.saveEntry(old);
        return  old;
    }
    @DeleteMapping("/delete/{myId}")
    public boolean deleteEntry(@PathVariable ObjectId myId){
        journalEntryService.delete(myId);
        return true;
    }
}