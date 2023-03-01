package sg.edu.nus.iss.workshop22.model;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.sql.Date;

public class RSVP {
    private String fullName;

    private String email;

    private Integer phone;

    private Date confirmationDate;

    private String comments;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public Date getConfirmationDate() {
        return confirmationDate;
    }

    public void setConfirmationDate(Date confirmationDate) {
        this.confirmationDate = confirmationDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public static JsonObject RSVPToJson(RSVP rsvp) {
        return Json.createObjectBuilder()
                .add("fullName", rsvp.getFullName())
                .add("email", rsvp.getEmail())
                .add("phone", rsvp.getPhone())
                .add("confirmationDate", String.valueOf(rsvp.getConfirmationDate()))
                .add("comments", rsvp.getComments())
                .build();
    }

    @Override
    public String toString() {
        return "RSVP{fullName='%s', email='%s', phone=%d, confirmationDate=%s, comments='%s'}\n".formatted(fullName, email, phone, confirmationDate, comments);
    }
}

