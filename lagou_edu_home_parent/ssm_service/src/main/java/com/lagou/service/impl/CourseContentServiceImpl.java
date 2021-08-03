package com.lagou.service.impl;

import com.lagou.dao.CourseContentMapper;
import com.lagou.domain.Course;
import com.lagou.domain.CourseSection;
import com.lagou.service.CourseContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CourseContentServiceImpl implements CourseContentService {

    @Autowired
    private CourseContentMapper courseContentMapper;


    @Override
    public List<CourseSection> findSectionAndLessonByCourseId(Integer courseId) {
        List<CourseSection> list = courseContentMapper.findSectionAndLessonByCourseId(courseId);
        return list;

    }

    @Override
    public Course findCourseByCourseId(Integer courseId) {
        Course course = courseContentMapper.findCourseByCourseId(courseId);
        return course;
    }

    @Override
    public void saveSection(CourseSection section) {
        section.setCreateTime(new Date());
        section.setUpdateTime(new Date());

        courseContentMapper.saveSection(section);
    }

    @Override
    public void updateSection(CourseSection section) {
        section.setUpdateTime(new Date());

        courseContentMapper.updateSection(section);
    }

    @Override
    public void updateSectionStatus(int id, int status) {
        Date date = new Date();

        CourseSection courseSection = new CourseSection();
        courseSection.setUpdateTime(date);
        courseSection.setStatus(status);
        courseSection.setId(id);

        courseContentMapper.updateSectionStatus(courseSection);

    }
}
