package com.droid.waterhub;

public class InfoStor {

	public String catName;
    private Integer img_tag;
    private Integer memory;

    public InfoStor (String name) {
        catName = name;
    }

    String getName(){
        return catName;
    }

    void setImageTag(Integer img_tag){
        img_tag = img_tag;
    }

    Integer getImageTag(){
        return img_tag;
    }

    void setMemory(Integer m){
        memory = m;
    }

    Integer getMemory(){
        return memory;
    }
	
	
}
