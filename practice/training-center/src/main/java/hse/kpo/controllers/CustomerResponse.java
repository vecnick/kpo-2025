package hse.kpo.controllers;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerResponse {
    private int id;
    private String name;
    private int legPower;
    private int handPower;
    private int iq;
}
