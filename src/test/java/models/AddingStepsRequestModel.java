package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/*   {
       "steps" : [
       {
           "name" : "Шаг 1",
               "spacing" : ""
       },
       {
           "name" : "Шаг 2",
               "spacing" : ""
       },
  ],
       "workPath" : [
       2
  ]
   }
*/
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddingStepsRequestModel {
    private List<ListUsersData> steps;


    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ListUsersData {

        private String name, spacing;
        private Integer workPath;

    }
}




