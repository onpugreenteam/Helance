package com.company.Helance.activity.lobby.commands;

import com.company.Helance.dto.ResumeDTO;

import java.util.List;

public interface ResumeNavigator {

    void handleError(Throwable throwable);

    void getDataInAdapter(List<ResumeDTO> resumeDTOS);
}
