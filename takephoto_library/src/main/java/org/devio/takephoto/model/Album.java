package org.devio.takephoto.model;

import java.io.Serializable;

/**
 * Created by Darshan on 4/14/2015.
 */
public class Album implements Serializable {
    public String name;
    public String cover;

    public Album(String name, String cover) {
        this.name = name;
        this.cover = cover;
    }
}
