package com.csmaxwell.tes.service;

import com.csmaxwell.tes.domain.TesCourse;

import java.util.List;

/**
 * S
 * Created by maxwell on 2020/9/15.
 */
public interface TesCourseService {
    List<TesCourse> listAllCourse();

    int create(TesCourse tesCourseParam);
}
