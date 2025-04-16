package in.guardianservices.coverletter.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@ToString
public class CoverLetterRequest {

    @JsonProperty("sFirstName")
    private String firstName;

    @JsonProperty("sLastName")
    private String lastName;

    @JsonProperty("sCompanyName")
    private String companyName;

    @JsonProperty("sHireManagerName")
    private String hireManagerName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getHireManagerName() {
        return hireManagerName;
    }

    public void setHireManagerName(String hireManagerName) {
        this.hireManagerName = hireManagerName;
    }


}
