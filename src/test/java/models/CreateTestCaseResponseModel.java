package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateTestCaseResponseModel {

    Integer id;
    String name, statusName, statusColor;

}
