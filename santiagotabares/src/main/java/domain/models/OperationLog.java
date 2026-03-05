package domain.models;

import java.time.LocalDate;
import java.util.*;

import domain.models.enums.SystemRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class OperationLog {

    private String logId;
    private String operationType;
    private LocalDate timestamp;
    private int userId;
    private SystemRole userRole;
    private String affectedProductId;
    private Map<String, Object> detailData;
    
}
