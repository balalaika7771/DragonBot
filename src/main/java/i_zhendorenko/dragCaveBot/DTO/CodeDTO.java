package i_zhendorenko.dragCaveBot.DTO;

import i_zhendorenko.dragCaveBot.models.Code;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

public class CodeDTO {
    public CodeDTO(){}
    public CodeDTO(String code){
        this.mycode = code;
    }
    public CodeDTO(Code code){
        this.mycode = code.getCode();
    }

    @Override
    public String toString(){
        return  mycode;
    }
    @Override
    public boolean equals(Object code){
        if (!(code instanceof CodeDTO)){
            return false;
        }

        return ((CodeDTO)code).getCode().equals(mycode);
    }
    public boolean equals(Code code){
        return code.getCode().equals(mycode);
    }
    @NotEmpty(message = "code не должно быть пустым")
    @Column(name = "code")
    private String mycode;

    public String getCode() {
        return mycode;
    }

    public void setCode(String mycode) {
        this.mycode = mycode;
    }
}
