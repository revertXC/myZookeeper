package com.revert.zk.lock.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class ZkModel implements Serializable{

    @Getter
    @Setter
    private String path;
    @Getter
    @Setter
    private String value;
    @Setter
    private byte data[];

    public byte[] getData(){
        return value.getBytes();
    }



}
