package sg.edu.nus.iss.workshop22.controller;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.edu.nus.iss.workshop22.model.RSVP;
import sg.edu.nus.iss.workshop22.repo.RsvpRepoImpl;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class RSVPController {

    @Autowired
    RsvpRepoImpl rsvpRepo;

    // Get list of RSVPs
    // Another method of returning object directly

    //    @GetMapping("/rsvps")
    //    public ResponseEntity<List<RSVP>> getAllRSVPS() {
    //        List<RSVP> listRSVP;
    //
    //        listRSVP = rsvpRepo.findAll();
    //
    //        if (listRSVP.isEmpty()) {
    //            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //        } else {
    //            return new ResponseEntity<>(listRSVP, HttpStatus.OK);
    //        }
    //    }

    // Get list of RSVPs
    @GetMapping("/rsvps")
    public ResponseEntity<String> getAllRSVPS() {
        List<RSVP> listRSVP;

        listRSVP = rsvpRepo.findAll();

        if(listRSVP.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Json.createObjectBuilder()
                            .add("message", "No results found")
                            .build()
                            .toString());

        return ResponseEntity.status(HttpStatus.OK)
                .body(listRSVP.stream()
                        .map(v -> RSVP.RSVPToJson(v))
                        .toList()
                        .toString());
    }


    // Get list of RSVPs based on name
    @GetMapping(path = "/rsvp")
    public ResponseEntity<String> getRSVPSByName(@RequestParam(value = "q") String name) {

        Optional<List<RSVP>> opt = rsvpRepo.findByName(name);

        if (opt.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Json.createObjectBuilder()
                            .add("message", "No results found")
                            .build()
                            .toString());

        return ResponseEntity.status(HttpStatus.OK)
                .body(opt.get().stream()
                        .map(v -> RSVP.RSVPToJson(v))
                        .toList()
                        .toString());
    }

    // Upsert RSVP
    @PostMapping(path = "/rsvp", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> insertRSVP(@RequestParam("name") String name,
                                             @RequestParam("email") String email,
                                             @RequestParam("phone") int phone,
                                             @RequestParam("confirmation_date") Date confirmation_date,
                                             @RequestParam("comments") String comments) {
        RSVP newRSVP = new RSVP();
        newRSVP.setFullName(name);
        newRSVP.setEmail(email);
        newRSVP.setPhone(phone);
        newRSVP.setConfirmationDate(confirmation_date);
        newRSVP.setComments(comments);

        boolean added = rsvpRepo.upsert(newRSVP);

        if (added)
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("RSVP created");
        else
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT)
                    .body("Invalid RSVP, please check!");
    }

    // Update RSVP
    @PutMapping(path = "/rsvp/{email}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> updateRSVP(@PathVariable String email,
                                             @RequestBody RSVP rsvp) {
        boolean updated = rsvpRepo.update(rsvp);
        if (updated)
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("RSVP updated successfully");
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Email not found");
    }

    // Count RSVP
    @GetMapping(path = "/rsvps/count")
    public ResponseEntity<String> getCount() {
        int count = rsvpRepo.countAll();
        return ResponseEntity.status(HttpStatus.CREATED)
                    .body(String.valueOf(count));
    }

}
