package utils;

import com.example.dev.moviedb.utils.DtoUtils;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Created by pedrogabriel on 30-11-2015.
 */
public class DtoUtilsTest {


    @Test
    public void transformValidRuntimeValue(){
        final int validRuntime = 90;
        final String expectedTransformation = "1h30";
        Assert.assertEquals(expectedTransformation, DtoUtils.transformRuntime(validRuntime));
    }

    @Test
    public void transformRuntimeZero(){
        final int validRuntime = 0;
        final String expectedTransformation = "0h0";
        Assert.assertEquals(expectedTransformation, DtoUtils.transformRuntime(validRuntime));
    }

    @Test
    public void transformNegativeRuntimeValue(){
        final int validRuntime = -90;
        final String expectedTransformation = "0h0";
        Assert.assertEquals(expectedTransformation, DtoUtils.transformRuntime(validRuntime));
    }



}
