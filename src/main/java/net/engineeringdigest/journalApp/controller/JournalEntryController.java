package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    private Map<Long, JournalEntry> journalEntries = new HashMap<>();

    @GetMapping("/allEntries")
    public List<JournalEntry> getAllEntries() {
        return new ArrayList<>(journalEntries.values());
    }

    @PostMapping("/createEntry")
    public boolean createEntry(@RequestBody JournalEntry myEntry) {
        journalEntries.put(myEntry.getId(), myEntry);
        return true;
    }

    @GetMapping("/singleEntry/{myId}")
    public JournalEntry getEntryById(@PathVariable Long myId) {
        return journalEntries.get(myId);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteEntryById(@PathVariable Long id) {
        journalEntries.remove(id);
        return true;
    }

    @PutMapping("/update/{id}")
    public JournalEntry updateEntryById(@PathVariable Long id, @RequestBody JournalEntry entry) {
        JournalEntry oldEntry = journalEntries.get(id);
        journalEntries.put(id, entry);
        return oldEntry;
    }

}
