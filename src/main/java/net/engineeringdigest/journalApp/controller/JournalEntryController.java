package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

//    @GetMapping("/getAll")
//    public ResponseEntity<?> getAllEntries() {
//        Map<String, Object> response = new HashMap<>();
//        List<JournalEntry> entries = journalEntryService.getAllEntry();
//        if (entries != null && !entries.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.FOUND).body(entries);
//        }
//        response.put("recordFound", false);
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
//    }
//
//    @GetMapping("/singleEntry/{id}")
//    public ResponseEntity<?> findById(@PathVariable ObjectId id) {
//        Map<String, Object> response = new HashMap<>();
//        Optional<JournalEntry> entry = journalEntryService.findById(id);
//        if (entry.isPresent()) {
//            return ResponseEntity.status(HttpStatus.FOUND).body(entry.get());
//        }
//        response.put("recordFound", false);
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
//    }
//
//    @PostMapping("/createEntry")
//    public ResponseEntity<?> createEntry(@RequestBody JournalEntry journalEntry) {
//        Map<String, Object> response = new HashMap<>();
//        try {
//            journalEntryService.saveEntry(journalEntry);
//            response.put("recordCreated", true);
//            response.put("record", journalEntry);
//            return ResponseEntity.status(HttpStatus.CREATED).body(response);
//        } catch (Exception e) {
//            response.put("recordCreated", false);
//            response.put("errorMessage", e.getMessage());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//        }
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<?> deleteById(@PathVariable ObjectId id) {
//        Map<String, Object> response = new HashMap<>();
//        try {
//            JournalEntry entry = journalEntryService.findById(id).orElse(null);
//            if (entry == null) {
//                response.put("recordFound", false);
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
//            }
//            journalEntryService.deleteById(id);
//            response.put("entryDeleted", true);
//            return ResponseEntity.status(HttpStatus.OK).body(response);
//        } catch (Exception e) {
//            response.put("entryDeleted", false);
//            response.put("errorMessage", e.getMessage());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//        }
//    }
//
//    @PutMapping("/update/{id}")
//    public ResponseEntity<?> updateById(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry) {
//        Map<String, Object> response = new HashMap<>();
//        JournalEntry oldEntry = journalEntryService.findById(id).orElse(null);
//        if (oldEntry != null) {
//            oldEntry.setTitle(newEntry.getTitle() != null ? newEntry.getTitle() : oldEntry.getTitle());
//            oldEntry.setContent(newEntry.getContent() != null ? newEntry.getContent() : oldEntry.getContent());
//            journalEntryService.saveEntry(oldEntry);
//            return ResponseEntity.status(HttpStatus.OK).body(oldEntry);
//        }
//        response.put("recordFound", false);
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
//    }

    @GetMapping("/getAll/{userName}")
    public ResponseEntity<?> getAllEntriesOfUser(@PathVariable String userName) {
        Map<String, Object> response = new HashMap<>();
        User user = userService.findByUserName(userName);
        List<JournalEntry> entries = user.getJournalEntries();
        if (entries != null && !entries.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FOUND).body(entries);
        }
        response.put("entriesFound", false);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @GetMapping("/singleEntry/{id}")
    public ResponseEntity<?> getEntryById(@PathVariable ObjectId id) {
        Map<String, Object> response = new HashMap<>();
        JournalEntry entry = journalEntryService.findById(id).orElse(null);
        if (entry != null) {
            return ResponseEntity.status(HttpStatus.FOUND).body(entry);
        }
        response.put("entryFound", false);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @PostMapping("/createEntry/{userName}")
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry newEntry, @PathVariable String userName) {
        Map<String, Object> response = new HashMap<>();
        try {
            journalEntryService.saveEntry(newEntry, userName);
            response.put("entryCreated", true);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.put("entryCreated", false);
            response.put("errorMessage", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/delete/{id}/{userName}")
    public ResponseEntity<?> deleteById(@PathVariable ObjectId id, @PathVariable String userName) {
        Map<String, Object> response = new HashMap<>();
        try {
            journalEntryService.deleteById(id, userName);
            response.put("entryDeleted", true);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.put("entryDeleted", false);
            response.put("errorMessage", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/update/{id}/{userName}")
    public ResponseEntity<?> updateEntryById(@PathVariable ObjectId id, @PathVariable String userName, @RequestBody JournalEntry newEntry) {
        Map<String, Object> response = new HashMap<>();
        try {
            JournalEntry entryInDB = journalEntryService.findById(id).orElse(null);
            if (entryInDB != null) {
                entryInDB.setTitle(newEntry.getTitle());
                entryInDB.setContent(newEntry.getContent() != null ? newEntry.getContent() : entryInDB.getContent());
                journalEntryService.saveEntry(entryInDB);
                return ResponseEntity.status(HttpStatus.CREATED).body(entryInDB);
            }
            response.put("entryFound", false);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("errorMessage", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

}
