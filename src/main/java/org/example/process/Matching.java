package org.example.process;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Matching {
    long from;
    long to;
    Accuracy accuracy;
}
