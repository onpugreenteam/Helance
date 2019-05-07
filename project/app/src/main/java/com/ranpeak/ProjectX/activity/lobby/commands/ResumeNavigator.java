package com.ranpeak.ProjectX.activity.lobby.commands;

import com.ranpeak.ProjectX.dto.ResumeDTO;

import java.util.List;

public interface ResumeNavigator {

    void handleError(Throwable throwable);

    void getDataInAdapter(List<ResumeDTO> resumeDTOS);
}
