package i_zhendorenko.dragCaveBot.DTO;

import i_zhendorenko.dragCaveBot.models.CoolCode;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

public class CoolCodeDTO {
    public CoolCodeDTO(){}
    public CoolCodeDTO(String code){
        this.mycode = code;
    }
    public CoolCodeDTO(CoolCode coolCode){
        this.mycode = coolCode.getCode();
        this.id = coolCode.getId();
    }

    @Override
    public String toString(){
        return  mycode;
    }
    @Override
    public boolean equals(Object code){
        if (!(code instanceof CoolCodeDTO)){
            return false;
        }

        return ((CoolCodeDTO)code).getCode().equals(mycode);
    }
    public boolean equals(CoolCode coolCode){
        return coolCode.getCode().equals(mycode);
    }

    private String mycode;
    private int id;
    public String getCode() {
        return mycode;
    }

    public void setCode(String mycode) {
        this.mycode = mycode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
