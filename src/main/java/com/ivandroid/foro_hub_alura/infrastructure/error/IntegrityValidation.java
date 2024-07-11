package com.ivandroid.foro_hub_alura.infrastructure.error;

public class IntegrityValidation extends RuntimeException{
    public IntegrityValidation(String s){
        super(s);
    }
}
