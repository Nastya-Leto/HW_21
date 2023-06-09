package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/*   {"steps" : [
      {
         "steps" : [],
         "stepsCount" : 0,
         "name" : "Шаг 1",
         "hasContent" : false,
         "leaf" : true
      },
      {
         "steps" : [],
         "stepsCount" : 0,
         "name" : "Шаг 2",
         "hasContent" : false,
         "leaf" : true }
*/
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddingStepsResponseModel {

    String name;
    Integer stepsCount;
    boolean hasContent, leaf;


}




