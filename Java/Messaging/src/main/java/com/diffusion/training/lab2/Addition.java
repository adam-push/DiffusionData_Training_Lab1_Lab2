package com.diffusion.training.lab2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.cbor.CBORFactory;
import com.pushtechnology.diffusion.client.Diffusion;
import com.pushtechnology.diffusion.datatype.binary.Binary;
import com.pushtechnology.diffusion.datatype.json.JSON;

public class Addition {

    private int num1;
    private int num2;
    private int sum;


    public Addition() {

    }

    public Addition(int num1, int num2, int sum) {
        this.num1 = num1;
        this.num2 = num2;
        this.sum = sum;
    }


    public int getNum1() {
        return num1;
    }


    public void setNum1(int num1) {
        this.num1 = num1;
    }


    public int getNum2() {
        return num2;
    }


    public void setNum2(int num2) {
        this.num2 = num2;
    }


    public int getSum() {
        return sum;
    }


    public void setSum(int sum) {
        this.sum = sum;
    }


    @Override
    public String toString() {
        return "Addition [num1=" + num1 + ", num2=" + num2 + ", sum=" + sum + "]";
    }


    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
