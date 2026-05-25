package com.Ilearn.journalApp.Controller;

import com.Ilearn.journalApp.Entity.JournalEntry;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/-journal")
@CrossOrigin(origins ="http://localhost:4200")
public class JournalEntryController {

    private Map<Long,JournalEntry> journalEntries = new HashMap();

   @GetMapping()
    public List<JournalEntry> getAll() {
        return new ArrayList<>(journalEntries.values());
    }

    @PostMapping
    public boolean createEntry(@RequestBody JournalEntry myEntry) {
      // journalEntries.put(myEntry.getId(), myEntry);
       return true;

    }

    @GetMapping("/{myid}")
    public JournalEntry getJournalEntrybyid(@PathVariable long myid) {
       return journalEntries.get(myid);
    }

    @DeleteMapping("/{myid}")
    public JournalEntry deleteJournalEntrybyid(@PathVariable long myid) {
        return journalEntries.remove(myid);
    }

    @PutMapping("/{id}")
    public JournalEntry updateJournalEntry( @PathVariable long id,@RequestBody JournalEntry myEntry) {
      return journalEntries.put(id, myEntry);

    }
}
