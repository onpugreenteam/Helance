package com.company.Helance.interfaces.navigators;

import com.company.Helance.dto.TaskDTO;

import java.util.List;

public interface TaskNavigator {

    void handleError(Throwable throwable);

    void getDataInAdapter(List<TaskDTO> taskDTOS);
}
