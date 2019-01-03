package com.example.storage.test;

import com.example.storage.QuickstartSample;
import org.junit.Test;

public class QuickstartSampleTest {


@Test
    public void test1() {
        QuickstartSample sample = new QuickstartSample();
        System.out.println(" bytes)");
        System.out.println(sample);
        try {
            sample.main(null);
        } catch(Throwable e) {
            e.printStackTrace();
        }
    }



}
