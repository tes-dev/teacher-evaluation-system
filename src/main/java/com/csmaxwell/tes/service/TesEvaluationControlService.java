package com.csmaxwell.tes.service;

import com.csmaxwell.tes.domain.TesEvaluationControl;

import java.util.List;

public interface TesEvaluationControlService {


    TesEvaluationControl select(Long semesterId, TesEvaluationControl tesEvaluationControl);

    List<TesEvaluationControl> tecList(Long semesterId);
}
