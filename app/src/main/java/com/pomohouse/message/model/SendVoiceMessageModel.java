package com.pomohouse.message.model;

import io.realm.RealmObject;

/**
 * Created by FoxjungNB on 9/4/2016.
 */
public class SendVoiceMessageModel extends RealmObject {

    private String name;
    private String status;
    private String relation;
    private String relationId;
    private String relationName;

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public String getRelationName() {
        return relationName;
    }

    public void setRelationName(String relationName) {
        this.relationName = relationName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
