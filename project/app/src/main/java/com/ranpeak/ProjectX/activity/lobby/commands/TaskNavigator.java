package com.ranpeak.ProjectX.activity.lobby.commands;

import com.ranpeak.ProjectX.dto.TaskDTO;

import java.util.List;

public interface TaskNavigator {

    void handleError(Throwable throwable);

    void getDataInAdapter(List<TaskDTO> taskDTOS);
}
