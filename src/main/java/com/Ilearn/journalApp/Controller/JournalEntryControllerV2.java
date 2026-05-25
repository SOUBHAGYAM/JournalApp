package com.Ilearn.journalApp.Controller;

import com.Ilearn.journalApp.Entity.JournalEntry;
import com.Ilearn.journalApp.Entity.User;
import com.Ilearn.journalApp.service.JournalEntryService;
import com.Ilearn.journalApp.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;

import org.bson.types.ObjectId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
@CrossOrigin(origins = "http://localhost:4200")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    // =========================
    // GET ALL JOURNALS
    // =========================

    @GetMapping
    @Operation(summary = "Get all journal entries")
    public ResponseEntity<?> getAllJournalEntriesofUser() {

        // =========================
        // JWT AUTH LOGIC (TEMP COMMENTED)
        // =========================

        /*
        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        if (auth == null ||
                !auth.isAuthenticated() ||
                auth.getName().equals("anonymousUser")) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("User not authenticated");
        }

        String username = auth.getName();

        User user = userService.findByUsername(username);

        if (user == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("User not found in DB");
        }

        List<JournalEntry> journalEntries =
                user.getJournalEntries();
        */

        // TEMPORARY DIRECT DB FETCH

        List<JournalEntry> journalEntries =
                journalEntryService.getAll();

        return ResponseEntity.ok(journalEntries);
    }

    // =========================
    // CREATE JOURNAL
    // =========================

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(
            @RequestBody JournalEntry myEntry
    ) {

        try {

            myEntry.setDate(LocalDateTime.now());

            // =========================
            // JWT AUTH LOGIC (TEMP COMMENTED)
            // =========================

            /*
            Authentication authentication =
                    SecurityContextHolder
                            .getContext()
                            .getAuthentication();

            String username = authentication.getName();

            journalEntryService.saveEntry(myEntry, username);
            */

            // TEMP SAVE DIRECTLY

            journalEntryService.saveEntry(myEntry);

            return new ResponseEntity<>(
                    myEntry,
                    HttpStatus.CREATED
            );

        } catch (Exception e) {

            return new ResponseEntity<>(
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    // =========================
    // GET BY ID
    // =========================

    @GetMapping("/id/{myid}")
    public ResponseEntity<JournalEntry> getJournalEntryById(

            @Parameter(
                    name = "myid",
                    description = "Journal Entry ID",
                    required = true,
                    schema = @Schema(
                            type = "string",
                            example = "67c3f3e3dbbf5c2ef78cf91e"
                    )
            )

            @PathVariable("myid") String myid
    ) {

        ObjectId objectId = new ObjectId(myid);

        Optional<JournalEntry> journalEntryById =
                journalEntryService.getJournalEntryById(objectId);

        return journalEntryById
                .map(entry ->
                        new ResponseEntity<>(entry, HttpStatus.OK))
                .orElseGet(() ->
                        new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // =========================
    // DELETE
    // =========================

    @DeleteMapping("/id/{myid}")
    public ResponseEntity<?> deleteById(

            @PathVariable String myid
    ) {

        journalEntryService.deleteById(
                new ObjectId(myid)
        );

        return ResponseEntity.ok().build();
    }

    // =========================
    // UPDATE
    // =========================

    @PutMapping("/id/{id}")
    public ResponseEntity<?> updateJournalEntryById(

            @PathVariable ObjectId id,

            @RequestBody JournalEntry newEntry
    ) {

        Optional<JournalEntry> journalEntryById =
                journalEntryService.getJournalEntryById(id);

        if (journalEntryById.isPresent()) {

            JournalEntry old = journalEntryById.get();

            old.setTitle(
                    newEntry.getTitle() != null &&
                    !newEntry.getTitle().equals("")
                            ? newEntry.getTitle()
                            : old.getTitle()
            );

            old.setContent(
                    newEntry.getContent() != null &&
                    !newEntry.getContent().equals("")
                            ? newEntry.getContent()
                            : old.getContent()
            );

            journalEntryService.saveEntry(old);

            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}