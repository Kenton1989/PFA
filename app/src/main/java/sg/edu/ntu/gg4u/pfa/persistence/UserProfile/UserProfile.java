package sg.edu.ntu.gg4u.pfa.persistence.UserProfile;

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
    private int age;
    private AcademicQualification qualification;

    @Ignore
    public UserProfile(String _name, Gender _gender, JobField _jobField,
                       int _familySize, double _income, AcademicQualification aq) {
        userId = UUID.randomUUID().toString();

        name = _name;
        gender = _gender;
        jobField = _jobField;
        familySize = _familySize;
        income = _income;
        qualification = aq;
    }

    public UserProfile() {
        userId = "dummy";
        name = "dummy";
        gender = Gender.MALE;
        jobField = JobField.UNKNOWN;
        familySize = 0;
        income = 0;
        qualification = AcademicQualification.UNKNOWN;
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

    public int getAge() {
        return age;
    }

    public AcademicQualification getQualification() {
        return qualification;
    }

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

    public void setAge(int age) {
        this.age = age;
    }

    public void setQualification(AcademicQualification qualification) {
        this.qualification = qualification;
    }

    public void setUserId(@NonNull String userId) {
        this.userId = userId;
    }
}
