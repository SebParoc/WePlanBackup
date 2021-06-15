package com.bakafulteam.weplan.domains;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Entity
@DynamicUpdate
public class WePlanFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    private long size;

    @Column
    private Date uploadTime;

    @Column (columnDefinition = "MEDIUMBLOB")
    private byte[] content;

    public WePlanFile () {
    }

    public WePlanFile (Long id, String name, long size) {
        this.id = id;
        this.name = name;
        this.size = size;
    }

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public long getSize () {
        return size;
    }

    public void setSize (long size) {
        this.size = size;
    }

    public Date getUploadTime () {
        return uploadTime;
    }

    public void setUploadTime (Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public byte[] getContent () {
        return content;
    }

    public void setContent (byte[] content) {
        this.content = content;
    }

    @Override
    public String toString () {
        return "WePlanFile{" +
                "name='" + name + '\'' +
                '}';
    }
}
