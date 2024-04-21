package com.example.common.enums;

public enum AdoptAstatusEnum {
    // Astatus of adopt
    ADOPT_YES("Adopted"),
    ADOPT_NO("waiting Adoption"),
    ;
    public String status;
    AdoptAstatusEnum(String status){
        this.status = status;
    }


}
