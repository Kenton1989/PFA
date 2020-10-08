package sg.edu.ntu.gg4u.pfa.persistence;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "UserProfile")
public class UserProfile {

    @NonNull
    @PrimaryKey
    private String userId;

    private String name;
    private Gender gender;
    private JobField jobField;
    private int familySize;
    private double income;

    @Ignore
    public UserProfile(String _name, Gender _gender, JobField _jobField,
                       int _familySize, double _income) {
        userId = UUID.randomUUID().toString();

        name = _name;
        gender = _gender;
        jobField = _jobField;
        familySize = _familySize;
        income = _income;
    }

    public UserProfile(@NonNull String id, String _name, Gender _gender, JobField _jobField,
                       int _familySize, double _income) {
        userId = id;
        name = _name;
        gender = _gender;
        jobField = _jobField;
        familySize = _familySize;
        income = _income;
    }

    // after that, all getter and setter stuff...


    @NonNull
    public String getUserId() {
        return userId;
    }

    public String getName() { return name; }

    public Gender getGender() {
        return gender;
    }

    public JobField getJobField() {
        return jobField;
    }

    public int getFamilySize() {
        return familySize;
    }

    public double getIncome() { return income; }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setJobField(JobField jobField) {
        this.jobField = jobField;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public void setFamilySize(int familySize) {
        this.familySize = familySize;
    }
}
