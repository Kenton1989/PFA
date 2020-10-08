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
    private Integer familySize;
    private Double income;
    private Integer age;
    private AcademicQualification qualification;

    @Ignore
    public UserProfile() {
        userId = UUID.randomUUID().toString();

        name = null;
        gender = null;
        jobField = null;
        familySize = null;
        income = null;
        age = null;
        qualification = null;
    }

    public UserProfile(@NonNull String id, String _name, Gender _gender, JobField _jobField,
                       Integer _familySize, Double _income, Integer _age,
                       AcademicQualification _qualification) {
        userId = id;
        name = _name;
        gender = _gender;
        jobField = _jobField;
        familySize = _familySize;
        income = _income;
        age = _age;
        qualification = _qualification;
    }

    // All getter and setter
    @NonNull
    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public JobField getJobField() {
        return jobField;
    }

    public Integer getFamilySize() {
        return familySize;
    }

    public Double getIncome() {
        return income;
    }

    public Integer getAge() {
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

    public void setIncome(Double income) {
        this.income = income;
    }

    public void setFamilySize(Integer familySize) {
        this.familySize = familySize;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setQualification(AcademicQualification qualification) {
        this.qualification = qualification;
    }
}
