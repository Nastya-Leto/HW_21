package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

// [{"id":1041,"name":"smouk"}]
//[{"id":1040,"name":"Автотесты"},{"id":1041,"name":"smouk"}]

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddingTegRequestModel {
        private String name;
        private Integer id;

    }




