package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.RequestWrapper;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    private Map<Long, JournalEntry> journalEntries = new HashMap<>();

    @GetMapping("/getAll")
    public List<JournalEntry> getAllEntries() {
        return new ArrayList<>(journalEntries.values());
    }

    @GetMapping("/singleEntry/{id}")
    public JournalEntry getEntryById(@PathVariable Long id) {
        return journalEntries.get(id);
    }

    @PostMapping("/createEntry")
    public boolean createEntry(@RequestBody JournalEntry journalEntry) {
        long id = journalEntry.getId();
        journalEntries.put(id, journalEntry);
        return true;
    }

    @PutMapping("/update/{id}")
    public JournalEntry updateEntryById(@PathVariable Long id, @RequestBody JournalEntry journalEntry) {
        JournalEntry oldEntry = journalEntries.get(id);
        journalEntries.put(id, journalEntry);
        return oldEntry;
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteEntryById(@PathVariable Long id) {
        journalEntries.remove(id);
        return true;
    }
}
