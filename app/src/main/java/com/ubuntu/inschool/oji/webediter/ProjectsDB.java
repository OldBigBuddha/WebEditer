package com.ubuntu.inschool.oji.webediter;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by oji on 16/07/16.
 */

@Table(name = "Projects")
public class ProjectsDB extends Model {

    @Column(name = "ProjectName")
    public String projectName;

    @Column(name = "extension")
    public int extension;

    public ProjectsDB() {
        super();
    }

    public ProjectsDB(String projectName, int extension) {
        super();
        this.projectName = projectName;
        this.extension = extension;
    }
}
