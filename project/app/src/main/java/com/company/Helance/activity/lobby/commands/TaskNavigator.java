package com.company.Helance.activity.lobby.commands;

import com.company.Helance.dto.TaskDTO;

import java.util.List;

public interface TaskNavigator {

    void handleError(Throwable throwable);

    void getDataInAdapter(List<TaskDTO> taskDTOS);
}
