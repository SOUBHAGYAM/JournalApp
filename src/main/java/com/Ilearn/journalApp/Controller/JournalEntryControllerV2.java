package com.Ilearn.journalApp.Controller;

import com.Ilearn.journalApp.Entity.JournalEntry;
import com.Ilearn.journalApp.Entity.User;
import com.Ilearn.journalApp.service.JournalEntryService;
import com.Ilearn.journalApp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/journal")
@Tag(name = "Journal APIs")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(summary = "Get all journal entries of a user")
    public ResponseEntity<?> getAllJournalEntriesofUser() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authentication => " + auth);

        if (auth == null || !auth.isAuthenticated() || auth.getName().equals("anonymousUser")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        String username = auth.getName();
        System.out.println("Username => " + username);

        User user = userService.findByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found in DB");
        }

        List<JournalEntry> journalEntries = user.getJournalEntries();

        if (journalEntries == null || journalEntries.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No journal entries found");
        }

        return ResponseEntity.ok(journalEntries);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            myEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(myEntry, username);
            return new ResponseEntity<JournalEntry>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{myid}")
    public ResponseEntity<JournalEntry> getJournalEntryById(
            @Parameter(
                    name = "myid",
                    description = "Journal Entry ID (MongoDB ObjectId)",
                    required = true,
                    schema = @Schema(type = "string", example = "67c3f3e3dbbf5c2ef78cf91e")
            )
            @PathVariable("myid") String myid) {

        ObjectId objectId = new ObjectId(myid);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userService.findByUsername(username);

        List<JournalEntry> collect = user.getJournalEntries().stream()
                .filter(x -> x.getId().equals(objectId))
                .collect(Collectors.toList());

        if (!collect.isEmpty()) {
            Optional<JournalEntry> journalEntryById = journalEntryService.getJournalEntryById(objectId);
            return journalEntryById.map(entry -> new ResponseEntity<>(entry, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/id/{myid}")
    public ResponseEntity<?> deleteJournalEntrybyid(@PathVariable ObjectId myid) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean removed = journalEntryService.deleteJournalEntryById(myid, username);
        if (removed) {
            return ResponseEntity.ok("Journal entry deleted successfully");
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> updateJournalEntryById(
            @PathVariable ObjectId id,
            @RequestBody JournalEntry newEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
        if (!collect.isEmpty()) {
            Optional<JournalEntry> journalEntryById = journalEntryService.getJournalEntryById(id);
            if (journalEntryById.isPresent()) {
                JournalEntry old = journalEntryById.get();
                old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
                old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
                journalEntryService.saveEntry(old);
                return new ResponseEntity<>(HttpStatus.OK);

            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}

