package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.service.JournalEntryService;
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

//    @GetMapping("/getAll")
//    public ResponseEntity<?> getAllEntries() {
//        Map<String, Object> response = new HashMap<>();
//        List<JournalEntry> entries = journalEntryService.getAllEntry();
//        if (entries != null && !entries.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.OK).body(entries);
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
//            return ResponseEntity.status(HttpStatus.OK).body(entry.get());
//        }
//        response.put("recordFound", false);
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
//    }
//
//    @PostMapping("/createEntry")
//    public ResponseEntity<?> createEntry(@RequestBody JournalEntry journalEntry) {
//        Map<String, Object> response = new HashMap<>();
//        try {
//            journalEntry.setDate(LocalDateTime.now());
//            journalEntryService.saveEntry(journalEntry);
//            return ResponseEntity.status(HttpStatus.CREATED).body(journalEntry);
//        } catch (Exception e) {
//            response.put("entryCreated", false);
//            response.put("errorMessage", e.getMessage());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//        }
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<?> deleteById(@PathVariable ObjectId id) {
//        Map<String, Object> response = new HashMap<>();
//        try {
//            Optional<JournalEntry> entry = journalEntryService.findById(id);
//            if (!entry.isPresent()) {
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
//        Optional<JournalEntry> entry = journalEntryService.findById(id);
//        JournalEntry oldEntry = entry.orElse(null);
//        if (oldEntry != null) {
//            oldEntry.setTitle(newEntry.getTitle() != null ? newEntry.getTitle() : oldEntry.getTitle());
//            oldEntry.setContent(newEntry.getContent() != null ? newEntry.getContent() : oldEntry.getContent());
//            journalEntryService.saveEntry(oldEntry);
//            return ResponseEntity.status(HttpStatus.OK).body(oldEntry);
//        }
//        response.put("recordFound", false);
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
//    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllEntries() {
        Map<String, Object> response = new HashMap<>();
        List<JournalEntry> entries = journalEntryService.getAllEntry();
        if (entries != null && !entries.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FOUND).body(entries);
        }
        response.put("recordFound", false);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @GetMapping("/singleEntry/{id}")
    public ResponseEntity<?> findById(@PathVariable ObjectId id) {
        Map<String, Object> response = new HashMap<>();
        Optional<JournalEntry> entry = journalEntryService.findById(id);
        if (entry.isPresent()) {
            return ResponseEntity.status(HttpStatus.FOUND).body(entry.get());
        }
        response.put("recordFound", false);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @PostMapping("/createEntry")
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry journalEntry) {
        Map<String, Object> response = new HashMap<>();
        try {
            journalEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(journalEntry);
            response.put("recordCreated", true);
            response.put("record", journalEntry);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.put("recordCreated", false);
            response.put("errorMessage", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable ObjectId id) {
        Map<String, Object> response = new HashMap<>();
        try {
            JournalEntry entry = journalEntryService.findById(id).orElse(null);
            if (entry == null) {
                response.put("recordFound", false);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            journalEntryService.deleteById(id);
            response.put("entryDeleted", true);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.put("entryDeleted", false);
            response.put("errorMessage", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateById(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry) {
        Map<String, Object> response = new HashMap<>();
        JournalEntry oldEntry = journalEntryService.findById(id).orElse(null);
        if (oldEntry != null) {
            oldEntry.setTitle(newEntry.getTitle() != null ? newEntry.getTitle() : oldEntry.getTitle());
            oldEntry.setContent(newEntry.getContent() != null ? newEntry.getContent() : oldEntry.getContent());
            journalEntryService.saveEntry(oldEntry);
            return ResponseEntity.status(HttpStatus.OK).body(oldEntry);
        }
        response.put("recordFound", false);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
